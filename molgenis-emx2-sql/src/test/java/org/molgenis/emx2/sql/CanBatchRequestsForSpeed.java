package org.molgenis.emx2.sql;

import org.junit.BeforeClass;
import org.junit.Test;
import org.molgenis.emx2.Database;
import org.molgenis.emx2.Row;
import org.molgenis.emx2.Table;
import org.molgenis.emx2.Query;
import org.molgenis.emx2.Schema;
import org.molgenis.emx2.*;
import org.molgenis.emx2.utils.MolgenisException;
import org.molgenis.emx2.utils.StopWatch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class CanBatchRequestsForSpeed {
  private static Database db;

  @BeforeClass
  public static void setUp() throws MolgenisException, SQLException {
    db = DatabaseFactory.getTestDatabase("molgenis", "molgenis");
  }

  @Test
  public void testBatch() throws MolgenisException {

    StopWatch.start("testBatch started");

    Schema schema = db.createSchema("testBatch");
    Table testBatchTable = schema.createTableIfNotExists("CanBatchRequestsForSpeed");
    testBatchTable.getMetadata().addColumn("test", Type.STRING).addColumn("testint", Type.INT);

    int size = 1000;
    StopWatch.print("Schema created");

    List<Row> rows = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      Row r = new Row();
      r.setString("test", "test" + i);
      r.setInt("testint", i);
      rows.add(r);
    }
    StopWatch.print("Generated " + size + " test records", size);

    testBatchTable.insert(rows.subList(0, 100));

    StopWatch.print("Inserted first batch", 100);

    testBatchTable.insert(rows.subList(100, 200));

    StopWatch.print("Inserted second batch", 100);

    testBatchTable.insert(rows.subList(200, 1000));

    StopWatch.print("Inserted third batch", 800);

    rows = testBatchTable.retrieve();
    assertEquals(1000, rows.size());
    for (Row r : rows) {
      r.setString("test", r.getString("test") + "_updated");
    }
    testBatchTable.update(rows);
    StopWatch.print("Batch update ", rows.size());

    StopWatch.print("Retrieved", schema.getTable("CanBatchRequestsForSpeed").retrieve().size());
  }

  @Test
  public void testCreate() throws MolgenisException {

    StopWatch.start("");

    Schema schema = db.createSchema("testCreate");

    String PERSON = "Person";
    Table personTable = schema.createTableIfNotExists(PERSON);

    personTable
        .getMetadata()
        .addColumn("First Name", Type.STRING)
        .setNullable(false)
        .addColumn("Last Name", Type.STRING)
        .addRef("Father", PERSON)
        .setNullable(true)
        .addUnique("First Name", "Last Name");

    // createColumn a fromTable
    // TODO need to optimize the reloading to be more lazy
    for (int i = 0; i < 10; i++) {
      Table personTable2 = schema.createTableIfNotExists(PERSON + i);
      personTable2
          .getMetadata()
          .addColumn("First Name", Type.STRING)
          .setNullable(false)
          .addColumn("Last Name", Type.STRING)
          .addRef("Father", personTable2.getName())
          .setNullable(true)
          .addUnique("First Name", "Last Name");
    }
    StopWatch.print("Created tables");

    // reinitialise database to see if it can recreate from background
    StopWatch.print("reloading database from disk");

    db.clearCache();
    schema = db.getSchema("testCreate");
    assertEquals(11, schema.getTableNames().size());
    StopWatch.print("reloading complete");

    // insert
    Table personTableReloaded = schema.getTable(PERSON);
    List<Row> rows = new ArrayList<>();
    int count = 1000;
    for (int i = 0; i < count; i++) {
      rows.add(new Row().setString("Last Name", "Duck" + i).setString("First Name", "Donald"));
    }
    System.out.println("Metadata" + personTableReloaded);
    personTableReloaded.insert(rows);

    StopWatch.print("insert", count);

    // queryOld
    Query q = schema.getTable(PERSON).query();
    StopWatch.print("QueryOld ", q.retrieve().size());

    // delete
    personTableReloaded.delete(rows);
    StopWatch.print("Delete", count);

    assertEquals(0, schema.getTable("Person").retrieve().size());
    assertEquals(1, personTableReloaded.getMetadata().getUniques().size());
    assertEquals(1, personTable.getMetadata().getUniques().size());
    try {
      personTable.getMetadata().removeUnique(Row.MOLGENISID);
      fail("you shouldn't be allowed to remove primary key unique constraint");
    } catch (Exception e) {
      // good stuff
    }
    assertEquals(1, personTable.getMetadata().getUniques().size());
    personTable.getMetadata().removeUnique("First Name", "Last Name");
    assertEquals(0, personTable.getMetadata().getUniques().size());

    assertEquals(4, personTable.getMetadata().getColumns().size());
    try {
      personTable.getMetadata().removeColumn(Row.MOLGENISID);
      fail("you shouldn't be allowed to remove primary key column");
    } catch (Exception e) {
      // good stuff
    }
    personTable.getMetadata().removeColumn("Father");
    assertEquals(3, personTable.getMetadata().getColumns().size());

    // drop a fromTable
    db.getSchema("testCreate").getMetadata().dropTable(personTable.getName());
    try {
      db.getSchema("testCreate").getTable(personTable.getName());
      fail("should have been dropped");
    } catch (Exception e) { // expected
    }

    // make sure nothing was left behind in backend
    db.clearCache();
    try {
      assertEquals(null, db.getSchema("testCreate").getTable(personTable.getName()));
      fail("should have been dropped");
    } catch (Exception e) { // expected
    }
  }
}