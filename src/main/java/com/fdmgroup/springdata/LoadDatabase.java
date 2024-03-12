package com.fdmgroup.springdata;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fdmgroup.springdata.model.Game;
import com.fdmgroup.springdata.repository.GameRepository;

@Configuration
public class LoadDatabase {
	
	@Bean
	CommandLineRunner initDatabase(GameRepository gameRepo) {
		return args -> {
			Game game1 = new Game("MH: Rise", "Capcom", 8.0, 49.99);
			Game game2 = new Game("RE 4: Remake", "Capcom", 10.0, 59.99);
			Game game3 = new Game("Zelda: TOTK", "Nintendo", 10.0, 49.99);
			Game game4 = new Game("NieR: Automata", "Platinum games", 8.0, 35.99);
			gameRepo.save(game1);
			gameRepo.save(game2);
			gameRepo.save(game3);
			gameRepo.save(game4);


		};
	}

}
