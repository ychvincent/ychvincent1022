package com.ychvincent.itproject2;

import android.view.View;

/**
 * Created by ychvi on 12/5/2018.
 */

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}