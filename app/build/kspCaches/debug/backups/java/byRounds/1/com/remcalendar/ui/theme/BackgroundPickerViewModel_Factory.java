package com.remcalendar.ui.theme;

import android.content.Context;
import com.remcalendar.data.repository.SettingsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class BackgroundPickerViewModel_Factory implements Factory<BackgroundPickerViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<Context> contextProvider;

  public BackgroundPickerViewModel_Factory(Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<Context> contextProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public BackgroundPickerViewModel get() {
    return newInstance(settingsRepositoryProvider.get(), contextProvider.get());
  }

  public static BackgroundPickerViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider, Provider<Context> contextProvider) {
    return new BackgroundPickerViewModel_Factory(settingsRepositoryProvider, contextProvider);
  }

  public static BackgroundPickerViewModel newInstance(SettingsRepository settingsRepository,
      Context context) {
    return new BackgroundPickerViewModel(settingsRepository, context);
  }
}
