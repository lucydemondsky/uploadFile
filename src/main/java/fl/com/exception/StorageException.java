package fl.com.exception;
/**
* @author XL
* @version create time：2018年4月16日 下午5:20:07
* 
*/
public class StorageException extends RuntimeException{
	public StorageException(String message){
		super(message);
	}
	
	public StorageException(String message, Throwable cause){
		super(message, cause);
	}
}
