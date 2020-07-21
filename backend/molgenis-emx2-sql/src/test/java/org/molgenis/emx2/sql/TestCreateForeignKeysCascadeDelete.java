package org.molgenis.emx2.sql;

import org.junit.BeforeClass;
import org.junit.Test;
import org.molgenis.emx2.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.molgenis.emx2.Column.column;
import static org.molgenis.emx2.ColumnType.*;
import static org.molgenis.emx2.TableMetadata.table;

public class TestCreateForeignKeysCascadeDelete {

  static Database db;

  @BeforeClass
  public static void setup() {
    db = TestDatabaseFactory.getTestDatabase();
  }

  @Test
  public void testInt() {
    executeTest(INT, 5, 6);
  }

  @Test
  public void testString() {
    executeTest(STRING, "test", "DependencyOrderOutsideTransactionFails");
  }

  @Test
  public void testDate() {
    executeTest(DATE, "2013-01-01", "2013-01-02");
  }

  @Test
  public void testDateTime() {
    executeTest(DATETIME, "2013-01-01T18:00:00", "2013-01-01T18:00:01");
  }

  @Test
  public void testDecimal() {
    executeTest(DECIMAL, 5.0, 6.0);
  }

  @Test
  public void testText() {
    executeTest(TEXT, "This is a hello world", "This is a hello back to you");
  }

  @Test
  public void testUUID() {
    executeTest(
        UUID, "f83133cc-aeaa-11e9-a2a3-2a2ae2dbcce4", "f83133cc-aeaa-11e9-a2a3-2a2ae2dbcce5");
  }

  private void executeTest(ColumnType columnType, Object insertValue, Object updateValue) {

    Schema schema =
        db.dropCreateSchema("TestCreateForeignKeysCascade" + columnType.toString().toUpperCase());

    String fieldName = "AKeyOf" + columnType;
    Table aTable = schema.create(table("A").add(column(fieldName).type(columnType).pkey()));
    Row aRow = new Row().set(fieldName, insertValue);
    aTable.insert(aRow);

    String refFromBToA = "RefToAKeyOf" + columnType;
    Table bTable =
        schema.create(
            table("B")
                .add(column("ID").type(INT).pkey())
                // only difference with other test
                .add(column(refFromBToA).type(REF).refTable("A").cascadeDelete(true).pkey()));
    Row bRow = new Row().setInt("ID", 2).set(refFromBToA, insertValue);
    bTable.insert(bRow);

    // insert to non-existing value should fail
    Row bErrorRow = new Row().setInt("ID", 3).set(refFromBToA, updateValue);
    try {
      bTable.insert(bErrorRow);
      fail("insert should fail because value is missing");
    } catch (Exception e) {
      System.out.println("insert exception correct: \n" + e);
    }

    // and update, should be cascading :-)
    // aTable.update(aRow.set(fieldName, updateValue));

    // delete of A should cascade
    try {
      aTable.delete(aRow);
      assertEquals(0, bTable.query().getRows().size());
    } catch (Exception e) {
      fail("delete should cascade because cascadeDelete was set");
    }
  }
}
