<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/basic.css" >
    <script src="https://code.jquery.com/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js" ></script>
    <div id="topOne">

    </div>
    <div id="Top">
        <!--<div class="logo"><a href="#"><img src="images/logo111.jpg" width="200px" height="50px"/></a></div>-->
        <div class="search">

        </div>
       <%-- <div class="logo1"><a href="#"><img src="${pageContext.request.contextPath}/images/logo.png"></a></div>--%>
        <div class="user">
            <c:if test="${not empty user}">
                <img src="${pageContext.request.contextPath}/images/${user.pic}" alt="" width="60px" height="60px"> ${user.username}
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
    <!--主体-->

    <div id="Foucs">
        <div class="FoucsCommon">
            <div class="news1">
                <div class="newsback"></div>
                <div class="newsCommon">

                    <h3>小说月榜</h3>
                    <ul>
                        <c:forEach items="${booksYue}" var="bookyue">
                            <li><a href="${pageContext.request.contextPath}/reads/read?bookId=${bookyue.bookId}">${bookyue.bookName}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="flash">
                <!--左右切换按扭-->
               <a href="javascript:void(0)" class="prev"></a>
                <a href="javascript:void(0)" class="next"></a>

                <!--图片滚动部分-->
                <div class="scroll">
                    <img src="${pageContext.request.contextPath}/images/koujue.jpg" width="100%" height="100%"/>
                    <img src="${pageContext.request.contextPath}/images/q_one.jpg" width="100%" height="100%"/>
                    <img src="${pageContext.request.contextPath}/images/woyufengtian.jpg" width="100%" height="100%" />
                    <img src="${pageContext.request.contextPath}/images/zetianji.jpg" width="100%" height="100%"/>
                    <img src="${pageContext.request.contextPath}/images/two.jpg" width="100%" height="100%"/>
                    <img src="${pageContext.request.contextPath}/images/doupocangqion.jpg" width="100%" height="100%"/>
                    <img src="${pageContext.request.contextPath}/images/xishou.jpg" width="100%" height="100%"/>
                </div>
                <!--滚动按扭部分-->
                <div class="But">
                    <span class="hover"></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
            </div>
            <!--轮播图结束-->
            <div class="news">
                <div class="newsback"></div>
                <div class="newsCommon">

                    <h3>小说周榜</h3>
                    <ul>
                        <c:forEach items="${booksZhou}" var="bookzhou">
                        <li><a href="${pageContext.request.contextPath}/reads/read?bookId=${bookzhou.bookId}">${bookzhou.bookName}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>

            <!--<div style="clear:both"></div></div>-->
    <div class="tuijian">
        <h1>首页推荐</h1>
        <div class="books">
            <ul>
                <c:forEach items="${booksYue}" var="bookyue">
                    <li>
                        <a href="${pageContext.request.contextPath}/reads/read?bookId=${bookyue.bookId}"><img src="${pageContext.request.contextPath}/images/${bookyue.bookImage}" alt="" width="80%" height="80%"></a>
                        <a href="${pageContext.request.contextPath}/reads/read?bookId=${bookyue.bookId}"><p>${bookyue.bookName}</p></a>
                    </li>
                </c:forEach>


            </ul>
        </div>
    </div>
</head>
</html>

<script>
    window.onload = function() {
        if (!sessionStorage.getItem("firstpageflag")) {

            sessionStorage.setItem("firstpageflag", 1);
            $.ajax({
                type : "post",
                async : false,
                url : "${pageContext.request.contextPath}/main/findAllBook",
                datatype : "json",
                success : function(data) {
                    window.location.reload();
                },
            })
        }
    }
</script>

