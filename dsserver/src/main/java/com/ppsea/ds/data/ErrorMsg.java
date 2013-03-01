package com.ppsea.ds.data;

/**
 * 传递到proxy的错误信息，包含错误码和错误描述
 * @author IBM
 *
 */
public class ErrorMsg extends BaseObject{

	private static final long serialVersionUID = 2965932143419764243L;
	public int code;
	public String text;
	public transient Object obj;
	
	public ErrorMsg(int code, String text){
		this(code,text,null);
	}
	
	public ErrorMsg(int code,Object obj){
		this(code,null,obj);
	}
	
	public ErrorMsg (int code,String text,Object obj){
		this.code = code;
		this.text = text;
		this.obj = obj;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}	
	
	public String toString(){
		return code+":"+text;
	}
}

