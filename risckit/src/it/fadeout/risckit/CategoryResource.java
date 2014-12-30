package it.fadeout.risckit;

import java.util.ArrayList;
import java.util.List;

import it.fadeout.risckit.business.Category;
import it.fadeout.risckit.data.CategoryRepository;
import it.fadeout.risckit.data.Repository;
import it.fadeout.risckit.viewmodels.CategoryViewModel;







import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/categories")
public class CategoryResource {

	@GET
	@Path("/")
	@Produces({"application/json"})
	public ArrayList<CategoryViewModel> getCategories() {

		ArrayList<CategoryViewModel> oReturnValue = new ArrayList<CategoryViewModel>();
		CategoryRepository oRepo = new CategoryRepository();

		List<Category> oList = oRepo.SelectAll(Category.class);
		for (Category category : oList) {
			oReturnValue.add(category.getViewModel());
		}
		
		oRepo.CloseSession();
		
		return oReturnValue;
	}
	
	
}
