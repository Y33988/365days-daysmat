package com.remcalendar.di;

import com.remcalendar.data.dao.AnniversaryDao;
import com.remcalendar.data.database.AppDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideAnniversaryDaoFactory implements Factory<AnniversaryDao> {
  private final Provider<AppDatabase> databaseProvider;

  public AppModule_ProvideAnniversaryDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public AnniversaryDao get() {
    return provideAnniversaryDao(databaseProvider.get());
  }

  public static AppModule_ProvideAnniversaryDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new AppModule_ProvideAnniversaryDaoFactory(databaseProvider);
  }

  public static AnniversaryDao provideAnniversaryDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideAnniversaryDao(database));
  }
}
