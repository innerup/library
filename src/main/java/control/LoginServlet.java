package control;

import entity.Admin;
import entity.Book;
import entity.Borrow;
import entity.Reader;
import service.BookService;
import service.LoginService;
import service.impl.BookServiceImpl;
import service.impl.LoginServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@javax.servlet.annotation.WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private LoginService loginService = new LoginServiceImpl();
    private BookService bookService = new BookServiceImpl();

    /**
     * 处理登陆业务逻辑
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String type = req.getParameter("type");
        Object object = loginService.login(username,password,type);//判断用户
        if(object == null){
            resp.sendRedirect("login.jsp");
        }else{
            HttpSession session = req.getSession();

            if(type.equals("reader")) {
                session.setAttribute("reader", (Reader)object);
                resp.sendRedirect("/library/book?method=findAll&page=1");//重定向给BookServlet
            }else{
                session.setAttribute("admin",(Admin)object);
                resp.sendRedirect("/library/admin?method=findAllBorrow&page=1");//重定向给AdminServlet


            }
        }
    }
}
