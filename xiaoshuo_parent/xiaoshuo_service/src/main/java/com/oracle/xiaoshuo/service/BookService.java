package com.oracle.xiaoshuo.service;

import com.oracle.xiaoshuo.pojo.Books;

import java.util.List;

public interface BookService {
    Books findById(Integer bookId);

    List<Books> findAllBook();

    List<Books> findAllBooks();

    List<Books> findAllBookYue();

    List<Books> findByBookType(Integer bookTypeId);

    Books findByName(String bookName);

    void saveMyBook(String bookName, Integer type, String zuozhe, String image, String jianjie);

    void updateAuthor(Integer bookId,String bookAutor);
}
