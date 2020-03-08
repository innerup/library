package dao;

import entity.Borrow;

import java.util.List;

public interface BorrowDao {

    public void insert(Integer bookid,
                       Integer readerid,
                       String borrowtime,
                       String returntime,
                       Integer adminid,
                       Integer state);

    public List<Borrow> findAllBorrow(Integer readid,Integer index);//根据读者的id查找读者借的书


    public int CountBorrowAll(Integer readid);//得到读者的借书记录总和


    public List<Borrow> findAllBorrowByState(Integer state,Integer index);//根据读者所借书本的状态查询读者所有的数


    public int CountBorrowByState(Integer state);//得到还书记录总和


    public void HandleReaderBorrow(Integer borrwId,Integer state,Integer adminId);//管理员处理读者借书请求





}
