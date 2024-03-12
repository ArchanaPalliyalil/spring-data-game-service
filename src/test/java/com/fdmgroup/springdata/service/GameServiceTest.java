package com.fdmgroup.springdata.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.springdata.model.Game;
import com.fdmgroup.springdata.repository.GameRepository;

@SpringBootTest
public class GameServiceTest {
	
	@Autowired
	private GameService gameService;
	
	@MockBean
	private GameRepository mockGameRepo;
	
	@MockBean
	private Game mockGame1;
	
	@Test
	public void testListAllCallsFindAllInGameRepository(){
		gameService.listAll();
		verify(mockGameRepo).findAll();
	}

	@Test
	public void testAddGameCallsSaveMethodInGameRepoWhenGameDoesnotExist() {
		when(mockGame1.getId()).thenReturn(1);
		when(mockGameRepo.existsById(1)).thenReturn(false);
		gameService.addGame(mockGame1);
		verify(mockGameRepo).save(mockGame1);
	}
	@Test
	public void testAddGameDoesnotCallsSaveMethodInGameRepoWhenGameExists() {
		when(mockGame1.getId()).thenReturn(1);
		when(mockGameRepo.existsById(1)).thenReturn(true);
		gameService.addGame(mockGame1);
		verify(mockGameRepo,never()).save(mockGame1);
	}
	
}
