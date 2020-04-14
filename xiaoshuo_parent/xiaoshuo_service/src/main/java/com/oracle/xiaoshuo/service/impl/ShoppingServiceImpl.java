package com.oracle.xiaoshuo.service.impl;

import com.oracle.xiaoshuo.common.exception.ReachException;
import com.oracle.xiaoshuo.dao.BooksDao;
import com.oracle.xiaoshuo.dao.ShoppingDao;
import com.oracle.xiaoshuo.pojo.BookType;
import com.oracle.xiaoshuo.pojo.Books;
import com.oracle.xiaoshuo.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShoppingServiceImpl implements ShoppingService {
    @Autowired
    private ShoppingDao shoppingDao;
    @Autowired
    private BooksDao booksDao;
    @Override
    public List<BookType> findAll() {
        return shoppingDao.findAll();
    }

    @Override
    public List<Books> findBytypeId(Integer bookTypeId) {
        return booksDao.findBytypeId(bookTypeId);
    }

    @Override
    public Books findByBookName(String bookName)throws ReachException {
      Books books=  booksDao.findByBookName(bookName);
       if(books!=null)
       {
           return books;
       }
      throw  new ReachException("对不起，您输入的小说名字不存在！");
    }

    @Override
    public Books findById(Integer bookId) {
        return booksDao.findById(bookId);
    }
}
