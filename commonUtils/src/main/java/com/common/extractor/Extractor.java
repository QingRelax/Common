package com.common.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Extractor  {
  public Object extract(ResultSet set) throws SQLException;

}
