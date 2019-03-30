<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <c:import url = "head.jsp" />
        <body>
        <div class="container">
        <c:import url = "header.jsp" />


            <h2>Search Experiences</h2>
            <p>There are two ways to search. You may select the income and household size that interests you.
                You'll see what our same-sized households with the closest incomes have experienced. Otherwise,
                you may search the financial stories for a topic that interests you, and you'll see relevant
                stories and be able to link to the profile of their authors.
            </p>

            <h3>Search by Income and Household Size</h3>
            <form method="post" action="search-stats">
                <div class="form-group">
                    <label for="income">Before-tax annual income:</label>
                    <input type="number" class="form-control" id="income" name="income" placeholder="######">
                </div>
                <div class="form-group">
                    <label for="householdSize">Select list:</label>
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