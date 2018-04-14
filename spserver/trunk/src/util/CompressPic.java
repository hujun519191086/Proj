package util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CompressPic {
	/*******************************************************************************
	 * 缩略图类（通用） 本java类能将jpg、bmp、png、gif图片文件，按指定宽高进行大小转换。 具体使用方法
	 * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度)
	 */
	private File file = null; // 文件对象
	private String inputDir; // 输入图路径
	private String outputDir; // 输出图路径
	private String inputFileName; // 输入图文件名
	private String outputFileName; // 输出图文件名
	private int outputWidth = 100; // 默认输出图片宽
	private int outputHeight = 100; // 默认输出图片高
	
	public CompressPic() { // 初始化变量
		inputDir = "";
		outputDir = "";
		inputFileName = "";
		outputFileName = "";
		outputWidth = 100;
		outputHeight = 100;
	}
	public void setInputDir(String inputDir) {
		this.inputDir = inputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public void setWidthAndHeight(int width, int height) {
		this.outputWidth = width;
		this.outputHeight = height;
	}
	
	// 图片处理
		public String compressPic() {
			try {
				// 获得源文件
				file = new File(inputDir+inputFileName);
				//System.out.println(inputDir + inputFileName);
				if (!file.exists()) {
					//throw new Exception("文件不存在");
				}
				Image img = ImageIO.read(file);
				// 判断图片格式是否正确
				if (img.getWidth(null) == -1) {
					System.out.println(" can't read,retry!" + "<BR>");
					return "no";
				} else {
					int newWidth;
					int newHeight;
					newWidth = outputWidth; // 输出的图片宽度
					newHeight = outputHeight; // 输出的图片高度
		
					BufferedImage tag = new BufferedImage((int) newWidth,
							(int) newHeight, BufferedImage.TYPE_INT_RGB);

					/*
					 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
					 */
					tag.getGraphics().drawImage(
							img.getScaledInstance(newWidth, newHeight,
									Image.SCALE_SMOOTH), 0, 0, null);
					File f=new File(outputDir);
					if (!f.exists()){
						f.mkdirs(); 
					}
					FileOutputStream out = new FileOutputStream(outputDir
							+ outputFileName);
					// JPEGImageEncoder可适用于其他图片类型的转换
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
					encoder.encode(tag);
					out.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return "ok";
		}
		public String compressPic(String inputDir, String outputDir,
				String inputFileName, String outputFileName, int width, int height) {
			// 输入图路径
			this.inputDir = inputDir;
			// 输出图路径
			this.outputDir = outputDir;
			// 输入图文件名
			this.inputFileName = inputFileName;
			// 输出图文件名
			this.outputFileName = outputFileName;
			// 设置图片长宽
			setWidthAndHeight(width, height);
			return compressPic();
		}
}
