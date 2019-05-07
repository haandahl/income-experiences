<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<c:import url = "head.jsp" />
<body>
<div class="container padded-container">
    <c:import url = "header.jsp" />

    <div class="card">
        <div class="card-body" id="welcome-card">
            <h2 class="card-text">Welcome, ${username}!</h2>
            <p class="card-text">Thanks so much for signing up and sharing useful information about how income has shaped your year!</p>
            <p class="card-text">
                You will need to <a href="login">log in</a> to start using the site. Please help others understand more
                about how income has impacted your experiences by visiting your <a href="profile">profile</a> and adding a financial story.
                Then search for stats and stories of interest to you! Enjoy!
            </p>
        </div>
    </div>

</div>
</body>
</html>