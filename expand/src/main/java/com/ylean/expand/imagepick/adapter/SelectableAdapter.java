package com.ylean.expand.imagepick.adapter;


import android.support.v7.widget.RecyclerView;

import com.ylean.expand.imagepick.bean.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：maojunxian
 * 版    本：1.0
 * 创建日期：2017/5/27
 * 描    述：
 * 修订历史：
 * ================================================
 */
public abstract class SelectableAdapter extends RecyclerView.Adapter implements Selectable {

    protected List<Image> mImages = new ArrayList<>();
    protected List<Image> mSelectedImages = new ArrayList<>();

    @Override
    public boolean isSelected(Image image) {
        return getSelectedPhotos().contains(image);
    }

    @Override
    public void toggleSelection(Image image) {
        if (getSelectedPhotos().contains(image)) {
            getSelectedPhotos().remove(image);
        } else {
            getSelectedPhotos().add(image);
        }
    }

    @Override
    public void clearSelection() {
        mSelectedImages.clear();
    }

    @Override
    public int getSelectedItemCount() {
        return mSelectedImages.size();
    }

    @Override
    public List<Image> getSelectedPhotos() {
        return mSelectedImages;
    }

    /**
     * 设置数据集
     *
     * @param images
     */
    public void setData(List<Image> images) {
        clearSelection();
        if (images != null && images.size() > 0) {
            mImages = images;
        } else {
            mImages.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 通过图片路径设置默认选择
     *
     * @param resultList
     */
    public void setDefaultSelected(ArrayList<String> resultList) {
        mSelectedImages.clear();
        for (String path : resultList) {
            Image image = getImageByPath(path);
            if (image != null) {
                mSelectedImages.add(image);
            }
        }
        notifyDataSetChanged();
    }

    private Image getImageByPath(String path) {
        if (mImages != null && mImages.size() > 0) {
            for (Image image : mImages) {
                if (image.path.equalsIgnoreCase(path)) {
                    return image;
                }
            }
        }
        return null;
    }
}