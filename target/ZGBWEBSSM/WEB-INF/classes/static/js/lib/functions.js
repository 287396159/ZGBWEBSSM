// 全选事件封装
function isAllChecked($allCheckbox,$eachCheckbox){
	var allCheckBoxEvent = function(){
		if($(this).is(":checked")){
			$eachCheckbox.prop("checked",true);
		}else{
			$eachCheckbox.prop("checked",false);
		}
		$eachCheckbox.change();
	}
	$allCheckbox.change(allCheckBoxEvent);
	var eachCheckboxEvent = function(){//绑定change事件
		var countTAG=0;
		for(var b=0;b<$eachCheckbox.length;b++){
			if($eachCheckbox.eq(b).is(":checked")){
				countTAG++;
			}
		}
		if(countTAG==$eachCheckbox.length){
			$allCheckbox.prop("checked",true);
		}else{
			$allCheckbox.prop("checked",false);
		}
	}
	$eachCheckbox.on("change", eachCheckboxEvent);
}
//封装拖拽事件
function drag($needDrag, $dragScope, callBack){
	var isdbClick=true;//用来判断当前行为是拖拽还是双击
	var mousedownX,mousedownY; var mouseupX,mouseupY;//比较鼠标按下松开的坐标，防止Chrome下的mousemove事件bug
	$needDrag.off("mousedown").on("mousedown",function(e){
		$needDrag.removeAttr("drag");
		isdbClick = true;
		var px = e.pageX - $(this).offset().left;//鼠标在当前节点按下的x坐标
		var py = e.pageY - $(this).offset().top;//鼠标在当前节点按下的y坐标
		mousedownX = parseInt($(this).position().left);
		mousedownY = parseInt($(this).position().top);
		var This = $(this);
		$dragScope.off("mousemove").mousemove(function(e){
			isdbClick = false;
			$needDrag.attr("drag", true);
			var x = e.pageX - px - $dragScope.offset().left;
			var y = e.pageY - py - $dragScope.offset().top;
			if(x<0) {
				x = 0;
			}else if(x>($dragScope[0].offsetWidth - This.find("span")[0].offsetWidth)){
				x = $dragScope[0].offsetWidth-This.find("span")[0].offsetWidth;
			}
			if(y<0){
				y = 0;
			}else if(y>($dragScope[0].offsetHeight-This.find("span")[0].offsetHeight)){
				y = $dragScope[0].offsetHeight-This.find("span")[0].offsetHeight;
			}
			This.css({'left':x,'top':y});
		});
	});
	$(window).off("mouseup").on("mouseup",function(event){
		$dragScope.off('mousemove');
	})
	$needDrag.off("mouseup").on("mouseup",function(event){
		if(callBack && $needDrag.attr("drag")) {
			callBack(getNodeObj($needDrag));
		}
	});
	function getNodeObj($needDrag){
		// 传节点值
		var nodeId = $needDrag.attr("id").replace("PNode", "");
		var nodeName = $needDrag.attr("pname");
		var nodeType = $needDrag.attr("ptype");
		var nodeX = parseInt($needDrag.css("left")) + 19;
		var nodeY = parseInt($needDrag.css("top")) + 8;
		var regionId = $needDrag.attr("regionId");
		var createId = $needDrag.attr("createId");
		var createTime = $needDrag.attr("createTime");
		return {
			"id" : nodeId,"name" : nodeName,"type" : nodeType,"mapX" : nodeX,"mapY" : nodeY,
			"regionId" : regionId,"createId" : createId,"createTime" : createTime
		};
	}
};
//限制input输入框只能输入数字
function onlyNumber(inputObj){
	inputObj.keyup(function(){
		$(this).val( $(this).val().replace(/[^0-9]/g,'') );
	});
};
//验证IP,返回true或false
function isValidIP(ip){
	var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
    return reg.test(ip);
};
//验证端口，返回true或false
function isValidPort(port){
	var reg = /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/;
	return reg.test(port);
};
/*===========自定义滚轮事件=========*/
/*var Scrolling=false;
function _$(o){
	return document.getElementById(o);
};
function ScroMove(){
	Scrolling=true;
};
document.onmousemove=function(e){
	if(Scrolling==false){
		return;
	}
	ScroNow(e);
};
document.onmouseup=function(e){
	Scrolling=false;
};
function ScroNow(event){
	var event=event?event:(window.event?window.event:null);
	var Y=event.clientY-_$("checkboxTreeDiv").getBoundingClientRect().top-_$("bar").clientHeight/2;
	var H=_$("scroll").clientHeight-_$("bar").clientHeight;
	var SH=Y/H*(_$("checkboxTree").scrollHeight-_$("checkboxTree").clientHeight);
	if(Y<0){ Y=0; }; if(Y>H){ Y=H; };
	_$("bar").style.top=Y+"px";
	_$("checkboxTree").scrollTop=SH;
};
//注册事件
if(document.addEventListener){   
   document.addEventListener('DOMMouseScroll',ScrollWheel,false); //FF  
};
document.onmousewheel=ScrollWheel;//IE/Opera/Chrome  
function ScrollWheel(e){
	if(e.target.clientWidth>250){ return; }
	e = e || window.event;
	var Y=_$("checkboxTree").scrollTop;
	var H=_$("checkboxTree").scrollHeight-_$("checkboxTree").clientHeight;
	if(e.wheelDelta){
		if(e.wheelDelta>=120){ Y=Y-10 }else{ Y=Y+10 }; //FF
	}else if(e.detail){
		if(e.detail<=-3){ Y=Y-10 }else{ Y=Y+10 }; //IE/Opera/Chrome
	}
	if(Y<0)Y=0; if(Y>H)Y=H;
	_$("checkboxTree").scrollTop=Y;
	var SH=Y/H*_$("scroll").clientHeight-_$("bar").clientHeight;
	if(SH<0)SH=0;
	_$("bar").style.top=SH+"px";
	return false;
};*/
/*===========自定义滚轮事件end=========*/

