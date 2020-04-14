package com.oracle.xiaoshuo.service.impl;

import com.oracle.xiaoshuo.common.constant.UserConstant;
import com.oracle.xiaoshuo.common.exception.UserException;
import com.oracle.xiaoshuo.dao.UserDao;
import com.oracle.xiaoshuo.pojo.User;
import com.oracle.xiaoshuo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {

        List<User> users= userDao.findAll();
        return users;
    }

    @Override
    public void newUser(String userName, String password) {
        Integer isWriter = 0;
        Integer balance = 0;
        Integer myEarnings = 0;
        System.out.println("newUser-->"+userName+" "+password);
        userDao.newUser(userName,password,balance,isWriter,myEarnings);
    }

    @Override
    public User login(String userName, String password) throws UserException {
        System.out.println(userName);
        System.out.println(password);
        User user= userDao.login(userName,password);
        System.out.println("service---->"+user);
        if(user!=null)
        {
            return user;
        }
        throw new UserException("用户名或密码不正确");
    }

    @Override
    public void updateBalance(User user)  {
            userDao.updateBalance(user.getUserId(),user.getBalance()-UserConstant.BOOK_PRICE);

        }

    @Override
    public void addBalance(User user) {
        userDao.updateBalance(user.getUserId(),user.getBalance()+UserConstant.BALANCE);
    }

    @Override
    public void updateUser(User user, String userName, String password) {
        userDao.updateUser(user,userName,password);
    }

    @Override
    public void updateWriter(User user) {
        userDao.updateWriter(user,UserConstant.IS_WRITER);
    }

    @Override
    public User findById(User user) {
        return userDao.findById(user);
    }


}
