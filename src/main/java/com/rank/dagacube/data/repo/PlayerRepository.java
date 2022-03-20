package com.rank.dagacube.data.repo;

import com.rank.dagacube.data.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
