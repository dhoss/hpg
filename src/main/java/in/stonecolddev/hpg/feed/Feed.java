package in.stonecolddev.hpg.feed;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;
import java.util.List;

@RecordBuilder
public record Feed(
  @Id
  Integer id,
  String name,
  List<FeedItem> items,
  OffsetDateTime updatedOn,
  OffsetDateTime createdOn) implements FeedBuilder.With {}