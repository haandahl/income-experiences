<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<p>Please share your financial story here. This is free-form, but here are some ideas:</p>
<ul>
    <li>Which financial need have and have not been met?</li>
    <li>Which financial goals have and have not been met?</li>
    <li>What has impacted your ability to meet your needs and goals besides income?  Maybe gifts, help, loans?</li>
    <li>What big life events are going on and how do they affect your finances?</li>
    <li>Compare your income, family size, and lifestyle to what they used to be.</li>
    <li>How are you feeling about your financial future, and why?</li>
    <li>What would you like to know from others posting on this site?</li>

</ul>

<form action="add-story" method="post">
<textarea name="financial-story" rows="4" cols="50" max-length="2000" placeholder="enter your story here">${profileStory.content}</textarea>

    <button type="submit" class="btn btn-primary">
        <c:choose>
            <c:when test="${!empty profileStory}">
                Share Your Revised Story
            </c:when>
            <c:otherwise>
                Share Your Story
            </c:otherwise>
        </c:choose>
    </button>
</form>