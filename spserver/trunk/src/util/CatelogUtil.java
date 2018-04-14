package util;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import frame.SingleFactory;

public class CatelogUtil {

	public static void getCatelogs(){
		String path = SingleFactory.class.getResource("/").getPath();
		String a1 = System.getProperty("user.dir");
		File file = new File("");
		try{
			
			String a2 = file.getCanonicalPath();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		SAXReader reader = new SAXReader();
		try{
			Document document = reader.read(file);
			Element root = document.getRootElement();
			List<Element> catelogs = root.elements();
			for(Element catelog:catelogs){
				long CatelogId = Long.valueOf(catelog.attribute("catelogId").getText());
				String CatelogName = catelog.attribute("catelogName").getText();
				return;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
