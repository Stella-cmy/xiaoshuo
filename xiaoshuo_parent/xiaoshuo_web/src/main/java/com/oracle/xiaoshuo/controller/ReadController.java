package com.oracle.xiaoshuo.controller;

import com.oracle.xiaoshuo.common.exception.ReachException;
import com.oracle.xiaoshuo.common.exception.UserException;
import com.oracle.xiaoshuo.pojo.*;
import com.oracle.xiaoshuo.service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/reads")
public class ReadController {
    private static int bookIds=0;
    private static Calendar ban = new GregorianCalendar(2020,04,04);//这是5月4日
    private static boolean flash = true;
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
        /*//修改书籍月榜与周榜
        Books tBook = bookService.findById(bookId);
        Integer yuebang = tBook.getReadYueBang();
        Integer zhoubang = tBook.getReadZhouBang();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //判断是否同一月
        String riqi = sdf.format(ban.getTime());
        String now = sdf.format(new Date());
        if(now.substring(0,7).equals(riqi.substring(0,7))==false&&now.substring(8,10).equals("01")==true&&flash==false)//新的一月且尚未更新
        {
            bookService.updateReadYue(bookId,1);flash=true;//已进行更改
        }
        else bookService.updateReadYue(bookId,yuebang+1);
        //判断是否是同一周
        Calendar d2 = Calendar.getInstance();
        int day1= ban.get(Calendar.DAY_OF_YEAR);
        int day2 = d2.get(Calendar.DAY_OF_YEAR);
        int year1 = ban.get(Calendar.YEAR);
        int year2 = d2.get(Calendar.YEAR);
        int days = 0;
        if(year1 != year2)   //不同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            days = timeDistance + (day2-day1) ;
        }
        else    //同一年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            days = day2-day1;
        }
        if(days%7==0&&zhoubang!=1)
        {

        }
*/
        if(ReadController.bookIds==0) ReadController.bookIds=bookId;
        session.setAttribute("lastBookId",ReadController.bookIds);
        Books book= shoppingService.findById(bookId);
        session.setAttribute("thisBook",book);
        List<Section> sections= readService.findByBookId(bookId);
        session.setAttribute("minSection",sections.get(0));
        session.setAttribute("sections",sections);
        //获取书架
        User user= (User) session.getAttribute("user");
        if(user!=null)
        {
            UserMiddelBook bookMark = userMiddelBookService.findByBookIdUserId(bookId,user.getUserId());
            if(bookMark!=null)
            {
                int a=1;
                for(Section ss:sections)
                {
                    if(ss.getSectionId().compareTo(bookMark.getSectionId())!=0)  a++;
                    else break;
                }
                System.out.println("This is "+a+" sector on bookmark.");
                session.setAttribute("bookMark",bookMark);
                session.setAttribute("bookMarkCount",a);
            }
        }
        //获取评论
        //先找楼主，在每个楼主下找回复
        List<Conment> louZhu = readService.findAllLouZhu(bookId);
        System.out.println("louZhu-->"+louZhu);
        List<Conment> all = new ArrayList<Conment>();
        for(Conment c:louZhu)
        {
            if(c!=null) all.add(c);
            List<Conment> m = null;
            m = readService.findAllLouXia(c.getConmentId());
            if(m!=null&&!m.isEmpty()) all.addAll(m);
        }
        System.out.println("allComments-->"+all);
        session.setAttribute("conmentsY",all);
        return "read";
    }
    @RequestMapping("/finByBookName")
    public String finByBookName(@Param("bookName")String bookName, RedirectAttributes attr, HttpSession session) {
        try {
            System.out.println(bookName);
            Books book = shoppingService.findByBookName(bookName);
            System.out.println("book--->"+book);
                session.setAttribute("thisBook", book);
                return "redirect:read?bookId="+book.getBookId();
        } catch (Exception ex) {
            attr.addFlashAttribute("errorMsg", "The novel name does not exist!");
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
    public String content(@Param("sectionId") Integer sectionId,@Param("bookId") Integer bookId,HttpSession session){
        ReadController.bookIds=bookId;
        System.out.println("content----bookId>"+ bookId);
        System.out.println("content----sectionId>"+ sectionId);
        System.out.println("last----lastBookId>"+ ReadController.bookIds);
        session.setAttribute("lastBookId",ReadController.bookIds);
        Section section1=readService.findBySectionId(sectionId,bookId);
        session.setAttribute("section",section1);
        int free=0;
        List<Section> sections= readService.findByBookId(bookId);
        int se=1;
        for(Section ss:sections)
        {
            if(ss.getSectionId().compareTo(section1.getSectionId())!=0)  se++;
            else break;
        }
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
            else if(se<=20) free=1;//若书架上没有，只能看前20章
        }
        else
        {if(se<=20) free=1;}//未登录，只能看前20章
        session.setAttribute("free",free);
        session.setAttribute("count",se);
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
                    try {
                        user= userService.login(user.getUsername(),user.getPassword());
                    } catch (UserException e) {
                        e.printStackTrace();
                    }
                    session.setAttribute("user",user);
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
                    try {
                        user= userService.login(user.getUsername(),user.getPassword());
                    } catch (UserException e) {
                        e.printStackTrace();
                    }
                    session.setAttribute("user",user);
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
    @RequestMapping("/Pinglin")
    public String pinglun(@Param("pinglun") String pinglun,@Param("bookId") Integer bookId, HttpSession session)
    {
        System.out.println(bookId+"---------"+pinglun);
        if(pinglun==null||pinglun=="")  return "read";
        User user= (User) session.getAttribute("user");
        Integer userId = user.getUserId();
        System.out.println("user "+userId+" book "+bookId+" add comment "+pinglun);
        readService.savePingLun(pinglun,userId,bookId);
        List<Conment> louZhu = readService.findAllLouZhu(bookId);
        List<Conment> all = null;
        for(Conment c:louZhu)
        {
            all.add(c);
            List<Conment> m = null;
            m = readService.findAllLouXia(c.getBookId());
            if(m!=null&&!m.isEmpty()) all.addAll(m);
        }
        System.out.println(all);
        session.setAttribute("conmentsY",all);
        return "read";
    }
    @RequestMapping("/reply")
    public String reply(@Param("replyContext") String replyContext,@Param("bookId") Integer bookId,@Param("replyId")Integer replyId, HttpSession session)
    {
        if(replyContext==null||replyContext=="")  return "read";
        User user= (User) session.getAttribute("user");
        Integer userId = user.getUserId();
        System.out.println("user "+userId+" book "+bookId+" add comment "+replyContext+" reply "+replyId);
        readService.reply(replyContext,userId,bookId,replyId);
        List<Conment> louZhu = readService.findAllLouZhu(bookId);
        List<Conment> all = null;
        for(Conment c:louZhu)
        {
            all.add(c);
            List<Conment> m = null;
            m = readService.findAllLouXia(c.getBookId());
            if(m!=null&&!m.isEmpty()) all.addAll(m);
        }
        System.out.println(all);
        session.setAttribute("conmentsY",all);
        return "read";
    }
}
