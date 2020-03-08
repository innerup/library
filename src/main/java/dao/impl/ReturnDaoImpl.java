package dao.impl;

import dao.ReturnDao;
import entity.Book;
import entity.Borrow;
import entity.Reader;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReturnDaoImpl implements ReturnDao {

    @Override
    public List<Borrow> handleAllReturnByState(Integer state, Integer index) {
        Connection connection = JDBCUtils.getConnection();
        String sql = "select borrow.id,book.name,book.author,book.publish,borrow.borrowtime,borrow.returntime,reader.name,reader.tel,reader.cardid,borrow.state from borrow,book,reader where state = ? and book.id = borrow.bookid and reader.id = borrow.readerid limit ?,10";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Borrow> borrowList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,state);
            preparedStatement.setInt(2,index);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

                borrowList.add(new Borrow(resultSet.getInt(1),
                        new Book(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)),
                        new Reader(resultSet.getString(7),resultSet.getString(8),resultSet.getString(9)),
                        resultSet.getString(5),
                        resultSet.getString(6),resultSet.getInt(10)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            JDBCUtils.close(connection,preparedStatement,resultSet);

        }
        return borrowList;
    }

    @Override
    public int countReturnPagesByState(Integer state) {
        Connection connection = JDBCUtils.getConnection();
        String sql = "select count(*) from borrow,book,reader where state = ? and book.id = borrow.bookid and reader.id = borrow.readerid";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int index = 0;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,state);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                index=resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(connection,preparedStatement,resultSet);
        }
        return index;
    }

    @Override
    public void addReturn(String bookname, Integer readid, String returntime, int adminid) {
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into returnbook(bookname,readerid,returntime,adminid) values(?,?,?,?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,bookname);
            preparedStatement.setInt(2,readid);
            preparedStatement.setString(3,returntime);
            preparedStatement.setInt(4,adminid);
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(preparedStatement,connection);
        }

    }
}
