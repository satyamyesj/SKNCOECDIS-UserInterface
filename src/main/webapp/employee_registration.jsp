<%-- 
    Document   : employee_registration
    Created on : 6 Jan, 2019, 3:19:59 PM
    Author     : windows
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DatabaseAccessObjects.QueryObjects.User"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- JSP Code -->
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");  //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setHeader("Expires", "0"); //Proxies

    if (session.getAttribute("RELOGIN") == null) {
        if (session.getAttribute("user") != null) {
            response.sendRedirect("home.jsp");
        }
    }
%>

<!-- HTML5 Structure -->
<%@include file="headers/main_header.html"%>
<link href="style/employee_registration.css" rel="stylesheet">
<section>
    <div class="rest-body-container container-fluid"> 
        <div id="employee_registration_form_frame" class="container">
            <!-- rest header --> 
            <c:if test="${servlet_error!=null}">
                <div id="servlet_error_alert" class="alert ${servlet_error.errorType}">${servlet_error.errorMessage}</div>
                ${servlet_error=null}
            </c:if>
            <c:if test="${servlet_message!=null}">
                <div id="servlet_message_alert" class="alert ${servlet_message.messageType}">${servlet_message.message}</div>
                ${servlet_message=null}
            </c:if>  
            <h1><img src="Icons/Block/SKNCOECDIS-Block 96px.png"  height="64" width="64"> SKNCOECDIS</h1>
            <small> Please fill all details carefully, will be stored as alumni record.</small>
            <div id="employee_registration_form_alert" class="alert" style="display:none;"><i id="employee_registration_form_alert_icon"></i><span id="employee_registration_form_alert_msg"></span></div>
            <form id="employee_registration_form"class="form" action="register_employee" method="POST">

                <div class="form-group">
                    <div class="row">
                        <div class="col-md-6 col-sm-6">
                            <input id="employee_registration_form_username" class="form-control" type="text" name="username" placeholder="Enter your username">
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <input id="employee_registration_form_employee_no" class="form-control" type="text" name="employee_no" placeholder="Enter your employee no">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-6 col-sm-6">
                            <input id="employee_registration_form_first_name" class="form-control" type="text" name="first_name" placeholder="Enter your first name">
                        </div>
                        <div class="col-md-6 col-sm-6">     
                            <input id="employee_registration_form_last_name" class="form-control" type="text" name="last_name" placeholder="Enter your last name">
                        </div>
                    </div>
                </div>


                <div class="form-group">
                    <div class="row">
                        <div class="col-md-6 col-sm-6">
                            <input id="employee_registration_form_email" class="form-control" type="email" name="email" placeholder="Enter your email">
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <input id="employee_registration_form_mobile_no" class="form-control" type="text" name="mobile_no" placeholder="Enter your mobile no">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row"> 
                        <div class="col-md-6 col-sm-6">
                            <input id="employee_registration_form_create_password" class="form-control" type="password" name="password" placeholder="Create password">
                        </div>
                        <div class="col-md-5 col-sm-5" >
                            <input id="employee_registration_form_confirm_password" class="form-control" type="password" placeholder="Confirm password">
                        </div>
                        <div id="show_password_field"class="col-md-1 col-sm-1">
                            <i id="show_password_icon" class="fas fa-eye" onclick="show_password()"></i>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <input id="employee_registration_form_submit" class="btn btn-outline-primary" type="submit" name="submit" value="Save Profile">
                </div>
            </form>
        </div>
    </div>
</section>
<%@include file="footers/main_footer.html"%>
<script src="js/employee_registration.js"></script>
