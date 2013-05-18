<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    	<form id="stockQuoteForm" action="#" method='POST'>
	        <table class="managementTable" id="stockQuoteTable">
	        	<thead>
	        		<tr>
                        <sec:authorize ifAnyGranted="ROLE_SUPERUSER">
                            <th class="tableTitle" colspan="6">
                                Stock Quotes
                            </th>
                        </sec:authorize>
                        <sec:authorize ifNotGranted="ROLE_SUPERUSER">
                            <th class="tableTitle" colspan="5">
                                Stock Quotes
                            </th>
                        </sec:authorize>

		        	</tr>
		        	<tr>
		        		<%--<th class="controlColumn"><input type="checkbox" class="itemCheckboxParent" /></th>--%>
		        		<th>Symbol</th>
		        		<th>Value</th>
		        		<th>Date</th>
                        <sec:authorize ifAnyGranted="ROLE_SUPERUSER">
                            <th>Tenant</th>
                        </sec:authorize>
		        	</tr>
	        	</thead>

                <tbody>
	        		<c:forEach var="stockQuote" items="${ stockQuoteList }">

	        			<tr>
		        			<%--<td class="controlColumn"><input name="itemIds" value = "${ stockQuote.id }" type="checkbox" class="itemCheckbox" id="item_${ stockQuote.id }" /></td>--%>
		        			<td>
		        				<a class="" href="#">${ stockQuote.stockCompany.symbol }</a>
							</td>
                  			<td>
		        				<a class="" href="#">${ stockQuote.value }</a>
							</td>
							<td>
		        				<a class="" href="#"><fmt:formatDate value="${ stockQuote.date }" pattern="yyyy-MM-dd HH:mm:ss" /></a>
							</td>
                            <sec:authorize ifAnyGranted="ROLE_SUPERUSER">
                                <td>
                                    <a class="" href="#">${ stockQuote.tenant.description }</a>
                                </td>
                            </sec:authorize>
							
		        		</tr> 
	        		</c:forEach>

	        	<tbody>
	        </table>
	    </form>
    </jsp:body>
    
</t:genericpage>
