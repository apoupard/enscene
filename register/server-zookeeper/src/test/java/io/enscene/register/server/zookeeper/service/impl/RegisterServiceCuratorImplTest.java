package io.enscene.register.server.zookeeper.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import io.enscene.register.api.Service;
import io.enscene.register.api.ServiceId;
import io.enscene.register.server.zookeeper.RegisterMicroService;
import io.enscene.register.server.zookeeper.core.ZookeeperServer;

import java.io.IOException;
import java.util.Collection;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class RegisterServiceCuratorImplTest {

    Injector injector = Guice.createInjector(new RegisterMicroService());
    
	private static RegisterServiceCuratorImpl discovery;
    
    private static ZookeeperServer zookeeperServer;
	
	private static CuratorFramework client;
	
	@BeforeClass
	public static void startServer() throws Exception {
		zookeeperServer = new ZookeeperServer("localhost", 8092);
		zookeeperServer.start();
		client = CuratorFrameworkFactory.newClient(zookeeperServer.getConnectUrl(), new ExponentialBackoffRetry(1000, 3));
		client.start();
		discovery = new RegisterServiceCuratorImpl(client);
	}
	
	@AfterClass
	public static void shutdownServer() throws IOException {
		client.close();
		zookeeperServer.shutdown();
	}
    
	private final ServiceId serviceId = ServiceId.from("service1", "1.0");
	
	private final Service service1 = Service.from(serviceId, "http://localhost:8081/enscene");
	private final Service service2 = Service.from(serviceId, "http://localhost:8082/enscene");
	
	@Before
	public void init() throws Exception {
		injector.injectMembers(this);
	}
	
	@Test 
	public void test_findAllShouldReturnNothing() {
		Collection<Service> instances = discovery.findAll(serviceId);
		assertThat(instances).isEmpty();
	}

	@Test 
	public void test_findAllShouldReturnOneInstance() {
		Collection<Service> instancesFirst = discovery.findAll(serviceId);
		assertThat(instancesFirst).isEmpty();

		discovery.register(service1);
		
		assertThat(discovery.findAll(serviceId)).containsOnly(service1);
		discovery.unregister(service1);
		
		assertThat(discovery.findAll(serviceId)).isEmpty();
		
	}
	
	@Test 
	public void test_registerTwoInstance() {

		Collection<Service> instancesFirst = discovery.findAll(serviceId);
		assertThat(instancesFirst).isEmpty();
		
		discovery.register(service1);
		discovery.register(service2);
		
		assertThat(discovery.findAll(serviceId)).containsOnly(service1, service2);

		discovery.unregister(service1);
		assertThat(discovery.findAll(serviceId)).containsOnly(service2);
		
		discovery.unregister(service2);
		
		assertThat(discovery.findAll(serviceId)).isEmpty();
	}
	
	@After
	public void close() throws IOException {
//		client.close();
//		zookeeperServer.shutdown();
	}
	
}
