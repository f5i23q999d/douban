package com.project.mapper;

import java.util.List;

import com.project.pojo.Cinema;
import com.project.pojo.Detail;
import com.project.pojo.Hall;
import com.project.pojo.Publish;

public interface Mapper {
	public List<Cinema> findCinema(String name,String time);
	public List<Publish> findPublish(Publish p);
	public Publish findPublishById(int id);
	public List<Hall> getSeat(Publish p);
	public int setSeat(Hall p);
	public int toOrder(Detail d);
	public List<Detail> getOrder(String id);//用openid找全部订单
	public Detail getOrderDetail(String id);//用订单号查订单详情
	public int IsUsed(String id);//在used表里面找这影票是否使用过
	public int insertUsedOrder(String id);//将用过的电影票插入到used表中
	public int delUsedOrder(String id);//将用过的电影票插入到used表中
	public int delTicket(String id);//将用过的电影票插入到used表中
	
}
