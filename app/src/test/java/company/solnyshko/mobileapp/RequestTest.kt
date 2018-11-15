package company.solnyshko.mobileapp

import android.content.Context
import android.content.SharedPreferences
import android.support.annotation.NonNull
import android.test.mock.MockContext
import company.solnyshko.mobileapp.Login.LoginActivity
import company.solnyshko.mobileapp.Login.LoginPresenter
import company.solnyshko.mobileapp.Login.LoginView
import io.reactivex.Scheduler
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import org.assertj.core.api.Assertions.assertThat;
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.runners.MockitoJUnitRunner
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


@RunWith(MockitoJUnitRunner::class) @FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RequestTest {
    lateinit var loginPresenter: LoginPresenter
    @Mock
    lateinit var mockContext : Context
    val sharedPreferences = SharedPreferencesImpl()
    val mockActivity = MockActivity()

    companion object {

        @BeforeClass @JvmStatic
        fun setUpRxSchedulers() {
            val immediate = object : Scheduler() {
                override fun scheduleDirect(@NonNull run: Runnable, delay: Long, @NonNull unit: TimeUnit): Disposable {
                    // this prevents StackOverflowErrors when scheduling with a delay
                    return super.scheduleDirect(run, 0, unit)
                }

                override fun createWorker(): Worker {
                    return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
                }
            }

            RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
        }
    }

    @Test
    fun loginRequestAndSaveData() {
        `when`(mockContext.getSharedPreferences("userData", Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences)
        loginPresenter = LoginPresenter(mockActivity, mockContext)
        loginPresenter.login("24", "1234")
        Thread.sleep(1000)
        val access_token = loginPresenter.sharedPreferences.getAccessToken()
        val id = loginPresenter.sharedPreferences.getId()
        assertNotEquals("", access_token)
        assertNotEquals("", id)
        assertNotEquals(null, access_token)
        assertNotEquals(null, id)
    }
}