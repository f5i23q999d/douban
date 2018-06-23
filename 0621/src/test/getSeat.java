package test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.project.mapper.Mapper;
import com.project.pojo.Hall;
import com.project.pojo.Publish;
import com.project.utils.SqlSessionFactoryUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Go
 */
//@WebServlet("/getSeat")
public class getSeat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getSeat() {
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
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Logger log=Logger.getLogger(getSeat.class);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		String cinema=request.getParameter("cinema");
		String movie=request.getParameter("movie");
		String hall=""+request.getParameter("hall").charAt(0);
		String date=request.getParameter("date");
		String time=request.getParameter("time");
		
		
		Date d=null;
		date=date.replaceAll("今天","2018年");	
		SimpleDateFormat sd1=new SimpleDateFormat("yyyy年MM月dd日");  
		try {
			d=sd1.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sd2=new SimpleDateFormat("yyyy-MM-dd");
		//将date转化位标准格式
		date=sd2.format(d);
		//
		//System.out.println(date);
		
		SqlSession sql=null;
		
		try{
		sql=SqlSessionFactoryUtils.openSqlSession();
		Mapper map = sql.getMapper(Mapper.class);
		Publish p=new Publish();
		p.setMovie(movie);
		p.setCinema(cinema);
		p.setHall(hall);
		p.setDate(date);
		p.setTime(time);
		System.out.println(movie+" "+cinema+" "+hall+" "+date+" "+time);
		List<Hall> H=map.getSeat(p);
		System.out.println(H+"size:"+H.size());
		if(H.size()!=0)
			{
			JSONArray jsonarray = new JSONArray();  
			JSONObject jsonobj = new JSONObject(); 
			for(int i=0;i<H.size();i++)
			{
				jsonobj.put("seat",H.get(i).getSeat());
				jsonobj.put("id",H.get(i).getId());
				jsonarray.add(jsonobj);
			}
			response.getWriter().write(jsonarray.toString());
			}
		else{
			//若一开始为空
			List<Publish> P=map.findPublish(p);
			JSONArray jsonarray = new JSONArray();  
			JSONObject jsonobj = new JSONObject(); 
			
				jsonobj.put("seat","");
				jsonobj.put("id",P.get(0).getId());
				jsonarray.add(jsonobj);
			response.getWriter().write(jsonarray.toString());
			
			
		}
		
		}
			finally{
				if(sql!=null)
					sql.close();
				
			}
		}

		
	
		
}


