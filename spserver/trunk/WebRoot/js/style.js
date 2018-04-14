var ports_url = "http://yiminsea.com/";
$(document).ready(function(){
	
	$(".top").load(getRealPath()+"/public/top.html",function(){
		if(loginInfo.nickname!=undefined){
			$(".top-label-login").html("欢迎您，"+loginInfo.nickname);
			$(".top-label-register").html("<a href='#' onclick='loginout()'>[退出登录]</a>");
		}
	});
	$(".logo-nav").load(getRealPath()+"/public/header.html",function(){
		var page = getPar("page");
		$("."+page).addClass("active");
	});
	
	$(".footer").load(getRealPath()+"/public/footer.html");
	$(".nav-right").load(getRealPath()+"/public/navRight.html");
});
/*
 * 获取地址=之后的字符串
 */
function getPar(par){
    var local_url = document.location.href; 
    var get = local_url.indexOf(par +"=");
    if(get == -1){
        return false;   
    }   
    var get_par = local_url.slice(par.length + get + 1);  
    var nextPar = get_par.indexOf("&");
    if(nextPar != -1){
        get_par = get_par.slice(0, nextPar);
    }
    return get_par;
}
function iselect(){//全选
	var count = parseInt($(".all_sum_money").text());
	var ids = document.getElementsByName("newslist"); 
	var CheckAll=document.getElementById('All');
	count = 0;
    for(var i=0;i<ids.length;i++){
        if(CheckAll.checked==false){
        	ids[i].checked = false;
        	$(ids[i]).parent().parent().find(".checkbox_val").val(0);
        	count = 0;
        }else {
        	ids[i].checked = true;
        	$(ids[i]).parent().parent().find(".checkbox_val").val(1);
        	var sum = $(ids[i]).parent().parent().find(".sum").text();
        	count += parseInt(sum);
        }
    }
    $(".all_sum_money").html(count);
}
function news_list(thisid){//单选
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
function adds_count(thisid,needCount,pid,gid){//增加数量
	var number_1 = parseInt($(thisid).parent().find(".number_1_value").val());
	var once = $(thisid).parent().parent().parent().find(".once").text();
	if(number_1 < needCount){
		number_1++;
		var sum_once = once * number_1;
		if(updateBuyCount(pid,gid,1)){
			
			$(thisid).parent().find(".number_1_value").val(number_1);
		}
		else{
			alert("数据库更新失败，请稍候重试");
			return;
		}
		$(thisid).parent().parent().parent().find(".sum").text(sum_once);
		if($(thisid).parent().parent().parent().find(".checkbox_val").val() == 1){
			var count = parseInt($(".all_sum_money").text());
			count = count + parseInt(once);
			$(".all_sum_money").html(count);
		}
	}else {
		alert("最多次数不能超过剩余人次");
	}
}
function number_1_value(thisid,needCount,pid,gid){//当焦点离开时
	var number_1 = parseInt($(thisid).parent().find(".number_1_value").val());//写入的值
	var once = $(thisid).parent().parent().parent().find(".once").text();//单价
	var orgvalue = $(thisid).attr("orgvalue");
	
	var sum_once = once*orgvalue;
	var buyCount = orgvalue;
	
	if(!isNaN(number_1))
	{
		if(number_1>needCount||number_1<0){
			buyCount = needCount;
		}else{
			buyCount = number_1;
		}
	}else{
		buyCount = needCount;
	}
	sum_once = once*buyCount;
	
	if(updateBuyCount(pid,gid,buyCount-orgvalue)){
		
		$(thisid).parent().find(".number_1_value").val(buyCount);
		$(thisid).parent().parent().parent().find(".sum").text(sum_once);
		$(".all_sum_money").html(totalAmount()); 
	}else{
		alert("数据更新失败，请稍候重试");
	}
	
 };
 
 //总计
 function totalAmount(){
	 var trs = $("#myTable").children("tr");
	 var totalAmount = 0;
	 for(var i =0;i<trs.length;i++){
		 if(trs.eq(i).find(".newslist").is(":checked")){
			 totalAmount += parseInt(trs.eq(i).find(".sum").html());
		 }
	 }
	 return totalAmount;
 }
function minus_count(thisid,pid,gid){//减少数量
	var number_1 = parseInt($(thisid).parent().find(".number_1_value").val());
	var once = $(thisid).parent().parent().parent().find(".once").text();
	if(number_1 > 1){
		number_1--;
		var sum_once = once * number_1;
		if(updateBuyCount(pid,gid,-1)){
			
			$(thisid).parent().find(".number_1_value").val(number_1);
		}
		else{
			alert("数据库更新失败，请稍候重试");
			return
		}
		$(thisid).parent().parent().parent().find(".sum").text(sum_once);
		if($(thisid).parent().parent().parent().find(".checkbox_val").val() == 1){
			var count = parseInt($(".all_sum_money").text());
			count = count - parseInt(once);
			$(".all_sum_money").html(count);
		}
	}else {
		alert("最多次数不能小于1");
	}
}
/**
 * 修改购物车数据库购买数量
 */
function updateBuyCount(pid,gid,buyCount){
	var flag=false;//数据库操作成功标记
	$.ajax({
		type:"post",
		url:getRealPath()+"/servlet/Insert2ShoppingCart",
		async:false,
		data:{"pid":pid,"gid":gid,"buyCount":buyCount},
		success:function(res){
			flag=true;
		},
		error:function(res){
			flag=false;
		}
	});
	return flag;
}
/*
 * 首页
 * index_onload 首页加载
 * geolocation 定位当前浏览器
 * get_products 获取首页物品
 */
function index_onload(){
	geolocation(0);
}
function geolocation(page){
	var geolocation = new BMap.Geolocation();
	var geocoder = new BMap.Geocoder();
	var pid = loginInfo.pid;
	if(pid == undefined){
		pid=0;
	}
	geolocation.getCurrentPosition(function(r){
		if(this.getStatus() == BMAP_STATUS_SUCCESS){
			//根据经纬度得到县级位置
			geocoder.getLocation(new BMap.Point(r.point.lng,r.point.lat),function(rs){
				var addcomp = rs.addressComponents;
				//console.log(addcomp.district);
				get_products(pid,page,addcomp.province+addcomp.city+addcomp.district,1);
			});
			//console.log("经度:"+r.point.lng+"纬度:"+r.point.lat);
		}
		else {
			alert('定位失败'+this.getStatus());
		}        
	},{enableHighAccuracy: true});
}


/**
 * 
 * @param page
 * @param lng
 * @param lat
 * @param loc
 * 读取首页的商品信息
 */
function get_products(pid,page,loc,order){
	var pageCount=4;
	//todo pid获取 loc位置信息获取
	//loadGoods(pid,order,page,pageCount,targert)
	
	//人气商品
	loadGoods(pid,order,loc,page,pageCount,$("#body_1"));
	
	//最新揭晓
	LoadRencentPublish(pid,page,pageCount,$("#body_2_2"));
	
	//分类商品
	loadFLGoods(pid,page,loc,order,pageCount,200000,$("#body_3_2"));//手机数码
	loadFLGoods(pid,page,loc,order,pageCount,100000,$("#body_4_2"));//家用电器
	loadFLGoods(pid,page,loc,order,pageCount,700000,$("#body_5_2"));//服饰百货

	setCountDown();
	//设置添加购物车动画效果
	$(function(){
		$('.add-to-cart').click(function(){
			var flyElm = $(this).parent().parent().find("img").eq(0).clone().css('opacity','0.7');
			flyElm.css({
				'z-index': 9000,
				'display': 'block',
				'position': 'absolute',
				'top': $(this).parent().parent().find("img").eq(0).offset().top +'px',
				'left': $(this).parent().parent().find("img").eq(0).offset().left +'px',
				'width': $(this).parent().parent().find("img").eq(0).width() +'px',
				'height': $(this).parent().parent().find("img").eq(0).height() +'px'
			});
			$('body').append(flyElm);
			flyElm.animate({
				top:$('.shopping-cart').offset().top,
				left:$('.shopping-cart').offset().left,
				width:0,
				height:0,
			},'slow');
		});
	});
	
}


//首页分类菜单
function drawMenu_index(){
	var obj = loadCatelogs();
	var length = obj.catelog_list.length;
	var html='';
	for(var i=0;i<length;i++){
		var catelogId = obj.catelog_list[i].catelogId;
		var catelogName = obj.catelog_list[i].catelogName;
		html += '<li><a href="page.html?catelog_id='+catelogId+'">'+catelogName+'<span class="sf">></span>'+'</a></li>';
	}
	$("#lb").html(html);
}

//二级页分类菜单

function drawMenu_page(realCatelogId){
	var obj = loadCatelogs();
	var length = obj.catelog_list.length;
	var lll='';
	for(var i=0;i<length;i++){
		var catelogId = obj.catelog_list[i].catelogId;
		var catelogName = obj.catelog_list[i].catelogName;
		var img = obj.catelog_list[i].img;
		
		lll += '<li><a href="page.html?catelog_id='+catelogId+'"><img src="'+img+'" /><br>'+catelogName+'</a></li>';
	}
	$(".cont_1_3_1").html(lll);
	
	//显示商品总数
	var cur_catelog_id = getParameter("catelog_id");
	var zs = loadGoodszs(cur_catelog_id);
	$(".spsl").html(zs);
	
	
	var catelog = "所有商品";
	var catelogItems = obj.catelog_list;
	for(var j=0;j<length;j++){
	    if(catelogItems[j].catelogId==realCatelogId){
	        catelog = catelogItems[j].catelogName;
	    }
	}
	$(".flqy").html(catelog);
	
	//分页
//	var fy=Math.ceil(zs/10);
//	$(".allPage").html(fy);
//	$("#Pagination").pagination(fy);
}


//获取分类,返回分类对象数组
function loadCatelogs(){
	var obj = null;
	$.ajax({
		type:"POST",
		url:getRealPath()+"/servlet/LoadCatelogs",
		async:false,
		success:function(res){
			obj = JSON.parse(res);
		},
		error:function(res){
			//alert("获取数据出错,请稍后再进入");
		}
	});
	return obj;
	
}
function loadGoods(pid,order,loc,page,pageCount,target){
	$.ajax({
		type:"POST",
		url:getRealPath()+"/servlet/LoadGoods",
		async:false,
		data:{"pid":pid,"order":order,"location":loc,"page":page,"pageCount":pageCount},
		success:function(res){
			var obj = JSON.parse(res);
			var length = obj.goods_list.length;
			var html='';
			var d3='';
			//最新推荐
			var gid = obj.goods_list[0].gid;
			var goods_img = obj.goods_list[0].goods_img;
			var dis_imgs = JSON.parse(obj.goods_list[0].dis_imgs);
			var goods_name = obj.goods_list[0].goods_name;
			var totalCount = obj.goods_list[0].totalCount;
			var needCount = obj.goods_list[0].needCount;
			var tobuy = totalCount - needCount;
			var progress = obj.goods_list[0].progress;
			var buyCount = 1;//立即购买默认购买1人次
			//html += '<div class="body-div">';
			d3 += '<img alt="图片" src="img/xptj.jpg">';
			d3 += '<div class="body-div-img jz">';
			d3 += '<a href="details.html?gid='+gid+'">';
			d3 += '<img src='+goods_img+'/'+dis_imgs[0]+' style="width:100%;height:100%;"/>';
			d3 += '</a>';
			d3 += '</div>';
			d3 += '<p class="title">'+goods_name+'</p>';
			d3 += '<p class="desc">总需:'+totalCount+'人次 </p>';
			d3 += '<div class="progress" style="height: 12px;margin-bottom:5px">';
			d3 += '<div class="progress-bar color-red" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width: '+progress+'%"></div>';
			d3 += ' </div>';
			d3 += '</div>';
			d3 += '<p><span class="pull-left">'+tobuy+'</span><span class="pull-right">'+needCount+'</span></p>';
			d3 += '<div class="blank"></div>';
			d3 += '<p><span class="pull-left">已参加人次</span><span class="pull-right">剩余人次</span></p>';				
			d3 += '<div class="zx"><span class="ljgm" onclick="buy(\''+gid+'\',\''+pid+'\','+buyCount+')">立即购买</span></div>';
			//新品推荐end
			for(var i=0;i<length;i++){
				var gid = obj.goods_list[i].gid;
				var goods_img = obj.goods_list[i].goods_img;
				var dis_imgs = JSON.parse(obj.goods_list[i].dis_imgs);
				var goods_name = obj.goods_list[i].goods_name;
				var totalCount = obj.goods_list[i].totalCount;
				var needCount = obj.goods_list[i].needCount;
				var tobuy = totalCount - needCount;
				var progress = obj.goods_list[i].progress;
				var buyCount = 1;//立即购买默认购买1人次
				//html += '<div class="body-div">';
				html += '<div class="body-div" data-gid="'+gid+'" data-pid="'+pid+'" data-buyCount="'+buyCount+'">';
				html += '<div class="body-div-img">';
				html += '<a href="details.html?gid='+gid+'">';
				html += '<img src='+goods_img+'/'+dis_imgs[0]+'>';
				html += '</a></div>';
				html += '<p class="title">'+goods_name+'</p>';
				html += '<p class="desc">总需:'+totalCount+'人次 </p>';
				html += '<div class="progress">';
				html += '<div class="progress-bar color-red" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width: '+progress+'%"></div>';
				html += '</div>';
				html += '<p><span class="pull-left">'+tobuy+'</span><span class="pull-right">'+needCount+'</span></p>';
				html += '<div class="blank"></div>';
				html += '<p><span class="pull-left">已参加人次</span><span class="pull-right">剩余人次</span></p>';
				html += '<p class="body-div-button">';
				html += '<button class="button button-caution button-buy" type="button" onclick="buy(\''+gid+'\',\''+pid+'\','+buyCount+')">立即购买</button>';
				html += '<button class="button button-highlight button-shopcart add-to-cart" type="button" onclick="shopcart(\''+gid+'\',\''+pid+'\','+buyCount+')"><img src="img/gouwuche.png"></button>';
				html += '</p></div>';
			};
			target.html(html);
			$(".d3").html(d3);
		},
		error:function(res){
			//alert("商品获取数据出错,请稍后再进入");
		}
	});
}

//获取商品总数
function loadGoodszs(catelog_id){
	var length = 0;
	$.ajax({
		type:"POST",
		url:getRealPath()+"/servlet/LoadGoods",
		async:false,
		data:{"pid":11,"catelog_id":catelog_id},
		success:function(res){
			var obj = JSON.parse(res);
			length = obj.goods_list.length;
		},
		error:function(res){
			//alert("商品件数获取数据出错,请稍后再进入");
		}
	});
	return length;
}
//读取最新揭晓的商品
function LoadRencentPublish(pid,page,pageCount,target){
	$.ajax({
		type:"POST",
		url:getRealPath()+"/servlet/LoadRecentPublish",
		async:false,
		data:{"pid":pid,"page":page,"pageCount":pageCount},
		success:function(res){
			var obj = JSON.parse(res);
			var length = obj.pub_his_list.length;
			var zxjx='';
			for(var i=0;i<length;i++){
				var gid = obj.pub_his_list[i].gid;
				var goods_img = obj.pub_his_list[i].goods_img;
				var dis_imgs = JSON.parse(obj.pub_his_list[i].dis_imgs);
				var goods_name = obj.pub_his_list[i].goods_name;
				var totalCount = obj.pub_his_list[i].totalCount;
				var cur_time = obj.pub_his_list[i].cur_time;
				var pub_time = obj.pub_his_list[i].pub_time;
				var period = obj.pub_his_list[i].period;
				//时间格式
				zxjx += '<div class="body-div rec_pub" style="padding:0px;" cur_time='+cur_time+' pub_time='+pub_time+' gid='+gid+' period='+period+'>';
				zxjx +='<img src="img/zzjx.png" class="zzjx"/>';
				zxjx += '<div class="body-div-img pad">';
				zxjx += '<a href="announcement.html?gid='+gid+'&period='+period+'" class="zxpt">';
				zxjx += '<img src='+goods_img+'/'+dis_imgs[0]+'>';
				zxjx += '</a></div>';
				zxjx += '<p class="title pad">'+goods_name+'</p>';
				zxjx += '<p class="desc pad">总需:'+totalCount+'人次 </p>';
				zxjx += '<p class="desc pad">期号:第'+period+'期</p>';
				zxjx += '<div class="jxsj">';
				zxjx += '揭晓倒计时&nbsp;&nbsp;<span class="minute_1"></span>';
				zxjx += '<span class="minute_2"></span>&nbsp;:&nbsp;';
				zxjx += '<span class="second_1"></span>';
				zxjx += '<span class="second_2"></span>&nbsp;:&nbsp;';
				zxjx += '<span class="millisec_1"></span>';
				zxjx += '<span class="millisec_2"></span>';
				zxjx += '</div>';
				zxjx += '<div class="jxsj_1" style="display:none">';
				zxjx += '</div>';
				zxjx += '</div>';
			};
			target.html(zxjx);
		},
		error:function(res){
			//alert("最新揭晓获取数据出错,请稍后再进入");
		}
	});
}
//读取分类下的商品
function loadFLGoods(pid,page,loc,order,pageCount,catelog_id,target){
	$.ajax({
		type:"POST",
		url:getRealPath()+"/servlet/LoadGoods",
		async:false,
		data:{"pid":pid,"page":page,"loc":loc,"order":order,"pageCount":pageCount,"catelog_id":catelog_id},
		success:function(res){
			var obj = JSON.parse(res);
			var length = obj.goods_list.length;
			var sjsm='';
			for(var i=0;i<length;i++){
				var gid = obj.goods_list[i].gid;
				var goods_img = obj.goods_list[i].goods_img;
				var dis_imgs = JSON.parse(obj.goods_list[i].dis_imgs);
				var goods_name = obj.goods_list[i].goods_name;
				var totalCount = obj.goods_list[i].totalCount;
				var needCount = obj.goods_list[i].needCount;
				var tobuy = totalCount - needCount;
				var buyCount = 1;//立即购买默认购买1人次
				var progress = obj.goods_list[i].progress;
				sjsm += '<div class="body-div" data-gid="'+gid+'" data-pid="'+pid+'" data-buyCount="'+buyCount+'">';
				sjsm += '<div class="body-div-img">';
				sjsm += '<a href="details.html?gid='+gid+'">';
				sjsm += '<img src='+goods_img+'/'+dis_imgs[0]+'>';
				sjsm += '</a></div>';
				sjsm += '<p class="title">'+goods_name+'</p>';
				sjsm += '<p class="desc">总需:'+totalCount+'人次 </p>';
				sjsm += '<div class="progress">';
				sjsm += '<div class="progress-bar color-red" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width: '+progress+'%"></div>';
				sjsm += '</div>';
				sjsm += '<p><span class="pull-left">'+tobuy+'</span><span class="pull-right">'+needCount+'</span></p>';
				sjsm += '<div class="blank"></div>';
				sjsm += '<p><span class="pull-left">已参加人次</span><span class="pull-right">剩余人次</span></p>';
				sjsm += '<p class="body-div-button">';
				sjsm += '<button class="button button-caution button-buy" type="button" onclick="buy(\''+gid+'\',\''+pid+'\','+buyCount+')">立即购买</button>';
				sjsm += '<button class="button button-highlight button-shopcart add-to-cart" type="button" onclick="shopcart(\''+gid+'\',\''+pid+'\','+buyCount+')"><img src="img/gouwuche.png"></button>';
				sjsm += '</p></div>';
			};
			target.html(sjsm);
		},
		error:function(res){
			//alert("分类商品获取数据出错,请稍后再进入");
		}
	});
}


function getRealPath(){
	  var curWwwPath=window.document.location.href;
	  var pathName=window.document.location.pathname;
	  var pos=curWwwPath.indexOf(pathName);
	  var localhostPaht=curWwwPath.substring(0,pos);
	  var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	  var realPath=localhostPaht+projectName;
	  return realPath;
}
//获取url参数
function getParameter(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}
//倒计时
function count_down(time_end,target,gid,period) {
	var int_minute, int_second, int_millisec;//取整
	var time_delay = 1000;//1000毫秒延迟
	var time_distance = time_end - new Date().getTime()+time_delay;
	
	if (time_distance >= 0) {
		//相减的时间换成分钟
		int_minute = Math.floor(time_distance / 60000);
		time_distance -= int_minute * 60000;

		//相减的时间换成分钟

		int_second = Math.floor(time_distance / 1000);

		time_distance -= int_second * 1000;

		// 相减的差数换算成秒数 

		int_millisec = Math.floor(time_distance / 10);
		
		
		// 显示倒计时效果      
	    
		var time_minute1 = target.getElementsByClassName("minute_1")[0];
		var time_minute2 = target.getElementsByClassName("minute_2")[0];
		var time_second1 = target.getElementsByClassName("second_1")[0];
		var time_second2 = target.getElementsByClassName("second_2")[0];
		var time_millisec1 = target.getElementsByClassName("millisec_1")[0];
		var time_millisec2 = target.getElementsByClassName("millisec_2")[0];
		
		time_minute1.innerHTML = parseInt(int_minute / 10);
		time_minute2.innerHTML = int_minute % 10;
		time_second1.innerHTML = parseInt(int_second / 10);
		time_second2.innerHTML = int_second % 10;
		time_millisec1.innerHTML = parseInt(int_millisec / 10);
		time_millisec2.innerHTML = int_millisec % 10;
		
		setTimeout(function(){count_down(time_end, target ,gid ,period);}, 100);
	}else{
		$.ajax({
			type:"POST",
			url:getRealPath()+"/servlet/LoadPubHistory",
			async:false,
			data:{"gid":gid,"period":period},
			success:function(res){
				var obj = JSON.parse(res);
				var zxjx='';
				
				var pub_his  = obj.pub_his_list[0];
				var nickname = pub_his.nickname;
				var lucky_code = pub_his.lucky_code;
				var buyCount = pub_his.buyCount;
				var pub_time = pub_his.pub_time;
				var format = "yyyy-MM-dd hh:mm:ss";
				zxjx += '<p>恭喜<span class="yhm">'+nickname+'</span><span class="dz"></span>获得该商品</p>';
				zxjx += '<p>幸运号码:<span class="renc">'+lucky_code+'</span></p>';
				zxjx += '<p>本期参与:<span class="renc">'+buyCount+'</span>人次</p>';
				zxjx += '<p>揭晓时间:'+formatDate(new Date(parseInt(pub_time)), format)+'</p>';
				
				target.getElementsByClassName("jxsj")[0].style.display = "none";
				target.getElementsByClassName("jxsj_1")[0].style.display = "";
				target.getElementsByClassName("jxsj_1")[0].innerHTML =zxjx;
			},
			error:function(res){
				alert("揭晓数据出错,请刷新页面");
			}
		});
	}
}
function setCountDown(){
	var objs = $(".rec_pub");
	for(var i=0;i<objs.length;i++){
		var cur_time = objs[i].attributes["cur_time"].value;
		var pub_time = objs[i].attributes["pub_time"].value;
		var gid = objs[i].attributes["gid"].value;
		var period = objs[i].attributes["period"].value;
		var time_diff = cur_time-new Date().getTime();//服务器与客户端时间差
		var time_end = pub_time - time_diff;
		count_down(time_end,objs[i],gid,period);
	}
}

function formatDate(date,format){
	var o = {
		"M+" : date.getMonth() + 1, // month
		"d+" : date.getDate(), // day
		"h+" : date.getHours(), // hour
		"m+" : date.getMinutes(), // minute
		"s+" : date.getSeconds(), // second
		"q+" : Math.floor((date.getMonth() + 3) / 3), // quarter
		"S" : date.getMilliseconds()
	// millisecond
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (date.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
 
}
function updatePassword(pid,oldPassword,newPassword){
	$.ajax({
		type:"POST",
		url:getRealPath()+"/servlet/UpdatePassword",
		async:false,
		data:{"pid":pid,"oldPassword":oldPassword,"newPassword":newPassword},
		success:function(res){
			var obj = JSON.parse(res);
			if(obj.errorCode == 99998){
				alert("密码修改成功");
			}
			else{
				alert(obj.errorMsg);
			}
		},
		error:function(res){
			alert("修改密码失败，请稍候重试");
		}
	});
}
function oldPhoneSend(pid){
	//原手机发送验证码
	$.ajax({
		type:"POST",
		url:getRealPath()+"/servlet/UpdatePhoneNum",
		async:false,
		data:{"pid":pid,"eventId":10601},
		success:function(res){
			var obj = JSON.parse(res);
			if(obj.errorCode == 99998){
				alert("验证码发送成功");
			}
			else{
				alert(obj.errorMsg);
			}
		},
		error:function(res){
			alert("验证码发送失败，请稍候重试");
		}
	});
}
function phoneSecurityCheck(pid){
	//绑定手机修改前检测验证
	var strSec = getParameter("strSec");
	$.ajax({
		type:"POST",
		url:getRealPath()+"/servlet/PhoneSecurityCheck",
		async:false,
		data:{"pid":pid,"strSec":strSec},
		success:function(res){
			var obj = JSON.parse(res);
			if(obj.errorCode == 99998){
				alert("验证码发送成功");
			}
			else{
				alert(obj.errorMsg);
			}
		},
		error:function(res){
			alert("验证码发送失败，请稍候重试");
		}
	});
	
}
function mailSecurityCheck(pid){
	//绑定邮箱修改前检测验证
	var strSec = getParameter("strSec");
	$.ajax({
		type:"POST",
		url:getRealPath()+"/servlet/UpdatePassword",
		async:false,
		data:{"pid":pid,"oldPassword":oldPassword,"newPassword":newPassword},
		success:function(res){
			var obj = JSON.parse(res);
			if(obj.errorCode == 99998){
				alert("密码修改成功");
			}
			else{
				alert(obj.errorMsg);
			}
		},
		error:function(res){
			alert("修改密码失败，请稍候重试");
		}
	});
}

