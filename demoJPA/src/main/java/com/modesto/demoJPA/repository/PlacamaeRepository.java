package com.modesto.demoJPA.repository;

import com.modesto.demoJPA.domain.Placamae;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacamaeRepository extends JpaRepository<Placamae, Long> {

}
