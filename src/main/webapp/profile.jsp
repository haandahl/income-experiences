<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="head.jsp"%>
    <body>
        <div class="container-fluid">
            <c:import url = "header.jsp" />
            <h2>${user.username}'s Profile</h2>

            <c:if test="${!empty survey}">
                <h3>Survey from ${survey.surveyDate}</h3>
                <li>Household income: ${survey.income}</li>
                <li>Household size: ${survey.familySize}</li>

                <%-- todo color coded boxes for needs/wants, "good" & "bad" colors --%>

                <h4>Impact of other financial factors: ${survey.incomeSkew.description}</h4>
                <h4>Needs</h4>
                <p>${survey.needsDescription.description}</p>

                <h4>Goals</h4>
                <p>${survey.goalsDescription.description}</p>
           </c:if>

            <%-- Normally users should not get to a profile page without having done a survey, but there could
             be exceptions added at the back end, such as the current admin (3/29/29)--%>
            <%--<c:choose>
                <c:when test="${!empty survey}">
                    <li>Household income: ${survey.income}</li>
                    <li>Household size: ${survey.familySize}</li>
                    <li>Impact of other financial factors: ${survey.incomeSkew.description}</li>
                    <li>Survey date: ${survey.surveyDate}</li>

                    <h3>Needs</h3>
                    <p>Overall: ${survey.needsDescription.description}</p>

                    <h3>Goals</h3>
                    <p>Overall: ${survey.goalsDescription.description}</p>


                </c:when>
                <c:otherwise>
                    <p>Oops! You don't have a survey! Get into the back end and get it done!</p>
                </c:otherwise>
            </c:choose>--%>


            <h3>${user.username}'s Story</h3>
            <c:choose>
                <c:when test="${!empty profileStory}">
                        <p>${profileStory.storyContent}</p>
                        <form method="post" action="add-story">
                            <span class="hidden"><textarea name="financial-story">${profileStory.storyContent}</textarea></span>
                            <button type="submit" class="btn btn-primary">Edit Your Story</button>
                        </form>
                </c:when>
                <c:otherwise>
                    <%@include file="storyArea.jsp"%>

                </c:otherwise>
            </c:choose>

            <%-- TODO before code review - run plugins, choose favorite --%>
            <%-- TODO - conditional display of story/ edit --%>
            <%-- TODO screenshots of logging, test coverage --%>
            <%-- TODO video demo can be loaded to YouTube and linked in GitHub --%>
            <%-- TODO remember I can't project --%>




        </div>
    </body>
</html>