package vladyslav.yarokh.ktxtestapplicationwithhilt.ui.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import vladyslav.yarokh.ktxtestapplicationwithhilt.data.BooksState
import vladyslav.yarokh.ktxtestapplicationwithhilt.repository.FirestoreRepository
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(private val repository: FirestoreRepository): ViewModel() {

    fun getBooks(query: String): LiveData<BooksState> {
        return repository.getBooks(query)
    }
}