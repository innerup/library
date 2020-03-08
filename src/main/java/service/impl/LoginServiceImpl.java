package service.impl;

import dao.*;
import dao.impl.AdminDaoImpl;
import dao.impl.ReaderDaoImpl;
import service.LoginService;

public class LoginServiceImpl implements LoginService {

    private ReaderDao readerDao = new ReaderDaoImpl();
    private AdminDao adminDao = new AdminDaoImpl();

    @Override
    public Object login(String username, String password, String type) {
        Object object = null;
        if(type.equals("reader")){
            object = readerDao.login(username,password);

            return object;
        }else if (type.equals("admin")){
            object = adminDao.login(username,password);
            return object;
        }
        else {
            return object;
        }
    }
}
