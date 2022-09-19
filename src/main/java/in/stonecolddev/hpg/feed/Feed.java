package in.stonecolddev.hpg.feed;

import java.util.List;

public record Feed(String name, List<FeedItem> items) {}