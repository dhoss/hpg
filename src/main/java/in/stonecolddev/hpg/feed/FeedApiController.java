package in.stonecolddev.hpg.feed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FeedApiController {

  private final Logger log = LoggerFactory.getLogger(FeedController.class);

  private final FeedRepository feedRepository;

  private final Clock clock;

  public FeedApiController(
      FeedRepository feedRepository,
      Clock clock) {

    this.feedRepository = feedRepository;
    this.clock = clock;
  }

  @PostMapping("/feed/seen")
  public HttpEntity<List<MarkUrlAsSeen>> markAsSeen(@RequestBody List<MarkUrlAsSeen> urls) {
    // TODO: write our own method for upserting instead of using saveAll
    feedRepository.saveAll(
        urls.stream().map(this::fromMarkUrlAsSeen)
            .collect(Collectors.toList()));

    return ResponseEntity.ok(urls);
  }

  private FeedUrlHistory fromMarkUrlAsSeen(MarkUrlAsSeen markUrlAsSeen) {
    return FeedUrlHistoryBuilder.builder()
        .created(OffsetDateTime.now(clock))
        .url(markUrlAsSeen.url().toString())
        .hash(markUrlAsSeen.hash())
        .build();
  }

}