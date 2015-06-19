package fixture;

import model.Report;

import java.util.Date;


public class TestData {

    public static Report standardReport() {
        Report report = new Report();
        report.setStartDate(new Date());
        report.setEndDate(new Date());
        report.setPerformer("Bob");
        return report;
    }

}
