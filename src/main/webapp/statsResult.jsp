<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<c:import url = "head.jsp" />
<body>
<div class="container-fluid">
    <c:import url = "header.jsp" />

    <h2>Search Results</h2>

    <section class="row">
        <div class="col">
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
            </c:otherwise>
    </c:choose>
        </div>
    </section>




            <c:if test="${!empty matchingSurveys}">
        <section class="row">
            <div class="col">
            <h3>Stats</h3>

                <!-- todo style charts to stack for better readability, or override page boundaries for bootstrap -->

                <div class="row">
                   <div class="col-md-6 col-lg-4 chart">
                       <h4 class="chart-title">How Well Needs Were Met</h4>
                       <div class="data-chart">
                           <canvas id="needsChart" width="250" height="350"></canvas>
                       </div>
                   </div>
                    <div class="col-md-6 col-lg-4 chart">
                        <h4 class="chart-title">How Well Goals Were Met</h4>
                        <div class="data-chart">
                            <canvas id="goalsChart" width="250" height="350"></canvas>
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-4 chart">
                        <h4 class="chart-title">Impact of Gifts, Borrowing, etc.</h4>
                        <div class="data-chart">
                            <canvas id="incomeSkewChart" width="250" height="350"></canvas>
                        </div>
                    </div>
                    <div class="col-xs-0 col-md-6 col-lg-0"></div>
                </div>
            </div>
        </section>
    </c:if>
    <c:if test="${!empty matchingSurveys}">
        <section class="row">
            <div class="col">

                <h3>Financial Stories</h3>
                 <c:choose>
                    <c:when test="${!empty matchingStories}">

                        <%-- TODO provide link to user profile --%>

                        <table class="table">
                            <thead class="thead-light">
                                <tr>
                                    <th>Person</th><th>Financial Story</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var = "story" items="${matchingStories}">
                                    <tr><td>${story.profileUser.username}</td><td>${story.storyContent}</td></tr>
                                </c:forEach>
                            </tbody>

                        </table>
                    </c:when>
                    <c:otherwise>
                        <p>There are no financial stories available that match your search.</p>
                    </c:otherwise>

                </c:choose>
            </div>
        </section>
    </c:if>


</div>
</body>
</html>
