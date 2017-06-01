package com.leodeleon.popmovies.di;

import dagger.Subcomponent;

@Subcomponent public interface ViewModelSubComponent {
  //BlockedUsersViewModel blockedUsersViewModel();

  @Subcomponent.Builder interface Builder {
    ViewModelSubComponent build();
  }
}
