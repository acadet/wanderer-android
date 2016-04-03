package com.adriencadet.wanderer.models.dao;

import android.content.Context;

import io.realm.Realm;

/**
 * BaseDAO
 * <p>
 */
abstract class BaseDAO {
    private Context context;

    BaseDAO(Context context) {
        this.context = context;
    }

    Realm getRealm() {
        Realm r = Realm.getInstance(context);
        r.refresh();
        return r;
    }
}
