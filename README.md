# mybatis目录
实现了自己用Map包了一层缓存mybatis的查询数据。  
javaAgent+javassist  
使用IDEA打开项目后，点击  
Maven->mybatis->packge进行打包成jar  
执行TestMybatis->SessionTest单元测试  
使用javaAgent+javassist进行修改mybatis的Configuration.newExecutor()方法的字节码
正常来说这个方法这里会出来返回一个CachingExecutor，而CachingExecutor包含了BaseExecutor的子类。
在返回时我拿到CachingExecutor装进我的MyCachingExecutor里面，并返回我的MyCachingExecutor给SqlSeeion。
