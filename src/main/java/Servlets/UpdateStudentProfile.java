/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import ServletUtils.ServletMessage;
import ServletUtils.ServletError;
import DatabaseAccessObjects.QueryObjects.Student;
import User.ServerRequests;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.text.StringEscapeUtils;

/**
 *
 * @author windows
 */
@WebServlet(name = "UpdateStudentProfile", urlPatterns = {"/update_student_profile"})
public class UpdateStudentProfile extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getParameter("submit") != null) {
                HttpSession httpSession = request.getSession();
                Student student = (Student) httpSession.getAttribute("student");
                student.first_name =StringEscapeUtils.escapeHtml4( request.getParameter("first_name"));
                student.last_name = StringEscapeUtils.escapeHtml4(request.getParameter("last_name"));
                student.email = StringEscapeUtils.escapeHtml4(request.getParameter("email"));
                student.mobile_no = StringEscapeUtils.escapeHtml4(request.getParameter("mobile_no"));
                student.unique_id = StringEscapeUtils.escapeHtml4(request.getParameter("unique_id"));
                student.division = StringEscapeUtils.escapeHtml4(request.getParameter("division"));
                student.academic_year = StringEscapeUtils.escapeHtml4(request.getParameter("academic_year"));
                student.dob = StringEscapeUtils.escapeHtml4(request.getParameter("dob"));
                student.permanent_address = StringEscapeUtils.escapeHtml4(request.getParameter("address"));
                boolean updatedSuccessfully = ServerRequests.updateStudentProfile(student);
               if (updatedSuccessfully) {
                    ServletMessage servletMessage = new ServletMessage();
                    servletMessage.message = "Profile saved successfully.";
                    servletMessage.messageType = "alert-success";
                    httpSession.setAttribute("servlet_message", servletMessage);
                    response.sendRedirect("home.jsp");
                } else {
                    ServletMessage servletMessage = new ServletMessage();
                    servletMessage.message = "Failed to save profile! email, mobile no, unique id/PRN might have been used";
                    servletMessage.messageType = "alert-danger";
                    httpSession.setAttribute("servlet_message", servletMessage);
                    response.sendRedirect("update_student_profile.jsp");
                }
            } else {
                response.sendRedirect("home.jsp");
            }
        }catch (NullPointerException ex) {
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
        } catch (IOException ex) {
            Logger.getLogger(UpdateStudentProfile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("home.jsp");
        } catch (IOException ex) {
            Logger.getLogger(UpdateStudentProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
