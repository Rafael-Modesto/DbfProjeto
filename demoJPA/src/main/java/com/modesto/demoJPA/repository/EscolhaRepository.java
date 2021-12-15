package com.modesto.demoJPA.repository;

import com.modesto.demoJPA.domain.Escolha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscolhaRepository extends JpaRepository<Escolha, Long> {

}
