$(function(){
	/*==========================設置管理===============================*/
	//設置切換
	$("#setClassify li").eq(0).addClass("activeSet");//顯示默認選項
	$("#setClassifyContent>li").eq(0).show();
	$("#setClassify li").click(function(){
		var setIndex = $(this).index();
		$("#setClassify li").removeClass("activeSet").eq(setIndex).addClass("activeSet");
		$("#setClassifyContent>li").hide().eq(setIndex).show();
		switch(setIndex){
		case 0: base_SettingObj.initGroup(); break;
		case 1: base_SettingObj.initRegion(); break;
		case 2: base_SettingObj.initNode(); break;
		case 3: base_SettingObj.initShowSetting(); break;
		case 4: base_SettingObj.initWarmingSetting(); break;
		case 5: base_SettingObj.initNetWorkSetting(); break;
		case 6: base_SettingObj.initSysSetting(); break;
		}
	});
	base_SettingObj.initGroup();
	/*========顯示設置========*/
	onlyNumber($("#displaySetup input[type='text']")); //只能輸入數字
	/*========警報設置========*/
	onlyNumber($("#alarmSet input[type='text']")); //只能輸入數字
	$("#selectRetentionModel").change(function(){
		if($(this).get(0).selectedIndex==2){
			$(".detentionAlarmTime").show();
		}else{
			$(".detentionAlarmTime").hide();
		}
	})
	/*========網絡設置========*/
	onlyNumber($("#savePort").siblings("input[type='text']"));
	/*========系統設置========*/
	onlyNumber($("#systemSet input[type='text']")); // 只能輸入數字
	/*新增 導入導出*/
	$("#ImportExport").click(function(){
		if($(this).hasClass("whiteColor")){
			$(this).removeClass("whiteColor");
			$("#secondMenu").hide();
		}else{
			$(this).addClass("whiteColor");
			$("#secondMenu").show();
		}
	})
	$("#secondMenu").hover(function(){
		$("#ImportExport").addClass("whiteColor");
	},function(){
		$("#secondMenu").hide();
		$("#ImportExport").removeClass("whiteColor");
	})
	$("#secondMenu li").eq(1).hover(function(){
		$("#secondMenu").show();
		$("#thirdMenu").show();
	},function(){
		$("#thirdMenu").hide();
	})
	$("#thirdMenu").hover(function(){
		$("#secondMenu").show();
		$("#thirdMenu").show();
		$("#ImportExport").addClass("whiteColor");
	},function(){
		$("#secondMenu").hide();
		$("#thirdMenu").hide();
		$("#ImportExport").removeClass("whiteColor");
	})
	//批量導入
	$("#returnBulkImport").click(function(){
		return $("#bulkImport").click();
	})
});
