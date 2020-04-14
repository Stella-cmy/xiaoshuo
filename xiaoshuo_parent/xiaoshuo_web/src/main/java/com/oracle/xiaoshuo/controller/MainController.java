package com.oracle.xiaoshuo.controller;

import com.oracle.xiaoshuo.pojo.Books;
import com.oracle.xiaoshuo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/main")
public class MainController {
    @Autowired
    private BookService bookService;
    @RequestMapping("/findAllBook")
    public String findAllBookZhou(HttpSession session)
    {

        List<Books> booksZhou=bookService.findAllBook();
        List<Books> booksYue=bookService.findAllBookYue();
        session.setAttribute("booksZhou",booksZhou);
        session.setAttribute("booksYue",booksYue);
        return "main";
    }

}
