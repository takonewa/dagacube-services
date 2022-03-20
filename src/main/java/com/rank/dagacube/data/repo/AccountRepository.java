package com.rank.dagacube.data.repo;

import com.rank.dagacube.data.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