//时间戳转换日期  格式化日期格式默认为：1970-00-00 00:00:00 可自行修改
Date.prototype.format = function(format)
{
	 var o = {
	 "M+" : this.getMonth()+1, //month
	 "d+" : this.getDate(),    //day
	 "h+" : this.getHours(),   //hour
	 "m+" : this.getMinutes(), //minute
	 "s+" : this.getSeconds(), //second
	 "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
	 "S" : this.getMilliseconds() //millisecond
	 }
	 if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	 (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	 for(var k in o)if(new RegExp("("+ k +")").test(format))
	 format = format.replace(RegExp.$1,
	 RegExp.$1.length==1 ? o[k] :
	 ("00"+ o[k]).substr((""+ o[k]).length));
	 return format;
}
function add0(m){return m<10?'0'+m:m};
function format(shijianchuo){ //时间戳应为整数，否则要parseInt转换
	var time = new Date(shijianchuo);
	var y = time.getFullYear();
	var m = time.getMonth()+1;
	var d = time.getDate();
	var h = time.getHours();
	var mm = time.getMinutes();
	var s = time.getSeconds();
	return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
};
//封装移动功能
function move_up_down($up,$down,$moveObj){
	//$up：向上移动； $down：向下移动； $moveObj：移动对象
	$up.click(function(){ //向上移动
		$moveObj.scrollTop($moveObj.scrollTop()+270);
		if($moveObj.scrollTop()>0){
			$(this).css("border-bottom","1px solid darkgray");
		}
	});
	$down.click(function(){ //向下移动
		$moveObj.scrollTop($moveObj.scrollTop()-270);
		if($moveObj.scrollTop()==0){
			$up.css("border","none");
		}
	});
};
//高度自适应
$(window).ready(function(){
	function autoHeight(){
		$(".section>li").height($(window).height());
		$(".classifyDetail").height($(".section>li").height()-40);
		$(".classifyContent").height($(".section>li").height()-40);
		
		$("#setClassifyContent").height($(window).height() - 2);
		$("#eNodeBMapDiv").height($(window).height() - 118);
		
		$(".classifyContent>li").height($(window).height() - 5);
		
		$("#alarmContent").height($(window).height());
		
		$("#baseStationContent").height($(window).height());
		
		$("#historyContent").height($(window).height() + 5);
		$("#accessContent").height($(window).height());
		
		$("#reportContent").height($(window).height());
		
		$("#contractorDataManage").height($(window).height())
		
		
		$(".formContent").height($(".classifyContent>li").height()-40);
		$(".eNodeBFormContent").height($(".classifyContent>li").height()-75);
		$(".departmentFormContent").height($(".classifyContent>li").height()-75);
		$(".fenceFormContent").height($(".classifyContent>li").height()-70);
		$("div.historySheet").height($("#historyContent").height() - 50);
		$(".TAGMgr").height($(".section>li").height()-40);
		$(".monitorFormContent").height($(".classifyContent>li").height()-35);
		$(".areaClassify").css("height","auto");
		/*新增*/
		$(".paramSetLeft,.paramSetRight").height($(".fenceFormContent").height()-43);
		$(".selectedTAG").height($(".fenceFormContent").height()-380);
	}
	autoHeight();
	$(window).resize(function(){
		autoHeight();
	});
})
