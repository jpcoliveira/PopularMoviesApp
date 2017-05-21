package com.example.android.popularmoviesapp.interfaces.presenters;

import android.os.Bundle;

/**
 * Created by joliveira on 5/1/17.
 */

public interface MainPresenter extends BasePresenter {
    void onItemMenuClicked(int menuItemId);
    void onCreate(Bundle savedInstanceState);
}
