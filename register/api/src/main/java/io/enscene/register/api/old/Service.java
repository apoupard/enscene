package io.enscene.register.api.old;

import static com.google.common.base.Preconditions.checkArgument;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface Service {

  ServiceId getId();

  String getPath();

  @JsonCreator
  public static Service from(@JsonProperty("id") ServiceId id, @JsonProperty("path") String path) {
    return new Builder().withServiceId(id).withPath(path).get();
  }


  public static Builder builder() {
    return new Builder();
  }


  public static class Builder {

    ServiceId id;

    String path;

    private Builder() {}

    public Builder withServiceId(ServiceId id) {
      this.id = id;
      return this;
    }

    public Builder withPath(String path) {
      this.path = path;
      return this;
    }

    public Service get() {
      Service service = new ServiceImpl(this);
      checkArgument(service.getId() != null, "The serviceId cannot be null");
      checkArgument(service.getPath() != null, "The path cannot be null");
      return service;
    }
  }

}
