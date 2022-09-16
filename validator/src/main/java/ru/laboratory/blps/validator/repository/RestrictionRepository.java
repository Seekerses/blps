package ru.laboratory.blps.validator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.laboratory.blps.validator.Restriction;

@Repository
public interface RestrictionRepository extends JpaRepository<Restriction, Long> {
}
