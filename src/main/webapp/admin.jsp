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

        <%--   THIS IS COPIED FROM TEXTRESULT.JSP - IT LOOKS LIKE I HAD IN MIND TO LAY OUT A HISTORY OF SOMEBODY'S STORIES?  MORE OF AN ADMIN THING??

        <c:choose>
            <c:when test="${!empty textResult}">
                <table>
                    <c:forEach var = "result" items="${textResult}">
                        <tr>
                            <td>${result.username}</td>
                        </tr>
                        <c:forEach var = "story" items="${result.storyVersionsForUserProfile}">
                            <tr>
                                <form acton="flag-content" method=""post">
                                <td><span class="hidden"><input type="text" value="${story.storyContent}" ></span>${story.storyContent}</td>
                                </form>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <p>No results were found.</p>
            </c:otherwise>
        </c:choose>

       --%>

<%--
    1. find stories currently visible and unsuitable
    2. find profile user with that story
    3. for each user, list ALL their stories marked unsuitable (visible and invisible) or simply keep a tally
    4. display user, current story, tally of invisible/unsuitable
    5. options: remove user, hide story and block user, mark story suitable

--%>

    </div>
    </body>
</html>