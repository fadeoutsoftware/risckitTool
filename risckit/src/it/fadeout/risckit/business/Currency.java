package it.fadeout.risckit.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="risckit.currency")
@XmlRootElement
public class Currency {
	
	@Id
	@Column(name="id")
	private Integer Id;
	
	@Column(name="currency")
	private String Currency;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}
	
	@OneToOne(mappedBy = "Currency")
	private SocioImpact SocioImpact;

}
