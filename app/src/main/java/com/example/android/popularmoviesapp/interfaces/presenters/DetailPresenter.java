package com.example.android.popularmoviesapp.interfaces.presenters;

import android.net.Uri;

/**
 * Created by joliveira on 5/1/17.
 */

public interface DetailPresenter extends BasePresenter, BaseFragmentPresenter {
    String formatDate(String date);

    Uri buildURLTrailer(String key);
}
