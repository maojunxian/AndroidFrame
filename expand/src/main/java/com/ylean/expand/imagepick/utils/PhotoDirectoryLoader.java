package com.ylean.expand.imagepick.utils;

import android.content.Context;
import android.provider.MediaStore.Images.Media;
import android.support.v4.content.CursorLoader;

import com.ylean.expand.imagepick.picker.PhotoFilter;

import static android.provider.MediaStore.MediaColumns.MIME_TYPE;


/**
 * ================================================
 * 作    者：maojunxian
 * 版    本：1.0
 * 创建日期：2017/5/27
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PhotoDirectoryLoader extends CursorLoader {

    private final String[] IMAGE_PROJECTION = {
            Media.DATA,
            Media.DISPLAY_NAME,
            Media.DATE_ADDED,
            Media.MIME_TYPE,
            Media.SIZE,
            Media._ID};

    public PhotoDirectoryLoader(Context context, PhotoFilter filter) {
        super(context);

        setProjection(IMAGE_PROJECTION);
        setUri(Media.EXTERNAL_CONTENT_URI);
        setSortOrder(Media.DATE_ADDED + " DESC");

        setSelection("(" + MIME_TYPE + "=? or " + MIME_TYPE + "=? or " + MIME_TYPE + "=?" +
                (filter.showGif ? (" or " + MIME_TYPE + "=? ") : "") + ")" +
                filterCofing(filter));

        String[] selectionArgs;
        if (filter.showGif) {
            selectionArgs = new String[]{"image/jpeg", "image/png", "image/jpg", "image/gif"};
        } else {
            selectionArgs = new String[]{"image/jpeg", "image/png", "image/jpg"};
        }

        setSelectionArgs(selectionArgs);
    }

    private String filterCofing(PhotoFilter filter) {
        StringBuilder sb = new StringBuilder();
        // 图片最低宽度
        if (filter.minWidth > 0) {
            sb.append(" and ");
            sb.append(Media.WIDTH + " >= " + filter.minWidth);
        }
        // 图片最低高低
        if (filter.minHeight > 0) {
            sb.append(" and ");
            sb.append(Media.HEIGHT + " >= " + filter.minHeight);
        }
        if (filter.minSize > 0) {
            sb.append(" and ");
            sb.append(Media.SIZE + " >= " + filter.minSize);
        }
        return sb.toString();
    }
}