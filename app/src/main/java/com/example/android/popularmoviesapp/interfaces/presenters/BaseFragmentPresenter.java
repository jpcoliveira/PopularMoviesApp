package com.example.android.popularmoviesapp.interfaces.presenters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by joliveira on 5/2/17.
 */

public interface BaseFragmentPresenter {
    void onViewCreated(View view, @Nullable Bundle savedInstanceState);
}
