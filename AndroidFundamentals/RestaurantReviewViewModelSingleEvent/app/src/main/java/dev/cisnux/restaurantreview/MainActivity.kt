package dev.cisnux.restaurantreview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dev.cisnux.restaurantreview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        with(binding) {
            mainViewModel.restaurant.observe(this@MainActivity) { restaurant ->
                setRestaurantData(restaurant)
            }

            val layoutManager = LinearLayoutManager(this@MainActivity)
            val itemDecoration = DividerItemDecoration(this@MainActivity, layoutManager.orientation)
            rvReview.layoutManager = layoutManager
            rvReview.addItemDecoration(itemDecoration)

            mainViewModel.listReview.observe(this@MainActivity) { consumerReviews ->
                setReviewData(consumerReviews)
            }

            mainViewModel.isLoading.observe(this@MainActivity) {
                showLoading(it)
            }

            mainViewModel.snackbarText.observe(this@MainActivity) {
                it.getContentIfNotHandled()?.let { snackbarText ->
                    Snackbar.make(
                        window.decorView.rootView,
                        snackbarText,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            btnSend.setOnClickListener { view ->
                mainViewModel.postReview(edReview.text.toString())
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

    private fun setReviewData(customerReviews: List<String>) {
        val adapter = ReviewAdapter(customerReviews)
        binding.rvReview.adapter = adapter
    }

    private fun setRestaurantData(restaurant: Restaurant) {
        with(binding) {
            tvTitle.text = restaurant.name
            tvDescription.text = restaurant.description
            Glide.with(this@MainActivity)
                .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
                .into(ivPicture)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}