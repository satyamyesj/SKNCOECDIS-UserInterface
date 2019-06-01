//global varaibles
var weather_username_is_unique = false;
var incorrect_field_indictor_color = "#ff0000";
var correct_field_indictor_color = "#836AD1";
var warning_icon_class = "fas fa-exclamation-triangle";
var danger_icon_class = "fas fa-times";
var success_icon_class = "fas fa-check";

function show_password() {
    var hide_password_icon = "fas fa-eye-slash";
    var show_password_icon = "fas fa-eye";
    if (document.getElementById("show_password_icon").className === show_password_icon) {
        document.getElementById("show_password_icon").className = hide_password_icon;
        document.getElementById("employee_registration_form_confirm_password").type = "text";
    } else {
        document.getElementById("show_password_icon").className = show_password_icon;
        document.getElementById("employee_registration_form_confirm_password").type = "password";
    }
}

/*validation functions
 #:form sequence
 0:username
 1:first_name
 2:last_name
 3:email
 4:mobile_no
 5:password
 6:employee_no
 */
function hide_field_error_alert() {
//    var employee_registration_form_alert = document.getElementById("employee_registration_form_alert");
//    employee_registration_form_alert.style.setProperty("display", "none");
    if (document.getElementById("employee_registration_form_alert").style.getPropertyValue("display") === "block") {
        $('#employee_registration_form_alert').slideUp();
    }
}

function show_field_error_alert(message, alert_type = "alert-warning") {
    var employee_registration_form_alert = document.getElementById("employee_registration_form_alert");
    if (alert_type === "alert-warning") {
        document.getElementById("employee_registration_form_alert_icon").className = warning_icon_class;
    } else if (alert_type === "alert-success") {
        document.getElementById("employee_registration_form_alert_icon").className = success_icon_class;
    } else {
        document.getElementById("employee_registration_form_alert_icon").className = danger_icon_class;
    }

    document.getElementById("employee_registration_form_alert_msg").innerHTML = " " + message;
    employee_registration_form_alert.className = "alert " + alert_type;
    // employee_registration_form_alert.style.setProperty("display", "block");
    if (document.getElementById("employee_registration_form_alert").style.getPropertyValue("display") === "none") {
        $('#employee_registration_form_alert').slideDown();
        $('#employee_registration_form_alert').css("display", "block");
}
}
function validate_username(should_show_error = false) {
    return weather_username_is_unique;
}

function validate_first_name(should_show_error = false) {
    if (document.getElementById("employee_registration_form_first_name").value === "") {
        return false;
    } else if (document.getElementById("employee_registration_form_first_name").value.length > 16) {
        if (should_show_error) {
            show_field_error_alert("maximum 16 characters are allowed in first name");
        }
        return  false;
    } else {
        if (should_show_error) {
            hide_field_error_alert();
        }
        return true;
}
}

function validate_last_name(should_show_error = false) {
    if (document.getElementById("employee_registration_form_last_name").value === "") {
        return false;
    } else if (document.getElementById("employee_registration_form_last_name").value.length > 16) {
        if (should_show_error) {
            show_field_error_alert("maximum 16 characters are allowed in last name");
        }
        return false;
    } else {
        if (should_show_error) {
            hide_field_error_alert();
        }
        return true;
}
}

function validate_email(should_show_error = false) {
    var regx_for_email = /^([a-zA-Z0-9\.\-]+)@([a-zA-Z0-9]+)\.([a-z]{2,20})$/;
    if (document.getElementById("employee_registration_form_email").value === "") {
        return false;
    } else if (document.getElementById("employee_registration_form_email").value.length > 255 || !regx_for_email.test(document.getElementById("employee_registration_form_email").value)) {
        if (should_show_error) {
            show_field_error_alert("incorrect email");
        }
        return false;
    } else {
        if (should_show_error) {
            hide_field_error_alert();
        }
        return true;
}
}

function validate_mobile_no(should_show_error = false) {
    var regx_for_mobile_no = /^[0-9]{10,10}$/;
    if (!regx_for_mobile_no.test(document.getElementById("employee_registration_form_mobile_no").value)) {
        if (should_show_error) {
            show_field_error_alert("incorrect mobile no");
        }
        return false;
    } else {
        if (should_show_error) {
            hide_field_error_alert();
        }
        return true;
}
}

function validate_create_password(should_show_error = false) {
    if (document.getElementById("employee_registration_form_create_password").value.length < 6 || document.getElementById("employee_registration_form_create_password").value.length > 64) {
        if (should_show_error) {
            show_field_error_alert("6 to 64 characters are allowed in password");
        }
        return false;
    } else if (document.getElementById("employee_registration_form_confirm_password").value !== "" && (document.getElementById("employee_registration_form_create_password").value !== document.getElementById("employee_registration_form_confirm_password").value)) {
        if (should_show_error) {
            show_field_error_alert("passwords are not matching");
        }
        return false;
    } else {
        if (should_show_error) {
            hide_field_error_alert();
        }
        return true;
}
}

