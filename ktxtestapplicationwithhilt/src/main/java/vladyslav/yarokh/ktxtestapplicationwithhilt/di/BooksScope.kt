package vladyslav.yarokh.ktxtestapplicationwithhilt.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BooksScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NullScope
