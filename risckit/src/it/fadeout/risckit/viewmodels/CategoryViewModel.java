package it.fadeout.risckit.viewmodels;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CategoryViewModel {

	private int id;
	
	private String description;
	
	private ArrayList<SubCategoryViewModel> subCategories;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<SubCategoryViewModel> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(ArrayList<SubCategoryViewModel> subCategories) {
		this.subCategories = subCategories;
	}
}
