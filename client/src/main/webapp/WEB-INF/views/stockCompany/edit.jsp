<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
    	<form id="stockCompaniesForm" action="<c:url value="${pageContext.request.contextPath}/stockCompanies/remove" />" method='POST'>
	        <table class="managementTable" id="stockQuoteTable">
	        	<thead>
	        		<tr>
                        <th class="tableTitle" colspan="5">
                                Stock Companies
                        </th>
                      

		        	</tr>
		        	<tr>
		        		<th class="controlColumn"><input type="checkbox" class="itemCheckboxParent" /></th>
		        		<th>Symbol</th>
		        		<th>Name</th>
		        	</tr>
	        	</thead>

                <tbody>
	        		<c:forEach var="stockCompany" items="${ stockCompanies }">
	        			<tr>
		        			<td class="controlColumn">
		        				<input name="symbols" value = "${ stockCompany.symbol }" type="checkbox" class="itemCheckbox" id="symbol_${ stockCompany.symbol }" />
		        			</td>
		        			<td>
		        				${ stockCompany.symbol }
							</td>
                  			<td>
		        				${ stockCompany.name }
							</td>
		        		</tr> 
	        		</c:forEach>
	        		
	        	<tbody>
	        </table>
            <div class="buttonWrapper">
				<a href="#" id="removeButton" class="removeButton">remove</a>
			</div>
	    </form>
         
         
         <div class="common-form">
         <h2>add or edit Stock Company</h2>
	     <form:form modelAttribute="newStockCompany" action="${pageContext.request.contextPath}/stockCompanies/update" method="POST" id="addCompanyForm">
	         <div>
	              <label>Symbol</label>
	              <form:input path="symbol" cssClass="textbox-a" id="symbol" value="${ symbol }"/>
	        </div>
	        <div>
	              <label>Name</label>
	              <form:input path="name" cssClass="textbox-a" id="name" value="${ name }"/>
	        </div>
	        
	        <div>
	              <input class="btn-a" name="commit" type="submit" value="OK">
	        </div>
	    </form:form>
	    </div>
	    </form>
    </jsp:body>
    
</t:genericpage>
