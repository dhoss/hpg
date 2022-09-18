package in.stonecolddev.hpg.feed;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedController {

  @GetMapping("/")
  public String index() {
    return "index";
  }

}