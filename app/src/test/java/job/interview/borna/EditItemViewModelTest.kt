package job.interview.borna

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import job.interview.borna.data.Item
import job.interview.borna.data.ItemsRepository
import job.interview.borna.ui.edititem.EditItemState
import job.interview.borna.ui.edititem.EditItemViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import java.lang.reflect.Method

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@ExperimentalUnsignedTypes
class EditItemViewModelTest : BaseViewModelTest<EditItemViewModel>() {

    override lateinit var viewModel: EditItemViewModel

    @Mock
    lateinit var observer: Observer<EditItemState>

    @Mock
    lateinit var repository: ItemsRepository

    override fun initializeViewModel() {
        viewModel = EditItemViewModel(repository)
    }

    override fun setUp() {
        super.setUp()
        viewModel.getEditItemState().observeForever(observer)
    }

    @Test
    fun `test edit item success`() {
        mainCoroutineRule.runBlockingTest {
            whenever(
                repository.insert(
                    any()
                )
            ).thenAnswer {
                it.arguments[0] as Item
            }
            viewModel.editItem(Item())
            verify(observer).onChanged(EditItemState.AddSuccess)
            verifyNoMoreInteractions(observer)
        }
    }

    @Test
    fun `test emit success state`() {
        val emitEditItemState: Method = EditItemViewModel::class.java.getDeclaredMethod(
            "emitEditItemStateState",
            EditItemState::class.java
        )
        emitEditItemState.isAccessible = true
        val parameters = arrayOfNulls<EditItemState>(1)
        parameters[0] = EditItemState.AddSuccess
        emitEditItemState.invoke(viewModel, *parameters)
        verify(observer).onChanged(EditItemState.AddSuccess)
    }

    @Test
    fun `test emit loading state`() {
        val emitEditItemState: Method = EditItemViewModel::class.java.getDeclaredMethod(
            "emitEditItemStateState",
            EditItemState::class.java
        )
        emitEditItemState.isAccessible = true
        val parameters = arrayOfNulls<EditItemState>(1)
        parameters[0] = EditItemState.Loading
        emitEditItemState.invoke(viewModel, *parameters)
        verify(observer).onChanged(EditItemState.Loading)
    }

    override fun destroy() {
    }
}