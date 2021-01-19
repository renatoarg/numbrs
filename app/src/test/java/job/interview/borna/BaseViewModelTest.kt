@file:Suppress("UNCHECKED_CAST")

package job.interview.borna

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import job.interview.borna.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit

@ExperimentalCoroutinesApi
@ExperimentalUnsignedTypes
abstract class BaseViewModelTest<T : BaseViewModel> : BaseTest() {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    abstract var viewModel: T

    @get:Rule
    var mockrule = MockitoJUnit.rule().silent()


    override fun setUp() {
        MockitoAnnotations.initMocks(this)
        initializeViewModel()
    }

    abstract fun initializeViewModel()

}