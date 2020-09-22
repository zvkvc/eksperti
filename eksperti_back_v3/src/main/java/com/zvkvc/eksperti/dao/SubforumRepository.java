package com.zvkvc.eksperti.dao;

import com.zvkvc.eksperti.model.Subforum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubforumRepository extends JpaRepository<Subforum, Long> {

    Optional<Subforum> findByName(String subforumName);
}
