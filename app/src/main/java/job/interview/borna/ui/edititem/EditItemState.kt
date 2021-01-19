package job.interview.borna.ui.edititem

sealed class EditItemState {

    object Loading : EditItemState()

    object AddSuccess: EditItemState()

    object Idle : EditItemState()

}
