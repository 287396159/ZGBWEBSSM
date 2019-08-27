package com.dmatek.zgb.export_.word;

import java.io.File;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
/**   
 * @Title: WordExport.java
 * @Description: TODO
 * @author zhangfu
 * @date 2019年8月16日 上午10:53:03
 * @version V1.0
 *   生成需要的word名称
 */
public class WordExport implements IWordExport {
	private String wordDir;
	private BaseResultFactory<Result> viewResultFactory;
	public WordExport(String wordDir, BaseResultFactory<Result> viewResultFactory) {
		this.wordDir = wordDir;
		this.viewResultFactory = viewResultFactory;
	}
	@Override
	public Result createWord(String wordName, Image[] images) {
		// 生成.word的文档
		CustomXWPFDocument document = new CustomXWPFDocument();
		// 声明输出流
		OutputStream stream = null;
		// 声明缓冲流
		// 当word文件目录不存在时, 我们应该删除这个目录
		File dir = new File(wordDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String wordPath = wordDir + "\\" + wordName;
		try {
			stream = new FileOutputStream(new File(wordPath));
			// 创建一个段落
			XWPFParagraph p1 = document.createParagraph();
			// 该段落左对齐
			p1.setAlignment(ParagraphAlignment.LEFT);
			InputStream in = null;
			// 开始将Image对象添加到Word文档中从而实现对应的功能
			for (Image image : images) {
				// Byte 数组输出流
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				ImageOutputStream imageOutStream = ImageIO
						.createImageOutputStream(output);
				// 此時已經將圖片訊息寫入到imageOutStream中
				ImageIO.write((RenderedImage) image, "png", imageOutStream);
				in = new ByteArrayInputStream(output.toByteArray());
				XWPFRun r = p1.createRun();
				CTInline ctinline = r.getCTR().addNewDrawing().addNewInline();
				String id = document.addPictureData(in,
						XWPFDocument.PICTURE_TYPE_PNG);
				int id2 = document.getAllPackagePictures().size() + 1;
				document.createPic(id, id2, 270, 173, ctinline);
				// 加上" "让资料卡相互隔开
				r.setText(" ");
				in.close();
				imageOutStream.close();
				output.close();
			}
			document.write(stream);
			stream.close();
		} catch (Exception e) {
			return viewResultFactory.errorResult("生成卡片資料卡Word文檔時出現異常："
					+ e.getMessage());
		} finally {
			try {
				if (null != stream) {
					stream.close();
				}
			} catch (IOException e) {
			}
			try {
				if (null != document) {
					document.close();
				}
			} catch (IOException e) {

			}
		}
		// 带上下载的路径
		return viewResultFactory.successResult(wordName);
	}
	public String getWordDir() {
		return wordDir;
	}
	public void setWordDir(String wordDir) {
		this.wordDir = wordDir;
	}
	@Override
	public void downloadWord(HttpServletResponse res, String name)
			throws Exception {
		OutputStream wordOut = null;
		res.addHeader("content-disposition", "attachment;filename="
				+ java.net.URLEncoder.encode("人員資料卡.doc", "utf-8"));
		wordOut = res.getOutputStream();
		InputStream wordIs = new FileInputStream(wordDir + "\\" + name);
		byte[] b = new byte[4096];
		int size = wordIs.read(b);
		while (size > 0) {
			wordOut.write(b, 0, size);
			size = wordIs.read(b);
		}
		// 每次傳輸完成以後, 我們都應該
		wordOut.close();
		wordIs.close();
		// 需要我們刪除當前的Word文件
		deleteWord(wordDir + "\\" + name);
	}
	/****
	 * 之所以需要刪除當前的Word, 原因是我們的Word不能让它长久保存到磁盘上, 
	 * 这样会占用内存, 删除后，下次要使用时大不了在生成一次Word, 反正这种
	 * 操作不是很频繁, 不会对执行效率有很大的影响
	 * @param wordPath
	 */
	private void deleteWord(String wordPath) {
		File file = new File(wordPath);
		if(file.exists() && file.isFile()) {
			file.delete();
		}
	}
}
