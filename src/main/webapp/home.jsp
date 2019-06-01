<%-- 
    Document   : home
    Created on : 4 Jan, 2019, 1:38:58 PM
    Author     : windows
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DatabaseAccessObjects.QueryObjects.User"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f"  uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- JSP Code -->
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");  //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setHeader("Expires", "0"); //Proxies

    User user = null;

    boolean isContentFileFormatKnown = false;
    if (session.getAttribute("RELOGIN") == null) {
        if (session.getAttribute("user") == null) {
            response.sendRedirect("index.jsp");
        } else {
            user = (User) session.getAttribute("user");
        }
    } else {
        response.sendRedirect("index.jsp");
    }
%>

<!-- HTML5 Structure -->
<%@include file="headers/main_header.html"%>
<link href="style/home.css" rel="stylesheet">
<!-- rest body -->
<div class="rest-body-container container-fluid">
    <!-- rest header -->     
    <c:if test="${servlet_error!=null}">
        <div id="servlet_error_alert" class="alert ${servlet_error.errorType}"><i class="fas fa-times"></i> ${servlet_error.errorMessage}</div>
        ${servlet_error=null}
    </c:if>
    <c:if test="${servlet_message!=null}">
        <div id="servlet_message_alert" class="alert ${servlet_message.messageType}"><i class="far fa-comment-alt"></i> ${servlet_message.message}</div>
        ${servlet_message=null}
    </c:if> 
    <div id="home_body_frame">
        <div class="row" >
            <div id="rest-body-col-3" class="col-md-3">
                <!-- search navigation and statistics -->
                <aside>
                    <div class="container">
                        <div class="list-group">
                            <a id="search_navigation" class="list-group-item " href=""><i class="fas fa-search"></i> Search</a>
                            <a class="list-group-item " href="#library_content_search"><i class="fas fa-folder-open"></i> Content <span class="badge badge-secondary float-right"> <c:out value="${statistics.total_library_content}"></c:out></span></a>
                            <a class="list-group-item " href="#book_search"><i class="fas fa-book-open"></i> Books <span class="badge badge-secondary float-right"> <c:out value="${statistics.total_books}"></c:out></span></a>
                            <a class="list-group-item " href="#report_search"><i class="fas fa-file-signature"></i> Reports <span class="badge badge-secondary float-right"> <c:out value="${statistics.total_reports}"></c:out></span></a>
                            </div>
                            <br>

                            <!--            <aside class="card card-body">
                                            <h4>Disk space</h4>
                                            <div class="progress">
                                                <div class="progress-bar" style="width:50%;">50%</div>
                                            </div>
                                            <br>
                                            <h4>Banwidth used</h4>
                                            <div class="progress">
                                                <div class="progress-bar" style="width:60%;">60%</div>
                                            </div>
                                        </aside>-->

                        </div>
                    </aside>
                </div>

                <div id="rest-body-col-9" class="col-md-9">
                    <!-- library content search -->
                    <section id="library_content_search">
                        <div class="container">
                            <div id="get_library_content_list_form_alert" class="alert" style="display:none"><i id="get_library_content_list_form_alert_icon"></i><span id="get_library_content_list_form_alert_msg"></span></div>
                            <form id="library_content_search_form"class="form-inline custom-form" action="get_library_content_list" method="POST">
                                <div class="form-group">
                                    <!--                                <label>content title</label>-->
                                    <input id="get_library_content_list_form_content_title" class="form-control mb-2 mr-sm-2" type="text" name="content_title" placeholder="Content Title">
                                </div>
                                <div class="form-group">
                                    <!--                                <label>subject</label>-->
                                    <input id="get_library_content_list_form_subject" class="form-control mb-2 mr-sm-2" type="text" name="subject" placeholder="Subject">
                                </div>
                                <div class="form-group">
                                    <!--                                <label>by</label>-->
                                    <input id="get_library_content_list_form_username" class="form-control mb-2 mr-sm-2" type="text" name="username" placeholder="By">
                                </div>
                                <div class="form-group">
                                    <!--                                <label>exam pattern</label>-->
                                    <select id="get_library_content_list_form_exam_pattern" class="form-control mb-2 mr-sm-2 custom-select" name="exam_pattern">
                                        <option value="NOT_ASSIGNED">Exam Pattern</option>                 
                                    <c:forEach items="${exam_patterns_list}" var="exam_pattern">
                                        <option><c:out value = "${exam_pattern}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <!--                            <label>content type</label>-->
                                <select id="get_library_content_list_form_content_type" class="form-control mb-2 mr-sm-2 custom-select" name="library_content_type">
                                    <option value="NOT_ASSIGNED">Content Type</option>                                            
                                    <c:forEach items="${library_content_types_list}" var="library_content_type">
                                        <option><c:out value = "${library_content_type.type_id}: ${library_content_type.type}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <button id="get_library_content_list_form_submit" class="form-control mb-2 mr-sm-2 btn btn-outline-primary" type="submit" name="submit" value="Search"><i class="fas fa-search"></i></button>
                            </div>
                        </form>
                    </div>
                    <br>
                    <div class="container">
                        <c:if test="${library_content_query_result_set!=null && library_content_query_result_set.size()>0}">
                            <table id="library_content_query_result_table" class="table table-striped">
                                <thead class="thead-dark">
                                    <tr class="table-header">
                                        <th class="table-header hidable" scope="col"><i class="fas fa-folder-open"></i></th>
                                        <th class="table-header" scope="col">Title</th>
                                        <th class="table-header" scope="col">Details</th>
                                        <th class="table-header" scope="col">Size</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${library_content_query_result_set}" var="library_content">
                                        <c:set var="isContentFileFormatKnown" value="${false}"></c:set>
                                            <tr>
                                            <c:if test="${library_content.content_file_extension=='.text'}">
                                                <th class="table-data hidable" scope="row"><i class="fas fa-file-alt"></i></th>
                                                    <c:set var="isContentFileFormatKnown" value="${true}"></c:set>
                                                </c:if>
                                                <c:if test="${library_content.content_file_extension=='.pdf'}">
                                                <th class="table-data hidable" scope="row"><i class="fas fa-file-pdf"></i></th>
                                                    <c:set var="isContentFileFormatKnown" value="${true}"></c:set>
                                                </c:if>
                                                <c:if test="${library_content.content_file_extension=='.ppt' || library_content.content_file_extension=='.pptx'}">
                                                <th class="table-data hidable" scope="row"><i class="fas fa-file-powerpoint"></i></th>
                                                    <c:set var="isContentFileFormatKnown" value="${true}"></c:set>
                                                </c:if>
                                                <c:if test="${isContentFileFormatKnown==false}">
                                                <th class="table-data hidable" scope="row"><i class="fas fa-file"></i></th>
                                                </c:if>
                                            <td class="table-data resizable_title" >${library_content.content_title}</td>
                                            <td class="table-data" >${library_content.subjectQueryResult.subject_abbreviation} ${library_content.subjectQueryResult.exam_pattern} Pattern, by ${library_content.username}</td>
                                            <td class="table-data" >${String.format("%.02f",(library_content.content_file_size*0.00000095367432))} MB <a href="save_library_content?content_id=${library_content.content_id}"><i class="fas fa-download"></i></a></th>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>   
                        </c:if>
                        <c:if test="${library_content_query_result_set==null || library_content_query_result_set.size()==0}">
                            <div class="card">
                                <div class="card-header">
                                    <h3><i class="fas fa-folder-open fa-x"></i> Search Content</h3>
                                </div>
                                <div class="card-body">
                                    <h6> <i class="fas fa-download"></i>  Search and download the content.</h6>    
                                    <p>Here you can find all PPT/ PDF Notes or PDF Books of academic subjects uploaded by respective teachers. Along with previous exam's question papers.</p>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </section>

                <!-- book search -->
                <section id="book_search">
                    <div class="container">
                        <div id="get_book_title_list_form_alert" class="alert"  style="display:none"><i id="get_book_title_list_form_alert_icon"></i><span id="get_book_title_list_form_alert_msg"></span></div>
                        <form id="book_search_form" class="form-inline" action="get_book_title_list" method="POST" >
                            <div class="form-group">
                                <!--                            <label>Book Author</label>-->
                                <input id="get_book_title_list_form_book_author" class="form-control mb-2 mr-sm-2" type="text" name="book_author" placeholder="Book Author">
                            </div>
                            <div class="form-group">
                                <!--                            <label>Book Title</label>-->
                                <input id="get_book_title_list_form_book_title" class="form-control mb-2 mr-sm-2" type="text" name="book_title" placeholder="Book Title">
                            </div>
                            <div class="form-group">
                                <!--                            <label>Book Domain</label>-->
                                <input id="get_book_title_list_form_book_domain" class="form-control mb-2 mr-sm-2" type="text" name="book_domain" placeholder="Book Domain">
                            </div>
                            <div class="form-group">
                                <!--                            <label>Subject Abbreviation</label>-->
                                <input id="get_book_title_list_form_subject_abbreviation" class="form-control mb-2 mr-sm-2" type="text" name="subject_abbreviation" placeholder="Subject">
                            </div>
                            <div class="form-group">
                                <button id="get_book_title_list_form_submit" class="form-control mb-2 mr-sm-2 btn btn-outline-primary" type="submit" name="submit" value="Search"><i class="fas fa-search"></i></button>
                            </div>
                        </form>
                    </div>
                    <br>
                    <div class="container">
                        <c:if test="${book_search_query_result_set!=null && book_search_query_result_set.size()>0}">
                            <table id="book_search_query_result_table" class="table table-striped">
                                <thead class="thead-dark">
                                    <tr>
                                        <th class="table-header hidable" scope="col"><i class="fas fa-book-open"></i></th>
                                        <th class="table-header" scope="col">Title</th>
                                        <th class="table-header" scope="col">Author</th>
                                        <th class="table-header scalable_title" scope="col">#</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${book_search_query_result_set}" var="book_title">
                                        <tr>
                                            <th class="table-data hidable" scope="row"> <i class="fas fa-book"></i></th>
                                            <td class="table-data">${book_title.book_title}</td>
                                            <td class="table-data resizable_title">${book_title.book_author}</td>
                                            <td class="table-data">${book_title.available_copies}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>    
                        </c:if>
                        <c:if test="${book_search_query_result_set==null || book_search_query_result_set.size()==0}">
                            <div class="card">
                                <div class="card-header">
                                    <h3><i class="fas fa-book-open fa-x"></i> Search Book</h3>
                                </div>
                                <div class="card-body">
                                    <h6> <i class="fas fa-clipboard-check"></i>  Search books to check availability.</h6>    
                                    <p>Computer department library has variety of books, you can issue using your username. At first time you have to verify your profile details registered with SCKNCOECDIS by showing your fee receipt.</p>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </section>

                <!-- report search -->
                <section id="report_search">
                    <div class="container">
                        <div id="get_report_title_list_form_alert" class="alert"  style="display:none"><i id="get_report_title_list_form_alert_icon"></i><span id="get_report_title_list_form_alert_msg"></span></div>
                        <form id="report_search_form" class="form-inline" action="get_report_title_list" method="POST">
                            <div class="form-group">
                                <!--                            <label>Report Title</label>-->
                                <input id="get_report_title_list_form_report_title" class="form-control mb-2 mr-sm-2" type="text" name="report_title" placeholder="Report Title">
                            </div>
                            <div class="form-group">
                                <!--                            <label>Report Domain</label>-->
                                <input id="get_report_title_list_form_report_domain" class="form-control mb-2 mr-sm-2" type="text" name="report_domain" placeholder="Report Domain">
                            </div>
                            <div class="form-group">
                                <button id="get_report_title_list_form_submit" class="form-control mb-2 mr-sm-2 btn btn-outline-primary" type="submit" name="submit" value="Search"><i class="fas fa-search"></i></button>
                            </div>
                        </form>
                    </div>
                    <br>
                    <div class="container">
                        <c:if test="${report_search_query_result_set!=null && report_search_query_result_set.size()>0}">
                            <table id="report_search_query_result_table" class="table table-striped">
                                <thead class="thead-dark">
                                    <tr>
                                        <th class="table-header hidable" scope="col"><i class="fas fa-file-signature"></i></th>
                                        <th class="table-header" scope="col">Title</th>
                                        <th class="table-header" scope="col">#</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${report_search_query_result_set}" var="report_title">
                                        <tr>
                                            <th class="table-data hidable" scope="row"><i class="fas fa-file-invoice"></i></th>
                                            <td class="table-data resizable_title">${report_title.report_title}</td>
                                            <td class="table-data">${report_title.availability_status}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table> 
                        </c:if>
                        <c:if test="${report_search_query_result_set==null || report_search_query_result_set.size()==0}">
                            <div class="card">
                                <div class="card-header">
                                    <h3> <i class="fas fa-file-signature fa-x"></i> Search Report</h3>
                                </div>
                                <div class="card-body">
                                    <h6> <i class="fas fa-lightbulb fa-x"></i>  Search reports to get references for your project.</h6>    
                                    <p>Computer department library has variety of project reports, you can issue using your username. At first time you have to verify your profile details registered with SCKNCOECDIS by showing your fee receipt.</p>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </section>
            </div>
        </div>
    </div>
