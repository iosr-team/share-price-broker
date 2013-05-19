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

		<script src="/resources/javascript/managementTable.js"></script>

        <link href="/resources/css/examples.css" rel="stylesheet" type="text/css">
	<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="/resources/javascript/excanvas.min.js"></script><![endif]-->
        <script language="javascript" type="text/javascript" src="/resources/javascript/jquery.js"></script>
        <script language="javascript" type="text/javascript" src="/resources/javascript/jquery.flot.js"></script>
        <script language="javascript" type="text/javascript" src="/resources/javascript/jquery.flot.time.min.js"></script>
        <script language="javascript" type="text/javascript" src="/resources/javascript/stockCompanyHistoryPlot.js"></script>


        <script language="javascript" type="text/javascript">
            jQuery(document).ready(function(){
                historyPlot("${companySymbol}", 10000);
            });
        </script>

		<title>Stock Companies</title>
	</jsp:attribute>
	
	<jsp:attribute name="topContent">
    	<jsp:include page="/WEB-INF/views/common/top.jsp" />
    </jsp:attribute>
    
    <jsp:attribute name="footerContent">
      	<!-- TODO -->
    </jsp:attribute>

    <jsp:body>

        <div class="demo-container">
            <div id="placeholder" class="demo-placeholder"></div>
        </div>

    </jsp:body>
    
</t:genericpage>
