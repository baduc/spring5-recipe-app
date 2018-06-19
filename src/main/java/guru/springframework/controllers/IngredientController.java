package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import guru.springframework.services.RecipeService;

@Controller
public class IngredientController {

	private final RecipeService recipeService;
	
	
	public IngredientController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}


	@GetMapping("/recipe/{id}/ingredients")
	public String listIngredient(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipe/ingredient/list";
	}
}
