package vladyslav.yarokh.ktxtestapplicationwithhilt.ui.books

import vladyslav.yarokh.ktxtestapplicationwithhilt.data.BookModel

internal interface BookItemListener {
    fun onBookClick(bookModel: BookModel)
}