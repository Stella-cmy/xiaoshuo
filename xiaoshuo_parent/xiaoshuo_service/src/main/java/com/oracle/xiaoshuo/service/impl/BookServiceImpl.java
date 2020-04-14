package com.oracle.xiaoshuo.service.impl;

import com.oracle.xiaoshuo.dao.BooksDao;
import com.oracle.xiaoshuo.pojo.Books;
import com.oracle.xiaoshuo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {
    @Override
    public void updateAuthor(Integer bookId, String bookAutor) {
        booksDao.updateAuthor(bookId,bookAutor);
    }

    @Autowired
    private BooksDao booksDao;
    @Override
    public Books findById(Integer bookId) {
        return booksDao.findById(bookId);
    }

    @Override
    public List<Books> findAllBookYue() {
        List<Books> booksYue=new ArrayList<>();
        List<Books> books=booksDao.findAllBookYue();
        for (int i = 0; i <10 ; i++) {
            booksYue.add( books.get(i));
        }
        return booksYue;
    }

    @Override
    public List<Books> findByBookType(Integer bookTypeId) {
        List<Books> bookLike=new ArrayList();
        List<Books> books=booksDao.findByBookType(bookTypeId);
        for (int i = 1; i <=5 ; i++) {
            bookLike.add(books.get(i));
        }
        return bookLike;
    }

    @Override
    public Books findByName(String bookName) {
        return booksDao.findByBookName(bookName);
    }

    @Override
    public void saveMyBook(String bookName, Integer type, String zuozhe, String image, String jianjie) {
        booksDao.saveMyBook(bookName,type,zuozhe,image,jianjie);
    }

    @Override
    public List<Books> findAllBook() {
        List<Books> booksZhou=new ArrayList<>();
        List<Books> books=booksDao.findAllBook();
        for (int i = 0; i <12 ; i++) {
            booksZhou.add( books.get(i));
        }
        return booksZhou;
    }
    public List<Books> findAllBooks() {
        List<Books> books=booksDao.findAllBooks();
        System.out.println(books.size());
        return books;
    }
}
