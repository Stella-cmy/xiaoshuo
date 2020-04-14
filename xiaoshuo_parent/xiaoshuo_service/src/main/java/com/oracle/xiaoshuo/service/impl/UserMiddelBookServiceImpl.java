package com.oracle.xiaoshuo.service.impl;

import com.oracle.xiaoshuo.dao.UserMiddelBookDao;
import com.oracle.xiaoshuo.pojo.UserMiddelBook;
import com.oracle.xiaoshuo.service.UserMiddelBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMiddelBookServiceImpl implements UserMiddelBookService {
    @Autowired
    private UserMiddelBookDao userMiddelBookDao;
    @Override
    public void addBook(Integer bookId, Integer userId,Integer sectionId,Integer maxSectionId) {
        userMiddelBookDao.addBook(bookId,userId,sectionId,maxSectionId);
    }

    @Override
    public void updateBook(Integer bookId, Integer userId, Integer sectionId, Integer maxSectionId) {
        userMiddelBookDao.updateBook(bookId,userId,sectionId,maxSectionId);
    }

    @Override
    public UserMiddelBook findByBookIdUserId(Integer bookId, Integer userId) {
        return userMiddelBookDao.findByBookIdUserId(bookId,userId);
    }

    @Override
    public List<UserMiddelBook> findByUserId(Integer userId) {
        return userMiddelBookDao.findByUserId(userId);
    }
}
