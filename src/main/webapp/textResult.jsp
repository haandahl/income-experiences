<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="head.jsp"%>
    <body>
    <div class="container">
        <c:import url = "header.jsp" />
        <h2> Results for the search</h2>
        <p>You searched for "${topic}"</p>

        <%-- Resource for evaluating empty tag:
         https://stackoverflow.com/questions/2811626/evaluate-empty-or-null-jstl-c-tags by BalusC--%>

        <c:choose>
            <c:when test="${!empty textResult}">
                <table class="table table-hover table-responsive">
                    <tr><th>Site User</th><th>Financial Story</th><th>Flag for Moderator Review</th></tr>
                    <c:forEach var = "result" items="${textResult}">
                        <tr>
                            <td>${result.profileUser.username}</td>
                            <td>${result.storyContent}</td>
                            <c:choose>
                                <c:when test="${!result.unsuitable}">
                                    <form action="flag-content" method="post">
                                        <td><span class="hidden"><input type="text" name="flaggable-story" value="${result.storyContent}" ></span>
                                        <button type="submit" class="btn btn-warning">Flag</button></td>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <td><span class="text-muted">moderator will review</span></td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <p>No results were found.</p>
            </c:otherwise>
        </c:choose>

    </div>

    </body>
</html>