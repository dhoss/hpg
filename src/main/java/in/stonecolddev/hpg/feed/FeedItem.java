package in.stonecolddev.hpg.feed;

import io.soabase.recordbuilder.core.RecordBuilder;

import java.net.URI;
import java.time.OffsetDateTime;

@RecordBuilder
public record FeedItem(
    int id,
    String title,
    URI link,
    String description,
    OffsetDateTime published,
    OffsetDateTime indexed
) {}