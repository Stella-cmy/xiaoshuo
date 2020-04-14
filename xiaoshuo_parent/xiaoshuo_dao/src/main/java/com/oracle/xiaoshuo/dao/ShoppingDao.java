package com.oracle.xiaoshuo.dao;

import com.oracle.xiaoshuo.pojo.BookType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingDao {
    /**
     * bookType
     * @return
     */
    @Select("select * from booktype")
    @Results(id="resultMap",value = {
            @Result(column = "id",property = "id" ,id=true),
            @Result(column = "books_type", property = "booksType")
    })
    public List<BookType> findAll();
    @Select("select * from booktype where id=#{id}")
    public BookType findtypeById(Integer id);


}
