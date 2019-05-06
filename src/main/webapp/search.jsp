<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <c:import url = "head.jsp" />
        <body>
        <div class="container">
        <c:import url = "header.jsp" />


            <section class="row">
                <div class="col">

                    <h2>Search Experiences</h2>
                    <!-- TODO finesse -->
                    <p> There are a couple different options for you. You may search for stats and stories, which will show
                        you survey data and contributors' financial stories for the household size you choose and incomes near
                        either an income that you choose or the median income of a career of interest. The career options
                        are the top five careers, according to US News, and the top five careers with projected new jobs,
                        according to the Bureau of Labor Statitics. (These were selected according to 2019 data).
                        You may select the income and household size that interests you.
                    </p>
                    <p>
                        Another option is to search the financial stories for a topic that interests you, and you'll see relevant
                        stories and be able to link to the profile of their authors.
                    </p>
                </div>
            </section>

            <section class="row">

                <!-- todo style forms to appear more separate, maybe colored differently, give 1st one more room, check -->

                <div class="col-md-7">
                    <div class="card">
                        <div class="card-header">

                            <h3>Search for Stats and Stories Matching a Household Size and Income</h3>

                        </div>
                        <c:if test="${!empty validationMessage}">
                            <p class="bg-warning">${validationMessage}</p>
                        </c:if>
                        <div class="card-body">
                <form method="post" action="search-stats">
                    <!-- Resource for blank defaults in select groups:
                    https://stackoverflow.com/questions/8605516/default-select-option-as-blank -->
                    <p>You may select a career (and we'll find its median income) or you may skip the career and enter the income directly.</p>
                    <div class="form-group">
                        <label for="careerInput">Career:</label>

                        <!-- TODO keep selection from erroneous submittal -->

                        <select class="form-control" id="careerInput" name="careerInput">
                            <option selected value="">(select a job or skip this and enter income below) </option>
                            <optgroup label="Both Top Jobs and Most New Jobs">
                                <option value="bestAndMost1">${incomeExperiencesProperties["bestAndMost1.display.name"]}</option>
                            </optgroup>
                            <optgroup label="Top Jobs">
                                <option value="best1">${incomeExperiencesProperties["best1.display.name"]}</option>
                                <option value="best2">${incomeExperiencesProperties["best2.display.name"]}</option>
                                <option value="best3">${incomeExperiencesProperties["best3.display.name"]}</option>
                                <option value="best4">${incomeExperiencesProperties["best4.display.name"]}</option>
                            </optgroup>
                            <optgroup label="Most New Jobs">
                                <option value="most1">${incomeExperiencesProperties["most1.display.name"]}</option>
                                <option value="most2">${incomeExperiencesProperties["most2.display.name"]}</option>
                                <option value="most3">${incomeExperiencesProperties["most3.display.name"]}</option>
                                <option value="most4">${incomeExperiencesProperties["most4.display.name"]}</option>
                            </optgroup>
                         </select>
                    </div>
                    <div class="form-group">
                        <label for="income">Before-tax annual income:</label>

                        <!-- TODO keep selection from erroneous submittal -->

                        <input type="number" class="form-control" id="income" name="income" placeholder="(leave empty if searching by career)">
                    </div>
                    <div class="form-group">
                        <label for="householdSize">Household size:</label>

                        <!-- TODO keep selection from erroneous submittal -->

                        <select class="form-control" id="householdSize" name="householdSize">
                            <option disabled selected value>(select a household size)</option>
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                            <option>6</option>
                            <option>7</option>
                            <option>8</option>
                            <option>9</option>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-primary">Search for Stats and Stories</button>
                </form>
                </div>
                    </div><!-- end card body -->
                </div><!-- end card -->

                <div class="col-md-5">
                    <div class="card">
                        <div class="card-header">

                <h3>Search by Topic</h3>
                        </div>
                        <div class="card-body">
                <form method="post" action="search-topics">
                    <div class="form-group">
                        <label for="topic">Topic(s):</label>
                        <input type="text" class="form-control" id="topic" name="topic" placeholder="example: frugal dumpster diving" >
                    </div>
                    <button type="submit" class="btn btn-primary">Search by Topic</button>
                </form>
                        </div><!-- end card body-->
                    </div><!-- end card -->
            </div>
            </section>
    </body>
</html>