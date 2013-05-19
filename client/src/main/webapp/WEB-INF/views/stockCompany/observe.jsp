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
		
		<title>Stock Companies</title>
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
    	<form id="stockCompaniesForm" action="<c:url value="${pageContext.request.contextPath}/stockCompanies/observe" />" method='POST'>
	        <table class="managementTable" id="stockCompaniesTable">
	        	<thead>
	        		<tr>
                        <th class="tableTitle" colspan="5">
                        	Observed Stock Companies
                        </th>
                      

		        	</tr>
		        	<tr>
		        		<th class="controlColumn"><input type="checkbox" class="itemCheckboxParent" /></th>
		        		<th>Symbol</th>
		        		<th>Name</th>
		        		<th>observed</th>
		        	</tr>
	        	</thead>

                <tbody>
                	<c:forEach var="stockCompany" items="${ observedStockCompanies }">

	        			<tr>
	        				<td>
		        				<input name="symbols" value = "${ stockCompany.symbol }" type="checkbox" class="itemCheckbox" id="symbol_${ stockCompany.symbol }" checked />
		        			</td>
		        			<td>
                                <a class="" href="${pageContext.request.contextPath}/stockCompanies/history/${ stockCompany.symbol }">${ stockCompany.symbol }</a>
							</td>
                  			<td>
                                <a class="" href="${pageContext.request.contextPath}/stockCompanies/history/${ stockCompany.symbol }">${ stockCompany.name }</a>
							</td>
							<td>
								yes
							</td>
		        		</tr> 
	        		</c:forEach>
	        		<c:forEach var="stockCompany" items="${ unobservedStockCompanies }">

	        			<tr>
		        			<td>
		        				<input name="symbols" value = "${ stockCompany.symbol }" type="checkbox" class="itemCheckbox" id="symbol_${ stockCompany.symbol }" />
		        			</td>
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
				<a href="#" id="removeButton" class="actionButton">update</a>
			</div>
	    </form>

    </jsp:body>
    
</t:genericpage>
