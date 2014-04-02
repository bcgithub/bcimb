package com.bergcomputers.bciweb.core;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

@ManagedBean
public class ErrorsBean {
	public String errors;
	public String type = "info";
	
    public String getType() {
    	List<FacesMessage> msgList = FacesContext.getCurrentInstance().getMessageList();
  	
    	for (int i = 0; i < msgList.size(); i++) {
    		if (msgList.get(i).getSeverity() == FacesMessage.SEVERITY_WARN) {
    			setType("warning");
    		}
		}
    	for (int i = 0; i < msgList.size(); i++) {
    		if (msgList.get(i).getSeverity() == FacesMessage.SEVERITY_ERROR) {
    			setType("error");
    		}
		}
    	for (int i = 0; i < msgList.size(); i++) {
    		if (msgList.get(i).getSeverity() == FacesMessage.SEVERITY_FATAL) {
    			setType("fatal");
    		}
		}
    	
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isHaveErrors() {
		return FacesContext.getCurrentInstance().getMessageList().size() > 0;  
	}

	public String getErrors() {	
    	List<FacesMessage> msgList = FacesContext.getCurrentInstance().getMessageList();
   	   	
    	for (int i = 0; i < msgList.size(); i++) {
    		errors = errors + ((FacesMessage)msgList.get(i)).getSummary() +'\n';
  		}

    	return errors;
    }
}