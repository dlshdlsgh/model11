<%@page import="com.model2.mvc.service.domain.Product"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	List<Product> list= (List<Product>)request.getAttribute("list");
	System.out.println(list);
%>
<html>
<head>
<title>��ǰ ��� ��ȸ</title>
<meta charset="EUC-KR">
	
	<!-- ���� : http://getbootstrap.com/css/   ���� -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	<!-- Bootstrap Dropdown Hover CSS -->
   <link href="/css/animate.min.css" rel="stylesheet">
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
   
    <!-- Bootstrap Dropdown Hover JS -->
   <script src="/javascript/bootstrap-dropdownhover.min.js"></script>
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
 		body {
            padding-top : 50px;
        }
     
     </style>
<link rel="stylesheet" href="/css/admin.css" type="text/css">
<link type="text/css" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet" />
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

<script type="text/javascript">
function fncGetUserList(currentPage){
	document.getElementById("currentPage").value = currentPage;
	document.detailForm.submit();
}


$(function(){
	$(".ct_input_k > option").on("click",function(){
		fncGetUserList(1);
	})
	
	$("td.ct_btn01:contains('�˻�')").on("click",function(){
		fncGetUserList(1);
	})
	
	$("a.ct_btn01:contains('����ϱ�')").on("click",function(){
		var productNo = $(this).data("value");
		alert(productNo);
		self.location = "/purchase/updateTranCodeByProd?prodNo="+productNo+"&tranCode=2";
	})
	$("a.btn.btn-primary:contains('����')").on("click",function(){
		var productNo = $("a.btn.btn-primary").parent().data("value");
		alert(productNo);
		self.location = "/purchase/addPurchaseView?prodNo="+productNo;
	})
	$("a.btn.btn-default:contains('�󼼺���')").on("click",function(){
		var productNo = $(this).parent().data("value");
		alert(productNo);
		self.location = "/product/getProduct?prodNo="+productNo;
	})
	
	$('td').tooltip({
			content: function() {
	        return $(this).prop('title');
	       }
	});
	
	var menu='${param.menu}';
	
	if (menu=="search") {
		$("tr.ct_list_pop >").on("click",function(){
			var productNo = $(this).data("value")
		
			self.location = "/product/getProduct?prodNo="+productNo;
		})
	}else if (menu=="manage") {
		$("tr.ct_list_pop >").on("click",function(){
			var productNo = $(this).data("value")
		
			self.location = "/product/updateProductView?prodNo="+productNo;
		})
	}
	
	

});

$(document).ready(function () {
	  $(document).scroll(function() {
	    var maxHeight = $(document).height();
	    var currentScroll = $(window).scrollTop() + $(window).height();
		var currentPage = 1;
	    if (maxHeight <= currentScroll + 100) {
	      $.ajax({
	    	  	url : "/product/json/listProduct",
				method : "POST" ,
				dataType : "json" ,
				headers : {
					"Accept" : "application/json",
					"Content-Type" : "application/json"
				},
				data : JSON.stringify({
					kind : '${kind}',
					searchCondition : '${search.searchCondition}',
					searchKeyword : '${search.searchKeyword}',
					currentPage : function(){
						fncGetUserList('${search.currentPage+1}')
					}
				}),
				success : function(JSONData , status) {

					//Debug...
					//alert(status);
					//alert("JSONData : \n"+JSONData);
					//alert( "JSON.stringify(JSONData) : \n"+JSON.stringify(JSONData) );
					//alert( JSONData != null );
					
					if( JSONData != null ){
						//[���1]
						//$(window.parent.document.location).attr("href","/index.jsp");
						
						//[���2]
						//window.parent.document.location.reload();
						
						//[���3]
						$("div.row").appendTo("body");
						
						//==> ��� 1 , 2 , 3 ��� ����
					}else{
						alert("���̵� , �н����带 Ȯ���Ͻð� �ٽ� �α���...");
					}
				}
	      })
	    }
	  })
	});

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<jsp:include page="/layout/toolbar.jsp" />

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/product/listProduct?menu=${param.menu}" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
		
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
				<c:if test="${!empty param.menu && param.menu == 'search'}">
				<td width="93%" class="ct_ttl01">��ǰ �����ȸ</td>
				</c:if>
				<c:if test="${!empty param.menu && param.menu == 'manage'}">
				<td width="93%" class="ct_ttl01">��ǰ �������</td>
				</c:if>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="left">
			<select name="kind" class="ct_input_k" style="width:80px" >
				<option value="" ${!empty kind && kind==""  ? "selected" : "" }>���м���</option>
				<option value="��Ÿ" ${!empty kind && kind=="��Ÿ"  ? "selected" : "" }>��Ÿ</option>
				<option value="�ķ�ǰ" ${!empty kind && kind=="�ķ�ǰ"  ? "selected" : "" }>�ķ�ǰ</option>
				<option value="������ǰ" ${!empty kind && kind=="������ǰ"   ? "selected" : "" }>������ǰ</option>
				<option value="�ڵ���" ${!empty kind && kind=="�ڵ���"   ? "selected" : "" }>�ڵ���</option>
			</select>
		</td>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px" >
				<option value="0" ${!empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>��ǰ��ȣ</option>
				<option value="1" ${!empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>��ǰ��</option>
				<option value="2" ${!empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>��ǰ����</option>
			</select>
			<input 	type="text" name="searchKeyword"  value="${search.searchKeyword}" 
							class="ct_input_g" style="width:200px; height:19px"/>
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						�˻�
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<div class="row">
	<c:set var="i" value="0" />
	 <c:forEach var="product" items="${list}">
	 <c:set var="i" value="${i+1}" />
	
  <div class="col-sm-6 col-md-3">
    <div class="thumbnail" style="width: 242px; height: 280px; overflow: hidden">
      <img src="/images/uploadFiles/${product.fileName}" height="200" width="200" alt="...">
      <div class="caption">
        <h3>${product.prodName}</h3>
        <p>���� : ${product.price} ��</p>
        <p data-value="${product.prodNo}"><a href="#" class="btn btn-primary" role="button">����</a> <a href="#" class="btn btn-default" role="button">�󼼺���</a></p>
      </div>
    </div>
  </div>

	</c:forEach>
</div>

<!--  ������ Navigator �� -->
</form>
</div>
</body>
</html>