package kr.goldenmine.inumincraftlauncher.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@RequestMapping("/html")
public class HtmlController {

    @RequestMapping(
            value = "/onlyhtml",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ModelAndView onlyHtml() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("empty");
        return modelAndView;
    }
    @RequestMapping(
            value = "/empty",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String empty() {
        return "";
    }
}
