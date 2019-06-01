/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import ServletUtils.ServletError;
import DatabaseAccessObjects.QueryObjects.User;
import DatabaseAccessObjects.ResultObjects.LibraryContentTypesQueryResult;
import DatabaseAccessObjects.ResultObjects.SubjectQueryResult;
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

/**
 *
 * @author windows
 */
@WebServlet(name = "GetLibraryContentUploadInfo", urlPatterns = {"/get_library_content_upload_info"})
public class GetLibraryContentUploadInfo extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession httpSession = request.getSession();
            User user = (User) httpSession.getAttribute("user");

            if (user.user_role.equals("E")) {
                List<SubjectQueryResult> subject_query_result_list = ServerRequests.getSubjectList();
                httpSession.setAttribute("subject_list", subject_query_result_list);
                List<LibraryContentTypesQueryResult> library_content_types_list = ServerRequests.getLibrayContentTypes();
                 httpSession.setAttribute("library_content_types_list", library_content_types_list);
                response.sendRedirect("upload_library_content.jsp");
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetLibraryContentUploadInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
