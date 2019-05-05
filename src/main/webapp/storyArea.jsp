<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul>
    <li>Which needs are hardest to meet? How have you coped?</li>
    <li>What are your goals for spending beyond the most basic needs? Which have you been able to do, and which have been out of reach?  What is that like?</li>
    <li>What has affected your ability to meet your needs and goals besides income?  Maybe gifts, help, loans?</li>
    <li>What big life events are going on and how do they affect your finances?</li>
    <li>Compare your income, family size, and lifestyle to what they used to be. What has surprised you about the changes?</li>
    <li>How are you feeling about your financial future, and why?</li>
    <li>What would you like to know from others posting on this site?</li>
</ul>

<form action="add-story" method="post">

        <c:choose>
            <c:when test="${!empty oldStory}">
                <div class="form-group">
                    <textarea name="financial-story" rows="4" cols="50" placeholder="enter your story here">${oldStory}</textarea>
                    <br />
                    <button type="submit" class="btn btn-primary">Share Your Revised Story</button>
                </div>
            </c:when>
            <c:otherwise>
                <div class="form-group">
                    <textarea name="financial-story" rows="4" cols="50" placeholder="enter your story here"></textarea>
                    <br />
                    <button type="submit" class="btn btn-primary">Share Your Story</button>
                </div>
            </c:otherwise>
        </c:choose>

</form>