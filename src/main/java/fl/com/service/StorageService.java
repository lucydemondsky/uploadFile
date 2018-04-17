package fl.com.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
* @author XL
* @version create time��2018��4��16�� ����3:48:24
* 
*/
public interface StorageService {
	void store(MultipartFile file);
	Stream<Path> loadAll();
	Path load(String filename);
	Resource loadAsResource(String filename);
}
