package com.pra.magnateageinterview.presentation.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.pra.magnateageinterview.databinding.RowViewImageBinding
import com.pra.magnateageinterview.presentation.listener.OnItemClickListener
import com.pra.magnateageinterview.presentation.utility.Utility

public class CustomPagerAdapter(
    val context: Context, var listMenu: List<String>,
    var onItemClickListener: OnItemClickListener
) : PagerAdapter() {

    var mLayoutInflater: LayoutInflater? = null


    init {
        mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }


    override fun getCount(): Int {
        return if (listMenu != null) {
            listMenu.size
        } else {
            0
        }
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mBinding = RowViewImageBinding.inflate(mLayoutInflater!!, container, false)
        Glide.with(context)
            .load(listMenu[position])
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    mBinding.progress.visibility = View.GONE
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    mBinding.progress.visibility = View.GONE
                    return false
                }
            })
            .into(mBinding.imageView)



        Glide.with(context)
            .load(listMenu[position])
            .centerCrop()
            .into(mBinding.imageView);

        container.addView(mBinding.root)
        mBinding.llParent.setOnClickListener {
           // Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
        }
        return mBinding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, `object`)
        container.removeView(`object` as View)
    }


}