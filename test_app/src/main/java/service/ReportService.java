package service;

import model.Report;

import java.util.Date;
import java.util.List;

public interface ReportService {
    List<String> getAllPerformers();
    List<Report> getAllReports(Date startDate, Date endDate,String performers);

}