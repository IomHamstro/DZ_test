package controller;

import service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    ReportService reportService;

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index", "performers", reportService.getAllPerformers() );
    }
}
