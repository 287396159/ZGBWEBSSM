/***
 * 数值设置指定长度显示，不足就补零
 * @param num
 * @param length
 * @returns
 */
function PrefixInteger(num, length) {
	return ( "0000000000000000" + num ).substr( -length );
}
/**
 * 获取字符串的字节长度
 * @param val
 * @returns
 */
function getLength(val) {  
    var str = new String(val);  
    var bytesCount = 0;  
    for (var i = 0 ,n = str.length; i < n; i++) {  
        var c = str.charCodeAt(i);  
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
            bytesCount += 1;  
        } else {  
            bytesCount += 2;  
        }  
    }  
    return bytesCount;  
}
/***
 * 截取字符串的最大长度
 * @param str
 * @param max
 * @returns
 */
function getMaxLen(str, max) {
	if(getLength(str) > max) {
		var totalLen = 0, size = 0;
		for (var i = 0; i < str.length; i++) {
			var val = str.charAt(i);
			var size = getLength(val);
			totalLen += size;
			if(totalLen > max) {
				size = i > 1?i - 1:i;
				break;
			}
		}
		return str.substr(0, size <= 0 ? max : size);
	}
	return str;
}