//global varaibles
var warning_icon_class = "fas fa-exclamation-triangle";
var danger_icon_class = "fas fa-times";
var success_icon_class = "fas fa-check";
//get_library_content_list_form validation

/*validation functions
 #:form sequence
 0:content_title
 1:subject
 2:username
 */

function hide_library_content_field_error_alert() {
    //var get_library_content_list_form_alert = document.getElementById("get_library_content_list_form_alert");
   // get_library_content_list_form_alert.style.setProperty("display", "none");
   if (document.getElementById("get_library_content_list_form_alert").style.getPropertyValue("display") === "block") {
        $('#get_library_content_list_form_alert').slideUp();
    }
}

function show_library_content_field_error_alert(message, alert_type = "alert-warning") {
  var get_library_content_list_form_alert = document.getElementById("get_library_content_list_form_alert");
    if (alert_type === "alert-warning") {
        document.getElementById("get_library_content_list_form_alert_icon").className = warning_icon_class;
    } else if (alert_type === "alert-success") {
        document.getElementById("get_library_content_list_form_alert_icon").className = success_icon_class;
    } else {
        document.getElementById("get_library_content_list_form_alert_icon").className = danger_icon_class;
    }

    document.getElementById("get_library_content_list_form_alert_msg").innerHTML = " " + message;
    get_library_content_list_form_alert.className = "alert " + alert_type;
    //get_library_content_list_form_alert.style.setProperty("display", "block");
    if (document.getElementById("get_library_content_list_form_alert").style.getPropertyValue("display") === "none") {
        $('#get_library_content_list_form_alert').slideDown();
        $('#get_library_content_list_form_alert').css("display","block");
    }
}

function validate_content_title(should_show_error = false) {
    if (document.getElementById("get_library_content_list_form_content_title").value === "") {
        return false;
    } else if (document.getElementById("get_library_content_list_form_content_title").value.length > 50) {
        if (should_show_error) {
            show_library_content_field_error_alert("maximum 50 characters are allowed in content title query");
        }
        return  false;
    } else {
        hide_library_content_field_error_alert();
        return true;
}
}

function validate_subject(should_show_error = false) {
    if (document.getElementById("get_library_content_list_form_subject").value === "") {
        return false;
    } else if (document.getElementById("get_library_content_list_form_subject").value.length > 50) {
        if (should_show_error) {
            show_library_content_field_error_alert("maximum 50 characters are allowed in subject query");
        }
        return  false;
    } else {
        hide_library_content_field_error_alert();
        return true;
}
}

function validate_username(should_show_error = false) {
    if (document.getElementById("get_library_content_list_form_username").value === "") {
        return false;
    } else if (document.getElementById("get_library_content_list_form_username").value.length > 16) {
        if (should_show_error) {
            show_library_content_field_error_alert("maximum 10 characters are allowed in username query");
        }
        return  false;
    } else {
        hide_library_content_field_error_alert();
        return true;
}
}


//incorrect field value handlers
document.querySelector("#get_library_content_list_form_content_title").addEventListener("keyup", () => {
    validate_content_title(true);
});

document.querySelector("#get_library_content_list_form_subject").addEventListener("keyup", () => {
    validate_subject(true);
});

document.querySelector("#get_library_content_list_form_username").addEventListener("keyup", () => {
    validate_username(true);
});


//final form validation
document.querySelector("#get_library_content_list_form_submit").addEventListener("click", (event) => {
    //event.preventDefault();
    var input_field_status = [false, false, false];
    var get_library_content_list_form_alert = document.getElementById("get_library_content_list_form_alert");

    input_field_status[0] = validate_content_title();

    input_field_status[1] = validate_subject();

    input_field_status[2] = validate_username();

    var should_submit = false;
    for (var i = 0; i < 3; i++) {
       // console.log(i+" "+input_field_status[i])
        if (input_field_status[i] === true) {
            should_submit = true;
            break;
        }
    }
    if (!should_submit) {
        event.preventDefault();
        show_library_content_field_error_alert("Enter at least one field to search content", "alert-danger");
    }
    else{
        hide_library_content_field_error_alert();
    }
});
