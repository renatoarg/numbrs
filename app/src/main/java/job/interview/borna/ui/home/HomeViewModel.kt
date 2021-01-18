package job.interview.borna.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import job.interview.borna.base.BaseViewModel
import job.interview.borna.data.Item
import job.interview.borna.data.ItemsRepository

@ExperimentalUnsignedTypes
class HomeViewModel @ViewModelInject constructor(
    repository: ItemsRepository,
) : BaseViewModel() {

    val items: LiveData<List<Item>> = repository.allItems.asLiveData()

}



