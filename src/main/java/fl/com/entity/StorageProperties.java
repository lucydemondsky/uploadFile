package fl.com.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
* @author XL
* @version create time：2018年4月16日 下午5:04:37
* 
*/
@ConfigurationProperties("storage")
public class StorageProperties {
	private String locationString = "dir";
	
	public String getLocationString() {
		return locationString;
	}

	public void setLocationString(String locationString) {
		this.locationString = locationString;
	}
}
