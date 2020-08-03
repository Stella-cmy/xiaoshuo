window.onload=function () {
    var pinglun_btn=document.getElementById('pinglun_btn');
    var pinglun=document.getElementById('pinglun');
    pinglun_btn.onclick=function () {
        pinglun.style.display='block';
    }
    var seepinglun_btn=document.getElementById("seepinglun_btn");
    var allComments=document.getElementById("allComments");
    seepinglun_btn.onclick=function () {
        allComments.style.display='block';
    }
}