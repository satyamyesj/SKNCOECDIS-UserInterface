/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import ServletUtils.ServletMessage;
import ServletUtils.ServletError;
import DatabaseAccessObjects.QueryObjects.AddLibraryContentAttributes;
import DatabaseAccessObjects.QueryObjects.User;
import DatabaseAccessObjects.ResultObjects.Statistics;
import User.ServerRequests;
import java.io.File;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author windows
 */
@WebServlet(name = "UploadLibraryContent", urlPatterns = {"/upload_library_content"})
public class UploadLibraryContent extends HttpServlet {

    final long maxFileSize = 16777216;  //16MB

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            //Retrieve parameters
            ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
            List<FileItem> requestParameters = servletFileUpload.parseRequest(request);
            /*
            #sequence
            0:content_type
            1:subject_descripter
            2:content_title
            3:content_file
            4:submit
             */
            if (requestParameters.get(4).getString() != null) {
                HttpSession httpSession = request.getSession();
                AddLibraryContentAttributes library_content_attributes = new AddLibraryContentAttributes();

                //Retrieve subject_id
                httpSession.removeAttribute("subject_list");
                String subject_description = requestParameters.get(1).getString();
                String subject_description_split[] = subject_description.split(":");
                library_content_attributes.subject_id = Integer.parseInt(subject_description_split[0]);

                //Retrieve username
                library_content_attributes.username = ((User) httpSession.getAttribute("user")).username;

                //Retrieve content_type_id
                httpSession.removeAttribute("library_content_types_list");
                String library_content_type = requestParameters.get(0).getString();
                String library_content_type_split[] = library_content_type.split(":");
                library_content_attributes.type_id = Integer.parseInt(library_content_type_split[0]);

                //Retrieve content_title
                library_content_attributes.content_title = requestParameters.get(2).getString();

                //Retrieve file
                File content_file = new File(requestParameters.get(3).getName());
                requestParameters.get(3).write(content_file);
                if (content_file.length() < maxFileSize) {
                    library_content_attributes.content_file_absolute_path = content_file.getAbsolutePath();
                    boolean added_successfully = ServerRequests.addLibrayContent(library_content_attributes);
                   content_file.delete();
                    if (added_successfully) {
                        ServletMessage servletMessage = new ServletMessage();
                        servletMessage.message = "File saved successfully.";
                        servletMessage.messageType = "alert-success";
                        httpSession.setAttribute("servlet_message", servletMessage);

                        //Get statistics
                        Statistics statistics = ServerRequests.getStatistics();
                        httpSession.setAttribute("statistics", statistics);
                        
                        response.sendRedirect("home.jsp");
                    } else {
                        ServletMessage servletMessage = new ServletMessage();
                        servletMessage.message = "Failed to save file!";
                        servletMessage.messageType = "alert-danger";
                        httpSession.setAttribute("servlet_message", servletMessage);
                        response.sendRedirect("home.jsp");
                    }
                } else {
                    ServletMessage servletMessage = new ServletMessage();
                    servletMessage.message = "Failed to save file! File size is too big to upload.";
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
        } catch (FileUploadException ex) {
            Logger.getLogger(UploadLibraryContent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
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
