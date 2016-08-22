$(function(){
	
	//TAB切换功能
	$(".tab-box-t2 a").click(function(){
		var i = $(".tab-box-t2 a").index(this);
		$(".tab-box-t2 a").removeClass("on");
		$(this).addClass("on");
		$(".search-form ul").hide();
		$(".search-form ul").eq(i).show();
	});
	
	//重置Form表单的select和input标签
	$("#reset").click(function(){
			$("#searchForm select").each(function(index, domEle){
				domEle.options[0].selected = true;		
			});
			$("#searchForm input[type='text']").each(function(index, domEle){
				domEle.value = '';		
			});
			$("#searchForm input[type='checkbox']").each(function(index, domEle){
				domEle.checked = false;		
			});
	});
	
	//返回
	$("#goBack").click(function(){
		window.history.back();
	});
	
	//checkbox多选、单选、全选、反选
	$(".AllCheckbox").click(function(){
		if($(this).is(':checked')){
			$(".danCheckbox").attr("checked",true);
		}else{
			$(".danCheckbox").attr("checked",false);
		}
	});
	$(".danCheckbox").click(function(){
		if($(this).is(':checked')){
			var flag = true;
			$(".danCheckbox").each(function(){
				if(!$(this).is(':checked')){
					flag = false;
					return;
				};
			});
			if(flag){
				$(".AllCheckbox").attr("checked",true);	
			}
		}else{
			$(".AllCheckbox").attr("checked",false);	
		}
	});
	
	$(".onlyNum").onlyNum();
	$(".onlyStockAdjust").onlyStockAdjust();
});
//ajax方法
var AjaxConfirm=(function(url,data,refresh){
	$.ajax({ 
		type: "post", 
		url : url, 
		dataType:'json',
		data: data, 
		success: function(data){
			if(data.success){
				//alert(data.msg);
				if(refresh == 0){
					window.location.reload(); 
				}else{
					$("#searchForm").submit();
				}
				
			}else{
				Alert(data.msg,function(){
					$("#searchForm").submit();
					window.location.reload(); 
				});
			}
		} 
		}); 
});

//取所有选中的checkbox的value值
var checkedArray=(function checkedArray(){
	var ids = [];
	$(".danCheckbox").each(function(){
		if($(this).is(':checked')){
			var id = $(this).val();
			ids.push(id);
		};
	});
	return JSON.stringify(ids);
});

var searchForm=function(i,fresh){
	$("[name='currentPage']").val("1");
	$("#listState").val("["+i+"]");
	if(typeof(fresh) == "undefined"){
		$("#fresh").val("");
	}else{
		$("#fresh").val(fresh);
	}
	
	$("#indexPage").val(1);
	$("#searchForm").submit();
}
var searchForm = window.searchForm;


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
$.fn.onlyStockAdjust = function () {
	$(this).keypress(function (event) {
		
		var fresh = $(this).attr("bind-fresh");
		var eventObj = event || e;
		var keyCode = eventObj.keyCode || eventObj.which;
		//0不为生鲜，1为生鲜
		if(fresh == 0){
			if ((keyCode >= 48 && keyCode <= 57 || keyCode == 45)){
				if($(this).val() == '' || /^([-]?)(\d{0,5})$/.test($(this).val())){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			} 
		}else{
			if ((keyCode >= 48 && keyCode <= 57 ||  keyCode == 46 || keyCode == 45)){
				var keyNum = [{"key":45,"value":"-"},{"key":46,"value":"."},{"key":48,"value":"0"},{"key":49,"value":"1"},{"key":50,"value":"2"},{"key":51,"value":"3"},{"key":52,"value":"4"},{"key":53,"value":"5"},{"key":54,"value":"6"},{"key":55,"value":"7"},{"key":56,"value":"8"},{"key":57,"value":"9"}];
				var keyNo;
				$.each(keyNum,function(i, itms){
					if(keyCode == itms.key){
						keyNo = itms.value;
						return;
					}
				});
				var value = $(this).val()+keyNo;
				console.log(value);
				if($(this).val() == '' || /^(([-]?)[0-9]{0,6})(\.\d{0,3})?$/.test(value)){
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


	//限制的输入框Class 要
	/*$(function(){
		$(".onlyNum").keyup(function(){
			$(this).attr("maxlength",10);
			var val = $(this).val();
			var fresh = $(this).attr("bind-fresh");
			var rule;
			if(fresh == 1){
				//rule= /^([1-9][0-9]+|[0-9])(\.\d{1,2})?$/;
				rule= /[^\d{1,}\.\d{1,}|\d{1,}]/g;
			}else if(fresh == 0){
				rule= /[^\d]/g;
			}
			console.log(!rule.test(val));
			if(!rule.test(val)){
				val=val.replace(/[^\d]/g,'')
			}
			$(this).val(val);
			
			
		});
	});*/
	//========================


