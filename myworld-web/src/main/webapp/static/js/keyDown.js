//是否禁掉功能键,默认是否
var keyDisabled = false;


window.document.onkeydown = disableRefresh;
function disableRefresh(evt){
evt = (evt) ? evt : window.event
	//alert(evt.keyCode);
	if (evt.keyCode) {
		//确认键或下一步，后期调整。125键功能转回车键
		
	  /* if(evt.keyCode == 125 && $("#keyDown125").length > 0){
		   if(!keyDisabled){
			   document.getElementById("keyDown125").click();
		   }
	   }*/
	   //取消键或后退
	   if(evt.keyCode == 126 && $("#keyDown126").length > 0){
		   document.getElementById("keyDown126").click();
	   }
	   
	 //数字键，1~9
	   if(evt.keyCode == 49 && $("#keyDown49").length > 0){
		   document.getElementById("keyDown49").click();
		   //$("#keyDown49").click();
	   }
	   if(evt.keyCode == 50 && $("#keyDown50").length > 0){
		   document.getElementById("keyDown50").click();
	   }
	   if(evt.keyCode == 51 && $("#keyDown51").length > 0){
		   document.getElementById("keyDown51").click();
	   }
	   if(evt.keyCode == 52 && $("#keyDown52").length > 0){
		   document.getElementById("keyDown52").click();
	   }
	   if(evt.keyCode == 53 && $("#keyDown53").length > 0){
		   document.getElementById("keyDown53").click();
	   }
	   if(evt.keyCode == 54 && $("#keyDown54").length > 0){
		   document.getElementById("keyDown54").click();
	   }
	   if(evt.keyCode == 55 && $("#keyDown55").length > 0){
		   document.getElementById("keyDown55").click();
	   }
	   if(evt.keyCode == 56 && $("#keyDown56").length > 0){
		   document.getElementById("keyDown56").click();
	   }
	   if(evt.keyCode == 57 && $("#keyDown57").length > 0){
		   document.getElementById("keyDown57").click();
	   }
	  //光标键
	   if(evt.keyCode == 37 && typeof(params) !="undefined"){
		   paging(params,0);
	   }
	   if(evt.keyCode == 39 && typeof(params) !="undefined"){
		   paging(params,1);
	   }
	   //Enter键
	   if(evt.keyCode == 13 && $("#keyDown125").length > 0){
		   if(!keyDisabled){
			   //document.getElementById("keyDown13").click();
			   document.getElementById("keyDown125").click();
		   }
	   }
	}
	
	
	
}


//翻页事件，type:0为减，“其他”为加；
var paging=function(params,type){
	var _index = params.currentPage;
	if(type == 0){
		if(_index == 1){
			_index =params.totalCount
		}else{
			_index = _index-1;
		}
	}else{
		if(_index == params.totalCount){
			_index = 1;
		}else{
			_index = _index+1;
		}
	}
	window.location.href=params.href+'&index='+_index;
}

//默认光标事件
$(function(){
	if($(".defaultCursor").val() == ''){
		$(".defaultCursor").focus();
	}
	
	//调用限制
	$(".onlyNum").onlyNum();
	//获得焦点，全选input
	$("input[type=text]").focus(function(){
	  $(this).select();
	});
	
	
	
});


//----------------------------------------------------------------------
//限制输入
//----------------------------------------------------------------------
$.fn.onlyNum = function () {
$(this).keypress(function (event) {
	 
	 var fresh = $(this).attr("bind-fresh");
   var eventObj = event || e;
   var keyCode = eventObj.keyCode || eventObj.which;
   //0不为生鲜，1为生鲜
   if(fresh == 0){
  	 if ((keyCode >= 48 && keyCode <= 57 )){
  		 if($(this).val() == '' || /^\d{0,5}$/.test($(this).val())){
      		 return true;
      	 }else{
      		 return false;
      	 }
       }else{
           return false;
           } 
   }else{
  	 if ((keyCode >= 48 && keyCode <= 57 ||  keyCode == 46)){
  		 var keyNum = [{"key":46,"value":"."},{"key":48,"value":"0"},{"key":49,"value":"1"},{"key":50,"value":"2"},{"key":51,"value":"3"},{"key":52,"value":"4"},{"key":53,"value":"5"},{"key":54,"value":"6"},{"key":55,"value":"7"},{"key":56,"value":"8"},{"key":57,"value":"9"}];
  		 var keyNo;
  		 $.each(keyNum,function(i, itms){
  			 if(keyCode == itms.key){
  				 keyNo = itms.value;
  				 return;
  			 }
  		 });
  		 var value = $(this).val()+keyNo;
  		 console.log(value);
      	 if($(this).val() == '' || /^([0-9]{0,6})(\.\d{0,3})?$/.test(value)){
      		 return true;
      	 }else{
      		 return false;
      	 }
           
       }else{
           return false;
           } 
   }
   
}).focus(function () {
//禁用输入法
   this.style.imeMode = 'disabled';

});
};



