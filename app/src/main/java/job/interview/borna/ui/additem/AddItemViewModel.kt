package job.interview.borna.ui.additem

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import job.interview.borna.Constants.INCOME
import job.interview.borna.base.BaseViewModel
import job.interview.borna.data.Item
import job.interview.borna.data.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalUnsignedTypes
class AddItemViewModel @ViewModelInject constructor(
    private val repository: ItemsRepository,
) : BaseViewModel() {

    private var addItemState = MutableLiveData<AddItemState>()

    fun getItemState(): LiveData<AddItemState> {
        return addItemState
    }

    private fun emitAddItemStateState(state: AddItemState) {
        launch {
            withContext(Dispatchers.Main) {
                addItemState.value = state
            }
        }
    }

    fun addItem(item: Item) = launch {
        if (item.type == INCOME)
            repository.insert(item)
        else
            repository.insert(
                Item(
                    id = item.id,
                    date = item.date,
                    name = item.name,
                    value = -1 * item.value,
                    type = item.type
                )
            )
        emitAddItemStateState(AddItemState.AddSuccess)
    }

    fun setIdle() {
        emitAddItemStateState(AddItemState.Idle)
    }
}



