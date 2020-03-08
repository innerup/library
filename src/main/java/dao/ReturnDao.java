package dao;

import entity.Borrow;

import java.util.List;

public interface ReturnDao {

    public List<Borrow> handleAllReturnByState(Integer readid, Integer index);//根据读者所借书本的状态查询读者所有的数


    public int countReturnPagesByState(Integer state);//查找还书的总记录数目，用来分页


    public void addReturn(String bookname,Integer readid,String returntime,int adminid);


}
