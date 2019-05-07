<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Resource for evaluating empty tag:
         https://stackoverflow.com/questions/2811626/evaluate-empty-or-null-jstl-c-tags by BalusC--%>

<c:choose>
    <c:when test="${!empty storiesToDisplay}">
        <table class="table table-hover table-responsive">
            <tr><th>Who</th><th>Financial Story</th><th>Flag for Moderator Review</th></tr>
            <c:forEach var = "story" items="${storiesToDisplay}">
                <tr>
                    <td>${story.profileUser.username}</td>
                    <td>${story.storyContent}</td>
                    <c:choose>
                        <c:when test="${!story.unsuitable}">
                            <form action="flag-content" method="post">
                                <td><span class="hidden" style="display-none"><input type="text" name="flaggable-story" value="${story.storyContent}" ></span>
                                    <button type="submit" class="btn btn-warning">Flag</button></td>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <td><span class="text-muted">to be reviewed</span></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <p>There are no stories matching your search.</p>
    </c:otherwise>
</c:choose>