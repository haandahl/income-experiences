<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="head.jsp"%>
    <body>
        <div class="container">
            <c:import url = "header.jsp" />
            <h2>Profile</h2>
            <h3>${user.username}</h3>

            <%-- Normally users should not get to a profile page without having done a survey, but there could
             be exceptions added at the back end, such as the current admin (3/29/29)--%>
            <c:choose>
                <c:when test="${!empty survey}">
                    <li>Household income: ${survey.income}</li>
                    <li>Household size: ${survey.familySize}</li>
                    <li>Impact of other financial factors: ${survey.incomeSkew.description}</li>
                    <%-- TODO investigate above, example output: Impact of other financial factors: Unmet goals caused frustration.--%>

                    <li>Survey date: ${survey.surveyDate}</li>

                    <h3>Needs</h3>
                    <p>Overall: ${survey.needsDescription.description}</p>

                    <%-- TODO - build when servlet & assoc logic are ready
                    <h4>Needs Met by Income</h4>
                    <h4>Unmet Needs</h4>
                    --%>

                    <h3>Goals</h3>
                    <p>Overall: ${survey.goalsDescription.description}</p>

                    <%-- TODO - build when servlet & assoc logic are ready
                    <h4>Goals Met by Income</h4>
                    <h4>Unmet Goals</h4>
                    --%>

                </c:when>
                <c:otherwise>
                    <p>Oops! You don't have a survey! Get into the back end and get it done!</p>
                </c:otherwise>
            </c:choose>


            <h3>${user.username}'s Story</h3>
            <c:if test="${!empty profileStory}">
              <p>${profileStory.storyContent}</p>
            </c:if>

            <%-- TODO - put an input here for adding/revising story --%>

        </div>
    </body>
</html>