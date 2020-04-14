package com.oracle.xiaoshuo.pojo;

public class BookType {
    private Integer id;
    private  String booksType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBooksType() {
        return booksType;
    }

    public void setBooksType(String booksType) {
        this.booksType = booksType;
    }

    @Override
    public String toString() {
        return "BookType{" +
                "id=" + id +
                ", booksType='" + booksType + '\'' +
                '}';
    }
}
