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
<title>상품 목록 조회</title>

	<!-- 참조 : http://getbootstrap.com/css/   참조 -->
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
	$('td').tooltip({
			content: function() {
	        return $(this).prop('title');
	       }
	});
	
	$("tr.ct_list_pop >").on("click",function(){
		var productNo = $(this).data("value")
		self.location = "/product/getProduct?prodNo="+productNo;
	})

});


</script>
</head>

<body bgcolor="#ffffff" text="#000000">
<jsp:include page="/layout/toolbar.jsp" />

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/product/historyList" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
		
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
				<td width="93%" class="ct_ttl01">최근 본 상품</td>
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
				<option value="" ${!empty kind && kind==""  ? "selected" : "" }>구분선택</option>
				<option value="기타" ${!empty kind && kind=="기타"  ? "selected" : "" }>기타</option>
				<option value="식료품" ${!empty kind && kind=="식료품"  ? "selected" : "" }>식료품</option>
				<option value="가전제품" ${!empty kind && kind=="가전제품"   ? "selected" : "" }>가전제품</option>
				<option value="자동차" ${!empty kind && kind=="자동차"   ? "selected" : "" }>자동차</option>
			</select>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100" >No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">등록일</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">현재 상태</td>
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
	 <c:forEach var="product" items="${hisList}">
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
		
			<c:if test="${product.proTranCode == null}">재고있음</c:if>
			<c:if test="${!empty product.proTranCode && product.proTranCode != null}">(판매완료상품)</c:if>
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
<!--  페이지 Navigator 끝 -->
</form>
</div>
</body>
</html>