<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/basic.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/HomePage.css">
    <script src="https://code.jquery.com/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap-paginator.js"></script>
    <script src="${pageContext.request.contextPath}/layer/layer.js"></script>
    <script>

        window.onload = function() {
            if (!sessionStorage.getItem("firstpageflag")) {

                sessionStorage.setItem("firstpageflag", 1);
                $.ajax({
                    type : "post",
                    async : false,
                    url :"${pageContext.request.contextPath}/mypage/pinglun",
                    datatype : "json",
                    success : function(data) {
                        window.location.reload();
                    },
                })
            }
        }
        function updatePic() {
            var file = document.getElementById("picb").value;
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
                    $("#updatePicF").submit();
                    $.post(
                        function () {
                            location.href="${pageContext.request.contextPath}/mypage/bookrack";
                        }
                    )
                }
            }


        }
        function addBlance() {
            $.post('${pageContext.request.contextPath}/users/addBalance',
            function () {
                location.href="${pageContext.request.contextPath}/mypage/bookrack";
            })
        };
        function deletePingLun(conmentId) {
            $.post('${pageContext.request.contextPath}/mypage/deletePingLun',
                {"conmentId":conmentId},
                function () {
                    alert("删除成功！");
                    location.href="${pageContext.request.contextPath}/mypage/bookrack";
                }
            )
        }
        function showReply() {
            //$.post('${pageContext.request.contextPath}/mypage/findReply');
            var allComments=document.getElementById("allComments");
            allComments.style.display='block';
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
<div class="homepage">
    <!--我的书架开始-->
    <div class="shujia">
        <div class="h21">
            <h2>我的书架</h2>
        </div>
        <div class="books">
            <c:if test="${not empty pageInfo.list}">
                <ul>
                    <c:forEach items="${pageInfo.list}" var="bookrack">
                        <li>
                            <a href="${pageContext.request.contextPath}/reads/read?bookId=${bookrack.bookId}"><img src="${pageContext.request.contextPath}/images/${bookrack.bookImage}"  width="80%" height="80%"></a>
                            <a href="${pageContext.request.contextPath}/reads/read?bookId=${bookrack.bookId}"><p>${bookrack.bookName}</p></a>
                        </li>
                    </c:forEach>

                </ul>
            </c:if>
            <c:if test="${empty pageInfo.list}">
                <h1>&nbsp;&nbsp;暂无书籍</h1>
            </c:if>

        </div>
    </div>
    <!--我的书架结束-->
    <div class="h21" onclick="see_pinglun()">
        <h2>我发出的评论</h2>
    </div>
    <!--评论钱包信息开始-->
    <div class="pinglun&qianbao">
        <div class="pinglun">
           <c:if test="${not empty conments}">
            <ul>
                <c:forEach items="${conments}" var="conment" >
                    <li>${conment.conment}
                        <button class="shanchu" value="" onclick="deletePingLun(${conment.conmentId})">删除</button>
                    </li>
                </c:forEach>
            </ul>
            </c:if>
            <c:if test="${empty conments}">
                <h1>&nbsp;&nbsp;暂无评论</h1>
            </c:if>
        </div>
        <div class="qianbao">
            <p class="qianbao_p">我的钱包</p>
            <p class="money1"> 当前用户余额：</p> <span id="money">￥：${user.balance}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <c:if test="${empty sign}">
                <button onclick="addBlance()">签到得金币</button>
            </c:if>
            <c:if test="${not empty sign}">
                <button >已经签到</button>
            </c:if>
            <c:if test="${user.isWriter eq 1}">
                <p class="money1">当前收益：</p><span id="earning">￥：${user.myEarnings}</span>
            </c:if>
        </div>
        <p class="xinxi_p">个人信息管理</p>
        <div class="xinxi">
            <form action="${pageContext.request.contextPath}/users/updateUser" class="xinxi_form1">
                <p>请输入修改后的用户名：</p><input type="text" name="userName">
                <p>请输入新的登录密码：</p><input type="password" name="password">
                <input type="submit" value="提交" class="submit_xinxi"/>
            </form>
        </div>
        <div class="xinxi2">
            <form action="/users/updateUserPic" id="updatePicF" method="post" enctype="multipart/form-data" class="xinxi_form2">
                <p>点此更换头像：</p><input type="file" id="picb" name="picb">
                <input type="button" value="确定" onclick="updatePic()"><br>
            </form>
        </div>
    </div>
    <button value="seepinglun" id="seepinglun_btn" onclick="showReply()" style="float: left">查看我收到的评论</button>
    <div class="pinglun&reply" id="allComments">
        <div class="see_pinglun" >
            <c:if test="${not empty conmentsS}">
                <ul>
                    <c:forEach items="${conmentsS}" var="conment">
                    <c:if test="${empty conment.replyId}">
                        <li>我：&nbsp;${conment.conment}
                        </li>
                    </c:if>
                    <c:if test="${not empty conment.replyId}">
                        <li>&nbsp;&nbsp;&nbsp;回复：&nbsp;&nbsp;${conment.conment}
                        </li>
                    </c:if>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${empty conmentsS}">
                <h1>&nbsp;&nbsp;暂无评论</h1>
            </c:if>
        </div>
    </div>
    <!--评论钱包信息结束-->
    <!--猜你喜欢-->
    <div class="shujia">
        <div class="h21">
            <h2>猜你喜欢</h2>
        </div>
        <div class="books">
            <ul>
                <c:forEach items="${likeTypebooks}" var="likeTypebook">
                    <li>
                        <a href="${pageContext.request.contextPath}/reads/read?bookId=${likeTypebook.bookId}"><img src="${pageContext.request.contextPath}/images/${likeTypebook.bookImage}" alt="" width="80%" height="80%"></a>
                        <a href="${pageContext.request.contextPath}/reads/read?bookId=${likeTypebook.bookId}"><p>${likeTypebook.bookName}</p></a>
                    </li>
                </c:forEach>

            </ul>
        </div>
    </div>
</div>
</body>
</html>
