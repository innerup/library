package dao.impl;

import dao.AdminDao;
import entity.Admin;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminDaoImpl implements AdminDao {

    public Admin login(String username,String password){
        Admin admin = null;
        Connection connection = null;

        try {
            connection = JDBCUtils.getConnection();
            String sql = "select * from bookadmin where username = ? and password = ?";
            QueryRunner queryRunner = new QueryRunner();
            admin = queryRunner.query(connection,sql,new BeanHandler<>(Admin.class),username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(connection);
        }

        return admin;
    }


}
