package org.molgenis;

import org.molgenis.annotations.ColumnMetadata;
import org.molgenis.beans.RowBean;
import org.molgenis.beans.TableBean;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static org.molgenis.Column.Type.*;

public class Mapper {

  public static Row[] map(Object... beans) {
    ArrayList<Row> rows = new ArrayList<>();
    try {
      Class c = beans[0].getClass();
      Field[] fields = c.getDeclaredFields();

      for (Object b : beans) {
        Map<String, Object> values = new LinkedHashMap<>();
        for (Field f : fields) {
          f.setAccessible(true);
          values.put(f.getName(), f.get(b));
        }
        rows.add(new RowBean(values));
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException("this should never happen");
    }
    return rows.toArray(new Row[rows.size()]);
  }

  public static <E> E map(Class<E> klazz, Row row) throws MolgenisException {
    try {
      E e = klazz.newInstance();
      Map<String, Object> values = row.getValueMap();
      for (String name : row.getColumns()) {
        Object value = values.get(name);
        if (value != null) {
          Method m =
              klazz.getMethod(
                  "set" + name.substring(0, 1).toUpperCase() + name.substring(1), value.getClass());
          m.invoke(e, values.get(name));
        }
      }
      return e;
    } catch (Exception e) {
      throw new MolgenisException(e);
    }
  }

  public static Table map(Class klazz) throws MolgenisException {
    Table t = new TableBean(klazz.getSimpleName());
    Field[] fields = klazz.getDeclaredFields();
    for (Field f : fields) {
      try {
        if (!f.getName().contains("jacocoData")) {
          Column col = t.addColumn(f.getName(), typeOf(f.getType()));
          if (f.isAnnotationPresent(ColumnMetadata.class)) {
            ColumnMetadata cm = f.getAnnotation(ColumnMetadata.class);
            col.setNullable(cm.nullable());
            col.setDescription(cm.description());
          }
          if (REF.equals(col.getType())) {
            // big todo, fake table. Need singleton or lazyload before whole world is loaded in
            // one go
            col.setRefTable(new TableBean(f.getType().getSimpleName()));
          }
          if (ENUM.equals(col.getType())) {
            // big todo: get the enum values from the enum

          }
        }
      } catch (Exception e) {
        throw new MolgenisException("Failed to map field " + f.getName(), e);
      }
    }
    return t;
  }

  private static Column.Type typeOf(Class<?> type) throws MolgenisException {
    if (type.isEnum()) return ENUM;
    if (type.equals(String.class)) return STRING;
    if (type.equals(UUID.class)) return UUID;
    if (type.equals(Boolean.class) || type.equals(boolean.class)) return BOOL;
    if (Identifiable.class.isAssignableFrom(type)) return REF;
    throw new MolgenisException("Failed to map type " + type.getSimpleName());
  }
}