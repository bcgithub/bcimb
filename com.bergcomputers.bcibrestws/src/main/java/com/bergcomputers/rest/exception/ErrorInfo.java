package com.bergcomputers.rest.exception;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorInfo implements Serializable{
	private String url;
	private String message;
	private String code;
	private String developerMessage;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMessage() {
		return message;
	}
	public ErrorInfo(String url, String message, String code,
			String developerMessage) {
		super();
		this.url = url;
		this.message = message;
		this.code = code;
		this.developerMessage = developerMessage;
	}
	public ErrorInfo() {
		super();
	}
	public ErrorInfo(String message, String code) {
		super();
		this.message = message;
		this.code = code;
	}
	public ErrorInfo(String url, String message, String code) {
		super();
		this.url = url;
		this.message = message;
		this.code = code;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDeveloperMessage() {
		return developerMessage;
	}
	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}
}
