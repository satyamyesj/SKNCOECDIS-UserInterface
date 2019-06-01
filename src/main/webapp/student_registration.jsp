<%-- 
    Document   : student_registration
    Created on : 6 Jan, 2019, 3:19:33 PM
    Author     : windows
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DatabaseAccessObjects.QueryObjects.User"%>
<%@page import="DatabaseAccessObjects.QueryObjects.Student"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- JSP Code -->
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");  //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setHeader("Expires", "0"); //Proxies
    
    Student input_student_profile=null;
    if (session.getAttribute("RELOGIN") == null) {
        if (session.getAttribute("user") != null) {
            response.sendRedirect("home.jsp");
        }
    }
%>

<!-- HTML5 Structure -->
<%@include file="headers/main_header.html"%>
<link href="style/student_registration.css" rel="stylesheet">
<section>
    <div class="rest-body-container container-fluid">
        <div id="student_registration_form_frame" class="container">
            <!-- rest header -->     
            <c:if test="${servlet_error!=null}">
                <div id="servlet_error_alert" class="alert ${servlet_error.errorType}"><i class="fas fa-times"></i> ${servlet_error.errorMessage}</div>
                ${servlet_error=null}
            </c:if>
            <c:if test="${servlet_message!=null}">
                <div id="servlet_message_alert" class="alert ${servlet_message.messageType}"><i class="far fa-comment-alt"></i> ${servlet_message.message}</div>
                <c:set var="input_student_profile" value="${input_Student_profile}"></c:set>
                ${input_Student_profile=null}
                ${servlet_message=null}
            </c:if> 

            <h1><img src="Icons/Block/SKNCOECDIS-Block 96px.png"  height="64" width="64"> SKNCOECDIS</h1>
            <small> Please fill all details carefully, will be stored as alumni record.</small>
            <div id="student_registration_form_alert" class="alert" style="display:none;"><i id="student_registration_form_alert_icon"></i><span id="student_registration_form_alert_msg"></span></div>
            <form id="student_registration_form" class="form" action="register_student" method="POST" >
                <div class="form-group">
                    <input id="student_registration_form_username" class="form-control" type="text" name="username" placeholder="Enter your username">
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-6 col-sm-6">
                            <input id="student_registration_form_first_name" class="form-control" type="text" name="first_name" placeholder="Enter your first name">
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <input id="student_registration_form_last_name" class="form-control" type="text" name="last_name" placeholder="Enter your last name">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-6 col-sm-6">
                            <input id="student_registration_form_email" class="form-control" type="email" name="email" placeholder="Enter your email">
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <input id="student_registration_form_mobile_no" class="form-control" type="text" name="mobile_no" placeholder="Enter your mobile no">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row"> 
                        <div class="col-md-6 col-sm-6">
                            <input id="student_registration_form_create_password" class="form-control" type="password" name="password" placeholder="Create password">
                        </div>
                        <div class="col-md-5 col-sm-5">
                            <input id="student_registration_form_confirm_password" class="form-control" type="password" placeholder="Confirm password">
                        </div>
                        <div id="show_password_field"class="col-md-1 col-sm-1">
                            <i id="show_password_icon" class="fas fa-eye" onclick="show_password()"></i>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-md-6 col-sm-6">
                            <select id="student_registration_form_academic_year" class="form-control" name="academic_year">
                                <option value="SE">SE</option>
                                <option value="TE">TE</option>
                                <option value="BE">BE</option>
                            </select>
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <input id="student_registration_form_division" class="form-control" type="text" name="division" placeholder="Enter your division">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-6 col-sm-6">
                            <input id="student_registration_form_unique_id" class="form-control" type="text" name="unique_id" placeholder="Enter your unique id/ PRN">
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <input id="student_registration_form_dob" class="form-control" type="text" name="dob" placeholder="Enter your date of birth" onfocus="this.type = 'date'" onblur="(this.type = 'text')">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <textarea id="student_registration_form_address" class="form-control" type="text" name="address" placeholder="Enter your address"></textarea>
                </div>
                <div class="form-group">
                    <button id="student_registration_form_submit" class="btn btn-outline-primary" type="submit" name="submit" value="Save Profile"><i class="fas fa-save"></i> Save Profile</button>
                </div>
            </form>
        </div>
    </div>
</section>
<%@include file="footers/main_footer.html"%>
<script src="js/student_registration.js"></script>

