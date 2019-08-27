package com.dmatek.zgb.export_.word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.file.update.CardUploadProperties;
import com.dmatek.zgb.file.update.StaffUploadProperties;
import com.dmatek.zgb.file.update.WordTemplateProperties;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

/**   
 * @Title: WordConfig.java 
 * @Description: TODO
 * @author zhangfu  
 * @date 2019年8月16日 下午4:58:29 
 * @version V1.0   
 */
@Configuration
public class WordConfig {
	@Autowired
	private CardUploadProperties cardUploadProperties;
	@Autowired
	private StaffUploadProperties staffUploadProperties;
	@Autowired
	private WordTemplateProperties wordTemplate;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	// 默认的头像名称是"sysperson.jpg"
	private final String DEFAULT_FACE_NAME = "sysperson.jpg";
	@Bean
	public DrawPersonnelCard getDrawPersonnelCard() {	
		return new DrawPersonnelCard(cardUploadProperties.getUploadFolder(),
				DEFAULT_FACE_NAME, staffUploadProperties.getUploadFolder());
	}
	@Bean
	public WordExport getWordExport() {
		return new WordExport(wordTemplate.getUploadFolder(), viewResultFactory);
	}
}
