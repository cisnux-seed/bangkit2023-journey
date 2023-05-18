package com.dicoding.myunlimitedquotes.data

import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.myunlimitedquotes.database.QuoteDatabase
import com.dicoding.myunlimitedquotes.network.ApiService
import com.dicoding.myunlimitedquotes.network.QuoteResponseItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class QuoteRemoteMediatorTest {

    private var mockApi: ApiService = FakeApiService()
    private var mockDb: QuoteDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        QuoteDatabase::class.java
    ).allowMainThreadQueries().build()

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val remoteMediator = QuoteRemoteMediator(
            mockDb,
            mockApi,
        )
        val pagingState = PagingState<Int, QuoteResponseItem>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @After
    fun tearDown() {
        mockDb.clearAllTables()
    }
}

class FakeApiService : ApiService {

    /**
     * Supaya lebih fleksibel untuk mengatur data pada API, kita membuat FakeApiServices. Anda perlu menggunakan subList untuk mengambil data sesuai dengan page dan size yang diberikan. Misal apabila page bernilai 2 dan size bernilai 10. Maka titik awal pada subList bernilai (2-1)x10 = 10. Kemudian untuk titik akhir bernilai (2-1)x10+10 = 20. Artinya, data yang diambil dari indeks 10 sampai 20.
     * */
    override suspend fun getQuote(page: Int, size: Int): List<QuoteResponseItem> {
        val items = List(100) { i ->
            QuoteResponseItem(
                i.toString(),
                "author + $i",
                "quote $i",
            )
        }

        return items.subList((page - 1) * size, (page - 1) * size + size)
    }
}