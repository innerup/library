package service.impl;

import dao.impl.ReturnDaoImpl;
import dao.BookDao;
import dao.BorrowDao;
import dao.ReturnDao;
import dao.impl.BookDaoImpl;
import dao.impl.BorrowDaoImpl;
import entity.Book;
import entity.Borrow;
import service.BookService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();
    private BorrowDao borrowDao = new BorrowDaoImpl();
    private ReturnDao returnDao = new ReturnDaoImpl();



    @Override
    public List<Book> findAll(Integer page) {

        int index = (page-1)*PAGING;
        return bookDao.findAll(index);

    }

    public int getPages(){
        int count = bookDao.CountAll();
        int page = 0;
        if(count % PAGING == 0){
            page = count/PAGING;
        }else{
            page = count/PAGING + 1;
        }
        return page;
    }

    @Override
    public void addBorrow(Integer bookid, Integer readerid) {
        Date date = new Date();//借书时间，当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//格式化
        String borrowTime = simpleDateFormat.format(date);
        Calendar calendar = Calendar.getInstance();
        int dates = calendar.get(Calendar.DAY_OF_YEAR) + 14;//还书时间，借书时间+14天
        calendar.set(Calendar.DAY_OF_YEAR, dates);
        Date date2 = calendar.getTime();
        String returnTime = simpleDateFormat.format(date2);
        borrowDao.insert(bookid,readerid,borrowTime,returnTime,null,0);
    }

    @Override
    public List<Borrow> findAllBorrow(Integer id,Integer page) {
        int index = (page-1)*BOOROWPAGING;
        return borrowDao.findAllBorrow(id,index);
    }

    @Override
    public int getBorrowPages(Integer readid) {
        int count = borrowDao.CountBorrowAll(readid);
        int page = 0;
        if(count % BOOROWPAGING == 0){
            page = count/BOOROWPAGING;
        }else{
            page = count/BOOROWPAGING + 1;
        }
        return page;
    }

    @Override
    public List<Borrow> findAllBorrowByState(Integer state, Integer page) {
        Integer index = (page-1)*ADMINPAGING;
        return borrowDao.findAllBorrowByState(state,index);
    }

    @Override
    public int getBorrowPagesByState(Integer state) {
        int count = borrowDao.CountBorrowByState(state);
        int page = 0;
        if(count % BOOROWPAGING == 0){
            page = count/BOOROWPAGING;
        }else{
            page = count/BOOROWPAGING + 1;
        }
        return page;
    }

    @Override
    public void HandleReaderBorrow(Integer borrowid, Integer state, Integer admin) {
        borrowDao.HandleReaderBorrow(borrowid,state,admin);
    }

    @Override
    public List<Borrow> handleAllReturnByState(Integer state, Integer page) {
        Integer index = (page-1)*ADMINPAGING;
        return returnDao.handleAllReturnByState(state,page);
    }

    @Override
    public int getReturnPagesByState(Integer state) {
        int count = returnDao.countReturnPagesByState(state);
        int page = 0;
        if(count % BOOROWPAGING == 0){
            page = count/BOOROWPAGING;
        }else{
            page = count/BOOROWPAGING + 1;
        }
        return page;
    }

    @Override
    public void addReturn(String bookname, Integer readid, Integer adminid) {
        Date date = new Date();//借书时间，当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//格式化
        String returnTime = simpleDateFormat.format(date);
        returnDao.addReturn(bookname,readid,returnTime,adminid);

    }

}
