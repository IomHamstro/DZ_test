package service.Impl;

import model.Report;
import model.ReportSpecs;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import repository.ReportRepository;
import service.ReportService;

import javax.persistence.criteria.Expression;
import java.util.Date;
import java.util.List;

/**
 * Created by otelezhnikova on 18.06.2015.
 */

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepository reportRepository;


    public List<String> getAllPerformers() {
        return reportRepository.findAllPerformers();
    }

    public List<Report> getAllReports(Date startDate, Date endDate, String performers) {
            return reportRepository.findAll(Specifications.where(ReportSpecs.checkParams(startDate, endDate, performers)));


    }
}
