package job.interview.borna.ui.additem

sealed class AddItemState {

    object Loading : AddItemState()

    object AddSuccess: AddItemState()

    object Idle : AddItemState()
}
