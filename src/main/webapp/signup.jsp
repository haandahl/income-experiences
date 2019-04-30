<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<c:import url = "head.jsp" />
<body>
<div class="container">
    <c:import url = "header.jsp" />

    <%-- TODO -- apply same types of validation steps here as on search form --%>

    <div class="card">
        <div class="card-header">

            <h2>Sign Up</h2>
        </div>
        <div class="card-body">
            <form method="post" action="sign-up">

                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" class="form-control" id="username" name="username" placeholder="name to display to other users">
                </div>

                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="password">
                </div>

                <div class="form-group">
                    <label for="password2">Re-type password:</label>
                    <input type="password" class="form-control" id="password2" name="password2" placeholder="password">
                </div>

                <div class="form-group">
                    <label for="householdSizeInput">Household size (anyone in the home dependent on the household income):</label>

                    <select class="form-control" id="householdSizeInput" name="householdSizeInput">
                        <option disabled selected value="0">(select a household size)</option>
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

                <div class="form-group">
                    <label for="surveyIncome">The household's most recent total yearly income, before tax:</label>
                    <input type="number" class="form-control" id="surveyIncome" name="surveyIncome" placeholder="00000.00">
                </div>

                <div class="form-group">
                    <label for="surveyNeeds">Select the best description of how well the household's income met everyone's needs:</label>
                    <p>Needs could include food, housing, utilities, health care, clothing, transportation, child care, etc.</p>

                    <select class="form-control" id="surveyNeeds" name="surveyNeeds">
                            <option disabled selected value="0">(select one)</option>
                            <option value="1">Severely unmet needs caused permanent harm.</option>
                            <option value="2">Unmet needs caused illness or decreased ability at work or school.</option>
                            <option value="3">Unmet needs caused discomfort.</option>
                            <option value="4">Needs were generally met.</option>
                            <option value="5">All needs were comfortably met.</option>
                        </select>
                </div>

                <div class="form-group">
                    <label for="surveyGoals">Select the best description of how well the household's income met everyone's goals:</label>
                    <p>Goals could include savings, education, upgraded necessities, hobbies, travel, gifts/donations, services, etc.</p>

                    <select class="form-control" id="surveyGoals" name="surveyGoals">
                        <option disabled selected value="0">(select one)</option>
                        <option value="1">Unmet goals caused insecurity or high stress.</option>
                        <option value="2">Unmet goals caused frustration.</option>
                        <option value="3">Many goals were met.</option>
                        <option value="4">Most or all goals were easily met.</option>
                        <option value="5">Income allowed for new or expanding financial goals.</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="surveyIncomeSkew">Select the best description of how much other financial factors helped meet needs and goals:</label>
                    <p>Examples could include reducing savings, increasing debt, or receiving gifts of time or services. </p>

                    <select class="form-control" id="surveyIncomeSkew" name="surveyIncomeSkew">
                        <option disabled selected value="0">(select one)</option>
                        <option value="1">little or no impact</option>
                        <option value="2">some impact</option>
                        <option value="3">strong impact</option>
                     </select>
                </div>

                <%-- todo - nice to do - let user review entries before final submit --%>

                <c:if test="${!empty validationMessage}">
                    <p>${validationMessage}</p>
                </c:if>
                <button type="submit" class="btn btn-primary">Sign Up</button>
            </form>
        </div>
    </div><!-- end card body -->
</div><!-- end card -->



</div>
</body>
</html>