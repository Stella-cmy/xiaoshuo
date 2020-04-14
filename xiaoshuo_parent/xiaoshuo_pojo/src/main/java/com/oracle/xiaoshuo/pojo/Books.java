package com.oracle.xiaoshuo.pojo;

import java.io.Serializable;

public class Books implements Serializable {
    private Integer bookId;
    private String bookName;
    private  String bookAutor;
    private  String bookIntro;
    private  String bookImage;
    private Integer readZhouBang;
    private  Integer readYueBang;
    private BookType type;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAutor() {
        return bookAutor;
    }

    public void setBookAutor(String bookAutor) {
        this.bookAutor = bookAutor;
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public Integer getReadZhouBang() {
        return readZhouBang;
    }

    public void setReadZhouBang(Integer readZhouBang) {
        this.readZhouBang = readZhouBang;
    }

    public Integer getReadYueBang() {
        return readYueBang;
    }

    public void setReadYueBang(Integer readYueBang) {
        this.readYueBang = readYueBang;
    }

    public BookType getType() {
        return type;
    }

    public void setType(BookType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Books{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookAutor='" + bookAutor + '\'' +
                ", bookIntro='" + bookIntro + '\'' +
                ", bookImage='" + bookImage + '\'' +
                ", readZhouBang=" + readZhouBang +
                ", readYueBang=" + readYueBang +
                ", type=" + type +
                '}';
    }
}
