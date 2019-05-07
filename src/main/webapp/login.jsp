<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

    <c:import url = "head.jsp" />

    <body>
    <div class="container padded-container">
        <c:import url="header.jsp" />

        <div class="card">
            <div class="card-body">
                <FORM ACTION="j_security_check" METHOD="POST">
                        <div class="form-group">
                           <label for="j_username">User name: </label>
                           <INPUT TYPE="TEXT" NAME="j_username" id="j_username" class="form-control">
                       </div>
                       <div class="form-group">
                           <label for="j_password">Password: </label>
                           <INPUT TYPE="PASSWORD" NAME="j_password" id="j_password" class="form-control">
                       </div>
                           <INPUT TYPE="SUBMIT" VALUE="Log In" class="btn btn-info" class="form-control">
                </FORM>
            </div>
        </div>
    </div>
    </body>
</html>