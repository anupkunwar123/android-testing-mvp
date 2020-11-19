package com.example.playground

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers.single
import io.reactivex.schedulers.TestScheduler
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declare
import org.koin.test.mock.declareMock
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class FirstFragmentTest {
    @Test
    fun testFailure() {
        val repo = object : MapperRepository {
            override fun getData(id: String): Single<String> {
                return Single.error(Exception("Error"))
            }

        }
        loadKoinModules(module {
            single<MapperRepository>(override = true) {
                repo
            }

        })
        launchFragmentInContainer<FirstFragment>()
        Espresso.onView(
            ViewMatchers.withId(R.id.textview_first)
        ).check(ViewAssertions.matches(ViewMatchers.withText("Error")))
    }

    @Test
    fun testSuccess() {
        val repo = object : MapperRepository {
            override fun getData(id: String): Single<String> {
                return Single.just("success")
            }

        }
        loadKoinModules(module {
            single<MapperRepository>(override = true) {
                repo
            }

        })
        launchFragmentInContainer<FirstFragment>()
        Espresso.onView(
            ViewMatchers.withId(R.id.textview_first)
        ).check(ViewAssertions.matches(ViewMatchers.withText("success")))
    }
}

