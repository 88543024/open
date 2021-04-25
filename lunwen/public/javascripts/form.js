var hasSaved = false;
$("input[name='_save'],input[name='_saveAndContinue'],input[name='_saveAndAddAnother'],a[name='_saveAndContinue']").click(function(){
	hasSaved = true;
});

window.onbeforeunload = function(){
    if(is_form_changed() && !hasSaved)   
    {  
 		return "您的修改内容还没有保存，您确定离开吗？";
    }
}

function is_form_changed(){
	var t_save0 = $("input[name='_save']"); 
	var t_save1 = $("input[name='_saveAndContinue']"); 
	var t_save2 = $("input[name='_saveAndAddAnother']"); 
	var t_save3 = $("a[name='_saveAndContinue']"); 
	if(t_save0.length > 0 || t_save1.length > 0 || t_save2.length >0 || t_save3.length >0){
		var is_changed = false;
		$(":input").each(function(){
			var _v = $(this).attr('_value');
			if(typeof(_v) == 'undefined')
				_v == '';
			if(_v != $(this).val())   
                is_changed = true;
		});
		return is_changed;
	}
	return false;
}