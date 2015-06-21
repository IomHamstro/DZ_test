import config.DataSourceTestConfig;
import config.PersistenceConfig;
import model.Report;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import repository.ReportRepository;

import java.sql.SQLException;
import java.util.List;

import static fixture.TestData.standardReport;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceTestConfig.class, PersistenceConfig.class})
public class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    @Test
    public void testFindAll() throws SQLException {
        Report newReport = standardReport();
        reportRepository.save(newReport);
        Iterable<Report> reports = reportRepository.findAll();
        assertNotNull(reports);
        assertTrue(reports.iterator().hasNext());
        for(Report report: reports) {
            assertNotNull(report);
            assertNotNull(report.getId());
        }
    }

    @Test
    public void testCRUD(){
        Report report = standardReport();
        reportRepository.save(report);
        report = reportRepository.findOne(report.getId());
        assertEquals(report.getPerformer(), "Bob");
        report.setPerformer("Mary");
        reportRepository.save(report);
        assertEquals(reportRepository.findOne(report.getId()).getPerformer(), "Mary");
        reportRepository.delete(report);
        assertNull(reportRepository.findOne(report.getId()));
    }

    @Test
    public void testFindAllPerformers() throws SQLException {
        Report newReport = standardReport();
        reportRepository.save(newReport);
        List<String> performers = reportRepository.findAllPerformers();
        assertNotNull(performers);
    }

}
