package company.solnyshko.mobileapp

import android.content.Context
import company.solnyshko.mobileapp.ParcelList.Parcel
import company.solnyshko.mobileapp.util.SharedPreferencesWrapper
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner

// Testing sharedPreferences - our local database for app
@RunWith(MockitoJUnitRunner::class)
class LocalDbaseTest {
    @Mock
    lateinit var mockContext : Context
    val sharedPreferences = SharedPreferencesImpl()
    lateinit var sharedPreferencesWrapper : SharedPreferencesWrapper

    @Test
    fun testPutMyParcel() {
        Mockito.`when`(mockContext.getSharedPreferences("userData", Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences)
        sharedPreferencesWrapper = SharedPreferencesWrapper(mockContext)
        // put parcel
        val parcel = Parcel(R.drawable.box, "Small box 10x10", false)
        sharedPreferencesWrapper.putMyParcel(parcel)
        // check if parcel exists
        val list = sharedPreferencesWrapper.getMyParcels()
        val actual = list.first()
        assertTrue("Parcels are not equal!", parcel.equals(actual))
    }

    @Test
    fun testPutParcelToDeliver() {
        Mockito.`when`(mockContext.getSharedPreferences("userData", Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences)
        sharedPreferencesWrapper = SharedPreferencesWrapper(mockContext)
        // put parcel
        val parcel = Parcel(R.drawable.box, "Small box 10x10", false)
        sharedPreferencesWrapper.putParcelsToDeliver(arrayListOf(parcel))
        // check if parcel exists
        val list = sharedPreferencesWrapper.getParcelsToDeliver()
        val actual = list.first()
        assertTrue("Parcels are not equal!", parcel.equals(actual))
    }

    @Test
    fun testPutParcelToPick() {
        Mockito.`when`(mockContext.getSharedPreferences("userData", Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences)
        sharedPreferencesWrapper = SharedPreferencesWrapper(mockContext)
        // put parcel
        val parcel = Parcel(R.drawable.box, "Small box 10x10", false)
        sharedPreferencesWrapper.putParcelsToPick(arrayListOf(parcel))
        // check if parcel exists
        val list = sharedPreferencesWrapper.getParcelsToPick()
        val actual = list.first()
        assertTrue("Parcels are not equal!", parcel.equals(actual))
    }

    @Test
    fun testDeleteAllMyParcels() {
        Mockito.`when`(mockContext.getSharedPreferences("userData", Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences)
        sharedPreferencesWrapper = SharedPreferencesWrapper(mockContext)
        // put parcel
        val parcel = Parcel(R.drawable.box, "Small box 10x10", false)
        sharedPreferencesWrapper.putMyParcel(parcel)
        // delete parcels
        sharedPreferencesWrapper.deleteAllMyParcels()
        val actual = sharedPreferencesWrapper.getMyParcels()
        // check if list is empty now
        assertTrue("'My parcels' isn't empty", actual.isEmpty())
    }

    @Test
    fun testDeleteParcelToDeliver() {
        Mockito.`when`(mockContext.getSharedPreferences("userData", Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences)
        sharedPreferencesWrapper = SharedPreferencesWrapper(mockContext)
        // put parcel
        val parcel = Parcel(R.drawable.box, "Small box 10x10", false)
        sharedPreferencesWrapper.putParcelsToDeliver(arrayListOf(parcel))
        // delete parcel
        sharedPreferencesWrapper.deleteFromParcelToDeliver(parcel)
        val actual = sharedPreferencesWrapper.getParcelsToDeliver()
        // check if returned list doesn't contain deleted parcel
        assertTrue("'Parcels' to deliver isn't empty", !(actual.contains(parcel)))
    }

    @Test
    fun testDeleteAllParcelsToDeliver() {
        Mockito.`when`(mockContext.getSharedPreferences("userData", Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences)
        sharedPreferencesWrapper = SharedPreferencesWrapper(mockContext)
        // put parcel
        val parcel = Parcel(R.drawable.box, "Small box 10x10", false)
        sharedPreferencesWrapper.putParcelsToDeliver(arrayListOf(parcel))
        // delete parcel
        sharedPreferencesWrapper.deleteAllParcelToDeliver()
        val actual = sharedPreferencesWrapper.getParcelsToDeliver()
        // check if returned list doesn't contain deleted parcel
        assertTrue("'Parcels' to deliver isn't empty", actual.isEmpty())
    }

    @Test
    fun testDeleteParcelToPick() {
        Mockito.`when`(mockContext.getSharedPreferences("userData", Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences)
        sharedPreferencesWrapper = SharedPreferencesWrapper(mockContext)
        // put parcel
        val parcel = Parcel(R.drawable.box, "Small box 10x10", false)
        sharedPreferencesWrapper.putParcelsToPick(arrayListOf(parcel))
        // delete parcel
        sharedPreferencesWrapper.deleteFromParcelToPick(parcel)
        val actual = sharedPreferencesWrapper.getParcelsToPick()
        // check if returned list doesn't contain deleted parcel
        assertTrue("'Parcels' to pick up isn't empty", !(actual.contains(parcel)))
    }

    @Test
    fun testDeleteAllParcelsToPick() {
        Mockito.`when`(mockContext.getSharedPreferences("userData", Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences)
        sharedPreferencesWrapper = SharedPreferencesWrapper(mockContext)
        // put parcel
        val parcel = Parcel(R.drawable.box, "Small box 10x10", false)
        sharedPreferencesWrapper.putParcelsToPick(arrayListOf(parcel))
        // delete parcel
        sharedPreferencesWrapper.deleteAllParcelToPick()
        val actual = sharedPreferencesWrapper.getParcelsToPick()
        // check if returned list doesn't contain deleted parcel
        assertTrue("'Parcels' to pick up isn't empty", actual.isEmpty())
    }
}