package in.stonecolddev.hpg.feed;

import io.soabase.recordbuilder.core.RecordBuilder;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Optional;

@RecordBuilder
public record FeedItem(
    int id,
    String title,
    URI link,
    String description,
    Optional<OffsetDateTime> published,
    OffsetDateTime indexed
) implements FeedItemBuilder.With {}