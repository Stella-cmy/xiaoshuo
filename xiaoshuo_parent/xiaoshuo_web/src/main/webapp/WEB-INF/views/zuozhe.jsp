<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/basic.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/HomePage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/zuozhe.css">
    <script src="https://code.jquery.com/jquery-2.0.0.min.js"></script>
    <script language="JavaScript" type="text/javascript">
        $(function(){
            $("#new_Book").click(function(){
                var file = document.getElementById("photo").value;
                if(file=="")
                {
                    alert("请选择文件");
                }
                else
                {
                    var reg = ".*\\.(jpg|png|JPG|PNG)";
                    var r = file.match(reg);
                    if(r == null){
                        alert("对不起，您的图片格式不正确，请重新上传");
                    }
                    else
                    {
                        var bianji = document.getElementById("bianji").innerHTML;
                        $("#con").val(bianji);
                        $("#newBookForm").submit();
                        $.post(
                            function () {
                                location.href="${pageContext.request.contextPath}/mypage/bookrack";
                            }
                        )
                    }
                }
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
                        alert("Update successfully!");
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
<c:if test="${user.isWriter==0}">
    <h1 style="font-size: 30px;text-align: center; color: red">您还不是作者请点击下面按钮成为作者</h1>
    <button onclick="AsWriter()" style="display:block; font-size: 30px; margin: 0 auto; ">成为作者</button>
</c:if>
<c:if test="${user.isWriter==1}">
<div class="center">
    <div class="title_1">
        <h1>发表新文</h1>
    </div>
    <form id="newBookForm" action="${pageContext.request.contextPath}/zuozhe/saveMyBook" method="post" enctype="multipart/form-data">
        <div class="writerBook">
            小说名称：<input type="text" id="newBookName" name="newBookName"/>
            <br>
            <c:if test="${not empty errorMsg}">
            <input type="text"  style="color:red" value="${errorMsg1}">
            </c:if>
            <br>
            小说类型：<select id="newBookType" name="newBookType">
            <option value ="1">都市</option>
            <option value ="2">军事</option>
            <option value="3">历史</option>
            <option value="4">武侠</option>
            <option value="5">玄幻</option>
            <option value="6">游戏</option>
        </select><br>
            <input id="photo" type="file" name="photo"><br>
            <input hidden type="text" id="newzuozhe" name="newzuozhe" value="${user.username}"/><br/>
            小说简介：<input type="text" id="newjianjie" name="newjianjie"/><br/>
        </div>
        <textarea hidden id="con" name="con"></textarea>
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
    <c:if test="${not empty errorMsg}">
        <input type="text"  style="color:red" value="${errorMsg}">
    </c:if>
    <br>
    <br>
    <div class="h21" onclick="see_pinglun()">
        <h2>我收到的评论</h2>
    </div>
    <div class="pinglun&qianbao">
    <div class="pinglun">
        <c:if test="${not empty conmentsA}">
            <ul>
                <c:forEach items="${conmentsA}" var="conment" >
                    <li>${conment.conment}</li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty conmentsA}">
            <h1>&nbsp;&nbsp;暂无评论</h1>
        </c:if>
    </div>
    </div>
    </c:if>
</div>
</body>
</html>