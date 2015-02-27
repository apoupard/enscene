package io.enscene.register.server.zookeeper.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ZookeeperServerTest {

  private static ZookeeperServer zookeeperServer;

  private static CuratorFramework client;

  @BeforeClass
  public static void startServer() throws IOException {
    zookeeperServer = new ZookeeperServer("localhost", 8090);
    zookeeperServer.start();
    client =
        CuratorFrameworkFactory.newClient(zookeeperServer.getConnectUrl(),
            new ExponentialBackoffRetry(1000, 3));
    client.start();
  }

  @AfterClass
  public static void shutdownServer() throws IOException {
    client.close();
    zookeeperServer.shutdown();
  }

  @Test
  public void test_serverShouldStart() throws Exception {
    client.create().forPath("/my", "test1".getBytes());
    byte[] data = client.getData().forPath("/my");
    assertThat(new String(data)).isEqualTo("test1");
  }

  @Before
  @After
  public void cleanup() throws Exception {
    if (client.checkExists().forPath("/my") != null) {
      client.delete().deletingChildrenIfNeeded().forPath("/my");
    }
  }

}
