package io.enscene.register.api.old;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

class ServiceIdImpl implements ServiceId {

  private String name;
  private String version;

  public ServiceIdImpl(String name, String version) {
    super();
    this.name = name;
    this.version = version;
  }

  ServiceIdImpl(Builder builder) {
    this.name = builder.name;
    this.version = builder.version;
  }

  @Override
  @JsonProperty
  public String getName() {
    return this.name;
  }

  @Override
  @JsonProperty
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
    final ServiceIdImpl other = (ServiceIdImpl) obj;
    return Objects.equal(this.name, other.name) && Objects.equal(this.version, other.version);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).addValue(name).addValue(version).toString();
  }


}
