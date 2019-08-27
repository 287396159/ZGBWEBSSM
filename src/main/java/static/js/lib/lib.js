// 登陸模塊
var loginObj = function($nameInput, $pswInput){
	/* 用戶名驗證 */
	function usernameCheck() {
		var username = $("input[name='username']").val();
		/* 是否以數字、字符串、漢字開頭 */
		var reg = /^[0-9a-zA-Z\u4e00-\u9fa5]/;
		if (!reg.test(username)) {
			showTip("nameTip", "必須以數字、英文字符或漢字開頭");
			return;
		}
		reg = /[0-9a-zA-Z\u4e00-\u9fa5_]+/;
		if (!reg.test(username)) {
			showTip("nameTip", "只能包含數字、英文、漢字或_");
			return;
		}
		if ((username.length > 16) || (username.length < 2)) {
			showTip("nameTip", "用戶名長度必須為2-16");
			return;
		}
		$("#nameTip").css("visibility", "hidden");
	}
	/*密碼驗證*/
	function pswCheck() {
		var psw = $("input[name='password']").val();
		var reg = /[0-9A-Za-z]{6,10}/;
		if (!reg.test(psw)) {
			showTip("pswTip", "密碼必須是數字或英文字符");
			return;
		}
		if ((psw.length > 10) || (psw.length < 6)) {
			showTip("pswTip", "密碼必須是6-10位的字符");
			return;
		}
		$("#pswTip").text("");
		$("#pswTip").css("visibility", "hidden");
	}
	function showTip(id, msg) {
		$("#" + id).text(msg);
		$("#" + id).css("visibility", "visible");
	}
	return function(){
		$nameInput.on("input propertychange",usernameCheck);
		$pswInput.on("input propertychange",pswCheck);
	}
}