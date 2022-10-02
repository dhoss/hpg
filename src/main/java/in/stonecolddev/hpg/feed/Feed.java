package in.stonecolddev.hpg.feed;

import io.soabase.recordbuilder.core.RecordBuilder;

import java.util.List;

@RecordBuilder
public record Feed(String name, List<FeedItem> items) implements FeedBuilder.With {}