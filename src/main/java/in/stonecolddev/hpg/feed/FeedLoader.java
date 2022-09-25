package in.stonecolddev.hpg.feed;

import in.stonecolddev.hpg.configuration.FeedSource;

import java.io.IOException;

// TODO: implement a file loader so you can upload a feed export and have it ingest everything
public interface FeedLoader {

  Feed loadFeed(FeedSource feedSource) throws IOException;

}