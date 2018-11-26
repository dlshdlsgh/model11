<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>���� �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	function fncGetUserList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}

	$(function() {
		$("jsp").bind("click", function() {
			fncGetUserList($("#currentPage").val());
		})

		$(".ct_list_pop > td[id='first']").on("click", function() {
			//Debug..
			//alert(  $( "td.ct_btn01:contains('����')" ).html() );
			var tranValue = $(this).data("name");
			self.location = "/purchase/getPurchase?tranNo=" + tranValue;
		});

		$("tr.ct_list_pop > td[id='second']").on("click", function() {
			//Debug..
			//alert(  $( "td.ct_btn01:contains('����')" ).html() );
			self.location = "/user/getUser?userId=" + $(this).text().trim();
		});

		$("tr.ct_list_pop > td[id='last']").on(
				"click",
				function() {
					//Debug..
					//alert(  $( "td.ct_btn01:contains('����')" ).html() );
					var tranValue = $(this).data("name2");
					self.location = "/purchase/updateTranCodeByProd?prodNo="
							+ tranValue + "&tranCode=3";
				});

	})
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">

		<form name="detailForm" action="/purchase/listPurchase" method="post">

			<table width="100%" height="37" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
						width="15" height="37"></td>
					<td background="/images/ct_ttl_img02.gif" width="100%"
						style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="93%" class="ct_ttl01">���� �����ȸ</td>
							</tr>
						</table>
					</td>
					<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"
						width="12" height="37"></td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td colspan="11">��ü ${resultPage.totalCount} �Ǽ�, ����
						${resultPage.currentPage} ������</td>
				</tr>
				<tr>
					<td class="ct_list_b" width="100">No</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">ȸ��ID</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">ȸ����</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">��ȭ��ȣ</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">�����Ȳ</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">��������</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>
				<c:set var="i" value="0" />
				<c:forEach var="purchase" items="${list}">
					<c:set var="i" value="${i+1}" />
					<tr class="ct_list_pop">
						<td align="center" id="first" data-name="${purchase.tranNo}">${i}
						</td>
						<td></td>
						<td align="left" id="second">${user.userId}</td>
						<td></td>
						<td align="left">${purchase.receiverName}</td>
						<td></td>
						<td align="left">${purchase.receiverPhone}</td>
						<td></td>
						<td align="left"><c:if
								test="${!empty purchase.tranCode && purchase.tranCode == 2}">�����</c:if>
							<c:if
								test="${!empty purchase.tranCode && purchase.tranCode == 3}">���ſϷ�Ȼ�ǰ</c:if>
							<c:if
								test="${!empty purchase.tranCode && purchase.tranCode == 1}">����غ����</c:if>
						</td>
						<td></td>
						<td align="left" id="last"
							data-name2="${purchase.purchaseProd.prodNo}"><c:if
								test="${!empty purchase.tranCode && purchase.tranCode == 2}">
			��ǰ�����˸����
			</c:if></td>
					</tr>
					<tr>
						<td colspan="11" bgcolor="D6D7D6" height="1"></td>
					</tr>
				</c:forEach>
			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td align="center"><input type="hidden" id="currentPage"
						name="currentPage" value="" /> <jsp:include
							page="../common/pageNavigator.jsp" /></td>
				</tr>
			</table>

			<!--  ������ Navigator �� -->
		</form>

	</div>

</body>
</html>