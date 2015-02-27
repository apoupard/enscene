package io.enscene.register.api.old;

import static com.google.common.base.Preconditions.checkArgument;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface ServiceId {

  String getName();

  String getVersion();

  @JsonCreator
  public static ServiceId from(@JsonProperty("name") String name,
      @JsonProperty("version") String version) {
    return new Builder().withName(name).withVersion(version).get();
  }

  public static Builder build() {
    return new Builder();
  }

  public static class Builder {

    String name;

    String version;

    private Builder() {}

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withVersion(String version) {
      this.version = version;
      return this;
    }

    public ServiceId get() {
      ServiceId service = new ServiceIdImpl(this);
      checkArgument(service.getName() != null, " service id cannot be null");
      checkArgument(service.getVersion() != null, "path cannot be null");
      return service;
    }
  }

}
