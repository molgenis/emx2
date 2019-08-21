package org.molgenis;

import java.io.Serializable;

public interface Where {
  Query eq(Serializable... values);

  // major todo: add other filters

  Query contains(Serializable... values);

  Query search(String terms);

  // below move to implementation?

  Operator getOperator();

  Serializable[] getValues();

  String[] getPath();
}
