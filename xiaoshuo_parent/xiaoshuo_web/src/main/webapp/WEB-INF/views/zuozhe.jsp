<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/basic.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/zuozhe.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js" ></script>
    <script language="JavaScript" type="text/javascript">
        $(function(){
            $("#new_Book").click(function(){
                    var newBookName = document.getElementById("newBookName").value;
                    var index =document.getElementById("newBookType").selectedIndex;
                    var newBookType = document.getElementById("newBookType").options[index].value;
                    var newzuozhe = document.getElementById("newzuozhe").value;
                    var newjianjie = document.getElementById("newjianjie").value;
                    var bianji = document.getElementById("bianji").innerHTML;
                    $.post('${pageContext.request.contextPath}/zuozhe/saveMyBook',
                        {
                            'newBookName':newBookName,
                            'newBookType':newBookType,
                            'newzuozhe':newzuozhe,
                            'newjianjie':newjianjie,
                            'num':1,
                            'bianji':bianji
                        },
                        function () {
                            alert("提交成功");
                            location.href='${pageContext.request.contextPath}/zuozhe/aszuozhe';
                        }
                    )
                }
            );
            $("#update_submit").click(function(){
                var context=document.getElementById("p_sectionContext").innerHTML;
                var bookId = document.getElementById("updateBookId").value;
                var sectionId = document.getElementById("updateSectionId").value;
                $.post('${pageContext.request.contextPath}/zuozhe/submitContext',
                    {'context':context,
                        'bookId': bookId,
                        'sectionId': sectionId
                    },
                    function () {
                        alert("修改成功！");
                        location.href='${pageContext.request.contextPath}/zuozhe/aszuozhe';
                    }
                )
            })
        })
        function AsWriter() {
            $.post('${pageContext.request.contextPath}/zuozhe/AsWriter',
                function () {
                    location.href='${pageContext.request.contextPath}/users/login';
                }
            )
        }
    </script>
</head>
<body>
<div id="Top">

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
<c:if test="${user.isWriter==0}">
    <h1 style="font-size: 30px;text-align: center; color: red">您还不是作者请点击下面按钮成为作者</h1>
    <button onclick="AsWriter()" style="display:block; font-size: 30px; margin: 0 auto; ">成为作者</button>
</c:if>
<c:if test="${user.isWriter==1}">
<div class="center">
    <div class="title_1">
        <h1>发表新文</h1>
    </div>
    <form id="newBookForm" action="${pageContext.request.contextPath}/zuozhe/saveMyBook" method="post">
        <div class="writerBook">
            小说名称：<input type="text" id="newBookName"/><br>
            小说类型：<select id="newBookType">
            <option value ="1">都市</option>
            <option value ="2">军事</option>
            <option value="3">历史</option>
            <option value="4">武侠</option>
            <option value="5">玄幻</option>
            <option value="6">游戏</option>
        </select><br>
            <input hidden type="text" id="newzuozhe" value="${user.username}"/><br/>
            小说简介：<input type="text" id="newjianjie"/><br/>
        </div>
        <div class="bianji">
            <p contenteditable="plaintext-only" id="bianji">写下第一章吧</p>
            小说章节：1<input hidden id="zhuangjie" value="1">
            <input type="button" style="height: 20px;width: 40px" id="new_Book" value="提交" />
        </div>
    </form>
</div>
<div class="updateMyBook">
    <div class="title_1">
        <h1>更新旧文</h1>
        <input hidden id="updateBookId" value="${updateBook.bookId}"/>
        <input hidden id="updateSectionId" value="${updateSection.sectionId}">
    </div>
    <form action="/zuozhe/updateBook" method="post">
        输入你要更新的小说名称： <input type="text" name="updateBookName" value="${updateBook.bookName}"/>
        输入你要更新的小说章节： <input type="number" name="updateSection" value="${thisSection}">
        <input type="submit" value="查询" class="button_serch">
    </form>
    <div class="update">
        <p contenteditable="plaintext-only" id="p_sectionContext">${updateSection.sectionContent}</p>
    </div>
    <input type="button" value="提交" id="update_submit">
    </c:if>
</div>
</body>
</html>