$(document).ready(function(){
	$(".logo-nav").load("header.html");
	$(".footer").load("footer.html");
});
function iselect(){
	var count = parseInt($(".all_sum_money").text());
	var ids = document.getElementsByName("newslist");                           
    for(var i=0;i<ids.length;i++){
        if(ids[i].checked){
        	ids[i].checked = false;
        	var sum = $(ids[i]).parent().parent().find(".sum").text();
        	$(ids[i]).parent().parent().find(".checkbox_val").val(0);
        	count -= parseInt(sum);
        }else {
        	ids[i].checked = true;
        	$(ids[i]).parent().parent().find(".checkbox_val").val(1);
        	var sum = $(ids[i]).parent().parent().find(".sum").text();
        	count += parseInt(sum);
        }
    }
    $(".all_sum_money").html(count);
}
function news_list(thisid){
	var count = parseInt($(".all_sum_money").text());
	var sum = $(thisid).parent().parent().find(".sum").text();
	if(thisid.checked){
		$(thisid).parent().parent().find(".checkbox_val").val(1);
		count += parseInt(sum);
	}else {
		$(thisid).parent().parent().find(".checkbox_val").val(0);
		count -= parseInt(sum);
	}
	$(".all_sum_money").html(count);
}
function adds_count(thisid){
	var number_1 = parseInt($(thisid).parent().find(".number_1_value").val());
	var once = $(thisid).parent().parent().parent().find(".once").text();
	if(number_1 < 99){
		number_1++;
		var sum_once = once * number_1;
		$(thisid).parent().parent().parent().find(".sum").text(sum_once);
		$(thisid).parent().find(".number_1_value").val(number_1);
		if($(thisid).parent().parent().parent().find(".checkbox_val").val() == 1){
			var count = parseInt($(".all_sum_money").text());
			count = count + parseInt(once);
			$(".all_sum_money").html(count);
		}
	}else {
		alert("最多次数不能超过99");
	}
}
function minus_count(thisid){
	var number_1 = parseInt($(thisid).parent().find(".number_1_value").val());
	var once = $(thisid).parent().parent().parent().find(".once").text();
	if(number_1 > 1){
		number_1--;
		var sum_once = once * number_1;
		$(thisid).parent().parent().parent().find(".sum").text(sum_once);
		$(thisid).parent().find(".number_1_value").val(number_1);
		if($(thisid).parent().parent().parent().find(".checkbox_val").val() == 1){
			var count = parseInt($(".all_sum_money").text());
			count = count - parseInt(once);
			$(".all_sum_money").html(count);
		}
	}else {
		alert("最多次数不能小于1");
	}
}
/*
 * 首页
 * index_onload 首页加载
 * geolocation 定位当前浏览器
 * get_products 获取首页物品
 */
function index_onload(){
	if($.cookie("lng") == "" || $.cookie("lng") == undefined){
		geolocation();
	}
	
}
function geolocation(){
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r){
		if(this.getStatus() == BMAP_STATUS_SUCCESS){
			$.cookie("lng",r.point.lng);
			$.cookie("lat",r.point.lat);
		}
		else {
			alert('定位失败'+this.getStatus());
		}        
	},{enableHighAccuracy: true})
}
function get_products(){
	var lng = $.cookie("lng");
	var lat = $.cookie("lat");
	$.ajax({
		type:"post",
		url:"servlet/LoadGoods",
		async:true,
		data:{"pid":0,"page":0,"longitude":lng,"latitude":lat,"order":1},
		success:function(res){
			
		},
		error:function(res){
			
		}
	});
}
