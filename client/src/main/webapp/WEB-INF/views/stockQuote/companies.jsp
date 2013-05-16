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
		
		<title>Stock companies</title>
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
                                Stock Quotes (superuser)
                                <div class="tableButtons">
                                        <%--<a href="${pageContext.request.contextPath}/stockQuote/add" class="actionButton">add</a>
                                        <a href="#" class="removeButton">remove</a>--%>
                                </div>
                            </th>
                        </sec:authorize>
                        <sec:authorize ifNotGranted="ROLE_SUPERUSER">
                            <th class="tableTitle" colspan="5">
                                Stock Quotes
                                <div class="tableButtons">
                                        <%--<a href="${pageContext.request.contextPath}/stockQuote/add" class="actionButton">add</a>
                                        <a href="#" class="removeButton">remove</a>--%>
                                </div>
                            </th>
                        </sec:authorize>

		        	</tr>
		        	<tr>
		        		<%--<th class="controlColumn"><input type="checkbox" class="itemCheckboxParent" /></th>--%>
		        		<th>Symbol</th>
		        		<th>Name</th>
		        		<th>observed</th>
		        	</tr>
	        	</thead>

                <tbody>
	        		<c:forEach var="stockCompany" items="${ observedStockCompanies }">

	        			<tr>
		        			<%--<td class="controlColumn"><input name="itemIds" value = "${ stockQuote.id }" type="checkbox" class="itemCheckbox" id="item_${ stockQuote.id }" /></td>--%>
		        			<td>
		        				<a class="" href="#">${ stockCompany.symbol }</a>
							</td>
                  			<td>
		        				<a class="" href="#">${ stockCompany.name }</a>
							</td>
							<td>
								yes
							</td>
		        		</tr> 
	        		</c:forEach>
	        		<c:forEach var="stockCompany" items="${ unobservedStockCompanies }">

	        			<tr>
		        			<%--<td class="controlColumn"><input name="itemIds" value = "${ stockQuote.id }" type="checkbox" class="itemCheckbox" id="item_${ stockQuote.id }" /></td>--%>
		        			<td>
		        				<a class="" href="#">${ stockCompany.symbol }</a>
							</td>
                  			<td>
		        				<a class="" href="#">${ stockCompany.name }</a>
							</td>
							<td>
								no
							</td>
		        		</tr> 
	        		</c:forEach>
	        		
	        	<tbody>
	        </table>
            <div class="buttonWrapper">
                <%--<a href="${pageContext.request.contextPath}/stockQuote/add" class="actionButton">add</a>
                <a href="#" class="removeButton">remove</a>--%>
            </div>
	    </form>
    </jsp:body>
    
</t:genericpage>