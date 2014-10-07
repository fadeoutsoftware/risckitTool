package it.fadeout.risckit.business;

import it.fadeout.risckit.viewmodels.CategoryViewModel;
import it.fadeout.risckit.viewmodels.SubCategoryViewModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="risckit.subcategories")
public class SubCategory {
	
	public SubCategory()
	{
		setCategory(new Category());
	}
	
	@Id
	@Column(name="id", unique=true)
	private Integer Id;
	
	@Column(name="description")
	private String Description;

	@Column(name="idcategory" , insertable=false, updatable=false)
	private Integer IdCategory;
	
	@ManyToOne
	@JoinColumn(name="idcategory", nullable=false)
	private Category Category;
	
	@OneToOne(mappedBy = "SubCategory")
	private SocioImpact SocioImpact;
	
	
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
	
	public SubCategoryViewModel getViewModel()
	{
		SubCategoryViewModel oViewModel = new SubCategoryViewModel();
		oViewModel.setId(this.getId());
		oViewModel.setDescription(this.getDescription());
		oViewModel.setIdCategory(this.getCategory().getId());
		return oViewModel;
	}

	public Category getCategory() {
		return Category;
	}

	public void setCategory(Category category) {
		Category = category;
	}

	public Integer getIdCategory() {
		return IdCategory;
	}

	public void setIdCategory(Integer idCategory) {
		IdCategory = idCategory;
	}

	public SocioImpact getSocioImpact() {
		return SocioImpact;
	}

	public void setSocioImpact(SocioImpact socioImpact) {
		SocioImpact = socioImpact;
	}

}
