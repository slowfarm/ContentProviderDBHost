package ru.tander.contentproviderdbhost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRealm = Realm.getDefaultInstance();


        if (mRealm.where(ApplicationPolicies.class).findFirst() == null) {
            mRealm.executeTransaction(realm -> {
                for (int i = 0; i < 100; i++) {
                    ApplicationPolicies policies = realm.createObject(ApplicationPolicies.class);
                    policies.setId(i);
                    policies.setName("name " + i);
                }
            });
        }

        List<ApplicationPolicies> list = mRealm.where(ApplicationPolicies.class).findAll();
        for(ApplicationPolicies policies : list)
            Log.d("TAG", policies.getName());
    }
}
