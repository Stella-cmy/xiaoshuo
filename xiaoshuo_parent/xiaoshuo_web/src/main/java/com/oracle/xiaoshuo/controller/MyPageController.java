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
import com.oracle.xiaoshuo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
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
    @Autowired
    private UserService userService;
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
        //猜你喜欢
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
        //评论
        List<Conment> conments = readService.findAllConment(user.getUserId());
        System.out.println(conments);
        session.setAttribute("conments",conments);
        //预先加载回复
        Iterator<Conment> iterator = conments.iterator();
        while (iterator.hasNext()) {
            Conment cc = iterator.next();
            if (cc.getReplyId()!=null)
                iterator.remove();
        }
        System.out.println("after delete-->"+conments);
        List<Conment> all = new ArrayList<Conment>();
        for(Conment c:conments)
        {
            if(c!=null) all.add(c);
            List<Conment> m = null;
            m = readService.findAllLouXia(c.getConmentId());
            if(m!=null&&!m.isEmpty()) all.addAll(m);
        }
        session.setAttribute("conmentsS",all);
        return "mypage";
    }
    @RequestMapping("/deletePingLun")
    public String deletePingLun(Integer conmentId, HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        readService.deletePingLun(conmentId);
        List<Conment> l = readService.findAllLouXia(conmentId);
        for(Conment c:l)
        readService.deletePingLun(c.getConmentId());
        List<Conment> conments = readService.findAllConment(user.getUserId());
        if(conments.size()!=0) session.setAttribute("conments",conments);
        return "mypage";
    }
    @RequestMapping("/findReply")
    public String findReply(HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        List<Conment> me = (List<Conment>) session.getAttribute("conments");
        Iterator<Conment> iterator = me.iterator();
        while (iterator.hasNext()) {
            Conment cc = iterator.next();
            if (cc.getReplyId()!=null)
                iterator.remove();
        }
        System.out.println("after delete-->"+me);
        List<Conment> all = new ArrayList<Conment>();
        for(Conment c:me)
        {
            if(c!=null) all.add(c);
            List<Conment> m = null;
            m = readService.findAllLouXia(c.getConmentId());
            if(m!=null&&!m.isEmpty()) all.addAll(m);
        }
        session.setAttribute("conmentsS",all);
        return "mypage";
    }
}
