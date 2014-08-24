package io.enscene.register.api.old;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

class ServiceImpl implements Service {

	private ServiceId id;
	
	private String path;

	public ServiceImpl() {
	}
	
	public ServiceImpl(ServiceId id, String path) {
		this.id = id;
		this.path = path;
	}

	ServiceImpl(Builder builder) {
		this.id = builder.id;
		this.path = builder.path;
	}

	@Override
	@JsonProperty
	public ServiceId getId() {
		return id;
	}

	@Override
	@JsonProperty
	public String getPath() {
		return this.path;
	}

	public void setId(ServiceId id) {
		this.id = id;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.path);
	}

	@Override
	public boolean equals(Object obj) {
		 if (obj == null) {  
	         return false;  
	      }  
	      if (getClass() != obj.getClass()) {  
	         return false;  
	      }  
	      final ServiceImpl other = (ServiceImpl) obj;  
	      return equal(this.path, other.path);
	}

	@Override
	public String toString() {
		return toStringHelper(this)
				.addValue(path)
				.toString();
	}
	
}

