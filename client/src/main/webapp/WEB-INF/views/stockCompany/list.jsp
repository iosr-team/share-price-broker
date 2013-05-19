<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<t:genericpage>
	<jsp:attribute name="headContent">
		
		<!-- management table -->
		<link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/management_table.css" />" />
		<link rel="stylesheet" href="/resources/css/buttons.css" />
		<script src="/resources/javascript/managementTable.js" ></script>
		
		<title>Stock Companies</title>
	</jsp:attribute>
	
	<jsp:attribute name="topContent">
    	<jsp:include page="/WEB-INF/views/common/top.jsp" />
    </jsp:attribute>
    
    <jsp:attribute name="footerContent">
      	<!-- TODO -->
    </jsp:attribute>
    
    <jsp:body>
	        <table class="managementTable" id="stockQuoteTable">
	        	<thead>
	        		<tr>
	        		 	<th class="tableTitle" colspan="5">
                                Stock Companies
                        </th>
		        	</tr>
		        	<tr>
		        		<th>Symbol</th>
		        		<th>Name</th>
		        	</tr>
	        	</thead>

                <tbody>
	        		<c:forEach var="stockCompany" items="${ stockCompanies }">

	        			<tr>
		        			<td>
                                <a class="" href="${pageContext.request.contextPath}/stockCompanies/history/${ stockCompany.symbol }">${ stockCompany.symbol }</a>
							</td>
                  			<td>
                                <a class="" href="${pageContext.request.contextPath}/stockCompanies/history/${ stockCompany.symbol }">${ stockCompany.name }</a>
							</td>
		        		</tr> 
	        		</c:forEach>

	        		
	        	<tbody>
	        </table>
    </jsp:body>
    
</t:genericpage>
