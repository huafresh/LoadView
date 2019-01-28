package com.hua.loadview;

import android.view.View;

/**
 * Author: hua
 * Created: 2017/9/26
 * Description:
 * 显示布局
 */

public abstract class LoadView {

    /**
     * 获取布局的视图
     *
     * @return 布局的视图
     */
    protected abstract View getContentView();

    /**
     * 重新加载监听
     */
    public interface ReLoadListener {
        void onReLoad(View v);
    }
}
