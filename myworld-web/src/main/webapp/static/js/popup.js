/**
 * @author Administrator
 */
var Popup = (function(){
    var p_width = 300;
    var p_height = 200;
    var p_x = 0;
    var p_y = 0;
    var p_time = 5000;
    var conentHeight = 0;
    var formFun = null;
    var cancelFun = null;
    var callData = null;
    var isHeight = 1;

    var methods = {
        setLocation : function(data){
            var  width = 0, height = 0, win = 0;
            win = $(window);
            if(data.width > 300){
                width = data.width + 60;
            }else{
                width = 360;
            }
            if(data.height != undefined){
                height =data.height;
            }else{
                height = $(".n-popup-cont-content").height() +96;
            }



            $(".n-popup-cont").css({
                "top" : (win.height() - height) / 2,
                "left" : (win.width() - width) / 2,
                "width" : width,
                "height" :height
            });
        }
    }



    /**
     * 初始化
     */
    function initialize (data){


        $('body .n-popup').remove();//清理原有的
        var content = '';
        if(data.url){
            content = '<iframe src="'+data.url+'" frameborder="no" border="0" allowTransparency="true" ></iframe>';
        }else if(data.content){
            content = data.content;
        }else{
            content = '暂无内容';
        };

        var text1 = "确定";
        var text2 = "取消";
        var _btnView = '';
        if(data.btns){
            if(data.btns.length == 1){
                text1 = data.btns[0];
                _btnView = '<a href="#" class="save" >'+text1+'</a>';
            }else{
                text1 = data.btns[0];
                text2 = data.btns[1];
                _btnView = '<a href="#" class="save" >'+text1+'</a><a href="#" class="cancel" >'+text2+'</a>';
            };
        }else{
            _btnView = '<a href="#" class="save" >'+text1+'</a>';
        };

        var view =  '<div class="n-popup clearfix" style="'+(data.isrolling ? "position:absolute" : "")+'">'
            +'<div class="background" style="'+(data.isrolling ? "position:fixed" : "")+'">'
            +'</div><div class="n-popup-cont" >'
            +(data.hideheaderBar ? '' : '<div class="n-popup-cont-header"><p>'
            +data.title+'</p><a href="#" class="cancel colse"></a></div>')
            +'<div class="n-popup-cont-content  clear">'+content+'</div>'
            +'<div class="n-popup-cont-footer" '
            +(data.hidefooterBar ? '' : 'style="display: none;"')+'  >'+_btnView+'</div></div></div>';

        $('body').append(view);
        $(".n-popup-cont-content div").css("line-height", "normal");

        methods.setLocation(data);

        $(window).resize(function(){
            methods.setLocation(data);
        });

        eventListeners();
    };

    /**
     * 事件监听区
     */
    function eventListeners (){
        $('.n-popup .cancel').click(function(){
            if(cancelFun){
                cancelFun(callData);
            };
            $('.n-popup').remove();
            return false;
        });
        $('.n-popup .save').click(function(){
            if(formFun){
                formFun(callData);
            };
            //$('.n-popup').remove();
            return false;
        });
    };

    /**
     * 参数设置
     */
    this.start = function(obj){
        formFun = null;
        cancelFun = null;
        callData = null;
        if(obj.time){
            p_time = obj.time;
        }
        if(obj.cancelclick){
            var time = setTimeout(function () {
                clearTimeout(time);
                $('.n-popup .cancel').click();
            }, p_time);
        };
        if(obj.saveclick){
            var time =  setTimeout(function () {
                clearTimeout(time);
                $('.n-popup .save').click();
            }, p_time);
        };

        if(obj.saveFun){
            formFun = obj.saveFun;
        };
        if(obj.cancelFun){
            cancelFun = obj.cancelFun;
        };

        //拼装页面
        initialize(obj);
        if(!obj.verification){
            $('.n-popup .save').click(function(){
                colse();
                return false;
            });
        }
    };

    /**
     * 设置数据
     */
    this.setData = function(data){
        callData = data;
    };

    this.colse = function(){
        $('.n-popup').remove();
    };

    return this;
})();




function Alert(message, fun){
    var message;
    var fun;
    Popup.start({
        width:350,
        title   : '温馨提示',
        content : '<p class="p-warning">'+message+'</p>',
        saveFun: fun,
        hidefooterBar:true,
        hideheaderBar:false,
        btns : ['确 定']
    });
}

function Confirm(message, fun){
    var message;
    var fun;
    Popup.start({
        width:350,
        title   : '温馨提示',
        content : '<p class="p-warning">'+message+'</p>',
        saveFun: fun,
        hidefooterBar:true,
        hideheaderBar:false,
        btns : ['确 定','取 消']
    });
}

var Popup = window.Popup;
var Alert = window.Alert;
var Confirm = window.Confirm;





//回车键绑定事件

$(document).keyup(function(event){ 
    var keycode = event.which; 
    if(keycode==13){ 
    	if($(".save").length>0){
    		$(".save").click();
    	}else{
    		$(".a-submit,input[type='submit']").click();
    	}
   }
    
    
});  