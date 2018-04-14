<%@page import="com.google.zxing.client.j2se.MatrixToImageWriter"%>
<%@page import="com.google.zxing.BarcodeFormat"%>
<%@page import="com.google.zxing.MultiFormatWriter"%>
<%@page import="com.google.zxing.common.BitMatrix"%>
<%@page import="com.google.zxing.EncodeHintType"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.io.OutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>



 
 
<script type="text/javascript" src="js/jquery-2.0.3.min.js" ></script>
<script type="text/javascript" src="js/jquery.qrcode.min.js" ></script>
 

<script> 
	
	//document.getElementById("count").innerText = counts;
	var url = location.search;
	var code = url.substr(1);
	var urlcode = code.split("=")[1]	
	window.onload = function loadQRcode(){
	$('#qrcode').qrcode(urlcode);	
	}
	/*
	$(document).ready(function () {
	    setInterval("ajaxstatus()", 3000);        
	});	
	
	function ajaxstatus() {	        
	        $.ajax({
	            url: "./QueryTradeno",
	            type: "GET",
	            dataType:"json",
	            data:{
	            tradeno:trades,
	            pid:pids
	            },
	            success: function (res) {	            	
	                 //订单状态为1表示支付成功
	                   window.location.href = "../PaySuccess.jsp"; //页面跳转
	            },
	            error: function () {
	                 
	            }
	        });
	}
	*/
</script>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
   	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>扫码支付</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<script type="text/javascript"></script>
	<script type="text/javascript" src="js/style.js" ></script>
  </head>
  <body>
  	<div class="top"></div>
  	<div class="logo-img pull-left">
		<img src="img/logo1.png" />
	</div>
	<div style="align:right">
		<img src="img/step_2.png" />
	</div>
    <div align = "center" id = "qrcode">
	 <p>打开微信扫一扫支付</p><p id = "count"></p>
	 <p>支付金额：</p>
	 
	</div>
	
	
  </body>
</html>
