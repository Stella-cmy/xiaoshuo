package com.oracle.xiaoshuo.pojo;

import java.io.Serializable;

public class Conment implements  Serializable {
    private Integer conmentId;
    private Integer replyId;
    private Integer userId;
    private Integer bookId;
    private String conment;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getConmentId() {
        return conmentId;
    }

    public void setConmentId(Integer conmentId) {
        this.conmentId = conmentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getConment() {
        return conment;
    }

    public void setConment(String conment) {
        this.conment = conment;
    }

    @Override
    public String toString() {
        return "Conment{" +
                "conmentId=" + conmentId +
                ", replyId=" + replyId +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", conment='" + conment + '\'' +
                '}';
    }
}
