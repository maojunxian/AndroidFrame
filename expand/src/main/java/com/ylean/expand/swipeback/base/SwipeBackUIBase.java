package com.ylean.expand.swipeback.base;


import com.ylean.expand.swipeback.SwipeBackLayout;

/**
 * Created by maojunxian on 2017/1/23.
 */

public interface SwipeBackUIBase {
    SwipeBackLayout getSwipeBackLayout();

    void setSwipeBackEnable(boolean enable);

    void scrollToFinishActivity();
}
