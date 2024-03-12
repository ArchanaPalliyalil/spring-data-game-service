package com.fdmgroup.springdata.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fdmgroup.springdata.model.Game;
import com.fdmgroup.springdata.service.GameService;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private GameService mockGameService;
	
	@MockBean
	private Game mockGame;
	
	@Test
	public void testTheGetRequestCalllsListAll() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/show-games"));
		verify(mockGameService).listAll();
	}
	
	@Test
	public void testThePostRequestToSubmitNewGameCallsAddGame() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/submit-new-game").flashAttr("game", mockGame));
		verify(mockGameService).addGame(mockGame);
	}
	@Test
	public void testGetRequestForDeleteGameCallsDeleteGameWithId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/delete-game").param("id", "12"));
		verify(mockGameService).deleteGame(12);
	}
	
	@Test
	public void testPostSubmitNewGameRedirectsToShowGamesWhenGameIsSavedSuccessfully() throws Exception {
		when(mockGameService.addGame(mockGame)).thenReturn(mockGame);
		mockMvc.perform(MockMvcRequestBuilders.post("/submit-new-game").flashAttr("game", mockGame))
		.andExpect(MockMvcResultMatchers.view().name("redirect:/show-games"));
	}
	@Test
	public void testGetRequestToShowGameDisplaysTheShowGamesPage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/show-games"))
		       .andExpect(MockMvcResultMatchers.view().name("showGames"));
	}
	@Test
	public void testPostSubmitNewGameRedirectsToAddGamePageWhenGameNotPersisted() throws Exception {
		when(mockGameService.addGame(mockGame)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.post("/submit-new-game").flashAttr("game", mockGame))
		       .andExpect(MockMvcResultMatchers.view().name("redirect:/add-game"));
	}
	
	@Test //8
	public void testGetAddGameRequestShowsAddgamePage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/add-game").flashAttr("game", mockGame))
		       .andExpect(MockMvcResultMatchers.view().name("addGame"));
	}

}
