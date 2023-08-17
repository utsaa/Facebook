package com.utsa.feedService.repository;

import com.utsa.feedService.dto.UserWs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsernameWsId extends JpaRepository<UserWs, Integer> {
}
