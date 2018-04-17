package fl.com;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.Test;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;

import org.mockito.BDDMockito.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;



import fl.com.service.StorageService;

/**
* @author XL
* @version create time：2018年4月17日 上午10:56:08
* 
*/

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UploadFileTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private StorageService storageService;
	
	@Test
	public void listAllFiles() throws Exception{
		given(this.storageService.loadAll()).willReturn(Stream.of(Paths.get("1.txt"), Paths.get("2.txt")));
		
		this.mvc.perform(get("/")).andExpect(status().isOk()).andExpect(model().attribute("files", Matchers.contains("http://localhost/files/1.txt","http://localhost/files/2.txt")));
	}
	@Test
	public void UploadFile() throws Exception{
		MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "frida lu".getBytes());
		this.mvc.perform(fileUpload("/").file(multipartFile)).andExpect(status().isFound()).andExpect(header().string("Location", "/"));
		then(this.storageService).should().store(multipartFile);
	}
	
	
}
