package com.heidiaandahl.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
    name = "logOut",
    urlPatterns = { "/log-out"}
)
public class LogOut extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.invalidate();


        // TODO -- PW feedback week 7:Log out should trigger a session.invalidate() type method to removed the logged in user's info from session. Hope that helps!



        // HOWEVER (http://pdf.moreservlets.com/More-Servlets-and-JSP-Chapter-07.pdf)
        // What about a page for logging out? The ses-sion should time out eventually,
        // but what if users want to log out immediately with-out  closing  the  browser?
        // Well,  the  servlet  specification  says  that  invalidating  the
        // HttpSession should log out users and cause them to be reauthenticated the next
        // time  they  try  to  access  a  protected  resource.  So,  in  principle  you should be able to
        // create a logout page by making servlet or JSP page that looks up the session and calls
        // invalidate on it. In practice, however, not all servers support this process.
        // Fortu-nately, changing users is simple: you just visit the login page a second time.

        response.sendRedirect("index.jsp");
    }
}



