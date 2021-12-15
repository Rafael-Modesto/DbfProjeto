package com.modesto.demoJPA.repository;

import com.modesto.demoJPA.domain.Alimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimentacaoRepository extends JpaRepository<Alimentacao, Long> {

}
