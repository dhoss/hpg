package in.stonecolddev.hpg.feed;

import io.soabase.recordbuilder.core.RecordBuilder;

import java.net.URI;


// TODO: it might be better to have type be an enum
@RecordBuilder
public record FeedSource(String name, URI uri, String type) implements FeedSourceBuilder.With {}