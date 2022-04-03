package com.pra.magnateageinterview.presentation.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.pra.magnateageinterview.data.model.Data
import com.pra.magnateageinterview.data.model.MenuResponseModel
import com.pra.magnateageinterview.presentation.utility.DateTimeUtils

class MainActivityViewModel(private val context: Context) : ViewModel() {

    val countryLoadError = MutableLiveData<String>()

    private var mSelectedObject = MutableLiveData<Data>()
    public val selectedObjectUpdate: LiveData<Data> = mSelectedObject

    var startDate = ""
    var endDate = ""
    var currentDate = ""

    var startTime = "";
    var endTime = "";
    var currentTime = ""

    private var menuResponse: MenuResponseModel? = null

    init {
        val data = context.readTextFromAsset("menu.json")
        menuResponse = Gson().fromJson(data, MenuResponseModel::class.java)
        println("Date=====>  " + menuResponse?.data?.size)

        getCurrentDateTime()

    }


    fun Context.readTextFromAsset(fileName: String): String {
        return assets.open(fileName).bufferedReader().use {
            it.readText()
        }
    }

    fun getCurrentDateTime() {
        val currentDateTime = DateTimeUtils.getCurrentDateTime()
        val list = currentDateTime.split(" ")
        currentDate = list[0]
        currentTime = list[1]
        println("Current Date time ==>" + currentDateTime)
    }


    fun getSelectedObject(): Data? {
        var mModel: Data? = null
        if (menuResponse?.data != null) {
            for (i in menuResponse?.data?.indices!!) {
                val validTime = DateTimeUtils.isBetweenLiedTwoTime(
                    context,
                    menuResponse?.data!![i].startTime,
                    menuResponse?.data!![i].endTime,
                    currentTime
                )
                println("Result time value==>$validTime")
                if (validTime) {
                    var endDate: String? = null;
                    endDate = if (menuResponse?.data!![i].endDate.equals("")) {
                        null
                    } else {
                        menuResponse?.data!![i].endDate
                    }

                    val validDate = DateTimeUtils.isDateBetweenTwoDate(
                        menuResponse?.data!![i].startDate, endDate, currentDate
                    )
                    if (validDate) {
                        Toast.makeText(
                            context,
                            "Data Got successfully in between Date-time",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        mModel = menuResponse?.data!![i]
                        break
                    }
                }
            }

        }
        mSelectedObject.value = mModel
        return mModel
    }


    fun getMenuList(mSelectObject: Data): MutableList<String> {
        var mImageList: MutableList<String> = ArrayList()
        for (i in mSelectObject.data.indices) {
            mImageList.add(mSelectObject.data[i])
        }
        //  mImageList.add("https://i.pinimg.com/564x/52/1e/4d/521e4d4ad17d24d802e6caf242b5cb3d.jpg")
        //   mImageList.add("https://i.pinimg.com/564x/52/1e/4d/521e4d4ad17d24d802e6caf242b5cb3d.jpg")
        //   mImageList.add("https://i.pinimg.com/564x/52/1e/4d/521e4d4ad17d24d802e6caf242b5cb3d.jpg")
        return mImageList
    }

    override fun onCleared() {
        super.onCleared()
    }
}