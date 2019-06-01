<%-- 
    Document   : home
    Created on : 4 Jan, 2019, 1:38:58 PM
    Author     : windows
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="DatabaseAccessObjects.QueryObjects.User"%>
<%@page import="DatabaseAccessObjects.ResultObjects.DomainQueryResult"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f"  uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- JSP Code -->
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");  //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setHeader("Expires", "0"); //Proxies

    User user = null;
    if (session.getAttribute("RELOGIN") == null) {
        if (session.getAttribute("user") == null || session.getAttribute("domain_list") == null || !((User) session.getAttribute("user")).user_role.equals("E")) {
            response.sendRedirect("home.jsp");
        } else {
            user = (User) session.getAttribute("user");
        }
    } else {
        response.sendRedirect("index.jsp");
    }
%>

<!-- HTML5 Structure -->
<%@include file="headers/main_header.html"%>
<link href="style/create_subject.css" rel="stylesheet">
<section> 
    <div class="rest-body-container container-fluid">
        <div id="create_subject_form_frame">
            <c:if test="${servlet_error!=null}">
                <div id="servlet_error_alert" class="alert ${servlet_error.errorType}">${servlet_error.errorMessage}</div>
                ${servlet_error=null}
            </c:if>
            <c:if test="${servlet_message!=null}">
                <div id="servlet_message_alert" class="alert ${servlet_message.messageType}">${servlet_message.message}</div>
                ${servlet_message=null}
            </c:if>  

            <h1><img src="Icons/Block/SKNCOECDIS-Block 96px.png"  height="64" width="64"> SKNCOECDIS</h1>
            <small> Enter subject details to create new subject.</small>

            <div id="create_subject_form_alert" class="alert" style="display:none"><i id="create_subject_form_alert_icon"></i><span id="create_subject_form_alert_msg"></span></div>
            <form id="create_subject_form" class="form" action="create_subject" method="POST" >
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>subject name</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <input id="create_subject_form_subject_name" class="form-control" type="text" name="subject_name" placeholder="Enter subject name">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>semester</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <select id="create_subject_form_semester" class="form-control custom-select" name="semester">
                                <option>1</option>
                                <option>2</option>       
                                <option>3</option>        
                                <option>4</option>       
                                <option>5</option>       
                                <option>6</option>       
                                <option>7</option>       
                                <option>8</option>       
                            </select>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>academic_year</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <select id="create_subject_form_academic_year" class="form-control custom-select" name="academic_year">
                                <option>FE</option>
                                <option>SE</option>       
                                <option>TE</option>        
                                <option>BE</option>       
                            </select>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>exam pattern</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <input id="create_subject_form_exam_pattern" class="form-control" type="text" name="exam_pattern" placeholder="Enter exam pattern">
                        </div>
                    </div>
                </div>
                <div class="form-group ">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>domain id</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <select id="create_subject_form_domain" class="form-control custom-select" name="domain">
                                <c:forEach items="${domain_list}" var="domain">
                                    <option><c:out value="${domain.domain_id}: ${domain.domain_name}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>subject abbreviation</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <input id="create_subject_form_subject_abbreviation" class="form-control" type="text" name="subject_abbreviation" placeholder="Enter subject abbreviation e.g. TOC for Theory of Computation">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-12 col-sm-12">
                            <input id="create_subject_form_submit" class="btn btn-outline-primary" type="submit" name="submit" value="Save Subject">
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>
<%@include file="footers/main_footer.html"%>
<script src="js/create_subject.js"></script>