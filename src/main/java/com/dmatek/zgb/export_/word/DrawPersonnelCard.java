package com.dmatek.zgb.export_.word;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import org.thymeleaf.util.StringUtils;

import sun.awt.SunHints;

import com.dmatek.zgb.db.pojo.person.Person;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**   
 * @Title: DrawPersonnelCard.java 
 * @Description: TODO
 * @author zhangfu  
 * @date 2019年8月16日 下午3:17:37 
 * @version V1.0   
 *  制作人员资料卡片
 */
public class DrawPersonnelCard implements IDrawImage<Person> {
	private final int IMAGE_TYPE = BufferedImage.TYPE_3BYTE_BGR;
	// 默认卡片的宽度、高度
	private final int DEFAULT_WIDTH = 260;
	private final int DEFAULT_HEIGHT = 170;
	// 二维码的宽度、高度
	private final int QRCODE_WIDTH = 58;
	private final int QRCODE_HEIGHT = 58;
	// 头像宽度、高度
	private final int FACE_WIDTH = 77;
	private final int FACE_HEIGHT = 96;
	// 默认的头像资源, 当没有图像讯息时, 我们应该用默认的头像讯息替代
	private String defaultName, faceDir, cardDir;
	public DrawPersonnelCard(String cardDir, String defaultName, String faceDir) {
		this.cardDir = cardDir;
		this.defaultName = defaultName;
		this.faceDir = faceDir;
	}
	@Override
	public Image draw(Person data) throws Exception {
		BufferedImage image = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT,
				IMAGE_TYPE);
		image = image.createGraphics().getDeviceConfiguration()
				.createCompatibleImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, Transparency.TRANSLUCENT);
		Graphics2D g = (Graphics2D) image.getGraphics();
		// KEY_ANTIALIASING 图形抗锯齿键值
		g.setRenderingHint(SunHints.KEY_ANTIALIASING, SunHints.VALUE_ANTIALIAS_OFF);
	    // KEY_TEXT_ANTIALIASING 文字抗锯齿键值
		g.setRenderingHint(SunHints.KEY_TEXT_ANTIALIASING, SunHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
	    // KEY_STROKE_CONTROL 笔画规范化控制提示键
		g.setRenderingHint(SunHints.KEY_STROKE_CONTROL, SunHints.VALUE_STROKE_DEFAULT);
	    //  KEY_TEXT_ANTIALIAS_LCD_CONTRAST 文字锯齿LCD对比呈现提示键
		g.setRenderingHint(SunHints.KEY_TEXT_ANTIALIAS_LCD_CONTRAST, 140);
	    // KEY_FRACTIONALMETRICS 文字小数规格提示键
		g.setRenderingHint(SunHints.KEY_FRACTIONALMETRICS, SunHints.VALUE_FRACTIONALMETRICS_OFF);
	    // 呈现提示键
		g.setRenderingHint(SunHints.KEY_RENDERING, SunHints.VALUE_RENDER_DEFAULT);
		g.setColor(new Color(175, 238, 238));
		g.fillRoundRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, 15, 18);
		g.setColor(Color.BLACK);
		String imagePath = data.getImgPath();
		Image faceImage = null;
		if (StringUtils.isEmpty(imagePath)) {
			faceImage = getPersonFace(faceDir + "\\" + defaultName);
		} else {
			faceImage = getPersonFace(faceDir + "\\" + imagePath);
		}
		if (null != faceImage) { // 头像的位置坐标, 头像开始位置 (10px, 10px) 的位置
			g.drawImage(faceImage.getScaledInstance(FACE_WIDTH, FACE_HEIGHT, Image.SCALE_SMOOTH), 10, 10, FACE_WIDTH, FACE_HEIGHT, null);
		}
		// 姓名讯息
		g.setFont(new Font("宋体", Font.PLAIN, 14));
		if (!StringUtils.isEmpty(data.getName())) {
			g.drawString("姓名：" + getMaxStr(data.getName(), 7), 99, 30);
		} else {
			g.drawString("姓名：****", 99, 30);
		}
		// 画血型讯息
		if (StringUtils.isEmpty(data.getBloodType())) {
			g.drawString("血型：", 190, 30);
		} else {
			g.drawString("血型：" + getMaxStr(data.getBloodType(), 6), 190, 30);
		}
		// 画工种
		if(StringUtils.isEmpty(data.getJobType_Name())) {
			g.drawString("工種：****", 99, 65);
		} else {
			String sJobType = StringUtils.isEmpty(data.getJobType_Name()) ? data
					.getJobType_No() : data.getJobType_Name();
			g.drawString("工種：" + getMaxStr(sJobType, 15), 99, 65);
		}
		// 画识别证讯息
		g.setFont(new Font("宋体", Font.BOLD, 18));
		// 設置
		if(!StringUtils.isEmpty(data.getGroup_Name())) {
			drawGroupName(g, data.getGroup_Name(), 126, 100);
		} else { 
			// 此时没有指定组别, 我们不能生成
			return null;
		}
		// 字体
		g.setFont(new Font("宋体", Font.PLAIN, 14));
		// 画二维码
		String serialNumber = String.format("%06d", data.getSerialNumber());
		Image qrCode = getQrCode(serialNumber);
		if (null != qrCode) {
			g.drawImage(qrCode.getScaledInstance(QRCODE_WIDTH, QRCODE_HEIGHT, Image.SCALE_SMOOTH), 
					19, 110, QRCODE_WIDTH, QRCODE_HEIGHT, null);
		}
		// 画编号
		g.drawString("編號：" + serialNumber, 99, 143);
		return image;
	}

	private Image getPersonFace(String filePath) throws IOException {
		BufferedImage faceImage = new BufferedImage(FACE_WIDTH, FACE_HEIGHT,
				IMAGE_TYPE);
		// 设置透明度
		faceImage = faceImage.createGraphics().getDeviceConfiguration()
				.createCompatibleImage(FACE_WIDTH, FACE_HEIGHT, Transparency.TRANSLUCENT);
		
		Graphics g = faceImage.getGraphics();
		g.fillRoundRect(0, 0, FACE_WIDTH, FACE_HEIGHT, 18, 18);
		// 将默认的头像画到页面上
		File faceFile = StringUtils.isEmpty(filePath) ? null : new File(
				filePath);
		if (null != faceFile && faceFile.exists()) {
			Image image = ImageIO.read(faceFile);
			g.drawImage(image, 0, 0, FACE_WIDTH, FACE_HEIGHT, null);
		}
		return faceImage;
	}
	/***
	 * 生成二维码图片
	 * @param serialNumber
	 * @return
	 * @throws IOException
	 * @throws WriterException
	 */
	private Image getQrCode(String serialNumber) throws IOException, WriterException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(serialNumber,
				BarcodeFormat.QR_CODE, QRCODE_WIDTH, QRCODE_HEIGHT);
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}
	/***
	 * 當卡片資料卡目录不存在時，我們需要創建資料卡目录
	 * @param dirPath
	 */
	@SuppressWarnings("unused")
	private void createCardDir(String dirPath) {
		File dir = new File(dirPath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
	}
	/***
	 * 画组别名称
	 * @param g
	 * @param title
	 * @param left
	 * @param top
	 *  组别长度最大 8 个, 大于8个以8个字符进行处理
	 */
	private void drawGroupName(Graphics2D g, String title, int iLeft, int iTop) {
		/*根据字体位置确定位置*/
		String top = "", bottom = "";
		switch (title.length()) {
		case 1: g.drawString(title, iLeft, iTop); g.drawString("識別證", 160, 100); break;
		case 2: g.drawString(title, iLeft, iTop); g.drawString("識別證", 160, 100); break;
		case 3: g.drawString(title, iLeft - 10, iTop); g.drawString("識別證", 175, 100); break;
		case 4: g.drawString(title, iLeft - 20, iTop); g.drawString("識別證", 188, 100); break;
		case 5:// 上面3个字， 下面2个字
			g.setFont(new Font(null, Font.BOLD, 12));
			String top1 = title.substring(0, 3);
			String bottom1 = title.substring(3, 5);
			g.drawString(top1, iLeft, iTop - 5);
			g.drawString(bottom1, iLeft + 7, iTop + 16);
			g.setFont(new Font(null, Font.BOLD, 18));
			g.drawString("識別證", 180, 108);
			break;
		case 6:
			g.setFont(new Font(null, Font.BOLD, 12));
			top = title.substring(0, 4);
			bottom = title.substring(4, 6);
			g.drawString(top, iLeft - 8, iTop - 5);
			g.drawString(bottom, iLeft + 5, iTop + 16);
			g.setFont(new Font(null, Font.BOLD, 18));
			g.drawString("識別證", 180, 108);
			break;
		case 7:
			g.setFont(new Font(null, Font.BOLD, 12));
			top = title.substring(0, 4);
			bottom = title.substring(4, 7);
			g.drawString(top, iLeft - 8, iTop - 5);
			g.drawString(bottom, iLeft - 1, iTop + 16);
			g.setFont(new Font(null, Font.BOLD, 18));
			g.drawString("識別證", 180, 108);
			break;
		default:
			// 最大8个，大于8个字符都默认8个进行画字符
			g.setFont(new Font(null, Font.BOLD, 12));
			top = title.substring(0, 4);
			bottom = title.substring(4, 8);
			g.drawString(top, iLeft - 4, iTop - 5);
			g.drawString(bottom, iLeft - 4, iTop + 16);
			g.setFont(new Font(null, Font.BOLD, 18));
			g.drawString("識別證", 180, 108);
			break;
		}
	}
	/***
	 * 截取字符串的最大长度
	 * @param str
	 * @param maxLen
	 * @return
	 */
	private String getMaxStr(String str, int maxLen) throws Exception {
		int size = str.getBytes("GBK").length;
		if(size <= maxLen) {
			return str;
		} else {
			return getMaxLargestByteStr(str, maxLen);
		}
	}
	private String getMaxLargestByteStr(String str, int maxSize) throws UnsupportedEncodingException {
		int totalSize = 0, len = 0, end = 0;
		for (int i = 0, size = str.length(); i < size; i++) {
			if (i + 1 >= size) {
				// 說明此時已經達到最大值
				end = i;
				break;
			}
			len = str.substring(i, i + 1).getBytes("GBK").length;
			totalSize += len;
			if (totalSize >= maxSize) {
				end = i;
				break;
			}
		}
		return str.substring(0, end);
	}
	
	
	public String getCardDir() {
		return cardDir;
	}
	public void setCardDir(String cardDir) {
		this.cardDir = cardDir;
	}
}
