package kr.sjh.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.sjh.data.datasource.local.DefaultLocalDataSource
import kr.sjh.data.datasource.local.LocalDataSource
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun provideLocalDataSource(
        defalutDataSource: DefaultLocalDataSource
    ): LocalDataSource
}