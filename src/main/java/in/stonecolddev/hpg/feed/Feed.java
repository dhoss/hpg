package in.stonecolddev.hpg.feed;

import io.soabase.recordbuilder.core.RecordBuilder;

import java.time.OffsetDateTime;
import java.util.List;

@RecordBuilder
public record Feed(
  String name,
  List<FeedItem> items,
  OffsetDateTime updatedOn,
  OffsetDateTime createdOn) implements FeedBuilder.With {}