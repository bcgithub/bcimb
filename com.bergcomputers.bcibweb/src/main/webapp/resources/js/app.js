/*
var onErrors = function onErrors(data) {
	$("#ajaxloader").hide();
	
    $("#ajax-error-text").html(' <b>Status: </b>' + data.status + '<br /> <b>Code: </b>' + data.responseCode + '<br /><br />' + data.description);    
    $('#ajax-error-div').dialog({
        modal: true,
        position: 'center',
        buttons: {
        	"Ok": function() {
                $( this ).dialog("close");
            }
        },
        open: function() {
            $(".background-overlay").show();
            $("#error-img").show();
        },
        close: function() {
            $(".background-overlay").hide("slow");
            $("#error-img").hide();
        }
    }); 
}; */
function displayError(title, text) {
	$('.ui-dialog-title').text(title);
	$("#error-text").html(text);
	$('.error-div').dialog('open');
}

var ajaxError = function ajaxError(data) {
	$("#ajaxloader").hide();	
	displayError('Ajax call error!', '<b>Status: </b>' + data.status + '<br /> <b>Code: </b>' + data.responseCode + '<br /><br />' + data.description);    
	$("#error-img").attr("src", "./resources/images/error.png");
};
jsf.ajax.addOnError(ajaxError);



function selectMenuItem(menuItemId) {
	$('.dropdown-menu a').removeClass('current-menu-item');
	$('a#'+menuItemId.slice(1,-6)).addClass('current-menu-item');
}

$(document).ready(function() {
	//$('.errors').change(function() {
	//	  alert('Handler for .change() called.');
	//});
	//displayError("MyTitle", "MyText");
});

function detectEnter(e)
{
var keynum; // set the variable that will hold the number of the key that has been pressed.
    
    //now, set keynum = the keystroke that we determined just happened...
    if(window.event) // (IE)
    {keynum = e.keyCode;}

    else if(e.which) // (other browsers)
    {keynum = e.which;}

    else{ // something funky is happening and no keycode can be determined...
        alert('nothing found');
        keynum = 0;
    }

    // now that keynum is set, interpret keynum
    if(keynum == 13){ // this is Enter (keyascii code 13)
    	$("#filterForm:fltr").click();
    	$("#usersForm:fltr").click();
    	$("#permissionsForm:fltr").click();
    	$("#resourcesForm:fltr").click();
    	$("#groupsForm:fltr").click();
        return false;
    }
    else{ // this is something other than enter
        return true;
    }
};

		
	

