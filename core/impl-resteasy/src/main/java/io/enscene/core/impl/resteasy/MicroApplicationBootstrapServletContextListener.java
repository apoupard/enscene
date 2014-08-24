package io.enscene.core.impl.resteasy;

import javax.servlet.annotation.WebListener;

import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;

@WebListener
public class MicroApplicationBootstrapServletContextListener extends GuiceResteasyBootstrapServletContextListener{}
