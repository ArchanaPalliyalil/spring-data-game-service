package com.fdmgroup.springdata.controller;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fdmgroup.springdata.model.Game;
import com.fdmgroup.springdata.service.GameService;



@Controller // 1
public class GameController {
    private GameService gameService;
    private static final Logger log = LoggerFactory.getLogger(GameController.class);

    @Autowired // 2
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/show-games")
    public String displayShowGames(Model model) {
    	log.info("showing games");
    	List<Game> games = gameService.listAll();
    	model.addAttribute("games", games);
    	return "showGames";
    }
    
    @GetMapping("/add-game")
    public String displayAddGame(Model model) {
    	log.debug("adding a game");
    	model.addAttribute("game", new Game());
    	return "addGame";
    }
    
    @PostMapping("/submit-new-game")
    public String handleSubmitNewGame(@ModelAttribute Game game ,RedirectAttributes redirectAttributes) {
//    	gameService.addGame(game);
//    	return "redirect:/show-games";
    	if(gameService.addGame(game) == null) {
    		redirectAttributes.addFlashAttribute("errorMessage", "Unable to add game,please check price and id");
    		redirectAttributes.addFlashAttribute("game", game);
    		return "redirect:/add-game";
    	}
    	return "redirect:/show-games";
    	
    }
    @GetMapping("/delete-game")
    public String handleDeleteGame(@RequestParam int id) {
    	gameService.deleteGame(id);
    	return "redirect:/show-games";
    }
    
    @GetMapping("/edit-game")
    public String updateGame(@RequestParam int id,Model model) {
    	model.addAttribute("game", gameService.getGame(id));
    	return "editGame";
    }
   @PostMapping("/submit-edit-game")
   public String handleSubmitEditgame(@ModelAttribute Game game) {
	   gameService.updateGame(game);
	   return "redirect:/show-games";
   }
   @PostMapping("search-by-name")
   public String handleSearchByName(@RequestParam String name,Model model) {
	  Game game = gameService.findByName(name);
	  if(Objects.nonNull(game)) {
		  model.addAttribute("game", game);
		  return "gameDetails";
	  }
	  return "errorPage";
   }
   
   @PostMapping("search-by-publisher")
   public String searchByPublisher(@RequestParam String publisher,Model model) {
	   List<Game> listGames = gameService.listByPublisher(publisher);
	   if(!listGames.isEmpty()) {
		   model.addAttribute("games", listGames);
		   return "showGames";
	   }
		  return "errorPage";

   }
   
   @PostMapping("search-by-price")
   public String searchByPriceUnder(@RequestParam double price , Model model) {
	   List<Game> gamesUnderGivenPrice = gameService.listGamesByPriceUnder(price);
	   if(!gamesUnderGivenPrice.isEmpty()) {
		   model.addAttribute("games", gamesUnderGivenPrice);
		   return "showGames";
	   }
	   return "errorPage";
   }
}
