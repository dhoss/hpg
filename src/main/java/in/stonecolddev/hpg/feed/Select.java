package in.stonecolddev.hpg.feed;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.stringtemplate.v4.ST;

// TODO: move this to its own namespace
// TODO: order by etc
@RecordBuilder
public record Select(
  String select,
  String from,
  String where,
  Integer limit
) implements SelectBuilder.With {

  @Override
  public String toString() {
    var queryTemplate = new ST(
      """
      select <select>
      from <from>
      <if(where)>where <where><endif>
      <if(limit)>limit <limit><endif>
      """
    );

    queryTemplate.add("select", select());
    queryTemplate.add("from", from());
    queryTemplate.add("where", where());
    queryTemplate.add("limit", limit());

    return queryTemplate.render();
  }
}