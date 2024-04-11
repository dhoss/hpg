package in.stonecolddev.hpg.feed;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URI;

@RecordBuilder
public record MarkUrlAsSeen(
    URI url
) implements MarkUrlAsSeenBuilder.With {

  public String hash() {
    return DigestUtils.sha256Hex(url.toString());
  }
}