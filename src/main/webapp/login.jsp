<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

    <c:import url = "head.jsp" />

    <body>
    <div class="container-fluid">
        <c:import url="header.jsp" />

           <FORM ACTION="j_security_check" METHOD="POST">
            <TABLE>
                <TR><TD>User name: <INPUT TYPE="TEXT" NAME="j_username">
                <TR><TD>Password: <INPUT TYPE="PASSWORD" NAME="j_password">
                <TR><TH><INPUT TYPE="SUBMIT" VALUE="Log In">
            </TABLE>
        </FORM>
    </div>
    </body>
</html>