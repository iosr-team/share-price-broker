<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="inside-nav">
	<div class="logo">
		<img src="<c:url value="${pageContext.request.contextPath}/resources/images/logo.png" />" />
        <div class="logoTitle">
            Stock Brokers
        </div>
	</div>

	<sec:authorize access="isAuthenticated()">
		<div id="welcome">
			<div id="tenantBox">
				<sec:authentication property="principal.tenant.description" />
			</div>
			<div id="userBox">
				<%-- <sec:authentication property="principal.username" /> --%>
				<sec:authentication property="principal.user.name" /> <sec:authentication property="principal.user.surname" />
			</div>
		</div>
	</sec:authorize>

	<sec:authorize access="isAuthenticated()">
		<ul class="nav-a">


            <li><a class=""
                   href="${pageContext.request.contextPath}/stockQuote/list">
                <span>Stock Quotes</span></a>
            </li>


			<sec:authorize ifAnyGranted="ROLE_ADMIN">
				<li><a class=""
					href="${pageContext.request.contextPath}/user/list">
					<span>User Management</span></a>
				</li>
				<li><a class=""
					href="${pageContext.request.contextPath}/stockCompany/list">
					<span>Stock Companies</span></a>
				</li>
			</sec:authorize>
			
			<sec:authorize ifAnyGranted="ROLE_SUPERUSER">
				<li><a class=""
					href="${pageContext.request.contextPath}/administrator/list">
					<span>Administrators</span></a>
				</li>
				<li><a class=""
					href="${pageContext.request.contextPath}/tenant/list">
					<span>Tenants</span></a>
				</li>
			</sec:authorize>

			<li><a href="<c:url value="/j_spring_security_logout" />"><span>Logout</span></a></li>
		</ul>
	</sec:authorize>

</div>