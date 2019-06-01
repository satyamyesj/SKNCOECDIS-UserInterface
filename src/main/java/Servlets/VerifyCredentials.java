package Servlets;

import ServletUtils.JJWT;
import ServletUtils.ServletError;
import DatabaseAccessObjects.ResultObjects.LibraryContentTypesQueryResult;
import DatabaseAccessObjects.ResultObjects.Statistics;
import ServletUtils.ServletMessage;
import User.ServerRequests;
import User.UserInterface;
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
import org.apache.commons.text.StringEscapeUtils;

@WebServlet(name = "VerifyCredentials", urlPatterns = {"/verify_credentials"})
public class VerifyCredentials extends HttpServlet {

    private final long userLoginJWTExpirationMS = 1000 * 60 * 15;      //15 Minutes
    private final int userLoginJWTCookieMaxAge = 900;                  //15 Minutes

    /**
     *
     * @param request
     * @param response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getParameter("submit") != null) {
                String username = StringEscapeUtils.escapeHtml4(request.getParameter("username").trim());
                String password = StringEscapeUtils.escapeHtml4(request.getParameter("password").trim());
                boolean signUpSuccessfully;
                signUpSuccessfully = ServerRequests.login(username, password);
                if (signUpSuccessfully && (UserInterface.user.user_role.equals("S") || UserInterface.user.user_role.equals("E"))) {
                    HttpSession httpSession = request.getSession();
                    httpSession.removeAttribute("RELOGIN");
                    httpSession.setAttribute("user", UserInterface.user);
                    JJWT jjwt = new JJWT();
                    String jwt = jjwt.createJWT("user_login_jwt", UserInterface.user, userLoginJWTExpirationMS);
                    Cookie cookie = new Cookie("user_login_jwt", jwt);
                    cookie.setMaxAge(userLoginJWTCookieMaxAge);
                    response.addCookie(cookie);
                    
                    //Get statistics
                    Statistics statistics=ServerRequests.getStatistics();
                    httpSession.setAttribute("statistics", statistics);
                    
                    //Get library_content_types_list & exam patterns
                    List<LibraryContentTypesQueryResult> library_content_types_list=ServerRequests.getLibrayContentTypes();
                    httpSession.setAttribute("library_content_types_list", library_content_types_list);
                    List<String> exam_patterns_list=ServerRequests.getExamPatterns();
                    httpSession.setAttribute("exam_patterns_list", exam_patterns_list);
                    
                    response.sendRedirect("home.jsp");
                } else {
                    ServletMessage servletMessage = new ServletMessage();
                    servletMessage.message = "Incorrect username or password";
                    servletMessage.messageType = "alert-danger";
                    HttpSession httpSession = request.getSession();
                    httpSession.setAttribute("servlet_message", servletMessage);
                    response.sendRedirect("index.jsp");
                }
            } else {
                response.sendRedirect("index.jsp");
            }
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(VerifyCredentials.class.getName()).log(Level.SEVERE, null, ex);

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
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("index.jsp");
        } catch (IOException ex) {
            Logger.getLogger(VerifyCredentials.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
