$(function(){
	//发运

	var carrierAjax = function() {
		$.ajax({
			type : "post",
			url : "/carrier/queryCarrier",
			dataType : 'json',
			data : '',
			success : function(data) {
				var carrierHtml = '';
				if (data.success) {
					var carrier = data.allResult.listCarrier;
					if(carrier.length != 0){
						for (var i = 0; i < carrier.length; i++) {
							carrierHtml = carrierHtml
									+ '<option value="'+carrier[i].id+'">'
									+ carrier[i].name + '</option>'
							//alert(carrierHtml);
						}
						carrierHtml = '<option value="0">请选择</option>'+carrierHtml;
						
					}else{
						carrierHtml = '<option value="0">请选择</option>'
					}
					$(".carrierSel").html(carrierHtml);
					
				} else {
					alert(data.msg);
				}
			}
		});
	};

	var driverAjax = function(carrierId) {
		if(carrierId == null || carrierId == 0){
			$(".driverDH").val("");
			$(".driverCP").val("");
			$(".driverSel").html("<option value='0'>请先选择承运人</option>");
			return;
		}
		$.ajax({
			type : "post",
			url : "/driver/queryDriver",
			dataType : 'json',
			data : 'carrierId=' + carrierId,
			success : function(data) {

				var driverHtml = '';
				if (data.success) {
					var driver = data.allResult.listDriver;
					if(driver.length != 0){
						$("#driverId").val(driver[0].id);
						//alert($("#driverId").val());
						for (var i = 0; i < driver.length; i++) {
							
							driverHtml = driverHtml
									+ '<option value="'+driver[i].id+'">'
									+ driver[i].name + '</option>'
							$(".driverDH").val(driver[0].contactWay);
							$(".driverCP").val(driver[0].licensePlateNumber);
						}
						driverList = driver;
						$(".driverSel").html(driverHtml);
					}else{
						$(".driverSel").html("<option value='0'>请先选择承运人</option>");
						$("#driverId").val("");
						$(".driverDH").val("");
						$(".driverCP").val("");
					}
					

				} else {
					alert(data.msg);
				}
			}
		});
	};
	//行发运
	$(".shipmentConfirm").click(
			function() {
				var id = $(this).attr("data-id");
				if(id == '0'){
					Alert("订单ID获取失败，请检查！");
				}
				carrierAjax();
				driverAjax($(".carrierSel").val());
				
				var conHtml = $("#carrierBox").html();
				Popup.start({
					width : 350,
					title : '温馨提示',
					content : conHtml,
					verification:true,
					saveFun : function() {
						if($("#driverId").val() == 0 || $("#driverId").val() == ''){
							alert("请选择一个司机！");
							return;
						}
						//alert($("#driverId").val());
						var data = 'idStr=["' + id + '"]&driverId='
								+ $("#driverId").val();
						
						$.ajax({
							type : "post",
							url : "/orderOut/shipmentOrderOut",
							dataType : 'json',
							data : data,
							success : function(data) {
								if (data.success) {
									$("#searchForm").submit();
									
								} else {
									alert(data.msg);
								}
							}
						});
					},
					hidefooterBar : true,
					hideheaderBar : false,
					btns : [ '确 定', '取 消' ]
				});

			});

	//批量发运确认
	$(".batchShipmentConfirm").click(function() {
		var ids = 'idStr=' + checkedArray();
		carrierAjax();
		driverAjax($(".carrierSel").val());

		var conHtml = $("#carrierBox").html();
		Popup.start({
			width : 350,
			title : '温馨提示',
			content : conHtml,
			verification:true,
			saveFun : function() {
				if($("#driverId").val() == 0 || $("#driverId").val() == ''){
					alert("请选择一个司机！");
					return;
				}
				//alert($("#driverId").val());
				var data = ids + '&driverId=' + $("#driverId").val();
				AjaxConfirm('/orderOut/shipmentOrderOut', data);
			},
			hidefooterBar : true,
			hideheaderBar : false,
			btns : [ '确 定', '取 消' ]
		});
	});

	$(".carrierSel").live('change', function() {
		var id = $(this).val();
		driverAjax(id);
	});
	$(".driverSel").live('change', function() {
		var id = $(this).val();
		$("#driverId").val(id);
		//alert($("#driverId").val());
		for (var i = 0; i < driverList.length; i++) {
			if (id == driverList[i].id) {
				$(".driverDH").val(driverList[i].contactWay);
				$(".driverCP").val(driverList[i].licensePlateNumber);
				return;
			}
			
		}
	});
});