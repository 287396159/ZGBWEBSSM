package com.dmatek.zgb.file.update;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="temp")
public class TempUploadProperties extends BaseFileUploadProperties {

}
