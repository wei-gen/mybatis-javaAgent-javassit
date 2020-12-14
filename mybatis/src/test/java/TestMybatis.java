import com.luo.mb.MyCachingExecutor;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

//-javaagent:F:\02_java\wei-springcloud\mybatis\target\mybatis-1.0-SNAPSHOT.jar=testargs

public class TestMybatis {

    private Configuration configuration;
    private Connection connection;
    private JdbcTransaction jdbcTransaction;
    SqlSessionFactory factory;
    @Before
    public void bef() throws SQLException {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        factory =
                factoryBuilder.build(TestMybatis.class.getResourceAsStream("/mybatis-config.xml"));
        configuration = factory.getConfiguration();
        connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/db2019?useUnicode=true&characterEncoding=utf-8&useSSL=false",
                        "root","root");
        jdbcTransaction = new JdbcTransaction(connection);
    }

    @Test
    public void simpleTest() throws SQLException {
        SimpleExecutor executor = new SimpleExecutor(configuration,jdbcTransaction);
        MappedStatement ms = configuration.getMappedStatement("com.luo.mb.dao.PaymentDao.getPaymentById");
        List<Object> list = executor.doQuery(ms,9, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER,ms.getBoundSql(9));
        System.out.println(list.get(0));
    }

    @Test
    public void CacheTest() throws SQLException {
        SimpleExecutor simpleExecutor = new SimpleExecutor(configuration,jdbcTransaction);
        CachingExecutor executor = new CachingExecutor(simpleExecutor);
        MappedStatement ms = configuration.getMappedStatement("com.luo.mb.dao.PaymentDao.getPaymentById");
        List<Object> list = executor.query(ms,9, RowBounds.DEFAULT,
                Executor.NO_RESULT_HANDLER);
        System.out.println(list.get(0));
    }

    @Test
    public void SessionTest() throws SQLException {
        SqlSession sqlSession = factory.openSession(ExecutorType.SIMPLE,true);
        List<Object> list = sqlSession.selectList("com.luo.mb.dao.PaymentDao.getPaymentById",9);
        sqlSession.selectList("com.luo.mb.dao.PaymentDao.getPaymentById",9);
    }

    @Test
    public void myCacheTest() throws SQLException {
        SimpleExecutor simpleExecutor = new SimpleExecutor(configuration,jdbcTransaction);
        MyCachingExecutor myCachingExecutor = new MyCachingExecutor(simpleExecutor);
        CachingExecutor executor = new CachingExecutor(myCachingExecutor);
        MappedStatement ms = configuration.getMappedStatement("com.luo.mb.dao.PaymentDao.getPaymentById");
        List<Object> list = executor.query(ms,9, RowBounds.DEFAULT,
                Executor.NO_RESULT_HANDLER);
        executor.query(ms,9, RowBounds.DEFAULT,
                Executor.NO_RESULT_HANDLER);
    }
}
