<%-- 
    Document   : index
    Created on : 4 Jan, 2019, 12:48:21 PM
    Author     : windows
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- JSP Code -->
<%!
    String user_login_jwt = null;
%>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");  //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setHeader("Expires", "0"); //Proxies

    if (session.getAttribute("RELOGIN") == null) {
        Cookie cookies[] = request.getCookies();
        if (session.getAttribute("user") != null) {
            response.sendRedirect("home.jsp");
        } else if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_login_jwt")) {
                    user_login_jwt = cookie.getValue();
                    if (user_login_jwt != null) {
                        response.sendRedirect("verify_jwt");
                    }
                    break;
                }
            }
        }
    }
%>

<!-- HTML5 Structure -->
<%@include file="headers/main_header.html"%>
<link href="style/index.css" rel="stylesheet">
<header>
    <div class="container">

    </div>
</header>
<section>
    <div class="rest-body-container container-fluid">
        <div id="login_form_frame" class="container">
            <!-- rest header -->     
            <c:if test="${servlet_error!=null}">
                <div id="servlet_error_alert" class="alert ${servlet_error.errorType}"><i class="fas fa-times"></i> ${servlet_error.errorMessage}</div>
                ${servlet_error=null}
            </c:if>
            <c:if test="${servlet_message!=null}">
                <div id="servlet_message_alert" class="alert ${servlet_message.messageType}"><i class="far fa-comment-alt"></i> ${servlet_message.message}</div>
                ${servlet_message=null}
            </c:if> 

            <h1><img src="Icons/Block/SKNCOECDIS-Block 96px.png"  height="64" width="64"> SKNCOECDIS</h1>
            <small> Please login or register to continue</small>
            <div id="login_form_alert" class="alert" style="display:none"><i id="login_form_alert_icon"></i><span id="login_form_alert_msg"></span></div>
            <form id="login_form" class="form" action="verify_credentials" method="POST" >
                <div class="form-group">
                    <label>Username</label>
                    <input id="login_form_username" class="form-control" type="text" name="username" >
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input id="login_form_password" class="form-control" type="password" name="password">
                </div>
                <div class="form-group">
                    <button id="login_form_submit" class="btn btn-outline-primary" type="submit" name="submit" value="Login"><i class="fas fa-sign-in-alt"></i> Login</button>
                </div>
            </form>
            <a href="student_registration.jsp"><i class="fas fa-user-plus"></i> Register as student</a>
            <br>
            <a href="employee_registration.jsp"><i class="fas fa-user-plus"></i> Register as employee</a>
        </div>
    </div>
</section>
<%@include file="footers/main_footer.html"%>
<script src="js/index.js"></script>

