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

		<title>Tenant list</title>
	</jsp:attribute>
	
	<jsp:attribute name="topContent">
    	<jsp:include page="/WEB-INF/views/common/top.jsp" />
    </jsp:attribute>
    
    <jsp:attribute name="footerContent">
      	<!-- TODO -->
    </jsp:attribute>
    
    <jsp:body>
    	<form id="tenantForm" action="<c:url value="${pageContext.request.contextPath}/tenant/remove" />" method='POST'>
	        <table class="managementTable" id="tenantTable">
	        	<thead>
	        		<tr>
		        		<th class="tableTitle" colspan="4">
		        			Tenant Management
		        		</th>
		        	</tr>
		        	<tr>
		        		<th>Name</th>
		        		<th>Description</th>
                        <th>Enabled</th>
		        	</tr>
	        	</thead>
	        		
	        	<tbody>
	        		<c:forEach var="tenant" items="${ tenantList }">
	        			<tr>
		        			<td class="controlColumn"><input name="itemIds" value = "${ tenant.id }" type="checkbox" class="itemCheckbox" id="item_${ tenant.id }" /></td>
		        			<td>
		        				<a class="" href="${pageContext.request.contextPath}/tenant/edit/${ tenant.id }">${ tenant.name }</a>
							</td>
							<td>
		        				<a class="" href="${pageContext.request.contextPath}/tenant/edit/${ tenant.id }">${ tenant.description }</a>
		        			</td>
                            <td>
                                <a class="" href="${pageContext.request.contextPath}/tenant/edit/${ tenant.id }">${ tenant.enabled }</a>
                            </td>
		        		</tr>
	        		</c:forEach>
	        	</tbody>
	        </table>
	        <div class="buttonWrapper">
				<a href="${pageContext.request.contextPath}/tenant/add" class="actionButton">add</a>
				<a href="#" id="removeButton" class="removeButton">remove</a>
			</div>
	    </form>
    </jsp:body>
    
</t:genericpage>
