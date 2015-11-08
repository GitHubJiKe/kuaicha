package com.ypf.kuaicha.entity;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.lidroid.xutils.db.annotation.Foreign;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
@Table(name="list")
public class List implements Serializable  {

	public List() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Id
	public int id;
	public String datetime  ;
	public String zone  ;
	public String remark  ;
	@Foreign(column="resultId",foreign="id")
	Result result;
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public void  setDatetime (String datetime){this.datetime=datetime;}
	public String getDatetime (){return this.datetime;}
	public void  setZone (String zone){this.zone=zone;}
	public String getZone (){return this.zone;}
	public void  setRemark (String remark){this.remark=remark;}
	public String getRemark (){return this.remark;}

	public String toString() {String s = "";Field[] arr = this.getClass().getFields();for (Field f : getClass().getFields()) {try {s += f.getName() + "=" + f.get(this) + "\n,";} catch (Exception e) {}}return getClass().getSimpleName() + "[" + (arr.length==0?s:s.substring(0, s.length() - 1)) + "]";      }
}
