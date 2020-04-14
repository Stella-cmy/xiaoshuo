package com.oracle.xiaoshuo.controller;

import com.oracle.xiaoshuo.common.exception.UserException;
import com.oracle.xiaoshuo.pojo.Books;
import com.oracle.xiaoshuo.pojo.User;
import com.oracle.xiaoshuo.service.BookService;
import com.oracle.xiaoshuo.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @param session
     * @param model
     */

    @RequestMapping("/register")
    public String register(String userName, String password, HttpSession session, Model model) {
            System.out.println(userName);
            System.out.println(password);
            //不允许同名
            List<User> userL = userService.findAll();
            for(User u:userL)
            {
                if(u.getUsername().equals(userName))
                {
                    model.addAttribute("errorMsg2","This username is exist!");
                    return "register";
                }
            }
            List<Books> booksL = bookService.findAllBooks();
            for(Books b:booksL)
            {
                if(b.getBookAutor().equals(userName))
                {
                    model.addAttribute("errorMsg2","This username is exist!");
                    return "register";
                }
            }
            userService.newUser(userName,password);
            model.addAttribute("errorMsg2","Succeed! Please login!");
            return "login";

    }
    @RequestMapping("/registerpage")
    public String registerpage() {
        return "register";
    }
    @RequestMapping("/login")
    public String login(String userName, String password, HttpSession session, Model model) {
        try {
            User user = userService.login(userName, password);
            System.out.println(userName);
            System.out.println(password);
            session.setAttribute("user", user);
            return "main";
        } catch (UserException ex) {
            model.addAttribute("errorMsg2", ex.getMessage());
            return "login";
        }

    }
    @RequestMapping("/addBalance")
    public void addBalance(HttpSession session)
    {
       User user= (User) session.getAttribute("user");
       userService.addBalance(user);
       session.setAttribute("Msg",1);
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

}
