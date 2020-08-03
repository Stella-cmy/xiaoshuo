package com.oracle.xiaoshuo.dao;

import com.oracle.xiaoshuo.pojo.Conment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PingLunDao {
    @Select("select * from conment_1 where user_id=#{userId}")
    @Results(id="comment",value = {
            @Result(property = "conmentId",column = "conment_id",id = true),
            @Result(column = "reply_id",property = "replyId"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "bookId",column = "book_id"),
            @Result(property = "conment",column = "conment")
    })
    List< Conment> findAllConment(@Param("userId") Integer userId);
    @Insert("insert into conment_1 (user_id,book_id,conment) values(#{userId},#{bookId},#{pinglun})")
    void savePingLun(@Param("pinglun") String pinglun, @Param("userId") Integer userId,@Param("bookId") Integer bookId);
    @Delete("delete from conment_1 where conment_id=#{conmentId}")
    void deletePingLun(@Param("conmentId") Integer conmentId);
    @Select("select * from conment_1 where book_id=#{bookId}")
    @Results(id="comments",value = {
            @Result(property = "conmentId",column = "conment_id",id = true),
            @Result(column = "reply_id",property = "replyId"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "bookId",column = "book_id"),
            @Result(property = "conment",column = "conment")
    })
    List< Conment> findAllConmentByBook(@Param("bookId") Integer bookId);
    @Insert("insert into conment_1(reply_id,user_id,book_id,conment) values(#{replyId},#{userId},#{bookId},#{context})")
    void reply(@Param("context") String context, @Param("bookId") Integer bookId,@Param("userId") Integer userId,@Param("replyId") Integer replyId);
    @Select("select * from conment_1 where book_id=#{bookId} and reply_id is null")
    @Results(id="commentsLZ",value = {
            @Result(property = "conmentId",column = "conment_id",id = true),
            @Result(column = "reply_id",property = "replyId"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "bookId",column = "book_id"),
            @Result(property = "conment",column = "conment")
    })
    List< Conment> findAllLouZhu(@Param("bookId") Integer bookId);
    @Select("select * from conment_1 where reply_id=#{replyId}")
    @Results(id="commentsLX",value = {
            @Result(property = "conmentId",column = "conment_id",id = true),
            @Result(column = "reply_id",property = "replyId"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "bookId",column = "book_id"),
            @Result(property = "conment",column = "conment")
    })
    List< Conment> findAllLouXia(@Param("replyId") Integer replyId);
}
