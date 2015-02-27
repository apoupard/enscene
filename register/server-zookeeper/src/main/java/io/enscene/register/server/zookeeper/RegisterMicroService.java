package io.enscene.register.server.zookeeper;

import javax.ws.rs.ApplicationPath;

import org.apache.curator.framework.CuratorFramework;

import com.google.inject.Binder;

import io.enscene.core.MicroService;
import io.enscene.register.api.ServiceRessource;
import io.enscene.register.server.zookeeper.core.CuratorFrameworkProvider;
import io.enscene.register.server.zookeeper.core.ServiceResourceImpl;
import io.enscene.register.server.zookeeper.core.ZookeeperServer;
import io.enscene.register.server.zookeeper.core.ZookeeperServerProvider;
import io.enscene.register.server.zookeeper.service.RegisterService;
import io.enscene.register.server.zookeeper.service.impl.RegisterServiceCuratorImpl;

@ApplicationPath("")
public class RegisterMicroService extends MicroService {

  @Override
  public void configure(Binder binder) {
    binder.bind(ServiceRessource.class).to(ServiceResourceImpl.class);

    binder.bind(RegisterService.class).to(RegisterServiceCuratorImpl.class);
    binder.bind(ZookeeperServer.class).toProvider(ZookeeperServerProvider.class);
    binder.bind(CuratorFramework.class).toProvider(CuratorFrameworkProvider.class);

  }

}
