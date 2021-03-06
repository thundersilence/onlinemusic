package com.action;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.UserDao;
import com.entity.User;
import com.model.AddRecord;
import com.model.CreateIcon;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		//编码标准UTF-8
		String username= request.getParameter("username");
		String password= request.getParameter("password");
		
		//读取用户名和密码
		User user=new User();
		UserDao UD=new UserDao();
		
		Random rand=new Random();
		int id;
		while(true) {
			id=rand.nextInt(999);
			if (UD.search(id).getId()==0)break;				//搜索不到已有id时返回一个id为0的user类
		}//随机生成id
		
		
		//String imagedir=request.getRequestURI()+"/store/imageStore/";
		String imagedir = getServletContext().getRealPath("sources/imageStore/");
		String imageurl = imagedir + id + ".jpg";
		String rltUrl = "imageStore/"+id + ".jpg";
		
		System.out.println("imagedir:"+ imagedir);
		System.out.println("imageurl:"+imageurl);
		
		
		CreateIcon.create(imagedir, imageurl);//生成头像并保存
		
		user.setId(id);
		user.setName(username);
		user.setPassword(password);
		user.setImg(rltUrl);
		
		UD.insert(user);	
		
		AddRecord.print("添加用户记录：" + id);
		System.out.println("注册成功");
		
		 request.setAttribute("regesterSuccess", "S");
		 request.getSession().setAttribute("id", id); //session 添加名称和id
		/*
		 * request.getSession().setAttribute("user", user);
		 * 
		 * request.getSession().setAttribute("image", imageurl);
		 */
		/* response.sendRedirect("sources/index.jsp"); */
		request.getRequestDispatcher("sources/login.jsp").forward(request, response);

	}
}
