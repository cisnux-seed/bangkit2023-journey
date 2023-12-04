package dev.erwin.todo.core.domain.models

data class NoteDetail(
    val id: String? = null,
    val title: String,
    val body: String,
    val createdAt: Long = System.currentTimeMillis(),
    val lastUpdatedAt: Long = System.currentTimeMillis(),
)