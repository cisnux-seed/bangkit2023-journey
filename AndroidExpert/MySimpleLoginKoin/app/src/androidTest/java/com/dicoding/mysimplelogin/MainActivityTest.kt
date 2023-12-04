package com.dicoding.mysimplelogin

import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class MainActivityTest : KoinTest {
    private val userRepository: UserRepository by inject()
    private val username = "dicoding"

    @Before
    fun before() {
        loadKoinModules(storageModule)
        userRepository.loginUser(username)
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun getUsernameFromRepository() {
        val requestedUsername = userRepository.getUser()
        assertEquals(username, requestedUsername)
    }
}