package com.remcalendar;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.remcalendar.data.dao.AnniversaryDao;
import com.remcalendar.data.dao.EventDao;
import com.remcalendar.data.database.AppDatabase;
import com.remcalendar.data.repository.AnniversaryRepository;
import com.remcalendar.data.repository.EventRepository;
import com.remcalendar.data.repository.SettingsRepository;
import com.remcalendar.di.AppModule_ProvideAnniversaryDaoFactory;
import com.remcalendar.di.AppModule_ProvideDatabaseFactory;
import com.remcalendar.di.AppModule_ProvideEventDaoFactory;
import com.remcalendar.ui.anniversary.AnniversaryViewModel;
import com.remcalendar.ui.anniversary.AnniversaryViewModel_HiltModules;
import com.remcalendar.ui.calendar.CalendarViewModel;
import com.remcalendar.ui.calendar.CalendarViewModel_HiltModules;
import com.remcalendar.ui.event.EventViewModel;
import com.remcalendar.ui.event.EventViewModel_HiltModules;
import com.remcalendar.ui.search.SearchViewModel;
import com.remcalendar.ui.search.SearchViewModel_HiltModules;
import com.remcalendar.ui.settings.SettingsViewModel;
import com.remcalendar.ui.settings.SettingsViewModel_HiltModules;
import com.remcalendar.ui.theme.BackgroundPickerViewModel;
import com.remcalendar.ui.theme.BackgroundPickerViewModel_HiltModules;
import com.remcalendar.ui.theme.BackgroundViewModel;
import com.remcalendar.ui.theme.BackgroundViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerRemCalendarApp_HiltComponents_SingletonC {
  private DaggerRemCalendarApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public RemCalendarApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements RemCalendarApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public RemCalendarApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements RemCalendarApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public RemCalendarApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements RemCalendarApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public RemCalendarApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements RemCalendarApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public RemCalendarApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements RemCalendarApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public RemCalendarApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements RemCalendarApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public RemCalendarApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements RemCalendarApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public RemCalendarApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends RemCalendarApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends RemCalendarApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends RemCalendarApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends RemCalendarApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(7).put(LazyClassKeyProvider.com_remcalendar_ui_anniversary_AnniversaryViewModel, AnniversaryViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_remcalendar_ui_theme_BackgroundPickerViewModel, BackgroundPickerViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_remcalendar_ui_theme_BackgroundViewModel, BackgroundViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_remcalendar_ui_calendar_CalendarViewModel, CalendarViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_remcalendar_ui_event_EventViewModel, EventViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_remcalendar_ui_search_SearchViewModel, SearchViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_remcalendar_ui_settings_SettingsViewModel, SettingsViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_remcalendar_ui_search_SearchViewModel = "com.remcalendar.ui.search.SearchViewModel";

      static String com_remcalendar_ui_theme_BackgroundPickerViewModel = "com.remcalendar.ui.theme.BackgroundPickerViewModel";

      static String com_remcalendar_ui_event_EventViewModel = "com.remcalendar.ui.event.EventViewModel";

      static String com_remcalendar_ui_anniversary_AnniversaryViewModel = "com.remcalendar.ui.anniversary.AnniversaryViewModel";

      static String com_remcalendar_ui_theme_BackgroundViewModel = "com.remcalendar.ui.theme.BackgroundViewModel";

      static String com_remcalendar_ui_calendar_CalendarViewModel = "com.remcalendar.ui.calendar.CalendarViewModel";

      static String com_remcalendar_ui_settings_SettingsViewModel = "com.remcalendar.ui.settings.SettingsViewModel";

      @KeepFieldType
      SearchViewModel com_remcalendar_ui_search_SearchViewModel2;

      @KeepFieldType
      BackgroundPickerViewModel com_remcalendar_ui_theme_BackgroundPickerViewModel2;

      @KeepFieldType
      EventViewModel com_remcalendar_ui_event_EventViewModel2;

      @KeepFieldType
      AnniversaryViewModel com_remcalendar_ui_anniversary_AnniversaryViewModel2;

      @KeepFieldType
      BackgroundViewModel com_remcalendar_ui_theme_BackgroundViewModel2;

      @KeepFieldType
      CalendarViewModel com_remcalendar_ui_calendar_CalendarViewModel2;

      @KeepFieldType
      SettingsViewModel com_remcalendar_ui_settings_SettingsViewModel2;
    }
  }

  private static final class ViewModelCImpl extends RemCalendarApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AnniversaryViewModel> anniversaryViewModelProvider;

    private Provider<BackgroundPickerViewModel> backgroundPickerViewModelProvider;

    private Provider<BackgroundViewModel> backgroundViewModelProvider;

    private Provider<CalendarViewModel> calendarViewModelProvider;

    private Provider<EventViewModel> eventViewModelProvider;

    private Provider<SearchViewModel> searchViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private AnniversaryRepository anniversaryRepository() {
      return new AnniversaryRepository(singletonCImpl.anniversaryDao());
    }

    private SettingsRepository settingsRepository() {
      return new SettingsRepository(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));
    }

    private EventRepository eventRepository() {
      return new EventRepository(singletonCImpl.eventDao());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.anniversaryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.backgroundPickerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.backgroundViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.calendarViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.eventViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.searchViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(7).put(LazyClassKeyProvider.com_remcalendar_ui_anniversary_AnniversaryViewModel, ((Provider) anniversaryViewModelProvider)).put(LazyClassKeyProvider.com_remcalendar_ui_theme_BackgroundPickerViewModel, ((Provider) backgroundPickerViewModelProvider)).put(LazyClassKeyProvider.com_remcalendar_ui_theme_BackgroundViewModel, ((Provider) backgroundViewModelProvider)).put(LazyClassKeyProvider.com_remcalendar_ui_calendar_CalendarViewModel, ((Provider) calendarViewModelProvider)).put(LazyClassKeyProvider.com_remcalendar_ui_event_EventViewModel, ((Provider) eventViewModelProvider)).put(LazyClassKeyProvider.com_remcalendar_ui_search_SearchViewModel, ((Provider) searchViewModelProvider)).put(LazyClassKeyProvider.com_remcalendar_ui_settings_SettingsViewModel, ((Provider) settingsViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_remcalendar_ui_event_EventViewModel = "com.remcalendar.ui.event.EventViewModel";

      static String com_remcalendar_ui_theme_BackgroundViewModel = "com.remcalendar.ui.theme.BackgroundViewModel";

      static String com_remcalendar_ui_theme_BackgroundPickerViewModel = "com.remcalendar.ui.theme.BackgroundPickerViewModel";

      static String com_remcalendar_ui_anniversary_AnniversaryViewModel = "com.remcalendar.ui.anniversary.AnniversaryViewModel";

      static String com_remcalendar_ui_search_SearchViewModel = "com.remcalendar.ui.search.SearchViewModel";

      static String com_remcalendar_ui_settings_SettingsViewModel = "com.remcalendar.ui.settings.SettingsViewModel";

      static String com_remcalendar_ui_calendar_CalendarViewModel = "com.remcalendar.ui.calendar.CalendarViewModel";

      @KeepFieldType
      EventViewModel com_remcalendar_ui_event_EventViewModel2;

      @KeepFieldType
      BackgroundViewModel com_remcalendar_ui_theme_BackgroundViewModel2;

      @KeepFieldType
      BackgroundPickerViewModel com_remcalendar_ui_theme_BackgroundPickerViewModel2;

      @KeepFieldType
      AnniversaryViewModel com_remcalendar_ui_anniversary_AnniversaryViewModel2;

      @KeepFieldType
      SearchViewModel com_remcalendar_ui_search_SearchViewModel2;

      @KeepFieldType
      SettingsViewModel com_remcalendar_ui_settings_SettingsViewModel2;

      @KeepFieldType
      CalendarViewModel com_remcalendar_ui_calendar_CalendarViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.remcalendar.ui.anniversary.AnniversaryViewModel 
          return (T) new AnniversaryViewModel(viewModelCImpl.anniversaryRepository());

          case 1: // com.remcalendar.ui.theme.BackgroundPickerViewModel 
          return (T) new BackgroundPickerViewModel(viewModelCImpl.settingsRepository(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 2: // com.remcalendar.ui.theme.BackgroundViewModel 
          return (T) new BackgroundViewModel(viewModelCImpl.settingsRepository());

          case 3: // com.remcalendar.ui.calendar.CalendarViewModel 
          return (T) new CalendarViewModel(viewModelCImpl.eventRepository(), viewModelCImpl.settingsRepository());

          case 4: // com.remcalendar.ui.event.EventViewModel 
          return (T) new EventViewModel(viewModelCImpl.eventRepository());

          case 5: // com.remcalendar.ui.search.SearchViewModel 
          return (T) new SearchViewModel(viewModelCImpl.eventRepository(), viewModelCImpl.anniversaryRepository());

          case 6: // com.remcalendar.ui.settings.SettingsViewModel 
          return (T) new SettingsViewModel(viewModelCImpl.settingsRepository());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends RemCalendarApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends RemCalendarApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends RemCalendarApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<AppDatabase> provideDatabaseProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private AnniversaryDao anniversaryDao() {
      return AppModule_ProvideAnniversaryDaoFactory.provideAnniversaryDao(provideDatabaseProvider.get());
    }

    private EventDao eventDao() {
      return AppModule_ProvideEventDaoFactory.provideEventDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 0));
    }

    @Override
    public void injectRemCalendarApp(RemCalendarApp remCalendarApp) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.remcalendar.data.database.AppDatabase 
          return (T) AppModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
