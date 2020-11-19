package com.example.playground

import io.reactivex.Scheduler
import io.reactivex.Single

interface WelcomeContract {
    interface View : BaseView<Presenter> {
        fun onError(errorMessage: String)
        fun onSuccess(string: String)
    }

    interface Presenter : BasePresenter<View> {
        fun fetchServers(id: String)
    }
}

interface BasePresenter<T> {
    var view: T
    fun stop()
}

interface BaseView<out T : BasePresenter<*>> {
    val presenter: T
    fun isActive(): Boolean
}

interface MapperRepository {
    fun getData(id: String): Single<String>
}

interface SchedulerProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler
}


interface UrlInfo {
    var userUrl: String
}