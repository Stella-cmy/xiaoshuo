package com.oracle.xiaoshuo.service.impl;

import com.oracle.xiaoshuo.dao.PingLunDao;
import com.oracle.xiaoshuo.dao.SectionDao;
import com.oracle.xiaoshuo.pojo.Conment;
import com.oracle.xiaoshuo.pojo.Section;
import com.oracle.xiaoshuo.service.ReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadServiceImpl implements ReadService {
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private PingLunDao pingLunDao;
    @Override
    public List<Section> findByBookId(Integer bookId) {
        return sectionDao.findByBookId(bookId);
    }

    @Override
    public Section findBySectionId(Integer sectionId,Integer bookId) {
        return sectionDao.findBySectionId(sectionId,bookId);
    }
    @Override
    public Integer findLastSectionId() {
        Section a = sectionDao.findLastSection();
        if(a==null) return 1;
        return a.getSectionId();
    }
    @Override
    public Section findByBookIdAndSectionId(Integer bookId, Integer sectionId) {
        return sectionDao.findByBookIdAndSectionId(bookId,sectionId);
    }

    @Override
    public void updateContext(String context, Integer bookId, Integer sectionId) {
        sectionDao.updateContext(context,bookId,sectionId);
    }

    @Override
    public void insertOne(String context, Integer bookId, Integer sectionId) {
        sectionDao.insertOne(context,bookId,sectionId);
    }

    @Override
    public void savePingLun(String pinglun, Integer userId,Integer bookId) {
        pingLunDao.savePingLun(pinglun,userId,bookId);
    }

    @Override
    public List<Conment> findAllConment(Integer userId) {
        return  pingLunDao.findAllConment(userId);
    }

    @Override
    public void deletePingLun(Integer conmentId) {
        pingLunDao.deletePingLun(conmentId);
    }


}
