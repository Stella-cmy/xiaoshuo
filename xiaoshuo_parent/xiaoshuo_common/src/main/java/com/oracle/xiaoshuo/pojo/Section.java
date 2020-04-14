package com.oracle.xiaoshuo.pojo;

import java.io.Serializable;

public class Section implements Serializable {
    private Integer sectionId;
    private Integer bookId;
    private String sectionContent;
    private Integer isFree;

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getSectionContent() {
        return sectionContent;
    }

    public void setSectionContent(String sectionContent) {
        this.sectionContent = sectionContent;
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    @Override
    public String toString() {
        return "Section{" +
                "sectionId=" + sectionId +
                ", bookId=" + bookId +
                ", sectionContent='" + sectionContent + '\'' +
                ", isFree=" + isFree +
                '}';
    }
}
