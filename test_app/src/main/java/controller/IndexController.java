package controller;

import controller.util.DateUtil;
import model.Report;
import org.springframework.web.bind.annotation.*;
import service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import viewobject.ReportVO;

import java.util.*;

@Controller
public class IndexController {


    @Autowired
    ReportService reportService;

    @ModelAttribute("report")
    public ReportVO getReportObject() {
        return new ReportVO();
    }

    @ModelAttribute("performers")
    public List<String> allPerformers() {
        List<String> performers = this.reportService.getAllPerformers();

        return performers;
    }

    @ModelAttribute("timePeriod")
    public HashMap<Integer, String> getTimePeriod() {
        return DateUtil.timePeriod();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView getReports(@RequestParam(value = "startDate", required = false) String startDate,
                                   @RequestParam(value = "endDate", required = false) String endDate,
                                   @RequestParam(value = "performer", required = false) String performer,
                                   @RequestParam(value = "timePeriod", required = false) String period) {

        ModelAndView mv = new ModelAndView("index");

        //  get Date objects according to the period
        DateUtil.Range range = DateUtil.getDates(startDate, endDate, period);

        // check dates for correctness
        if(DateUtil.standardDate().equals(range.getStartDate()) || DateUtil.standardDate().equals(range.getEndDate())){
            mv.addObject("error","Проверьте правильность написания дат");
        }
        else {
            List<Report> reports = reportService.getAllReports(range.getStartDate(),range.getEndDate(),performer);
            if(reports != null && !reports.isEmpty()){
                mv.addObject("reports", reports);
            }
            else {
                mv.addObject("error", "По данном запросу записей не найдено");
            }
        }

        return mv;
    }


}
