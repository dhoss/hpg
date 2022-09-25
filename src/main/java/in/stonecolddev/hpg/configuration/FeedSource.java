package in.stonecolddev.hpg.configuration;

import java.net.URI;

// TODO: it might be better to have type be an enum
public record FeedSource(String name, URI uri, String type) {}