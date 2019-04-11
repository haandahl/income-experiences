<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <c:import url = "head.jsp" />
        <body>
        <div class="container">
        <c:import url = "header.jsp" />


            <h2>Search Experiences</h2>
            <!-- TODO finesse -->
            <p> There are three ways to search. You may search by career and household size.  The career options
                are the top five careers according to US News and the top five careers with projected new jobs.
                (These were selected according to 2019 data).
                You may select the income and household size that interests you.
                You'll see what our same-sized households with the closest incomes have experienced. Otherwise,
                you may search the financial stories for a topic that interests you, and you'll see relevant
                stories and be able to link to the profile of their authors.
            </p>


            <!-- TODO incorporate requirement - Users of the public API should cite the date that data
                    were accessed or retrieved using the API. Users must clearly state that
                    “BLS.gov cannot vouch for the data or analyses derived from these data after the data have been
                    retrieved from BLS.gov.”  -->

            <!-- TODO revise so that career drop-down populates the income, and it's all one form. -->

            <h3>Search by Career and Household Size</h3>
            <form method="post" action="search-stats">
                <div class="form-group">
                    <label for="careerInput">Career:</label>
                    <!-- TODO have a default (select from options) input with no value -->
                    <select class="form-control" id="careerInput" name="careerInput">
                        <optgroup label="Both Top Jobs and Most New Jobs">
                            <option value="softwareDeveloperOrProgrammer">Software Developer</option>
                        </optgroup>
                        <optgroup label="Top Jobs">
                            <option value="statistician">Statistician</option>
                            <option value="physicianAssistant">Physician Assistant</option>
                            <option value="dentist">Dentist</option>
                            <option value="orthodontist">Orthodontist</option>
                        </optgroup>
                        <optgroup label="Most New Jobs">
                            <option value="personalCareAndServiceOccupations">Personal Care Aide</option>
                            <option value="foodPrepAndServing">Food Preparation or Serving Worker</option>
                            <option value="registeredNurse">Registered Nurse</option>
                            <option value="homeHealthAide">Home Health Aide</option>
                        </optgroup>
                    </select>
                </div>
                <div class="form-group">
                    <label for="householdSize">Household size:</label>
                    <select class="form-control" id="householdSize" name="householdSize">
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


           <h3>Search by Income and Household Size</h3>
            <form method="post" action="search-stats">
                <div class="form-group">
                    <label for="income">Before-tax annual income:</label>
                    <input type="number" class="form-control" id="income" name="income" placeholder="######">
                </div>
                <div class="form-group">
                    <label for="householdSize">Household size:</label>
                    <select class="form-control" id="householdSize" name="householdSize">
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
            </form>

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