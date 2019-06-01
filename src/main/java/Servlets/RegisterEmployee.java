/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import ServletUtils.ServletMessage;
import ServletUtils.ServletError;
import DatabaseAccessObjects.QueryObjects.Employee;
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
@WebServlet(name = "RegisterEmployee", urlPatterns = {"/register_employee"})
public class RegisterEmployee extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getParameter("submit") != null) {
                Employee employee = new Employee();
                employee.username = StringEscapeUtils.escapeHtml4(request.getParameter("username"));
                employee.first_name =StringEscapeUtils.escapeHtml4( request.getParameter("first_name"));
                employee.last_name = StringEscapeUtils.escapeHtml4(request.getParameter("last_name"));
                employee.email = StringEscapeUtils.escapeHtml4(request.getParameter("email"));
                employee.mobile_no = StringEscapeUtils.escapeHtml4(request.getParameter("mobile_no"));
                employee.password = StringEscapeUtils.escapeHtml4(request.getParameter("password"));
                employee.employee_no = StringEscapeUtils.escapeHtml4(request.getParameter("employee_no"));
                boolean registeredSuccessfully = ServerRequests.registerEmployeeProfile(employee);
                if (registeredSuccessfully) {
                    ServletMessage servletMessage = new ServletMessage();
                    servletMessage.message = "Profile saved successfully.";
                    servletMessage.messageType = "alert-success";
                    HttpSession httpSession = request.getSession();
                    httpSession.setAttribute("servlet_message", servletMessage);
                    response.sendRedirect("index.jsp");
                } else {
                    ServletMessage servletMessage = new ServletMessage();
                    servletMessage.message = "Failed to save profile! email, mobile no, employee no might have been used";
                    servletMessage.messageType = "alert-danger";
                    HttpSession httpSession = request.getSession();
                    httpSession.setAttribute("servlet_message", servletMessage);
                    response.sendRedirect("employee_registration.jsp");
                }
            } else {
                response.sendRedirect("employee_registration.jsp");
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
            Logger.getLogger(RegisterEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("employee_registration.jsp");
        } catch (IOException ex) {
            Logger.getLogger(VerifyCredentials.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
