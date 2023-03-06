package dev.cisnux.myquote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import dev.cisnux.myquote.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getRandomQuote()

        binding.btnAllQuotes.setOnClickListener {
            startActivity(Intent(this@MainActivity, ListQuotesActivity::class.java))
        }
    }

    private fun getRandomQuote() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            val client = AsyncHttpClient()
            val url = "https://quote-api.dicoding.dev/random"
            client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?
                ) {
                    // when response is success
                    progressBar.visibility = View.INVISIBLE

                    val result = responseBody?.let { String(it) }
                    Log.d(TAG, result ?: "")
                    try {
                        val responseObject = result?.let { JSONObject(it) }

                        val quote = responseObject?.getString("en")
                        val author = responseObject?.getString("author")

                        tvQuote.text = quote
                        tvAuthor.text = author
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@MainActivity, e.message, Toast.LENGTH_SHORT
                        ).show()
                        e.printStackTrace()
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable?
                ) {
                    // when response is failed
                    progressBar.visibility = View.INVISIBLE
//                    val errorMessage = when (statusCode) {
//                        401 -> "$statusCode : Bad Request"
//                        403 -> "$statusCode : Forbidden"
//                        404 -> "$statusCode : Not Found"
//                        else -> "$statusCode : ${error?.message}"
//                    }
                    val errorMessages = mutableMapOf(
                        401 to "$statusCode : Bad Request",
                        403 to "$statusCode : Forbidden",
                        404 to "$statusCode : Not Found"
                    )
                    val errorMessage =
                        errorMessages[statusCode] ?: "$statusCode : ${error?.message}"
                    Toast.makeText(
                        this@MainActivity, errorMessage, Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}