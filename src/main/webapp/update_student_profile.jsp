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

    if (session.getAttribute("RELOGIN") == null) {
        if (session.getAttribute("user") == null) {
            response.sendRedirect("home.jsp");
        }
    }
%>

<!-- HTML5 Structure -->
<%@include file="headers/main_header.html"%>
<link href="style/update_student_profile.css" rel="stylesheet">
<section>
    <div class="rest-body-container container-fluid">
        <div id="update_student_profile_form_frame">
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
            <div id="student_registration_form_alert" class="alert" style="display:none"><i id="student_registration_form_alert_icon"></i><span id="student_registration_form_alert_msg"></span></div>
            <form id="update_student_profile_form" class="form" action="update_student_profile" method="POST">
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>first name</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <input id="student_registration_form_first_name" class="form-control" type="text" name="first_name" value="${student.first_name}">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>last name</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <input id="student_registration_form_last_name" class="form-control" type="text" name="last_name" value="${student.last_name}">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>email</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <input id="student_registration_form_email" class="form-control" type="email" name="email" value="${student.email}">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>mobile no</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <input id="student_registration_form_mobile_no" class="form-control" type="text" name="mobile_no" value="${student.mobile_no}">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>unique id/ PRN</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <input id="student_registration_form_unique_id" class="form-control" type="text" name="unique_id" value="${student.unique_id}">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>academic year</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <select id="student_registration_form_academic_year" class="form-control" name="academic_year">
                                <option selected="selected">
                                    ${student.academic_year}
                                </option>
                                <option value="SE">SE</option>
                                <option value="TE">TE</option>
                                <option value="BE">BE</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>division</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <input id="student_registration_form_division" class="form-control" type="text" name="division" value="${student.division}">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>dob</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <input id="student_registration_form_dob" class="form-control" type="date" name="dob" value="${student.dob}">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>address</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <textarea id="student_registration_form_address" class="form-control" type="text" name="address" >${student.permanent_address}</textarea>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-12 col-sm-12">
                            <input id="student_registration_form_submit" class="btn btn-outline-primary" type="submit" name="submit" value="Save Profile">
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>
<%@include file="footers/main_footer.html"%>
<script src="js/update_student_profile.js"></script>