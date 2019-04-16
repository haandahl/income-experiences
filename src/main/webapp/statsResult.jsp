<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<c:import url = "head.jsp" />
<body>
<div class="container">
    <c:import url = "header.jsp" />
    <h2>Search Results</h2>
    <h3>Your Search</h3>
    <ul>
        <c:choose>
            <c:when test="${!empty careerName}">
                <li>Career: ${careerName}</li>
                <li>Median income from BLS*: ${income}</li>
            </c:when>
            <c:otherwise>
                <li>Income: ${income}</li>

            </c:otherwise>
        </c:choose>
        <li>Household size: ${householdSize}</li>
    </ul>
    <c:if test="${!empty careerName}">
        <p>* While income information was obtained using the Bureau of Labor Statistics API,
            BLS.gov cannot vouch for the data or analyses derived from these data after the data have been
            retrieved from BLS.gov.</p>
    </c:if>


    <c:choose>
            <c:when test="${empty matchingSurveys}">
                <p>Our data does not include a household of ${householdSize}
                    with an income within ${percentDifferenceSearched} of ${income}. Please try another search.</p>
             </c:when>
            <c:otherwise>
                <p>Results are for the household size that you searched and includes incomes within ${percentDifferenceSearched}
                    of your search income.</p>
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
