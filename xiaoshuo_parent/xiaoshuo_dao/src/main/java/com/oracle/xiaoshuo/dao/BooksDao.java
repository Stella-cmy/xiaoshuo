package com.oracle.xiaoshuo.dao;

import com.oracle.xiaoshuo.pojo.Books;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksDao {
    @Select("select * from books where type=#{bookTypeId}")
    @Results(id="resultBookMap",value = {
            @Result(column = "book_id",property = "bookId",id = true),
            @Result(column = "book_name",property = "bookName"),
            @Result(column = "book_autor",property = "bookAutor"),
            @Result(column = "book_intro",property = "bookIntro"),
            @Result(column = "book_image",property = "bookImage"),
            @Result(column = "read_zhoubang",property = "readZhouBang"),
            @Result(column = "read_yuebang",property = "readYueBang"),
    })
    List<Books> findBytypeId(@Param("bookTypeId") Integer bookTypeId);
    @Select("select * from books where book_name=#{bookName}")
    @ResultMap("resultBookMap")
    Books findByBookName(@Param("bookName") String bookName);
    @Select("select * from books where book_id=#{bookId}")
    @ResultMap("resultBookMap")
    Books findById(@Param("bookId") Integer bookId);
    @Select("select * from books order by read_zhoubang desc")
    @ResultMap("resultBookMap")
    List<Books> findAllBook();
    @Select("select * from books")
    @ResultMap("resultBookMap")
    List<Books> findAllBooks();
    @Select("select * from books order by read_yuebang desc")
    @ResultMap("resultBookMap")
    List<Books> findAllBookYue();
    @Select("select * from books where type= #{bookTypeId2}")
    @ResultMap("resultBookMap")
    List<Books> findByBookType(@Param("bookTypeId2") Integer bookTypeId2);
    @Insert("insert into books (book_name,book_autor,book_image,book_intro,type) values(#{bookName},#{zuozhe},#{image},#{jianjie},#{type})")
    void saveMyBook(@Param("bookName") String bookName,@Param("type") Integer type,@Param("zuozhe") String zuozhe,@Param("image") String image,@Param("jianjie") String jianjie);
    @Update("update books set book_autor=#{bookAutor} where book_id=#{bookId} ")
    void updateAuthor(@Param("bookId") Integer bookId,@Param("bookAutor") String bookAutor);
}