</div>

<%@include file="footers/main_footer.html"%>
<script type="text/javascript">
    var warning_icon_class = "fas fa-exclamation-triangle";
    var danger_icon_class = "fas fa-times";
    var success_icon_class = "fas fa-check";

    function hide_alert(alert_id) {
//    var student_registration_form_alert = document.getElementById("student_registration_form_alert");
//    student_registration_form_alert.style.setProperty("display", "none");    
        if (document.getElementById(alert_id).style.getPropertyValue("display") === "block") {
            $('#' + alert_id).slideUp();
        }
    }

    function show_alert(alert_id, message, alert_type = "alert-warning") {
        var student_registration_form_alert = document.getElementById(alert_id);
        if (alert_type === "alert-warning") {
            document.getElementById(alert_id + "_icon").className = warning_icon_class;
        } else if (alert_type === "alert-success") {
            document.getElementById(alert_id + "_icon").className = success_icon_class;
        } else {
            document.getElementById(alert_id + "_icon").className = danger_icon_class;
        }

        document.getElementById(alert_id + "_msg").innerHTML = " " + message;
        student_registration_form_alert.className = "alert " + alert_type;
        //student_registration_form_alert.style.setProperty("display","block");
        if (document.getElementById(alert_id).style.getPropertyValue("display") === "none") {
            $('#' + alert_id).slideDown();
             $('#' + alert_id).css("display","block");
    }
    }

    var recently_loaded_table = "<%= (String) session.getAttribute("recently_loaded_table")%>";
    var recently_loaded_table_size = "<%=(String) session.getAttribute("recently_loaded_table_size")%>";


    if (recently_loaded_table !== "null" && document.getElementById(recently_loaded_table) !== null) {
        $('html, body').animate({
            scrollTop: $('#' + recently_loaded_table).offset().top
        }, 'slow');
    }

    var target_alert;
    if (recently_loaded_table === "library_content_query_result_table") {
        target_alert = "get_library_content_list_form_alert";
    } else if (recently_loaded_table === "book_search_query_result_table") {
        target_alert = "get_book_title_list_form_alert";
    } else {
        target_alert = "get_report_title_list_form_alert";
    }

    if (recently_loaded_table_size === "0") {
        show_alert(target_alert, "no result found.", "alert-warning");
    } else {
        hide_alert(target_alert);
    }
</script>
<script src="js/get_library_content_list_form.js"></script>
<script src="js/get_book_title_list_form.js"></script>
<script src="js/get_report_title_list_form.js"></script>