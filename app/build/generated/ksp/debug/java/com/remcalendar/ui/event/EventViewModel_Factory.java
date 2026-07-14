package com.remcalendar.ui.event;

import com.remcalendar.data.repository.EventRepository;
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
public final class EventViewModel_Factory implements Factory<EventViewModel> {
  private final Provider<EventRepository> eventRepositoryProvider;

  public EventViewModel_Factory(Provider<EventRepository> eventRepositoryProvider) {
    this.eventRepositoryProvider = eventRepositoryProvider;
  }

  @Override
  public EventViewModel get() {
    return newInstance(eventRepositoryProvider.get());
  }

  public static EventViewModel_Factory create(Provider<EventRepository> eventRepositoryProvider) {
    return new EventViewModel_Factory(eventRepositoryProvider);
  }

  public static EventViewModel newInstance(EventRepository eventRepository) {
    return new EventViewModel(eventRepository);
  }
}
