package io.enscene.register.api;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/services")
@Consumes({"application/json"})
@Produces({"application/json"})
public interface ServiceRessource {

	@GET
	@Path("/test")
	public String test() throws RegisterException;
	
	
	@POST
	public void register(Service service) throws RegisterException;
	
	
	@DELETE
	@Path("/{name}/{version}/{url}")
	public void unregister(
			@PathParam("name") String name, 
			@PathParam("version") String version, 
			@PathParam("url") String url
	) throws RegisterException;
	
	@GET
	@Path("/{name}/{version}/instance")
	Service findOne(@PathParam("name") String name, @PathParam("version") String version) throws RegisterException;

	@GET
	@Path("/{name}/{version}/instances")
	Collection<Service> findAll(@PathParam("name") String name, @PathParam("version") String version) throws RegisterException;

}