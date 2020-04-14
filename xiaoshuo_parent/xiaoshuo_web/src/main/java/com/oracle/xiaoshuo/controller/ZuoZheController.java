package com.oracle.xiaoshuo.controller;

import com.oracle.xiaoshuo.dao.SectionDao;
import com.oracle.xiaoshuo.pojo.Books;
import com.oracle.xiaoshuo.pojo.Section;
import com.oracle.xiaoshuo.pojo.User;
import com.oracle.xiaoshuo.service.BookService;
import com.oracle.xiaoshuo.service.ReadService;
import com.oracle.xiaoshuo.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
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
    public String updateBook(String updateBookName, Integer updateSection, HttpSession session, Model model) {
        System.out.println(updateBookName);
        System.out.println(updateSection);
        User user= (User) session.getAttribute("user");
        Books book = bookService.findByName(updateBookName);
        System.out.println(book.getBookName()+book.getBookAutor());
        if(book.getBookAutor().equals(user.getUsername()))
        {
            model.addAttribute("updateBook",book);//需要书名
            List<Section> sections= readService.findByBookId(book.getBookId());//在发表新书时已经发布了一章，sections不为空
            int min = sections.get(0).getSectionId();
            int max = sections.get(sections.size()-1).getSectionId();
            System.out.println(min+"---"+max+" updateSection:"+updateSection);
            model.addAttribute("thisSection",updateSection);//需要章节数
            if(updateSection>=1&&updateSection<=max-min+2)
            {
                Section section = readService.findByBookIdAndSectionId(book.getBookId(), updateSection+min-1);
                if(section==null)
                {
                    Section sec = new Section();
                    sec.setBookId(book.getBookId());
                    sec.setSectionId(max+1);
                    sec.setSectionContent(null);
                    model.addAttribute("updateSection", sec);
                }
                else model.addAttribute("updateSection",section);
            }
        }
        else model.addAttribute("errorMsg","该章节不可编辑");
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
            model.addAttribute("Msg","修改成功！");
        }
       else  if(sectionId==max+1)
        {
            Integer lastSection = readService.findLastSectionId();
            readService.insertOne(context,bookId,lastSection+1);
            model.addAttribute("Msg","更新成功！");
        }
       return "zuozhe";
    }
    @RequestMapping("/saveMyBook")
    public String  saveMyBook( @Param("newBookName") String newBookName, @Param("newBookType") Integer newBookType,@Param("newzuozhe") String newzuozhe,@Param("newjianjie") String newjianjie,@Param("bianji") String bianji, HttpSession session) {
            String image = "moren.png";
            bookService.saveMyBook(newBookName, newBookType, newzuozhe, image, newjianjie);
            Books book = bookService.findByName("bookName");
            Integer lastSectionId = readService.findLastSectionId();
            System.out.println(lastSectionId + " " + book.getBookId() + bianji);
            readService.insertOne(bianji, book.getBookId(), lastSectionId + 1);
            System.out.println(1111 + newBookName + " " + newBookType + " " + newzuozhe + " " + newjianjie + " " + " " + bianji);
            session.setAttribute("mybook", book);
            return "zuozhe";
    }

}
