package in.stonecolddev.hpg.feed;

import com.apptasticsoftware.rssreader.RssReader;
import in.stonecolddev.hpg.configuration.FeedSource;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RssFeedLoader implements FeedLoader {

  private final Logger log = LoggerFactory.getLogger(RssFeedLoader.class);

  private final Clock clock;

  private final RssReader rssReader;


  // TODO: write tests for this
  // Thu, 01 Sep 2022 08:25:29 +0000
  // EEE, dd MMM yyyy HH:mm:ss Z
  // Fri, 5 Apr 2024 16:21:49 +0000
  // EEE  d MMM yyyy HH:mm:ss Z
  // 2024-02-20T09:19:00.005Z
  //
  // 2022-10-06T12:18:00.004+01:00
  private final DateTimeFormatterBuilder dateTimeFormatterBuilder =
      new DateTimeFormatterBuilder()
          .append(
              DateTimeFormatter.ofPattern(
                  "[yyyy-MM-dd'T'HH:mm:ss.SSSXXX]" +
                  "[EEE, dd MMM yyyy HH:mm:ss Z]"  +
                  "[EEE, d MMM yyyy HH:mm:ss Z]"));

  public RssFeedLoader(
    RssReader rssReader,
    Clock clock
  ) {
    this.rssReader = rssReader;
    this.clock = clock;
  }

  // TODO: ideally this will be enqueued and then called by a job executor
  public Feed loadFeed(FeedSource feedSource) throws IOException {
    log.info("Loading feed {}", feedSource.name());
    return FeedBuilder.builder()
             .name(feedSource.name())
             .items(
               rssReader.read(feedSource.uri().toString())
                 .map(
                   i ->
                     FeedItemBuilder.builder()
                       .title(i.getTitle().orElseThrow(
                         () -> new IllegalArgumentException("No title provided")))
                       .link(URI.create(i.getLink().orElseThrow(
                         () -> new IllegalArgumentException("No link provided"))))
                       .description(
                         maybeCleanText(i.getDescription()).orElse("(no description provided)"))
                       .indexed(OffsetDateTime.now(clock))
                       .published(maybeSafeDateTime(i.getPubDate()))
                       .build())
                 .collect(Collectors.toList()))
             .build();
  }

  private Optional<String> maybeCleanText(Optional<String> text) {
    return text.map(t -> StringUtils.truncate(t, 300) + "...") // TODO: make description length configurable
               .map(t -> Jsoup.clean(t, Safelist.basic().removeTags("ul", "ol", "li")));
  }

  private Optional<OffsetDateTime> maybeSafeDateTime(Optional<String> dateTimeString) {
    try {
      return dateTimeString.map(d -> OffsetDateTime.parse(d, dateTimeFormatterBuilder.toFormatter()));
    } catch (java.time.format.DateTimeParseException e) {
      log.error("Unable to parse String to OffsetDateTime: {}", e.getMessage());
      return Optional.empty();
    }
  }
}