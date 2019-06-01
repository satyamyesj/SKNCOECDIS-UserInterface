//global varaibles
var warning_icon_class = "fas fa-exclamation-triangle";
var danger_icon_class = "fas fa-times";
var success_icon_class = "fas fa-check";
//get_report_title_list_form validation

/*validation functions
 #:form sequence
 0:report_title
 1:report_domain
 */

function hide_report_title_field_error_alert() {
//    var get_report_title_list_form_alert = document.getElementById("get_report_title_list_form_alert");
//    get_report_title_list_form_alert.style.setProperty("display", "none");
     if (document.getElementById("get_report_title_list_form_alert").style.getPropertyValue("display") === "block") {
        $('#get_report_title_list_form_alert').slideUp();
    }
}

function show_report_title_field_error_alert(message, alert_type = "alert-warning") {
    var get_report_title_list_form_alert = document.getElementById("get_report_title_list_form_alert");
    if (alert_type === "alert-warning") {
        document.getElementById("get_report_title_list_form_alert_icon").className = warning_icon_class;
    } else if (alert_type === "alert-success") {
        document.getElementById("get_report_title_list_form_alert_icon").className = success_icon_class;
    } else {
        document.getElementById("get_report_title_list_form_alert_icon").className = danger_icon_class;
    }

    document.getElementById("get_report_title_list_form_alert_msg").innerHTML = " " + message;
    get_report_title_list_form_alert.className = "alert " + alert_type;
   // get_report_title_list_form_alert.style.setProperty("display", "block");
   if (document.getElementById("get_report_title_list_form_alert").style.getPropertyValue("display") === "none") {
        $('#get_report_title_list_form_alert').slideDown();
          $('#get_report_title_list_form_alert').css("display","block");
    }
}


function validate_report_title(should_show_error = false) {
    if (document.getElementById("get_report_title_list_form_report_title").value === "") {
        return false;
    } else if (document.getElementById("get_report_title_list_form_report_title").value.length > 200) {
        if (should_show_error) {
            show_report_title_field_error_alert("maximum 200 characters are allowed in report title query");
        }
        return  false;
    } else {
        hide_report_title_field_error_alert();
        return true;
}
}

function validate_report_domain(should_show_error = false) {
    if (document.getElementById("get_report_title_list_form_report_domain").value === "") {
        return false;
    } else if (document.getElementById("get_report_title_list_form_report_domain").value.length > 100) {
        if (should_show_error) {
            show_report_title_field_error_alert("maximum 100 characters are allowed in report domain query");
        }
        return  false;
    } else {
        hide_report_title_field_error_alert();
        return true;
}
}


//incorrect field value handlers
document.querySelector("#get_report_title_list_form_report_title").addEventListener("keyup", () => {
    validate_report_title(true);
});

document.querySelector("#get_report_title_list_form_report_domain").addEventListener("keyup", () => {
    validate_report_domain(true);
});


//final form validation
document.querySelector("#get_report_title_list_form_submit").addEventListener("click", (event) => {
    //event.preventDefault();
    var input_field_status = [false, false];
    var get_report_title_list_form_alert = document.getElementById("get_report_title_list_form_alert");

    input_field_status[0] = validate_report_title();

    input_field_status[1] = validate_report_domain();
    
    var should_submit = false;
    for (var i = 0; i < 2; i++) {
        //console.log(i + " " + input_field_status[i])
        if (input_field_status[i] === true) {
            should_submit = true;
            break;
        }
    }
    if (!should_submit) {
        event.preventDefault();
        show_report_title_field_error_alert("Enter at least one field to search report", "alert-danger");
    } else {
        hide_report_title_field_error_alert();
    }
});
