package com.dmatek.zgb.export_.word;

import java.awt.Image;
import javax.servlet.http.HttpServletResponse;
import com.dmatek.zgb.setting.vo.Result;
/**   
 * @Title: IWordExport.java 
 * @Description: TODO
 * @author zhangfu  
 * @date 2019年8月16日 上午10:37:46 
 * @version V1.0   
 *  生成Word操作文檔
 */
public interface IWordExport {
	/***
	 * 生成Word文档
	 * @param wordName
	 * @param img
	 * @return
	 */
	Result createWord(String wordName, Image[] img);
	/***
	 * 下载Word文档
	 * @param res
	 * @param name
	 * @throws Exception
	 */
	void downloadWord(HttpServletResponse res, String name) throws Exception;
}
