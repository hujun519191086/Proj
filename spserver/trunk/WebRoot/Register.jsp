<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>My JSP 'Register.jsp' starting page</title>
<link href="<%=basePath%>css/register.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script src="<%=basePath%>jquery/jquery-1.12.4.min.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>jquery/jquery-1.12.4.js"></script>
<script type="text/javascript">
	function registPhone() {
		alert("抱歉！暂时不支持手机注册!")
	}

	function registEmail() {
	}
	
	
	
	function onMailBlur()
		{
			var email=$('.input_eamil').val();

			if(email=="")
			{
				alert("邮箱不能为空");
			    return false;
			}
			else
			{
			    reg=/^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/gi;
			    if(!reg.test(email))
			    {
			        alert("邮箱格式错误");
			        return false;
			    }
			    else
			   {
			    	//alert("邮箱格式正确");
			    	return true;
			    }
			}
	
		}
		
		
		
	var countdown = 60;
	function settime(val) {
	var t;
		if (countdown == 0) {
			val.removeAttribute("disabled");
			val.value = "发送邮件验证码";
			countdown = 60;
			return null;
		} else {
			val.setAttribute("disabled", true);
			val.value =  countdown + "秒后重新发送";
			countdown--;
		}
		t = setTimeout(function() {
			settime(val)
		}, 1000)
	}

	/*发送验证码*/
	function sendVerificationCode(sendValue) {
		var checkEmail = onMailBlur();

		if (!checkEmail) {
			return;
		}

		$.post('<%=basePath%>servlet/RegisterServlet', {
			emailName : $('.input_eamil').val(),
			eventId : 10001
		}, function(data) {
			//alert(data);
		});
		
		settime(sendValue);
	}
	
	
	function checkStatus(){
		var isChecked = $('#cb_agree').is(':checked');
		if(isChecked == true){
		//alert(isChecked+"asd");
			$("#sub_register").attr("disabled", false);
			$("#sub_register").css("background-color", "#f63854");
		}else{
		//alert(isChecked+"a11");
			$("#sub_register").css("background-color", "#cccccc");
		}
	}
	
	/*注册*/
	function register() {
		var checkEmail = onMailBlur();
	
		if(!checkEmail){
			return;
		}
	
		$.post('<%=basePath%>servlet/RegisterServlet', {
			emailName : $('.input_eamil').val(),
			pass : $('.input_pwd').val(),
			VerificationCode : $('.email_code').val(),
			eventId : 10101
		}, function(data) {
			alert(data);
		});
	}
</script>
</head>
<body>
	<div class="root">
		<div class="register_phone">
			<label id="js-register_phone" class="button" onclick="registPhone()">
				手机注册</label>
		</div>
		<div class="register_email">
			<label id="js-register_mail" class="button" onclick="registEmail()">
				邮箱注册</label>
		</div>
		<div class="form_div">
			<div class="div_30">
				<label class="label_name">邮箱地址 : </label> <input type="text"
					class="input_eamil" name="emailName" />
			</div>
			<div class="div_20">
				<label class="label_name">新密码 : </label> <input type="text"
					class="input_pwd" name="pass" />
			</div>
			<div class="div_20">
				<label class="label_name">邮箱验证码 : </label> <input type="text"
					class="email_code" name="VerificationCode" /><input type="button"
					class="send_email_code" value="发送邮件验证码"
					onclick="sendVerificationCode(this)" />
			</div>
			<div class="div_20">
				<input type="checkbox" id="cb_agree" onclick="checkStatus()"/> <label>同意<a href="javascript:;">《同意一元海购协议》</a></label>
			</div>
			<div class="div_30">
				<input type="submit" value="立即注册" id = "sub_register" class="sub_register"
					onclick="register()" disabled="disabled"/>
			</div>
		</div>
	</div>
</body>
</html>
