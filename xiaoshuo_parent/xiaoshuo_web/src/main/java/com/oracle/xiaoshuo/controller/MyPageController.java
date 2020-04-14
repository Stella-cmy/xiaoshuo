package com.oracle.xiaoshuo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oracle.xiaoshuo.common.constant.PaginationConstant;
import com.oracle.xiaoshuo.pojo.Books;
import com.oracle.xiaoshuo.pojo.Conment;
import com.oracle.xiaoshuo.pojo.User;
import com.oracle.xiaoshuo.pojo.UserMiddelBook;
import com.oracle.xiaoshuo.service.BookService;
import com.oracle.xiaoshuo.service.ReadService;
import com.oracle.xiaoshuo.service.UserMiddelBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
    @Autowired
    private UserMiddelBookService userMiddelBookService;
    @Autowired
    private BookService bookService;
    @Autowired
    private ReadService readService;
    @RequestMapping("/bookrack")
    public String bookRack(Integer pageNum, HttpSession session, Model model)
    {
        if(session.getAttribute("user")==null|session.getAttribute("user")=="")
        {
            return "login";
        }
        //分页
        if(ObjectUtils.isEmpty(pageNum))
        {
            pageNum= PaginationConstant.PAGE_Num;
        }
        PageHelper.startPage(pageNum,PaginationConstant.PAGE_SIZE);
        //获取书架
        User user= (User) session.getAttribute("user");
        List<UserMiddelBook> userMiddelBookList=userMiddelBookService.findByUserId(user.getUserId());
        System.out.println(userMiddelBookList);
        List<Books> bookracks=new ArrayList<>();
        for (UserMiddelBook userMiddelBook:userMiddelBookList) {
            Books book=bookService.findById(userMiddelBook.getBookId());
            bookracks.add(book);
        }
        PageInfo<Books> pageInfo=new PageInfo<>(bookracks);
        session.setAttribute("pageInfo",pageInfo);
        Integer bookTypeId=1;
        Integer radom = (int)(Math.random()*10)+1;
        if(radom<=6)
        {
             bookTypeId=radom;
        }
       else{
             bookTypeId=1;
        }
        System.out.println("bookTypeId-------->"+bookTypeId);
        List<Books>likeTypebooks = bookService.findByBookType(bookTypeId);
        session.setAttribute("likeTypebooks",likeTypebooks);
        List<Conment> conments = readService.findAllConment(user.getUserId());
        System.out.println(conments);
        if(conments.size()!=0) session.setAttribute("conments",conments);
        return "mypage";
    }
    @RequestMapping("/deletePingLun")
    public String deletePingLun(Integer conmentId)
    {
        readService.deletePingLun(conmentId);

        return "mypage";
    }
}
