$(document).ready(function () {

    // $('#add_article').on('click', function (e) {
    //     window.location.href="foodinput"
    // });

    ArticleJs.bindEdit();
    ArticleJs.bindDelete();
});

var ArticleJs={

    getData:function(params){
        $.ajax({
            type:"POST",
            url:"http://localhost:8080/BootSpringBoot/admin/search",
            dataType:"json",
            data:params,
            success:function(data){
                if(data&&data.code==100){
                    var htmlStr = ArticleJs.initHtml(data.data.list);
                    $("#article_list").html(htmlStr);
                    ArticleJs.bindEdit();
                    ArticleJs.bindDelete();
                }else{
                    alert("no such food")
                }
            },
            error:function(jqXHR){
                console.log("接口异常"+jqXHR.status);
            }
        });
    },
    initHtml:function (data) {
       var htmlStr="";
       for(var i=0;i<data.length;i++){
           var typeStr="";
           switch (data[i].type) {
               case 1:
                   typeStr="soup";
                   break;
               case 2:
                   typeStr="cold dishes";
                   break;
               case 3:
                   typeStr="main dishes";
                   break;
               case 4:
                   typeStr="dessert";
                   break;
           }
           htmlStr +=
               ' <tr >'+
               '<td>'+data[i].id+'</td>'+
               '<td>'+data[i].name+'</td>'+
               '<td>'+typeStr+'</td>'+
               '<td><button data-articleid="'+data[i].id+'"  type="button" class="btn btn-primary btn-sm edit-article "><i class="fa fa-edit"></i> &nbsp;编辑</button>&nbsp;&nbsp;&nbsp;&nbsp;'+
               '<button data-articleid="'+data[i].id+'"   type="button" class="btn btn-danger btn-sm delete-article"><i class="fa fa-trash"></i> &nbsp; 删除</button>'+
               '</td>'+
               '</tr>';
       }
        return htmlStr;
    },
    bindEdit:function(){
        $('.edit-article').off('click').on('click', function (e) {
            var articleId = $(this).attr("data-articleid");
            window.location.href="article?id="+articleId;
        });
    },
    bindDelete:function(){
        $('.delete-article').off('click').on('click', function (e) {
            var articleId = $(this).attr("data-articleid");
            // window.location.href="delete?id="+articleId;
            $.ajax({
                type:"POST",
                url:"http://localhost:8080/BootSpringBoot/admin/delete",
                dataType:"json",
                data:{'id':articleId},
                success:function(data){
                    if(data&&data=="success"){
                        alert("success")
                        window.location.href="index"
                    }else{
                        alert("no such food")
                    }
                },
                error:function(jqXHR){
                    alert("success")
                    window.location.href="index"
                    console.log("接口异常"+jqXHR.status);
                }
            });
        });
    }

}