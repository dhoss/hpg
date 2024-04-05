package in.stonecolddev.hpg.feed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// TODO: tests
@Controller
public class FeedController {

  private final Logger log = LoggerFactory.getLogger(FeedController.class);

  private final FeedCache feedCache;

  public FeedController(FeedCache feedCache) {
    this.feedCache = feedCache;
  }

  @GetMapping("/")
  public String index(Model model) {
    log.debug("***** INDEX");
    // TODO: caching headers
    model.addAttribute("feeds", feedCache.list());
    return "index";
  }

}