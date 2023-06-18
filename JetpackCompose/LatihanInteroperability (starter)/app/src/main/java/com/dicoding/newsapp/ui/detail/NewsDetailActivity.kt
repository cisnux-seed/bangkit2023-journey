package com.dicoding.newsapp.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.dicoding.newsapp.R
import com.dicoding.newsapp.data.local.entity.NewsEntity
import com.dicoding.newsapp.databinding.ActivityNewsDetailBinding
import com.dicoding.newsapp.ui.ViewModelFactory

class NewsDetailActivity : AppCompatActivity() {

    private var newsDetail: NewsEntity? = null
    private lateinit var binding: ActivityNewsDetailBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val viewModel: NewsDetailViewModel by viewModels {
        factory
    }
//    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsDetail = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(NEWS_DATA, NewsEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<NewsEntity>(NEWS_DATA) as NewsEntity
        }

        setContent {
            MaterialTheme {
                newsDetail?.let { NewsDetailScreen(newsDetail = it, viewModel = viewModel) }
            }
        }


//        supportActionBar?.title = newsDetail?.title
//        binding.webView.webViewClient = WebViewClient()
//        binding.webView.loadUrl(newsDetail?.url.toString())
//
//        newsDetail?.let(viewModel::setNewsData)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.detail_menu, menu)
//        this.menu = menu
//        viewModel.bookmarkStatus.observe(this) { status ->
//            setBookmarkState(status)
//        }
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.action_bookmark) {
//            newsDetail?.let(viewModel::changeBookmark)
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    private fun setBookmarkState(state: Boolean) {
//        if (menu == null) return
//        val menuItem = menu?.findItem(R.id.action_bookmark)
//        if (state) {
//            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmarked_white)
//        } else {
//            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark_white)
//        }
//    }

    companion object {
        const val NEWS_DATA = "data"
    }
}

@Composable
fun NewsDetailScreen(
    newsDetail: NewsEntity,
    viewModel: NewsDetailViewModel,
) {
    viewModel.setNewsData(newsDetail)
    val bookmarkStatus by viewModel.bookmarkStatus.observeAsState()
    NewsDetailContent(
        title = newsDetail.title,
        url = newsDetail.url.toString(),
        bookmarkStatus = bookmarkStatus,
        updateBookmarkStatus = {
            viewModel.changeBookmark(newsDetail)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun NewsDetailContentPreview() {
    MaterialTheme {
        NewsDetailContent(
            title = "New News",
            url = "www.dicoding.com",
            bookmarkStatus = false,
            updateBookmarkStatus = {})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailContent(
    modifier: Modifier = Modifier,
    title: String,
    url: String,
    bookmarkStatus: Boolean?,
    updateBookmarkStatus: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                actions = {
                    IconButton(onClick = updateBookmarkStatus) {
                        Icon(
                            painter = if (bookmarkStatus == true) {
                                painterResource(R.drawable.ic_bookmarked_white)
                            } else {
                                painterResource(R.drawable.ic_bookmark_white)
                            },
                            contentDescription = stringResource(R.string.save_bookmark),
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AndroidView(
                factory ={
                   WebView(it).apply {
                       layoutParams = ViewGroup.LayoutParams(
                           ViewGroup.LayoutParams.MATCH_PARENT,
                           ViewGroup.LayoutParams.MATCH_PARENT,
                       )
                       webViewClient = WebViewClient()
                   }
                },
                update = {
                    it.loadUrl("https://www.dicoding.com")
                }
            )
        }
    }
}
