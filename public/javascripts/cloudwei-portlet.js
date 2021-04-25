/**
 * --------------------------------------------------------------------
 * Cloudwei portlet plugin
 * Author: Steven Kwok
 * Copyright (c) 2012 Cloudwei
 * version 1.0
 * --------------------------------------------------------------------
 */
 (function ($) {

	$.fn.portlet = function( method ) {
		
		var defaults = {
			headerTemplate:'<div class=\'portlet-header ui-widget-header ui-corner-all ui-state-default\'></div>',
			contentTemplate:'<div class=\'portlet-content\'></div>',
			closeIconTemplate:'<span class=\'ui-icon ui-icon-closethick\'></span>',
			setupIconTemplate:'<span class=\'ui-icon ui-icon-wrench\'></span>',
			titleTemplate:'<span type=\'title\'></span>',
			needRender:true,
			type:null,
			uid:null,
			url:null,
			title:null,
			width:200,
			height:100,
			setupDialogTitle:'Setup Portlet',
			okBtnTitle:'Ok',
			cancelBtnTitle:'Cancel',
			validateTitle:'Please input the title',
			validateWidth:'Width must be a numeric',
			validateHeight:'Height must be a numeric',
			validateMinWidth:'Width must greater than ',
			validateMinHeight:'Height must greater than '
		},
		render = function(){
			var $this = $(this),
				settings = $this.data('portletSettings');
			var header = $(settings.headerTemplate),
				content = $(settings.contentTemplate),
				headerTitle = $(settings.titleTemplate),
				closeIcon = $(settings.closeIconTemplate),
				setupIcon = $(settings.setupIconTemplate),
				width = settings.width,
				height = settings.height,
				type = settings.type,
				uid = settings.uid,
				url = settings.url,
				title = settings.title,
				setupDialog = settings.setupDialog,
				needRender = settings.needRender;
			$this.css('width',width);
			$this.css('height',height);	
			if(!needRender){
				closeIcon = $this.find(".ui-icon-closethick");
				setupIcon = $this.find(".ui-icon-wrench");
				content = $this.find(".portlet-content");
			}		
			closeIcon.css("cursor","hand").click(function() {
				$this.portlet("destroy");
			});
			setupIcon.css("cursor","hand").click(function() {
				setupDialog.empty();
				setupDialog.load('/DashBoard/configPortlet?portletId='+type+'&uid='+uid,function(data){
				setupDialog.dialog("open");
				});
			});
			if(needRender){
				headerTitle.text(title);			
				header.append(setupIcon)
					.append(closeIcon)
					.append(headerTitle);					
				$this.append(header)
					.append(content);
			}
			content.load(url+'?uid='+uid,function(data){});
		},
		methods = {
				init: function( options ){
					var opts = $.extend( {}, defaults, options );
					
					return this.each(function() {
						var $this = $(this),
								settings = {};
						settings.originalContent = $this.html();
						settings.headerTemplate = opts.headerTemplate;
						settings.contentTemplate = opts.contentTemplate;
						settings.closeIconTemplate = opts.closeIconTemplate;
						settings.setupIconTemplate = opts.setupIconTemplate;
						settings.titleTemplate = opts.titleTemplate;
						settings.type = opts.type;
						settings.uid = opts.uid;
						settings.url = opts.url;
						settings.title = opts.title;
						settings.width = opts.width;
						settings.height = opts.height;
						settings.needRender = opts.needRender;
						var dialogTitle = opts.setupDialogTitle,
							ok = opts.okBtnTitle+"",
							cancel = opts.cancelBtnTitle+"",
							vTitle = opts.validateTitle,
							vWidth = opts.validateWidth,
							vHeight = opts.validateHeight,
							vMinWidth = opts.validateMinWidth,
							vMinHeight = opts.validateMinHeight;
						var setupDialog = $('<div name=\'setupDialog\'></div>');
						setupDialog.dialog({
							autoOpen: false,
							resizable:false,
							modal: true,
							title:dialogTitle,
							buttons: [
								{
									text:ok,
									click:function(){								
										var $setupDialog = $(this),
											url = settings.url,
											uid = settings.uid
											portlet = $this;
										var attrs = $setupDialog.find("input[name='layoutattr']"),
											bValid = true,
											p_title = '',
											p_width = 0,
											p_height = 0,
											tmp = '[',
											tips = $setupDialog.find(".portletTips");
										attrs.each(function(index,input){
											if(index>0)
												tmp+=',';										
											tmp += "{attrName:'"+input.id+"',attrValue:'"+input.value+"'}";									
											if(input.id=='title'){										
												bValid = bValid && $(input).validate('length',1,tips,vTitle);	
												p_title = input.value;
											}else if(input.id=='width'){
												bValid = bValid && $(input).validate('numeric',tips,vWidth);
												p_width = input.value;
											}else if(input.id=='height'){
												bValid = bValid && $(input).validate('numeric',tips,vHeight);		
												p_height = input.value;
											}									
										});
										tmp+=']';
										if(!bValid)
											return;
										if(p_width < p_title.length*10+20){     // dialog width must large than title width
											var m = vMinWidth+(p_title.length*10+20);
											$setupDialog.validate('tip',tips,m);
											bValid = false;
										}else if(p_height < 110){					  // dialog height must large than 110
											var m = vMinHeight+110;
											$setupDialog.validate('tip',tips,m);
											bValid = false;
										}else{
											$this.find("span[type='title']").text(p_title);
											$this.css("width",p_width);
											$this.css("height",p_height);
										}
										if ( bValid ) {
											$setupDialog.dialog( "close" );
											$.post('/DashBoard/updatePortletCache?uid='+uid+'&data='+encodeURIComponent(tmp),function(data){
												var portletContent = portlet.find('.portlet-content');
												portletContent.load(url+"?uid="+uid,function(data){});
											});														
										}
									}
								},
								{
									text:cancel,
									click:function(){
										$( this ).dialog( "close" );
									}
								}
							]
						});
						settings.setupDialog = setupDialog;					
						$this.data( 'portletSettings', settings );
						render.apply($this);
					});
				},
				getSettings: function( name ){				
					var settings,
						$this = $(this[0]);					
						settings = $this.data( 'portletSettings' );
					if( !settings ){
							return undefined;
						}
					if( name ) {
						if( settings[name] ) {
							return settings[name];
						}
						return undefined;
					}
					return settings;
				},
				destroy: function(){
				
					return this.each(function() {
						var $this = $(this),
							settings;
						
						settings = $this.data( 'portletSettings' );
						
						if( !settings ){
							return true;
						}
						$this.unbind('.cwPortlet');
						settings.setupDialog.dialog("destroy").remove();
						$this.removeData('portletSettings');
						$this.remove();
					});
				}
		};
			
			
		if( methods[method] ) {
			
			return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ) );	
		} else if( $.type( method ) === 'object' || !method ) {
			
			return methods.init.apply( this, arguments );
		} else {			
			
			$.error('Method '+ method+' does not exist in the cwPortlet Plugin');
		}
	};
		
})(jQuery);