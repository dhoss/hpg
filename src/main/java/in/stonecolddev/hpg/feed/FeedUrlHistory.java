package in.stonecolddev.hpg.feed;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;

@RecordBuilder
public record FeedUrlHistory(
    @Id
    int id,
    String url,
    String hash,
    OffsetDateTime created,
    OffsetDateTime updated
) implements FeedUrlHistoryBuilder.With {}