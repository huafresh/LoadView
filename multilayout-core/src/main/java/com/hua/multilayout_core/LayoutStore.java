package com.hua.multilayout_core;

import android.util.SparseArray;

import java.util.List;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/29 10:31
 */

class LayoutStore {
    private static SparseArray<ILayoutProvider> providers;

    static void put(int type, ILayoutProvider provider) {
        if (providers == null) {
            providers = new SparseArray<>();
        }
        providers.put(type, provider);
    }

    static ILayoutProvider get(int type) {
        if (providers != null) {
            return providers.get(type);
        }
        return null;
    }

}
