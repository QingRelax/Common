package com.common.rowmapper;

import java.sql.ResultSet;

public interface RowMapper  {
  public Object mapRow(ResultSet set);

}
