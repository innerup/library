package control;

import entity.Admin;
import entity.Borrow;
import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {


    private BookService bookService = new BookServiceImpl();

    /**
     * 处理管理员业务逻辑
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        HttpSession session = req.getSession();
        Admin admin = (Admin) session.getAttribute("admin");


        if(method.equals("findAllBorrow")){//查找所有需要处理的借书记录
            String pagaStr = req.getParameter("page");//获取当前页数
            Integer page = Integer.parseInt(pagaStr);

            List<Borrow> list = bookService.findAllBorrowByState(0,page);
            //跳转到管理员首页
            req.setAttribute("list",list);
            int pages = bookService.getBorrowPagesByState(0);
            req.setAttribute("datPrePage",10);//每页所容纳最多的数据条数
            req.setAttribute("currentPage",page);//当前页
            req.setAttribute("pages",pages);
            req.getRequestDispatcher("admin.jsp").forward(req,resp);
        }
        else if(method.equals("handle")){//处理借书
            String page = req.getParameter("page");//获取当前页数
            String idStr = req.getParameter("id");
            String stateStr = req.getParameter("state");
            Integer id = Integer.parseInt(idStr);
            Integer state = Integer.parseInt(stateStr);

            bookService.HandleReaderBorrow(id,state,admin.getId());//处理借书请求，state为拒绝或者同意
            resp.sendRedirect("/library/admin?method=findAllBorrow&page="+page);//回到当前页
        }else if (method.equals("getBorrowed")){//待处理还书数据查询，还书审核
            String pageStr = req.getParameter("page");
            Integer page = Integer.parseInt(pageStr);
            List<Borrow> list = bookService.handleAllReturnByState(1,page);

            int pages = bookService.getReturnPagesByState(1);
            req.setAttribute("list",list);
            req.setAttribute("datprepage",10);
            req.setAttribute("currentPage",page);//当前页
            req.setAttribute("pages",pages);
            req.getRequestDispatcher("return.jsp").forward(req,resp);
        }else if(method.equals("handleReturn")){//处理还书的业务逻辑，还书管理

            String page = req.getParameter("page");//获取当前页数

            String bidStr = req.getParameter("bid");
            String stateStr = req.getParameter("state");
            String readeridStr = req.getParameter("rid");
            String bookName = req.getParameter("bname");
            Integer bid = Integer.parseInt(bidStr);
            Integer state = Integer.parseInt(stateStr);
            Integer readerId = Integer.parseInt(readeridStr);


            bookService.HandleReaderBorrow(bid,state,admin.getId());//处理读者还书的请求,state为3

            bookService.addReturn(bookName,readerId,admin.getId());//将还书记录加入表中

            resp.sendRedirect("/library/admin?method=getBorrowed&page="+page);//转到当前页
        }
    }
}
