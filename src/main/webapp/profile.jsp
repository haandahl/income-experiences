<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="head.jsp"%>
    <body>
        <div class="container padded-container">
            <c:import url = "header.jsp" />
            <h2>${user.username}'s Profile</h2>

            <div class="card" id="profile-card">

            <c:if test="${!empty survey}">

                <div class="card-header bg-light">
                    <h3>$${survey.income} for a household of ${survey.familySize}</h3>
                    <p>(survey completed ${survey.surveyDate})</p>
               </div>
                <div class="card-body">

                     <div class="card-deck deck-margin">

                        <div class="card" id="profileNeeds">
                            <div class="card-body text-center">
                                <h4 class="card-text">Needs</h4>
                                <p class="card-text">${survey.needsDescription.description}</p>
                            </div>
                          </div>


                        <div class="card" id="profileGoals">
                            <div class="card-body text-center">
                                <h4 class="card-text">Goals</h4>
                                <p class="card-text">${survey.goalsDescription.description}</p>
                            </div>
                        </div>


                        <div class="card" id="profileSkew">
                            <div class="card-body text-center">
                                <h4 class="card-text">Reliance on Non-Income</h4>
                                <p class="card-text">${survey.incomeSkew.description}</p>
                            </div>
                        </div>
                    </div>
                </div>

           </c:if>
            </div>

            <c:choose>
                <c:when test="${user.ableToWrite}">
                    <c:choose>
                        <c:when test="${!empty profileStory && empty oldStory}">

                            <h3>${user.username}'s Story</h3>
                            <p>${profileStory.storyContent}</p>
                            <br />
                            <form action="edit-story" method="post">
                                <span style="display: none"><textarea name="old-story">${profileStory.storyContent}</textarea></span>
                                <button type="submit" class="btn btn-info">Edit Your Story</button>
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
                </c:when>
                <c:otherwise>
                    <p class="bg-warning feedback">Due to content unsuitable for the Income Experiences website, your financial story has been removed.</p>
                </c:otherwise>
            </c:choose>

            <%-- TODO before code review - run plugins, choose favorite --%>
            <%-- TODO screenshots of logging, test coverage --%>
            <%-- TODO video demo can be loaded to YouTube and linked in GitHub --%>
            <%-- TODO remember I can't project --%>

        </div>
    </body>
</html>