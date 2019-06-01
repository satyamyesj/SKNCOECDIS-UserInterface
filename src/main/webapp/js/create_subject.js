//global varaibles
var incorrect_field_indictor_color = "#ff0000";
var correct_field_indictor_color = "#836AD1";
var warning_icon_class = "fas fa-exclamation-triangle";
var danger_icon_class = "fas fa-times";
var success_icon_class = "fas fa-check";
/*validation functions
 #:form sequence
 0:subject_name
 1:semester
 2:academic_year
 3.exam_pattern
 4.domain_id
 5.subject_abbreviation
 */

function hide_field_error_alert() {
//    var create_subject_form_alert = document.getElementById("create_subject_form_alert");
//    create_subject_form_alert.style.setProperty("display", "none");
    if (document.getElementById("create_subject_form_alert").style.getPropertyValue("display") === "block") {
        $('#create_subject_form_alert').slideUp();
    }
}

function show_field_error_alert(message, alert_type = "alert-warning") {
    var create_subject_form_alert = document.getElementById("create_subject_form_alert");
    if (alert_type === "alert-warning") {
        document.getElementById("create_subject_form_alert_icon").className = warning_icon_class;
    } else if (alert_type === "alert-success") {
        document.getElementById("create_subject_form_alert_icon").className = success_icon_class;
    } else {
        document.getElementById("create_subject_form_alert_icon").className = danger_icon_class;
    }

    document.getElementById("create_subject_form_alert_msg").innerHTML = " " + message;
    create_subject_form_alert.className = "alert " + alert_type;
    //create_subject_form_alert.style.setProperty("display", "block");
    if (document.getElementById("create_subject_form_alert").style.getPropertyValue("display") === "none") {
        $('#create_subject_form_alert').slideDown();
        $('#create_subject_form_alert').css("display", "block");
}
}

function validate_subject_name(should_show_error = false) {
    if (document.getElementById("create_subject_form_subject_name").value === "") {
        return false;
    } else if (document.getElementById("create_subject_form_subject_name").value.length > 50) {
        if (should_show_error) {
            show_field_error_alert("maximum 50 characters are allowed in subject name");
        }
        return  false;
    } else {
        if (should_show_error) {
            hide_field_error_alert();
        }
        return true;
}
}

function validate_semester(should_show_error = false) {
    if (document.getElementById("create_subject_form_semester").value === "") {
        return false;
    } else {
        return true;
}
}

function validate_academic_year(should_show_error = false) {
    if (document.getElementById("create_subject_form_academic_year").value === "") {
        return false;
    } else {
        return true;
}
}

function validate_exam_pattern(should_show_error = false) {
    if (document.getElementById("create_subject_form_exam_pattern").value === "") {
        return false;
    } else if (document.getElementById("create_subject_form_exam_pattern").value.length > 20) {
        if (should_show_error) {
            show_field_error_alert("maximum 20 characters are allowed in exam pattern");
        }
        return  false;
    } else {
        if (should_show_error) {
            hide_field_error_alert();
        }
        return true;
}
}

function validate_domain(should_show_error = false) {
    if (document.getElementById("create_subject_form_domain").value === "") {
        return false;
    } else {
        return true;
}
}

function validate_subject_abbreviation(should_show_error = false) {
    if (document.getElementById("create_subject_form_subject_abbreviation").value === "") {
        return false;
    } else if (document.getElementById("create_subject_form_subject_abbreviation").value.length > 10) {
        if (should_show_error) {
            show_field_error_alert("maximum 10 characters are allowed in subject_abbreviation");
        }
        return  false;
    } else {
        if (should_show_error) {
            hide_field_error_alert();
        }
        return true;
}
}


//incorrect field value handlers
document.querySelector("#create_subject_form_subject_name").addEventListener("keyup", () => {
    validate_subject_name(true);
});

document.querySelector("#create_subject_form_exam_pattern").addEventListener("keyup", () => {
    validate_exam_pattern(true);
});

document.querySelector("#create_subject_form_subject_abbreviation").addEventListener("keyup", () => {
    validate_subject_abbreviation(true);
});


//final form validation
document.querySelector("#create_subject_form_submit").addEventListener("click", (event) => {
    //event.preventDefault();
    var input_field_status = [false, false, false, false, false, false];
    var create_subject_form_alert = document.getElementById("create_subject_form_alert");

    input_field_status[0] = validate_subject_name();
    if (!input_field_status[0]) {
        document.getElementById("create_subject_form_subject_name").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("create_subject_form_subject_name").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }

    input_field_status[1] = validate_semester();
    if (!input_field_status[1]) {
        document.getElementById("create_subject_form_semester").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("create_subject_form_semester").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }

    input_field_status[2] = validate_academic_year();
    if (!input_field_status[2]) {
        document.getElementById("create_subject_form_academic_year").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("create_subject_form_academic_year").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[3] = validate_exam_pattern();
    if (!input_field_status[3]) {
        document.getElementById("create_subject_form_exam_pattern").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("create_subject_form_exam_pattern").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[4] = validate_domain();
    if (!input_field_status[4]) {
        document.getElementById("create_subject_form_domain").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("create_subject_form_domain").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[5] = validate_subject_abbreviation();
    if (!input_field_status[5]) {
        document.getElementById("create_subject_form_subject_abbreviation").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("create_subject_form_subject_abbreviation").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }



    for (var i = 0; i < 6; i++) {
        //console.log(i+" "+input_field_status[i])
        if (input_field_status[i] === false) {
            event.preventDefault();
            show_field_error_alert("All fields are mandatory", "alert-danger");
            break;
        }
    }
});