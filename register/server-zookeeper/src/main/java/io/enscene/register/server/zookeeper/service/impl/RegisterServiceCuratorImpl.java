package io.enscene.register.server.zookeeper.service.impl;

import static java.net.URLDecoder.decode;
import static java.net.URLEncoder.encode;
import io.enscene.register.api.Service;
import io.enscene.register.api.ServiceId;
import io.enscene.register.server.zookeeper.core.RegisterException;
import io.enscene.register.server.zookeeper.service.RegisterService;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.inject.Inject;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NoNodeException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class RegisterServiceCuratorImpl implements RegisterService {

  private static final String ENCODING = "UTF-8";
  private static final String URL_SEPARATOR = "/";

  private final CuratorFramework client;

  @Inject
  public RegisterServiceCuratorImpl(CuratorFramework client) throws Exception {
    this.client = client;
  }

  @Override
  public void register(Service service) {
    try {
      String path = buildPath(service);
      client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
    } catch (Exception e) {
      throw new RegisterException(e);
    }
  }

  @Override
  public void unregister(Service service) {
    try {
      String path = buildPath(service);
      client.delete().forPath(path);
    } catch (Exception e) {
      throw new RegisterException(e);
    }
  }

  @Override
  public Optional<Service> findOne(ServiceId service) throws RegisterException {
    try {
      String path = buildPath(service);
      List<Service> data = getRegisteredData(service, path);
      Integer index = new Random().nextInt(data.size());
      return Optional.of(data.get(index));
    } catch (Exception e) {
      throw new RegisterException(e);
    }
  }

  @Override
  public Collection<Service> findAll(ServiceId service) throws RegisterException {
    try {
      String path = buildPath(service);
      return getRegisteredData(service, path);
    } catch (Exception e) {
      throw new RegisterException(e);
    }
  }

  private List<Service> getRegisteredData(ServiceId id, String path) throws Exception {
    try {
      List<String> urls = client.getChildren().forPath(path);
      List<Service> instances = Lists.newArrayList();
      for (String url : urls) {
        instances.add(Service.from(id, decode(url, ENCODING)));
      }
      return instances;
    } catch (NoNodeException e) {
      return ImmutableList.of();
    }
  }

  private String buildPath(Service service) throws UnsupportedEncodingException {
    return new StringBuilder().append(buildPath(service.getId())).append(URL_SEPARATOR)
        .append(encode(service.getPath(), ENCODING)).toString();
  }

  private String buildPath(ServiceId service) throws UnsupportedEncodingException {

    return new StringBuilder().append(URL_SEPARATOR).append(encode(service.getName(), ENCODING))
        .append(URL_SEPARATOR).append(encode(service.getVersion(), ENCODING)).toString();
  }

}
