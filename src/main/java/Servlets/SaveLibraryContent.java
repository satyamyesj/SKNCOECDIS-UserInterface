/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import ServletUtils.ServletError;
import DatabaseAccessObjects.ResultObjects.LibrayContentFileQueryResult;
import User.ServerRequests;
import java.io.File;
import java.io.FileInputStream;
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
@WebServlet(name = "SaveLibraryContent", urlPatterns = {"/save_library_content"})
public class SaveLibraryContent extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            int content_id = Integer.parseInt(StringEscapeUtils.escapeHtml4(request.getParameter("content_id")));
            LibrayContentFileQueryResult libray_content_file = ServerRequests.getLibraryContentFile(content_id);

            File content_file = new File(libray_content_file.content_file_absolute_path);
            String file_name = content_file.getName();
            String file_extension = this.getServletContext().getMimeType(content_file.getName());  /* abc.txt => text/plain, abc.zip => application/zip, abc.pdf => application/pdf */
            long file_size = content_file.length();

            response.setHeader("Content-Type", file_extension);
            response.setHeader("Content-Length", String.valueOf(file_size));
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file_name + "\"");   /* inline - to be displayed in web browser, attachment - should be downloaded*/

            //Sending byte read as integer from file to response
            FileInputStream fileInputStream = new FileInputStream(content_file);
            int i;
            while ((i = fileInputStream.read()) != -1) {
                response.getWriter().write(i);
            }
            fileInputStream.close();
            content_file.delete();
            
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
            Logger.getLogger(SaveLibraryContent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
