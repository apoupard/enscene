package io.enscene.register.server.zookeeper;

import java.util.List;

import io.enscene.register.api.Service;
import io.enscene.register.api.ServiceId;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.fest.assertions.Assertions.*;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MyResourceArquillianTest {

	private static final String SERVICE1_URL = "url";
	private static final String SERVICE2_URL = "url2";

	@Deployment
	public static WebArchive createDeployment() {
		WebArchive webArchive = ShrinkWrap
				.create(WebArchive.class, "register.war")
				.addPackages(true, RegisterMicroService.class.getPackage())
				.addAsLibraries(
						Maven.resolver()
								.resolve(
									"io.enscene.core:enscene-core-impl-resteasy:0.0.1-SNAPSHOT"
								)
								.withTransitivity().asFile());
		System.out.println(webArchive.toString(true));
		return webArchive;
	}
	
	@Test
	@RunAsClient
	@InSequence(1)
	public void test(@ArquillianResteasyResource("services/test") ResteasyWebTarget webTarget) {
		String response = webTarget.request().get(String.class);
		assertThat(response).isEqualTo("test");
	}
	
	@Test
	@RunAsClient
	@InSequence(1)
	public void should_register_service(@ArquillianResteasyResource("services") ResteasyWebTarget webTarget) {
		ServiceId id = ServiceId.from("serviceName", "1.0");
		Service service = Service.from(id, SERVICE1_URL);
		Response response = webTarget.request().post(Entity.entity(service, MediaType.APPLICATION_JSON));
		assertThat(response.getStatus()).isEqualTo(204);
	}
	
	@Test
	@RunAsClient
	@InSequence(2)
	public void should_retrieveRegisteredService(@ArquillianResteasyResource("services/serviceName/1.0/instance") ResteasyWebTarget webTarget) {
		Service service = webTarget.request().get(Service.class);
		
		assertThat(service).isNotNull();
		assertThat(service.getPath()).isEqualTo(SERVICE1_URL);
	}
	
	@Test
	@RunAsClient
	@InSequence(3)
	public void should_register_service2(@ArquillianResteasyResource("services") ResteasyWebTarget webTarget) {
		ServiceId id = ServiceId.from("serviceName", "1.0");
		Service service = Service.from(id, SERVICE2_URL);
		Response response = webTarget.request().post(Entity.entity(service, MediaType.APPLICATION_JSON));
		assertThat(response.getStatus()).isEqualTo(204);
	}
	
	@Test
	@RunAsClient
	@InSequence(4)
	public void should_retrieveRegisteredServices(@ArquillianResteasyResource("services/serviceName/1.0/instances") ResteasyWebTarget webTarget) {
		List<Service> services = webTarget.request().get(new GenericType<List<Service>>() {});
		assertThat(services).hasSize(2);
	}
	
	@Test
	@RunAsClient
	@InSequence(5)
	public void unRegisteredService1(@ArquillianResteasyResource("services/serviceName/1.0/url") ResteasyWebTarget webTarget) {
		Response response = webTarget.request().delete();
		assertThat(response.getStatus()).isEqualTo(204);
		
	}
	
	@Test
	@RunAsClient
	@InSequence(6)
	public void should_retrieveOnlyOneRegisteredService(@ArquillianResteasyResource("services/serviceName/1.0/instances") ResteasyWebTarget webTarget) {
		List<Service> services = webTarget.request().get(new GenericType<List<Service>>() {});
		assertThat(services).hasSize(1);
	}
	
	@Test
	@RunAsClient
	@InSequence(7)
	public void unRegisteredService2(@ArquillianResteasyResource("services/serviceName/1.0/url2") ResteasyWebTarget webTarget) {
		Response response = webTarget.request().delete();
		assertThat(response.getStatus()).isEqualTo(204);
		
	}
	
	@Test
	@RunAsClient
	@InSequence(8)
	public void should_retrieveNoRegisteredService(@ArquillianResteasyResource("services/serviceName/1.0/instances") ResteasyWebTarget webTarget) {
		List<Service> services = webTarget.request().get(new GenericType<List<Service>>() {});
		assertThat(services).isEmpty();	;
	}

}
