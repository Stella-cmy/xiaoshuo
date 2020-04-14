package com.oracle.xiaoshuo.pojo;

import java.io.Serializable;

public class UserMiddelBook implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private Integer sectionId;
    private Integer maxSectionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getMaxSectionId() {
        return maxSectionId;
    }

    public void setMaxSectionId(Integer maxSectionId) {
        this.maxSectionId = maxSectionId;
    }

    @Override
    public String toString() {
        return "UserMiddelBook{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", sectionId=" + sectionId +
                ", maxSectionId=" + maxSectionId +
                '}';
    }
}
