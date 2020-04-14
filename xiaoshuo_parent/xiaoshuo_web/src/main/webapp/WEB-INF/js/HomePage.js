window.onload=function () {
    var btn1=document.getElementById('btn1');
    var btn2=document.getElementById('btn2');
    var btn3=document.getElementById('btn3');
    var btn4=document.getElementById('btn4');
    var btn5=document.getElementById('btn5');
    var btn6=document.getElementById('btn6');

    var model1=document.getElementById('model1');
    var model2=document.getElementById('model2');
    var model3=document.getElementById('model3');
    var model4=document.getElementById('model4');
    var model5=document.getElementById('model5');
    var model6=document.getElementById('model6');
    btn1.onclick=function () {
       model1.style.display="block";
        model2.style.display="none";
       // model1.style.display="none";
        model4.style.display="none";
        model3.style.display="none";
        model5.style.display="none";
        model6.style.display="none";
    }
    btn2.onclick=function () {
        model2.style.display="block";
        model1.style.display="none";
        model4.style.display="none";
        model3.style.display="none";
        model5.style.display="none";
        model6.style.display="none";

    }
    btn3.onclick=function () {
        model2.style.display="none";
        model1.style.display="none";
        model4.style.display="none";
        model3.style.display="block";
        model5.style.display="none";
        model6.style.display="none";
    }
    btn4.onclick=function () {
        model4.style.display="block";
        model2.style.display="none";
        model1.style.display="none";
        model3.style.display="none";
        model5.style.display="none";
        model6.style.display="none";
    }
    btn5.onclick=function () {
        model5.style.display="block";
        model2.style.display="none";
        model1.style.display="none";
        model4.style.display="none";
        model3.style.display="none";
        model6.style.display="none";
    }
    btn6.onclick=function () {
        model6.style.display="block";
        model2.style.display="none";
        model1.style.display="none";
        model4.style.display="none";
        model3.style.display="none";
        model5.style.display="none";
    }
}