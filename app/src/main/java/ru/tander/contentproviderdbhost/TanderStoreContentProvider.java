package ru.tander.contentproviderdbhost;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class TanderStoreContentProvider extends ContentProvider {

    private Realm mRealm;

    private final String TAG = "CONTENT PROVIDER";

    static final String[] sColumns = new String[]{"id", "name"};

    List<ApplicationPolicies> policies;


    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate: ");
        Realm.init(getContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        mRealm = Realm.getDefaultInstance();

        policies = mRealm.copyFromRealm(mRealm.where(ApplicationPolicies.class).findAll());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //projection - столбцы
        //selection - условие
        //selectionArgs - аргументы для условия
        //sortOrder - сортировка
        if (selection != null && selection.equals("ru.tander.contentprovidersamp")) {
            Log.d(TAG, "query: ");
            MatrixCursor matrixCursor = new MatrixCursor(sColumns);

            for (ApplicationPolicies policy : policies) {
                Object[] rowData = new Object[]{policy.getId(), policy.getName()};
                matrixCursor.addRow(rowData);
            }
            return matrixCursor;
        } else {
            throw new IllegalArgumentException("Application does not have rights to receive policies");
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
