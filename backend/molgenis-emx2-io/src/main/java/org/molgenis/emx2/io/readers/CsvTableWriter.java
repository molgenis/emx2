package org.molgenis.emx2.io.readers;

import java.io.IOException;
import java.io.Writer;
import java.util.*;
import org.molgenis.emx2.Row;
import org.simpleflatmapper.csv.CsvWriter;

public class CsvTableWriter {

  private CsvTableWriter() {
    // hide constructor
  }

  public static void write(Iterable<Row> rows, Writer writer, Character seperator)
      throws IOException {

    // get most extensive headers
    Set<String> columnNames = new LinkedHashSet<>();
    for (Row r : rows) {
      columnNames.addAll(r.getColumnNames());
    }

    CsvWriter.CsvWriterDSL<Map> writerDsl =
        CsvWriter.from(Map.class).columns(columnNames.toArray(new String[columnNames.size()]));

    CsvWriter<Map> csvWriter = writerDsl.separator(seperator).to(writer);
    for (Row r : rows) {
      // fromReader all values into strings first
      Map<String, String> values = new LinkedHashMap<>();
      for (String columnName : r.getColumnNames()) {
        values.put(columnName, r.getString(columnName));
      }
      csvWriter.append(values);
    }
  }
}
