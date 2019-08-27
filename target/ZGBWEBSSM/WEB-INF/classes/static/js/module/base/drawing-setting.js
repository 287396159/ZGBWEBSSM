$.namespace("devices.node.drawing");
devices.node.drawing = function() {
		// 渲染設置自身Node設置
	var drawingSettingParam = {
		node: {
			serverIp: function(id, result) {// 設置ServerIp
				if (result.code == 600) {
					$("#serverIp i").text("設置成功");
				} else {
					$("#serverIp i").text(result.msg);
				}
			},
			serverPort : function(id, result) {// 渲染SeverPort
				if (result.code == 600) {
					$("#serverPort i").text("設置成功");
				} else {
					$("#serverPort i").text(result.msg);
				}
			},
			channel : function(id, result) {// 渲染信道
				if (result.code == 600) {
					$("#nodeChannel i").text("設置成功");
				} else {
					$("#nodeChannel i").text(result.msg);
				}
			},
			wifiName : function(id, result) {// Wifi名稱
				if (result.code == 600) {
					$("#nodeWifiName i").text("設置成功");
				} else {
					$("#nodeWifiName i").text(result.msg);
				}
			},
			wifiPsw : function(id, result) {// Wifi密碼
				if (result.code == 600) {
					$("#nodeWifiPsw i").text("設置成功");
				} else {
					$("#nodeWifiPsw i").text(result.msg);
				}
			},
			wifiIpMode : function(id, result) {// Wifi的Ip模式
				if (result.code == 600) {
					$("#nodeWifiIpMode i").text("設置成功");
				} else {
					$("#nodeWifiIpMode i").text(result.msg);
				}
			},
			lanIpMode: function(id, result){
				if(result.code == 600) {
					$("#nodeLanIpMode i").text("設置成功");
				} else {
					$("#nodeLanIpMode i").text(result.msg);
				}
			},
			wifiStaticIp : function(id, result) {// Wifi 的靜態Ip
				if (result.code == 600) {
					$("#nodeWifiStaticIp i").text("設置成功");
				} else {
					$("#nodeWifiStaticIp i").text(result.msg);
				}
			},
			lanIp: function(id, result){// Lan IP
				if (result.code == 600) {
					$("#nodeLanIp i").text("設置成功");
				} else {
					$("#nodeLanIp i").text(result.msg);
				}
			},
			lanMask: function(id, result) {
				if (result.code == 600) {
					$("#nodeLanMask i").text("設置成功");
				} else {
					$("#nodeLanMask i").text(result.msg);
				}
			},
			lanGateWay: function(id, result){
				if (result.code == 600) {
					$("#nodeLanGateWay i").text("設置成功");
				} else {
					$("#nodeLanGateWay i").text(result.msg);
				}
			},
			nodeReset : function(id, result) {// 節點復位
				if (result.code == 600) {
					$("#nodeReset i.hint").text("復位成功");
				} else {
					$("#nodeReset i.hint").text(result.msg);
				}
			}
		},
		refer: {
			receiveSign: function(id, result){
				if (result.code == 600) {
					$("#referSig i").text("設置成功");
				} else {
					$("#referSig i").text(result.msg);
				}
			},
			signK: function(id, result){
				if (result.code == 600) {
					$("#referSigK i").text("設置成功");
				} else {
					$("#referSigK i").text(result.msg);
				}
			}
		},
		arround: {
			serverIp: function(id, result) {
				if (result.code == 600) {
					$("#arroundServerIp i").text("設置成功");
				} else {
					$("#arroundServerIp i").text(result.msg);
				}
			},
			serverPort: function(id, result) {
				if (result.code == 600) {
					$("#arroundServerPort i").text("設置成功");
				} else {
					$("#arroundServerPort i").text(result.msg);
				}
			},
			ipMode: function(id, result) {
				if (result.code == 600) {
					$("#arroundIpMode i").text("設置成功");
				} else {
					$("#arroundIpMode i").text(result.msg);
				}
			},
			lanSubMask: function(id, result) {
				if (result.code == 600) {
					$("#arroundSubMask i").text("設置成功");
				} else {
					$("#arroundSubMask i").text(result.msg);
				}
			},
			lanGateWay: function(id, result) {
				if (result.code == 600) {
					$("#arroundGateWay i").text("設置成功");
				} else {
					$("#arroundGateWay i").text(result.msg);
				}
			},
			lanIp: function(id, result) {
				if (result.code == 600) {
					$("#arroundStaticIp i").text("設置成功");
				} else {
					$("#arroundStaticIp i").text(result.msg);
				}
			},
			arroundWifiName: function(id, result) {
				if (result.code == 600) {
					$("#arroundWifiName i").text("設置成功");
				} else {
					$("#arroundWifiName i").text(result.msg);
				}
			},
			arroundWifiPsw: function(id, result) {
				if (result.code == 600) {
					$("#arroundWifiPsw i").text("設置成功");
				} else {
					$("#arroundWifiPsw i").text(result.msg);
				}
			},
			arroundWifiRssi: function(id, routerId, channel, result) {
				if (result.code == 600) {
					$("#arroundSearchWifiSig i").text("查詢成功");
					$("#arroundSearchWifiSig input").val("-" + result.data + "dBm");
				} else {
					$("#arroundSearchWifiSig i").text(result.msg);
				}
			},
			arroundAtCommand: function(id, routerId, channel, result) {
				if (result.code == 600) {
					$("#arroundWifiOper i").text("查詢成功");
					$("#arroundWifiAtCommond textarea").val(result.data);
				} else {
					$("#arroundWifiOper i").text(result.msg);
				}
			}
		}
	}
	var drawingReadParam = {
			node: {
				typeVersion : function(id, result) {// 渲染類型和版本
					if (result.code == 600) {
						// 將 字符 轉化 為 json 對象
						var typeVersion = JSON.parse(result.data);
						if (typeVersion.TYPE == "ZB2530-01PA_02PA_WIFI_V1.0"
							|| typeVersion.TYPE == "ZB2530_LAN_WIFI_04_V1.0-Wifi") {// wifi
							$("div.wifiItem").show();
							$("div.lanItem").hide();
						} else if (typeVersion.TYPE == "ZB2530-LAN_V02.02"
								|| typeVersion.TYPE == "ZB2530_LAN_WIFI_04_V1.0-Lan") {// lan
							$("div.wifiItem").hide();
							$("div.lanItem").show();
						} else {
							$("div.lanItem").hide();
							$("div.wifiItem").hide();
						}
						$("#currentNodeSetting>p.device:eq(0) span:last").text(typeVersion.ID);
						$("#currentNodeSetting>p.device:eq(1) span:last").text(typeVersion.TYPE);
						$("#currentNodeSetting>p.device:eq(2) span:last").text(typeVersion.VERSION);
					} else {
						$("#currentNodeSetting>p.device:eq(0) span:last").text("");
						$("#currentNodeSetting>p.device:eq(1) span:last").text("");
						$("#currentNodeSetting>p.device:eq(2) span:last").text("");
					}
				},
				serverIp : function(id, result) {// 渲染serverIp
					if (result.code == 600) {
						$("#serverIp i").text("讀取成功");
						$("#serverIp input").val(result.data);
					} else {
						$("#serverIp input").val("");
						$("#serverIp i").text(result.msg);
					}
				},
				serverPort : function(id, result) {// 渲染SeverPort
					if (result.code == 600) {
						$("#serverPort i").text("讀取成功");
						$("#serverPort input").val(result.data);
					} else {
						$("#serverPort input").val();
						$("#serverPort i").text(result.msg);
					}
				},
				channel : function(id, result) {// 渲染信道
					if (result.code == 600) {
						$("#nodeChannel i").text("讀取成功");
						$("#nodeChannel input").val(result.data);
					} else {
						$("#nodeChannel input").val();
						$("#nodeChannel i").text(result.msg);
					}
				},
				wifiName : function(id, result) {// Wifi名稱
					if (result.code == 600) {
						$("#nodeWifiName i").text("讀取成功");
						$("#nodeWifiName input").val(result.data);
					} else {
						$("#nodeWifiName input").val();
						$("#nodeWifiName i").text(result.msg);
					}
				},
				wifiPsw : function(id, result) {// Wifi密碼
					if (result.code == 600) {
						$("#nodeWifiPsw i").text("讀取成功");
						$("#nodeWifiPsw input").val(result.data);
					} else {
						$("#nodeWifiPsw input").val();
						$("#nodeWifiPsw i").text(result.msg);
					}
				},
				wifiIpMode : function(id, result) {// Wifi的Ip模式
					if (result.code == 600) {
						$("#nodeWifiIpMode i").text("讀取成功");
						$("#nodeWifiIpMode dd").removeClass("layui-this");
						if (result.data == "DHCP") {
							$("#nodeWifiIpMode select").find("option:last").attr(
									"selected", "selected");
							$("#nodeWifiIpMode dd[lay-value='1']").addClass("layui-this").click();
						} else {
							$("#nodeWifiIpMode select").find("option:first").attr(
									"selected", "selected");
							$("#nodeWifiIpMode dd[lay-value='0']").addClass("layui-this").click();
						}
					} else {
						$("#nodeWifiStaticIp input").text(result.msg);
					}
				},
				lanIpMode: function(id, result){
					if(result.code == 600) {
						$("#nodeLanIpMode i").text("讀取成功");
						$("#nodeLanIpMode dd").removeClass("layui-this");
						if (result.data == "DHCP") {
							$("#nodeLanIpMode select").find("option:last").attr(
									"selected", "selected");
							$("#nodeLanIpMode dd[lay-value='1']").addClass("layui-this").click();
						} else {
							$("#nodeLanIpMode select").find("option:first").attr(
									"selected", "selected");
							$("#nodeLanIpMode dd[lay-value='0']").addClass("layui-this").click();
						}
					} else {
						$("#nodeLanIpMode input").text(result.msg);
					}
				},
				wifiStaticIp : function(id, result) {// Wifi 的靜態Ip
					if (result.code == 600) {
						$("#nodeWifiStaticIp i").text("讀取成功");
						$("#nodeWifiStaticIp input").val(result.data);
					} else {
						$("#nodeWifiStaticIp input").val();
						$("#nodeWifiStaticIp i").text(result.msg);
					}
				},
				lanIp: function(id, result){// Lan IP
					if (result.code == 600) {
						$("#nodeLanIp i").text("讀取成功");
						$("#nodeLanIp input").val(result.data);
					} else {
						$("#nodeLanIp input").val();
						$("#nodeLanIp i").text(result.msg);
					}
				},
				lanMask: function(id, result) {
					if (result.code == 600) {
						$("#nodeLanMask i").text("讀取成功");
						$("#nodeLanMask input").val(result.data);
					} else {
						$("#nodeLanMask input").val();
						$("#nodeLanMask i").text(result.msg);
					}
				},
				lanGateWay: function(id, result){
					if (result.code == 600) {
						$("#nodeLanGateWay i").text("讀取成功");
						$("#nodeLanGateWay input").val(result.data);
					} else {
						$("#nodeLanGateWay input").val();
						$("#nodeLanGateWay i").text(result.msg);
					}
				},
				arroundWifiSig : function(id, result) {// 周圍Wifi的信號
					if (result.code == 600) {
						$("#nodeArroundWifiSig i").text("-" + result.data + "dBm");
					} else {
						$("#nodeArroundWifiSig i").text(result.msg);
					}
				},
				nodeReset : function(id, result) {// 節點復位
					if (result.code == 600) {
						$("#nodeReset i.hint").text("復位成功");
					} else {
						$("#nodeReset i.hint").text(result.msg);
					}
				}
		},
		refer: {
			typeVersion: function(id, result){
				if (result.code == 600) {
					// 將 字符 轉化 為  -json- 對象
					var typeVersion = JSON.parse(result.data);
					$("#referSetting>p.device:eq(1) span:last").text(typeVersion.TYPE);
					$("#referSetting>p.device:eq(2) span:last").text(typeVersion.VERSION);
				} else {
					$("#referSetting>p.device:eq(1) span:last").text("");
					$("#referSetting>p.device:eq(2) span:last").text("");
				}
			},
			receiveSign: function(id, result) {
				if (result.code == 600) {
					$("#referSig i").text("讀取成功");
					$("#referSig input").val(result.data);
				} else {
					$("#referSig input").val();
					$("#referSig i").text(result.msg);
				}
			},
			signK: function(id, result) {
				if (result.code == 600) {
					$("#referSigK i").text("讀取成功");
					$("#referSigK input").val(result.data);
				} else {
					$("#referSigK input").val();
					$("#referSigK i").text(result.msg);
				}
			},
			referReset: function(id, result){
				if (result.code == 600) {
					$("#referReset i.hint").text("讀取成功");
				} else {
					$("#referReset i.hint").text(result.msg);
				}
			}
		},
		arround: {
			setReferSigThreshold: function(id, routerId, result) {
				if(result.code == 600) {
					$("#arroundReferSigThreshold i.hint").text("設置成功");
				} else {
					$("#arroundReferSigThreshold i").text(result.msg);
				}
			},
			setReferSigK: function(id, routerId, result) {
				if(result.code == 600) {
					$("#arroundReferSigK i.hint").text("設置成功");
				} else {
					$("#arroundReferSigK i").text(result.msg);
				}
			},
			referReset: function(id, routerId, result) {
				if(result.code == 600) {
					$("#arroundReferReset i.hint").text("復位成功");
				} else {
					$("#arroundReferReset i.hint").text(result.msg);
				}
			},
			betweenReferNodeSig: function(id, routerId, result) {
				if(result.code == 600) {
					var rssiObj = JSON.parse(result.data);
					$("#arroundReferNodeToReferSig i").text("讀取成功");
					$("#arroundReferNodeToReferSig input").val( "-" + rssiObj.RSSI1 + " dbm");
					
					$("#arroundReferReferToNodeSig i").text("讀取成功");
					$("#arroundReferReferToNodeSig input").val("-" + rssiObj.RSSI2 + " dbm");
				} else {
					$("#arroundReferNodeToReferSig i").text(result.msg);
					$("#arroundReferNodeToReferSig input").val("");
					
					$("#arroundReferReferToNodeSig i").text(result.msg);
					$("#arroundReferReferToNodeSig input").val("");
				}
			},
			referSigK: function(id, routerId, result) {
				if(result.code == 600) {
					$("#arroundReferSigK i.hint").text("讀取成功");
					$("#arroundReferSigK input").val(result.data);
				} else {
					$("#arroundReferSigK input").val();
					$("#arroundReferSigK i").text(result.msg);
				}
			},
			referSigThreshold: function(id, routerId, result) {
				if(result.code == 600) {
					$("#arroundReferSigThreshold i.hint").text("讀取成功");
					$("#arroundReferSigThreshold input").val(result.data);
				} else {
					$("#arroundReferSigThreshold input").val();
					$("#arroundReferSigThreshold i").text(result.msg);
				}
			},
			referTypeVersion: function(id, routerId, result) {
				if(result.code == 600) {
					var typeVersionObj = JSON.parse(result.data);
					$("#nodeSetting>div.nodeSettingRight>div.refer>p.device:eq(0) span:last").text(id);
					$("#nodeSetting>div.nodeSettingRight>div.refer>p.device:eq(1) span:last").text(typeVersionObj.TYPE);
					$("#nodeSetting>div.nodeSettingRight>div.refer>p.device:eq(2) span:last").text(typeVersionObj.VERSION);
				} else {
					$("#nodeSetting>div.nodeSettingRight>div.refer>p.device:eq(0) span:last").text(id);
					$("#nodeSetting>div.nodeSettingRight>div.refer>p.device:eq(1) span:last").text("");
					$("#nodeSetting>div.nodeSettingRight>div.refer>p.device:eq(2) span:last").text("");
				}
			},
			nodeServerIp: function(id,  routerId, channel,  result) {
				if (result.code == 600) {
					$("#arroundServerIp i").text("讀取成功");
					$("#arroundServerIp input").val(result.data);
				} else {
					$("#arroundServerIp input").val("");
					$("#arroundServerIp i").text(result.msg);
				}
			},
			nodeReset: function(id, routerId, channel, result){
				if(result.code == 600) {
					$("#arroundNodeReset i.hint").text("復位成功");
				} else {
					$("#arroundNodeReset i.hint").text(result.msg);
				}
			},
			nodeLastState: function(id, routerId, channel, result){
				if (result.code == 600) {
					var msg = "";
					var isLan = $("#nodeSetting").attr("iswifi") == 'false'?true:false;
					switch (result.data) {
					case '0': msg = isLan?"網絡連接成功":"Wifi連接成功"; break;
					case '1': msg = isLan?"網絡IC初始化失敗":"AT+RST失敗";break;
					case '2': msg = isLan?"物理網線連接失敗":"AT+CWMODE_CUR失敗";break;
					case '3': msg = isLan?"獲取ARP失敗":"AT+CWDHCP_CUR失敗";break;
					case '4': msg = isLan?"DHCP失敗":"AT+CIPSTA_CUR失敗";break;
					case '5': msg = isLan?"IP沖突":"AT+CWJAP_CUR失敗_未響應";break;
					case '6': msg = "AT+PING失敗";break;
					case '7': msg = "AT+CIPSEND失敗";break;
					case '8': msg = "AT+CWJAP_CUR失敗_連接超時";break;
					case '9': msg = "AT+CWJAP_CUR失敗_密碼錯誤";break;
					case '10': msg = "AT+CWJAP_CUR失敗_找不到目標AP";break;
					case '11': msg = "AT+CWJAP_CUR失敗_連接失敗";break;
					case '12': msg = "AT+CWJAP_CUR失敗_其它";break;
					default:
						break;
					}
					$("#arroundNodeLastState i").text("讀取成功");
					$("#arroundNodeLastState input").val(msg);
				} else {
					$("#arroundNodeLastState input").val();
					$("#arroundNodeLastState i").text(result.msg);
				}
			},
			nodeLanStaticIp: function(id,  routerId, channel, result){
				if (result.code == 600) {
					$("#arroundStaticIp i").text("讀取成功");
					$("#arroundStaticIp input").val(result.data);
				} else {
					$("#arroundStaticIp input").val();
					$("#arroundStaticIp i").text(result.msg);
				}
			},
			nodeWifiName: function(id,  routerId, channel, result) {
				if (result.code == 600) {
					$("#arroundWifiName i").text("讀取成功");
					$("#arroundWifiName input").val(result.data);
				} else {
					$("#arroundWifiName input").val();
					$("#arroundWifiName i").text(result.msg);
				}
			},
			nodeWifiPsw: function(id,  routerId, channel, result) {
				if (result.code == 600) {
					$("#arroundWifiPsw i").text("讀取成功");
					$("#arroundWifiPsw input").val(result.data);
				} else {
					$("#arroundWifiPsw input").val();
					$("#arroundWifiPsw i").text(result.msg);
				}
			},
			nodeLanIpMode: function(id,  routerId, channel, result) {
				if(result.code == 600) {
					$("#arroundIpMode i").text("讀取成功");
					$("#arroundIpMode dd").removeClass("layui-this");
					if (result.data == "DHCP") {
						$("#arroundIpMode select").find("option:last").attr(
								"selected", "selected");
						$("#arroundIpMode dd[lay-value='1']").addClass("layui-this").click();
					} else {
						$("#arroundIpMode select").find("option:first").attr(
								"selected", "selected");
						$("#arroundIpMode dd[lay-value='0']").addClass("layui-this").click();
					}
				} else {
					$("#arroundIpMode input").text(result.msg);
				}
			},
			nodeLanMask: function(id,  routerId, channel, result){
				if (result.code == 600) {
					$("#arroundSubMask i").text("讀取成功");
					$("#arroundSubMask input").val(result.data);
				} else {
					$("#arroundSubMask input").val();
					$("#arroundSubMask i").text(result.msg);
				}
			},
			nodeGateWay: function(id, routerId, channel, result){
				if (result.code == 600) {
					$("#arroundGateWay i").text("讀取成功");
					$("#arroundGateWay input").val(result.data);
				} else {
					$("#arroundGateWay input").val();
					$("#arroundGateWay i").text(result.msg);
				}
			},
			nodeServerPort: function(id,  routerId, channel, result){
				if (result.code == 600) {
					$("#arroundServerPort i").text("讀取成功");
					$("#arroundServerPort input").val(result.data);
				} else {
					$("#arroundServerPort input").val("");
					$("#arroundServerPort i").text(result.msg);
				}
			},
			typeVersion: function(id, result) {
				if(result.code == 600) {
					var typeVersion = JSON.parse(result.data);
					$("#nodeSetting>div.nodeSettingLeft>p.device:eq(1)>span:last").text(typeVersion.VERSION);
					$("#nodeSetting>div.nodeSettingLeft>p.device:eq(2)>span:last").text(typeVersion.TYPE);
					if(typeVersion.TYPE == "ZB2530-01PA_02PA_WIFI_V1.0" || 
					   typeVersion.TYPE == "ZB2530_LAN_WIFI_04_V1.0-Wifi") {
						$("#nodeSetting").attr("selfIsWifi",true);
						$("#currentNodeSetting div.wifiItem").show();
						$("#currentNodeSetting div.lanItem").hide();
					} else {
						$("#nodeSetting").attr("selfIsWifi",false);
						$("#currentNodeSetting div.wifiItem").hide();
						$("#currentNodeSetting div.lanItem").show();
					}
				} else {
					$("#nodeSetting>div.nodeSettingLeft>p.device:eq(1)>span:last").text("");
					$("#nodeSetting>div.nodeSettingLeft>p.device:eq(2)>span:last").text("");
				}
			},
			search: function(id, result){
				if(result.code == 600) {
					$("#searchDevicesTable>tbody").empty();
					$("#searchDevicesTable").attr("routerId", id);
					var array = [];
					for (var i = 0; i < result.data.length; i++) {
						if(null != result.data && null != result.data[i] 
						&& result.data[i].code == 600 ) {
							var obj = result.data[i].data;
							if (obj["channel"]) {
								array.push("<tr channel='" + obj["channel"] 
										+ "' devId='" + obj["id"]
										+ "' type='NODE'>" + "<td>" + obj["id"]
										+ "</td><td>" + obj["name"]
										+ "</td><td>" + obj["type"] + "</td>"
										+ "<td>" + obj["version"]
										+ "</td></tr>");
							} else {
								array.push("<tr devId='" + obj["id"]
										+ "' type='REFER'><td>" + obj["id"]
										+ "</td>" + "<td>" + obj["name"]
										+ "</td><td>" + obj["type"]
										+ "</td><td>" + obj["version"]
										+ "</td></tr>");
							}
						}
					}
					$("#searchDevicesTable>tbody").append(array.join(""));
					// 綁定相應的單擊事件
					$("#searchDevicesTable tr").off("click").click(function(){
						var kind = $(this).attr("type");
						var devId = $(this).attr("devId");
						var routerId = $("#searchDevicesTable").attr("routerId");
						var devType = $(this).find("td:eq(2)").text();
						var devVersion = $(this).find("td:eq(3)").text();
						if (kind == "REFER") {
							// 讓右側設置基站的隱藏，參考點顯示
							$("#nodeSetting>div.nodeSettingRight>div").hide();
							$("#nodeSetting>div.nodeSettingRight>div.refer").show();
							// 設置裡面的信息清除
							$("#nodeSetting div.refer i.hint").text("");
							$("#nodeSetting div.refer input").val("");
							// 直接從表格中獲取類型及版本訊息
							passSettingSelf.arround.referTypeVersion(devId, devType, devVersion);
							devices.self.setting.arround.read(devId, routerId, "ReadArroundReferSigThresholdUdpPack", 
									passSettingSelf.arround.referSigThreshold);
							drawingReadParam.arround.referInitEvent(devId, routerId);
						} else if (kind == "NODE") {
							// 讓基站顯示，參考點隱藏
							$("#nodeSetting>div.nodeSettingRight>div").hide();
							$("#nodeSetting>div.nodeSettingRight>div.node").show();
							// 清除提醒的資料訊息
							$("#nodeSetting div.node i.hint").text("");
							$("#nodeSetting div.node input").val("");
							// 獲取當前選擇的基站的信道
							var channel = $(this).attr("channel");
							// 不用重新獲取版本訊息，直接讀表格中的內容即可
							passSettingSelf.arround.nodeTypeVersion(devId, devType, devVersion, channel);
							// 根據基站是Wifi還是Lan，判斷各自顯示什麼
							if(devType == "ZB2530-01PA_02PA_WIFI_V1.0" || 
							   devType == "ZB2530_LAN_WIFI_04_V1.0-Wifi") {
								$("#nodeSetting div.node form>div.arroundWifi").show();
								$("#nodeSetting div.node form>div.arroundLan").hide();
								$("#nodeSetting").attr("isWifi", true);
							} else {
								$("#nodeSetting div.node form>div.arroundWifi").hide();
								$("#nodeSetting div.node form>div.arroundLan").show();
								$("#nodeSetting").attr("isWifi", false);
							}
							// 請求服務器IP
							devices.self.setting.arround.readNode(devId, routerId, channel, "ReadArroundNodeServerIpUdpPack",
									passSettingSelf.arround.nodeServerIp);
							// 綁定單擊事件
							drawingReadParam.arround.nodeInitEvent(devId, routerId, channel);
						}
					})
				}
			},
			referInitEvent: function(id, routerId) {// 綁定參考點初始化事件
				// 讀取參考點信號閥值
				$("#arroundReferSigThreshold button:last").off("click").click(function(){
					$("#arroundReferSigThreshold i.hint").text("");
					devices.self.setting.arround.read(id, routerId, "ReadArroundReferSigThresholdUdpPack", 
							drawingReadParam.arround.referSigThreshold);
				})
				// 讀取參考點信號強度系數
				$("#arroundReferSigK button:last").off("click").click(function(){
					$("#arroundReferSigK i.hint").text("");
					devices.self.setting.arround.read(id, routerId, "ReadArroundReferSigKUdpPack", 
							drawingReadParam.arround.referSigK);
				})
				// 讀取參考點與節點之間的信號強度
				$("#arroundReferReferToNodeSig button:last").off("click").click(function(){
					$("#arroundReferReferToNodeSig i.hint").text("");
					$("#arroundReferNodeToReferSig i.hint").text("");
					devices.self.setting.arround.read(id, routerId, "ReadArroundBetweenSigUdpPack", 
							drawingReadParam.arround.betweenReferNodeSig);
				})
				// 復位參考點
				$("#arroundReferReset button").off("click").click(function(){
					$("#arroundReferReset i.hint").text("");
					devices.self.setting.arround.read(id, routerId, "ResetArroundReferUdpPack", 
							drawingReadParam.arround.referReset);
				})
				// 設置信號閥值
				$("#arroundReferSigThreshold button:first").off("click").click(function(){
					$("#arroundReferSigThreshold i.hint").text("");
					var paramValue = $("#arroundReferSigThreshold input").val();
					devices.self.setting.arround.set(id, routerId, "SetArroundReferSigThresholdUdp",paramValue,
							drawingReadParam.arround.setReferSigThreshold);
				})
				// 設置信號強度係數
				$("#arroundReferSigK button:first").off("click").click(function(){
					$("#arroundReferSigK i.hint").text("");
					var paramValue = $("#arroundReferSigK input").val();
					devices.self.setting.arround.set(id, routerId, "SetArroundReferSigKUdpPack", paramValue,
							drawingReadParam.arround.setReferSigK);
				})
			},
			nodeInitEvent: function(id, routerId, channel) {// 綁定基站初始化事件
				// 服務器IP
				$("#arroundServerIp button:last").off("click").click(function(){
					$("#arroundServerIp i.hint").text("");
					devices.self.setting.arround.readNode(id, routerId,channel, "ReadArroundNodeServerIpUdpPack", 
							drawingReadParam.arround.nodeServerIp);
				})
				// 服務端口
				$("#arroundServerPort button:last").off("click").click(function(){
					$("#arroundServerPort i.hint").text("");
					devices.self.setting.arround.readNode(id, routerId,channel, "ReadArroundNodeServerPortUdpPack", 
							drawingReadParam.arround.nodeServerPort);
				})
				// 讀取Ip名稱
				$("#arroundWifiName button:last").off("click").click(function(){
					$("#arroundWifiName i.hint").text("");
					devices.self.setting.arround.readNode(id, routerId,channel, "ReadArroundNodeWifiNameUdpPack", 
							drawingReadParam.arround.nodeWifiName);
				})
				// 讀取Ip密碼
				$("#arroundWifiPsw button:last").off("click").click(function(){
					$("#arroundWifiPsw i.hint").text("");
					devices.self.setting.arround.readNode(id, routerId,channel, "ReadArroundNodeWifiPswUdpPack", 
							drawingReadParam.arround.nodeWifiPsw);
				})
				// IP模式
				$("#arroundIpMode button:last").off("click").click(function(){
					$("#arroundIpMode i.hint").text("");
					var isWifi = $("#nodeSetting").attr("iswifi")=='true'?true:false;
					if(isWifi) {
						devices.self.setting.arround.readNode(id, routerId,channel, "ReadArroundNodeWifiIpModeUdpPack",
								drawingReadParam.arround.nodeLanIpMode);
					} else {
						devices.self.setting.arround.readNode(id, routerId,channel, "ReadArroundNodeLanIpModeUdpPack", 
								drawingReadParam.arround.nodeLanIpMode);
					}
				})
				// 掩碼
				$("#arroundSubMask button:last").off("click").click(function(){
					$("#arroundSubMask i.hint").text("");
					devices.self.setting.arround.readNode(id, routerId,channel, "ReadArroundNodeMaskUdpPack", 
							drawingReadParam.arround.nodeLanMask);
				})
				// 網關
				$("#arroundGateWay button:last").off("click").click(function(){
					$("#arroundGateWay i.hint").text("");
					devices.self.setting.arround.readNode(id, routerId,channel, "ReadArroundNodeGateWayUdpPack", 
							drawingReadParam.arround.nodeGateWay);
				})
				// 靜態IP
				$("#arroundStaticIp button:last").off("click").click(function(){
					$("#arroundStaticIp i.hint").text("");
					var isWifi = $("#nodeSetting").attr("iswifi")=='true'?true:false;
					if(isWifi) {
						devices.self.setting.arround.readNode(id, routerId,channel, "ReadArroundNodeWifiStaticIpUdpPack", 
								drawingReadParam.arround.nodeLanStaticIp);
					} else {
						devices.self.setting.arround.readNode(id, routerId,channel, "ReadArroundNodeLanStaticIpUdpPack", 
								drawingReadParam.arround.nodeLanStaticIp);
					}
				})
				// 基站最後壹次連接狀態
				$("#arroundNodeLastState button").off("click").click(function(){
					$("#arroundNodeLastState i.hint").text("");
					devices.self.setting.arround.readNode(id, routerId,channel, "ReadArroundNodeWifiLastState", 
							drawingReadParam.arround.nodeLastState);
				})
				// 基站復位
				$("#arroundNodeReset button").off("click").click(function(){
					$("#arroundNodeReset i.hint").text("");
					devices.self.setting.arround.readNode(id, routerId, channel, "ResetArroundNodeUdpPack",
							drawingReadParam.arround.nodeReset);
				})
				// 設置服務器IP
				$("#arroundServerIp button:first").off("click").click(function(){
					$("#arroundServerIp i.hint").text("");
					var val = $("#arroundServerIp input").val();
					devices.self.setting.arround.setNode(id, routerId, channel, "SetArroundNodeServerIpUdpPack",
							val, drawingSettingParam.arround.serverIp);
				})
				// 設置服務器端口
				$("#arroundServerPort button:first").off("click").click(function(){
					$("#arroundServerPort i.hint").text("");
					var val = $("#arroundServerPort input").val();
					devices.self.setting.arround.setNode(id, routerId, channel, "SetArroundNodeServerPortUdpPack",
							val, drawingSettingParam.arround.serverPort);
				})
				// 設置基站的Ip模式
				$("#arroundIpMode button:first").off("click").click(function(){
					$("#arroundIpMode i.hint").text("");
					var val = $("#arroundIpMode select[name='IPMode']").find(
							  "option:selected").val();
					var isWifi = $("#nodeSetting").attr("iswifi")=='true'?true:false;
					if(isWifi) {
						devices.self.setting.arround.setNode(id, routerId, channel, "SetArroundNodeWifiIpModelUdpPack",
								val, drawingSettingParam.arround.ipMode);
					} else {
						devices.self.setting.arround.setNode(id, routerId, channel, "SetArroundNodeLanIpModeUdpPack",
								val, drawingSettingParam.arround.ipMode);
					}
				})
				// 設置基站的Ip掩碼
				$("#arroundSubMask button:first").off("click").click(function(){
					$("#arroundSubMask i.hint").text("");
					var val = $("#arroundSubMask input").val();
					devices.self.setting.arround.setNode(id, routerId, channel, "SetArroundNodeSubMaskUdpPack",
							val, drawingSettingParam.arround.lanSubMask);
				})
				// 設置基站的網關
				$("#arroundGateWay button:first").off("click").click(function(){
					$("#arroundGateWay i.hint").text("");
					var val = $("#arroundGateWay input").val();
					devices.self.setting.arround.setNode(id, routerId, channel, "SetArroundNodeGateWayUdpPack",
							val, drawingSettingParam.arround.lanGateWay);
				})
				// 設置基站的靜態Ip
				$("#arroundStaticIp button:first").off("click").click(function(){
					$("#arroundStaticIp i.hint").text("");
					var val = $("#arroundStaticIp input").val();
					var isWifi = $("#nodeSetting").attr("iswifi")=='true'?true:false;
					if(isWifi) {
						devices.self.setting.arround.setNode(id, routerId, channel, "SetArroundNodeWifiStaticIpUdpPack",
								val, drawingSettingParam.arround.lanIp);
					} else {
						devices.self.setting.arround.setNode(id, routerId, channel, "SetArroundNodeLanIpUdpPack",
								val, drawingSettingParam.arround.lanIp);
					}
				})
				// 設置Wifi名稱
				$("#arroundWifiName button:first").off("click").click(function(){
					$("#arroundWifiName i.hint").text("");
					var val = $("#arroundWifiName input").val();
					devices.self.setting.arround.setNode(id, routerId, channel, "SetArroundNodeWifiNameUdpPack",
							val, drawingSettingParam.arround.arroundWifiName);
				})
				// 設置Wifi密碼
				$("#arroundWifiPsw button:first").off("click").click(function(){
					$("#arroundWifiPsw i.hint").text("");
					var val = $("#arroundWifiPsw input").val();
					devices.self.setting.arround.setNode(id, routerId, channel, "SetArroundNodeWifiPswUdpPack",
							val, drawingSettingParam.arround.arroundWifiPsw);
				})
				// 查找指定Wifi的信號強度
				$("#arroundSearchWifiSig button:first").off("click").click(function(){
					$("#arroundSearchWifiSig i.hint").text("");
					$("#arroundSearchWifiSig input").val("");
					
					var val = $("#arroundSearchSigWifiName input").val();
					devices.self.setting.arround.readNode_Param(id, routerId, channel, 
							"ReadArroundNodeWifiRssiUdpPack", val, drawingSettingParam.arround.arroundWifiRssi);
				})
				
				$("#arroundWifiOper button:first").off("click").click(function(){
					$("#arroundWifiOper i.hint").text("");
					$("#arroundWifiAtCommond textarea").val("");
					
					var timeOut = $("#arroundNodeResponseTimeOut input").val();
					var atCommand = $("#arroundWifiOper input").val();
					
					devices.self.setting.arround.readNodeTimeOut_Param(id, routerId, channel, "ReadArroundNodeAtCommandUdpPack", timeOut, 
							atCommand, drawingSettingParam.arround.arroundAtCommand);
				})
			}
		}
	}
	var passSettingSelf = {
		node: {
			typeVersion : function(id, result) {// 類型和版本訊息
				drawingReadParam.node.typeVersion(id, result);
				devices.self.setting.self.read(id, "ReadIPUdpPack",
						passSettingSelf.node.serverIp);
			},
			serverIp : function(id, result) {// Server Ip
					drawingReadParam.node.serverIp(id, result);
					devices.self.setting.self.read(id, "ReadPortUdpPack",
							passSettingSelf.node.serverPort);
			},
			serverPort : function(id, result) {// Server Port
					drawingReadParam.node.serverPort(id, result);
					// 讀取頻段
					devices.self.setting.self.read(id,
							"ReadNodeChannelUdpPack", passSettingSelf.node.channel);
			},
			channel : function(id, result) {// 信道
					drawingReadParam.node.channel(id, result);
					// 判斷當前設備的類型，是wifi還是帶lan口的，如果是Wifi我們應該讀取Wifi名稱和Wifi密碼
					// 如果帶Lan口的，我們應該讀取Lan口的子網掩碼和網關
					var TYPE = $("#currentNodeSetting>p.device:eq(1) span:last").text();
					if (TYPE == "ZB2530-01PA_02PA_WIFI_V1.0"
					 || TYPE == "ZB2530_LAN_WIFI_04_V1.0-Wifi") {
						devices.self.setting.self.read(id,
								"ReadWifiNameUdpPack", passSettingSelf.node.wifiName);
					} else if(TYPE == "ZB2530-LAN_V02.02" 
					 || TYPE == "ZB2530_LAN_WIFI_04_V1.0-Lan") {
						devices.self.setting.self.read(id,
								"ReadIpModeUdpPack", passSettingSelf.node.lanIpMode);
					}
			},
			wifiName : function(id, result) {// 信號名稱
					drawingReadParam.node.wifiName(id, result);
					devices.self.setting.self.read(id,
							"ReadWifiPswUdpPack", passSettingSelf.node.wifiPsw);
			},
			wifiPsw : function(id, result) {// 信號密碼
					drawingReadParam.node.wifiPsw(id, result);
					devices.self.setting.self
							.read(id, "ReadNodeWifiIpModeUdpPack",
									passSettingSelf.node.wifiIpMode);
			},
			wifiIpMode : function(id, result) {// Wifi的Ip模式
					drawingReadParam.node.wifiIpMode(id, result);
					devices.self.setting.self.read(id,
							"ReadNodeWifiStaticIpUdpPack",
							drawingReadParam.node.wifiStaticIp);
			},
			lanIpMode: function(id, result){
					drawingReadParam.node.lanIpMode(id, result);
					devices.self.setting.self.read(id,
							"ReadNodeIpUdpPack",
							passSettingSelf.node.lanIp);
			},
			lanIp: function(id, result){
					drawingReadParam.node.lanIp(id, result);
					
					devices.self.setting.self.read(id,
							"ReadNodeMaskUdpPack",
							passSettingSelf.node.lanMask);
			},
			lanMask: function(id, result) {
					drawingReadParam.node.lanMask(id, result);
					
					devices.self.setting.self.read(id,
							"ReadNodeGateWayUdpPack",
							passSettingSelf.node.lanGateWay);
			},
			lanGateWay: function(id, result){
					drawingReadParam.node.lanGateWay(id, result);
			},
			initEvent: function(){
					// 綁定搜索周圍Wifi信號強度的按鈕
					var nodeId = $("#nodeSetting").attr("nodeId");
					$("#nodeArroundWifiSig button").off("click").click(function() {
						var wifiName = $("#nodeArroundWifiSig input").val();
						$("#nodeArroundWifiSig i.hint").text("");
						devices.self.setting.self.read_Param(nodeId,
								"ReadNodeArroundWifiRssiUdpPack", wifiName,
								drawingReadParam.node.arroundWifiSig);
					})
					// 綁定復位節點的按鈕
					$("#nodeReset button").off("click").click(function() {
						$("#nodeReset i.hint").text("");
						devices.self.setting.self.read(nodeId, "ResetNodePack",
								drawingReadParam.node.nodeReset);
					})
					// 綁定讀取-ServerIp-的按鈕
					$("#serverIp button:last").off("click").click(
							function() {
								$("#serverIp i.hint").text("");
								devices.self.setting.self.read(nodeId,
										"ReadIPUdpPack", drawingReadParam.node.serverIp);
					})
					// 綁定-ServerPort-的按鈕
					$("#serverPort button:last").off("click").click(
							function() {
								$("#serverPort i.hint").text("");
								devices.self.setting.self.read(nodeId,
										"ReadPortUdpPack", drawingReadParam.node.serverPort);
					})
					// 綁定-Channel-頻道
					$("#nodeChannel button:first").off("click").click(
							function() {
								$("#nodeChannel i.hint").text("");
								devices.self.setting.self.read(nodeId,
										"ReadNodeChannelUdpPack", drawingReadParam.node.channel);
					})
					// 綁定-WifiName wifi-名稱
					$("#nodeWifiName button:last").off("click").click(
							function() {
								$("#nodeWifiName i.hint").text("");
								devices.self.setting.self.read(nodeId,
										"ReadWifiNameUdpPack", drawingReadParam.node.wifiName);
					})
					// 綁定-WifiPsw wifi-密碼
					$("#nodeWifiPsw button:last").off("click").click(
							function() {
								$("#nodeWifiPsw i.hint").text("");
								devices.self.setting.self.read(nodeId,
										"ReadWifiPswUdpPack", drawingReadParam.node.wifiPsw);
					})
					// 綁定-IP-模式
					$("#nodeWifiIpMode button:last").off("click").click(
							function() {
								$("#nodeWifiIpMode i.hint").text("");
								devices.self.setting.self.read(nodeId,
										"ReadNodeWifiIpModeUdpPack",
										drawingReadParam.node.wifiIpMode);
					})
					// 綁定靜態-IP-
					$("#nodeWifiStaticIp button:last").off("click").click(
							function() {
								$("#nodeWifiStaticIp i.hint").text("");
								devices.self.setting.self.read(nodeId,
										"ReadNodeWifiStaticIpUdpPack",
										drawingReadParam.node.wifiStaticIp);
					})		
					$("#nodeLanIpMode button:last").off("click").click(
							function(){
								$("#nodeLanIpMode i.hint").text("");
								devices.self.setting.self.read(nodeId,
										"ReadIpModeUdpPack",
										drawingReadParam.node.lanIpMode);
					})
					// Lan Ip
					$("#nodeLanIp button:last").off("click").click(function(){
							$("#nodeLanIp i.hint").text("");
							devices.self.setting.self.read(nodeId,
										"ReadNodeIpUdpPack",
										drawingReadParam.node.lanIp);
					})
					// Lan 掩碼
					$("#nodeLanMask button:last").off("click").click(function(){
						$("#nodeLanMask i.hint").text("");
						devices.self.setting.self.read(nodeId,
								"ReadNodeMaskUdpPack",
								drawingReadParam.node.lanMask);
					})
					// Lan 網關
					$("#nodeLanGateWay button:last").off("click").click(function(){
						$("#nodeLanGateWay i.hint").text("");
						devices.self.setting.self.read(nodeId,
								"ReadNodeGateWayUdpPack",
								drawingReadParam.node.lanGateWay);
					})
					// 設置 Server的IP
					$("#serverIp button:first").off("click").click(function(){
						var serverIp = $("#serverIp input").val();
						$("#serverIp i.hint").text("");
						devices.self.setting.self.set_Param(nodeId,
								"SetNodeServerIp", serverIp, drawingSettingParam.node.serverIp);
					})
					// 設置Server的Port
					$("#serverPort button:first").off("click").click(function(){
						var serverPort = $("#serverPort input").val();
						$("#serverPort i.hint").text("");
						devices.self.setting.self.set_Param(nodeId,
								"SetNodeServerPort", serverPort, drawingSettingParam.node.serverPort);
					})
					// 設置Ip模式
					$("#nodeLanIpMode button:first").off("click").click(function(){
						var val = $("#nodeLanIpMode select[name='lanIpMode']").find(
										"option:selected").val();
						$("#nodeLanIpMode i.hint").text("");
						devices.self.setting.self.set_Param(nodeId,
								"SetNodeIpModeUdpPack", val, drawingSettingParam.node.lanIpMode);
					})
					// 設置自身Ip
					$("#nodeLanIp button:first").off("click").click(function(){
						var val = $("#nodeLanIp input").val();
						$("#nodeLanIp i.hint").text("");
						devices.self.setting.self.set_Param(nodeId,
								"SetNodeSelfIPUdpPack", val, drawingSettingParam.node.lanIp);
					})
					// 設置自身掩碼
					$("#nodeLanMask button:first").off("click").click(function(){
						var val = $("#nodeLanMask input").val();
						$("#nodeLanMask i.hint").text("");
						devices.self.setting.self.set_Param(nodeId,
								"SetNodeSelfMaskUdpPack", val, drawingSettingParam.node.lanMask);
					})
					// 設置網關
					$("#nodeLanGateWay button:first").off("click").click(function(){
						var val = $("#nodeLanGateWay input").val();
						$("#nodeLanGateWay i.hint").text("");
						devices.self.setting.self.set_Param(nodeId,
								"SetNodeSelfGateWayUdpPack", val, drawingSettingParam.node.lanGateWay);
					})
					// 設置Wifi名稱
					$("#nodeWifiName button:first").off("click").click(function(){
						var val = $("#nodeWifiName input").val();
						$("#nodeWifiName i.hint").text("");
						devices.self.setting.self.set_Param(nodeId,
								"SetWifiNameUdpPack", val, drawingSettingParam.node.wifiName);
					})
					// 設置Wifi密碼
					$("#nodeWifiPsw button:first").off("click").click(function(){
						var val = $("#nodeWifiPsw input").val();
						$("#nodeWifiPsw i.hint").text("");
						devices.self.setting.self.set_Param(nodeId,
								"SetWifiPswUdpPack", val, drawingSettingParam.node.wifiPsw);
					})
					// 設置Wifi的Ip模式
					$("#nodeWifiIpMode button:first").off("click").click(function(){
						var val = $("#nodeWifiIpMode select[name='wifiIpMode']").find(
							"option:selected").val();
						$("#nodeWifiIpMode i.hint").text("");
						devices.self.setting.self.set_Param(nodeId,
								"SetWifiIpModeUdpPack", val, drawingSettingParam.node.wifiIpMode);
					})
					// 設置靜態的Ip模式
					$("#nodeWifiStaticIp button:first").off("click").click(function(){
						var val = $("#nodeWifiStaticIp input").val();
						$("#nodeWifiStaticIp i.hint").text("");
						devices.self.setting.self.set_Param(nodeId,
								"SetWifiStaticIpUdpPack", val, drawingSettingParam.node.wifiStaticIp);
					})
			}
		},
		refer: {
			typeVersion : function(id, result) {// 類型和版本訊息
				drawingReadParam.refer.typeVersion(id, result);
				devices.self.setting.self.read(id, "ReadReferReceiveSign",
						passSettingSelf.refer.receiveSign);
			},
			receiveSign: function(id, result){// 信號強度閥值
				drawingReadParam.refer.receiveSign(id, result);
				devices.self.setting.self.read(id, "ReadReferSignKUdpPack",
						drawingReadParam.refer.signK);
			},
			initEvent: function(){
				var referId = $("#referSetting").attr("referId");
				// 讀取接收的信號閥值
				$("#referSig button:last").off("click").click(function(){
					$("#referSig i.hint").text("");
					devices.self.setting.self.read(referId,
							"ReadReferReceiveSign",
							drawingReadParam.refer.receiveSign);
				})
				// 讀取接收的信號系數
				$("#referSigK button:last").off("click").click(function(){
					$("#referSigK i.hint").text("");
					devices.self.setting.self.read(referId,
							"ReadReferSignKUdpPack",
							drawingReadParam.refer.signK);
				})
				// 復位
				$("#referReset button").off("click").click(function(){
					$("#referReset i.hint").text("");
					devices.self.setting.self.read(referId, "ResetReferUdpPack", 
							drawingReadParam.refer.referReset);
				})
				// 設置接收的信號閥值
				$("#referSig button:first").off("click").click(function(){
					var val = $("#referSig input").val();
					$("#referSig i.hint").text("");
					devices.self.setting.self.set_Param(referId,
							"SetReferReceiveSignUdpPack", val, 
							drawingSettingParam.refer.receiveSign);
				})
				// 設置接收的信號係數
				$("#referSigK button:first").off("click").click(function(){
					var val = $("#referSigK input").val();
					$("#referSigK i.hint").text("");
					devices.self.setting.self.set_Param(referId,
							"SetReferSignKUdpPack", val, 
							drawingSettingParam.refer.signK);
				})
			}
		},
		arround: {
			typeVersion: function(id, result){
				drawingReadParam.arround.typeVersion(id, result);
			},
			nodeTypeVersion: function(id, type, version, channel){
				$("#nodeSetting>div.nodeSettingRight>div.node>p.device:eq(0) span:last")
					.text(id);
				$("#nodeSetting>div.nodeSettingRight>div.node>p.device:eq(1) span:last")
					.text(type);
				$("#nodeSetting>div.nodeSettingRight>div.node>p.device:eq(2) span:last")
					.text(version);
				$("#nodeSetting>div.nodeSettingRight>div.node>p.device:eq(3) span:last")
					.text(channel);
			},
			referTypeVersion: function(id, type, version){
				// 直接從表格中獲取類型及版本訊息
				$("#nodeSetting>div.nodeSettingRight>div.refer>p.device:eq(0) span:last")
						.text(id);
				$("#nodeSetting>div.nodeSettingRight>div.refer>p.device:eq(1) span:last")
						.text(type);
				$("#nodeSetting>div.nodeSettingRight>div.refer>p.device:eq(2) span:last")
						.text(version);
			},
			referSigThreshold: function(id, routerId, result) {
				drawingReadParam.arround.referSigThreshold(id,routerId, result);
				// 請求獲取參考點信號強度系數
				devices.self.setting.arround.read(id, routerId,
						"ReadArroundReferSigKUdpPack", 
						passSettingSelf.arround.referSigK);
			},
			referSigK: function(id, routerId, result){
				drawingReadParam.arround.referSigK(id,routerId, result);
				
				devices.self.setting.arround.read(id, routerId,
						"ReadArroundBetweenSigUdpPack",
						passSettingSelf.arround.betweenReferNodeSig);
			},
			betweenReferNodeSig: function(id,routerId, result) {
				drawingReadParam.arround.betweenReferNodeSig(id, routerId, result);
			},
			nodeServerIp: function(id, routerId, channel, result){
				drawingReadParam.arround.nodeServerIp(id, routerId, channel, result);
				// 讀取nodeServerPort
				devices.self.setting.arround.readNode(id, routerId, channel,
						"ReadArroundNodeServerPortUdpPack",
						passSettingSelf.arround.nodeServerPort);
			},
			nodeServerPort: function(id, routerId, channel, result) {
				drawingReadParam.arround.nodeServerPort(id,  routerId, channel, result);
				// 需要我們判斷當前的設備類型是Lan還是Wifi
				// 讀取IP模式
				if($("#nodeSetting").attr("isWifi") == 'true') {
					devices.self.setting.arround.readNode(id, routerId, channel,
							"ReadArroundNodeWifiNameUdpPack",
							passSettingSelf.arround.nodeWifiName);
				} else {
					devices.self.setting.arround.readNode(id, routerId, channel,
							"ReadArroundNodeLanIpModeUdpPack",
							passSettingSelf.arround.nodeLanIpMode);
				}
			},
			nodeWifiName: function(id, routerId, channel, result) {
				drawingReadParam.arround.nodeWifiName(id, routerId, channel, result);
				// 讀取Wifi密碼
				devices.self.setting.arround.readNode(id, routerId, channel,
						"ReadArroundNodeWifiPswUdpPack",
						passSettingSelf.arround.nodeWifiPsw);
			},
			nodeWifiPsw: function(id, routerId, channel, result){
				drawingReadParam.arround.nodeWifiPsw(id, routerId, channel, result);
				// 查詢Wifi的Ip模式
				devices.self.setting.arround.readNode(id, routerId, channel,
						"ReadArroundNodeWifiIpModeUdpPack",
						passSettingSelf.arround.nodeWifiIpModel);
			},
			nodeWifiIpModel: function(id, routerId, channel, result) {
				drawingReadParam.arround.nodeLanIpMode(id, routerId, channel, result);
				// 查詢Wifi靜態Ip
				devices.self.setting.arround.readNode(id, routerId, channel,
						"ReadArroundNodeWifiStaticIpUdpPack",
						passSettingSelf.arround.nodeWifiStaticIp);
			},
			nodeWifiStaticIp: function(id, routerId, channel, result) {
				drawingReadParam.arround.nodeLanStaticIp(id, routerId, channel, result);
				// 查詢Wifi最後壹次的連接狀態
				devices.self.setting.arround.readNode(id, routerId, channel,
						"ReadArroundNodeWifiLastState",
						passSettingSelf.arround.nodeWifiLastState);
			},
			nodeWifiLastState: function(id, routerId, channel, result) {
				drawingReadParam.arround.nodeLastState(id, routerId, channel, result);
			},
			nodeLanIpMode: function(id, routerId, channel, result) {
				drawingReadParam.arround.nodeLanIpMode(id, routerId, channel, result);
				// 讀取掩碼
				devices.self.setting.arround.readNode(id, routerId, channel,
						"ReadArroundNodeMaskUdpPack",
						passSettingSelf.arround.nodeLanMask);
			},
			nodeLanMask: function(id, routerId, channel, result){
				drawingReadParam.arround.nodeLanMask(id, routerId, channel, result);
				
				// 讀取網關
				devices.self.setting.arround.readNode(id, routerId, channel,
						"ReadArroundNodeGateWayUdpPack",
						passSettingSelf.arround.nodeGateWay);
			},
			nodeGateWay: function(id, routerId, channel, result){
				drawingReadParam.arround.nodeGateWay(id, routerId, channel, result);
				
				devices.self.setting.arround.readNode(id, routerId, channel,
						"ReadArroundNodeLanStaticIpUdpPack",
						passSettingSelf.arround.nodeLanStaticIp);
			},
			nodeLanStaticIp: function(id, routerId, channel, result){
				drawingReadParam.arround.nodeLanStaticIp(id, routerId, channel, result);
				// 讀取最後壹次連接狀態
				devices.self.setting.arround.readNode(id, routerId,channel,
						"ReadArroundNodeWifiLastState", 
						drawingReadParam.arround.nodeLastState);
			},
			nodeLastState: function(id, routerId, channel, result){
				drawingReadParam.arround.nodeLastState(id,  routerId, channel, result);
			},
			initEvent: function() {
				var nodeId = $("#nodeSetting").attr("nodeId");
				// 綁定搜索按鈕
				$("#searchDevices").off("click").click(function(){
					$("#settingSelfNode").removeClass("disableSetting").addClass("disableSetting");
					$("#searchDevices").removeClass("disableSetting");
					
					var index = $("select[name='deviceType'] option:selected").index();
					$("#searchDevicesTable>tbody").empty();
					if(index) {
						// 需要我們對表格做壹個標記
						devices.self.setting.arround.search(nodeId,
								"SearchArroundRefersUdpPack",
								drawingReadParam.arround.search);
					} else {
						devices.self.setting.arround.search(nodeId, 
								"SearchArroundNodesUdpPack", 
								drawingReadParam.arround.search);
					}
				}) 
				// 綁定設置自身的按鈕
				$("#settingSelfNode").off("click").click(function(){
					$("#settingSelfNode").removeClass("disableSetting");
					$("#searchDevices").removeClass("disableSetting").addClass("disableSetting");
					// 1. 顯示右側設置自身的容器
					$("#nodeSetting>div.nodeSettingRight>div").hide();
					$("#currentNodeSetting").show();
					// 2. 讀取相關參數
					var nodeId = $("#nodeSetting").attr("nodeid");
					// 清空狀態標誌
					$("#currentNodeSetting i.hint").text("");
					$("#currentNodeSetting input").val("");
					$("#currentNodeSetting>p.device:eq(0)>span:last").text("");
					$("#currentNodeSetting>p.device:eq(1)>span:last").text("");
					$("#currentNodeSetting>p.device:eq(2)>span:last").text("");
					// 讀取自身訊息
					devices.self.setting.self.read( nodeId, 
							"ReadDeviceTypeVersionUdpPack", 
							devices.node.drawing.node.typeVersion);
					// 3. 綁定按鈕事件
					devices.node.drawing.node.initEvent();
				})
			}
		}
	}
	return passSettingSelf;
}()