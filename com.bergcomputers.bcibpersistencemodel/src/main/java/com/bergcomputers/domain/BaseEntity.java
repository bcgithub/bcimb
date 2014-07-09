package com.bergcomputers.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: BaseEntity
 *
 */
@Entity
@Table(name = "BaseEntity")
@XmlRootElement
@NamedQuery(name=BaseEntity.findAll,query="SELECT a from BaseEntity a")
@Inheritance(strategy=InheritanceType.JOINED)
public class BaseEntity implements Serializable, IBaseEntity {


	private static final long serialVersionUID = -7944505705705785135L;
	public final static String findAll = "com.bergcomputers.domain.baseentity.findAll";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
	protected Long id;

	@Version
    @Column(name = "VERSION")
    protected Integer version;

	protected int deleted;

    @NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date creationDate;

	public BaseEntity() {
		super();
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}


	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}


	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + deleted;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (deleted != other.deleted)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BaseEntity [id=" + id + ", deleted=" + deleted + ", version="
				+ version + ", creationDate=" + creationDate + "]";
	}



}
