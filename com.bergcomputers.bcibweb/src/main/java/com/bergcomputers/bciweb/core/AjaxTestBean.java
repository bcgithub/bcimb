package com.bergcomputers.bciweb.core;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialResponseWriter;

@ManagedBean
@SessionScoped
public class AjaxTestBean implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String displayName() throws InterruptedException {
        if("".equals(name) || name == null){
            return "";
        }else{
            Thread.currentThread().sleep(3000);
            return "Your name is "+name+"!";
        }
    }

    public String displayName2() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()){
        	throw new RuntimeException("Exception!!!!");
           // FacesContext.getCurrentInstance().getPartialViewContext().getPartialResponseWriter().append("aaaa");
        }
        /*
        if("".equals(name) || name == null){
            return "";
        }else{
            Thread.currentThread().sleep(3000);
            return "Your name is "+name+"!";
        }           */
        return null;
    }
    
    public String displayName3() throws IOException {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()){
        	//throw new RuntimeException("Exception!!!!");
        	PartialResponseWriter responseWriter =
                    FacesContext.getCurrentInstance().getPartialViewContext().getPartialResponseWriter();
                  responseWriter.startDocument();
                  responseWriter.startEval();
                  responseWriter.write("test");
                  responseWriter.endEval();
                  responseWriter.endDocument();
                  responseWriter.flush();
                  responseWriter.close();
           
        }
        /*
        if("".equals(name) || name == null){
            return "";
        }else{
            Thread.currentThread().sleep(3000);
            return "Your name is "+name+"!";
        }           */
        return null;
    }    
}

