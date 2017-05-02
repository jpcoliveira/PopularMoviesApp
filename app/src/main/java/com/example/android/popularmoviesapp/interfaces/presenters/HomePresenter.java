package com.example.android.popularmoviesapp.interfaces.presenters;

/**
 * Created by joliveira on 5/1/17.
 */

public interface HomePresenter extends BasePresenter, BaseFragmentPresenter {

    void onIntemClicked(int position);

    void onItemMenuClicked(int menuItemId);
}
