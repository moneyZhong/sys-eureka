各主流注册中心对比

![Image text](https://raw.githubusercontent.com/moneyZhong/myFiles/master/%E5%90%84%E4%B8%BB%E6%B5%81%E6%B3%A8%E5%86%8C%E4%B8%AD%E5%BF%83%E5%AF%B9%E6%AF%94.png)

CAP原则又称CAP定理，指的是在一个分布式系统中， Consistency（一致性）、 Availability（可用性）、Partition tolerance（分区容错性），三者不可得兼。

注册中心主要分为两块： Dubbo -> zookeeper  ,   spring cloud -> euraka
     
   1.以ZK 作为服务注册和发现的集群模式， 这种多台机器情况下，会有2个角色 Leader/Follower  。 只有Leader是可以写，也就是服务注册，它会将注册信息同步给Follower.
    读的时候，两个角色都可以。
    
   2.ZK 遵循CAP 中 CP . 它保证了一致性和分区容错性
       ZK 会有一个Leader节点接收数据， 然后同步给其他节点。 一旦Leader节点挂掉，会重新选举Leader, 这个过程为了保证C, 就牺牲了A,不可用一段时间。
       一旦Leader选举好，那么就可以继续写数据， 保证了一致性。以ZK作为服务注册发现的时效性一般秒级可以感知到
       不适合大规模的服务实例，因为服务上下线的时候，要瞬间推送数据通知到所以其他服务实例，一旦服务规模过大，上千个的时候，会导致网络带宽被大量占用

   以 Spring Cloud 作为服务框架的一般会选择 Eureka 为注册中心
   以 Eureka 作为服务注册和发现的集群模式
   这种情况下集群里面的机器都是对等的，每个服务都可以进行服务注册与服务发现
   并且集群中任意一台机器收到写请求后，会自动同步给其余机器
   Eureka ，遵循了CAP 中的 AP
   它保证了可用性和分区容错性
   Eureka 每个实例的平级的，可能数据还没同步过去，自己就挂了
   此时还是可以从别的机器上获取注册表，但是看到的就不是最新的数据，保证了可用性，但是 Eureka 遵循的是最终一致性
   以 Eureka 作为服务注册发现的时效性默认配置非常差，服务感知可能几十秒甚至几分钟级别
   因为从注册中心的缓存刷新，服务同步注册中心的数据，服务故障检查心跳，都有一个时间间隔
   针对这种情况，是肯定不能用默认配置在生产上的
   eureka，必须优化参数
   
   
## 每隔  3秒 只读 从 只写 缓存 更新缓存
eureka.server.responseCacheUpdateIntervalMs = 3000 
## 客户端获取注册表信息缓存
eureka.client.registryFetchIntervalSeconds = 3000
## 心跳间隔，5秒
eureka.client.leaseRenewalIntervalInSeconds = 5
## 主动失效检测间隔,配置成5秒
eureka.server.evictionIntervalTimerInMs = 5 
## 没有心跳的淘汰时间，10秒
eureka.instance.leaseExpirationDurationInSeconds = 10
 
服务发现的时效性变成秒级，几秒钟可以感知服务的上线和下线
eureka 也很难支持大规模的服务实例
因为每个实例都要接收写请求，需要同步，实例太多扛不住，很难达到几千服务实例










