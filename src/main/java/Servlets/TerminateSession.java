package Servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
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
@WebServlet(name = "TerminateSession", urlPatterns = {"/terminate_session"})
public class TerminateSession extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getParameter("submit") != null) {
                //remove user profile
                HttpSession httpSession = request.getSession();
                httpSession.removeAttribute("user");

                //remove seacr results
                httpSession.removeAttribute("library_content_query_result_set");
                httpSession.removeAttribute("book_search_query_result_set");
                httpSession.removeAttribute("report_search_query_result_set");
                httpSession.removeAttribute("recently_loaded_table");
                httpSession.removeAttribute("recently_loaded_table_size");
                
                httpSession.setAttribute("RELOGIN", true);
                Cookie cookie = new Cookie("user_login_jwt", null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                response.sendRedirect("index.jsp");
            } else {
                response.sendRedirect("index.jsp");
            }
        } catch (IOException ex) {
            Logger.getLogger(TerminateSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param request
     * @param response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("index.jsp");
        } catch (IOException ex) {
            Logger.getLogger(VerifyCredentials.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
