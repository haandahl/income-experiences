<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<c:import url = "head.jsp" />
<body>
<div class="container padded-container">
    <c:import url = "header.jsp" />

    <h2>Search Results</h2>

    <section class="row">
        <div class="col">
    <h3>Your Search</h3>

            <div class="card-deck deck-margin">

                <div class="card" id="income-card">
                    <div class="card-body text-center">
                        <h4 class="card-text">${income}</h4>
                        <c:if test="${!empty careerName}"><p class="card-text disclaimer">(median income for ${careerName}*)</p></c:if>
                        <p class="card-text">Based on the number of related surveys available, we searched for those with incomes within ${percentDifferenceSearched} of ${income}.</p>
                    </div>
                </div>

                <c:if test="${!empty careerName}">
                <div class="card" id="career-card">
                    <div class="card-body text-center">
                        <h4 class="card-text">${careerName}</h4>
                        <p class="card-text disclaimer">(*Income information was obtained using the Bureau of Labor Statistics API.
                        BLS.gov cannot vouch for the data or analyses derived from these data after the data have been
                        retrieved from BLS.gov.)</p>
                    </div>
                </div>
                </c:if>

               <div class="card" id="household-card">
                    <div class="card-body text-center">
                        <h4 class="card-text"> ${householdSize} People</h4>
                        <p class="card-text">We only looked for surveys matching the household size of ${householdSize}.</p>
                    </div>
                </div>

            </div>
            <c:if test="${empty matchingSurveys}">
                <p>Sorry, our data does not include a household of ${householdSize}
                    with an income within ${percentDifferenceSearched} of ${income}. Please try another search.</p>
            </c:if>
        </div>
    </section>

            <c:if test="${!empty matchingSurveys}">
        <section class="row">
            <div class="col">
            <h3>Stats</h3>

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
        <h3>Financial Stories</h3>
        <section class="row">
            <div class="col">
                <%@include file="storyTable.jsp"%>
            </div>
        </section>
    </c:if>

</div><!-- end container -->
</body>
</html>
