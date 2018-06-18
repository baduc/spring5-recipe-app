package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

	private CategoryToCategoryCommand categoryConverter;
	private IngredientToIngredientCommand ingredientConverter;
	private NotesToNotesCommand notesConverter;

	public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter,
			IngredientToIngredientCommand ingredientConverter, NotesToNotesCommand notesConverter) {
		this.categoryConverter = categoryConverter;
		this.ingredientConverter = ingredientConverter;
		this.notesConverter = notesConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public RecipeCommand convert(Recipe source) {
		if (source == null) {
			return null;
		}
		final RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(source.getId());
		recipeCommand.setDescription(source.getDescription());
		recipeCommand.setPrepTime(source.getPrepTime());
		recipeCommand.setCookTime(source.getCookTime());
		recipeCommand.setServings(source.getServings());
		recipeCommand.setSource(source.getSource());
		recipeCommand.setUrl(source.getUrl());
		recipeCommand.setDirections(source.getDirections());
		recipeCommand.setDifficulty(source.getDifficulty());
		recipeCommand.setNotes(notesConverter.convert(source.getNotes()));
		if (!source.getIngredients().isEmpty()) {
			source.getIngredients()
					.forEach(ingredient -> recipeCommand.getIngredients().add(ingredientConverter.convert(ingredient)));
		}

		if (!source.getCategories().isEmpty()) {
			source.getCategories()
					.forEach(category -> recipeCommand.getCategories().add(categoryConverter.convert(category)));
		}
		return recipeCommand;
	}

}
