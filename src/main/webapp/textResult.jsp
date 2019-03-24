<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="head.jsp"%>
    <body>
        <h2> Results for the search</h2>
        <p>Search term is currently hard coded.</p>



        <c:choose>
            <%-- Resource for evaluating empty tag:
            https://stackoverflow.com/questions/2811626/evaluate-empty-or-null-jstl-c-tags by BalusC--%>
            <c:when test="${!empty textResult}">
                <table>
                    <c:forEach var = "result" items="${textResult}">
                        <tr><td>${result}</td></tr>
                    </c:forEach>

                </table>
            </c:when>
            <c:otherwise>
                <p>No results were found.</p>
            </c:otherwise>

        </c:choose>

    </body>

<h2><a href="admin">Temporary Link to Admin Page to demonstrate authentication</a></h2>
</html>