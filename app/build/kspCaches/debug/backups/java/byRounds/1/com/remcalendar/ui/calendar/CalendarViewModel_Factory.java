package com.remcalendar.ui.calendar;

import com.remcalendar.data.repository.EventRepository;
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
public final class CalendarViewModel_Factory implements Factory<CalendarViewModel> {
  private final Provider<EventRepository> eventRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public CalendarViewModel_Factory(Provider<EventRepository> eventRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.eventRepositoryProvider = eventRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public CalendarViewModel get() {
    return newInstance(eventRepositoryProvider.get(), settingsRepositoryProvider.get());
  }

  public static CalendarViewModel_Factory create(Provider<EventRepository> eventRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new CalendarViewModel_Factory(eventRepositoryProvider, settingsRepositoryProvider);
  }

  public static CalendarViewModel newInstance(EventRepository eventRepository,
      SettingsRepository settingsRepository) {
    return new CalendarViewModel(eventRepository, settingsRepository);
  }
}
