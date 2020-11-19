package com.example.playground

import io.reactivex.disposables.Disposable
import java.util.function.Consumer

open class WelcomePresenter(
    private val mapperRepository: MapperRepository,
    private val schedulerProvider: SchedulerProvider,
    private val urlInfo: UrlInfo,
    override var view: WelcomeContract.View
) : WelcomeContract.Presenter {

    private var disposable: Disposable? = null

    override fun fetchServers(id: String) {
        disposable = mapperRepository.getData(id)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { success -> view.onSuccess(success) },
                { error -> view.onError(errorMessage = error.message ?: "") })
    }


    override fun stop() {
        disposable?.dispose()
    }

}