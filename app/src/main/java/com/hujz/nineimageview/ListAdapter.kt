package com.hujz.nineimageview

import android.content.Context
import android.widget.ImageView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hujz.imageloader.loader.ImageLoader
import com.lxj.imagegridview.ImageGridAdapter
import com.lxj.imagegridview.ImageGridView
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.ImageViewerPopupView
import com.lxj.xpopup.interfaces.XPopupImageLoader
import java.io.File
import java.util.ArrayList

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
                    val urls2 = ArrayList<Any>()
                    urls.forEach { urls2.add(it) }
                    XPopup.Builder(context).asImageViewer(
                        gridView.getChildAt(position) as ImageView,
                        position,
                        urls2,
                        { popupView: ImageViewerPopupView, pos: Int ->
                            popupView.updateSrcView(
                                gridView.getChildAt(pos) as ImageView
                            )
                        },
                        ImageLoader()
                    ).show()
                }

                override fun loadImage(imageView: ImageView, position: Int, url: String) {
                    com.hujz.imageloader.loader.ImageLoader.instance.with {
                        this.url = url
                        overrideWidth = Target.SIZE_ORIGINAL
                        overrideHeight = Target.SIZE_ORIGINAL
                    }.into(imageView)
                }
            })
    }

    internal inner class ImageLoader : XPopupImageLoader {
        override fun loadImage(position: Int, url: Any, imageView: ImageView) {
            //必须指定Target.SIZE_ORIGINAL，否则无法拿到原图，就无法享用天衣无缝的动画
            com.hujz.imageloader.loader.ImageLoader.instance.with {
                this.url = url as String
                overrideWidth = Target.SIZE_ORIGINAL
                overrideHeight = Target.SIZE_ORIGINAL
            }.into(imageView)
        }

        override fun getImageFile(context: Context, uri: Any): File? {
            try {
                return Glide.with(context).downloadOnly().load(uri).submit().get()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

}