<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<t:genericpage>
	<jsp:attribute name="headContent">
		<title>Add Tenant</title>
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
	        $("#addTenantForm").validate({
	           rules: {
	                 name: "required",
	                 description: "required",
	               },
	               messages: {
	                 name: "Please enter tenant name",
	                 description: "Please enter tenant description",
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
	    <div class="common-form">
	     <form:form modelAttribute="tenant" action="${pageContext.request.contextPath}/tenant/add" method="POST" id="addTenantForm">
	         <div>
	              <label>Name</label>
	              <form:input path="name" cssClass="textbox-a" id="name" value="${ name }"/>
	        </div>
	        <div>
	              <label>Description</label>
	              <form:input path="description" cssClass="textbox-a" id="description" value="${ description }"/>
	        </div>
	        
	        <div>
	              <input class="btn-a" name="commit" type="submit" value="Add">
	        </div>
	    </form:form>
	    </div>
    </jsp:body>
    
</t:genericpage>
