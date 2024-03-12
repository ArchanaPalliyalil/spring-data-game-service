package com.fdmgroup.springdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.springdata.model.Game;
import com.fdmgroup.springdata.repository.GameRepository;



@Service // 1
public class GameService {
    private GameRepository gameRepo;

    @Autowired // 2
    public GameService(GameRepository gameRepo) {
        this.gameRepo = gameRepo;
    }

    public Game addGame(Game game) {
        if(game.getPrice()>= 0 && 
        		!gameRepo.existsById(game.getId())) {
            return gameRepo.save(game);
        }
        return null;
    }

    public Game getGame(int id) {
        Optional<Game> optGame = gameRepo.findById(id);

        if(optGame.isPresent()) {
            return optGame.get();
        }

        return null;
    }

    public Game updateGame(Game game) {
        if(gameRepo.existsById(game.getId()) && game.getPrice() >= 0) {
            gameRepo.save(game);
            return game;
        } 
        return null;
    }

    public void deleteGame(int id) {
        gameRepo.deleteById(id);
    }

    public List<Game> listAll() {
        return gameRepo.findAll();
    }
    public Game findByName(String name) {
    	Optional<Game> optGame = gameRepo.findByNameContainsIgnoreCase(name);
    	if(optGame.isPresent()) {
    		return optGame.get();
    	}else {
    		return null;
    	}
    		
    }
    public List<Game> listByPublisher(String publisher){
    	return gameRepo.findByPublisherContainsIgnoreCase(publisher);
    }
    public List<Game> listGamesByPriceUnder(double price){
    	return gameRepo.listByPriceUnder(price);
    }
}
