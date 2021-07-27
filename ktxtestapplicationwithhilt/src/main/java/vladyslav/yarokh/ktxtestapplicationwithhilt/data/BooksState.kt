package vladyslav.yarokh.ktxtestapplicationwithhilt.data

sealed class BooksState
object LoadingState: BooksState()
data class ErrorState(val throwable: Throwable): BooksState()
data class SuccessState(val data: List<BookModel>): BooksState()
