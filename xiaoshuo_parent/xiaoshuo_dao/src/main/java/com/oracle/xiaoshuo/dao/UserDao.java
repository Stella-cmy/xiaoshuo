package com.oracle.xiaoshuo.dao;

import com.oracle.xiaoshuo.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    @Select("select * from user")
    @Results(id = "userMap",value = {
            @Result(id=true,column = "id",property = "UserId"),
            @Result(column = "username",property = "username"),
            @Result(column = "password",property = "password"),
            @Result(column = "balance",property = "balance"),
            @Result(column = "is_writer",property = "isWriter"),
            @Result(column = "email",property = "email"),
            @Result(column = "my_earnings",property = "myEarnings"),
            @Result(column = "lastDate",property = "lastDate"),
            @Result(column = "pic",property = "pic")
    }
    )
    public List<User> findAll();

    @Select("select * from user where username=#{userName} and password=#{password}")
    @ResultMap("userMap")
    User login(@Param("userName") String userName, @Param("password") String password);
    @Update("update user set balance=#{v} where id=#{userId}")
    void updateBalance(@Param("userId") Integer userId,@Param("v") Integer v);
    @Update("update user set my_earnings=#{myEarnings} where id=#{userId}")
    void updateMyEarnings(@Param("userId") Integer userId,@Param("myEarnings") Double myEarnings);
    @Update("update user set username=#{userName},password=#{password} where id=#{user.UserId}")
    void updateUser(@Param("user") User user, @Param("userName") String userName,@Param("password") String password);
    @Update("update user set is_writer=#{isWriter} where id=#{user.UserId}")
    void updateWriter(@Param("user") User user, @Param("isWriter") int isWriter);
    @Update("update user set lastDate=#{lastDate} where id=#{userId}")
    void updateLastDate(@Param("userId") Integer userId,@Param("lastDate") String lastDate);
    @Update("update user set pic=#{pic} where id=#{userId}")
    void updatePic(@Param("userId") Integer userId,@Param("pic") String pic);
    @Select("select * from user where id=#{user.UserId}")
    User findById(@Param("user") User user);
    @Insert("insert into user (username,password,balance,is_writer,my_earnings,pic) values(#{userName},#{password},#{balance},#{isWriter},#{myEarnings},'pic/tou.png')")
    void newUser(@Param("userName") String userName,@Param("password") String password,@Param("balance")Integer balance,@Param("isWriter")Integer isWriter,@Param("myEarnings") Integer myEarnings);
}
