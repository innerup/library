package control;

import entity.Book;
import entity.Borrow;
import entity.Reader;
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

@WebServlet("/book")
public class BookServlet extends HttpServlet {

    private BookService bookService = new BookServiceImpl();

    /**
     * 处理借书，查询现有书籍，查询该读者借的所有书的业务逻辑
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getParameter("method");

        HttpSession httpSession = req.getSession();
        Reader reader = (Reader) httpSession.getAttribute("reader");
        if(method.equals("findAll")){

            //查找所有的书
            String pagaStr = req.getParameter("page");//获取当前页数
            Integer page = Integer.parseInt(pagaStr);
            List<Book> list = bookService.findAll(page);

            int pages = bookService.getPages();
            req.setAttribute("datPrePage",10);//每页所容纳最多的数据条数
            req.setAttribute("currentPage",page);//当前页
            req.setAttribute("pages",pages);

            req.setAttribute("list",list);//把list交给jsp去遍历
            req.getRequestDispatcher("index.jsp").forward(req,resp);
        }else if(method.equals("addBorrow")) {

            //借书
            String bookidStr = req.getParameter("bookid");
            Integer bookid = Integer.parseInt(bookidStr);
            bookService.addBorrow(bookid,reader.getId());
            resp.sendRedirect("/library/book?method=findAllBorrow&page=1");//定位到该读者所有的借书情况

        }else if(method.equals("findAllBorrow")){

            //用户的总借书记录
            String pagaStr = req.getParameter("page");//获取当前页数
            Integer page = Integer.parseInt(pagaStr);

            List<Borrow> borrowList = bookService.findAllBorrow(reader.getId(),page);//展示当前用户的所有借书记录
            req.setAttribute("list",borrowList);
            int pages = bookService.getBorrowPages(reader.getId());
            req.setAttribute("datPrePage",10);//每页所容纳最多的数据条数
            req.setAttribute("currentPage",page);//当前页
            req.setAttribute("pages",pages);

            req.getRequestDispatcher("borrow.jsp").forward(req,resp);
        }
    }
}
