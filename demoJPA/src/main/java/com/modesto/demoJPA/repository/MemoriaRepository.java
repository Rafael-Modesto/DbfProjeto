package com.modesto.demoJPA.repository;

import com.modesto.demoJPA.domain.Memoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoriaRepository extends JpaRepository<Memoria, Long> {

}
