package com.example.islam.dmitask.bookdetails.view;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.islam.dmitask.R;
import com.example.islam.dmitask.application.DMITaskApplication;
import com.example.islam.dmitask.bookdetails.presenter.BookDetailsPresenter;
import com.example.islam.dmitask.common.Constants;
import com.example.islam.dmitask.db.dto.BookDetail;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by islam on 11/09/16.
 */
public class BookDetailsFragment extends DialogFragment implements BookDetailsView {
    @Bind(R.id.progressBar)
    ProgressBar loadingBar;
    @Bind(R.id.book_title_textview)
    TextView mBookTitleTextView;
    @Bind(R.id.book_imageview)
    ImageView mBookImageView;
    @Bind(R.id.book_auther_textview)
    TextView mAutherTextView;
    @Bind(R.id.book_price_textview)
    TextView mBookPriceTextView;
    @Inject
    BookDetailsPresenter bookDetailsPresenter;

    public BookDetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container);
        ButterKnife.bind(this, view);
        attachPresenter();
        bookDetailsPresenter.getBookDetails(getArguments().getBoolean(Constants.IS_LOCAL_BOOK),
                getArguments().getString(Constants.BOOK_DETALS_KEY));
        return view;
    }

    private void attachPresenter() {
        DMITaskApplication.getInstance().getApplicationComponent().inject(this);
        bookDetailsPresenter.setView(this);
    }

    @Override
    public void showProgress() {
        loadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loadingBar.setVisibility(View.GONE);
    }

    @Override
    public void showBookDetails(BookDetail bookDetail) {
        Glide.with(getActivity()).load(bookDetail.getImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mBookImageView);
        mBookTitleTextView.setText(getResources().getString(R.string.book_title_text) + " : " + bookDetail.getTitle());
        mBookPriceTextView.setText(getResources().getString(R.string.book_price_text) + " : " + bookDetail.getPrice() + "$");
        mAutherTextView.setText(getResources().getString(R.string.book_auther_text) + " : " + bookDetail.getAuthor());
    }

    @Override
    public void showErrorMessage(String message) {
        Snackbar.make(mBookTitleTextView, message, Snackbar.LENGTH_LONG).show();

    }
}
