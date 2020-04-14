package com.oracle.xiaoshuo.controller;

import com.oracle.xiaoshuo.common.exception.ReachException;
import com.oracle.xiaoshuo.pojo.Books;
import com.oracle.xiaoshuo.pojo.Section;
import com.oracle.xiaoshuo.pojo.User;
import com.oracle.xiaoshuo.pojo.UserMiddelBook;
import com.oracle.xiaoshuo.service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/reads")
public class ReadController {
    private static int bookIds=0;
    @Autowired
    private ReadService readService;
    @Autowired
    private ShoppingService shoppingService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMiddelBookService userMiddelBookService;
    @Autowired
    private BookService bookService;
    @RequestMapping("/read")
    public String read(@Param("bookId") Integer bookId, HttpSession session)
    {
        if(ReadController.bookIds==0) ReadController.bookIds=bookId;
        session.setAttribute("lastBookId",ReadController.bookIds);
        Books book= shoppingService.findById(bookId);
        session.setAttribute("thisBook",book);
        List<Section> sections= readService.findByBookId(bookId);
        session.setAttribute("minSection",sections.get(0));
        session.setAttribute("sections",sections);
        //获取书架
        User user= (User) session.getAttribute("user");
        UserMiddelBook bookMark = userMiddelBookService.findByBookIdUserId(bookId,user.getUserId());
        if(bookMark!=null) session.setAttribute("bookMark",bookMark);
        return "read";
    }
    @RequestMapping("/finByBookName")
    public String finByBookName(@Param("bookName")String bookName, HttpSession session) {
        try {
            System.out.println(bookName);
            Books book = shoppingService.findByBookName(bookName);
            System.out.println("book--->"+book);
                session.setAttribute("thisBook", book);
                return "read";
        } catch (ReachException ex) {
            session.setAttribute("errorMsg", ex.getMessage());
            return "redirect:/shop/books?bookTypeId="+1;
        }
    }
    @RequestMapping("/section")
    public String section(@Param("bookId") Integer bookId,HttpSession session)
    {
        ReadController.bookIds=bookId;
        session.setAttribute("lastBookId",ReadController.bookIds);//引入了上一本书标记，因为在书之间切换时，章节和内容都要有所改变
        List<Section> sections= readService.findByBookId(bookId);
        session.setAttribute("minSection",sections.get(0));
        session.setAttribute("sections",sections);
        return "read";
    }
    @RequestMapping(value = "/content")
    public String content(@Param("sectionId") Integer sectionId,@Param("bookId") Integer bookId, HttpSession session){
        ReadController.bookIds=bookId;
        System.out.println("content----bookId>"+ bookId);
        System.out.println("content----sectionId>"+ sectionId);
        System.out.println("last----lastBookId>"+ ReadController.bookIds);
        session.setAttribute("lastBookId",ReadController.bookIds);
        Section section1=readService.findBySectionId(sectionId,bookId);
        session.setAttribute("section",section1);
        int free=0;
        Section minSec = (Section)session.getAttribute("minSection");
        System.out.println("minSection---"+minSec.getSectionId());
        if(session.getAttribute("user")!=null)//登录
        {
            User user= (User) session.getAttribute("user");
            Books book =  bookService.findById(bookId);
            if(book.getBookAutor().equals(user.getUsername())) free=1;//作者可阅读全文
            UserMiddelBook userMiddelBook= userMiddelBookService.findByBookIdUserId(bookId,user.getUserId());
            if(userMiddelBook!=null)//书架上有这本书
            {
                if(userMiddelBook.getMaxSectionId()>=sectionId) free=1;//查看买到了哪一章
            }
            else if(sectionId-minSec.getSectionId() < 20) free=1;//若书架上没有，只能看前20章
        }
        else
        {if(sectionId-minSec.getSectionId() < 20) free=1;}//未登录，只能看前20章
        session.setAttribute("free",free);
        return "read";
    }
    @RequestMapping("/buyBook")
    public String buyBook(Integer sectionId ,Integer bookId,HttpSession session,Model model)
    {
        User user= (User) session.getAttribute("user");
        UserMiddelBook userMiddelBook= userMiddelBookService.findByBookIdUserId(bookId,user.getUserId());
        System.out.println(userMiddelBook);
        if(userMiddelBook!=null) {//书架上有此书 其实就代表买到了maxSection
            Integer x = userMiddelBook.getMaxSectionId();
            if (x+1 == sectionId) {//如果要买后一章
                if (user.getBalance() != 0) {//余额充足
                    userService.updateBalance(user);
                    Integer y = userMiddelBook.getSectionId();
                    userMiddelBookService.updateBook(bookId,user.getUserId(),y,sectionId);
                    model.addAttribute("errorMsg2", "Purchase successful!");
                    return "read";
                } else {//余额不足
                    model.addAttribute("errorMsg2", "The current account balance is insufficient, please sign in to get coins!");
                    return "read";
                }
            }
            else{//不允许买后面的章节
                model.addAttribute("errorMsg2", "Please buy the previous chapter!");
                return "read";
            }
        }
        else//书架上没有此书
        {
            Section minSec = (Section)session.getAttribute("minSection");
            System.out.println(sectionId-minSec.getSectionId());
            if(sectionId-minSec.getSectionId()==20) {//如果是第21章
                if (user.getBalance() != 0) {//余额充足
                    userService.updateBalance(user);
                    userMiddelBookService.addBook(bookId, user.getUserId(), sectionId, sectionId);
                    model.addAttribute("errorMsg2", "Purchase successful! Click again to read!");
                    return "read";
                } else {//余额不足
                    model.addAttribute("errorMsg2", "The current account balance is insufficient, please sign in to get coins!");
                    return "read";
                }
            }
            else{//大于21章
                model.addAttribute("errorMsg2", "Please buy the previous chapter!");
                return "read";
            }
        }
    }
    @RequestMapping("/addBook")
    public String addBook(@Param("bookId") Integer bookId,@Param("sectionId") Integer sectionId, HttpSession session,Model model)
    {
        System.out.println("Book "+bookId+" Section "+sectionId+" add");
        User user= (User) session.getAttribute("user");
        UserMiddelBook userMiddelBook= userMiddelBookService.findByBookIdUserId(bookId,user.getUserId());
        Section minSec = (Section)session.getAttribute("minSection");
        if(userMiddelBook==null)//书架没有此书
        {
            int max = (sectionId-minSec.getSectionId()>=20)?20:sectionId;//最多是20章
            userMiddelBookService.addBook(bookId,user.getUserId(),sectionId,max);//加入书架
            System.out.println("Book "+bookId+" Section "+sectionId+" added");
            model.addAttribute("errorMsg3","Added successfully!");
            return "read";
        }
        else {//若书架存在此书，更新在看章节
            Integer x = userMiddelBook.getMaxSectionId();
            userMiddelBookService.updateBook(bookId,user.getUserId(),sectionId,x);
            System.out.println("Book "+bookId+" Section "+sectionId+" added");
            model.addAttribute("errorMsg3","Added successfully!");
            return "read";
        }
    }
    @RequestMapping("/PingLin")
    public String pinglun(@Param("pinglun") String pinglun,@Param("bookId") Integer bookId, HttpSession session)
    {
        if(pinglun==null||pinglun=="")  return "read";
        User user= (User) session.getAttribute("user");
        Integer userId = user.getUserId();
        System.out.println("user "+userId+" book "+bookId+" add comment "+pinglun);
        readService.savePingLun(pinglun,userId,bookId);
        return "read";
    }
}
