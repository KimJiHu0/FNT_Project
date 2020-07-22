<%@page import="com.fnt.model.dto.NoticeBoardDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%
	NoticeBoardDto noticeboardlistone = (NoticeBoardDto)request.getAttribute("noticeboardlistone");
%>
<title>FNT(Feel New Item) : <%=noticeboardlistone.getNbtitle() %></title>
<link href="css/section.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
	.notice{
		margin-left: 300px;
		margin-top: 200px;
		border: 1px solid gray;
		width: 600px;
	}
	.nickname{
		margin-bottom: 30px;
	}
	.button{
		margin-left: 300px;
		
	}
</style>
</head>
<body>
	<%@ include file="./form/header.jsp" %>
	<%@ include file="./form/aside.jsp" %>
	<section>
		<table border="1"> 
			<tr>
				<td>공지사항 > 
				<button onclick="location.href='notice.do?command=notice'">목록</button></td>
			<tr>
			<tr>
				<td><%=noticeboardlistone.getNbtitle() %></td>
			</tr>
			<tr>
				<td><%=noticeboardlistone.getNbnickname() %></td>
			</tr>
			<tr>
				<td width="400" height="200"><%=noticeboardlistone.getNbcontent() %></td>
			</tr>
	
		<%
			if(memberdto == null) {
		%>
		
		<%
			} else if (memberdto.getMemberrole().equals("ADMIN")) {
		%>
		<tr>
		<td align="right">
			<button onclick="location.href='notice.do?command=noticeupdate&nbboardno=<%=noticeboardlistone.getNbboardno()%>'">수정</button>
			<button onclick="location.href='notice.do?command=noticedelete&nbboardno=<%=noticeboardlistone.getNbboardno()%>'">삭제</button>
		</td>
		</tr>
		<%
			} else if (memberdto.getMemberrole().equals("USER")) {
		%>
			
		<%
			}
		%>
		</table>
	</section>
	<%@ include file="./form/footer.jsp" %>
</body>
</html>