var warning_icon_class = "fas fa-exclamation-triangle";
var danger_icon_class = "fas fa-times";
var success_icon_class = "fas fa-check";

function hide_field_error_alert() {
    if(document.getElementById("login_form_alert").style.getPropertyValue("display")==="block"){
   $('#login_form_alert').slideUp();
    }
}

function show_field_error_alert(message, alert_type = "alert-warning") {
    var login_form_alert = document.getElementById("login_form_alert");
    if (alert_type === "alert-warning") {
        document.getElementById("login_form_alert_icon").className = warning_icon_class;
    } else if (alert_type === "alert-success") {
        document.getElementById("login_form_alert_icon").className = success_icon_class;
    } else {
        document.getElementById("login_form_alert_icon").className = danger_icon_class;
    }

    document.getElementById("login_form_alert_msg").innerHTML = " " + message;
    login_form_alert.className = "alert " + alert_type;
    if(document.getElementById("login_form_alert").style.getPropertyValue("display")==="none"){
    $('#login_form_alert').slideDown();
    }
}

document.querySelector("#login_form_submit").addEventListener("click", (event) => {
    if (document.getElementById("login_form_username").value === "" || document.getElementById("login_form_password").value === "") {
        event.preventDefault();
      show_field_error_alert("Enter username and password to login.", "alert-danger");
    }

    if (document.getElementById("login_form_username").value.length > 16 || document.getElementById("login_form_password").value.length > 64) {
        event.preventDefault();
      show_field_error_alert("Incorrect username or password!", "alert-danger");
    }
});

document.querySelector("#login_form_username").addEventListener("keyup", () => {
    if (document.getElementById("login_form_username").value !== "" && document.getElementById("login_form_password").value !== "" && document.getElementById("login_form_username").value.length <= 16 && document.getElementById("login_form_password").value.length <= 64) {
        hide_field_error_alert();
    }
});

document.querySelector("#login_form_password").addEventListener("keyup", () => {
    if (document.getElementById("login_form_username").value !== "" && document.getElementById("login_form_password").value !== "" && document.getElementById("login_form_username").value.length <= 16 && document.getElementById("login_form_password").value.length <= 64) {
        hide_field_error_alert();
    }
});

