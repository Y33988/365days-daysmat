package com.remcalendar.ui.anniversary;

import com.remcalendar.data.repository.AnniversaryRepository;
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
public final class AnniversaryViewModel_Factory implements Factory<AnniversaryViewModel> {
  private final Provider<AnniversaryRepository> anniversaryRepositoryProvider;

  public AnniversaryViewModel_Factory(
      Provider<AnniversaryRepository> anniversaryRepositoryProvider) {
    this.anniversaryRepositoryProvider = anniversaryRepositoryProvider;
  }

  @Override
  public AnniversaryViewModel get() {
    return newInstance(anniversaryRepositoryProvider.get());
  }

  public static AnniversaryViewModel_Factory create(
      Provider<AnniversaryRepository> anniversaryRepositoryProvider) {
    return new AnniversaryViewModel_Factory(anniversaryRepositoryProvider);
  }

  public static AnniversaryViewModel newInstance(AnniversaryRepository anniversaryRepository) {
    return new AnniversaryViewModel(anniversaryRepository);
  }
}
