package com.oracle.xiaoshuo.dao;

import com.oracle.xiaoshuo.pojo.Section;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionDao {
    @Select("select * from section where book_id=#{bookId}")
    @Results(id = "SectionMap",value = {
            @Result(column = "section_id",property = "sectionId"),
            @Result(column = "book_id",property = "bookId"),
            @Result(column = "section_content",property = "sectionContent"),
            @Result(column = "is_free",property = "isFree")
    }
    )
    List<Section> findByBookId(@Param("bookId") Integer bookId);
    @Select("select * from section where section_id=#{sectionId} and book_id=#{bookId}")
    @ResultMap("SectionMap")
    Section findBySectionId(@Param("sectionId") Integer sectionId,@Param("bookId") Integer bookId);
    @Insert("insert into section(book_id,section_id,section_content) values(#{bookId},#{sectionId},#{context})")
    void insertOne(@Param("context") String context, @Param("bookId") Integer bookId,@Param("sectionId") Integer sectionId);
    @Select("select * from section where book_id=#{bookId} and section_id=#{sectionId}")
    @ResultMap("SectionMap")
    Section findByBookIdAndSectionId(@Param("bookId") Integer bookId,@Param("sectionId") Integer sectionId);
    @Update("update section set section_content=#{context} where section_id=#{sectionId} and book_id=#{bookId}")
    void updateContext(@Param("context") String context, @Param("bookId") Integer bookId,@Param("sectionId") Integer sectionId);
    @Select("select * from section where section_id = (select max(section_id) from section)")
    @ResultMap("SectionMap")
    Section findLastSection();
    @Insert("insert into section(book_id,section_content) values(#{bookId},#{context})")
    void insertOneNew(@Param("context") String context, @Param("bookId") Integer bookId);

}
