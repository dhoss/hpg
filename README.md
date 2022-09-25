# HPG

[![codecov](https://codecov.io/gh/dhoss/hpg/branch/master/graph/badge.svg?token=uAkVRjiI3C)](https://codecov.io/gh/dhoss/hpg)

A light feed aggregator with the eventual goal of replacing [Pocket](https://getpocket.com/).

## Eventual features (TODO)

 * Flexible feed aggregation
   * Just write a new ```FeedLoader```.  Eventually there will be functionality to iterate through FeedLoaders and pull them into the aggregation.
 * [archive.today](https://archive.today) integration
 * Non-RSS feed ingestion
   * Aggregate YouTube, etc. feeds
 * Rate limiting
 * Filters and feed tuning/feedback
   * some sort of ML magic to filter out items classified based on "good"/"bad" feedback (thumbs up/down)
 * browser extension for saving links
 * Android app for saving links via "Share"
 * Import various existing feeds
   * Pocket
 * Search
