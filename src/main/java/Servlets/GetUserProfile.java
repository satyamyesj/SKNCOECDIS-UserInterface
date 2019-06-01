/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import ServletUtils.ServletError;
import DatabaseAccessObjects.QueryObjects.User;
import DatabaseAccessObjects.QueryObjects.UserProfileRequestAttribute;
import DatabaseAccessObjects.ResultObjects.UserProfileQueryResult;
import User.ServerRequests;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author windows
 */
@WebServlet(name = "GetUserProfile", urlPatterns = {"/get_user_profile"})
public class GetUserProfile extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getParameter("submit") != null) {
                HttpSession httpSession = request.getSession();
                User user = (User) httpSession.getAttribute("user");
                UserProfileRequestAttribute user_profile_query = new UserProfileRequestAttribute();
                user_profile_query.username = user.username;
                UserProfileQueryResult user_profile_query_result = ServerRequests.getUserProfile(user_profile_query);
                //response.getWriter().print(user_profile_query_result.user_role);
                if (user_profile_query_result.user_role.equals("S")) {
                    httpSession.setAttribute("student", user_profile_query_result.student);
                    response.sendRedirect("update_student_profile.jsp");
                } else {
                    httpSession.setAttribute("employee", user_profile_query_result.employee);
                    response.sendRedirect("update_employee_profile.jsp");
                }
            } else {
                response.sendRedirect("home.jsp");
            }
        } catch (NullPointerException ex) {
            ServletError servletError = new ServletError();
            servletError.errorMessage = "Database server not responding!";
            servletError.errorType = "alert-danger";
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("servlet_error", servletError);
            try {
                response.sendRedirect("home.jsp");
            } catch (IOException ex1) {
                Logger.getLogger(VerifyCredentials.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(GetUserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("home.jsp");
        } catch (IOException ex) {
            Logger.getLogger(VerifyCredentials.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
