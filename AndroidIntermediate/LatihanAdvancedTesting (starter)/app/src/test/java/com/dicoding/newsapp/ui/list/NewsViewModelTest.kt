package com.dicoding.newsapp.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dicoding.newsapp.data.NewsRepository
import com.dicoding.newsapp.data.Result
import com.dicoding.newsapp.data.local.entity.NewsEntity
import com.dicoding.newsapp.utils.DataDummy
import com.dicoding.newsapp.utils.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var newsRepository: NewsRepository
    private lateinit var newsViewModel: NewsViewModel
    private val dummyNews = DataDummy.generateDummyNewsEntity()

    @Before
    fun setUp() {
        newsViewModel = NewsViewModel(newsRepository)
    }

    @Test
    fun `when Get HeadlineNews Should Not Null and Return Success`() {
        // arrange
        val expectedNews = MutableLiveData<Result<List<NewsEntity>>>()
        expectedNews.value = Result.Success(dummyNews)

        // act
        `when`(newsRepository.getHeadlineNews()).thenReturn(expectedNews)
        val actualNews = newsViewModel.getHeadlineNews().getOrAwaitValue()

        // assert
        Mockito.verify(newsRepository).getHeadlineNews()
        assertNotNull(actualNews)
        assertTrue(actualNews is Result.Success)
        assertEquals(dummyNews.size, (actualNews as Result.Success).data.size)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        // arrange
        val headlineNews = MutableLiveData<Result<List<NewsEntity>>>()
        headlineNews.value = Result.Error("Error")

        // act
        `when`(newsRepository.getHeadlineNews()).thenReturn(headlineNews)
        val actualNews = newsViewModel.getHeadlineNews().getOrAwaitValue()

        // assert
        Mockito.verify(newsRepository).getHeadlineNews()
        assertNotNull(actualNews)
        assertTrue(actualNews is Result.Error)
    }

    @Test
    fun `when Get Bookmarked News Should Not Empty`() {
        // arrange
        val expectedBookmarkedNews = MutableLiveData<List<NewsEntity>>()
        expectedBookmarkedNews.value = dummyNews

        // act
        `when`(newsRepository.getBookmarkedNews()).thenReturn(expectedBookmarkedNews)
        val actualBookmarkedNews = newsViewModel.getBookmarkedNews().getOrAwaitValue()

        // assert
        Mockito.verify(newsRepository).getBookmarkedNews()
        assertTrue(actualBookmarkedNews.isNotEmpty())
        assertEquals(dummyNews.size, actualBookmarkedNews.size)
    }

    @Test
    fun `when Get Bookmarked News Should Empty`() {
        // arrange
        val expectedBookmarkedNews = MutableLiveData<List<NewsEntity>>()
        expectedBookmarkedNews.value = emptyList()

        // act
        `when`(newsRepository.getBookmarkedNews()).thenReturn(expectedBookmarkedNews)
        val actualBookmarkedNews = newsViewModel.getBookmarkedNews().getOrAwaitValue()

        // assert
        Mockito.verify(newsRepository).getBookmarkedNews()
        assertTrue(actualBookmarkedNews.isEmpty())
    }
}