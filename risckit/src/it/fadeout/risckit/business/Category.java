package it.fadeout.risckit.business;

import it.fadeout.risckit.viewmodels.CategoryViewModel;
import it.fadeout.risckit.viewmodels.SubCategoryViewModel;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="risckit.categories")
@XmlRootElement
public class Category {

	@Id
	@Column(name="id")
	private Integer Id;
	
	@Column(name="description")
	private String Description;
	
	@OneToMany(mappedBy = "Category", fetch = FetchType.EAGER)  
	private Set<SubCategory> SubCategories;

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

	public Set<SubCategory> getSubCategories() {
		return SubCategories;
	}

	public void setSubCategories(Set<SubCategory> subCategories) {
		SubCategories = subCategories;
	}
	
	public CategoryViewModel getViewModel()
	{
		CategoryViewModel oViewModel = new CategoryViewModel();
		oViewModel.setId(this.getId());
		oViewModel.setDescription(this.getDescription());
		for (SubCategory oSub : this.getSubCategories()) {
			if (oViewModel.getSubCategories() == null)
				oViewModel.setSubCategories(new ArrayList<SubCategoryViewModel>());
			oViewModel.getSubCategories().add(oSub.getViewModel());
		}
		return oViewModel;
	}
	
}
