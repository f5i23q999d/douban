package test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.project.mapper.Mapper;
import com.project.pojo.Detail;
import com.project.pojo.Hall;
import com.project.utils.SqlSessionFactoryUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class toOrder
 */
//@WebServlet("/toOrder")
public class toOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public toOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String phone=request.getParameter("phone");
			String seat=request.getParameter("seats");
			String accoutid=request.getParameter("accoutid");
			String sessionid=request.getParameter("sessionid");
			
			String seats[]=seat.split(" ");
			
			Date d1= new Date();
			SimpleDateFormat Fmt1=new SimpleDateFormat("yyyy-MM-dd");
		    SimpleDateFormat Fmt2=new SimpleDateFormat("HH:mm:ss");

			SqlSession sql=null;
			
			try{
			sql=SqlSessionFactoryUtils.openSqlSession();
			Mapper map = sql.getMapper(Mapper.class);
			
			Hall tmp=new Hall();
			Detail d=new Detail();
			d.setPhone(phone);
			d.setAccountId(accoutid);
			d.setPublishId(sessionid);
			d.setDate(Fmt1.format(d1));
			d.setTime(Fmt2.format(d1));
			
			for(int i=0;i<seats.length;i++)
			{
				tmp.setId(Integer.valueOf(sessionid));
				tmp.setSeat(seats[i]);
				d.setSeat(seats[i]);
				map.setSeat(tmp);
				map.toOrder(d);
			}
			
			
			}
			finally{
				if(sql!=null)
					sql.close();
				
			}
			
			
//			Connection conn=null;
//			Statement statement=null;
//			ResultSet rs=null;
//			try {
//				Class.forName("com.mysql.jdbc.Driver");
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			try {
//				conn=DriverManager.getConnection("jdbc:mysql://193.112.94.186:3306/Order?characterEncoding=utf8","jiang","junfeng");
//				//conn=DriverManager.getConnection("jdbc:mysql://123.207.117.122:3306/web","root","");
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			try {
//				PreparedStatement stat=conn.prepareStatement("insert into hall values(?,?)");
//				
//				for(int i=0;i<seats.length;i++)
//				{
//						stat.setString(1, sessionid);
//						stat.setString(2, seats[i]);
//						stat.executeUpdate();
//				}
//
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			

	}

}
