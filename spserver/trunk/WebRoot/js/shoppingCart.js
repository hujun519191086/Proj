/**
 * 快速购买以及添加购物车
 */

function buy(gid,pid,buyCount){
	if (pid==0){
		alert("请先登录");
		return;
	}
	var isSuccess = addShopcart(gid, pid, buyCount);
	if(isSuccess == true){
		location = "ShopCart.html";
	}
}
function shopcart(gid,pid,buyCount){
	if (pid==0){
		alert("请先登录");
		return;
	}
	addShopcart(gid, pid, buyCount);
	$.ajax({
		type:"POST",
		url:"./servlet/LoadShoppingCart",
		async:false,
		data:{"pid":pid},
		success:function(res){
			var obj = JSON.parse(res);
			var length = obj.cartList.length;
			$(".badge").html(length);
		}
	});
}
function mainBuy(gid,pid){
	var buyCount = $(".tx")[0].value;
	buy(gid,pid,buyCount);
}
function mainShopcart(gid,pid){
	var buyCount = $(".tx")[0].value;
	shopcart(gid,pid,buyCount);
	
}
function addShopcart(gid,pid,buyCount){
	var isSuccess = false;
	$.ajax({
		type:"POST",
		url:"./servlet/Insert2ShoppingCart",
		async:false,
		data:{"pid":pid,"gid":gid,"buyCount":buyCount},
		success:function(res){
			var result = res;
			if(result == 99998)
				isSuccess = true;
		},
		error:function(res){
			alert("商品添加失败，请稍候重试");
		}
	});
	return isSuccess;
}
