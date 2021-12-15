package com.modesto.demoJPA.repository;

import com.modesto.demoJPA.domain.Gabinete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GabineteRepository extends JpaRepository<Gabinete, Long> {

}
