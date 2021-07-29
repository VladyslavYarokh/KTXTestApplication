package vladyslav.yarokh.ktxactivityresultapi.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import vladyslav.yarokh.ktxactivityresultapi.SecondActivity

private const val TOAST = "toast"
private const val PERMISSION_CODE = "permission_code"

class OpenSecondActivityContract: ActivityResultContract<String, String?>() {
    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(context, SecondActivity::class.java).putExtra(TOAST, "I'm from $input")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? = when {
        resultCode != Activity.RESULT_OK -> null
        else -> intent?.getStringExtra(PERMISSION_CODE)
    }
}