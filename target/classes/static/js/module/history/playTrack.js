/**
 * 播放卡片轨迹
 */
function playTrace(iStart, iEnd, iPlayType, iTagId, drawingCallBack, drawingTime, drawingSpeed) {
	var fiveMinute = 300000;
	/* 监听播放状态、开始时间、结束时间、当前播放的时间、
	 * 播放类型(单点播放/全部播放)、卡片ID
	 */
	var state, speed, start, end, current,
		playType, tagId;
	// 缓存数组、缓存中最大的数据量、缓存中最小的数据量
	var cacheObj = {
		data:[],
		maxSize: 1000,
		current: null,
		setCurrent: function(time) {
			this.current = time;
		},
		getCurrent: function() {
			return this.current;
		},
		check: function(end) {
			// 1. 首先判断缓存是否达到最大值
			if(this.data.length >= this.maxSize) {
				return false;
			}
			if(this.current > end.getTime()) {
				return false;
			}
			return true;
		},
		add: function(datas) {
			// 将新获取到的缓存数据添加到缓存末尾
			this.data =  this.data.concat(datas);
		},
		clear: function() {
			this.data = [];
		},
		cahcheData: function(current) {// 获取缓存数据
			var array = [];
			for (var i = 0; i < this.data.length; i++) {
				if(null != this.data[i]) {
					var reportTick = (new Date(this.data[i]['reportTime'])).getTime();
					if(reportTick == current) {
						array.push( this.data.splice(i,1)[0] );
						i --;
					} else if(reportTick < current) {// 应该将当前数据踢出
						this.data.splice(i,1);
						i --;
					}
				} 
			}
			return array;
		}
	}
	var stateVal = {
		Play : 1,
		Pause : 2,
		Stop : 3
	}
	var speedVal = {
		One: 2000,
		Two: 1500,
		Three: 1000,
		Four: 500,
		Five: 100,
		Six: 50,
		Seven: 20,
		speedDown: function(val){
			switch (val) {
			case this.One:
				return this.One;
			case this.Two:
				return this.One;
			case this.Three:
				return this.Two;
			case this.Four:
				return this.Three;
			case this.Five:
				return this.Four;
			case this.Six:
				return this.Five;
			case this.Seven:
				return this.Six;
			}
		},
		speedUp: function(val){
			switch (val) {
			case this.One:
				return this.Two;
			case this.Two:
				return this.Three;
			case this.Three:
				return this.Four;
			case this.Four:
				return this.Five;
			case this.Five:
				return this.Six;
			case this.Six:
				return this.Seven;
			case this.Seven:
				return this.Seven;
			}
		},
		getLevel: function(val){
			switch(val){
			case this.One:
				return 0.1;
			case this.Two:
				return 0.5;
			case this.Three:
				return 1;
			case this.Four:
				return 1.5;
			case this.Five:
				return 2;
			case this.Six:
				return 2.5;
			case this.Seven:
				return 3;
			}
		}
	}
	var detory = function() {
		state = null;
		speed = null;
		start = null;
		end = null;
		playType = null;
		tagId = null;
		current = null;
	}
	var init = function() {
		state = stateVal.Play;
		speed = speedVal.Three;
		start = new Date(iStart);
		end = new Date(iEnd);
		// 清空缓存
		cacheObj.clear();
		playType = iPlayType;
		tagId = iTagId;
		// 设置缓存的时间戳
		cacheObj.setCurrent(start.getTime());
		current = start.getTime();
	}
	var requestCallBack = function(data){
		// 向缓存中添加数据
		cacheObj.add(data);
		// 设置缓存下次请求的数据的时间
		cacheObj.setCurrent(cacheObj.getCurrent() + fiveMinute);
	}
	// 请求获取轨迹资料
	var requestTrace = function(playType, tagId, current, callBack) {
		var cacheCurrent = (new Date(current)).format("yyyy-MM-dd hh:mm:ss");
		$.ajax({
			type : "GET",
			url : "graph/track/" + playType +"/" + tagId +"/" + cacheCurrent,
			dataType : "JSON",
			success : function(result) {
				if (result.code == 600) { // 向缓存中添加数据
					if(null != result.data) {
						callBack(result.data);
					}
				} else {
					console.log(result.msg);
				}
			}, error : function(result) {
				console.log("获取轨迹数据【start: " + start + ", end: " + end
						+ ", playType: " + playType + ", tagId: " + tagId
						+ "】出现异常...");
			}
		})
	}
	var playTimer = function() {// 播放延时器
		/*1. 获取网络数据*/
		// 判断缓存时间是否超过结束时间
		if (cacheObj.check(end)) {	
			requestTrace(playType, tagId, cacheObj.current, requestCallBack);
		}
		/* 2. 渲染历史数据界面  */
		drawingCallBack(cacheObj.cahcheData(current));
		/* 3.当前的时间戳时间 + 1 */
		current += 1000;
		// 4. 显示当前播放的时间
		drawingTime((new Date(current)).format("yyyy-MM-dd hh:mm:ss"), end.format("yyyy-MM-dd hh:mm:ss"));
		drawingSpeed(speedVal.getLevel(speed));
	}
	var timerAction = function(ms) {
		setTimeout(function() {
			if (state == stateVal.Stop || state == null || 
				current >=	end.getTime()) {
				// 关闭定时器、触发关闭的标记
				detory();
				return;
			} else if (state == stateVal.Pause) {
				// 状态值处在暂停状态
				return;
			} else if (state == stateVal.Play) {
				// 状态值处在播放状态
				playTimer();
			} else {
				// 状态值处在未知的状态
			}
			timerAction(speed);
		}, ms);
	}
	var player = {
		start : function() {// 开始
			// 设置播放状态
			state = stateVal.Play;
			// 开启定时器进行播放
			timerAction(speed);
			return {'code': 600};
		},
		pause : function() {// 暂停
			// 设置播放状态
			state = stateVal.Pause;
			return {'code': 600, 
					'val': state};
		},
		stop : function() {// 停止
			state = stateVal.Stop;
			return {
				'code' : 600
			};
		},
		speedUp : function() {// 加速
			var current = speedVal.speedUp(speed);
			if(current == speed) {
				return {
					'code' : 601,
					'val' : "已經達到最大速度"
				};
			} else {
				speed = current;
				return {
					'code' : 600,
					'val' : speed
				};
			}
		},
		speedDown : function() {// 减速
			var current = speedVal.speedDown(speed);
			if(current == speed) {
				return {
					'code' : 601,
					'val' : "已經達到最小速度"
				};
			} else {
				speed = current;
				return {
					'code' : 600,
					'val' : speed
				};
			}
		},
	}
	// 开始时进行初始化
	init();
	return player;
}
function getRegionCache(data){
	var regionCache = {
	};
	for (var i = 0; i < data.length; i++) {
		regionCache[data[i]['id']] = {};
	}
	return regionCache;
}

