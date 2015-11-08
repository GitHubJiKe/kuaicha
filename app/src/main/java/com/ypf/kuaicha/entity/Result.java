package com.ypf.kuaicha.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import com.lidroid.xutils.db.annotation.Finder;
import com.lidroid.xutils.db.annotation.Foreign;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
@Table(name="result")
public class Result implements Serializable  {

	public Result() {
		super();
	}
	@Id
	int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<com.ypf.kuaicha.entity.List> getList() {
		return list;
	}
	public void setList(List<com.ypf.kuaicha.entity.List> list) {
		this.list = list;
	}
	@Foreign(column="userId",foreign="id")
	User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String com  ;
	public String no  ;
	public String company  ;
	public String status  ;
	@Finder(valueColumn="id",targetColumn="resultId")
	public List<com.ypf.kuaicha.entity.List> list;
	public void  setCom (String com){this.com=com;}
	public String getCom (){return this.com;}
	public void  setNo (String no){this.no=no;}
	public String getNo (){return this.no;}
	public void  setCompany (String company){this.company=company;}
	public String getCompany (){return this.company;}
	public void  setStatus (String status){this.status=status;}
	public String getStatus (){return this.status;}

	public String toString() {String s = "";Field[] arr = this.getClass().getFields();for (Field f : getClass().getFields()) {try {s += f.getName() + "=" + f.get(this) + "\n,";} catch (Exception e) {}}return getClass().getSimpleName() + "[" + (arr.length==0?s:s.substring(0, s.length() - 1)) + "]";   }
}
