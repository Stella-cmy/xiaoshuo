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

    void reply(String context,Integer userId, Integer bookId, Integer replyId);

    void insertOneNew(String context, Integer bookId);

    void savePingLun(String pinglun, Integer userId,Integer bookId);

    List<Conment> findAllConment(Integer userId);

    List<Conment> findAllLouZhu(Integer bookId);

    List<Conment> findAllLouXia(Integer replyId);

    void deletePingLun(Integer conmentId);

    List<Conment> findAllConmentByBook(Integer bookId);

}
