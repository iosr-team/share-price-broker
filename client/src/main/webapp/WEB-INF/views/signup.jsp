<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<t:genericpage>
	<jsp:attribute name="headContent">
		<title>Add user</title>
	    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />
	    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
	    <script src="http://code.jquery.com/jquery-latest.js"></script>
	      <script type="text/javascript" src="http://jzaefferer.github.com/jquery-validation/jquery.validate.js"></script>
	    <style type="text/css">
	     * { font-family: Verdana; font-size: 96%; }
	    label { width: 10em; float: left; }
	    label.error { float: none; color: red; vertical-align: bottom;
	            padding:20px 0px 10px 0px; display:inline}
	    p { clear: both; }
	    .submit { margin-left: 12em; }
	    em { font-weight: bold; padding-right: 1em; vertical-align: top; } 
	    </style>
	      <script>
	      $(document).ready(function(){
	        $("#signupForm").validate({
	           rules: {
	                 login: "required",
	                 password: "required",
	                 email: {
	                   required: true,
	                   email: true
	                 },
	                 name: "required",
	                 surname: "required",
	               },
	               messages: {
	                 login: "Please enter your user login",
	                 password: "Please enter your password",
	                 email: {
	                   required: "Please enter your email",
	                   email: "Your email address must be in the format of name@domain.com"
	                 },
	               name: "Please enter your user name",
	               surname: "Please enter your surname"
	               }
	            });
	      });
	      </script>
	</jsp:attribute>
	
	<jsp:attribute name="topContent">
    	<jsp:include page="/WEB-INF/views/common/top.jsp" />
    </jsp:attribute>
    
    <jsp:attribute name="footerContent">
      	<!-- TODO -->
    </jsp:attribute>
    
    <jsp:body>
        <c:if test="${not empty param.errorMsg}">
    
	        <div class="errorblock">
	            ${param.errorMsg}
	        </div>
	    </c:if>
	    <div class="signing-form">
	     <form action="${pageContext.request.contextPath}/signup" class="new_user" method="post" id="signupForm">
	         <div>
	              <label>Login</label>
	              <input class="textbox-a" id="login" name="login" required="required"  placeholder="login" size="30" type="text">
	        </div>
	        <div>
	              <label>Password</label>
	              <input class="textbox-a" id="password" name="password" placeholder="Password" size="30" type="password" required="required">
	        </div>
	        <div>
	              <label>Email</label>
	              <input class="textbox-a" id="email" name="email" placeholder="email" size="30" type="email" required="required">
	        </div>
	        <div>
	              <label>Name</label>
	              <input class="textbox-a" id="name" name="name" placeholder="name" size="30" type="text" required="required">
	        </div>
	        <div>
	              <label>Surname</label>
	              <input class="textbox-a" id="surname" name="surname" placeholder="surname" size="30" type="text" required="required">
	        </div>
	        <div>
	            <label>Role</label>
	        	<select id="role" name="role">
			    	<c:forEach var="item" items="${roleMap}">
			            <option value="${item.key}">${item.value}</option>
			        </c:forEach>
				</select>
	        </div>
	        <div>
	            <label>Tenant</label>
	        	<select id="tenant" name="tenant">
			    	<c:forEach var="item" items="${tenantMap}">
			            <option value="${item.key}">${item.value}</option>
			        </c:forEach>
				</select>
	        </div>
	        
	        <div>
	              <input class="btn-a" name="commit" type="submit" value="Sign Up">
	        </div>
	    </form>
	    </div>
    </jsp:body>
    
</t:genericpage>
