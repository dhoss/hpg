package in.stonecolddev.hpg.feed;

import io.soabase.recordbuilder.core.RecordBuilder;

import java.time.OffsetDateTime;

@RecordBuilder
public record FeedUrlHistory(
    int id,
    String url,
    String hash,
    OffsetDateTime created,
    OffsetDateTime updated
) implements FeedUrlHistoryBuilder.With {}