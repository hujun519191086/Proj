<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>登录</title>
<link href="<%=basePath%>css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
</head>
<body style="background-color: #47c6f5">
	<script src="<%=basePath%>jquery/jquery-1.12.4.min.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="<%=basePath%>jquery/jquery-1.12.4.js"></script>
	<script type="text/javascript">
		var isAutoLogin;
		function autoLogin() {
			var isChecked = $('#auto_login').is(':checked');
			if (isChecked == true) {
				isAutoLogin = "1";
				alert(isChecked+isAutoLogin);
			} else {
				isAutoLogin = "0";
				alert(isChecked+isAutoLogin);
			}

		}

		function login() {

			var name = $('#js-mail_ipt').val();
			var pwd = $('#js-mail_pwd_ipt').val();

			if (name.trim() == "") {
				alert("请输入用户名!");
				return;
			}

			if (pwd.trim() == "") {
				alert("请输入密码!");
				return;
			}

			$.post('<%=basePath%>servlet/LoginInfoServlet', {
				emailName : $('#js-mail_ipt').val(),
				pass : $('#js-mail_pwd_ipt').val(),
				autoLogin: isAutoLogin
			}, function(data) {
				alert(data);
			});
		}

		function regist() {
			window.location.href = "Register.jsp";
		}
	</script>
	<div class="container">
		<div class="register-box">
			<div class="reg-slogan" style="text-align: center;">账户登录</div>
			<div class="reg-form">
				<div class="linearlayout">
					<input type="text" name="emailName" id="js-mail_ipt" class="text"
						 placeholder="邮箱账号/手机号" />
				</div>
				<div class="linearlayout">
					<input type="password" name="pass" id="js-mail_pwd_ipt"
						placeholder="密码" class="text" />
				</div>
				<div class="linearlayout_auto">
					<label class><input type="checkbox" class="checkbox" id="auto_login" onchange="autoLogin()">自动登录</label><label
						class="label">忘记密码？</label>
				</div>
				<div class="linearlayout">
					<a id="js-mail_btn" href="javascript:;" class="button btn-red"
						onclick="login()">登录</a>
				</div>
				<div class="linearlayout">
					<a id="js-register_btn" href="javascript:;" class="button btn-gray"
						onclick="regist()">注册</a>
				</div>
				<div class="linearlayout_30">
					<label style="font-family: '微软雅黑'; color: #999999">其他账号登录:
					</label> <img class="otherimg_10" alt=""
						src="<%=basePath%>images/login/qq_login.jpg"></img><img
						class="otherimg_20" alt=""
						src="<%=basePath%>images/login/qq_login.jpg"></img> <img
						class="otherimg_20" alt=""
						src="<%=basePath%>images/login/qq_login.jpg"></img>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
