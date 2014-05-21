package com.bergcomputers.domain;

import java.util.Date;

public interface IBaseEntity {
	public static final String id = new String("id");
	public static final String version = new String("version");
	public static final String deleted = new String("deleted");
	public static final String creationDate = new String("creationDate");

	public abstract Long getId();

	public abstract void setId(Long id);

	public Integer getVersion();
	
	public void setVersion(Integer version);
	
	public int getDeleted();
	
	public void setDeleted(int deleted);
	
	public abstract Date getCreationDate();

	public abstract void setCreationDate(Date creationDate);

}