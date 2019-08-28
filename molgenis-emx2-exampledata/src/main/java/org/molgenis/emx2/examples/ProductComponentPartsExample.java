package org.molgenis.emx2.examples;

import org.molgenis.utils.MolgenisException;
import org.molgenis.Row;
import org.molgenis.Schema;
import org.molgenis.Table;
import org.molgenis.SchemaMetadata;
import org.molgenis.TableMetadata;

import static org.molgenis.Type.INT;
import static org.molgenis.Type.STRING;

public class ProductComponentPartsExample {
  public static final String PART = "Part";
  public static final String COMPONENT = "Component";
  public static final String PRODUCT = "Product";
  public static final String WEIGHT = "weight";
  public static final String NAME = "name";
  public static final String PARTS = "parts";
  public static final String EXPLORER = "explorer";
  public static final String NAVIGATOR = "navigator";
  public static final String LOGIN = "login";
  public static final String FORMS = "forms";
  public static final String COMPONENTS = "components";

  private ProductComponentPartsExample() {
    // hide constructor
  }

  public static void create(SchemaMetadata schema) throws MolgenisException {

    TableMetadata partTable = schema.createTableIfNotExists(PART);
    partTable.addColumn(NAME, STRING);
    partTable.addColumn(WEIGHT, INT);
    partTable.addUnique(NAME);

    TableMetadata componentTable = schema.createTableIfNotExists(COMPONENT);
    componentTable.addColumn(NAME, STRING);
    componentTable.addUnique(NAME);
    componentTable.addRefArray(PARTS, PART, NAME);

    TableMetadata productTable = schema.createTableIfNotExists(PRODUCT);
    productTable.addColumn(NAME, STRING);
    productTable.addUnique(NAME);
    productTable.addRefArray(COMPONENTS, COMPONENT, NAME);
  }

  public static void populate(Schema schema) throws MolgenisException {

    Table partTable = schema.getTable(PART);
    Row part1 = new Row().setString(NAME, FORMS).setInt(WEIGHT, 100);
    Row part2 = new Row().setString(NAME, LOGIN).setInt(WEIGHT, 50);
    partTable.insert(part1);
    partTable.insert(part2);

    Table componentTable = schema.getTable(COMPONENT);
    Row component1 = new Row().setString(NAME, EXPLORER).setRefArray(PARTS, FORMS, LOGIN);
    Row component2 = new Row().setString(NAME, NAVIGATOR).setRefArray(PARTS, LOGIN);
    componentTable.insert(component1);
    componentTable.insert(component2);

    Table productTable = schema.getTable(PRODUCT);
    Row product1 =
        new Row().setString(NAME, "molgenis").setRefArray(COMPONENTS, EXPLORER, NAVIGATOR);

    productTable.insert(product1);
  }
}