function validate_confirm_password(should_show_error = false) {
    if (document.getElementById("employee_registration_form_create_password").value.length < 6 || document.getElementById("employee_registration_form_create_password").value.length > 64) {
        if (should_show_error) {
            show_field_error_alert("6 to 64 characters are allowed in password");
        }
        return false;
    } else if (document.getElementById("employee_registration_form_create_password").value !== document.getElementById("employee_registration_form_confirm_password").value) {
        if (should_show_error) {
            show_field_error_alert("passwords are not matching");
        }
        return false;
    } else {
        if (should_show_error) {
            hide_field_error_alert();
        }
        return true;
}
}

function validate_employee_no(should_show_error = false) {
    var regx_for_employee_no = /^[a-zA-Z0-9]{1,20}$/;
    if (!regx_for_employee_no.test(document.getElementById("employee_registration_form_employee_no").value)) {
        if (should_show_error) {
            show_field_error_alert("maximum 20 characters are allowed in employee no");
        }
        return false;
    } else {
        if (should_show_error) {
            hide_field_error_alert();
        }
        return true;
}
}

//ajax call for uniquness of username
document.querySelector("#employee_registration_form_username").addEventListener("focusout", () => {
    var input_username = document.getElementById("employee_registration_form_username").value;
    var employee_registration_form_alert = document.getElementById("employee_registration_form_alert");
    if (input_username !== "" && input_username.length <= 16) {
        var xhtm = new XMLHttpRequest();
        xhtm.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                if (this.responseText === "USERNAME_AVAILABLE") {
                    show_field_error_alert("username is available", "alert-success");
                    weather_username_is_unique = true;
                } else if (this.responseText === "USERNAME_UNAVAILABLE") {
                    show_field_error_alert("username is unavailable, try another username");
                    weather_username_is_unique = false;
                } else if (this.responseText === "DATABASE_SERVER_ERROR") {
                    show_field_error_alert("database server is not responding!", "alert-danger");
                    weather_username_is_unique = false;
                }
            }
        };
        xhtm.open('GET', "check_weather_username_is_available?input_username=" + input_username, true);
        xhtm.send();
    } else if (input_username !== "" && input_username.length > 16) {
        show_field_error_alert("maximum 16 characters are allowed in username");
        weather_username_is_unique = false;
    }
});

//incorrect field value handlers
document.querySelector("#employee_registration_form_first_name").addEventListener("keyup", () => {
    validate_first_name(true);
});
document.querySelector("#employee_registration_form_last_name").addEventListener("keyup", () => {
    validate_last_name(true);
});
document.querySelector("#employee_registration_form_email").addEventListener("keyup", () => {
    validate_email(true);
});
document.querySelector("#employee_registration_form_mobile_no").addEventListener("keyup", () => {
    validate_mobile_no(true);
});
document.querySelector("#employee_registration_form_create_password").addEventListener("keyup", () => {
    validate_create_password(true);
});
document.querySelector("#employee_registration_form_confirm_password").addEventListener("keyup", () => {
    validate_confirm_password(true);
});
document.querySelector("#employee_registration_form_employee_no").addEventListener("keyup", () => {
    validate_employee_no(true);
});

//final form validation
document.querySelector("#employee_registration_form_submit").addEventListener("click", (event) => {
    var input_field_status = [false, false, false, false, false, false, false];
    var employee_registration_form_alert = document.getElementById("employee_registration_form_alert");
    input_field_status[0] = validate_username();
    if (!input_field_status[0]) {
        document.getElementById("employee_registration_form_username").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("employee_registration_form_username").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[1] = validate_first_name();
    if (!input_field_status[1]) {
        document.getElementById("employee_registration_form_first_name").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("employee_registration_form_first_name").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[2] = validate_last_name();
    if (!input_field_status[2]) {
        document.getElementById("employee_registration_form_last_name").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("employee_registration_form_last_name").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[3] = validate_email();
    if (!input_field_status[3]) {
        document.getElementById("employee_registration_form_email").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("employee_registration_form_email").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[4] = validate_mobile_no();
    if (!input_field_status[4]) {
        document.getElementById("employee_registration_form_mobile_no").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("employee_registration_form_mobile_no").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    if (!validate_create_password()) {
        document.getElementById("employee_registration_form_create_password").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("employee_registration_form_create_password").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[5] = validate_confirm_password();
    if (!input_field_status[5]) {
        document.getElementById("employee_registration_form_confirm_password").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("employee_registration_form_confirm_password").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[6] = validate_employee_no();
    if (!input_field_status[6]) {
        document.getElementById("employee_registration_form_employee_no").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("employee_registration_form_employee_no").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }

    for (var i = 0; i < 7; i++) {
        if (input_field_status[i] === false) {
            event.preventDefault();
            show_field_error_alert("All fields are mandatory", "alert-danger");
            break;
        }
    }
});




