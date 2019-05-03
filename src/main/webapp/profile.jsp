<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="head.jsp"%>
    <body>
        <div class="container">
            <c:import url = "header.jsp" />
            <h2>${user.username}'s Profile</h2>

            <c:if test="${!empty survey}">
                <h3>Survey from ${survey.surveyDate}</h3>
                <li>Household income: ${survey.income}</li>
                <li>Household size: ${survey.familySize}</li>

            <div class="row">

                <div class="col-md-4 profile-box" id="profileNeeds">
                    <h4>Needs</h4>
                    <p>${survey.needsDescription.description}</p>
                </div>
                <div class="col-md-4 profile-box" id="profileGoals">
                    <h4>Goals</h4>
                    <p>${survey.goalsDescription.description}</p>
                </div>
                <div class="col-md-4 profile-box" id="profileSkew">
                    <h4>Reliance on Non-Income</h4>
                    <p>${survey.incomeSkew.description}</p>
                </div>
            </div>
           </c:if>

           <c:choose>
                <c:when test="${!empty profileStory && empty oldStory}">

                    <h3>${user.username}'s Story</h3>
                    <p>${profileStory.storyContent}</p>
                    <form action="edit-story" method="post">
                            <span class="hidden"><textarea name="old-story">${profileStory.storyContent}</textarea></span>
                            <button type="submit" class="btn btn-primary">Edit Your Story</button>
                    </form>

                </c:when>
                <c:when test="${!empty profileStory && !empty oldStory}">
                    <h3>Tips for Updating Your Story </h3>
                    <p>Add to the story you've already told - don't lose your past!  Also consider these points of interest:</p>
                    <%@include file="storyArea.jsp"%>
                </c:when>
                <c:otherwise>
                    <h3>Share Your Story</h3>
                    <p>Let people know what your money situation has really been like. This is free-form, but here are some ideas:</p>
                    <%@include file="storyArea.jsp"%>

                </c:otherwise>
            </c:choose>

            <%-- TODO before code review - run plugins, choose favorite --%>
            <%-- TODO screenshots of logging, test coverage --%>
            <%-- TODO video demo can be loaded to YouTube and linked in GitHub --%>
            <%-- TODO remember I can't project --%>

        </div>
    </body>
</html>