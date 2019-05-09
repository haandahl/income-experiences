<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <c:import url = "head.jsp" />
        <body>
        <div class="container padded-container">
        <c:import url = "header.jsp" />

            <h2>Search Income Experiences</h2>

            <div class="row">

                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header">

                            <h3>By Household Size and Income</h3>

                        </div>
                        <c:if test="${!empty validationMessage}">
                            <p class="bg-warning feedback">${validationMessage}</p>
                        </c:if>
                        <div class="card-body">
                <form method="post" action="search-stats">
                    <!-- Resource for blank defaults in select groups:
                    https://stackoverflow.com/questions/8605516/default-select-option-as-blank -->
                    <p>The career options are the top five careers, according to US News, and the top five careers with
                        projected new jobs, according to the Bureau of Labor Statistics. (These were selected according to 2019 data).
                        You may select a career (and we'll find its median income) or you may skip the career and enter the income directly. </p>
                    <div class="form-group">
                        <label for="careerInput">Career:</label>

                        <!-- TODO keep selection from erroneous submittal -->

                        <select class="form-control" id="careerInput" name="careerInput">
                            <option selected value="">(select a job or skip this and enter income below) </option>
                            <optgroup label="Both Top Jobs and Most New Jobs">
                                <option <c:if test="${careerInput.equals('bestAndMost1')}">selected</c:if> value="bestAndMost1">${incomeExperiencesProperties["bestAndMost1.display.name"]}</option>
                            </optgroup>
                            <optgroup label="Top Jobs">
                                <option <c:if test="${careerInput.equals('best1')}">selected</c:if> value="best1">${incomeExperiencesProperties["best1.display.name"]}</option>
                                <option <c:if test="${careerInput.equals('best2')}">selected</c:if> value="best2">${incomeExperiencesProperties["best2.display.name"]}</option>
                                <option <c:if test="${careerInput.equals('best3')}">selected</c:if> value="best3">${incomeExperiencesProperties["best3.display.name"]}</option>
                                <option <c:if test="${careerInput.equals('best4')}">selected</c:if> value="best4">${incomeExperiencesProperties["best4.display.name"]}</option>
                            </optgroup>
                            <optgroup label="Most New Jobs">
                                <option <c:if test="${careerInput.equals('most1')}">selected</c:if> value="most1">${incomeExperiencesProperties["most1.display.name"]}</option>
                                <option <c:if test="${careerInput.equals('most2')}">selected</c:if> value="most2">${incomeExperiencesProperties["most2.display.name"]}</option>
                                <option <c:if test="${careerInput.equals('most3')}">selected</c:if> value="most3">${incomeExperiencesProperties["most3.display.name"]}</option>
                                <option <c:if test="${careerInput.equals('most4')}">selected</c:if> value="most4">${incomeExperiencesProperties["most4.display.name"]}</option>
                            </optgroup>
                         </select>
                    </div>
                    <div class="form-group">
                        <label for="income">Before-tax annual income:</label>
                        <input type="number" class="form-control" id="income" name="income" min="0"
                        <c:choose>
                            <c:when test="${empty incomeInput}">
                               placeholder="(leave empty if searching by career)"
                            </c:when>
                            <c:otherwise>
                               value="${incomeInput}"
                        </c:otherwise>
                        </c:choose> >
                    </div>
                    <div class="form-group">
                        <label for="householdSize">Household size:</label>

                        <select class="form-control" id="householdSize" name="householdSize">
                            <option disabled selected value>(select a household size)</option>
                            <option <c:if test="${householdSizeInput.equals('1')}">selected</c:if> >1</option>
                            <option <c:if test="${householdSizeInput.equals('2')}">selected</c:if> >2</option>
                            <option <c:if test="${householdSizeInput.equals('3')}">selected</c:if> >3</option>
                            <option <c:if test="${householdSizeInput.equals('4')}">selected</c:if> >4</option>
                            <option <c:if test="${householdSizeInput.equals('5')}">selected</c:if> >5</option>
                            <option <c:if test="${householdSizeInput.equals('6')}">selected</c:if> >6</option>
                            <option <c:if test="${householdSizeInput.equals('7')}">selected</c:if> >7</option>
                            <option <c:if test="${householdSizeInput.equals('8')}">selected</c:if> >8</option>
                            <option <c:if test="${householdSizeInput.equals('9')}">selected</c:if> >9</option>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-info">Search for Stats and Stories</button>
                </form>
                </div>
                    </div><!-- end card body -->
                </div><!-- end card -->

                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header">

                <h3>By Topic</h3>
                        </div>
                        <div class="card-body">
                <form method="post" action="search-topics">
                    <div class="form-group">
                        <label for="topic">Topic(s):</label>
                        <input type="text" class="form-control" id="topic" name="topic" placeholder="example: frugal dumpster diving" required>
                    </div>
                    <button type="submit" class="btn btn-info">Search by Topic</button>
                </form>
                        </div><!-- end card body-->
                    </div><!-- end card -->
            </div>
            </div><!-- end row-->
        </div><!-- end container -->
    </body>
</html>