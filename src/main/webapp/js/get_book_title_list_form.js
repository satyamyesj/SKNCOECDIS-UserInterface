//global varaibles
var warning_icon_class = "fas fa-exclamation-triangle";
var danger_icon_class = "fas fa-times";
var success_icon_class = "fas fa-check";
//get_book_title_list_form validation

/*validation functions
 #:form sequence
 0:book_author
 1:book_title
 2:book_domain
 3:subject_abbreviation
 */

function hide_book_title_field_error_alert() {
//    var get_book_title_list_form_alert = document.getElementById("get_book_title_list_form_alert");
//    get_book_title_list_form_alert.style.setProperty("display", "none");
if (document.getElementById("get_book_title_list_form_alert").style.getPropertyValue("display") === "block") {
        $('#get_book_title_list_form_alert').slideUp();
    }
}

function show_book_title_field_error_alert(message, alert_type = "alert-warning") {
    var get_book_title_list_form_alert = document.getElementById("get_book_title_list_form_alert");
    if (alert_type === "alert-warning") {
        document.getElementById("get_book_title_list_form_alert_icon").className = warning_icon_class;
    } else if (alert_type === "alert-success") {
        document.getElementById("get_book_title_list_form_alert_icon").className = success_icon_class;
    } else {
        document.getElementById("get_book_title_list_form_alert_icon").className = danger_icon_class;
    }

    document.getElementById("get_book_title_list_form_alert_msg").innerHTML = " " + message;
    get_book_title_list_form_alert.className = "alert " + alert_type;
   // get_book_title_list_form_alert.style.setProperty("display", "block");
   if (document.getElementById("get_book_title_list_form_alert").style.getPropertyValue("display") === "none") {
        $('#get_book_title_list_form_alert').slideDown();
         $('#get_book_title_list_form_alert').css("display","block");
    }
}

function validate_book_author(should_show_error = false) {
    if (document.getElementById("get_book_title_list_form_book_author").value === "") {
        return false;
    } else if (document.getElementById("get_book_title_list_form_book_author").value.length > 100) {
        if (should_show_error) {
            show_book_title_field_error_alert("maximum 100 characters are allowed in book author query");
        }
        return  false;
    } else {
        hide_book_title_field_error_alert();
        return true;
}
}

function validate_book_title(should_show_error = false) {
    if (document.getElementById("get_book_title_list_form_book_title").value === "") {
        return false;
    } else if (document.getElementById("get_book_title_list_form_book_title").value.length > 100) {
        if (should_show_error) {
            show_book_title_field_error_alert("maximum 100 characters are allowed in book title query");
        }
        return  false;
    } else {
        hide_book_title_field_error_alert();
        return true;
}
}

function validate_book_domain(should_show_error = false) {
    if (document.getElementById("get_book_title_list_form_book_domain").value === "") {
        return false;
    } else if (document.getElementById("get_book_title_list_form_book_domain").value.length > 100) {
        if (should_show_error) {
            show_book_title_field_error_alert("maximum 100 characters are allowed in book domain query");
        }
        return  false;
    } else {
        hide_book_title_field_error_alert();
        return true;
}
}

function validate_subject_abbreviation(should_show_error = false) {
    if (document.getElementById("get_book_title_list_form_subject_abbreviation").value === "") {
        return false;
    } else if (document.getElementById("get_book_title_list_form_subject_abbreviation").value.length > 10) {
        if (should_show_error) {
            show_book_title_field_error_alert("maximum 10 characters are allowed in subject abbreviation query");
        }
        return  false;
    } else {
        hide_book_title_field_error_alert();
        return true;
}
}


//incorrect field value handlers
document.querySelector("#get_book_title_list_form_book_author").addEventListener("keyup", () => {
    validate_book_author(true);
});

document.querySelector("#get_book_title_list_form_book_title").addEventListener("keyup", () => {
    validate_book_title(true);
});

document.querySelector("#get_book_title_list_form_book_domain").addEventListener("keyup", () => {
    validate_book_domain(true);
});

document.querySelector("#get_book_title_list_form_subject_abbreviation").addEventListener("keyup", () => {
    validate_subject_abbreviation(true);
});


//final form validation
document.querySelector("#get_book_title_list_form_submit").addEventListener("click", (event) => {
    //event.preventDefault();
    var input_field_status = [false, false, false, false];
    var get_book_title_list_form_alert = document.getElementById("get_book_title_list_form_alert");

    input_field_status[0] = validate_book_author();

    input_field_status[1] = validate_book_title();

    input_field_status[2] = validate_book_domain();
    
    input_field_status[3]=validate_subject_abbreviation();

    var should_submit = false;
    for (var i = 0; i < 4; i++) {
        //console.log(i + " " + input_field_status[i])
        if (input_field_status[i] === true) {
            should_submit = true;
            break;
        }
    }
    if (!should_submit) {
        event.preventDefault();
        show_book_title_field_error_alert("Enter at least one field to search book", "alert-danger");
    } else {
        hide_book_title_field_error_alert();
    }
});
