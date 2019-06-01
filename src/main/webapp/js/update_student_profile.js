//global varaibles
var incorrect_field_indictor_color = "#ff0000";
var correct_field_indictor_color = "#836AD1";
var warning_icon_class = "fas fa-exclamation-triangle";
var danger_icon_class = "fas fa-times";
var success_icon_class = "fas fa-check";
/*validation functions
 #:form sequence
 0:first_name
 1:last_name
 2:email
 3:mobile_no
 4:unique_id
 5:academic_year
 6:division
 7:dob
 8:address
 
 */
function hide_field_error_alert() {
//    var student_registration_form_alert = document.getElementById("student_registration_form_alert");
//    student_registration_form_alert.style.setProperty("display", "none");
    if (document.getElementById("student_registration_form_alert").style.getPropertyValue("display") === "block") {
        $('#student_registration_form_alert').slideUp();
    }
}

function show_field_error_alert(message, alert_type = "alert-warning") {
    var student_registration_form_alert = document.getElementById("student_registration_form_alert");
    if (alert_type === "alert-warning") {
        document.getElementById("student_registration_form_alert_icon").className = warning_icon_class;
    } else if (alert_type === "alert-success") {
        document.getElementById("student_registration_form_alert_icon").className = success_icon_class;
    } else {
        document.getElementById("student_registration_form_alert_icon").className = danger_icon_class;
    }

    document.getElementById("student_registration_form_alert_msg").innerHTML = " " + message;
    student_registration_form_alert.className = "alert " + alert_type;
    //student_registration_form_alert.style.setProperty("display", "block");
    if (document.getElementById("student_registration_form_alert").style.getPropertyValue("display") === "none") {
        $('#student_registration_form_alert').slideDown();
        $('#student_registration_form_alert').css("display", "block")
}
}

function validate_first_name(should_show_error = false) {
    if (document.getElementById("student_registration_form_first_name").value === "") {
        return false;
    } else if (document.getElementById("student_registration_form_first_name").value.length > 16) {
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
    if (document.getElementById("student_registration_form_last_name").value === "") {
        return false;
    } else if (document.getElementById("student_registration_form_last_name").value.length > 16) {
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
    if (document.getElementById("student_registration_form_email").value === "") {
        return false;
    } else if (document.getElementById("student_registration_form_email").value.length > 255 || !regx_for_email.test(document.getElementById("student_registration_form_email").value)) {
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
    if (!regx_for_mobile_no.test(document.getElementById("student_registration_form_mobile_no").value)) {
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

function validate_unique_id(should_show_error = false) {
    var regx_for_unique_id = /^[a-zA-Z0-9]{1,9}$/;
    if (!regx_for_unique_id.test(document.getElementById("student_registration_form_unique_id").value)) {
        if (should_show_error) {
            show_field_error_alert("maximum 9 characters are allowed in unique id/PRN");
        }
        return false;
    } else {
        if (should_show_error) {
            hide_field_error_alert();
        }
        return true;
}
}

function validate_academic_year(should_show_error = false) {
    if (document.getElementById("student_registration_form_academic_year").value === "") {
        return false;
    } else {
        return true;
}
}

function validate_division(should_show_error = false) {
    if (document.getElementById("student_registration_form_division").value.length !== 1) {
        if (should_show_error) {
            show_field_error_alert("incorrect division");
        }
        return false;
    } else {
        if (should_show_error) {
            hide_field_error_alert();
        }
        return true;
}
}

function validate_dob(should_show_error = false) {
    if (document.getElementById("student_registration_form_dob").value === "") {
        return false;
    } else {
        return true;
}
}

function validate_address(should_show_error = false) {
    if (document.getElementById("student_registration_form_address").value === "") {
        return false;
    } else if (document.getElementById("student_registration_form_address").value.length > 200) {
        if (should_show_error) {
            show_field_error_alert("maximum 200 characters are allowed in address");
        }
        return false;
    } else {
        if (should_show_error) {
            hide_field_error_alert();
        }
        return true;
}
}



//incorrect field value handlers
document.querySelector("#student_registration_form_first_name").addEventListener("keyup", () => {
    validate_first_name(true);
});
document.querySelector("#student_registration_form_last_name").addEventListener("keyup", () => {
    validate_last_name(true);
});
document.querySelector("#student_registration_form_email").addEventListener("keyup", () => {
    validate_email(true);
});
document.querySelector("#student_registration_form_mobile_no").addEventListener("keyup", () => {
    validate_mobile_no(true);
});
document.querySelector("#student_registration_form_unique_id").addEventListener("keyup", () => {
    validate_unique_id(true);
});
document.querySelector("#student_registration_form_division").addEventListener("keyup", () => {
    validate_division(true);
});
document.querySelector("#student_registration_form_address").addEventListener("keyup", () => {
    validate_address(true);
});

//final form validation
document.querySelector("#student_registration_form_submit").addEventListener("click", (event) => {
    var input_field_status = [false, false, false, false, false, false, false, false, false];
    var student_registration_form_alert = document.getElementById("student_registration_form_alert");
    //TODO highlight incrrect fields
    input_field_status[0] = validate_first_name();
    if (!input_field_status[0]) {
        document.getElementById("student_registration_form_first_name").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("student_registration_form_first_name").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[1] = validate_last_name();
    if (!input_field_status[1]) {
        document.getElementById("student_registration_form_last_name").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("student_registration_form_last_name").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[2] = validate_email();
    if (!input_field_status[2]) {
        document.getElementById("student_registration_form_email").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("student_registration_form_email").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[3] = validate_mobile_no();
    if (!input_field_status[3]) {
        document.getElementById("student_registration_form_mobile_no").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("student_registration_form_mobile_no").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[4] = validate_unique_id();
    if (!input_field_status[4]) {
        document.getElementById("student_registration_form_unique_id").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("student_registration_form_unique_id").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[5] = validate_academic_year();
    if (!input_field_status[5]) {
        document.getElementById("student_registration_form_academic_year").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("student_registration_form_academic_year").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[6] = validate_division();
    if (!input_field_status[6]) {
        document.getElementById("student_registration_form_division").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("student_registration_form_division").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[7] = validate_dob();
    if (!input_field_status[7]) {
        document.getElementById("student_registration_form_dob").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("student_registration_form_dob").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[8] = validate_address();
    if (!input_field_status[8]) {
        document.getElementById("student_registration_form_address").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("student_registration_form_address").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }

    for (var i = 0; i < 9; i++) {
        if (input_field_status[i] === false) {
            event.preventDefault();
            show_field_error_alert("All fields are mandatory", "alert-danger");
            break;
        }
    }
});




