package hpandro.java.infosec.md5.base

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter {
    var composite: CompositeDisposable
        get() {
            return if (field.isDisposed) CompositeDisposable() else field
        }
        private set

    init {
        composite = CompositeDisposable()
    }
}