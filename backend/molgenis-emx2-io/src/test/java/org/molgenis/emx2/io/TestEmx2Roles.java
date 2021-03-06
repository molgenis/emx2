package org.molgenis.emx2.io;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.molgenis.emx2.Database;
import org.molgenis.emx2.Member;
import org.molgenis.emx2.Schema;
import org.molgenis.emx2.io.emx2.Emx2Roles;
import org.molgenis.emx2.io.tablestore.TableStore;
import org.molgenis.emx2.io.tablestore.TableStoreForCsvInMemory;
import org.molgenis.emx2.sql.TestDatabaseFactory;

public class TestEmx2Roles {
  private static Schema schema;

  @BeforeClass
  public static void setup() {
    Database database = TestDatabaseFactory.getTestDatabase();
    schema = database.dropCreateSchema(TestEmx2Roles.class.getSimpleName());
  }

  @Test
  public void testRolesIO() {
    // create user roles
    schema.addMember("bofke", "Viewer");
    TableStore store = new TableStoreForCsvInMemory();

    // export
    Emx2Roles.outputRoles(store, schema);

    // empty the database, verify
    schema = schema.getDatabase().dropCreateSchema(TestEmx2Roles.class.getSimpleName());
    assertEquals(0, schema.getMembers().size());

    // import and see if consistent
    Emx2Roles.inputRoles(store, schema);
    List<Member> members = schema.getMembers();
    assertEquals("bofke", members.get(0).getUser());
    assertEquals("Viewer", members.get(0).getRole());
  }
}
