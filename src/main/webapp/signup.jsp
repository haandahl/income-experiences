<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<c:import url = "head.jsp" />
<body>
<div class="container padded-container">
    <c:import url = "header.jsp" />

     <div class="card">
        <div class="card-header">

            <h2>Sign Up</h2>
        </div>
        <div class="card-body <c:if test="${!empty validationMessage}">form-error</c:if>">
            <c:if test="${!empty validationMessage}">
                <p class="bg-warning feedback">${validationMessage}</p>
            </c:if>

            <form method="post" action="sign-up">

                <div class="row">
                    <div class="col-lg-6">
                        <h3>Account</h3>
                        <div class="form-group">
                            <label for="username">Username:</label>
                            <c:choose>
                                <c:when test="${empty signUpAttempt.username}">
                                    <input type="text" class="form-control" id="username" name="username" placeholder="name to display to other users" required>
                                </c:when>
                                <c:otherwise>
                                    <input type="text" class="form-control" id="username" name="username" value="${signUpAttempt.username}" required>
                                </c:otherwise>
                            </c:choose>

                        </div>

                         <div class="form-group">
                            <label for="password">Password:</label>
                            <c:choose>
                                <c:when test="${empty signUpAttempt.password}">
                                    <input type="password" class="form-control" id="password" name="password" placeholder="password" required>
                                </c:when>
                                <c:otherwise>
                                    <input type="password" class="form-control" id="password" name="password"  value="${signUpAttempt.password}" required>
                                </c:otherwise>
                            </c:choose>

                        </div>

                        <div class="form-group">
                            <label for="password2">Re-type password:</label>
                            <c:choose>
                                <c:when test="${empty signUpAttempt.password2}">
                                    <input type="password" class="form-control" id="password2" name="password2" placeholder="password" required>
                                </c:when>
                                <c:otherwise>
                                    <input type="password" class="form-control" id="password2" name="password2"  value="${signUpAttempt.password2}" required>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div class="col-lg-6">
                        <h3>Household Stats</h3>
                        <div class="form-group">
                            <label for="householdSizeInput">Household size (anyone in the home dependent on the household income):</label>

                            <select class="form-control" id="householdSizeInput" name="householdSizeInput" required>
                                <option disabled selected value="0">(select a household size)</option>
                                <option <c:if test="${signUpAttempt.householdSize.equals('1')}">selected</c:if>>1</option>
                                <option <c:if test="${signUpAttempt.householdSize.equals('2')}">selected</c:if>>2</option>
                                <option <c:if test="${signUpAttempt.householdSize.equals('3')}">selected</c:if>>3</option>
                                <option <c:if test="${signUpAttempt.householdSize.equals('4')}">selected</c:if>>4</option>
                                <option <c:if test="${signUpAttempt.householdSize.equals('5')}">selected</c:if>>5</option>
                                <option <c:if test="${signUpAttempt.householdSize.equals('6')}">selected</c:if>>6</option>
                                <option <c:if test="${signUpAttempt.householdSize.equals('7')}">selected</c:if>>7</option>
                                <option <c:if test="${signUpAttempt.householdSize.equals('8')}">selected</c:if>>8</option>
                                <option <c:if test="${signUpAttempt.householdSize.equals('9')}">selected</c:if>>9</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="surveyIncome">The household's most recent total yearly income, before tax:</label>
                            <c:choose>
                                <c:when test="${empty signUpAttempt.income}">
                                    <input type="number" class="form-control" id="surveyIncome" name="surveyIncome" min="0" placeholder="Example: 40000" required>
                                </c:when>
                                <c:otherwise>
                                    <input type="number" class="form-control" id="surveyIncome" name="surveyIncome" min="0" value="${signUpAttempt.income}" required>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-lg-2"></div>

                    <div class="col-lg-8">
                        <h3>About the Household's Year</h3>
                        <div class="form-group">
                            <label for="surveyNeeds">Select the best description of how well the household's income met everyone's needs:</label>
                            <p class="text-info">Needs could include food, housing, utilities, health care, clothing, transportation, child care, etc.</p>

                            <select class="form-control" id="surveyNeeds" name="surveyNeeds" required>
                                    <option disabled selected value="0">(select one)</option>
                                    <option value="1" <c:if test="${signUpAttempt.needs.equals('1')}">selected</c:if>>Severely unmet needs caused permanent harm.</option>
                                    <option value="2" <c:if test="${signUpAttempt.needs.equals('2')}">selected</c:if>>Unmet needs caused illness or decreased ability at work or school.</option>
                                    <option value="3" <c:if test="${signUpAttempt.needs.equals('3')}">selected</c:if>>Unmet needs caused discomfort.</option>
                                    <option value="4" <c:if test="${signUpAttempt.needs.equals('4')}">selected</c:if>>Needs were generally met.</option>
                                    <option value="5" <c:if test="${signUpAttempt.needs.equals('5')}">selected</c:if>>All needs were comfortably met.</option>
                                </select>
                        </div>

                        <div class="form-group">
                            <label for="surveyGoals">Select the best description of how well the household's income met everyone's goals:</label>
                            <p class="text-info">Goals could include savings, education, upgraded necessities, hobbies, travel, gifts/donations, services, etc.</p>

                            <select class="form-control" id="surveyGoals" name="surveyGoals" required>
                                <option disabled selected value="0">(select one)</option>
                                <option value="1" <c:if test="${signUpAttempt.goals.equals('1')}">selected</c:if>>Unmet goals caused insecurity or high stress.</option>
                                <option value="2" <c:if test="${signUpAttempt.goals.equals('2')}">selected</c:if>>Unmet goals caused frustration.</option>
                                <option value="3" <c:if test="${signUpAttempt.goals.equals('3')}">selected</c:if>>Many goals were met.</option>
                                <option value="4" <c:if test="${signUpAttempt.goals.equals('4')}">selected</c:if>>Most or all goals were easily met.</option>
                                <option value="5" <c:if test="${signUpAttempt.goals.equals('5')}">selected</c:if>>Income allowed for new or expanding financial goals.</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="surveyIncomeSkew">Select the best description of how much other financial factors helped meet needs and goals:</label>
                            <p class="text-info">Examples could include reducing savings, increasing debt, or receiving gifts of time or services. </p>

                            <select class="form-control" id="surveyIncomeSkew" name="surveyIncomeSkew" required>
                                <option disabled selected value="0">(select one)</option>
                                <option value="1" <c:if test="${signUpAttempt.incomeSkew.equals('1')}">selected</c:if>>little or no impact</option>
                                <option value="2" <c:if test="${signUpAttempt.incomeSkew.equals('2')}">selected</c:if>>some impact</option>
                                <option value="3" <c:if test="${signUpAttempt.incomeSkew.equals('3')}">selected</c:if>>strong impact</option>
                             </select>
                        </div>
                    </div>

                    <div class="col-lg-2"></div>
                </div>
                <div class="centered">
                    <button type="submit" class="btn btn-info">Sign Up</button>
                </div>
            </form>
        </div>
    </div><!-- end card body -->
</div><!-- end card -->

</div>
</body>
</html>