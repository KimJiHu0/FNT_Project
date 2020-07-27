<%@page import="com.fnt.model.dto.MemberDto"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath }/js/kakao.min.js"></script>

<script type="text/javascript">
Kakao.init('290f8b616802bba9fad591226e4dab51');

/*function loginWithKakao() {
	Kakao.Auth.authorize({
		redirectUri: '${pageContext.request.contextPath}/kakaosignup.do'
	});	
}*/
	
// 카카오 소셜 로그인
	function loginWithKakao() {
		Kakao.Auth.login({
			success: function(authObj) {
				location.href = '${pageContext.request.contextPath}/kakaosignup.do?access_token=' + authObj.access_token;
				/*Kakao.API.request({
				    url: '/v2/user/me',
				    success: function(response) {
				    	fetch('${pageContext.request.contextPath}/kakaosignup.do', {
				    		method: 'POST',
				    		headers: {
				    			'Content-Type': 'application/json;charset=utf-8',
				    		},
				    		body: JSON.stringify({
				    			response, access_token: authObj.access_token,
				    		}),
				    	})
				    	.then(res => res.text())
				    	.then(console.log);
				    },
				    fail: function(error) {
				        console.log(error);
				    }
				});*/
			},
			fail: function(err) {
				alert(JSON.stringify(err))
		},
	})}
	
</script>

<link href="css/fntlogincrud.css" rel="stylesheet" type="text/css"/>
<%
    String clientId = "T0e_dO0FJagJxo8igTCZ";//애플리케이션 클라이언트 아이디값";
    String redirectURI = URLEncoder.encode("http://127.0.0.1:8787/FNT_Project/naversignup.do", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += "&client_id=" + clientId;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&state=" + state;
    session.setAttribute("state", state);
 %>
</head>

<body>
	<div class="logo">
		<a href="fntmain.jsp">
			<img id="fnt_logo" alt="FNT" src="./img/fnt_logo.png">
		</a>	
	</div>
	<form action="LoginCrudController" method="post">
	<input type="hidden" name="command" value="login">
	<table>
		<tr>
			<th>ID</th>
			<td><input type="text" name="id" required="required" placeholder="ID를 입력하세요."></td>
		</tr>
		<tr>
			<th>PW</th>
			<td><input type="password" name="pw" required="required" placeholder="Password를 입력하세요."></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<button class="login_btn" type="submit">로그인</button>
				<button class="login_btn" type="button" onclick="location.href='fntsignupform.jsp'">회원가입</button>
			</td>
		</tr>
		
		<tr>
			<td colspan="2">
				<a class="findbtn" href="LoginClurController?command=logincurdsearchid">ID 찾기</a>
				<input class="findbtn" type="button" value="ID 찾기 " onclick="location.href='LoginCrudController?command=logincrudsearchid'">
				<input class="findbtn" type="button" value="PW 찾기 " onclick="location.href='LoginCrudController?command=logincrudsearchpw'">

			</td>
		</tr> 
		<tr>
			<td colspan="2">
				<div class="social_login_btn">
					<!-- navers -->
					<a href="<%=apiURL%>"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>
					
					<!-- 카카오 -->
					<img id="kakao_id_login" src="./img/login_btn_kakao.png" onclick="javascript:loginWithKakao()"/>
				</div>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>	