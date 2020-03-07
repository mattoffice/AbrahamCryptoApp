package abraham.crypto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/js")
public class JSTestController {

    @GetMapping("/test")
    public ModelAndView testView(Map<String, Object> model) {
        model.put("number", 1234);
        model.put("message", "hdfhjdskhfjsdhfjksd");
        return new ModelAndView("index");
    }
}
