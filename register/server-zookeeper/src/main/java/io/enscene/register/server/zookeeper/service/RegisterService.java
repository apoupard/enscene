package io.enscene.register.server.zookeeper.service;

import io.enscene.register.api.RegisterException;
import io.enscene.register.api.Service;
import io.enscene.register.api.ServiceId;

import java.util.Collection;
import java.util.Optional;

public interface RegisterService {

	void register(Service service) throws RegisterException;
	
	void unregister(Service service) throws RegisterException;
	
	Optional<Service> findOne(ServiceId id) throws RegisterException;
	
	Collection<Service> findAll(ServiceId id) throws RegisterException;

}
