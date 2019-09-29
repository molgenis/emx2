package org.molgenis.emx2;

public enum DefaultRoles {
  MEMBER("Member"),
  EDITOR("Editor"),
  MANAGER("Manager"),
  OWNER("Owner");

  private String name;

  DefaultRoles(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}