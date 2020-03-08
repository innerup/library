package service;

import entity.Book;
import entity.Borrow;

import java.util.List;

public interface BookService {

    int PAGING = 10;//图书界面分页
    int BOOROWPAGING = 10;//借书界面分页
    int ADMINPAGING = 10;//管理员管理分页

    public List<Book> findAll(Integer page);//查询所有图书
    //根据分页的页数

    public int getPages();//得到图书馆现有图书的总页数，用于分页操作
    //

    public void addBorrow(Integer bookid,Integer readerid);//借书
    //根据书的ID,以及读者的ID


    public List<Borrow> findAllBorrow(Integer id,Integer page);//根据读者的id查找读者借的书
    //根据读者ID,以及分页的page


    public int getBorrowPages(Integer readid);//得到借书页的总页数，用于分页操作
    //根据读者ID


    public List<Borrow> findAllBorrowByState(Integer state,Integer page);//查找借的书的总数
    //根据一本书状态以及分页的page


    public int getBorrowPagesByState(Integer state);//得到总页数，用于分页操作
    //一个书的状态

    public void HandleReaderBorrow(Integer borrowid,Integer state,Integer adminid);//管理员审核借书
    //参数为该记录的id,状态,adminid

    public List<Borrow> handleAllReturnByState(Integer state,Integer page);//查找要还书的总数
    //根据一本书状态以及分页的page


    public int getReturnPagesByState(Integer state);//得到总页数，用于分页操作
    //一个书的状态

    public void addReturn(String booknam,Integer readid,Integer adminid);
}
