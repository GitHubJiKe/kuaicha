package com.ypf.kuaicha.entity;
import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;
public class Location implements Serializable  {
	public String reason  ;
	public int error_code  ;
	public String resultcode  ;
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}

	public  Result result;
	public class Result implements Serializable  {
		public String address  ;
		public String lng  ;
		public String business  ;
		public int citycode  ;
		public String type  ;
		public String lat  ;
		public  Ext ext;
		public Ext getExt() {
			return ext;
		}
		public void setExt(Ext ext) {
			this.ext = ext;
		}

		public class Ext implements Serializable  {
			public String country  ;
			public int country_code  ;
			public String distance  ;
			public String province  ;
			public String city  ;
			public String street  ;
			public String district  ;
			public String street_number  ;
			public String direction  ;
			public void  setCountry (String country){this.country=country;}
			public String getCountry (){return this.country;}
			public void  setCountry_code (int country_code){this.country_code=country_code;}
			public int getCountry_code (){return this.country_code;}
			public void  setDistance (String distance){this.distance=distance;}
			public String getDistance (){return this.distance;}
			public void  setProvince (String province){this.province=province;}
			public String getProvince (){return this.province;}
			public void  setCity (String city){this.city=city;}
			public String getCity (){return this.city;}
			public void  setStreet (String street){this.street=street;}
			public String getStreet (){return this.street;}
			public void  setDistrict (String district){this.district=district;}
			public String getDistrict (){return this.district;}
			public void  setStreet_number (String street_number){this.street_number=street_number;}
			public String getStreet_number (){return this.street_number;}
			public void  setDirection (String direction){this.direction=direction;}
			public String getDirection (){return this.direction;}

			public String toString() {String s = "";Field[] arr = this.getClass().getFields();for (Field f : getClass().getFields()) {try {s += f.getName() + "=" + f.get(this) + "\n,";} catch (Exception e) {}}return getClass().getSimpleName() + "[" + (arr.length==0?s:s.substring(0, s.length() - 1)) + "]";         }
		}
		public void  setAddress (String address){this.address=address;}
		public String getAddress (){return this.address;}
		public void  setLng (String lng){this.lng=lng;}
		public String getLng (){return this.lng;}
		public void  setBusiness (String business){this.business=business;}
		public String getBusiness (){return this.business;}
		public void  setCitycode (int citycode){this.citycode=citycode;}
		public int getCitycode (){return this.citycode;}
		public void  setType (String type){this.type=type;}
		public String getType (){return this.type;}
		public void  setLat (String lat){this.lat=lat;}
		public String getLat (){return this.lat;}

		public String toString() {String s = "";Field[] arr = this.getClass().getFields();for (Field f : getClass().getFields()) {try {s += f.getName() + "=" + f.get(this) + "\n,";} catch (Exception e) {}}return getClass().getSimpleName() + "[" + (arr.length==0?s:s.substring(0, s.length() - 1)) + "]";      }
	}
	public void  setReason (String reason){this.reason=reason;}
	public String getReason (){return this.reason;}
	public void  setError_code (int error_code){this.error_code=error_code;}
	public int getError_code (){return this.error_code;}
	public void  setResultcode (String resultcode){this.resultcode=resultcode;}
	public String getResultcode (){return this.resultcode;}

	public String toString() {String s = "";Field[] arr = this.getClass().getFields();for (Field f : getClass().getFields()) {try {s += f.getName() + "=" + f.get(this) + "\n,";} catch (Exception e) {}}return getClass().getSimpleName() + "[" + (arr.length==0?s:s.substring(0, s.length() - 1)) + "]";   }
}