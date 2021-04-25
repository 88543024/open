/*
 * Module ID & link definitions
 * Format:
 * moduleId:{url: "url_of_this_module (relative to index)",
 *  		 t:   "title_for_this_module",
 */ 
var _modules={};

/*
 * Layout definitions for each tab, i.e., which modules go to which columns under which tab
 * Format:
 * 		tab_id: [
 * 			...
 * 			"{module_id}:{column_id, c1, c2, ...}:[optional color class]:[optional template name]",
 * 			...
 * 		]
 */
var _moduleLayout={
	wt1:["stg_020001:c1:red", "fc_020001:c2", "fc_020004:c3", "fc_020009:c4"],
	
	wt2:["fc_020001:c1", "fc_020002:c2", "fc_020003:c3", "fc_020004:c4"]
};

/* 
 * Column layout definitions, i.e., how the columns (containers) are placed under each tab
 * Pure CSS properties can be set upon each column, e.g., width, float, etc. You can refer
 * to jQuery.fn.css() for more details.
 * 
 * The "bg" property is used to set the background of all columns, which actually affects <body>
 * 
 * A _default value set is provided, to save your efforts of setting each tab manually
 */
var _columnLayout = {
	_default: { bg:'lighter',
				c5:'span12 last',
				c1:'span4',
				c2:'span4',
				c3:'span4 last',
				c4:'span12'
	},
	wt1:{ bg:'lighter',
		c1:'span4',
		c2:'span4',
		c3:'span4 last',
		c4:'span12'
	},
	wt2:{ bg:'darker',
		 c1:'span3',
		 c2:'span3',
		 c3:'span3',
		 c4:'span3'
	},
	t3:{ bg:'darker',
		 c1:'span12 last',
		 c2:'span4',
		 c3:'span4',
		 c4:'span4'
	},
	t4:{ bg:'darker',
		 c1:'span4',
		 c2:'span4',
		 c3:'span4 last',
		 c4:'span12'
	},
	t5:{ c1:'span12',
		 c2:'span6',
		 c3:'span6 last'
	}
};