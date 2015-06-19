package controller;

import org.springframework.web.bind.annotation.PathVariable;
import service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    ReportService reportService;

    @RequestMapping("/")
    public ModelAndView getReports(@PathVariable Date startDate, Date endDate, String performer) {
                ModelAndView mv = new ModelAndView("index");
        mv.addObject("reports", reportService.getAllReports(startDate,endDate,performer));
        return mv;
    }
}
