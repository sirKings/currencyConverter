package john.kingsley.currencyconverter.data.util

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import john.kingsley.currencyconverter.ui.main.MainViewModel

class FetchDataWorker(var appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    override fun doWork(): Result {

        // Do the work here--in this case, upload the images.
        val vm = MainViewModel(appContext)
        vm.getQuotes()
        vm.getCurrencyDetails()

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
}