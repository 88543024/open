function loadLocalURL(container, url) {
	$('#' + container).load(url);
}

function parseCapacity(number) {
	var num = number / 1024 / 1024;
	var num_result = Math.round(num * 100) / 100;
	return num_result;
}

function pushToAgent(url){
	$('#pushbutton').button('loading');  // show the loading message.
    $.ajax({
        url: url,
        type: "POST",
        cache: false,
        success : function(text){
        	$('#pushbutton').button('reset'); // hide the loading message
        }
    });
}

function portType(type){
	var x="";
	switch (parseInt(type)) {
	case 0:
		x = "Unknown";
		break;
	case 1:
		x = "Other";
		break;
	case 10:
		x = "N";
		break;
	case 11:
		x = "NL";
		break;
	case 12:
		x = "F/NL";
		break;
	case 13:
		x = "Nx";
		break;
	case 14:
		x = "E";
		break;
	case 15:
		x = "F";
		break;
	case 16:
		x = "FL";
		break;
	case 17:
		x = "B";
		break;
	case 18:
		x = "G";
		break;
	case 32768:
		x = "LB";
		break;
	case 32769:
		x = "PL";
		break;
	}
	
	return x;
}