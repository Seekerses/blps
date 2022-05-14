package ru.laboratory.blps.model.essay.repository;

import ru.laboratory.blps.model.essay.Essay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EssayRepository extends JpaRepository<Essay, Long> {
    List<Essay> getAllByCategory(String category);

    @Query(nativeQuery = true, value = "SELECT * FROM essay " +
            "WHERE ts @@ phraseto_tsquery('english', :phrase)")
    List<Essay> searchByPhrase(@Param("phrase") String phrase);
}
