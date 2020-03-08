package dao.impl;

import dao.BorrowDao;
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

public class BorrowDaoImpl implements BorrowDao {
    @Override
    public void insert(Integer bookid, Integer readerid, String borrowtime, String returntime, Integer adminid, Integer state) {
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into borrow(bookid, readerid, borrowtime, returntime,state)  values (?,?,?,?,0)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,bookid);
            preparedStatement.setInt(2,readerid);
            preparedStatement.setString(3,borrowtime);
            preparedStatement.setString(4, returntime);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(preparedStatement,connection);
        }

    }

    @Override
    public List<Borrow> findAllBorrow(Integer id,Integer index) {
        Connection connection = JDBCUtils.getConnection();
        String sql = "select borrow.id,book.name,book.author,book.publish,borrow.borrowtime,borrow.returntime,reader.name,reader.tel,reader.cardid,borrow.state from borrow,book,reader where readerid = ? and book.id = borrow.bookid and reader.id = borrow.readerid limit ?,10";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Borrow> borrowList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
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
    public int CountBorrowAll(Integer readid) {
        Connection connection = JDBCUtils.getConnection();
        String sql = "select count(*) from borrow,book,reader where readerid = ? and book.id = borrow.bookid and reader.id = borrow.readerid";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0 ;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,readid);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            JDBCUtils.close(preparedStatement,connection);

        }
        return count;
    }

    @Override
    public List<Borrow> findAllBorrowByState(Integer state, Integer index) {
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
    public int CountBorrowByState(Integer state) {
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
    public void HandleReaderBorrow(Integer borrowId, Integer state, Integer adminId) {
        Connection connection = JDBCUtils.getConnection();
        String sql = "update borrow set state = ?,adminid = ? where id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,state);
            statement.setInt(2,adminId);
            statement.setInt(3,borrowId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(statement,connection);
        }
    }
}
