<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<t:genericpage>
	<jsp:attribute name="headContent">
		
		<!-- management table -->
		<link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/management_table.css" />" />
		<script src="<c:url value="${pageContext.request.contextPath}/resources/javascript/managementTable.js" />" ></script>
		
		<title>Home</title>
	</jsp:attribute>
	
	<jsp:attribute name="topContent">
    	<jsp:include page="/WEB-INF/views/common/top.jsp" />
    </jsp:attribute>
    
    <jsp:attribute name="footerContent">
      	<!-- TODO -->
    </jsp:attribute>
    
    <jsp:body>
    	<form action="<c:url value='j_spring_security_check' />" method='POST'>
	        <table class="managementTable">
	        	<thead>
	        		<tr>
		        		<th class="tableTitle" colspan="7">
		        			Tenant Management
		        			<div class="tableButtons"></div>
		        		</th>
		        	</tr>
		        	<tr>
		        		<th class="controlColumn"><input type="checkbox" class="itemCheckboxParent" /></th>
		        		<th>Login</th>
		        		<th>Tenant</th>
		        		<th>Name</th>
		        		<th>Surname</th>
		        		<th>Email</th>
		        		<th>Enabled</th>
		        	</tr>
	        	</thead>
	        		<c:forEach var="administrator" items="${ administratorList }">

	        			<tr>
		        			<td class="controlColumn"><input type="checkbox" class="itemCheckbox" id="item_${ administrator.id }" /></td>
		        			<td>
		        				<a class="" href="${pageContext.request.contextPath}/administrator/edit/${ administrator.id }">${ administrator.login }</a>
							</td>
							<td>
		        				<a class="" href="${pageContext.request.contextPath}/administrator/edit/${ administrator.id }">${ administrator.tenant }</a>
							</td>
							<td>
		        				<a class="" href="${pageContext.request.contextPath}/administrator/edit/${ administrator.id }">${ administrator.name }</a>
							</td>
							<td>
		        				<a class="" href="${pageContext.request.contextPath}/administrator/edit/${ administrator.id }">${ administrator.surname }</a>
							</td>
							<td>
		        				<a class="" href="${pageContext.request.contextPath}/administrator/edit/${ administrator.id }">${ administrator.email }</a>
							</td>
							<td>
		        				<a class="" href="${pageContext.request.contextPath}/administrator/edit/${ administrator.id }">${ administrator.enabled }</a>
							</td>
							
		        		</tr> 
	        		</c:forEach>
	        	<tbody>
	        	<tbody>
	        </table>
	    </form>
    </jsp:body>
    
</t:genericpage>
