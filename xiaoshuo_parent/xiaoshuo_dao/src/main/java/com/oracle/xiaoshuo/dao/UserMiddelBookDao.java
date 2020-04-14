package com.oracle.xiaoshuo.dao;

import com.oracle.xiaoshuo.pojo.UserMiddelBook;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMiddelBookDao {
    @Update("update user_middle_books set section_id=#{sectionId},maxSection_id=#{maxSectionId} where user_id=#{userId} and book_id=#{bookId}")
    void updateBook(@Param("bookId") Integer bookId,@Param("userId") Integer userId,@Param("sectionId") Integer sectionId,@Param("maxSectionId") Integer maxSectionId);
    @Insert("insert into user_middle_books(user_id,book_id,section_id,maxSection_id) values (#{userId},#{bookId},#{sectionId},#{maxSectionId})")
    void addBook(@Param("bookId") Integer bookId,@Param("userId") Integer userId,@Param("sectionId") Integer sectionId,@Param("maxSectionId") Integer maxSectionId);
    @Select("select * from user_middle_books where user_id=#{userId} and book_id=#{bookId}")
    @Results(id="UserMiddelBookMap",value = {
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "book_id",property = "bookId"),
            @Result(column = "user_id",property = "userId"),
            @Result(column = "section_id",property = "sectionId"),
            @Result(column = "maxSection_id",property = "maxSectionId")
    })
    UserMiddelBook findByBookIdUserId(@Param("bookId") Integer bookId,@Param("userId") Integer userId);
    @Select("select * from user_middle_books where user_id=#{userId}")
    @ResultMap("UserMiddelBookMap")
    List<UserMiddelBook> findByUserId(@Param("userId") Integer userId);
}
