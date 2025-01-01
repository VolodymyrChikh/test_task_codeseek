package com.volodymyrchikh.footballservice.repository;

import com.volodymyrchikh.footballservice.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PlayerRepository extends JpaRepository<Player, Long>, QuerydslPredicateExecutor<Player> {
}
