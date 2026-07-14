package com.remcalendar.ui.search;

import com.remcalendar.data.repository.AnniversaryRepository;
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
public final class SearchViewModel_Factory implements Factory<SearchViewModel> {
  private final Provider<EventRepository> eventRepositoryProvider;

  private final Provider<AnniversaryRepository> anniversaryRepositoryProvider;

  public SearchViewModel_Factory(Provider<EventRepository> eventRepositoryProvider,
      Provider<AnniversaryRepository> anniversaryRepositoryProvider) {
    this.eventRepositoryProvider = eventRepositoryProvider;
    this.anniversaryRepositoryProvider = anniversaryRepositoryProvider;
  }

  @Override
  public SearchViewModel get() {
    return newInstance(eventRepositoryProvider.get(), anniversaryRepositoryProvider.get());
  }

  public static SearchViewModel_Factory create(Provider<EventRepository> eventRepositoryProvider,
      Provider<AnniversaryRepository> anniversaryRepositoryProvider) {
    return new SearchViewModel_Factory(eventRepositoryProvider, anniversaryRepositoryProvider);
  }

  public static SearchViewModel newInstance(EventRepository eventRepository,
      AnniversaryRepository anniversaryRepository) {
    return new SearchViewModel(eventRepository, anniversaryRepository);
  }
}
