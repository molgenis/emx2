package org.molgenis.emx2;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;
import com.fasterxml.uuid.Generators;

public class EmxRow {
  private Map<String, Object> valueMap;

  public EmxRow() {
    this(Generators.timeBasedGenerator().generate());
  }

  public static final String MOLGENISID = "molgenisid";
  protected Map<String, Object> values = new LinkedHashMap<>();

  public EmxRow(Map<String, Object> values) {
    this();
    this.values.putAll(values);
  }

  public EmxRow(UUID id) {
    setRowID(id);
  }

  public UUID getRowID() {
    return (UUID) values.get(MOLGENISID);
  }

  public EmxRow setRowID(UUID id) {
    this.setRef(MOLGENISID, id);
    return this;
  }

  public UUID getUuid(String columnId) {
    return (UUID) this.values.get(columnId);
  }

  public String getString(String name) {
    return (String) values.get(name);
  }

  public Integer getInt(String name) {
    return (Integer) values.get(name);
  }

  public Boolean getBool(String name) {
    return (Boolean) values.get(name);
  }

  public Double getDecimal(String name) {
    return (Double) values.get(name);
  }

  public String getText(String name) {
    return (String) values.get(name);
  }

  public LocalDate getDate(String name) {
    if (values.get(name) == null) return null;
    if (values.get(name) instanceof Date) {
      return LocalDate.parse(values.get(name).toString());
    } else if (values.get(name) instanceof OffsetDateTime) {
      return ((OffsetDateTime) values.get(name)).toLocalDate();
    } else {
      return (LocalDate) values.get(name);
    }
  }

  public List<EmxRow> getMref(String name) {
    return (List<EmxRow>) values.get(name);
  }

  public OffsetDateTime getDateTime(String name) {
    return (OffsetDateTime) values.get(name);
  }

  public UUID getRef(String name) {
    return (UUID) values.get(name);
  }

  public EmxRow setString(String name, String value) {
    values.put(name, value);
    return this;
  }

  public EmxRow setInt(String name, Integer value) {
    values.put(name, value);
    return this;
  }

  public EmxRow setRef(String name, EmxRow value) {
    values.put(name, value.getRowID());
    return this;
  }

  public EmxRow setRef(String name, UUID value) {
    values.put(name, value);
    return this;
  }

  public EmxRow setDecimal(String columnId, Double value) {
    values.put(columnId, value);
    return this;
  }

  public EmxRow setBool(String columnId, Boolean value) {
    values.put(columnId, value);
    return this;
  }

  public EmxRow setDate(String columnId, LocalDate value) {
    values.put(columnId, value);
    return this;
  }

  public EmxRow setDateTime(String columnId, OffsetDateTime value) {
    values.put(columnId, value);
    return this;
  }

  public EmxRow setText(String columnId, String value) {
    values.put(columnId, value);
    return this;
  }

  public EmxRow setUuid(String columnId, UUID value) {
    values.put(columnId, value);
    return this;
  }

  public List values(String... columns) {
    List<Object> result = new ArrayList<>();
    for (String name : columns) {
      result.add(values.get(name));
    }
    return Collections.unmodifiableList(result);
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ROW(");
    for (Map.Entry<String, Object> col : values.entrySet()) {
      builder.append(col.getKey()).append("='").append(col.getValue()).append("' ");
    }
    builder.append(")");
    return builder.toString();
  }

  public EmxRow setMref(String columnName, List<EmxRow> mrefList) {
    values.put(columnName, mrefList);
    return this;
  }

  public Map<String, Object> getValueMap() {
    return values;
  }

  public EmxRow setMref(String columnName, EmxRow... mrefs) {
    return this.setMref(columnName, Arrays.asList(mrefs));
  }

  public Collection<String> getColumns() {
    return values.keySet();
  }
}