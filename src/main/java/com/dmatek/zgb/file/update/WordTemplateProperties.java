package com.dmatek.zgb.file.update;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**   
 * @Title: WordTemplate.java 
 * @Description: TODO
 * @author zhangfu  
 * @date 2019年8月16日 下午4:37:13 
 * @version V1.0   
 */
@Component
@ConfigurationProperties(prefix="card.template")
public class WordTemplateProperties extends BaseFileUploadProperties {

}
