package vladyslav.yarokh.ktxtestapplicationwithhilt.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vladyslav.yarokh.ktxtestapplicationwithhilt.repository.FirestoreRepository
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Singleton
import kotlin.math.log

/**
 * 1) Создаем annotation class для определенных Scope
 * 2) Помечаем @Provides метод нужной нам аннотацией
 * 3) В конструктор другого @Provides метода передаем обьект с пометкой Scope
 * */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    internal fun provideFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    internal fun provideRepository(firestore: FirebaseFirestore, @NullScope logger: Logger): FirestoreRepository = FirestoreRepository(firestore, logger)

    @Provides
    @NullScope
    internal fun provideNullLogger(): Logger = Logger.getAnonymousLogger().apply {
        level = Level.INFO
    }

    @Provides
    @BooksScope
    internal fun provideBooksLogger(): Logger = Logger.getAnonymousLogger().apply {
        level = Level.WARNING
    }
}