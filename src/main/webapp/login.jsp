<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

    <c:import url = "head.jsp" />

    <body>
    <div class="container">
        <c:import url="header.jsp" />

           <FORM ACTION="j_security_check" METHOD="POST">
               <%--
            <TABLE>
                <TR><TD>User name: <INPUT TYPE="TEXT" NAME="j_username">
                <TR><TD>Password: <INPUT TYPE="PASSWORD" NAME="j_password">
                <TR><TH><INPUT TYPE="SUBMIT" VALUE="Log In">
            </TABLE>
            --%>
               <div class="form-group">
                   <label for="j_username">User name: </label>
                   <INPUT TYPE="TEXT" NAME="j_username" id="j_username">
               </div>
               <div class="form-group">
                   <label for="j_password">Password: </label>
                   <INPUT TYPE="PASSWORD" NAME="j_password" id="j_password">
               </div>
                   <INPUT TYPE="SUBMIT" VALUE="Log In" class="btn btn-primary">
        </FORM>
    </div>
    </body>
</html>