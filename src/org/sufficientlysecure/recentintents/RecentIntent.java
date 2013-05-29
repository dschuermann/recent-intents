/*
 * Copyright (C) 2013 Dominik Schürmann <dominik@dominikschuermann.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sufficientlysecure.recentintents;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RecentIntent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_intent);

        Button button = (Button) findViewById(R.id.button);
        final EditText text = (EditText) findViewById(R.id.editText);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                final List<RecentTaskInfo> recentTasks = activityManager.getRecentTasks(
                        Integer.MAX_VALUE, ActivityManager.RECENT_WITH_EXCLUDED);

                for (RecentTaskInfo task : recentTasks) {
                    Intent i = task.baseIntent;

                    text.setText("intent package: " + i.getPackage() + "\n");
                    text.setText("intent action: " + i.getAction() + "\n");
                    // On Android >= 4.1.1 extras is null
                    // http://stackoverflow.com/questions/15501260/protecting-extra-data-in-android-intent
                    Bundle bundle = i.getExtras();
                    if (bundle != null) {
                        Set<String> ks = bundle.keySet();
                        Iterator<String> iterator = ks.iterator();

                        text.setText("Bundle extras:" + "\n");
                        text.setText("------------------------------" + "\n");
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            Object value = bundle.get(key);

                            if (value != null) {
                                text.setText(key + " : " + value.toString() + "\n");
                            } else {
                                text.setText(key + " : null" + "\n");
                            }
                        }
                        text.setText("------------------------------" + "\n");
                    } else {
                        text.setText("Bundle extras: null" + "\n");
                    }
                }

            }
        });

    }

}
