package com.oracle.xiaoshuo.controller;

import com.oracle.xiaoshuo.pojo.BookType;
import com.oracle.xiaoshuo.pojo.Books;
import com.oracle.xiaoshuo.service.ShoppingService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShoppingController {
    @Autowired
    private ShoppingService shoppingService;
    @RequestMapping("/bookType")
    public  String bookType(HttpSession session,Model model,RedirectAttributes attr)
    {
        List<BookType> bookTypes=shoppingService.findAll();
        books(1,model);
       session.setAttribute("bookTypes",bookTypes);
        return "Shopping";
    }
    @RequestMapping("/books")
    public String books(@Param("bookTypeId") Integer bookTypeId, Model model)
    {
        System.out.println(bookTypeId);
        List<Books> books=shoppingService.findBytypeId(bookTypeId);
        model.addAttribute("books",books);
        return "Shopping";
    }

}
