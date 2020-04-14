<%@ page import="com.itcast.xiaoshuo.pojo.Books" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: 13129
  Date: 2020/2/25
  Time: 2:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/basic.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Shopping.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/layer/layer.js"></script>
    <script>
        //服务器提示消息
        var errorMsg='${errorMsg}';
        if(errorMsg!='')
        {
            layer.msg(errorMsg,{
                time:4000,
                skin:'errorMsg'
            })
        }
        ${errorMsg}=null;
        function openwin(){
        window.open("pop1.html","","width=120,height=240") 
} 
        function get_cookie(Name) {
            var search = Name + "=" 
            var returnvalue = ""; 
            if (document.cookie.length>0) {
                offset = document.cookie.indexOf(search) 
                if (offset != -1) {
                offset += search.length; 
                end = document.cookie.indexOf(";", offset); 
                    if (end == -1)
                        end = document.cookie.length; 
                    returnvalue=unescape(document.cookie.substring(offset, end)) 
                } 
            } 
            return returnvalue; 
        } 
        function loadpopup(){ //*控制弹出窗口的函数哟，你要使用他的啊
        if (get_cookie('popped')!=''){
            openwin()
           document.cookie="popped=yes"
            }}
    </script>
    <style>
    .searchTxt{
    width: 331px;
    }
    </style>
</head>
<body onload="loadpopup()">
<div id="topOne">

</div>
<div id="Top">
    <!--<div class="logo"><a href="#"><img src="images/logo111.jpg" width="200px" height="50px"/></a></div>-->
    <div class="search">
        <form id="nameForm" action="${pageContext.request.contextPath}/reads/finByBookName"  method="post">
            <div id="searchTxt" class="searchTxt" >
                <div class="searchMenu">
                </div>
                <input id="sBookName" name="bookName" type="text"  placeholder="请输入小说名称"/>
                <div class="searchBtn">
                    <input type="submit" style="width: 70px;height: 30px;color: red;background-color:#40ac9c" id="searchBtn" value="搜索">
                </div>
            </div>
        </form>
    </div>
    <div class="user">
        <c:if test="${not empty user}">
            欢迎您：${user.username}
        </c:if>
        <c:if test="${empty user}">
            <font><a href="${pageContext.request.contextPath}/users/login">登录&nbsp;&nbsp;&nbsp;|</a></font>
            <font><a href="${pageContext.request.contextPath}/users/registerpage">注册</a></font>
        </c:if>
    </div>
</div>


<div id="Logo" >
    <ul>
        <li class="first"><span class="iconfont">&#xe627;</span>小说网</li>
        <li><a href="${pageContext.request.contextPath}/main/findAllBook" title="首页">首页</a></li>
        <li><a href="${pageContext.request.contextPath}/shop/bookType" title="小说商城">小说书城</a></li>
        <li><a href="${pageContext.request.contextPath}/mypage/bookrack">个人主页</a></li>
        <li><a href="${pageContext.request.contextPath}/zuozhe/aszuozhe">成为作者</a></li>
    </ul>
</div>
<div class="nav_bookType">
    <ul>
        <c:forEach items="${bookTypes}" var="booktype">
            <li><a href="${pageContext.request.contextPath}/shop/books?bookTypeId=${booktype.id}">${booktype.booksType}</a></li>
        </c:forEach>
    </ul>
</div>
<div class="books">
    <ul>
        <c:forEach items="${books}" var="book">
            <li>
                <a href="${pageContext.request.contextPath}/reads/read?bookId=${book.bookId}"> <img src="${pageContext.request.contextPath}/images/${book.bookImage}"  width="80%" height="80%"></a>
                <a href="${pageContext.request.contextPath}/reads/read?bookId=${book.bookId}"><p>${book.bookName} </p></a>
            </li>
        </c:forEach>

    </ul>
</div>
</body>
</html>
