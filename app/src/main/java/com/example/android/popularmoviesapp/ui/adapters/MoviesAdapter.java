package com.example.android.popularmoviesapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.ui.listeners.MoviesAdapterOnClickHandler;
import com.example.android.popularmoviesapp.data.model.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by joliveira on 5/1/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    List<MovieModel> mMovies;
    private MoviesAdapterOnClickHandler mClickHandler;

    public MoviesAdapter(MoviesAdapterOnClickHandler handler) {
        mClickHandler = handler;
    }

    public void setMovies(List<MovieModel> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public List<MovieModel> getMovies() {
        return mMovies;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int)}. Since it will be re-used to display different
     * items in the data set, it is a good idea to cache references to sub views of the View to
     * avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MoviesAdapter.MoviesViewHolder viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_list, parent, false);
        viewHolder = new MoviesAdapter.MoviesViewHolder(layoutView);
        return viewHolder;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method
     * should update the contents of the {@link ViewHolder#itemView} to reflect the item at
     * the given position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this
     * method again if the position of the item changes in the data set unless the item itself
     * is invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside this
     * method and should not keep a copy of it. If you need the position of an item later on
     * (e.g. in a click listener), use {@link ViewHolder#getPosition()} which will have the
     * updated position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        MovieModel movie = mMovies.get(position);
        Picasso.with(holder.context).load(movie.getThumbnail()).into(holder.image);
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
    }


    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView image;
        public Context context;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.img_item_movie);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getPosition();
            mClickHandler.clickItemListener(mMovies.get(position));
        }
    }
}
