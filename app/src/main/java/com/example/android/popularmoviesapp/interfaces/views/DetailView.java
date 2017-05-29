package com.example.android.popularmoviesapp.interfaces.views;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.android.popularmoviesapp.model.MovieModel;

/**
 * Created by joliveira on 5/1/17.
 */

public interface DetailView {
    void showProgress();

    void hideProgress();

    void setItemDetail(MovieModel movieModel);

    void showMessage(String msg);

    Context getContextView();

    void getPackageManagerApp(PackageManager packageManager);

    void setColorFavorite(boolean isFavorite);
}
