<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="head.jsp"%>
    <body>
        <div class="container">
            <c:import url = "header.jsp" />
            <h2>Profile</h2>
            <h3>${user.username}</h3>

        <c:if test="${!empty profileStory}">
            <h3>${user.username}'s Story</h3>
            <p>${profileStory.storyContent}</p>
        </c:if>
    </div>
    </body>
</html>