package com.oracle.xiaoshuo.service;

import com.oracle.xiaoshuo.common.exception.UserException;
import com.oracle.xiaoshuo.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    List<User> findAll();

    void newUser(String userName,String password);

    User login(String userName, String password) throws UserException;

    void updatePic(Integer userId,String pic);

    void updateBalance(User user) ;

    void addBalance(User user);

    void updateUser(User user, String userName, String password);

    void updateWriter(User user);

    void updateMyEarnings(User user,Double myEarnings);

    void flashBalance(User user,Integer newBalance);

    void updateLastDate(Integer userId,String lastDate);

    User findById(User user);
}
