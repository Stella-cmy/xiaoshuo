package com.oracle.xiaoshuo.controller;

import com.oracle.xiaoshuo.common.exception.UserException;
import com.oracle.xiaoshuo.pojo.*;
import com.oracle.xiaoshuo.service.BookService;
import com.oracle.xiaoshuo.service.ReadService;
import com.oracle.xiaoshuo.service.UserMiddelBookService;
import com.oracle.xiaoshuo.service.UserService;
import org.apache.ibatis.annotations.Param;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserMiddelBookService userMiddelBookService;
    @Autowired
    private ReadService readService;
    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @param session
     * @param model
     */
    @RequestMapping("/register")
    public String register(String userName, String password, HttpSession session,RedirectAttributes attr, Model model) {
        if(userName==null||password==null) return "redirect:registerpage";
        System.out.println(userName);
            System.out.println(password);
            //不允许同名
            List<User> userL = userService.findAll();
            for(User u:userL)
            {
                if(u.getUsername().equals(userName))
                {
                    attr.addFlashAttribute("errorMsg", "This username is exist!");
                    return "redirect:registerpage";
                }
            }
            List<Books> booksL = bookService.findAllBooks();
            for(Books b:booksL)
            {
                if(b.getBookAutor().equals(userName))
                {
                    attr.addFlashAttribute("errorMsg", "This username is exist!");
                    return "redirect:registerpage";
                }
            }
            userService.newUser(userName,password);
            model.addAttribute("errorMsg2","Succeed! Please login!");
            return "login";
    }
    @RequestMapping("/registerpage")
    public String registerpage(RedirectAttributes attr) {
        return "register";
    }
    @RequestMapping("/login")
    public String login(String userName, String password, HttpSession session, RedirectAttributes attr, Model model) {
        try {
            if(userName==null&&password==null) return "login";
            User user = userService.login(userName, password);
            System.out.println(userName);
            System.out.println(password);
            //更新myEarnings与balance 找写的书->所有人买到多少章，求和
            //收到的评论
            if(user.getIsWriter()==1)
            {
                Double myEarnings=0.0;
                List<Conment> comments = new ArrayList<Conment>();
                List<Conment> comment = new ArrayList<Conment>();
                List<Books> booksL = bookService.findAllBooks();
                for(Books b:booksL)
                {
                    if(b.getBookAutor().equals(userName))
                    {
                        System.out.println(b.getBookId());
                        comment = readService.findAllConmentByBook(b.getBookId());
                        System.out.println("comment-->"+comment);
                        if(comment != null&&!comment.isEmpty())
                        {comments.addAll(comment);}
                        List<UserMiddelBook> bookInMiddle = userMiddelBookService.findByBookId(b.getBookId());
                        if(bookInMiddle!=null)
                            for(UserMiddelBook k:bookInMiddle)
                            {
                                List<Section> sections= readService.findByBookId(k.getBookId());
                                Integer min = sections.get(0).getSectionId();
                                Integer x = k.getMaxSectionId();
                                if(x-min>=20) myEarnings = myEarnings+x-min-20+1;
                            }
                    }
                }
                double newEarnings = myEarnings-user.getMyEarnings();
                if(newEarnings!=0)
                {
                    Integer newBalance = (int)newEarnings+user.getBalance();
                    userService.flashBalance(user,newBalance);
                }
                userService.updateMyEarnings(user,myEarnings);
                session.setAttribute("conmentsA",comments);
                System.out.println(comments);
            }
            user = userService.login(userName, password);
            session.setAttribute("user", user);
            return "redirect:/main/findAllBook";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            attr.addFlashAttribute("errorMsg", "The username or password error!");
            return "redirect:tologin";
        }

    }
    @RequestMapping("/tologin")
    public String tologin(RedirectAttributes attr)
    {
        return "login";
    }
    @RequestMapping("/addBalance")
    public void addBalance(HttpSession session)
    {
        User user= (User) session.getAttribute("user");
        String lastDate = user.getLastDate();
        /*签到有两种情况：
        1.从未签到过的话 一开始显示可以签到，签到成功后不可签到
        2.签到过的人在退出登陆后再次进入网站，一开始会显示可以签到，
        因为session的内容会在退出之后清空，由于已经签到过，在点击签到之后不会增加金币（调用数据库中的日期String进行判断此天内是否签到过）
        再显示不可签到
        */
        if(lastDate!=null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String now = sdf.format(new Date());
            userService.updateLastDate(user.getUserId(),now);//更新签到时间
            if(lastDate.equals(now)==false)
                userService.addBalance(user);//不是同一天，允许签到
        }
        else
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
            String now = sdf.format(new Date());
            userService.updateLastDate(user.getUserId(),now);
            userService.addBalance(user);
            //第一次签到，本次签到成功
        }
        session.setAttribute("sign", "signed");//session有flag就不能签到了
        try {
            user= userService.login(user.getUsername(),user.getPassword());
        } catch (UserException e) {
            e.printStackTrace();
        }
        session.setAttribute("user",user);
    }
    @RequestMapping("/updateUser")
    public String updateUser(@Param("userName") String userName, @Param("password") String password ,HttpSession session,Model model)
    {
        //不允许同名
        List<User> userL = userService.findAll();
        for(User u:userL)
        {
            if(u.getUsername().equals(userName))
            {
                model.addAttribute("errorMsg3","This username is exist!");
                return "mypage";
            }
        }
        List<Books> booksL = bookService.findAllBooks();
        for(Books b:booksL)
        {
            if(b.getBookAutor().equals(userName))
            {
                model.addAttribute("errorMsg3","This username is exist!");
                return "mypage";
            }
        }
        User user= (User) session.getAttribute("user");
        String lastName = user.getUsername();
        userService.updateUser(user,userName,password);
        //如果是作者，那么就要更改作品的作者名
        if(user.getIsWriter()==1)
            for(Books b:booksL)
            {
                if(b.getBookAutor().equals(lastName))
                {
                    System.out.println(b.getBookAutor()+b.getBookId()+userName);
                    bookService.updateAuthor(b.getBookId(),userName);
                    System.out.println(b.getBookAutor());
                }
            }
        return "login";
    }
    @RequestMapping("/updateUserPic")
    public String updateUserPic(@RequestParam MultipartFile picb, HttpServletRequest request, HttpSession session)
    {
        ServletContext application = request.getServletContext();
        String path = application.getRealPath("WEB-INF/images/pic");
        File file = new File(path);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH-mm-ss");
        String oldname = picb.getOriginalFilename();
        String image = null;
        int index = oldname.lastIndexOf(".");
        if(index!=-1)
        {
            image = sdf.format(new Date())+oldname.substring(index);
        }
        File saveFile = new File(file,image);
        try {
            picb.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = (User) session.getAttribute("user");
        userService.updatePic(user.getUserId(),"pic/"+image);
        System.out.println(user.getUsername()+" pic is changed.");
        try {
            user = userService.login(user.getUsername(),user.getPassword());
            session.setAttribute("user",user);
        } catch (UserException e) {
            e.printStackTrace();
        }
        return "mypage";
    }

}
