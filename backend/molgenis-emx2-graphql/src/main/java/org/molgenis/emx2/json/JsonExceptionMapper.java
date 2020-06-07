package org.molgenis.emx2.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.molgenis.emx2.MolgenisException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonExceptionMapper {

  private JsonExceptionMapper() {
    // hide public constructor
  }

  public static String molgenisExceptionToJson(MolgenisException e) {
    Map map = new LinkedHashMap();
    map.put("message", e.getMessage());

    List errorList = new ArrayList<>();
    errorList.add(map);

    Map error = new LinkedHashMap();
    error.put("errors", errorList);

    try {
      return JsonUtil.getWriter().writeValueAsString(error);
    } catch (JsonProcessingException ex) {
      return "ERROR CONVERSION FAILED " + ex;
    }
  }
}