package com.mySpring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.mapper.Mapper;
import com.project.pojo.Hall;
import com.project.pojo.Publish;
import com.project.utils.SqlSessionFactoryUtils;

@Controller
public class SeatController {

	@RequestMapping(value="/getSeat",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getSeat(@RequestParam("cinema") String cinema,@RequestParam("movie") String movie,@RequestParam("hall") String hall,@RequestParam("date") String date,@RequestParam("time") String time)
	{
		Date d=null;
		hall=""+hall.charAt(0);
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
		System.out.println("aaaa");
		
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
				return jsonarray.toString();
			}
		else{
			//若一开始为空
			List<Publish> P=map.findPublish(p);
			JSONArray jsonarray = new JSONArray();  
			JSONObject jsonobj = new JSONObject(); 
			
				jsonobj.put("seat","");
				jsonobj.put("id",P.get(0).getId());
				jsonarray.add(jsonobj);
				return jsonarray.toString();
			
			
		}
		
		}
			finally{
				if(sql!=null)
					sql.close();
				
			}
		}
	
}
	

