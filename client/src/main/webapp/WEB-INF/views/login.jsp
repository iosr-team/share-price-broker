<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<t:genericpage>
	<jsp:attribute name="headContent">
		<title>Login</title>
	</jsp:attribute>
	
	<jsp:attribute name="topContent">
    	<jsp:include page="/WEB-INF/views/common/top.jsp" />
    </jsp:attribute>
    
    <jsp:attribute name="footerContent">
      	<!-- TODO -->
    </jsp:attribute>
    
    <jsp:body>
        <div class="common-form">
			<c:if test="${not empty error}">
				<div class="errorblock">
					Your login attempt was not successful, try again.<br /> 
					Caused: ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
				</div>
			</c:if>
			<c:if test="${not empty param.succMsg }">
				<div class="successblock">${param.succMsg}</div>

			</c:if>
				<div class="errorblock">
					${errorMsg}
				</div>
			<form action="<c:url value='j_spring_security_check' />" method='POST'>
				<div>
					<label>Username</label> <input class="textbox-a" id="username"
						name="j_username" placeholder="UserName" type="text"
						required="required">
				</div>
				<div>
					<label>Password</label> <input class="textbox-a" id="password"
						name="j_password" placeholder="Password" type="password"
						required="required">
				</div>
				<div>
					<input class="btn-a" name="commit" type="submit" value="Log in">
				</div>
			</form>
		</div>
    </jsp:body>
    
</t:genericpage>