package com.oracle.xiaoshuo.service;

import com.oracle.xiaoshuo.pojo.UserMiddelBook;

import java.util.List;

public interface UserMiddelBookService {
    void addBook(Integer bookId, Integer userId,Integer sectionId,Integer maxSectionId);

    void updateBook(Integer bookId, Integer userId,Integer sectionId,Integer maxSectionId);

    UserMiddelBook findByBookIdUserId(Integer bookId, Integer userId);

    List<UserMiddelBook> findByUserId(Integer userId);

    List<UserMiddelBook> findByBookId(Integer bookId);
}
