package com.oracle.xiaoshuo.service;

import com.oracle.xiaoshuo.pojo.Conment;
import com.oracle.xiaoshuo.pojo.Section;

import java.util.List;

public interface ReadService {
    List<Section> findByBookId(Integer bookId);

    Integer findLastSectionId();

    Section findBySectionId(Integer sectionId,Integer bookId);

    Section findByBookIdAndSectionId(Integer bookId, Integer sectionId);

    void updateContext(String context, Integer bookId, Integer sectionId);

    void insertOne(String context, Integer bookId, Integer sectionId);

    void savePingLun(String pinglun, Integer userId,Integer bookId);

    List<Conment> findAllConment(Integer userId);

    void deletePingLun(Integer conmentId);

}
