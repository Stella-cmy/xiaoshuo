package com.oracle.xiaoshuo.dao;

import com.oracle.xiaoshuo.pojo.Conment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PingLunDao {
    @Insert("insert into conment_1 (user_id,book_id,conment) values(#{userId},#{bookId},#{pinglun})")
    void savePingLun(@Param("pinglun") String pinglun, @Param("userId") Integer userId,@Param("bookId") Integer bookId);
    @Select("select * from conment_1 where user_id=#{userId}")
    @Results(id="comment",value = {
            @Result(property = "conmentId",column = "conment_id",id = true),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "bookId",column = "book_id"),
            @Result(property = "conment",column = "conment")
    })
   List< Conment> findAllConment(Integer userId);
    @Delete("delete from conment_1 where conment_id=#{conmentId}")
    void deletePingLun(@Param("conmentId") Integer conmentId);
}
