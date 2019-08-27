package com.dmatek.zgb.dialect;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

@Component
public class CustomIPDialect extends AbstractProcessorDialect{
	private static final String PREFIX = "custom Dialect";
	private static final String ELEMENT_NAME = "custom";
	protected CustomIPDialect() {
		super(PREFIX, ELEMENT_NAME, StandardDialect.PROCESSOR_PRECEDENCE);
	}
	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new CustomIP_IProcessor(dialectPrefix));//添加我们定义的标签
        
        processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
        return processors;
	}
}
