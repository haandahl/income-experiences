<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="head.jsp"%>

<html>
    <body>
        <h2>${user.username}</h2>


        <c:if test="${profileStory.storyContent}">
            <h2>${user.username}'s Story</h2>
            <p>${profileStory.storyContent}</p>
        </c:if>
    </body>
</html>