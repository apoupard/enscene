package io.enscene.register.api;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;

public class Service {

	private ServiceId id;
	
	private String path;

	public static Service from(ServiceId id, String path) {
		return new Service(id, path);
	}
	
	public Service() {
	}

	public Service(ServiceId id, String path) {
		this.id = checkNotNull(id);
		this.path = checkNotNull(path);
	}

	public ServiceId getId() {
		return id;
	}

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
		final Service other = (Service) obj;
		return equal(this.path, other.path);
	}

	@Override
	public String toString() {
		return toStringHelper(this).addValue(path).toString();
	}

}
