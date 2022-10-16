package in.stonecolddev.hpg.feed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

// TODO: tests
@Controller
public class FeedController {

  private final Logger log = LoggerFactory.getLogger(FeedController.class);
  private final FeedService feedService;

  public FeedController(FeedService feedService) {
    this.feedService = feedService;
  }

  @GetMapping("/")
  public String index(Model model) throws SQLException, IOException {
    // TODO: caching headers
    List<Feed> all = feedService.all();
    log.debug("***** FEED {}", all);
    model.addAttribute("feeds", all);
    return "index";
  }
}