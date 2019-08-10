package org.molgenis.emx2.examples.synthetic;

import org.molgenis.*;

import static org.molgenis.Type.*;

public class CompositeRefExample {

  private CompositeRefExample() {
    // hide constructor
  }

  public static void createCompositeRefExample(Schema schema) throws MolgenisException {
    Type[] types = new Type[] {UUID, STRING, BOOL, INT, DECIMAL, TEXT, DATE, DATETIME};

    for (Type type : types) {
      String aTableName = type.toString() + "_A";
      Table aTable = schema.createTableIfNotExists(aTableName);
      String uniqueColumn1 = "AUnique" + type;
      String uniqueColumn2 = "AUnique" + type + "2";

      aTable.addColumn(uniqueColumn1, type);
      aTable.addColumn(uniqueColumn2, type);

      // we use MOLGENISID as primary key
      aTable.addUnique(uniqueColumn1, uniqueColumn2);

      String bTableName = type.toString() + "_B";
      Table bTable = schema.createTableIfNotExists(bTableName);
      String refFromBToA1 = "RefToAKeyOf" + type;
      String refFromBToA2 = "RefToAKeyOf" + type + "2";

      bTable
          .addRefMultiple(refFromBToA1, refFromBToA2)
          .to(aTableName, uniqueColumn1, uniqueColumn2);
    }
  }
}