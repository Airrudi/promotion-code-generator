package nl.ruudclaassen.jfall3.controller;

import nl.ruudclaassen.jfall3.services.CodeServiceImpl;
import nl.ruudclaassen.jfall3.general.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


@Controller
public class HomeController {

    @Autowired
    CodeServiceImpl codeService;

    @RequestMapping("/")
    public String home(ModelMap modelMap) {
        return Constants.HOMEPAGE;
    }
}
