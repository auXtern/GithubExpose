package com.abdullah.core.di

import android.content.Context
import com.abdullah.core.data.source.pref.SettingPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PrefModule {

    @Singleton
    @Provides
    fun provideSettingPreference(@ApplicationContext context: Context): SettingPreference = SettingPreference(context)

}