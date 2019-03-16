<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="head.jsp"%>
    <body>
        <h2>${user.username}</h2>


        <c:if test="${profileStory.storyContent}">
            <h2>${user.username}'s Story</h2>
            <p>${profileStory.storyContent}</p>
        </c:if>
    </body>
</html>