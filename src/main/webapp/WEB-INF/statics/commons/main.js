/**
 * Created by zzf on 2016/12/29.
 */
/*bootstrap 确认选择框*/
var Common = {
    confirm:function(params){
        var model = $("#common_confirm_model");
        model.find(".title").html(params.title)
        model.find(".message").html(params.message)

        $("#common_confirm_btn").click()
        //每次都将监听先关闭，防止多次监听发生，确保只有一次监听
        model.find(".cancel").off("click")
        model.find(".ok").off("click")

        model.find(".ok").on("click",function(){
            params.operate(true)
        })

        model.find(".cancel").on("click",function(){
            params.operate(false)
        })
    }
}

var myAlert={
    showAndHide:function(objId,alertclas,alerttext){
        var thisObj = $("#"+objId);
        $(thisObj).attr("class","");
        $(thisObj).attr("class","alert "+alertclas);
        $(thisObj).find("strong").text(alerttext);
        $(thisObj).slideDown(500);
        setTimeout("$('#myAlert').slideUp(500);",3000);
    },
    hide:function(objId){
        $("#"+objId).slideUp(500);
    }
}