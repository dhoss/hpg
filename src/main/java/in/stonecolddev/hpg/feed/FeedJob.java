package in.stonecolddev.hpg.feed;

import io.soabase.recordbuilder.core.RecordBuilder;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@RecordBuilder
public record FeedJob(
  Integer id, UUID jobId, FeedSource feedSource, OffsetDateTime created, OffsetDateTime updated)
 implements FeedJobBuilder.With {

  public FeedJob(FeedSource feedSource) {
    this(0, UUID.randomUUID(), feedSource, OffsetDateTime.now(), null);
  }

  public UUID jobId() {
    return Optional.ofNullable(this.jobId).orElseGet(UUID::randomUUID);
  }

}