var nodeInit = function(){
	layui.use(['form','element','table'], function() {
		// 初始化組別訊息
		var initGroups = function(layer) {
			// 刷新組別選擇框
			var refreshGroupsSelect = function(groups) {
				$("#nodeGroupSelect").empty();
				var regArr = [];
				for (var i = 0; i < groups.length; i++) {
					regArr.push("<option value='" + groups[i]["id"] + "'>"
							+ groups[i]["name"] + "</option>");
				}
				$("#nodeGroupSelect").append(regArr.join(""));
			}
			$.ajax({
				url : "group/all/",
				dataType : "json",
				type : "GET",
				success : function(result) {
					if(result.code == 600) {//組別獲取成功
						// 將當前的組別訊息添加到組別的選擇框中
						refreshGroupsSelect(result.data);
						// 重新生成layUi表單
						layui.form.render();
						// 獲取當前選擇的組別ID
						var groupSelectVal = $(
								"#nodeGroupSelect+div.layui-form-select").find(
								"input[type='text']").val();
						// 選擇當前選擇的組別ID
						var groupSelectId = $(
								"#nodeGroupSelect option:contains('" + 
								groupSelectVal + "')").attr("value");
						// 更新組別對應的區域選擇框訊息
						initRegions(groupSelectId, layer);
					} else {
						layer.msg(result.msg, {
							type : 5,
							time : 2000
						});
					}
				},
				error : function(result) {
					layer.msg("獲取組別訊息時出現異常...", {
						type : 1,
						time : 2000
					});
				}
			})
		}
		// 根據組別的 ID重新加載組別對應的區域訊息
		var initRegions = function(groupId, layer) {
			var refreshRegionsSelect = function(regions) {
				$("#nodeRegionSelect").empty();
				var regArr = [];
				for (var i = 0; i < regions.length; i++) {
					regArr.push("<option image='" + regions[i]["image"]
							+ "' value='" + regions[i]["id"] + "'>"
							+ regions[i]["name"] + "</option>");
				}
				$("#nodeRegionSelect").append(regArr.join(""));
			}
			$.ajax({
				url : "region/groupId/" + groupId,
				type : "GET",
				dataType : "json",
				success : function(result) {
					if(result.code == 600){
						// 將區域訊息添加到下拉區域列表框中
						refreshRegionsSelect(result.data);
						// 重新加載select
						layui.form.render();
						// 根據當前選擇的區域顯示地圖訊息
						var regionSelectVal = $(
						"#nodeRegionSelect+div.layui-form-select").find(
						"input[type='text']").val();
						// 獲取選擇的區域ID和地圖路徑
						var regionSelectId = $(
								"#nodeRegionSelect option:contains('" + 
								regionSelectVal + "')").attr("value");
						var regionSelectImage =  $(
								"#nodeRegionSelect option:contains('" + 
								regionSelectVal + "')").attr("image");
						if(regionSelectImage) {// 改變區域地圖
							$("#eNodeBMap > img").attr("src","image/" + regionSelectImage);
						} else {// 設置區域地圖為空地圖
							$("#eNodeBMap > img").attr("src","image/5c7fe97c-255b-4baf-b804-083e4fc8f9d3.png");
						}
					}else{
						layer.alert(result.msg,{type: 5});
					}
				},
				error : function(result) {
					layer.msg("獲取所有的區域訊息出現異常...",{type: 5});
				}
			})
		}
		var element = layui.element;
		var form = layui.form;
		var table = layui.table;
		var $selectDom = $("#nodeSelectRegionForm select");
		initGroups(layer, $selectDom);
		//第壹個實例
		table.render({
		    elem: '#demo',
		    height: "full-140",
		    url: 'group/page/',
		    page: true,
		    cols: [[ //表頭
		       {field: 'id', title: '設備ID', sort: true},
		       {field: 'name', title: '名稱', sort: true},
		       {field: 'type', title: '類型', sort: true},
		       {fixed: 'right', title:'操作', toolbar: '#eNodeBTableTool', align:"center"}
		    ]]
		 });
		element.on("tab(nodeNav)",function(data){
			var index = data.index;
			if(index == 0){// 顯示地圖
				$("#eNodeBMapDiv").show();
				$("#eNodeBTableDiv").hide();
			}else if(index == 1){// 顯示列表
				$("#eNodeBMapDiv").hide();
				$("#eNodeBTableDiv").show();
			}
		})
		table.on("tool(eNodeBTable)",function(obj){
			var data = obj.data;
			if (obj.event === 'del') {
				layer.confirm('真的刪除行麽', function(index) {
					obj.del();
					layer.close(index);
				});
			} else if (obj.event === 'edit') {
				layer.open({type: 1,
					title:"編輯",
					content: $("#nodeEditForm"),
					area: ["400px","300px"]})
			}
		})
		form.on('select(nodeGroupSelectFilter)',function(elem){
			// 獲取當前選擇的組別ID
			var groupSelectVal = $(
					"#nodeGroupSelect+div.layui-form-select").find(
					"input[type='text']").val();
			// 選擇當前選擇的組別ID
			var groupSelectId = $(
					"#nodeGroupSelect option:contains('" + 
					groupSelectVal + "')").attr("value");
			// 更新組別對應的區域選擇框訊息
			initRegions(groupSelectId, layer);
		})
		form.on('select(nodeRegionSelectFilter)', function(elem){
			var regionId = elem.value;
			// 根據區域ID切換不同的區域
			var image = $selectDom.find("option[value='" + regionId + "']").attr("image");
			// 修改地圖
			$("#eNodeBMap > img").attr("src","image/" + image);
		});
		form.on('submit(nodeEditFilter)', function(data){
		    layer.msg(JSON.stringify(data.field));
		    return false;
		});
		form.on('submit(nodeDeleteFilter)', function(data){
			console.log(data);
		})
	});
}