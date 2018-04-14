package control.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import util.CompressPic;
import util.GoodsUtil;
import util.WordUtil;
import frame.SingleFactory;
public class UpLoadGoodsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2367087739212794084L;

	/**
	 * Constructor of the object.
	 */
	public UpLoadGoodsServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);//TODO 要删除
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Object fromWhere = request.getAttribute("from");

		if (fromWhere != null && fromWhere.toString().equals("ios")) {
			response.setContentType("text/json;charset=UTF-8");
		} else {
			response.setContentType("text/html;charset=UTF-8");
		}
		
		String province = "";
		String city = "";
		String district = "";
		String cat = "";
		String gName = "";
		int totalCount = 0;
		int onecCount = 1;
		String second_name = "";
		long open_time = System.currentTimeMillis();
		long gid = 0;
		int stock = 0;
		int catelog_id = 0;
		List<FileItem> tempList = new ArrayList<FileItem>();
		
		//获取图片目录
		String path = this.getClass().getClassLoader().getResource("/").getPath();
		String imgPath = path.replace("/build/classes", "").replace("%20", " ").replace("/WEB-INF/classes", "").replaceFirst("/", "") + "img";
		//判断目录是否为空 为空则创建目录
		File file = new File(imgPath);
		if(!file.exists() && !file.isDirectory()){
			file.mkdir();
		}
		//创建DiskFileItemFactory工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//创建文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		//判断是否是普通提交方式的数据
		if(!ServletFileUpload.isMultipartContent(request)){
			return;
			
		}
		try{
			
			List<FileItem> list = upload.parseRequest(request);
			for(FileItem item:list){
				if(item.isFormField()){
					String name = item.getFieldName();
					switch(name){
					case "province":
						province = item.getString("UTF-8");
						break;
					case "city":
						city = item.getString("UTF-8");
						break;
					case "district":
						district = item.getString("UTF-8");
						break;
					case "cat":
						cat = item.getString("UTF-8");
						break;
					case "gName":
						gName = item.getString("UTF-8");
						break;
					case "totalCount":
						totalCount = item.getString("UTF-8")!=""?Integer.valueOf(item.getString("UTF-8")):0;
						break;
					case "onecCount":
						onecCount = item.getString("UTF-8")!=""?Integer.valueOf(item.getString("UTF-8")):1;
						break;
					case "second_name":
						second_name =item.getString("UTF-8");
						break;
					case "gid":
						String strgid = item.getString("UTF-8");
						if(!(strgid.equals(""))){
							gid = Long.valueOf(item.getString("UTF-8"));
						}
						break;
					case "stock":
						stock =item.getString("UTF-8")!=""?Integer.valueOf(item.getString("UTF-8")):0;
						break;
					case "catelog_id":
						catelog_id = item.getString("UTF-8")!=""?Integer.valueOf(item.getString("UTF-8")):0;
						break;
						
					}
				}
				else{
					tempList.add(item);
				}
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		if(gid == 0){
			gid = WordUtil.getId();
		}
		String proImgDir = "pro_"+gid;
		
		List<String> dis_img_list = new ArrayList<String>();
		List<String> detail_img_list = new ArrayList<String>();
		for(FileItem item:tempList){
			String proImgPath = imgPath+"/"+proImgDir+"/";
			
			//创建图片文件目录
			File f = new File(proImgPath);
			if(!f.exists()){
				f.mkdirs();
			}
			
			String filename = item.getName().trim();
			if(filename == null || filename.trim().equals("")){
				continue;
			}
			String name = item.getFieldName();
			
			
			InputStream in = item.getInputStream();
			FileOutputStream out = new FileOutputStream(proImgPath+filename);
			byte buffer[] = new byte[1024];
			//判断图片数据是否已经读完
			int len = 0;
			//将图片流写入缓冲区
			while((len = in.read(buffer))>0){
				out.write(buffer,0,len);
			}
			in.close();
			out.close();
			item.delete();
			
			if(name.equals("dis_img")){
				dis_img_list.add(filename);
				CompressPic cp = new CompressPic();
				cp.compressPic(proImgPath, proImgPath, filename, filename.substring(0,filename.indexOf("."))+"_compress"+filename.substring(filename.indexOf(".")), 200, 200);
			}
			else if(name.equals("detail_img")){
				detail_img_list.add(filename);
			}
			
		}
		
		Gson gson = new Gson();
		
		request.setAttribute("beloneId", "yy");
		request.setAttribute("province", province);
		request.setAttribute("city", city);
		request.setAttribute("district", district);
		request.setAttribute("cat", cat);
		request.setAttribute("gName", gName);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("onecCount", onecCount);
		request.setAttribute("second_name", second_name);
		request.setAttribute("open_time", open_time);
		request.setAttribute("gid", gid);
		request.setAttribute("goods_img", "img/"+proImgDir);
		request.setAttribute("status", 1);
		request.setAttribute("stock", stock);
		request.setAttribute("catelog_id", catelog_id);
		request.setAttribute("dis_imgs", gson.toJson(dis_img_list));
		request.setAttribute("detail_imgs", gson.toJson(detail_img_list));
		

		
		 SingleFactory.ins().getGoodsIns().updateGoods(request);
		 SingleFactory.ins().getGoodsIns().updateCodeOrder(Long.valueOf(gid));

//		SingleFactory.ins().getNumberIns().createNumber("beijign_YY_1", 9999);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
