package com.remcalendar.data.repository;

import com.remcalendar.data.dao.AnniversaryDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class AnniversaryRepository_Factory implements Factory<AnniversaryRepository> {
  private final Provider<AnniversaryDao> anniversaryDaoProvider;

  public AnniversaryRepository_Factory(Provider<AnniversaryDao> anniversaryDaoProvider) {
    this.anniversaryDaoProvider = anniversaryDaoProvider;
  }

  @Override
  public AnniversaryRepository get() {
    return newInstance(anniversaryDaoProvider.get());
  }

  public static AnniversaryRepository_Factory create(
      Provider<AnniversaryDao> anniversaryDaoProvider) {
    return new AnniversaryRepository_Factory(anniversaryDaoProvider);
  }

  public static AnniversaryRepository newInstance(AnniversaryDao anniversaryDao) {
    return new AnniversaryRepository(anniversaryDao);
  }
}
