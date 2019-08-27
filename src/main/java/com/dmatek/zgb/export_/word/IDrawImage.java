package com.dmatek.zgb.export_.word;

import java.awt.Image;
/**   
 * @Title: IDrawImage.java 
 * @Description: TODO
 * @author zhangfu  
 * @date 2019年8月16日 下午3:16:32 
 * @version V1.0   
 *  画图并保存到文件中
 */
public interface IDrawImage<T> {
	Image draw(T data) throws Exception;
}
