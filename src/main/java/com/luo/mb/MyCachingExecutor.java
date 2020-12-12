package com.luo.mb;


import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCachingExecutor implements Executor {
    private final Executor delegate;
    private Map<Object, Object> cacheMap = new HashMap<Object, Object>();

    public MyCachingExecutor(Object o){
        this.delegate = (Executor) o;
    }

    public MyCachingExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    public int update(MappedStatement mappedStatement, Object o) throws SQLException {
        return 0;
    }

    public <E> List<E> query(MappedStatement mappedStatement, Object o, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException {
        //根据id获取缓存数据
        List<E> list = (List) this.cacheMap.get(mappedStatement.getId());
        if (list == null) {
            System.out.println("从数据库获取数据");
            list = this.delegate.query(mappedStatement, o, rowBounds, resultHandler, cacheKey, boundSql);
            System.out.println("刚从数据库拿出来的数据："+list.get(0));
            //根据id缓存数据
            this.cacheMap.put(mappedStatement.getId(), list);
        }else{
            System.out.println("从我的缓存中拿到的数据："+list.get(0));
        }
        return list;
    }

    public <E> List<E> query(MappedStatement mappedStatement, Object o, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
        BoundSql boundSql = mappedStatement.getBoundSql(o);
        //创建缓存key
        CacheKey key = this.createCacheKey(mappedStatement, o, rowBounds, boundSql);
        return this.query(mappedStatement, o, rowBounds, resultHandler, key, boundSql);
    }

    public <E> Cursor<E> queryCursor(MappedStatement mappedStatement, Object o, RowBounds rowBounds) throws SQLException {
        return null;
    }

    public List<BatchResult> flushStatements() throws SQLException {
        return null;
    }

    public void commit(boolean b) throws SQLException {

    }

    public void rollback(boolean b) throws SQLException {

    }

    public CacheKey createCacheKey(MappedStatement mappedStatement, Object o, RowBounds rowBounds, BoundSql boundSql) {
        return this.delegate.createCacheKey(mappedStatement, o, rowBounds, boundSql);
    }

    public boolean isCached(MappedStatement mappedStatement, CacheKey cacheKey) {
        return false;
    }

    public void clearLocalCache() {

    }

    public void deferLoad(MappedStatement mappedStatement, MetaObject metaObject, String s, CacheKey cacheKey, Class<?> aClass) {

    }

    public Transaction getTransaction() {
        return null;
    }

    public void close(boolean b) {

    }

    public boolean isClosed() {
        return false;
    }

    public void setExecutorWrapper(Executor executor) {

    }
}
