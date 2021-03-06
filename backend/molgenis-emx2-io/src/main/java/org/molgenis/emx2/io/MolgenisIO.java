package org.molgenis.emx2.io;

import static org.molgenis.emx2.io.emx2.Emx2.inputMetadata;
import static org.molgenis.emx2.io.emx2.Emx2.outputMetadata;
import static org.molgenis.emx2.io.emx2.Emx2Roles.inputRoles;
import static org.molgenis.emx2.io.emx2.Emx2Roles.outputRoles;
import static org.molgenis.emx2.io.emx2.Emx2Settings.outputSettings;
import static org.molgenis.emx2.io.emx2.Emx2Tables.inputTable;
import static org.molgenis.emx2.io.emx2.Emx2Tables.outputTable;

import java.nio.file.Path;
import org.molgenis.emx2.Schema;
import org.molgenis.emx2.Table;
import org.molgenis.emx2.io.emx1.Emx1;
import org.molgenis.emx2.io.tablestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MolgenisIO {

  private static final Logger logger = LoggerFactory.getLogger(MolgenisIO.class.getName());

  private MolgenisIO() {
    // hide constructor
  }

  static void inputAll(TableStore store, Schema schema) {
    long start = System.currentTimeMillis();
    schema.tx(
        db -> {
          // read emx1 metadata, if available (to be removed in future versions)
          if (store.containsTable("attributes")) {
            Emx1.uploadFromStoreToSchema(store, schema);
          } else if (store.containsTable("molgenis")) {
            inputMetadata(store, schema);
          }

          for (Table table : schema.getTablesSorted()) {
            inputTable(store, table);
          }

          // read settings

          // read user roles
          inputRoles(store, schema);
        });
    if (logger.isInfoEnabled()) {
      logger.info("Import transaction completed in {0}ms", System.currentTimeMillis() - start);
    }
  }

  private static void outputAll(TableStore store, Schema schema) {
    outputMetadata(store, schema);
    outputRoles(store, schema);
    outputSettings(store, schema);
    for (String tableName : schema.getTableNames()) {
      outputTable(store, schema.getTable(tableName));
    }
  }

  public static void toDirectory(Path directory, Schema schema) {
    outputAll(new TableStoreForCsvFilesDirectory(directory), schema);
  }

  public static void toZipFile(Path zipFile, Schema schema) {
    outputAll(new TableStoreForCsvInZipFile(zipFile), schema);
  }

  public static void toExcelFile(Path excelFile, Schema schema) {
    outputAll(new TableStoreForXlsxFile(excelFile), schema);
  }

  public static void toEmx1ExcelFile(Path excelFile, Schema schema) {
    executeEmx1Export(new TableStoreForXlsxFile(excelFile), schema);
  }

  private static void executeEmx1Export(TableStore store, Schema schema) {
    // write metadata
    store.writeTable("entities", Emx1.getEmx1Entities(schema.getMetadata()));
    store.writeTable("attributes", Emx1.getEmx1Attributes(schema.getMetadata()));
    // write data
    for (String tableName : schema.getTableNames()) {
      outputTable(store, schema.getTable(tableName));
    }
  }

  public static void toZipFile(Path zipFile, Table table) {
    outputTable(new TableStoreForCsvInZipFile(zipFile), table);
  }

  public static void toExcelFile(Path excelFile, Table table) {
    outputTable(new TableStoreForXlsxFile(excelFile), table);
  }

  public static void toCsvFile(Path csvFile, Table table) {
    outputTable(new TableStoreForCsvFile(csvFile), table);
  }

  public static void fromDirectory(Path directory, Schema schema) {
    inputAll(new TableStoreForCsvFilesDirectory(directory), schema);
  }

  public static void fromZipFile(Path zipFile, Schema schema) {
    inputAll(new TableStoreForCsvInZipFile(zipFile), schema);
  }

  public static void importFromExcelFile(Path excelFile, Schema schema) {
    inputAll(new TableStoreForXlsxFile(excelFile), schema);
  }
}
