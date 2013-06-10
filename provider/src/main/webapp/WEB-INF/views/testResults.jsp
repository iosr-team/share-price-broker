<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
  <head>
    <title>RabbitMQ performance test results</title>
  </head>
  <body>
    <h1>RabbitMQ performance test results</h1>
	<table border>
	<tr>
		<th>number of messages</th><th>total time [ms]</th>
	</tr>
    	<c:forEach var="result" items="${ results }">
      		<tr>
       			<td>
                            ${ result.num }
				</td>
				<td>
               				${ result.time }
				</td>
       		</tr> 
	 	</c:forEach>
	 </table>
  </body>
</html>