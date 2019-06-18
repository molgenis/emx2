package org.molgenis.emx2.graphql;

import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaPrinter;
import org.junit.Test;
import org.molgenis.MolgenisException;
import org.molgenis.Schema;
import org.molgenis.Table;
import org.molgenis.beans.SchemaBean;

import static org.molgenis.Column.Type.STRING;

public class EmxToGraphQLSchemaTest {

  @Test
  public void test1() throws MolgenisException {
    Schema m = new SchemaBean();

    Table t2 = m.createTable("Family");
    t2.addColumn("Name", STRING);

    Table t = m.createTable("Person");
    t.addColumn("FirstName", STRING);
    t.addColumn("LastName", STRING);
    t.addRef("family", t2);

    GraphQLSchema s = new GrahpqlEndpoint().getSchema(m);

    System.out.println(new SchemaPrinter().print(s));
  }
}