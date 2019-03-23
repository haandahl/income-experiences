<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<c:import url = "head.jsp" />
<p>Sorry, you are not authorized to access this page. </p>
<p><a href="index.jsp">home</a> </p>

<!-- TODO (nice to do) ... figure out why I get a 404 error when trying to log in with a different user
HTTP Status 404 - /incomeexperiences/j_security_check
NB:  j_security_check is the action on the login form.  It is available when accessed via login error but not here
"Once a user has been authenticated, the user (and his or her associated roles) are cached
within Tomcat for the duration of the user's login. For FORM-based authentication, that means
until the session times out or is invalidated"
https://tomcat.apache.org/tomcat-5.5-doc/realm-howto.html
-->

<!-- <p><a href="login.jsp">try different login</a> </p> -->

</html>