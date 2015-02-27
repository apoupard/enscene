package io.enscene.register.client;

import io.enscene.register.api.RegisterException;
import io.enscene.register.api.Service;
import io.enscene.register.api.ServiceRessource;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

import javax.ws.rs.core.Link;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

public class ServiceClient implements ServiceRessource {

  private final ServiceRessource serviceRessource;

  public static ServiceClient with(URL baseURL) throws URISyntaxException {
    URI uri = baseURL.toURI();
    return with(Link.fromUri(uri).build());
  }

  public static ServiceClient with(Link link) {
    return new ServiceClient(link);
  }

  private ServiceClient(Link link) {
    this.serviceRessource =
        new ResteasyClientBuilder().build().target(link).proxy(ServiceRessource.class);
  }

  @Override
  public void register(Service service) throws RegisterException {
    serviceRessource.register(service);
  }

  @Override
  public void unregister(String name, String version, String url) throws RegisterException {
    serviceRessource.unregister(name, version, url);
  }

  @Override
  public Service findOne(String name, String version) throws RegisterException {
    return serviceRessource.findOne(name, version);
  }

  @Override
  public Collection<Service> findAll(String name, String version) throws RegisterException {
    return serviceRessource.findAll(name, version);
  }

  @Override
  public String test() throws RegisterException {
    return serviceRessource.test();
  }


}
