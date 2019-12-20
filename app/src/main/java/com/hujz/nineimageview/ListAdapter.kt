package com.hujz.nineimageview

import android.content.Context
import android.widget.ImageView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hujz.imageloader.loader.ImageLoader
import com.lxj.imagegridview.ImageGridAdapter
import com.lxj.imagegridview.ImageGridView

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/20
 *     desc   :
 * </pre>
 */
class ListAdapter(layoutResId: Int, data: List<CircleInfo>?) :
    BaseQuickAdapter<CircleInfo, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: CircleInfo) {
        helper.getView<ImageGridView>(R.id.mIgvList)
            .setAdapter(object : ImageGridAdapter(mContext, item.list) {
                override fun onImageItemClick(
                    context: Context,
                    gridView: ImageGridView,
                    position: Int,
                    urls: MutableList<String>
                ) {

                }

                override fun loadImage(imageView: ImageView, position: Int, url: String) {
                    ImageLoader.instance.with {
                        this.url = url
                        overrideWidth = Target.SIZE_ORIGINAL
                        overrideHeight =Target.SIZE_ORIGINAL
                    }.into(imageView)
                }
            })
    }

}