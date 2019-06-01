/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import ServletUtils.ServletError;
import DatabaseAccessObjects.QueryObjects.ReportSearchAttributes;
import DatabaseAccessObjects.ResultObjects.ReportSearchQueryResult;
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
@WebServlet(name = "GetReportTitleList", urlPatterns = {"/get_report_title_list"})
public class GetReportTitleList extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {

            HttpSession httpSession = request.getSession();
            ReportSearchAttributes report_search_attributes = new ReportSearchAttributes();

            if (request.getParameter("report_title").trim().equals("")) {
                report_search_attributes.report_title = "%";
            } else {
                report_search_attributes.report_title = "%" + StringEscapeUtils.escapeHtml4(request.getParameter("report_title").trim()) + "%";
            }

            if (request.getParameter("report_domain").trim().equals("")) {
                report_search_attributes.domain_name = "%";
            } else {
                report_search_attributes.domain_name = "%" + StringEscapeUtils.escapeHtml4(request.getParameter("report_domain").trim() )+ "%";
            }
            List<ReportSearchQueryResult> report_search_query_result_set = ServerRequests.getReportSearchResult(report_search_attributes);
            httpSession.setAttribute("report_search_query_result_set", report_search_query_result_set);
            httpSession.setAttribute("recently_loaded_table", "report_search_query_result_table");
             httpSession.setAttribute("recently_loaded_table_size", String.valueOf(report_search_query_result_set.size()));
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
        } catch (IOException ex) {
            Logger.getLogger(GetLibraryContentList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetReportTitleList.class.getName()).log(Level.SEVERE, null, ex);
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
