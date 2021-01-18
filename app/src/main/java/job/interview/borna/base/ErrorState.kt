package job.interview.borna.base

sealed class ErrorState {

    object OnIdle : ErrorState()

    data class OnError(
        val error: String?
    ) : ErrorState()

    data class OnServerError(
        val error: String?
    ) : ErrorState()

}