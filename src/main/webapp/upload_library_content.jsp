<%-- 
    Document   : home
    Created on : 4 Jan, 2019, 1:38:58 PM
    Author     : windows
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="DatabaseAccessObjects.QueryObjects.User"%>
<%@page import="DatabaseAccessObjects.ResultObjects.LibraryContentTypesQueryResult"%>
<%@page import="DatabaseAccessObjects.ResultObjects.SubjectQueryResult"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f"  uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- JSP Code -->
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");  //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setHeader("Expires", "0"); //Proxies

    User user = null;
    if (session.getAttribute("RELOGIN") == null) {
        if (session.getAttribute("user") == null || session.getAttribute("subject_list") == null || !((User) session.getAttribute("user")).user_role.equals("E")) {
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
<link href="style/upload_library_content.css" rel="stylesheet">
<section> 
    <div class="rest-body-container container-fluid">
        <div id="upload_library_content_form_frame" class="container">
            <c:if test="${servlet_error!=null}">
                <div id="servlet_error_alert" class="alert ${servlet_error.errorType}">${servlet_error.errorMessage}</div>
                ${servlet_error=null}
            </c:if>
            <c:if test="${servlet_message!=null}">
                <div id="servlet_message_alert" class="alert ${servlet_message.messageType}">${servlet_message.message}</div>
                ${servlet_message=null}
            </c:if>  


            <h1><img src="Icons/Block/SKNCOECDIS-Block 96px.png"  height="64" width="64"> SKNCOECDIS</h1>
            <small> Enter description & choose file to upload.</small>
            <div id="upload_library_content_form_alert" class="alert" style="display:none"><i id="upload_library_content_form_alert_icon"></i><span id="upload_library_content_form_alert_msg"></span></div>
            <form id="upload_library_content_form"class="form" action="upload_library_content" method="POST" enctype="multipart/form-data">
                <div class="form-group ">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>Choose content type</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <select id="upload_library_content_form_content_type" class="form-control custom-select" name="library_content_type">
                                <c:forEach items="${library_content_types_list}" var="library_content_type">
                                    <option><c:out value="${library_content_type.type_id}: ${library_content_type.type}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>Choose subject</label>
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <select id="upload_library_content_form_subject" class="form-control custom-select" name="subject">
                                <c:forEach items="${subject_list}" var="subject">
                                    <option><c:out value = "${subject.subject_id}: ${subject.subject_name}, ${subject.academic_year}, ${subject.exam_pattern} Pattern"/></option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-2 col-sm-2">
                           <a href="get_create_subject_info" class="btn btn-outline-primary">+Subject</a>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>Content title</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <input id="upload_library_content_form_content_title" class="form-control" type="text" name="content_title" placeholder="e.g. TOC Unit 4 Notes">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <label>Choose file</label>
                        </div>
                        <div class="col-md-8 col-sm-8">
                            <div id="test" class="custom-file">
                                <label id="upload_library_content_form_content_file_label" class="custom-file-label" >Max Size 16 MB</label>
                                <input id="upload_library_content_form_content_file" class="custom-file-input" type="file" name="content_file">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-12 col-sm-12">
                            <input id="upload_library_content_form_submit" class="btn btn-outline-primary" type="submit" name="submit" value="Save Content">
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>
<%@include file="footers/main_footer.html"%>
<script src="js/upload_library_content.js"></script>