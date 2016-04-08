package com.adriencadet.dao;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * BaseDAO
 * <p>
 */
abstract class BaseDAO {
    private RealmConfiguration realmConfiguration;

    BaseDAO(RealmConfiguration realmConfiguration) {
        this.realmConfiguration = realmConfiguration;
    }

    Realm getRealm() {
        Realm r = Realm.getInstance(realmConfiguration);
        r.refresh();
        return r;
    }
}
