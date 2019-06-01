/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import ServletUtils.ServletError;
import DatabaseAccessObjects.QueryObjects.LibraryContentSearchAttributes;
import DatabaseAccessObjects.ResultObjects.LibraryContentSearchQueryResult;
import User.ServerRequests;
import java.io.IOException;
import java.util.List;
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
@WebServlet(name = "GetLibraryContentList", urlPatterns = {"/get_library_content_list"})
public class GetLibraryContentList extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession httpSession = request.getSession();
            LibraryContentSearchAttributes library_content_search_attributes = new LibraryContentSearchAttributes();
            if (request.getParameter("content_title").trim().equals("")) {
                library_content_search_attributes.content_title = "%";
            } else {
                library_content_search_attributes.content_title = "%" + StringEscapeUtils.escapeHtml4(request.getParameter("content_title").trim()) + "%";
            }

            if (request.getParameter("subject").trim().equals("")) {
                library_content_search_attributes.subject = "%";
            } else {
                library_content_search_attributes.subject = "%" + StringEscapeUtils.escapeHtml4(request.getParameter("subject").trim()) + "%";
            }

            if (request.getParameter("username").trim().equals("")) {
                library_content_search_attributes.username = "%";
            } else {
                library_content_search_attributes.username = "%" +StringEscapeUtils.escapeHtml4( request.getParameter("username").trim()) + "%";
            }

            if (request.getParameter("library_content_type").trim().equals("NOT_ASSIGNED")) {
                library_content_search_attributes.content_type_id = "%";
            } else {
                String content_type = StringEscapeUtils.escapeHtml4(request.getParameter("library_content_type"));
                String content_type_split[] = content_type.split(":");
                library_content_search_attributes.content_type_id = content_type_split[0];
            }

            if (request.getParameter("exam_pattern").equals("NOT_ASSIGNED")) {
                library_content_search_attributes.exam_pattern = "%";
            } else {
                library_content_search_attributes.exam_pattern =StringEscapeUtils.escapeHtml4( request.getParameter("exam_pattern"));
            }

            List<LibraryContentSearchQueryResult> library_content_query_result_set = ServerRequests.getLibraryContentSearchResult(library_content_search_attributes);
            httpSession.setAttribute("library_content_query_result_set", library_content_query_result_set);
            httpSession.setAttribute("recently_loaded_table", "library_content_query_result_table");
            httpSession.setAttribute("recently_loaded_table_size", String.valueOf(library_content_query_result_set.size()));
            response.sendRedirect("home.jsp");
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
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(GetLibraryContentList.class.getName()).log(Level.SEVERE, null, ex);
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
