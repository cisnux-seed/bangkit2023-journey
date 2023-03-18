package dev.cisnux.myunittest

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

internal class MainViewModelTest {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mockCuboidModel: CuboidModel

    private val dummyLength = 12.0
    private val dummyWidth = 7.0
    private val dummyHeight = 6.0

    private val dummyVolume = 504.0
    private val dummyCircumference = 100.0
    private val dummySurfaceArea = 396.0

    @Before
    fun setUp() {
        mockCuboidModel = mock(CuboidModel::class.java)
        mainViewModel = MainViewModel(mockCuboidModel)
    }

    @Test
    fun getCircumference() {
        mockCuboidModel = CuboidModel()
        mainViewModel = MainViewModel(mockCuboidModel)
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        val circumference = mainViewModel.circumference
        assertEquals(dummyCircumference, circumference, 0.0001)
    }

    @Test
    fun getSurfaceArea() {
        mockCuboidModel = CuboidModel()
        mainViewModel = MainViewModel(mockCuboidModel)
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        val surfaceArea = mainViewModel.surfaceArea
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }

    @Test
    fun getVolume() {
        // arrange
        mockCuboidModel = CuboidModel()
        mainViewModel = MainViewModel(mockCuboidModel)
        // action
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        // assert
        val volume = mainViewModel.volume
        assertEquals(dummyVolume, volume, 0.0001)
        /**
         * Angka 0.0001 pada parameter ketiga dalam assertEquals() adalah angka delta yang
         * merupakan selisih range di belakang koma bilangan double.
         * */
    }

    @Test
    fun testMockVolume() {
        `when`(mainViewModel.volume).thenReturn(dummyVolume)
        val volume = mainViewModel.volume
        verify(mockCuboidModel).volume
        assertEquals(dummyVolume, volume, 0.0001)
    }

    @Test
    fun testMockCircumference() {
        `when`(mainViewModel.circumference).thenReturn(dummyCircumference)
        val circumference = mainViewModel.circumference
        verify(mockCuboidModel).circumference
        assertEquals(dummyCircumference, circumference, 0.0001)
    }

    @Test
    fun testMockSurfaceArea() {
        // stubbing
        `when`(mainViewModel.surfaceArea).thenReturn(dummySurfaceArea)
        val surfaceArea = mainViewModel.surfaceArea
        // verify that the surfaceArea has been called
        verify(mockCuboidModel).surfaceArea
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }
}