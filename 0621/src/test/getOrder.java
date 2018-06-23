package test;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.session.SqlSession;

import com.project.mapper.Mapper;
import com.project.pojo.Detail;
import com.project.pojo.Publish;
import com.project.utils.SqlSessionFactoryUtils;

/**
 * Servlet implementation class getOrder
 */
//@WebServlet("/getOrder")
public class getOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getOrder() {
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		String openid=request.getParameter("openid");
		System.out.println(openid);
		SqlSession sql=null;
		List<Detail> D;
		Publish P;
		
		
		JSONArray jsonarray = new JSONArray();  
		JSONObject jsonobj = new JSONObject(); 
		
		try{
		sql=SqlSessionFactoryUtils.openSqlSession();
		Mapper map = sql.getMapper(Mapper.class);
		//D=map.getDetail(openid);
		
		
		for(int i=0;i<D.size();i++)
			{
				
				P=map.findPublishById(Integer.valueOf(D.get(i).getPublishId()));
				jsonobj.put("movie", P.getMovie());
				jsonobj.put("price", P.getPrice());
				jsonobj.put("seat", D.get(i).getSeat());
				jsonarray.add(jsonobj);
			}
		
		}
		finally{
			if(sql!=null)
				sql.close();
		}
		
		response.getWriter().write(jsonarray.toString());
		
	}

}
