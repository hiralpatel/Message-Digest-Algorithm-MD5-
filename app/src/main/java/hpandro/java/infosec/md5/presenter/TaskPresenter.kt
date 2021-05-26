package hpandro.java.infosec.md5.presenter

import hpandro.java.infosec.md5.base.BasePresenter
import hpandro.java.infosec.md5.network.APIClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TaskPresenter(val activity: TaskPresenterCallback) : BasePresenter() {
    fun getHashFlag(flag: String) {
        composite.add(
            APIClient
                .getAPIClient().getHashFlag(flag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        run {
                            activity.onGetLogs(result)
                        }
                    },
                    { error ->
                        run {
                            activity.onError(error)
                        }
                    }
                )
        )
    }

    fun dispose() {
        composite.dispose()
    }
}