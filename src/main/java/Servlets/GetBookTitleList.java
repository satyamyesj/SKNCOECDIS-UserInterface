/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import ServletUtils.ServletError;
import DatabaseAccessObjects.QueryObjects.BookSearchAttributes;
import DatabaseAccessObjects.ResultObjects.BookSearchQueryResult;
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
@WebServlet(name = "GetBookTitleList", urlPatterns = {"/get_book_title_list"})
public class GetBookTitleList extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession httpSession = request.getSession();
            BookSearchAttributes book_search_attributes = new BookSearchAttributes();

            if (request.getParameter("book_author").trim().equals("")) {
                book_search_attributes.book_author = "%";
            } else {
                book_search_attributes.book_author = "%" +StringEscapeUtils.escapeHtml4( request.getParameter("book_author").trim()) + "%";
            }

            if (request.getParameter("book_title").trim().equals("")) {
                book_search_attributes.book_title = "%";
            } else {
                book_search_attributes.book_title = "%" + StringEscapeUtils.escapeHtml4(request.getParameter("book_title").trim()) + "%";
            }

            if (request.getParameter("book_domain").trim().equals("")) {
                book_search_attributes.domain_name = "%";
            } else {
                book_search_attributes.domain_name = "%" + StringEscapeUtils.escapeHtml4(request.getParameter("book_domain").trim()) + "%";
            }
            List<BookSearchQueryResult> book_search_query_result_set = ServerRequests.getBookSearchResult(book_search_attributes);
            httpSession.setAttribute("book_search_query_result_set", book_search_query_result_set);
            httpSession.setAttribute("recently_loaded_table", "book_search_query_result_table");
              httpSession.setAttribute("recently_loaded_table_size", String.valueOf(book_search_query_result_set.size()));
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
            Logger.getLogger(GetBookTitleList.class.getName()).log(Level.SEVERE, null, ex);
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
