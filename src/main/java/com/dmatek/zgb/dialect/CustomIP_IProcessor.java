package com.dmatek.zgb.dialect;

import java.util.List;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.tool.Inetaddress.InetAddressTools;

public class CustomIP_IProcessor extends AbstractElementTagProcessor {
	private Logger logger = Logger.getLogger(CustomIP_IProcessor.class);
	private static final int PRECEDENCE = 10000;//优先级
	private static final String TAG_NAME = "ip";
	public CustomIP_IProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
	}
	
	@Override
	protected void doProcess(ITemplateContext context,IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {
       String  paramIp= tag.getAttributeValue("param");
       final IModelFactory modelFactory = context.getModelFactory();
       final IModel model = modelFactory.createModel();
       model.add(modelFactory.createOpenElementTag("select"));
	   // 获取所有的IPv4讯息
	   List<String> localIps = null;
	   try {
			localIps = InetAddressTools.getAllLocalIps();
			paramIp = InetAddressTools.getLocalIp(paramIp);
	   } catch (SocketException e) {
			logger.error("获取本地所有Ipv4地址出现异常，" + e.toString());
	   } catch (UnknownHostException e) {
			logger.error("获取有效Ipv4地址出现异常，" + e.toString());
	   }
	   for (String string : localIps) {
			if(!StringUtils.isEmpty(paramIp) && paramIp.equalsIgnoreCase(string)){
				model.add(modelFactory.createText("<option selected='selected'  value='" + string + "'>" + string + "</option>"));
			} else {
				model.add(modelFactory.createText("<option value='" + string + "'>" + string + "</option>"));
			}
		}
	   model.add(modelFactory.createText("<option value='127.0.0.1'>127.0.0.1</option>"));
	   model.add(modelFactory.createCloseElementTag("select"));
	   structureHandler.replaceWith(model, false);
	}
}
