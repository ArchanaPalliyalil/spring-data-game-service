package com.fdmgroup.springdata.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fdmgroup.springdata.model.Game;

public interface GameRepository extends JpaRepository<Game, Integer>{

	//derived queries:no need to write code for it
	Optional<Game> findByName(String name);
	Optional<Game> findByNameContainsIgnoreCase(String name);
	List<Game> findByPublisherContainsIgnoreCase(String publisher);
	
	//custom queries
	@Query("Select g from Game g where g.price < :givenPrice")
	List<Game> listByPriceUnder(@Param("givenPrice") double price);
}
