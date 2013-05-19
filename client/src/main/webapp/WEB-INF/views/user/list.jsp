<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<t:genericpage>
	<jsp:attribute name="headContent">
		
		<!-- management table -->
		<link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/management_table.css" />" />
		<link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/buttons.css" />" />
		<script src="<c:url value="${pageContext.request.contextPath}/resources/javascript/managementTable.js" />" ></script>
		
		<title>Administrator list</title>
	</jsp:attribute>
	
	<jsp:attribute name="topContent">
    	<jsp:include page="/WEB-INF/views/common/top.jsp" />
    </jsp:attribute>
    
    <jsp:attribute name="footerContent">
      	<!-- TODO -->
    </jsp:attribute>
    
    <jsp:body>
    	<form id="userForm" action="<c:url value="${pageContext.request.contextPath}/user/remove" />" method='POST'>
	        <table class="managementTable" id="userTable">
	        	<thead>
	        		<tr>
		        		<th class="tableTitle" colspan="8">
		        			User Management
		        		</th>
		        	</tr>
		        	<tr>
		        		<th class="controlColumn"><input type="checkbox" class="itemCheckboxParent" /></th>
		        		<th>Login</th>
		        		<th>Tenant</th>
                        <th>Role</th>
		        		<th>Name</th>
		        		<th>Surname</th>
		        		<th>Email</th>
		        		<th>Enabled</th>
		        	</tr>
	        	</thead>

                <tbody>
	        		<c:forEach var="user" items="${ userList }">

	        			<tr>
		        			<td class="controlColumn"><input name="itemIds" value = "${ user.id }" type="checkbox" class="itemCheckbox" id="item_${ user.id }" /></td>
		        			<td>
		        				<a class="" href="${pageContext.request.contextPath}/user/edit/${ user.id }">${ user.login }</a>
							</td>
							<td>
		        				<a class="" href="${pageContext.request.contextPath}/user/edit/${ user.id }">${ user.tenant.description }</a>
							</td>
                            <td>
                                <a class="" href="${pageContext.request.contextPath}/user/edit/${ user.id }">${ user.role.name }</a>
                            </td>
							<td>
		        				<a class="" href="${pageContext.request.contextPath}/user/edit/${ user.id }">${ user.name }</a>
							</td>
							<td>
		        				<a class="" href="${pageContext.request.contextPath}/user/edit/${ user.id }">${ user.surname }</a>
							</td>
							<td>
		        				<a class="" href="${pageContext.request.contextPath}/user/edit/${ user.id }">${ user.email }</a>
							</td>
							<td>
		        				<a class="" href="${pageContext.request.contextPath}/user/edit/${ user.id }">${ user.enabled }</a>
							</td>
							
		        		</tr> 
	        		</c:forEach>

	        	<tbody>
	        </table>
	     	<div class="buttonWrapper">
				<a href="${pageContext.request.contextPath}/stockQuote/add" class="actionButton">add</a>
				<a href="#" id="removeButton" class="removeButton">remove</a>
			</div>
	    </form>
    </jsp:body>
    
</t:genericpage>
