<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
  <head>
    <title>Simple RabbitMQ Application</title>
  </head>
  <body>
    <h1>Simple RabbitMQ Application</h1>

    <h2>Publish a message</h2>
    <p><b>SAMPLE ROUTING KEYS:</b> GOOG, NVDA</p>
    
    <form:form modelAttribute="message" action="/publish" method="post">
		<form:label for="value" path="value">Value:</form:label>
		<form:input path="value" type="text"/>
		
		<form:label for="key" path="key">Company symbol:</form:label>
		<form:input path="key" type="text"/>
		
		<input type="submit" value="Publish"/>
    </form:form>

    <c:if test="${published}">
		<p>Published a message!</p>
    </c:if>

    <h2>Get a message</h2>

    <form:form modelAttribute="key" action="/get" method="post">
    	<form:label for="value" path="value">Queue name:</form:label>
		<form:input path="value" type="text"/>	
    
		<input type="submit" value="Get one"/>
    </form:form>

	<c:choose>
		<c:when test="${got_queue_empty}">
			<p>Queue empty</p>
		</c:when>
		<c:when test="${got != null}">
			<p>Got message: <c:out value="${got}"/></p>
		</c:when>
	</c:choose>
  </body>
</html>