package in.stonecolddev.hpg.configuration;

import io.soabase.recordbuilder.core.RecordBuilder;

import java.net.URI;

// TODO: it might be better to have type be an enum
public record FeedSource(String name, URI uri, String type) {}