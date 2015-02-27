package io.enscene.register.server.zookeeper.core;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

public class CuratorFrameworkProvider implements Provider<CuratorFramework> {

  private final ZookeeperServer server;

  private CuratorFramework client;

  @Inject
  public CuratorFrameworkProvider(ZookeeperServer server) {
    super();
    this.server = server;
  }

  @Override
  public CuratorFramework get() {
    if (client == null) {
      client = initCuratorFramework();
    }
    return client;
    // return initCuratorFramework();
  }

  private CuratorFramework initCuratorFramework() {
    CuratorFramework client =
        CuratorFrameworkFactory.newClient(server.getConnectUrl(), new RetryNTimes(5, 1000));
    client.start();
    return client;
  }

}
