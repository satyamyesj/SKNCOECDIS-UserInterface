package Servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import User.ServerRequests;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;

/**
 *
 * @author windows
 */
@WebServlet(name = "CheckWeatherUsernameIsAvailable", urlPatterns = {"/check_weather_username_is_available"})
public class CheckWeatherUsernameIsAvailable extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String input_username = StringEscapeUtils.escapeHtml4(request.getParameter("input_username"));
            if (!input_username.equals("")) {
                boolean is_available = ServerRequests.checkWeatherUsernameIsAvailable(input_username);
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                if (is_available) {
                    response.getWriter().write("USERNAME_AVAILABLE");
                } else {
                    response.getWriter().write("USERNAME_UNAVAILABLE");
                }
            }
        } catch (NullPointerException ex) {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("DATABASE_SERVER_ERROR");
        } catch (UnknownHostException ex) {
            Logger.getLogger(CheckWeatherUsernameIsAvailable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CheckWeatherUsernameIsAvailable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
