package com.mySpring;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.mapper.Mapper;
import com.project.pojo.Detail;
import com.project.pojo.Hall;
import com.project.pojo.Publish;
import com.project.utils.SqlSessionFactoryUtils;

@Controller
public class OrderController {
	@RequestMapping(value="/getOrder",produces = "application/json;charset=UTF-8")
	
	@ResponseBody
	public String getOrder(@RequestParam("openid") String openid)
	{	
		
	  
		System.out.println(openid);
		SqlSession sql=null;
		List<Detail> D;
		Publish P;
		
		
		JSONArray jsonarray = new JSONArray();  
		JSONArray used = new JSONArray();  //存放未使用的电影票
		JSONArray noused = new JSONArray();  //存放已使用的电影票
		JSONObject jsonobj = new JSONObject(); 
		
		try{
		sql=SqlSessionFactoryUtils.openSqlSession();
		Mapper map = sql.getMapper(Mapper.class);
		
		D=map.getOrder(openid);
		
		
		for(int i=0;i<D.size();i++)
			{
				
				P=map.findPublishById(Integer.valueOf(D.get(i).getPublishId()));
				jsonobj.put("id", D.get(i).getId());
				jsonobj.put("movie", P.getMovie());
				jsonobj.put("price", P.getPrice());
				jsonobj.put("publishId", D.get(i).getPublishId());
				jsonobj.put("seat", D.get(i).getSeat());
				if(map.IsUsed(String.valueOf(D.get(i).getId()))==0)//如果该电影票还没被使用
					noused.add(jsonobj);
				else
					used.add(jsonobj);
						
				
			}
					jsonarray.add(used);
					jsonarray.add(noused);
		}
		finally{
			if(sql!=null)
				sql.close();
		}
		System.out.println(jsonarray.toString());
		
		return jsonarray.toString();
	}
	
	@RequestMapping(value="/getOrderDetail",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getOrderDetail(@RequestParam("id") String id)
	{	
		
	  
		SqlSession sql=null;
		Detail D;
		Publish P;
		
		JSONObject jsonobj = new JSONObject(); 
		
		try{
		sql=SqlSessionFactoryUtils.openSqlSession();
		Mapper map = sql.getMapper(Mapper.class);
		D=map.getOrderDetail(id);
		P=map.findPublishById(Integer.valueOf(D.getPublishId()));
		jsonobj.put("price", P.getPrice());
		jsonobj.put("seat", D.getSeat());
		jsonobj.put("orderId", D.getId());
		jsonobj.put("phone",D.getPhone());
		jsonobj.put("date",D.getDate());
		jsonobj.put("time",D.getTime());
		jsonobj.put("movie",P.getMovie());
		jsonobj.put("cinema",P.getCinema());
		jsonobj.put("moviedate",P.getDate());
		jsonobj.put("movietime",P.getTime());
		jsonobj.put("type",P.getType());
		jsonobj.put("hall",P.getHall());
		
		
		}
		finally{
			if(sql!=null)
				sql.close();
		}
	
		
		return jsonobj.toString();
	}
	

	@RequestMapping(value="/toOrder",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void toOrder(@RequestParam("phone") String phone,@RequestParam("seats") String seat,@RequestParam("accoutid") String accoutid,@RequestParam("sessionid") String sessionid)
	{
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

	
	}
	
	@RequestMapping(value="/insertUsedOrder",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void insertUsedOrder(@RequestParam("id") String id)
	{
		SqlSession sql=null;
		
		try{
		sql=SqlSessionFactoryUtils.openSqlSession();
		Mapper map = sql.getMapper(Mapper.class);
		
		map.insertUsedOrder(id);
		}
		finally{
			if(sql!=null)
				sql.close();
		}

	
	}
	
	@RequestMapping(value="/delUsedOrder",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void delUsedOrder(@RequestParam("id") String id)
	{
		SqlSession sql=null;
		
		try{
		sql=SqlSessionFactoryUtils.openSqlSession();
		Mapper map = sql.getMapper(Mapper.class);
		
		map.delUsedOrder(id);
		map.delTicket(id);
		}
		finally{
			if(sql!=null)
				sql.close();
		}

	
	}
	
	
	/*
	@RequestMapping(value="/IsUsed",method=RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void IsUsed(@RequestParam("id") String id)
	{
		

		SqlSession sql=null;
		
		try{
		sql=SqlSessionFactoryUtils.openSqlSession();
		Mapper map = sql.getMapper(Mapper.class);
		System.out.println(map.IsUsed(id));
		}
		finally{
			if(sql!=null)
				sql.close();
			
		}

	
	}
	*/
}
