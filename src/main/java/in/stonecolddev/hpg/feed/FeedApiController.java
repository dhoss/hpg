package in.stonecolddev.hpg.feed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FeedApiController {

  private final Logger log = LoggerFactory.getLogger(FeedController.class);

  @PostMapping("/feed/seen")
  public HttpEntity<List<MarkUrlAsSeen>> markAsSeen(@RequestBody List<MarkUrlAsSeen> urls) {
    return ResponseEntity.ok(urls);
  }

}