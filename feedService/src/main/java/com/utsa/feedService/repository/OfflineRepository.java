package com.utsa.feedService.repository;

import com.utsa.feedService.dto.Offline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfflineRepository extends JpaRepository<Offline, Integer> {
}
