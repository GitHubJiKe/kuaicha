package com.ypf.kuaicha.entity;
import java.io.Serializable;
import java.util.List;
/**
 * @author Administrator
 *
 */
public class Company implements Serializable  {
	public String reason  ;
	public String resultcode  ;
	public List<Result> result;
	public List<Result> getResult() {
		return result;
	}
	public void setResult(List<Result> result) {
		this.result = result;
	}
	public class Result implements Serializable  {
		public String com  ;
		public String no  ;
		public void  setCom (String com){this.com=com;}
		public String getCom (){return this.com;}
		public void  setNo (String no){this.no=no;}
		public String getNo (){return this.no;}

	}
	public void  setReason (String reason){this.reason=reason;}
	public String getReason (){return this.reason;}
	public void  setResultcode (String resultcode){this.resultcode=resultcode;}
	public String getResultcode (){return this.resultcode;}

}