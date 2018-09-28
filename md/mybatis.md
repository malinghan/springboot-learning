- 多数据源配置 多数据源动态切换

- https://blog.csdn.net/maoyeqiu/article/details/74011626

- https://www.cnblogs.com/java-zhao/category/806019.html

- https://blog.csdn.net/u011126891/article/details/79014741


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


