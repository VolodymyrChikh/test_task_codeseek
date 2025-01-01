package com.volodymyrchikh.footballservice.repository;

import com.volodymyrchikh.footballservice.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>, QuerydslPredicateExecutor<Team> {
}
