/**
 * 登录，注册
 */
$(".button_phone_register").click(function(){
	$(".button_phone_register").addClass("actives");
	$(".button_email_register").removeClass("actives");
	$(".tab_1").css("display","block");
	$(".tab_2").css("display","none");
});
$(".button_email_register").click(function(){
	$(".button_email_register").addClass("actives");
	$(".button_phone_register").removeClass("actives");
	$(".tab_2").css("display","block");
	$(".tab_1").css("display","none");
});
var loginInfo = {"nickname":"","pid":""};//登录信息 
loadLoginInfo();

/*
 * 登录 
 * autoLogin 自动登录
 * login 登录
 */
var isAutoLogin = "0";
function autoLogin() {
	var isChecked = $('#auto_login').is(':checked');
	if (isChecked == true) {
		isAutoLogin = "1";
	} else {
		isAutoLogin = "0";
	}
}
function login(){
	var account = $("#account").val();
	var pwd = $("#password").val();
	if(account == "" || pwd == ""){
		alert("账户和密码不能为空");
		return false;
	}
	if(isPhoneNum(account)){
		$.ajax({
			type:"post",
			url:getRealPath()+"/servlet/LoginInfoServlet",
			async:false,
			data:{"phonenum":account,"pass":pwd,"autoLogin":isAutoLogin},
			success:function(res){
				var obj = JSON.parse(res);
				if(obj.errorCode == "99998"){
					$.cookie("pid",obj.pid,{expires:30});
					$.cookie("nickname",obj.nickname,{expires:30});
					window.location.href = "index.html";
				}else if(obj.errorCode == "20201"){
					alert(obj.errorMsg);
					return false;
				}else if(obj.errorCode == "20202"){
					alert(obj.errorMsg);
					return false;
				}else {
					alert(obj.errorMsg);
					return false;
				}
			},
			error:function(res){
				alert("数据提交出错，请稍后再登录");
			}
		});
	}
	else if(isEmail(account)){
		
		$.ajax({
			type:"post",
			url:getRealPath()+"/servlet/LoginInfoServlet",
			async:false,
			data:{"emailName":account,"pass":pwd,"autoLogin":isAutoLogin},
			success:function(res){
				var obj = JSON.parse(res);
				if(obj.errorCode == "99998"){
					$.cookie("pid",obj.pid,{expires:30});
					$.cookie("nickname",obj.nickname,{expires:30});
					window.location.href = "index.html";
				}else if(obj.errorCode == "20201"){
					alert(obj.errorMsg);
					return false;
				}else if(obj.errorCode == "20202"){
					alert(obj.errorMsg);
					return false;
				}else {
					alert(obj.errorMsg);
					return false;
				}
			},
			error:function(res){
				alert("数据提交出错，请稍后再登录");
			}
		});
	}
	
}
/*
 * 注册
 * checkStatus 是否同意协议
 * settime 倒计时
 * onMailBlur 邮箱验证
 * sendVerificationCode 发送验证码
 * register 注册
 */
