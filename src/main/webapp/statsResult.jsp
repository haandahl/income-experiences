<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<c:import url = "head.jsp" />
<body>
<div class="container">
    <c:import url = "header.jsp" />
    <h2>Search Results</h2>
    <p>
        You searched for experiences with incomes similar to those earned by a ${careerName}.
        You searched for experiences with a household size of ${householdSize}.
        The statistics and stories below represent site contributors with incomes within ${percentDifferenceSearched}
        of your search income (${income}).
    </p>

    <!-- TODO incorporate requirement - Users of the public API should cite the date that data
            were accessed or retrieved using the API. Users must clearly state that
            “BLS.gov cannot vouch for the data or analyses derived from these data after the data have been
            retrieved from BLS.gov.”  -->
    <c:choose>
            <c:when test="${empty matchingSurveys}">
                <p>Our data does not include a household of ${householdSize}
                    with an income within ${percentDifferenceSearched} of ${income}. Please try another search.</p>
             </c:when>
            <c:otherwise>
                <h3>Survey Dump</h3>
                <p>${matchingSurveys}</p>

                <h3>Stats</h3>
                <p>this feature is under development</p>


                <h3>Financial Stories</h3>
                <p>this feature is under development</p>
            </c:otherwise>
    </c:choose>

</div>
</body>
</html>
