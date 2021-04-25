/**
 * --------------------------------------------------------------------
 * Cloudwei validate plugin
 * Author: Steven Kwok
 * Copyright (c) 2012 Cloudwei
 * version 1.0
 * --------------------------------------------------------------------
 */
(function ($) {

	$.fn.validate = function( method ) { 
		var showTime=500,
			clearTime=1500,
			tipsClass='ui-state-highlight',
			errorClass='ui-state-error',
			numericRegexp=/^([0-9])+$/,
			
		updateTips = function(tips,text){
			if(tips){
				tips.text(text).addClass(tipsClass);
				setTimeout(function() {
					tips.removeClass(tipsClass,clearTime);
				}, showTime);
			}
		},
		methods = {
			
				tip: function(){		
					var tips,text;
					if(arguments){
						tips = arguments[0];
						text = arguments[1];						
					}
					updateTips(tips,text);
					return false;
				},
			
				length: function(){
					var min,tips,text,$this = this;
					if(arguments){
						min = arguments[0];
						tips = arguments[1];
						text = arguments[2];						
					}					
					if ($this.val().length < min) {
						$this.addClass(errorClass);
						setTimeout(function() {
							$this.removeClass(errorClass,clearTime);
						}, showTime);
						updateTips(tips,text);
						return false;		
					}else{
						return true;
					}					
				},
				
				numeric: function(){
					var tips,text,$this = this;
					if(arguments){
						tips = arguments[0];
						text = arguments[1];
					}
					if (!(numericRegexp.test($this.val()))) {
						$this.addClass(errorClass);
						setTimeout(function() {
							$this.removeClass(errorClass,clearTime);
						}, showTime);
						updateTips(tips,text);
						return false;
					} else {
						return true;
					}
				},
				
				destroy: function(){				
					return this.each(function() {
						var $this = $(this);
						$this.removeData('cwValidateSettings');
					});
				}
		};
		
		if( methods[method] ) {
			
			return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ) );	
		} else {			
			
			$.error('Method '+ method+' does not exist in the cwValidate Plugin');
		}
	};
		
})(jQuery);