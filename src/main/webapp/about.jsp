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

%>

<!-- HTML5 Structure -->
<%@include file="headers/main_header.html"%>
<link href="style/about.css" rel="stylesheet">
<section>
    <div class="rest-body-container container-fluid">
        <div id="about_frame" class="container">

            <h1><img src="Icons/Block/SKNCOECDIS-Block 96px.png"  height="64" width="64"> SKNCOECDIS</h1>
            <small> designed & developed by </small>
            <br>
            <h4><i class="fab fa-connectdevelop"></i> Satyam Sanjay Jadhav, TE, 2018-2019</h4>
            <h4><i class="fab fa-connectdevelop"></i> Omkar Mahadeo Kumbhar, TE, 2018-2019</h4>

            <small> guided by </small>
            <br>
            <h4><i class="fas fa-chalkboard-teacher"></i> Professor. Sandip A. Kahate</h4>
            <h4><i class="fas fa-chalkboard-teacher"></i> Dr. Parikshit N. Mahalle</h4>
        </div>
    </div>
</section>
<%@include file="footers/main_footer.html"%>


