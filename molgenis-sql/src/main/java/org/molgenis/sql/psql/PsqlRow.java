package org.molgenis.sql.psql;

import org.jooq.Field;
import org.jooq.Record;
import org.molgenis.sql.SqlRow;

import java.util.UUID;

public class PsqlRow extends SqlRow {

  public PsqlRow() {
    super();
  }

  public PsqlRow(Record record) {
    super();
    for (Field f : record.fields()) {
      values.put(f.getName(), record.get(f));
    }
  }

  public PsqlRow(UUID molgenisid) {
    super(molgenisid);
  }
}