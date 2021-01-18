package job.interview.borna.ui.edititem

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import job.interview.borna.base.BaseViewModel
import job.interview.borna.data.Item
import job.interview.borna.data.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalUnsignedTypes
class EditItemViewModel @ViewModelInject constructor(
    private val repository: ItemsRepository,
) : BaseViewModel() {

    private var addItemState = MutableLiveData<EditItemState>()

    fun getEditItemState(): LiveData<EditItemState> {
        return addItemState
    }

    private fun emitEditItemStateState(state: EditItemState) {
        launch {
            withContext(Dispatchers.Main) {
                addItemState.value = state
            }
        }
    }

    fun editItem(item: Item) = launch {
        repository.insert(item)
        emitEditItemStateState(EditItemState.AddSuccess)
    }

    fun deleteItem(item: Item) = launch {
        repository.delete(item)
        emitEditItemStateState(EditItemState.AddSuccess)
    }

    fun setIdle() {
        emitEditItemStateState(EditItemState.Idle)
    }
}



