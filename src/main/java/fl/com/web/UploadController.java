package fl.com.web;

import java.io.IOException;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fl.com.exception.NotFoundException;
import fl.com.service.StorageService;

/**
* @author FL
* @version create time??2018??4??16?? ????3:38:01
* 
*/

@Controller
public class UploadController {
	private final StorageService storageService;
	
	@Autowired
	public UploadController(StorageService storageServie){
		this.storageService = storageServie;
	}
	@GetMapping("/")
	public String listFiles(Model model) throws IOException{
		
		model.addAttribute("files", storageService.loadAll().map(path-> MvcUriComponentsBuilder
				.fromMethodName(UploadController.class, "storeFile", path.getFileName().toString())
				.build().toString()).collect(Collectors.toList()));
		
		return "uploadForm";
	}
	
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> storeFile(@PathVariable String filename){
		Resource fileResource = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileResource.getFilename() + "\"").body(fileResource);
	}
	@PostMapping("/")
	public String fileUploaded(@RequestParam("file") MultipartFile file, 
			RedirectAttributes redirectAttributes){

		storageService.store(file);
		redirectAttributes.addFlashAttribute("message", "Successfully!! You uploaded" + file.getOriginalFilename());
		return "redirect:/";
	}
	
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> FileNotFoundException(NotFoundException exception){
		return ResponseEntity.notFound().build();
	}
}
