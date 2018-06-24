package com.mySpring;

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
import com.project.pojo.SellerOrder;
import com.project.utils.SqlSessionFactoryUtils;

@Controller
public class SellerController {
	
		@RequestMapping(value="/Seller",method=RequestMethod.GET,produces = "text/html;charset=UTF-8")
		@ResponseBody
		public String Seller(@RequestParam("seller") String seller){

			SqlSession sql=null;
			JSONArray jsonarray = new JSONArray();  
			JSONObject jsonobj = new JSONObject(); 
			
			try{
			sql=SqlSessionFactoryUtils.openSqlSession();
			Mapper map = sql.getMapper(Mapper.class);
			List<SellerOrder> SO=map.getSellerOrder(seller);
		
			for(int i=0;i<SO.size();i++)
				{
					jsonobj.put("PublishTime", SO.get(i).getPublishTime());
					jsonobj.put("PublishDate", SO.get(i).getPublishDate());
					jsonobj.put("movie", SO.get(i).getMovie());
					jsonobj.put("type", SO.get(i).getType());
					jsonobj.put("hall", SO.get(i).getHall());
					jsonobj.put("price", SO.get(i).getPrice());
					jsonobj.put("orderId", SO.get(i).getOrderId());
					jsonobj.put("buyer", SO.get(i).getBuyer());
					jsonarray.add(jsonobj);
				}
			
			}
			finally{
				if(sql!=null)
					sql.close();
				
			}
				System.out.println(jsonarray.toString());
				return  jsonarray.toString();
			
		}
}
