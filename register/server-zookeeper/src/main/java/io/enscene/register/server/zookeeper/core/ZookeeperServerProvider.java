package io.enscene.register.server.zookeeper.core;

import java.io.IOException;

import javax.inject.Provider;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ZookeeperServerProvider implements Provider<ZookeeperServer> {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private ZookeeperServer server = null; 
	
	@Override
	public ZookeeperServer get() {
		if(server == null) {
			server = initZookeeperServer();
		}
		return server;
	}

	private ZookeeperServer initZookeeperServer() {
		try {
			ZookeeperServer server = new ZookeeperServer("localhost", 8095); 
			server.start();
			return server;
		} catch (IOException e) {
			log.error("Error starting zookeeper server.", e);
			return null;
		}
		
	}
	
}
