<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="head.jsp"%>
    <body>
        <div class="container">
            <c:import url = "header.jsp" />
            <h2> Results for the search</h2>
            <p>You searched for "${topic}"</p>

            <%@include file="storyTable.jsp"%>
        </div>
    </body>
</html>