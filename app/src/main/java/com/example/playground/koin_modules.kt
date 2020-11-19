package com.example.playground

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module

val appModule = module {
    single<SchedulerProvider> {
        object : SchedulerProvider {
            override fun io(): Scheduler {
                return Schedulers.io()
            }

            override fun ui(): Scheduler {
                return AndroidSchedulers.mainThread()
            }

            override fun computation(): Scheduler {
                return Schedulers.computation()
            }
        }
    }
}
val welcomePresenterModule = module {
    single<MapperRepository> { MapperRepositoryImpl() }
    single<UrlInfo> {
        UrlInfoImpl()
    }
    scope<FirstFragment> {
        scoped<WelcomeContract.Presenter> { (view: WelcomeContract.View) ->
            WelcomePresenter(get(), get(), get(), view)
        }
    }
}

