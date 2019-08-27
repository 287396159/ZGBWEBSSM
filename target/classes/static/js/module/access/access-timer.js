var accessTimer = function(time_ms) {
	var drawingRegions = function(regions) {
		var total = 0;
		for (var i = 0; i < regions.length; i++) {
			$("#regionNumber li[regionid='" + regions[i]["id"] + "']").find("p").text(regions[i]["number"]);
			total += regions[i]["number"];
		}
		$("#regionNumber li[regionTotal]").find("p").text(total);
	}
	var obtainAllRegions = function(groupId, callBack) {
		$.ajax({type: "GET", url:"region/number/" + groupId, dataType:"JSON", 
			success: function(result) {
				if(result.code == 600) {
					callBack(result.data);
				}
			}, error: function(result) {
				console.log("獲取組別【groupId: " + groupId + "】的所有區域出現異常...");
			}})
	}
	return function() {
		setInterval(function() {
			var groupId = $("#groupClassify li.active").attr("groupId");
			obtainAllRegions(groupId ,drawingRegions);
		}, time_ms)
	}
}