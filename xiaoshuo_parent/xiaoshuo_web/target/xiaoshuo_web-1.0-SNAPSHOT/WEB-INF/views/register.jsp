<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <style type="text/css">
        *{ margin: 0;padding: 0;font-family:"微软雅黑"}
        em{display: block;font-style: normal;font-size: 14px;color: rgb(179, 177, 177)}
        li{list-style: none;display: inline-block;}
        li a{text-decoration: none;}
        html,body,.wrapper{
            width: 100%;
            height: 100%;
            overflow: hidden;
            background-image:url(${pageContext.request.contextPath}/images/background.jpg);
        }
        .wrapper{
            background-image: url("${pageContext.request.contextPath}/../img/bj.gif");
            background-size: 100% 100%;
            position: relative;
        }
        article{
            width: 1200px;
            margin: 0 auto;
        }
        article{
            width: 400px;
            margin: 100px auto 0px auto;
        }
        article h1{width: 400px;color:#AFEEEF;text-align: center;margin-bottom: 15px;font-weight: normal;}
        article h1 em{display: inline-block;color:white;font-size: 25px;}
        .main{
            padding: 40px 0px;
            width: 100%;
            background-color: rgba(24, 80, 137, 0.3);
        }
        form{
            width: 297px;
            margin: 0 auto;
        }
        .main form input{
            margin: 10px 0;
            width: 280px;
            height: 35px;
            border-radius: 3px;
            display: inline-block;
            background:#CCCCCC;
            border: 1px solid rgba(165, 161, 161,0.1);
            padding-left: 10px;
        }

        #one{
            margin-left: 90px;
            width: 100px;
            height: 35px;
            color:white;
            border:none;
            background:#006699;
            margin-top: 15px;
            letter-spacing: 10px;
            font-size: 16px;
            text-align: center;}

    </style>
    <script src="https://code.jquery.com/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/layer/layer.js"></script>
</head>
<script>
    function check() {
        var username = document.getElementById("userName").value;
        var password = document.getElementById("password").value;
        var regex1 = new RegExp("^([a-zA-Z0-9_-]){5,8}$");
        var regex = new RegExp("^(?!^(\\d+|[a-zA-Z]+|[~!@#$%^&*?]+)$)^[\\w~!@#$%^&*?]{8,12}$");
        if(regex.test(password)==false||regex1.test(username)==false)
        {
            alert("The username or password error!");
            location.href='${pageContext.request.contextPath}/users/register';
        }
        else $("#rform").submit();
    }
    //服务器提示消息
    var errorMsg='${errorMsg2}';
    if(errorMsg!='')
    {
        layer.msg(errorMsg,{
            time:2000,
            skin:'errorMsg'
        })
    }
</script>
<body>
<div class="wrapper">
    <article>
        <h1><em>小说网注册</em></h1>
        <div class="main">
            <form action="/users/register" id="rform" >
                <div class="userName">
                    <input type="text" name="userName" id="userName" placeholder="用户名" ><em>由5-8个字符组成！</em>
                </div>
                <div class="password">
                    <input type="password" name="password" id="password" placeholder="密码"><em>使用字母、数字、符号两种及以上的组合，8-12个字符</em>
                </div>
                <input type="button" value="注册" id="one" onclick="check()">
                <br>
                <c:if test="${not empty errorMsg}">
                    <input type="text"  style="color:red" value="${errorMsg}">
                </c:if>
            </form>
        </div>
    </article>

</div>

</body>
</html>
