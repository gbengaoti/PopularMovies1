package com.example.popularmoviespart1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviespart1.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder>{
    private Movie[] mMovieList;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185";
    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final MovieAdapterOnClickHandler mClickHandler;

    void setMovieData(Movie[] movies) {
        this.mMovieList = movies;
        notifyDataSetChanged();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(Movie currentMovie);
    }

    MovieAdapter(MovieAdapterOnClickHandler mClickHandler){
        this.mClickHandler = mClickHandler;
    }

    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new MovieAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MyViewHolder holder, int position) {
        ImageView imageView = holder.mMovieImageView;
        Movie currentMovie = mMovieList[position];
        String getPath = currentMovie.getPosterPath();
        String fullPath = IMAGE_BASE_URL + getPath;

        Picasso.get()
                .load(fullPath)
                .placeholder(R.mipmap.placeholder_image)
                .error(R.mipmap.placeholder_error_image)
                .resize(WIDTH, HEIGHT)
                .into(imageView);

    }

    @Override
    public int getItemCount() {
        if (null == mMovieList) return 0;
        return mMovieList.length;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final ImageView mMovieImageView;

        MyViewHolder(@NonNull View view) {
            super(view);
            mMovieImageView = view.findViewById(R.id.movie_image_box);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie myMovie = mMovieList[adapterPosition];
            mClickHandler.onClick(myMovie);
        }
    }

}
