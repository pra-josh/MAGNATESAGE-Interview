package com.pra.magnateageinterview.presentation.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pra.magnateageinterview.data.model.Data
import com.pra.magnateageinterview.databinding.ActivityMainBinding
import com.pra.magnateageinterview.presentation.adapter.CustomPagerAdapter
import com.pra.magnateageinterview.presentation.listener.OnItemClickListener
import com.pra.magnateageinterview.presentation.viewmodel.MainActivityViewModel
import com.pra.myapplication.UI.Util.ViewModelFactory
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private var mAdapter: CustomPagerAdapter? = null
    private lateinit var mViewModel: MainActivityViewModel
    private lateinit var mBinding: ActivityMainBinding
    private var mImageList: MutableList<String> = ArrayList()
    private var mSelectObject: Data? = null


    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        initComponents()
        prepareView()
    }


    fun initComponents() {
        val factory = ViewModelFactory(this)
        mViewModel = ViewModelProvider(this, factory)[MainActivityViewModel::class.java]

    }

    fun prepareView() {

        mViewModel.selectedObjectUpdate.observe(this, Observer {
            it.let {
                if (it != null) {
                    if (mSelectObject == null) {
                        mSelectObject = it
                    } else {
                        if (mSelectObject?.id!! == it?.id) {
                            return@let
                        } else {
                            mSelectObject = it
                        }
                    }
                }
                if (mSelectObject != null) {
                    println("Model =================>" + mSelectObject)
                    mImageList = mViewModel.getMenuList(mSelectObject!!)
                    mBinding.viewPager.startAutoScroll()
                    mBinding.viewPager.interval = mSelectObject?.timeInterval?.toLong()!! * 1000
                    mBinding.viewPager.isCycle = true
                    mAdapter = CustomPagerAdapter(this, mImageList, this)
                    mBinding.viewPager.adapter = mAdapter
                    mBinding.indicator.setViewPager(mBinding.viewPager)

                } else {
                    mBinding.flViewPager.visibility = View.GONE
                    mBinding.tvError.visibility = View.VISIBLE
                    mBinding.tvError.text = "Selected time or Date is not Proper"
                    Toast.makeText(this, "Selected time or Date is not Proper", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }


    override fun onResume() {
        super.onResume()
        val mSelectObjectNew = mViewModel.getSelectedObject()
        startUpdates()
    }

    override fun onPause() {
        // stopTimer()
        stopUpdates()
        super.onPause()
    }



    override fun onItemClick(position: Int) {
        //   Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
    }


    val scope = MainScope() // could also use an other scope such as viewModelScope if available
    var job: Job? = null

    fun startUpdates() {
        stopUpdates()
        job = scope.launch(Dispatchers.IO) {
            while (true) {
                mViewModel.getSelectedObject()
                delay(20000)
            }

        }
    }

    fun stopUpdates() {
        job?.cancel()
        job = null
    }
}