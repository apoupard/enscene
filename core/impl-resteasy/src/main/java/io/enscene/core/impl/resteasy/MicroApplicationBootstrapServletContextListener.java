package io.enscene.core.impl.resteasy;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import com.google.common.collect.ImmutableList;
import com.google.inject.Module;
import com.google.inject.servlet.ServletModule;

public abstract class MicroApplicationBootstrapServletContextListener extends GuiceResteasyBootstrapServletContextListener {

	@Override
	protected List<Module> getModules(ServletContext context) {
		Collection<Module> modules = bindModules(context);
		return ImmutableList.of(new ServletModule() {
			@Override
			protected void configureServlets() {
				modules.forEach(module -> install(module));
				bind(HttpServletDispatcher.class).in(Singleton.class);
				serve(getApplicationPath()+"/*").with(HttpServletDispatcher.class);
			}
		});
	}
	
	private String getApplicationPath(){
		if(getApplication().isPresent()){
			ApplicationPath applicationPath = getApplication().get().getDeclaredAnnotation(ApplicationPath.class);
			if(applicationPath != null){
				return applicationPath.value();
			}
		}
		return "";
	}
	
	protected Optional<Class<? extends Application>> getApplication(){
		return Optional.empty();
	}

	protected abstract Collection<Module> bindModules(ServletContext context);

}