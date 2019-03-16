#### mybatis
##### 1. 入门使用
##### 2. 源码分析
https://www.cnblogs.com/dongying/p/4031382.html

- org.apache.ibatis.builder.xml.XMLConfigBuilder


##### 3. 通用mapper 
##### 4. PageHelper
##### 5. mybatis generator maven 插件
MyBatis3、Ibatis2Java2、Ibatis2Java5、Ibatis3、MyBatis3Simple
```
   if(!StringUtility.stringHasValue(type)) {
            type = IntrospectedTableMyBatis3Impl.class.getName();
        } else if("Ibatis2Java2".equalsIgnoreCase(type)) {
            type = IntrospectedTableIbatis2Java2Impl.class.getName();
        } else if("Ibatis2Java5".equalsIgnoreCase(type)) {
            type = IntrospectedTableIbatis2Java5Impl.class.getName();
        } else if("Ibatis3".equalsIgnoreCase(type)) {
            type = IntrospectedTableMyBatis3Impl.class.getName();
        } else if("MyBatis3".equalsIgnoreCase(type)) {
            type = IntrospectedTableMyBatis3Impl.class.getName();
        } else if("MyBatis3Simple".equalsIgnoreCase(type)) {
            type = IntrospectedTableMyBatis3SimpleImpl.class.getName();
        }
```        