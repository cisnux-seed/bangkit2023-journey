package dev.cisnux.myquote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import dev.cisnux.myquote.databinding.ActivityListQuotesBinding
import org.json.JSONArray

class ListQuotesActivity : AppCompatActivity() {
    companion object {
        private val TAG = ListQuotesActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityListQuotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            supportActionBar?.title = "List of Quotes"
            val layoutManager = LinearLayoutManager(this@ListQuotesActivity)
            listQuotes.layoutManager = layoutManager
            val itemDecoration =
                DividerItemDecoration(this@ListQuotesActivity, layoutManager.orientation)
            listQuotes.addItemDecoration(itemDecoration)

            getListQuotes()
        }
    }

    private fun getListQuotes() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            val client = AsyncHttpClient()
            val url = "https://quote-api.dicoding.dev/list"
            client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?
                ) {
                    // Jika koneksi berhasil
                    progressBar.visibility = View.INVISIBLE

                    val listQuote = ArrayList<String>()

                    val result = responseBody?.let { String(it) }
                    Log.d(TAG, result ?: "")
                    try {
                        val jsonArray = JSONArray(result)

                        for(i in 0 until jsonArray.length()){
                            val jsonObject = jsonArray.getJSONObject(i)
                            val quote = jsonObject.getString("en")
                            val author = jsonObject.getString("author")
                            listQuote.add("\n$quote\n - $author\n")
                        }

                        val adapter = QuoteAdapter(listQuote)
                        listQuotes.adapter = adapter
                    } catch (e: Exception) {
                        Toast.makeText(this@ListQuotesActivity, e.message, Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable?
                ) {
                    // Jika koneksi gagal
                    progressBar.visibility = View.INVISIBLE
                    val errorMessage = when (statusCode) {
                        401 -> "$statusCode : Bad Request"
                        403 -> "$statusCode : Forbidden"
                        404 -> "$statusCode : Not Found"
                        else -> "$statusCode : ${error?.message}"
                    }
                    Toast.makeText(this@ListQuotesActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}