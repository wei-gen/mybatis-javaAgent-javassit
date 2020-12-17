# mybatis目录
实现了自己用Map包了一层缓存mybatis的查询数据。  
javaAgent+javassist
##运行
使用IDEA打开项目后，点击  
Maven->mybatis->packge进行打包成jar  
设置IDEA的 EditConfigurations...->Junit->SessionTest单元测试的VM Options->-javaagent:F:\02_java\wei-springcloud\mybatis\target\mybatis-1.0-SNAPSHOT.jar  
执行TestMybatis->SessionTest单元测试
##解释运行  
使用javaAgent+javassist进行修改mybatis的Configuration.newExecutor()方法的字节码
正常来说这个方法这里会出来返回一个CachingExecutor，而CachingExecutor包含了BaseExecutor的子类。
在返回时我拿到CachingExecutor装进我的MyCachingExecutor里面，并返回我的MyCachingExecutor给SqlSeeion。
