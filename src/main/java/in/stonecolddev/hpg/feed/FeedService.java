package in.stonecolddev.hpg.feed;


import org.springframework.stereotype.Component;

@Component
public class FeedService {

  private final FeedRepository feedRepository;

  public FeedService(DefaultFeedRepository feedRepository) {
    this.feedRepository = feedRepository;
  }

  public Feed save(Feed feed) {
    return FeedBuilder.builder().build();//feedRepository.save(feed);
  }

}