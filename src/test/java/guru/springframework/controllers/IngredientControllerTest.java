package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    private IngredientController controller;

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListIngredient() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(1l);

        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testShowIngredient() throws Exception {
        IngredientCommand command = new IngredientCommand();
        command.setId(1l);
        command.setRecipeId(2l);

        when(ingredientService.findByRecipeIdAndIngredientId(2l, 1l)).thenReturn(command);

        mockMvc.perform(get("/recipe/2/ingredient/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(2l, 1l);
    }

    @Test
    public void testUpdateRecipeIngredient() throws Exception {
        IngredientCommand command = new IngredientCommand();
        command.setId(1l);
        command.setRecipeId(2l);

        when(ingredientService.findByRecipeIdAndIngredientId(2l, 1l)).thenReturn(command);
        when(unitOfMeasureService.listAllUoms()).thenReturn(Collections.EMPTY_SET);

        mockMvc.perform(get("/recipe/2/ingredient/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("uomList"))
                .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(2l, 1l);
        verify(unitOfMeasureService, times(1)).listAllUoms();
    }

    @Test
    public void saveOrUpdatengredient() throws Exception {
        IngredientCommand command = new IngredientCommand();
        command.setId(1l);
        command.setRecipeId(2l);

        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "abc")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/1/show"));

        verify(ingredientService, times(1)).saveIngredientCommand(any());
    }

    @Test
    public void testDeleteIngredient() throws Exception {
        IngredientCommand command = new IngredientCommand();
        command.setId(1l);
        command.setRecipeId(2l);

        mockMvc.perform(get("/recipe/2/ingredient/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));

        verify(ingredientService, times(1)).deleteIngredient(2l, 1l);
    }

}
