package com.oracle.xiaoshuo.service;

import com.oracle.xiaoshuo.common.exception.ReachException;
import com.oracle.xiaoshuo.pojo.BookType;
import com.oracle.xiaoshuo.pojo.Books;


import java.util.List;

public interface ShoppingService {
    public List<BookType> findAll();

    List<Books> findBytypeId(Integer bookTypeId);

    Books findByBookName(String bookName) throws ReachException;
    Books findById(Integer bookId);
}
