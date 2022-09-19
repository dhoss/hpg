package in.stonecolddev.hpg.feed;

import java.net.URI;
import java.time.OffsetDateTime;

// TODO: replace with AutoValue
public record FeedItem(
    int id,
    String title,
    URI link,
    String description,
    OffsetDateTime published,
    OffsetDateTime indexed
) {}