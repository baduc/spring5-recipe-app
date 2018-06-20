package guru.springframework.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

	private RecipeRepository recipeRepository;
	private IngredientToIngredientCommand converter;
	
	public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand converter) {
		super();
		this.recipeRepository = recipeRepository;
		this.converter = converter;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if (!recipeOptional.isPresent()) {
			log.error("Recipe id not found: " + ingredientId);
		}
		Recipe recipe = recipeOptional.get();
		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> converter.convert(ingredient))
				.findFirst();
		if (!ingredientCommandOptional.isPresent()) {
			 log.error("Ingredient id not found: " + ingredientId);
		}
		return ingredientCommandOptional.get();
	}

}
