package vladyslav.yarokh.ktxtestapplicationwithhilt.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import vladyslav.yarokh.ktxtestapplicationwithhilt.data.*
import vladyslav.yarokh.ktxtestapplicationwithhilt.di.BooksScope
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

private const val DB_NAME = "books"
private const val DB_FIELD_URL = "url"
private const val DB_FIELD_NAME = "name"
private const val DB_FIELD_AUTHOR = "author"

@Singleton
internal class FirestoreRepository @Inject constructor(
        private val firestore: FirebaseFirestore,
        private val logger: Logger
    ) {

    private val mutableState: MutableLiveData<BooksState> = MutableLiveData()
    val state: LiveData<BooksState>
        get() = mutableState

    fun getBooks(query: String): LiveData<BooksState> {
        mutableState.postValue(LoadingState)

        firestore.collection(DB_NAME)
            .get()
            .addOnSuccessListener { result ->
                val books = mutableListOf<BookModel>()
                for (document in result) {
                    books.add(BookModel(
                        document.data[DB_FIELD_URL].toString(),
                        document.data[DB_FIELD_NAME].toString(),
                        document.data[DB_FIELD_AUTHOR].toString()
                    ))
                }
                val filteredBooks = books.filter {
                    it.name.contains(query, true)
                }
                logger.log(logger.level!!, "Its ${logger.level}")
                mutableState.postValue(SuccessState(filteredBooks))
            }
            .addOnFailureListener {
                mutableState.postValue(ErrorState(it))
            }

        return state
    }
}