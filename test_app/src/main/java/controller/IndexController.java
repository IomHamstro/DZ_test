package controller;

import model.Report;
import org.springframework.web.bind.annotation.*;
import service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    ReportService reportService;

    @ModelAttribute("report")
    public Report getReportObject() {
        return new Report();
    }

    @ModelAttribute("performers")
    public List<String> allPerformers() {
        List<String> performers = this.reportService.getAllPerformers();

        return performers;
    }

    @ModelAttribute("timePeriod")
    public HashMap<Integer,String> getTimePeriod() {
      return timePeriod();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Map<String, Object> model){
        model.put("reports", new Report());
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView getReports(Report report){
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("reports",
                reportService.getAllReports(report.getStartDate(),
                        report.getEndDate(), report.getPerformer()));
        return mv;
    }

    private static HashMap<Integer,String> timePeriod(){
        HashMap<Integer,String> periods = new HashMap<Integer,String>();
        periods.put(0,"");
        periods.put(1,"LastQtr");
        periods.put(2,"Last Month");
        periods.put(3,"Last Calendar Year");
        periods.put(4,"Current Year to Date");
        periods.put(5,"Current Qtr to Date");
        periods.put(6,"Current Month to Date");


        return periods;
    }
}
