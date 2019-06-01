//global varaibles
var incorrect_field_indictor_color = "#ff0000";
var correct_field_indictor_color = "#836AD1";
var warning_icon_class = "fas fa-exclamation-triangle";
var danger_icon_class = "fas fa-times";
var success_icon_class = "fas fa-check";
var loading_icon_class="fas fa-upload";
var file_size;
/*validation functions
 #:form sequence
 0:content_type
 1:subject
 2:content_title
 3.content_file
 
 */
//retrieve and show file name 
$('#upload_library_content_form_content_file').on('change', function () {
    //get the file name
    var fileName = $('#upload_library_content_form_content_file').val().replace(/C:\\fakepath\\/i, '');
    //replace the "Choose a file" label
    $('#upload_library_content_form_content_file_label').html(fileName);
    file_size = this.files[0].size;
});



function hide_field_error_alert() {
//    var upload_library_content_form_alert = document.getElementById("upload_library_content_form_alert");
//    upload_library_content_form_alert.style.setProperty("display", "none");
    if (document.getElementById("upload_library_content_form_alert").style.getPropertyValue("display") === "block") {
        $('#upload_library_content_form_alert').slideUp();
    }
}

function show_field_error_alert(message, alert_type = "alert-warning") {
    var upload_library_content_form_alert = document.getElementById("upload_library_content_form_alert");
    if (alert_type === "alert-warning") {
        document.getElementById("upload_library_content_form_alert_icon").className = warning_icon_class;
    } else if (alert_type === "alert-success") {
        document.getElementById("upload_library_content_form_alert_icon").className = success_icon_class;
    } else if(alert_type==="alert-danger"){
        document.getElementById("upload_library_content_form_alert_icon").className = danger_icon_class;
    }
   
    document.getElementById("upload_library_content_form_alert_msg").innerHTML = " " + message;
    upload_library_content_form_alert.className = "alert " + alert_type;
    //set alert-class if loading
    if(alert_type==="alert-loading"){
         upload_library_content_form_alert.className = "alert " +"alert-success";
    }
    // upload_library_content_form_alert.style.setProperty("display", "block");
    if (document.getElementById("upload_library_content_form_alert").style.getPropertyValue("display") === "none") {
        $('#upload_library_content_form_alert').slideDown();
        $('#upload_library_content_form_alert').css("display", "block");
}
}

function validate_content_type(should_show_error = false) {
    if (document.getElementById("upload_library_content_form_content_type").value === "") {
        return false;
    } else {
        return true;
}
}

function validate_subject(should_show_error = false) {
    if (document.getElementById("upload_library_content_form_subject").value === "") {
        return false;
    } else {
        return true;
}
}

function validate_content_title(should_show_error = false) {
    if (document.getElementById("upload_library_content_form_content_title").value === "") {
        return false;
    } else if (document.getElementById("upload_library_content_form_content_title").value.length > 50) {
        if (should_show_error) {
            show_field_error_alert("maximum 50 characters are allowed in content title");
        }
        return  false;
    } else {
        if (should_show_error) {
            hide_field_error_alert();
        }
        return true;
}
}

function validate_content_file(should_show_error = false) {
    if (document.getElementById("upload_library_content_form_content_file").value === "") {
        return false;
    } else if (file_size >= 16777216) {
        if (should_show_error) {
            show_field_error_alert("File size is greater than 16 MB");
        }
        return false;
    } else {
        return true;
}
}


//incorrect field value handlers
document.querySelector("#upload_library_content_form_content_file").addEventListener("change", () => {
    validate_content_file(true);
});

document.querySelector("#upload_library_content_form_content_title").addEventListener("keyup", () => {
    validate_content_title(true);
});


//final form validation
document.querySelector("#upload_library_content_form_submit").addEventListener("click", (event) => {
    //event.preventDefault();
    var input_field_status = [false, false, false, false];
    var upload_library_content_form_alert = document.getElementById("upload_library_content_form_alert");

    input_field_status[0] = validate_content_type();
    if (!input_field_status[0]) {
        document.getElementById("upload_library_content_form_content_type").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("upload_library_content_form_content_type").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }

    input_field_status[1] = validate_subject();
    if (!input_field_status[1]) {
        document.getElementById("upload_library_content_form_subject").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("upload_library_content_form_subject").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }

    input_field_status[2] = validate_content_title();
    if (!input_field_status[2]) {
        document.getElementById("upload_library_content_form_content_title").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("upload_library_content_form_content_title").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }
    input_field_status[3] = validate_content_file();
    if (!input_field_status[3]) {
        document.getElementById("upload_library_content_form_content_file").style.setProperty("border-left", "4px solid " + incorrect_field_indictor_color);
    } else {
        document.getElementById("upload_library_content_form_content_file").style.setProperty("border", "2px solid" + correct_field_indictor_color);
    }

var should_submit=true;
    for (var i = 0; i < 4; i++) {
        //console.log(i+" "+input_field_status[i])
        if (input_field_status[i] === false) {
            event.preventDefault();
            should_submit=false;
            show_field_error_alert("All fields are mandatory", "alert-danger");
            break;
        }
    }
    if(should_submit){
         show_field_error_alert("<div class=\"d-flex align-items-center\"><div class=\"spinner-border\" role=\"status\" aria-hidden=\"true\" style=\"margin-right:10px\"></div><strong> Uploading File...</strong></div>", "alert-loading");
    }
});