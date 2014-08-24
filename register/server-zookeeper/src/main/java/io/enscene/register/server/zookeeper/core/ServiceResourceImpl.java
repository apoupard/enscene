package io.enscene.register.server.zookeeper.core;

import io.enscene.register.api.RegisterException;
import io.enscene.register.api.Service;
import io.enscene.register.api.ServiceId;
import io.enscene.register.api.ServiceRessource;
import io.enscene.register.server.zookeeper.service.RegisterService;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class ServiceResourceImpl implements ServiceRessource {

	private final RegisterService registerService;
	
	@Inject
	public ServiceResourceImpl(RegisterService registerService) {
		this.registerService = registerService;
	}
	
	@Override
	public void register(Service service) throws RegisterException {
		registerService.register(service);
	}

	@Override
	public void unregister(String name, String version, String url) throws RegisterException {
		ServiceId id = ServiceId.from(name, version);
		Service service = Service.from(id, url);
		registerService.unregister(service);
	}
	
	@Override
	public Service findOne(String name, String version) throws RegisterException {
		ServiceId id = ServiceId.from(name, version);
		return registerService.findOne(id).orElse(null);
	}

	@Override
	public Collection<Service> findAll(String name, String version) throws RegisterException {
		ServiceId id = ServiceId.from(name, version);
		return registerService.findAll(id);
	}

	@Override
	@Produces(MediaType.TEXT_PLAIN)
	public String test() throws RegisterException {
		return "test";
	}

}