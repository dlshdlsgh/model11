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

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >��ü ${resultPage.totalCount} �Ǽ�, ���� ${resultPage.currentPage} ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100" >No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">�����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">���� ����</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<%--
	<% 	
		for(int i=0; i<list.size(); i++) {
			Product vo = (Product)list.get(i);
	%>
	--%>
	<c:set var="i" value="0" />
	 <c:forEach var="product" items="${list}">
	 <c:set var="i" value="${i+1}" />
	<tr class="ct_list_pop">
		<td align="center">${i}</td>
		<td></td>
		<td align="left" title="<img src = '/images/uploadFiles/${product.fileName}' width='180' height='120' />" 
		data-value="${product.prodNo}">
		${product.prodName}
		</td>
		<td></td>
		<td align="left">${product.price}</td>
		<td></td>
		<td align="left">${product.regDate}
		</td>
		<td></td>
		<td align="left">
		<c:if test="${!empty param.menu && param.menu == 'manage'}">
			<c:if test="${product.proTranCode == null}">�Ǹ���</c:if>
			<c:if test="${!empty product.proTranCode && product.proTranCode == 1}">
			���ſϷ� <a class="ct_btn01" data-value="${product.prodNo}">����ϱ�</a>
			</c:if>
			<c:if test="${!empty product.proTranCode && product.proTranCode == 2}">-�����-</c:if>
			<c:if test="${!empty product.proTranCode && product.proTranCode == 3}">��ۿϷ�</c:if>
		</c:if>
		<c:if test="${!empty param.menu && param.menu == 'search'}">
			<c:if test="${product.proTranCode == null}">�������</c:if>
			<c:if test="${!empty product.proTranCode && product.proTranCode != null}">(�ǸſϷ��ǰ)</c:if>
		</c:if>
		</td>		
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="currentPage" name="currentPage" value=""/>
		<jsp:include page="../common/pageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!--  ������ Navigator �� -->
</form>
</div>
</body>
</html>