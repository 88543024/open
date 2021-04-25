//选择项高亮显示
function selectTDColor(choiceName){
	 $("input[name='"+choiceName+"']").parent().removeClass();
	 $("input[name='"+choiceName+"']").parent().siblings().removeClass();
	 $("input[name='"+choiceName+"']:checked").parent().addClass("choiceTd");
	 $("input[name='"+choiceName+"']:checked").parent().siblings().addClass("choiceTd");
}
//通过状态登录用户判断权限(工单管理模块)
function checkPower(){
   var userPer = $("input[name='orderName']:checked").attr("userPer").trim(); //是否为超级管理员
   var operatorPer = $("input[name='orderName']:checked").attr("operatorPer").trim(); //是否为指定的操作者
   var status = $("input[name='orderName']:checked").attr("objectSTATUS").trim();
   //判断派单操作可否执行
   if(status == '1' || status == '' || status == null){
	   //是管理员可派单
     if(userPer == '1'){
       $("#orderASSIGN").attr("onclick","assignMesBox()")
   	   $("#orderASSIGN").removeClass("unbtn");
   	   $("#orderASSIGN").addClass("btn-primary");
     }
   }else{
	   //不可操作
	   $("#orderASSIGN").removeAttr("onclick")
	   $("#orderASSIGN").removeClass("btn-primary");
	   $("#orderASSIGN").addClass("unbtn");
   }

   //判断执行操作可否执行
   if(status != '2' && status != '5' && status != '4'){
	  if(userPer == '1'){
		   //管理员可以直接操作
		   $("#orderOperation").attr("onclick","operationMesBox()")
	   	   $("#orderOperation").removeClass("unbtn");
	   	   $("#orderOperation").addClass("btn-primary"); 
	  }else if(userPer == '0' && status == '3' && operatorPer == '1'){
	     	 //当前登录用户是指定的操作用户，可以操作
    	   $("#orderOperation").attr("onclick","operationMesBox()")
	   	   $("#orderOperation").removeClass("unbtn");
	   	   $("#orderOperation").addClass("btn-primary");
	  }else{
		//不可操作
	 	   $("#orderOperation").removeAttr("onclick")
		   $("#orderOperation").removeClass("btn-primary");
		   $("#orderOperation").addClass("unbtn");
	  }  //不是管理员
   }else{
	   //不可操作
 	   $("#orderOperation").removeAttr("onclick")
	   $("#orderOperation").removeClass("btn-primary");
	   $("#orderOperation").addClass("unbtn")
   }
   
 //判断备注操作可否执行
   if(status == '5'){
	   //不是管理员
     if(userPer == '0'){
    	 //当前登录用户是指派操作用户，可以操作
    	if(operatorPer == '1'){
		    //判断操作
		   $("#orderRemark").attr("onclick","remarkMesBox()")
	   	   $("#orderRemark").removeClass("unbtn");
	   	   $("#orderRemark").addClass("btn-primary");	 
	    }else{
	    	//不可操作
	 	   $("#orderRemark").removeAttr("onclick")
		   $("#orderRemark").removeClass("btn-primary");
		   $("#orderRemark").addClass("unbtn");
	    }
     }else if(userPer == '1'){
	   //直接操作
		   $("#orderRemark").attr("onclick","remarkMesBox()")
	   	   $("#orderRemark").removeClass("unbtn");
	   	   $("#orderRemark").addClass("btn-primary");
     } 
   }
}

//通过状态判断权限(镜像管理模块)
function checkImageStatus(choiceImageName,registe){
	  var registe = $("input[name='"+choiceImageName+"']:checked").attr(registe).trim();//是否已经注册
	  
	  //注册判断
	  if(registe == '0'){
		   //注册可以操作
		   $("#registrationImage").attr("onclick","registration()")
	   	   $("#registrationImage").removeClass("unbtn");
	   	   $("#registrationImage").addClass("btn-primary");
	  }else if(registe == '1'){
	       //注册不可操作
	       $("#registrationImage").removeAttr("onclick")
		   $("#registrationImage").removeClass("btn-primary");
		   $("#registrationImage").addClass("unbtn")
	  }else{
		   //注册不可操作
	       $("#registrationImage").removeAttr("onclick")
		   $("#registrationImage").removeClass("btn-primary");
		   $("#registrationImage").addClass("unbtn") 
	  }
	  
	  //取消判断
	  if(registe == '0'){
	    	//取消不可操作
		   $("#cancelImage").removeAttr("onclick")
		   $("#cancelImage").removeClass("btn-primary");
		   $("#cancelImage").addClass("unbtn")
	  }else if(registe == '1'){
	       //取消可操作
		   $("#cancelImage").attr("onclick","imageCancel()")
	   	   $("#cancelImage").removeClass("unbtn");
	   	   $("#cancelImage").addClass("btn-primary");
	  }else{
	    	//取消不可操作
		   $("#cancelImage").removeAttr("onclick")
		   $("#cancelImage").removeClass("btn-primary");
		   $("#cancelImage").addClass("unbtn")
	  }
}
