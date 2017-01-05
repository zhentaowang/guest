springboot 配置文件 .properties和.yml的写法区别

spring.redis.cluster.nodes[0]=192.168.0.1:6379
spring.redis.cluster.nodes[1]=192.168.0.2:6379
或
spring:
   redis:
      cluster:
         nodes:
            - 192.168.0.1:6379
            - 192.168.0.2:6379