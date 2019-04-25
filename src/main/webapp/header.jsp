<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
    <!-- Grey with black text -->
    <nav class="navbar navbar-expand-sm bg-light navbar-light">
        <a class="navbar-brand" href="index.jsp">Income Experiences</a>
        <ul class="navbar-nav">

            <!-- todo add logic to only show admin page to logged in admin-->

            <li class="nav-item">
                <a class="nav-link" href="profile">Profile</a>
            </li>

             <li class="nav-item">
                <a class="nav-link" href="search.jsp">Search</a>
            </li>

             <li class="nav-item">
                <a class="nav-link" href="admin">Admin</a>
            </li>

            <c:choose>
                <c:when test="${empty pageContext.request.userPrincipal}">
                    <li class="nav-item">
                        <a class="nav-link" href="login">Log In</a>
                    </li>
                    <li>
                        <a class="nav-link" href="signup.jsp">Sign Up</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item">
                        <a class="nav-link" href="log-out">Log Out</a>
                    </li>
                </c:otherwise>
            </c:choose>


          </ul>
    </nav>
    <div class="jumbotron">
        <h1>breaking the money taboo</h1>
    </div>
</header>
