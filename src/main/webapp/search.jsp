<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <c:import url = "head.jsp" />
        <body>
        <div class="container">
        <c:import url = "header.jsp" />


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

            <!-- TODO incorporate requirement - Users of the public API should cite the date that data
                    were accessed or retrieved using the API. Users must clearly state that
                    “BLS.gov cannot vouch for the data or analyses derived from these data after the data have been
                    retrieved from BLS.gov.”  -->

            <!-- TODO revise so that career drop-down populates the income, and it's all one form. -->

            <h3>Search for Stats and Stories Matching a Household Size and Income</h3>
            <form method="post" action="search-stats">
                <!-- Resource for blank defaults in select groups:
                https://stackoverflow.com/questions/8605516/default-select-option-as-blank -->
                <p>You may select a career (and we'll find its median income) or you may skip the career and enter the income directly.</p>
                <div class="form-group">
                    <label for="careerInput">Career:</label>
                    <!-- TODO have a default (select from options) input with no value -->
                    <select class="form-control" id="careerInput" name="careerInput">
                        <option selected value="">(select a job) </option>
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
                    <input type="number" class="form-control" id="income" name="income" placeholder="(leave empty if searching by career)">
                </div>
                <div class="form-group">
                    <label for="householdSize">Household size:</label>
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
                <button type="submit" class="btn btn-primary">Search by Career and Household Size</button>
            </form>


           <!--<h3>Search by Income and Household Size</h3>
            <form method="post" action="search-stats">
                <div class="form-group">
                    <label for="income">Before-tax annual income:</label>
                    <input type="number" class="form-control" id="income" name="income" placeholder="######">
                </div>
                <div class="form-group">
                    <label for="householdSize">Household size:</label>
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
                <button type="submit" class="btn btn-primary">Search by Income and Household Size</button>
            </form>-->

            <h3>Search by Topic</h3>
            <form method="post" action="search-topics">
                <div class="form-group">
                    <label for="topic">Topic(s):</label>
                    <input type="text" class="form-control" id="topic" name="topic" placeholder="example: frugal dumpster diving" >
                </div>
                <button type="submit" class="btn btn-primary">Search by Topic</button>
            </form>
        </div>
        </body>
</html>