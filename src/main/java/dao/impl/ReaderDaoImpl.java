package dao.impl;

import entity.Reader;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import dao.ReaderDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class ReaderDaoImpl implements ReaderDao {

    @Override
    public Reader login(String username, String password) {
        Reader reader = null;
        Connection connection = null;

        try {
            connection = JDBCUtils.getConnection();
            String sql = "select * from reader where username = ? and password = ?";
            QueryRunner queryRunner = new QueryRunner();
            reader = queryRunner.query(connection,sql,new BeanHandler<>(Reader.class),username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(connection);
        }

        return reader;
    }
}
