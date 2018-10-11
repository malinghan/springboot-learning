#### 多数据源配置 多数据源动态切换
##### Spring Boot 集成Mybatis实现多数据源
 https://blog.csdn.net/maoyeqiu/article/details/74011626
##### springboot 系列文章
https://www.cnblogs.com/java-zhao/category/806019.html
##### spring boot 2.0 多数据源切换 
https://www.jianshu.com/p/3ffcf7033779
##### 循环依赖
https://www.cnblogs.com/mowei/p/8202489.html
##### 最全的
https://www.cnblogs.com/java-zhao/p/5413845.html


databaseType -> key
DatabaseContextHolder
DynamicDatasource -> AbstractRoutingDataSource
MybatisConfig
primary dao的使用


##### mapper interface
mybatis的mapper接口是在启动的时候被框架以JdkProxy的形式封装了的，
具体对应的类是MapperFactoryBean，
这个类中有一个checkDaoConfig()方法，
是从父类继承并重写了该方法，继承结构如下
MapperFactoryBean -> SqlSessionDaoSupport -> DaoSupport

##### 多数据源动态切换
- AbstractRoutingDataSource
Spring提供的AbstractRoutingDataSource提供了运行时动态切换DataSource的功能，但是AbstractRoutingDataSource对象中包含的DataSourceBuilder构建的仅仅是Spring JDBC的DataSource，并不是我们使用的DruidDataSource,需要自行构建。

`determineCurrentLookupKey`


- datasource config
1. 构建的TargetDataSources中的DataSource仅包含driverClassName,username,password,url等基本属性，对于DruidDataSource这种复杂的DataSource，仅赋这些属性是不够的。
2. 构建AbstractingRoutingDataSource使用ImportBeanDefinitionRegistrar进行注册，不够直观。


- DynamicDataSource
- DynamicDataSourceContextHolder
- DynamicDataSourceAspect

