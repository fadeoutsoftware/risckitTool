package it.fadeout.risckit.business;

import it.fadeout.risckit.viewmodels.SocioImpactViewModel;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="risckit.socioimpacts")
@XmlRootElement
public class SocioImpact {

	public SocioImpact()
	{
		setSubCategory(new SubCategory());
		setCurrency(new Currency());
	}

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer Id;

	@Column(name="idevent")
	private Integer IdEvent;

	@Column(name="idsubcategory")
	private Integer IdSubcategory;

	@Column(name="description")
	private String Description;

	@Column(name="unitmeasure")
	private String UnitMeasure;

	@Column(name="cost")
	private BigDecimal Cost;

	@Column(name="idcurrency")
	private Integer IdCurrency;

	@OneToOne(cascade= CascadeType.REFRESH)
	@PrimaryKeyJoinColumn
	private SubCategory SubCategory;

	@OneToOne(cascade= CascadeType.REFRESH)
	@PrimaryKeyJoinColumn
	private Currency Currency;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getIdEvent() {
		return IdEvent;
	}

	public void setIdEvent(Integer idEvent) {
		IdEvent = idEvent;
	}

	public Integer getIdSubcategory() {
		return IdSubcategory;
	}

	public void setIdSubcategory(Integer idSubcategory) {
		IdSubcategory = idSubcategory;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUnitMeasure() {
		return UnitMeasure;
	}

	public void setUnitMeasure(String unitMeasure) {
		UnitMeasure = unitMeasure;
	}

	public BigDecimal getCost() {
		return Cost;
	}

	public void setCost(BigDecimal cost) {
		Cost = cost;
	}

	public SubCategory getSubCategory() {
		return SubCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		SubCategory = subCategory;
	}

	public SocioImpactViewModel GetViewModel()
	{
		SocioImpactViewModel oViewModel = new SocioImpactViewModel();
		oViewModel.setId(this.getId());
		oViewModel.setIdEvent(this.getIdEvent());
		oViewModel.setIdSubcategory(this.getIdSubcategory());
		oViewModel.setIdCurrency(this.getIdCurrency());
		if (this.getSubCategory() != null)
		{
			oViewModel.setIdCategory(this.getSubCategory().getCategory().getId());
			oViewModel.setCategory(this.getSubCategory().getCategory().getDescription());
			oViewModel.setSubcategory(this.getSubCategory().getDescription());
		}
		oViewModel.setDescription(this.getDescription());
		oViewModel.setUnitMeasure(this.getUnitMeasure());
		oViewModel.setCost(this.getCost());
		if (this.getCurrency() != null)
			oViewModel.setCurrency(this.getCurrency().getCurrency());
		return oViewModel;
	}

	public void SetEntity(SocioImpactViewModel oViewModel)
	{

		this.setId(oViewModel.getId());
		this.setIdEvent(oViewModel.getIdEvent());
		this.setIdSubcategory(oViewModel.getIdSubcategory());
		this.setDescription(oViewModel.getDescription());
		this.setUnitMeasure(oViewModel.getUnitMeasure());
		this.setIdCurrency(oViewModel.getIdCurrency());
		this.setCost(oViewModel.getCost());

	}

	public Currency getCurrency() {
		return Currency;
	}

	public void setCurrency(Currency currency) {
		Currency = currency;
	}

	public Integer getIdCurrency() {
		return IdCurrency;
	}

	public void setIdCurrency(Integer idCurrency) {
		IdCurrency = idCurrency;
	}


}
