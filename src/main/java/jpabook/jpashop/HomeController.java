package jpabook.jpashop;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    // lombok에서 제공하는 @Slf4j와 같은 효과가 있다.
    // Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/")
    public String home {
        log.info("home controller");
        return "home";
    }


}
