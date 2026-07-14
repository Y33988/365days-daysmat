package com.remcalendar.ui.theme;

import com.remcalendar.data.repository.SettingsRepository;
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
public final class BackgroundViewModel_Factory implements Factory<BackgroundViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public BackgroundViewModel_Factory(Provider<SettingsRepository> settingsRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public BackgroundViewModel get() {
    return newInstance(settingsRepositoryProvider.get());
  }

  public static BackgroundViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new BackgroundViewModel_Factory(settingsRepositoryProvider);
  }

  public static BackgroundViewModel newInstance(SettingsRepository settingsRepository) {
    return new BackgroundViewModel(settingsRepository);
  }
}
