package com.modesto.demoJPA.repository;

import com.modesto.demoJPA.domain.Placadevideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacadevideoRepository extends JpaRepository<Placadevideo, Long> {

}
