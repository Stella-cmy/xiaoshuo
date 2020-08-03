package com.oracle.xiaoshuo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oracle.xiaoshuo.common.constant.PaginationConstant;
import com.oracle.xiaoshuo.dao.SectionDao;
import com.oracle.xiaoshuo.pojo.*;
import com.oracle.xiaoshuo.service.BookService;
import com.oracle.xiaoshuo.service.ReadService;
import com.oracle.xiaoshuo.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
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
@RequestMapping("/zuozhe")
public class ZuoZheController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private ReadService readService;
    @Autowired
    private SectionDao sectionDao;
    @RequestMapping("/aszuozhe")
    public String aszuozhe(HttpSession session)
    {
        if(session.getAttribute("user")==null|session.getAttribute("user")=="")
        {
            return "login";
        }
        return "zuozhe";
    }
    @RequestMapping("/AsWriter")
    public String AsWriter(HttpSession session)
    {
        User user= (User) session.getAttribute("user");
        userService.updateWriter(user);
        return "login";
    }
    @RequestMapping("/updateBook")
    public String updateBook(String updateBookName, Integer updateSection, HttpSession session,RedirectAttributes attr, Model model) {
        System.out.println(updateBookName);
        System.out.println(updateSection);
        User user= (User) session.getAttribute("user");
        Books book = bookService.findByName(updateBookName);
        System.out.println(book.getBookName()+book.getBookAutor());
        if(book.getBookAutor().equals(user.getUsername())) {
            model.addAttribute("updateBook", book);//需要书名
            List<Section> sections = readService.findByBookId(book.getBookId());//在发表新书时已经发布了一章，sections不为空
            int min = sections.get(0).getSectionId();
            int max = sections.get(sections.size() - 1).getSectionId();
            System.out.println(sections.size());
            System.out.println(min + "---" + max + " updateSection:" + updateSection);
            if (sections.size() >= updateSection)
                System.out.println(sections.get(updateSection - 1).getSectionId());
            model.addAttribute("thisSection", updateSection);//需要章节数
            if (updateSection >= 1 && updateSection <= sections.size() + 1) {//可编辑或更新
                if (updateSection == sections.size() + 1) {
                    Section sec = new Section();
                    sec.setBookId(book.getBookId());
                    sec.setSectionId(max + 1);
                    sec.setSectionContent(null);
                    model.addAttribute("updateSection", sec);
                } else {
                    Section section = readService.findByBookIdAndSectionId(book.getBookId(), sections.get(updateSection - 1).getSectionId());
                    model.addAttribute("updateSection", section);
                }
            }
            else {
                attr.addFlashAttribute("errorMsg", "This chapter can't be edited!");
                return "redirect:tozuozhe";
            }
        }
        else {
            attr.addFlashAttribute("errorMsg", "This chapter can't be edited!");
            return "redirect:tozuozhe";
        }
        return "zuozhe";
    }
    @RequestMapping("/tozuozhe")
    public String tozuozhe(RedirectAttributes attr)
    {
        return "zuozhe";
    }
    @RequestMapping("/submitContext")
    public String submitContext(String context,Integer bookId,Integer sectionId,HttpSession session,Model model)
    {
        Books book = bookService.findById(bookId);
        List<Section> sections= readService.findByBookId(book.getBookId());//在发表新书时已经发布了一章，sections不为空
        int max = sections.get(sections.size()-1).getSectionId();
        if(sectionId<=max)
        {
            readService.updateContext(context,bookId,sectionId);
        }
       else  if(sectionId==max+1)
        {
            try{
                readService.insertOneNew(context, bookId);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
       return "zuozhe";
    }
    @RequestMapping("/saveMyBook")
    public String  saveMyBook(@Param("newBookName") String newBookName, @Param("newBookType") Integer newBookType, @Param("newzuozhe") String newzuozhe, @Param("newjianjie") String newjianjie, @RequestParam MultipartFile photo, @Param("con") String con, RedirectAttributes attr, HttpSession session, HttpServletRequest request) {
     try {
            List<Books> booksL = bookService.findAllBooks();//书名不允许相同
            for (Books b : booksL) {
                if (b.getBookName().equals(newBookName)) {
                    attr.addFlashAttribute("errorMsg1", "This book name is exist!");
                    return "redirect:tozuozhe";
                }
            }
            String type = null;
            switch (newBookType) {
                case 1:
                    type = "city";
                    break;
                case 2:
                    type = "military";
                    break;
                case 3:
                    type = "history";
                    break;
                case 4:
                    type = "swordsman";
                    break;
                case 5:
                    type = "fantasy";
                    break;
                case 6:
                    type = "game";
                    break;
            }
            ServletContext application = request.getServletContext();
            String path = application.getRealPath("WEB-INF/images/"+type);
            System.out.println("path---->"+path);
            File file = new File(path);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH-mm-ss");
            String oldname = photo.getOriginalFilename();
            String image = null;
            int index = oldname.lastIndexOf(".");
            if(index!=-1)
            {
                image = sdf.format(new Date())+oldname.substring(index);
            }
            File saveFile = new File(file,image);
            try {
                photo.transferTo(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bookService.saveMyBook(newBookName, newBookType, newzuozhe, type+"/"+image, newjianjie);
            Books book = bookService.findByName(newBookName);
            System.out.println("newBook-->" + book.getBookId());
            readService.insertOneNew(con, book.getBookId());
            System.out.println(1111 + newBookName + " " + newBookType + " " + newzuozhe + " " + newjianjie + " " + " " + con);
            session.setAttribute("mybook", book);
            return "zuozhe";
        }catch (Exception e)
        {
            attr.addFlashAttribute("errorMsg1", "Succeed!");
            return "redirect:tozuozhe";
        }
        }
}
