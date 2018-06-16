package guru.springframework.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;

public class IndexControllerTest {

	@Mock
	RecipeService recipeService;

	@Mock
	Model model;

	IndexController controller;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		controller = new IndexController(recipeService);
	}

	@Test
	public void testMockMVC() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
	}

	@Test
	public void getIndexPage() {

		Set<Recipe> recipes = new HashSet<>();

		Recipe recipe1 = new Recipe();
		recipe1.setId(1L);
		recipes.add(recipe1);

		Recipe recipe2 = new Recipe();
		recipe2.setId(5L);
		recipes.add(recipe2);

		when(recipeService.getRecipes()).thenReturn(recipes);

		String viewName = controller.getIndexPage(model);

		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

		assertEquals("index", viewName);
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(Mockito.eq("recipes"), argumentCaptor.capture());

		Set<Recipe> setInController = argumentCaptor.getValue();
		assertEquals(2, setInController.size());

	}
}