function checkStatus(thisid){
	var isChecked =$(thisid).is(":checked");
	if(isChecked == true){
		$(thisid).parents(".form-group").find("#sub_register").attr("disabled", false);
	}else{
		$(thisid).parents(".form-group").find("#sub_register").attr("disabled", true);
	}
}
var countdown = 60;
function settime(val) {
	if (countdown == 0) {
		val.removeAttribute("disabled");
		val.value = "发送验证码";
		countdown = 60;
		return null;
	} else {
		val.setAttribute("disabled", true);
		val.value =  countdown + "秒后重新发送";
		countdown--;
	}
	t = setTimeout(function() {
		settime(val);
	}, 1000);
}
function onMailBlur() {
	var email=$('#reg_email').val();
	if(email==""){
		alert("邮箱不能为空");
	    return false;
	}else{
	    if(!isEmail(email)){
	        alert("邮箱格式错误");
	        return false;
	    } else{
	    	return true;
	    }
	}

}
function onPhoneBlur(){
	var phone = $('#reg_phone').val();
	if(phone==""){
		alert("手机号不能为空");
		return false;
	}
	else{
		if(!isPhoneNum(phone)){
			
			alert("请填写正确的手机号");
			return false;
		}
		else{
			return true;
		}
	}
}
function isEmail(email){
    reg=/^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/gi;
    if(!reg.test(email)){
        return false;
    } else{
    	return true;
    }
}
function isPhoneNum(phone){
	reg=/^1[34578]\d{9}$/;
	if(!reg.test(phone)){
		return false;
	}
	else{
		return true;
	}
}
function sendVerificationCode(sendValue,mode){
	//mode 注册方式  1 手机注册  2 邮箱注册 
	if(mode==1){
		var checkPhone = onPhoneBlur();
		if (!checkPhone) {
			return;
		}
		var phone = $("#reg_phone").val();
		$.ajax({
			type:"post",
			url:getRealPath()+"/servlet/RegisterServlet",
			async:false,
			data:{"phoneNum":phone,"eventId":10401},
			success:function(res){
				settime(sendValue);
			},
			error:function(res){
				alert("数据提交出错，请稍后再登录");
			}
		});
	}
	else if(mode==2){
		var checkEmail = onMailBlur();
		if (!checkEmail) {
			return;
		}
		var email = $("#reg_email").val();
		$.ajax({
			type:"post",
			url:getRealPath()+"/servlet/RegisterServlet",
			async:false,
			data:{"emailName":email,"eventId":10001},
			success:function(res){
				settime(sendValue);
			},
			error:function(res){
				alert("数据提交出错，请稍后再登录");
			}
		});
	}
}
function register(thisid,mode){
	if(mode==1){//手机注册
		var checkPhone = onPhoneBlur();
		if(!checkPhone){
			return;
		}
		var phoneNum = $('#reg_phone').val();
		var pwd = $(thisid).parents(".form-group").find("#reg_password").val();
		var code = $(thisid).parents(".form-group").find("#reg_code").val();
		$.ajax({
			type:"post",
			url:getRealPath()+"/servlet/RegisterServlet",
			async:false,
			data:{"phoneNum":phoneNum,"pass":pwd,"VerificationCode":code,"eventId":10101},
			success:function(res){
				var obj = JSON.parse(res);
				if(obj.errorCode == "99998"){
					$.cookie("pid",obj.pid,{expires:30});
					$.cookie("nickname",obj.nickname,{expires:30});
					window.location.href = "index.html";
				}else if(obj.errorCode == "20102"){
					alert(obj.errorMsg);
					return false;
				}else if(obj.errorCode == "20103"){
					alert(obj.errorMsg);
					return false;
				}else if(obj.errorCode == "20104"){
					alert(obj.errorMsg);
					return false;
				}else if(obj.errorCode == "20105"){
					alert(obj.errorMsg);
					return false;
				}else {
					alert(obj.errorMsg);
					return false;
				}
			},
			error:function(res){
				alert("数据提交出错，请稍后再注册");
			}
		});

	}
	else if(mode==2){//邮箱注册
		
		var checkEmail = onMailBlur();
		if(!checkEmail){
			return;
		}
		var email = $('#reg_email').val();
		var pwd = $(thisid).parents(".form-group").find("#reg_password").val();
		var code = $(thisid).parents(".form-group").find("#reg_code").val();
		$.ajax({
			type:"post",
			url:getRealPath()+"/servlet/RegisterServlet",
			async:false,
			data:{"emailName":email,"pass":pwd,"VerificationCode":code,"eventId":10101},
			success:function(res){
				var obj = JSON.parse(res);
				if(obj.errorCode == "99998"){
					$.cookie("pid",obj.pid,{expires:30});
					$.cookie("nickname",obj.nickname,{expires:30});
					window.location.href = "index.html";
				}else if(obj.errorCode == "20102"){
					alert(obj.errorMsg);
					return false;
				}else if(obj.errorCode == "20103"){
					alert(obj.errorMsg);
					return false;
				}else if(obj.errorCode == "20104"){
					alert(obj.errorMsg);
					return false;
				}else if(obj.errorCode == "20105"){
					alert(obj.errorMsg);
					return false;
				}else {
					alert(obj.errorMsg);
					return false;
				}
			},
			error:function(res){
				alert("数据提交出错，请稍后再注册");
			}
		});
	}
}
function loadLoginInfo(){
	/**
	 *实现自动登录
	 *返回登录信息
	 */
	$.ajax({
		type:"post",
		url:getRealPath()+"/servlet/LoadLoginInfo",
		async:false,
		success:function(res){
			var obj = JSON.parse(res);
			loginInfo.nickname = obj.nickname;
			loginInfo.pid = obj.pid;
		},
		error:function(res){
		}
	});
}
/*
 * 退出登录
 * loginout 退出登录
 */
function loginout(){
	$.cookie("pid","");
	$.cookie("nickname","");
	$.ajax({
		type:"POST",
		url:getRealPath()+"/servlet/LoginOut",
		async:false
	});
	window.location.reload();
}
