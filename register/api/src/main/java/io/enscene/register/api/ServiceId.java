package io.enscene.register.api;

import com.google.common.base.Objects;

public class ServiceId {

//	@FormParam("name")	
	private String name;
	
//	@FormParam("version")
	private String version;
	
	public static ServiceId from(String name, String version) {
		return new ServiceId(name, version);
	}
	
	public ServiceId() {
	}
	
	public ServiceId(String name, String version) {
		this.name = name;
		this.version = version;
	}

	public String getName() {
		return this.name;
	}

	public String getVersion() {
		return this.version;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.name, this.version);
	}

	@Override
	public boolean equals(Object obj) {
		 if (obj == null) {  
	         return false;  
	      }  
	      if (getClass() != obj.getClass()) {  
	         return false;  
	      }  
	      final ServiceId other = (ServiceId) obj;  
	      return Objects.equal(this.name, other.name) && Objects.equal(this.version, other.version);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(name)
				.addValue(version)
				.toString();
	}
	
}
