$(document).ready(function () {

    $('#login_btn').on('click', function (e) {
        e.preventDefault();
        login();
    });

    function login(){
        $.ajax({
            type:"POST",
            url:"http://localhost:8080/BootSpringBoot/admin/login",
            dataType:"json",
            data:{
                'userName': $('#userName').val(),
                'pwd': $('#pwd').val()
            },
            success:function(data){
                if(data&&data.success==true){
                    alert("success")
                    window.location.href="index"
                }else{
                    $("#login_tips").html(data.msg);
                    $("#login_tips").show();
                }
            },
            error:function(jqXHR){
                console.log("接口异常"+jqXHR.status);
            }
        });
    }
    document.onkeydown = function (e) { // 回车提交表单
// 兼容FF和IE和Opera
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
        if (code == 13) {
            login();
        }
    }
});