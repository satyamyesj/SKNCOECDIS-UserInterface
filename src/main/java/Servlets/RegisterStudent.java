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
@WebServlet(name = "RegisterStudent", urlPatterns = {"/register_student"})
public class RegisterStudent extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getParameter("submit") != null) {
                Student student = new Student();
                student.username =StringEscapeUtils.escapeHtml4( request.getParameter("username"));
                student.first_name = StringEscapeUtils.escapeHtml4(request.getParameter("first_name"));
                student.last_name = StringEscapeUtils.escapeHtml4(request.getParameter("last_name"));
                student.email = StringEscapeUtils.escapeHtml4(request.getParameter("email"));
                student.mobile_no = StringEscapeUtils.escapeHtml4(request.getParameter("mobile_no"));
                student.password = StringEscapeUtils.escapeHtml4(request.getParameter("password"));
                student.unique_id =StringEscapeUtils.escapeHtml4( request.getParameter("unique_id"));
                student.academic_year = StringEscapeUtils.escapeHtml4(request.getParameter("academic_year"));
                student.division = StringEscapeUtils.escapeHtml4(request.getParameter("division"));
                student.dob = StringEscapeUtils.escapeHtml4(request.getParameter("dob"));
                student.permanent_address = request.getParameter("address");
                boolean registeredSuccessfully = ServerRequests.registerStudentProfile(student);
                if (registeredSuccessfully) {
                    ServletMessage servletMessage = new ServletMessage();
                    servletMessage.message = "Profile saved successfully.";
                    servletMessage.messageType = "alert-success";
                    HttpSession httpSession = request.getSession();
                    httpSession.setAttribute("servlet_message", servletMessage);
                    response.sendRedirect("index.jsp");
                } else {
                    ServletMessage servletMessage = new ServletMessage();
                    servletMessage.message = "Failed to save profile! email, mobile no, unique id/PRN might have been used";
                    servletMessage.messageType = "alert-danger";
                    HttpSession httpSession = request.getSession();
                    httpSession.setAttribute("servlet_message", servletMessage);
                    response.sendRedirect("student_registration.jsp");
                }
            } else {
                response.sendRedirect("student_registration.jsp");
            }
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
        }catch (IOException ex) {
            Logger.getLogger(RegisterStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("student_registration.jsp");
        } catch (IOException ex) {
            Logger.getLogger(VerifyCredentials.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
