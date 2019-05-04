<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="head.jsp"%>
    <body>
    <div class="container">

        <c:import url = "header.jsp" />

        <h2>Items for Moderator Action</h2>

        <c:choose>
            <c:when test="${!empty itemsToReview}">
                <table class="table table-hover table-responsive">
                    <tr><th>Who</th><th>No. of Past Flags</th><th class="limited-width">Current Story</th><th></th>Options</th></tr>
                    <c:forEach var = "item" items="${itemsToReview}">
                        <tr>
                            <td>${item.profileUser.username}</td>
                            <td class="limited-width">${item.profileUser.archivedUnsuitableStories}</td>
                            <td>${item.storyContent}</td>

                            <%-- TODO come back and wrap in choose/when/otherwise so admin can see results of actions --%>
                            <td>
                                <div class="admin-form">
                                    <form action="unflag" method="post">
                                        <span class="hidden"><input type="text" name="story-to-unflag" value="${item}" ></span>
                                        <button type="submit" class="btn btn-success">Unflag Story</button>
                                    </form>
                                </div>
                                <div class="admin-form">
                                    <form action="hide-content" method="post">
                                        <span class="hidden"><input type="text" name="story-to-archive" value="${item}" ></span>
                                        <button type="submit" class="btn btn-secondary">Hide Story</button>
                                    </form>
                                </div>
                                <div class="admin-form">
                                    <form action="block-user" method="post">
                                        <span class="hidden"><input type="text" name="user-to-block" value="${item.profileUser}" ></span>
                                        <button type="submit" class="btn btn-warning">Block User</button>
                                    </form>
                                </div>
                                <div class="admin-form">
                                    <form action="remove-user" method="post">
                                         <span class="hidden"><input type="text" name="user-to-remove" value="${item.profileUser}" ></span>
                                         <button type="submit" class="btn btn-danger">Remove User</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </c:when>
            <c:otherwise>
                <p>There are no financial stories flagged for review.</p>
            </c:otherwise>

        </c:choose>
    </div>
    </body>
</html>