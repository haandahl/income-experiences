<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="head.jsp"%>
    <body>
        <div class="container">
            <c:import url = "header.jsp" />
            <h2>Profile</h2>
            <h3>${user.username}</h3>
            <li>Household income: ${survey.income}</li>
            <li>Household size: ${survey.familySize}</li>
            <li>Impact of other financial factors: ${survey.incomeSkew.description}</li>
            <li>Survey date: ${survey.date}</li>

            <h3>Needs</h3>
            <p>Overall: ${survey.needsDescription.description}</p>

            <h4>Needs Met by Income</h4>
            <%-- <c:forEach var = "need" items="${survey.needsUnmet}"> --%>
                <%-- TODO - I want to do for each property value of false, list the property name.  but maybe that's not a thing --%>
            <%-- </c:forEach>--%>
            <h4>Unmet Needs</h4>

            <h3>Goals</h3>
            <p>Overall: ${survey.goalsDescription.description}</p>

            <h4>Goals Met by Income</h4>

            <h4>Unmet Goals</h4>

            <h3>${user.username}'s Story</h3>
            <c:if test="${!empty profileStory}">
              <p>${profileStory.storyContent}</p>
            </c:if>
            <%-- TODO - put an input here for adding/revising story --%>
        </div>
    </body>
</html>