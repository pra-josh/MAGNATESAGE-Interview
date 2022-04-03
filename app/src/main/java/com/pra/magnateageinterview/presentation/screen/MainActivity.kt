package com.pra.magnateageinterview.presentation.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pra.magnateageinterview.data.model.Data
import com.pra.magnateageinterview.databinding.ActivityMainBinding
import com.pra.magnateageinterview.presentation.adapter.CustomPagerAdapter
import com.pra.magnateageinterview.presentation.listener.OnItemClickListener
import com.pra.magnateageinterview.presentation.viewmodel.MainActivityViewModel
import com.pra.myapplication.UI.Util.ViewModelFactory

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var mAdapter: CustomPagerAdapter
    private lateinit var mViewModel: MainActivityViewModel
    private lateinit var mBinding: ActivityMainBinding
    private var mImageList: MutableList<String> = ArrayList()
    private lateinit var mSelectObject: Data


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
                mSelectObject = it
            }
        })

        mSelectObject = mViewModel.getSelectedObject()!!

        if (mSelectObject != null) {
            println("Model =================>" + mSelectObject)
            mImageList = mViewModel.getMenuList(mSelectObject)
        } else {
            Toast.makeText(this, "Selected time or Date is not Proper", Toast.LENGTH_SHORT).show()
        }

        mBinding.viewPager.startAutoScroll()
        mBinding.viewPager.interval = mSelectObject.timeInterval.toLong() * 1000
        mBinding.viewPager.isCycle = true
        mAdapter = CustomPagerAdapter(this, mImageList, this)
        mBinding.viewPager.adapter = mAdapter
        mBinding.indicator.setViewPager(mBinding.viewPager)

    }

    fun setActionListeners() {
    }

    override fun onItemClick(position: Int) {
     //   Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
    }
}