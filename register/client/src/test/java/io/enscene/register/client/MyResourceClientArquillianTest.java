package io.enscene.register.client;

import static org.assertj.core.api.Assertions.*;
import io.enscene.register.api.RegisterException;
import io.enscene.register.api.Service;
import io.enscene.register.api.ServiceId;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import static org.jboss.shrinkwrap.api.ShrinkWrap.*;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.jboss.shrinkwrap.resolver.api.maven.Maven.resolver;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MyResourceClientArquillianTest {

  private static final String SERVICE1_URL = "url";
  private static final String SERVICE2_URL = "url2";

  @Deployment
  public static WebArchive createDeployment() throws IOException {
    return create(WebArchive.class).addAsLibraries(
        resolver().loadPomFromFile("pom.xml").importRuntimeAndTestDependencies().resolve()
            .withTransitivity().asFile());
  }

  @ArquillianResource
  private URL baseURL;

  @Test
  @RunAsClient
  @InSequence(1)
  public void should_register_service() throws URISyntaxException, RegisterException {
    ServiceId id = ServiceId.from("serviceName", "1.0");
    Service service = Service.from(id, SERVICE1_URL);

    ServiceClient client = ServiceClient.with(baseURL);
    client.register(service);

  }

  @Test
  @RunAsClient
  @InSequence(2)
  public void should_retrieveRegisteredService() throws RegisterException, URISyntaxException {

    ServiceClient client = ServiceClient.with(baseURL);
    Service service = client.findOne("serviceName", "1.0");

    assertThat(service).isNotNull();
    assertThat(service.getPath()).isEqualTo(SERVICE1_URL);
  }

  @Test
  @RunAsClient
  @InSequence(3)
  public void should_register_service2() throws RegisterException, URISyntaxException {
    ServiceId id = ServiceId.from("serviceName", "1.0");
    Service service = Service.from(id, SERVICE2_URL);

    ServiceClient client = ServiceClient.with(baseURL);
    client.register(service);

  }

  @Test
  @RunAsClient
  @InSequence(4)
  public void should_retrieveRegisteredServices() throws RegisterException, URISyntaxException {
    ServiceClient client = ServiceClient.with(baseURL);
    Collection<Service> services = client.findAll("serviceName", "1.0");

    assertThat(services).hasSize(2);
  }

  @Test
  @RunAsClient
  @InSequence(5)
  public void unRegisteredService1() throws RegisterException, URISyntaxException {
    ServiceClient client = ServiceClient.with(baseURL);
    client.unregister("serviceName", "1.0", SERVICE1_URL);
  }

  @Test
  @RunAsClient
  @InSequence(6)
  public void should_retrieveOnlyOneRegisteredService() throws URISyntaxException,
      RegisterException {
    ServiceClient client = ServiceClient.with(baseURL);
    Collection<Service> services = client.findAll("serviceName", "1.0");

    assertThat(services).hasSize(1);
  }

  @Test
  @RunAsClient
  @InSequence(7)
  public void unRegisteredService2() throws URISyntaxException, RegisterException {
    ServiceClient client = ServiceClient.with(baseURL);
    client.unregister("serviceName", "1.0", SERVICE2_URL);

  }

  @Test
  @RunAsClient
  @InSequence(8)
  public void should_retrieveNoRegisteredService() throws URISyntaxException, RegisterException {
    ServiceClient client = ServiceClient.with(baseURL);
    Collection<Service> services = client.findAll("serviceName", "1.0");

    assertThat(services).isEmpty();;
  }

}
