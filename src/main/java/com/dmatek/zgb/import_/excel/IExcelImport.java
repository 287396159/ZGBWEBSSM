package com.dmatek.zgb.import_.excel;

import org.springframework.web.multipart.MultipartFile;
import com.dmatek.zgb.setting.vo.Result;

public interface IExcelImport<T> {
	Result importExcel(MultipartFile file) throws Exception;
}
