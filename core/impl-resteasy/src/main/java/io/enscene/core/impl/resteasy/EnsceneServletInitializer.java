package io.enscene.core.impl.resteasy;

import static com.google.common.collect.FluentIterable.from;
import io.enscene.core.MicroService;

import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.plugins.servlet.ResteasyServletInitializer;

import com.google.common.base.Joiner;
import com.google.inject.Module;

@HandlesTypes({Application.class, Path.class, Provider.class, Module.class})
public class EnsceneServletInitializer extends ResteasyServletInitializer {

  @Override
  public void onStartup(Set<Class<?>> classes, ServletContext servletContext)
      throws ServletException {
    servletContext.setInitParameter("resteasy.scan", "true");
    registerModules(classes, servletContext);
    super.onStartup(classes, servletContext);
  }

  private void registerModules(Set<Class<?>> classes, ServletContext servletContext) {
    List<String> modulesName =
        from(classes).filter(c -> isLoadableModule(c)).transform(c -> c.getName()).toList();
    String modules = Joiner.on(";").join(modulesName);
    servletContext.setInitParameter("resteasy.guice.modules", modules);
  }

  private Boolean isLoadableModule(Class<?> clazz) {
    return MicroService.class.isAssignableFrom(clazz) && clazz != MicroService.class;
  }



}
