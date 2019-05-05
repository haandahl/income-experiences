<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="head.jsp"%>
    <body>
    <div class="container">

        <c:import url = "header.jsp" />

        <h2>Items for Moderator Review</h2>

        <c:if test="${!empty adminFeedbackMessage}">
            <p id="admin-feedback" class="bg-info">${adminFeedbackMessage}</p>
        </c:if>

        <c:choose>
            <c:when test="${!empty itemsToReview}">
                    <c:forEach var = "item" items="${itemsToReview}">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-4">
                                        <h3>${item.profileUser.username}</h3>
                                        <p>Number of flagged stories in archive: ${item.profileUser.archivedUnsuitableStories}</p>
                                    </div>
                                    <div class="col-md-8">
                                        <h3>Flagged Story</h3>
                                        <p>${item.storyContent}</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <div class="admin-form">
                                            <form action="remove-user" method="post">
                                                <span class="hidden"><input type="text" name="user-to-remove" value="${item.profileUser.id}" ></span>
                                                <button type="submit" class="btn btn-danger btn-block">Remove User</button>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="admin-form">
                                            <form action="block-user" method="post">
                                                <span class="hidden"><input type="text" name="user-to-block" value="${item.profileUser.id}" ></span>
                                                <button type="submit" class="btn btn-warning btn-block">Block User & Hide Story</button>
                                            </form>
                                        </div>
                                    </div>
                                        <%--  todo reveal as implemented
                                        <div class="col">
                                            <div class="admin-form">
                                                <form action="hide-content" method="post">
                                                    <span class="hidden"><input type="text" name="story-to-archive" value="${item.id}" ></span>
                                                    <button type="submit" class="btn btn-secondary btn-block">Just Hide Story</button>
                                                </form>
                                            </div>
                                        </div>
                                        --%>

                                    <div class="col">
                                        <div class="admin-form">
                                            <form action="unflag" method="post">
                                                <span class="hidden"><input type="text" name="story-to-unflag" value="${item.id}" ></span>
                                                <button type="submit" class="btn btn-success btn-block">UnFlag Story</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                     </c:forEach>
            </c:when>
            <c:otherwise>
                <p>There are no financial stories flagged for review.</p>
            </c:otherwise>

        </c:choose>
    </div>
    </body>
</html>