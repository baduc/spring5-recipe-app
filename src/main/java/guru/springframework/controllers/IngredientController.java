package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;

@Controller
public class IngredientController {

	private final RecipeService recipeService;
	
	private final IngredientService ingredientService;
	
	public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
	}

	@GetMapping("/recipe/{id}/ingredients")
	public String listIngredient(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipe/ingredient/list";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndId(Long.valueOf(recipeId), Long.valueOf(id)));
		return "recipe/ingredient/show";
	}
}
