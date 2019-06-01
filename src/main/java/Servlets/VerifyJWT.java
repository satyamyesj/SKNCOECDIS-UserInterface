package Servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ServletUtils.JJWT;
import ServletUtils.ServletError;
import DatabaseAccessObjects.QueryObjects.User;
import DatabaseAccessObjects.ResultObjects.LibraryContentTypesQueryResult;
import DatabaseAccessObjects.ResultObjects.Statistics;
import User.ServerRequests;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author windows
 */
@WebServlet(name = "VerifyJWT", urlPatterns = {"/verify_jwt"})
public class VerifyJWT extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            Cookie cookies[] = request.getCookies();
            String user_login_jwt = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_login_jwt")) {
                    user_login_jwt = cookie.getValue();
                }
            }
            JJWT jjwt = new JJWT();
            User user = jjwt.parseJWT(user_login_jwt);
            if (user != null) {
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("user", user);

                //Get statistics
                Statistics statistics = ServerRequests.getStatistics();
                httpSession.setAttribute("statistics", statistics);

                //Get library_content_types_list & exam patterns
                List<LibraryContentTypesQueryResult> library_content_types_list = ServerRequests.getLibrayContentTypes();
                httpSession.setAttribute("library_content_types_list", library_content_types_list);
                List<String> exam_patterns_list = ServerRequests.getExamPatterns();
                httpSession.setAttribute("exam_patterns_list", exam_patterns_list);

                response.sendRedirect("home.jsp");
            } else {
                HttpSession httpSession = request.getSession();
                httpSession.removeAttribute("user");
                httpSession.setAttribute("RELOGIN", true);
                Cookie cookie = new Cookie("user_login_jwt", null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                response.sendRedirect("index.jsp");
            }
        } catch (IOException ex) {

        } catch (NullPointerException ex) {
            ServletError servletError = new ServletError();
            servletError.errorMessage = "Database server not responding!";
            servletError.errorType = "alert-danger";
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("servlet_error", servletError);
            try {
                response.sendRedirect("index.jsp");
            } catch (IOException ex1) {
                Logger.getLogger(VerifyCredentials.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VerifyJWT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
