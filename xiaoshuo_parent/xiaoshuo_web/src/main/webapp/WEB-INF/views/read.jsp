<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/basic.css" >
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/AsAuthor.css" >
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/reads.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/layer/layer.js"></script>
    <style>
        .Intro{
            font-size: 15px;
            color: #fd0033;
        }
        .sectionId{
            width: 30px;
            height: 20px;
            background: #009f95;
            text-align: center;
            color: white;
            float: left;
            margin: 10px;
        }
        .sectionId button{
            width: 50px;
            height: 30px;
            color: white;
            background: #009f95;
        }
    </style>
    <script>
        function read_ajax(bookId) {
            $.post('${pageContext.request.contextPath}/reads/section',
                {'bookId':bookId},
                function () {
                    location.href='${pageContext.request.contextPath}/reads/section?bookId='+bookId;
                }
            )
        }

        function section_ajax(sectionId,bookId) {
            $.post('${pageContext.request.contextPath}/reads/content',
                {'sectionId':sectionId,
                    'bookId':bookId
                },
                function () {
                    location.href='${pageContext.request.contextPath}/reads/content?sectionId='+sectionId+'&bookId='+bookId;
                }
            )
        }
        function buy_ajax(sectionId,bookId) {
            if(${not empty user})
            {
                if(${free eq 1}) alert("此章节已开放");
                else{
                    $.post('${pageContext.request.contextPath}/reads/buyBook',
                        {'sectionId':sectionId,'bookId':bookId},
                        function () {
                            location.href='${pageContext.request.contextPath}/reads/content?sectionId='+sectionId+'&bookId='+bookId;
                        }
                    )
                }

            }
            else
            {
                alert("请先登录");
                location.href='${pageContext.request.contextPath}/users/login';
            }

        }
        function addBOOK(bookId,sectionId) {
            if(${not empty user})
            {
                if(sectionId==null||sectionId=="") sectionId=${minSection.sectionId};
                $.post('${pageContext.request.contextPath}/reads/addBook',
                    {'bookId':bookId,'sectionId':sectionId},
                    function () {
                        location.href='${pageContext.request.contextPath}/reads/addBook?bookId='+bookId+'&sectionId='+sectionId;
                    }
                )
            }
            else
            {
                alert("请先登录");
                location.href='${pageContext.request.contextPath}/users/login';
            }
        }
        function addpinglun() {
            if(${not empty user})
            {
                pinglunForm.action="${pageContext.request.contextPath}/reads/PingLin?bookId="+${thisBook.bookId};
                pinglunForm.submit();
            }
            else
            {
                alert("请先登录");
                location.href='${pageContext.request.contextPath}/users/login';
            }
        }
    </script>
    <script>
        var errorMsg='${errorMsg2}';
        if(errorMsg!='')
        {
            layer.msg(errorMsg,{
                time:2000,
                skin:'errorMsg'
            })
        }
        var errorMsg='${errorMsg3}';
        if(errorMsg!='')
        {
            layer.msg(errorMsg,{
                time:2000,
                skin:'errorMsg'
            })
        }
    </script>
</head>
<body>
<div id="topOne">

</div>
<div id="Top">
    <!--<div class="logo"><a href="#"><img src="images/logo111.jpg" width="200px" height="50px"/></a></div>-->
    <div class="search">

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
<div class="zhuti">
    <div class="tupian">
        <img src="${pageContext.request.contextPath}/images/${thisBook.bookImage}" alt="${thisBook.bookName}">
    </div>
    <div class="jianjie">
        <h2>小说名称：${thisBook.bookName} </h2>
        <h2>作者：${thisBook.bookAutor}</h2>
        <h3>小说简介：</h3>
        <p class="Intro">${thisBook.bookIntro}</p>
        <button value="yuedu" id="read_btn" style=" width: 150px;height: 30px;background: #3297d8;color: white;" onclick="read_ajax(${thisBook.bookId})">查看章节并阅读</button>
        <%-- <input id="minSection" value="${sections}.sectionId" hidden>--%>
        <c:if test="${not empty bookMark && bookMark.bookId==thisBook.bookId}">
            上次阅读至第${bookMark.sectionId-minSection.sectionId+1}章
        </c:if><br/><hr/>
        <c:forEach items="${sections}" var="section2">
        <div class="sectionId">
            <c:choose>
            <c:when test="${thisBook.bookId!=lastBookId}">
            <button onclick="section_ajax(${section2.sectionId},${thisBook.bookId})" style="font-size: 22px">${section2.sectionId-minSection.sectionId+1}</button></div>
        </c:when>
        <c:when  test="${section2.sectionId==section.sectionId}">
        <button onclick="section_ajax(${section2.sectionId},${thisBook.bookId})" style="font-size: 22px;background-color:red">${section2.sectionId-minSection.sectionId+1}</button></div>
    </c:when>
    <c:otherwise>
    <button onclick="section_ajax(${section2.sectionId},${thisBook.bookId})" style="font-size: 22px">${section2.sectionId-minSection.sectionId+1}</button></div>
</c:otherwise>
</c:choose>
</c:forEach>

</div>
<div class="see">
    <button value="buy" onclick="buy_ajax(${section.sectionId},${thisBook.bookId})">购买</button>
    <button value="pinglun" id="pinglun_btn">评论</button>
    <button value="shuqian" onclick="addBOOK(${thisBook.bookId},${section.sectionId})">添加书签</button>
</div>
</div>
<div class="text_pinglun" id="pinglun">
    <form name="pinglunForm"  method="post">
        <input type="text" name="pinglun">
        <button onclick="addpinglun(${thisBook.bookId})" value="提交" class="tijiao" >评论</button>
    </form>
</div>
<div class="read" id="read">
    <c:choose>
        <c:when test="${not empty section.sectionId&&free eq 1&&lastBookId==thisBook.bookId}">
            <h2 style="text-align: center;color: #fd0033">第${section.sectionId-minSection.sectionId+1}章</h2>
            ${section.sectionContent}
        </c:when>
        <c:when test="${not empty section.sectionId&&free eq 0&&lastBookId==thisBook.bookId}">
            <h2 style="text-align: center;color: #fd0033">当前章节需要收费,请点击上方购买按钮进行购买</h2>
            <input type="hidden" id="noFree" value="${section.sectionId}"/>
        </c:when>
        <c:otherwise>

        </c:otherwise>
    </c:choose>
</div>
</body>
</html>