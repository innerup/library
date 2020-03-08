package dao.impl;

import dao.BookDao;
import entity.Book;
import entity.BookCase;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    @Override
    public List<Book> findAll(int i) {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select * from book,bookcase where book.bookcaseid=bookcase.id limit ?,10";
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;
        List<Book> list = new ArrayList<>();

        try{

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,i);
            resultset =  preparedStatement.executeQuery();

            while (resultset.next()){
                list.add(new Book(resultset.getInt(1),resultset.getString(2),resultset.getString(3),
                        resultset.getString(4),resultset.getInt(5),resultset.getDouble(6),
                        new BookCase(resultset.getInt(9),resultset.getString(10))));

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.close(conn,preparedStatement,resultset);
        }
        return list;
    }

    @Override
    public int CountAll() {
        int i = 0;
        Connection connection = JDBCUtils.getConnection();
        ResultSet resultset = null;
        String sql = "select count(*) from book,bookcase where book.bookcaseid=bookcase.id";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(sql);
            resultset =  preparedStatement.executeQuery();

            while (resultset.next()){
                i = resultset.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.close(connection, preparedStatement, resultset);
        }
        return i;
    }
}
