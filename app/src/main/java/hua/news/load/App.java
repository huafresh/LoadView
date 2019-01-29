package hua.news.load;

import android.app.Application;

import com.hua.multilayout_core.MultiLayout;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/29 14:24
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MultiLayout.registerLoadingLayout(new LoadingLayout());
        MultiLayout.registerErrorLayout(new LoadErrorLayout());
        MultiLayout.registerLayoutProvider(1, new CustomLayout());

    }
}
