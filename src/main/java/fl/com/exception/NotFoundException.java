package fl.com.exception;
/**
* @author XL
* @version create time：2018年4月16日 下午5:36:00
* 
*/
public class NotFoundException extends StorageException{
	public NotFoundException(String message){
		super(message);
	}
	public NotFoundException(String message, Throwable cause){
		super(message, cause);
	}
}
