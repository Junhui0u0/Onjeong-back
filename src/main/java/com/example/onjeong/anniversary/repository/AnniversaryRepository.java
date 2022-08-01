package com.example.onjeong.anniversary.repository;

import com.example.onjeong.anniversary.domain.Anniversary;
import com.example.onjeong.family.domain.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnniversaryRepository extends JpaRepository<Anniversary,Long> {
    //Map<LocalDate,Anniversary> findAllByAnniversaryDateBetween(LocalDate start, LocalDate end);
    Optional<List<Anniversary>> findAllByAnniversaryDateBetweenAndFamily(LocalDate start, LocalDate end, Family family);
    Optional<List<Anniversary>> findAllByAnniversaryDateAndFamily(LocalDate anniversaryDate, Family family);
    Optional<Anniversary> findByAnniversaryIdAndFamily(Long anniversaryId, Family family);
    String deleteByAnniversaryIdAndFamily(Long anniversaryId, Family family);
}
