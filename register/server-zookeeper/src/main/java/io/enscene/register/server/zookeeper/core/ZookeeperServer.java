package io.enscene.register.server.zookeeper.core;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.zookeeper.server.ServerCnxnFactory;
import org.apache.zookeeper.server.ZooKeeperServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZookeeperServer {

  private static Logger logger = LoggerFactory.getLogger(ZookeeperServer.class);
  private ServerCnxnFactory standaloneServerFactory;
  private ZooKeeperServer server;

  private final InetSocketAddress address;

  public ZookeeperServer(InetSocketAddress address) {
    this.address = address;
  }

  public ZookeeperServer(String hostname, int port) {
    this.address = new InetSocketAddress(hostname, port);
  }

  public void start() throws IOException {
    try {
      int tickTime = 2000;
      int numConnections = 5000;
      String dataDirectory = System.getProperty("java.io.tmpdir");

      File dir = new File(dataDirectory, "zookeeper").getAbsoluteFile();

      server = new ZooKeeperServer(dir, dir, tickTime);
      this.standaloneServerFactory = ServerCnxnFactory.createFactory(address, numConnections);
      standaloneServerFactory.startup(server);

    } catch (InterruptedException | IOException e) {
      logger.error("Error starting zookeeper", e);
    }
  }

  public void shutdown() {
    server.shutdown();
    standaloneServerFactory.shutdown();
  }

  public String getConnectUrl() {
    InetSocketAddress address = standaloneServerFactory.getLocalAddress();
    return address.getHostString() + ":" + address.getPort();
  }

}
