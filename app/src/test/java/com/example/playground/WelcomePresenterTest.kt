package com.example.playground

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

@RunWith(MockitoJUnitRunner::class)
class WelcomePresenterTest {

    companion object {
        const val TEST_ID="test123"
        const val TEST_SUCCESS="success data"
        const val TEST_ERROR="error"

    }
    lateinit var welcomePresenter: WelcomePresenter

    @Mock
    lateinit var view: WelcomeContract.View

    @Mock
    lateinit var mapperRepository: MapperRepository

    @Mock
    lateinit var urlInfo: UrlInfo


    private val testScheduler = TestScheduler()
    private val provider = object : SchedulerProvider {
        override fun io() = testScheduler
        override fun ui() = testScheduler
        override fun computation() = testScheduler
    }

    @Before
    fun setUp() {
        welcomePresenter = WelcomePresenter(
            mapperRepository = mapperRepository,
            schedulerProvider = provider,
            urlInfo = urlInfo,
            view = view
        )
    }

    @Test
    fun testSuccess() {
        `when`(mapperRepository.getData(TEST_ID)).thenReturn(Single.just(TEST_SUCCESS))
        welcomePresenter.fetchServers(id = TEST_ID)
        testScheduler.triggerActions()
        verify(view).onSuccess(TEST_SUCCESS)
        verify(urlInfo).userUrl = TEST_SUCCESS
    }

    @Test
    fun testFailure() {
        `when`(mapperRepository.getData(TEST_ID)).thenReturn(Single.error(Exception(TEST_ERROR)))
        welcomePresenter.fetchServers(id = TEST_ID)
        testScheduler.triggerActions()
        verify(view).onError(TEST_ERROR)
    }
}