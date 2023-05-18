package com.dicoding.myunlimitedquotes

import com.dicoding.myunlimitedquotes.network.QuoteResponseItem

object DataDummy {
    fun generateDummyQuoteResponse(): List<QuoteResponseItem> = List(100) { i ->
        QuoteResponseItem(
            i.toString(),
            "author $i",
            "quote $i"
        )
    }
}