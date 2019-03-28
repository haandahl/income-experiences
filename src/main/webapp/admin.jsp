<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="head.jsp"%>
    <body>
    <div class="container">

        <c:import url = "header.jsp" />

        <h2>Unsuitable!</h2>

        <c:choose>
            <c:when test="${unsuitableStories}">
                <table>
                    <c:forEach var = "story" items="${unsuitableStories}">
                        <tr><td>${story.editDate}</td><td>${story.profileUser}</td><td>${story.storyContent}</td></tr>
                    </c:forEach>

                </table>
            </c:when>
            <c:otherwise>
                <p>There is no history of unsuitable stories to report!</p>
            </c:otherwise>

        </c:choose>
    </div>
    </body>
</html>