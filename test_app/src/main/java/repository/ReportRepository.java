package repository;

import model.Report;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long>, JpaSpecificationExecutor<Report> {

    @Transactional
    @Query("select DISTINCT r.performer from Report r ")
    List<String> findAllPerformers();

}
