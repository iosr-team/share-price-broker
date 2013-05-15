<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@tag description="Page template" language="java" pageEncoding="UTF-8"%>
<%@attribute name="headContent" fragment="true" %>
<%@attribute name="topContent" fragment="true" %>
<%@attribute name="footerContent" fragment="true" %>

<html>
	
	<head>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/style.css" />" />
		
		<jsp:invoke fragment="headContent" />
	</head>
	
  	<body>
  		<div id="contentWrapper">
		    <div id="top">
		      <jsp:invoke fragment="topContent"/>
		    </div>
		    <div id="body">
		      <jsp:doBody/>
		    </div>
		    <div id="footer">
		      <jsp:invoke fragment="footerContent"/>
		    </div>
		</div>
  	</body>
</html>