<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="head.jsp"%>
    <body>


        <div class="container">
            <c:import url = "header.jsp" />

        <h2>${user.username}</h2>
        <p>The username above does not reflect the logged in user.  Currently just demonstrating authentication.</p>


        <c:if test="${profileStory.storyContent}">
            <h2>${user.username}'s Story</h2>
            <p>${profileStory.storyContent}</p>
        </c:if>
    </div>
    </body>
</html>