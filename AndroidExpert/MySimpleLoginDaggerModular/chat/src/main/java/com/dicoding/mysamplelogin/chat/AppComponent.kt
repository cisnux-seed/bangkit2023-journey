package com.dicoding.mysamplelogin.chat

import android.content.Context
import com.dicoding.di.StorageModule
import com.dicoding.mysimplelogin.HomeActivity
import com.dicoding.mysimplelogin.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: ChatActivity)
}