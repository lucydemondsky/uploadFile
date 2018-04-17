package fl.com.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import fl.com.entity.StorageProperties;
import fl.com.exception.NotFoundException;
import fl.com.exception.StorageException;

/**
* @author XL
* @version create time：2018年4月16日 下午5:13:22
* 
*/

@Service
public class StorageServiceImpl implements StorageService{
	private final Path rootLocation;
	@Autowired
	public StorageServiceImpl(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocationString());
	}
	
	@Override
	public void store(MultipartFile file) {
		try {
			if(file.isEmpty()){
				throw new StorageException("Failed to store empty file" + file.getOriginalFilename());
			}
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
		} catch (IOException e) {
			throw new StorageException("Failed to store file" + file.getOriginalFilename(), e);
		}
		
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation)).
					map(this.rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Fail to read stores files", e);
		}
	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {		
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()){
				return resource;
			}
			else{
				throw new NotFoundException("Can not found file:" + filename);
			}
		} catch (MalformedURLException e) {
			throw new NotFoundException("Can not found file:" + filename, e);
		}
		
	}

}
