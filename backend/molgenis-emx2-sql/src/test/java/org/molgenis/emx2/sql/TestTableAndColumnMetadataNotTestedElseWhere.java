package org.molgenis.emx2.sql;

import org.junit.BeforeClass;
import org.junit.Test;
import org.molgenis.emx2.*;

import static junit.framework.TestCase.*;
import static org.molgenis.emx2.Column.column;
import static org.molgenis.emx2.TableMetadata.table;

public class TestTableAndColumnMetadataNotTestedElseWhere {
  private static Database db;

  @BeforeClass
  public static void setUp() {
    db = TestDatabaseFactory.getTestDatabase();
  }

  @Test
  public void testDuplicateColumnError() {
    try {
      SchemaMetadata s = db.dropCreateSchema("testDuplicateColumnError").getMetadata();
      TableMetadata t = s.create(table("test").add(column("test")));
      System.out.println(t);

      t.add(column("test"));
      fail("should not be able to add same column twice");
    } catch (MolgenisException me) {
      System.out.println("Error correctly:\n" + me);
    }
  }

  @Test
  public void testAlterColumnName() {
    try {
      Schema s = db.dropCreateSchema("testAlterColumnName");
      Table t = s.create(table("test").add(column("test")));
      System.out.println(t);

      t.getMetadata().alterColumn("test", column("test2"));
      assertNull(t.getMetadata().getColumn("test"));
      assertNotNull(t.getMetadata().getColumn("test2"));

      t.insert(new Row().set("test", "value").set("test2", "value"));
      assertNull(t.getRows().get(0).getString("test"));
      assertEquals("value", t.getRows().get(0).getString("test2"));
    } catch (MolgenisException me) {
      System.out.println("Error unexpected:\n" + me);
    }
  }
}
