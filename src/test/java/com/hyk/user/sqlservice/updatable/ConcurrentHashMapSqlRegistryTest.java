package com.hyk.user.sqlservice.updatable;

import com.hyk.user.sqlservice.UpdatableSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {

  @Override
  protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
    return new ConcurrentHashMapSqlRegistry();
  }

}
