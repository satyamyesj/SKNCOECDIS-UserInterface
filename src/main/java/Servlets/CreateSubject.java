/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import ServletUtils.ServletMessage;
import ServletUtils.ServletError;
import DatabaseAccessObjects.QueryObjects.AddSubjectAttributes;
import DatabaseAccessObjects.ResultObjects.SubjectQueryResult;
import User.ServerRequests;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
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
@WebServlet(name = "CreateSubject", urlPatterns = {"/create_subject"})
public class CreateSubject extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            if (request.getParameter("submit") != null) {
                HttpSession httpSession = request.getSession();
                AddSubjectAttributes subject_attributes = new AddSubjectAttributes();

                //Retrieve subject_name
                subject_attributes.subject_name = StringEscapeUtils.escapeHtml4(request.getParameter("subject_name"));

                //Retrieve semester
                subject_attributes.semester = StringEscapeUtils.escapeHtml4(request.getParameter("semester"));

                //Retrieve academic_year
                subject_attributes.academic_year = StringEscapeUtils.escapeHtml4(request.getParameter("academic_year"));

                //Retrieve exam_pattern
                subject_attributes.exam_pattern =StringEscapeUtils.escapeHtml4( request.getParameter("exam_pattern"));

                //Retrieve domain_id
                String domain = StringEscapeUtils.escapeHtml4(request.getParameter("domain"));
                httpSession.removeAttribute("domain_list");
                String domain_split[] = domain.split(":");
                subject_attributes.domain_id = Integer.parseInt(domain_split[0]);

                //Retrieve subject_abbreviation
                subject_attributes.subject_abbreviation = StringEscapeUtils.escapeHtml4(request.getParameter("subject_abbreviation"));

                //validate subject for duplication
                boolean weather_subject_exist = false;
                List<SubjectQueryResult> subject_list = ServerRequests.getSubjectList();
                for (SubjectQueryResult subject : subject_list) {
                    if (subject.subject_name.equalsIgnoreCase(subject_attributes.subject_name) && subject.exam_pattern.equalsIgnoreCase(subject_attributes.exam_pattern)) {
                        weather_subject_exist = true;
                        break;
                    }
                }

                if (!weather_subject_exist) {
                    boolean added_successfully = ServerRequests.addSubject(subject_attributes);
                    if (added_successfully) {
                        ServletMessage servletMessage = new ServletMessage();
                        servletMessage.message = "Subject saved successfully.";
                        servletMessage.messageType = "alert-success";
                        httpSession.setAttribute("servlet_message", servletMessage);
                        response.sendRedirect("home.jsp");
                    } else {
                        ServletMessage servletMessage = new ServletMessage();
                        servletMessage.message = "Failed to save subject!";
                        servletMessage.messageType = "alert-danger";
                        httpSession.setAttribute("servlet_message", servletMessage);
                        response.sendRedirect("home.jsp");
                    }
                } else if (weather_subject_exist) {
                    ServletMessage servletMessage = new ServletMessage();
                    servletMessage.message = "Failed to save subject! subject already exists.";
                    servletMessage.messageType = "alert-danger";
                    httpSession.setAttribute("servlet_message", servletMessage);
                    response.sendRedirect("home.jsp");
                }
            } else {
                response.sendRedirect("home.jsp");
            }
        } catch (IOException ex) {
            Logger.getLogger(VerifyCredentials.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException | NumberFormatException ex) {
            Logger.getLogger(UploadLibraryContent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("home.jsp");
        } catch (IOException ex) {
            Logger.getLogger(VerifyCredentials.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
