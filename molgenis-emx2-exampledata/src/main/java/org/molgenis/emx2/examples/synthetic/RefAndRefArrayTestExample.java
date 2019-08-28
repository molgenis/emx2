package org.molgenis.emx2.examples.synthetic;

import org.molgenis.SchemaMetadata;
import org.molgenis.TableMetadata;
import org.molgenis.Type;
import org.molgenis.utils.MolgenisException;

import static org.molgenis.Type.*;
import static org.molgenis.Type.DATETIME;

public class RefAndRefArrayTestExample {
  private RefAndRefArrayTestExample() {
    // hide constructor
  }

  public static void createRefAndRefArrayTestExample(SchemaMetadata schema)
      throws MolgenisException {

    Type[] types = new Type[] {UUID, STRING, BOOL, INT, DECIMAL, TEXT, DATE, DATETIME};

    for (Type type : types) {

      String aTableName = type.toString() + "_A";
      TableMetadata aTable = schema.createTableIfNotExists(aTableName);
      String fieldName = "AKeyOf" + type;
      aTable.addColumn(fieldName, type);
      aTable.addUnique(fieldName);

      String bTableName = type.toString() + "_B";
      TableMetadata bTable = schema.createTableIfNotExists(bTableName);
      String refFromBToA = "RefToAKeyOf" + type;
      String refArrayFromBToA = "RefArrayToAKeyOf" + type;
      bTable.addRef(refFromBToA, aTableName, fieldName);
      bTable.addRefArray(refArrayFromBToA, aTableName, fieldName);
    }
  }
}
