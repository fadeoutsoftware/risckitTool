package it.fadeout.risckit.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="risckit.subcategories")
@XmlRootElement
public class SubCategory {
	
	@Id
	@Column(name="id")
	private Integer Id;
	
	@Column(name="description")
	private String Description;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

}
