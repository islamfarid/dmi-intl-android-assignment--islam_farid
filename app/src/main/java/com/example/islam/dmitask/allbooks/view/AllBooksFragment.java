package com.example.islam.dmitask.allbooks.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.islam.dmitask.R;
import com.example.islam.dmitask.allbooks.adapter.AllBooksAdapter;
import com.example.islam.dmitask.allbooks.presenter.AllBooksPresenter;
import com.example.islam.dmitask.application.DMITaskApplication;
import com.example.islam.dmitask.bookdetails.view.BookDetailsFragment;
import com.example.islam.dmitask.common.Constants;
import com.example.islam.dmitask.models.Book;
import com.sa90.infiniterecyclerview.InfiniteRecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by islam on 10/09/16.
 */
public class AllBooksFragment extends Fragment implements AllBooksView {
    @Bind(R.id.books_recyclerview)
    InfiniteRecyclerView mAllBooksRecyclerView;
    @Bind(R.id.progressBar)
    ProgressBar loadingBar;
    @Inject
    AllBooksPresenter allBooksPresenter;
    AllBooksAdapter allBooksAdapter;
    private int pageElementsNumber = 10;

    public AllBooksFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_books, container, false);
        ButterKnife.bind(this, view);
        initBooksList();
        attachPresenter();
        return view;
    }


    private void attachPresenter() {
        DMITaskApplication.getInstance().getApplicationComponent().inject(this);
        allBooksPresenter.setView(this);
        allBooksPresenter.loadAllBooks(String.valueOf(0), true, String.valueOf(pageElementsNumber));// first time skip no element
    }

    @Override
    public void showProgress() {
        loadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMessage(String message) {
        Snackbar.make(mAllBooksRecyclerView, message, Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void setBooks(ArrayList<Book> books) {
        int positoion = allBooksAdapter.getCount() + 1;
        allBooksAdapter.getBooks().addAll(books);
        mAllBooksRecyclerView.moreDataLoaded(positoion, books.size());
    }

    @Override
    public void hideProgress() {
        loadingBar.setVisibility(View.GONE);
    }

    @Override
    public void setShouldLoadMore(boolean loadMore) {
        mAllBooksRecyclerView.setShouldLoadMore(false);
        mAllBooksRecyclerView.getAdapter().notifyDataSetChanged();
        Snackbar.make(mAllBooksRecyclerView, getResources().getString(R.string.no_more_items), Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void setShouldLoadMore(Book book, boolean loadMore) {
        allBooksAdapter.getBooks().add(0, book);
        allBooksAdapter.notifyDataSetChanged();
        mAllBooksRecyclerView.setShouldLoadMore(false);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == Constants.MY_REQUEST_CODE) {
            allBooksPresenter.getBookById(data.getExtras().getString(Constants.BOOK_ID));
        }
    }

    private void initBooksList() {
        mAllBooksRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        allBooksAdapter = new AllBooksAdapter(getActivity(), new ArrayList<>());
        mAllBooksRecyclerView.setAdapter(allBooksAdapter);
        mAllBooksRecyclerView.setOnLoadMoreListener(() -> {
            allBooksPresenter.
                    loadAllBooks(String.valueOf(mAllBooksRecyclerView.getAdapter().getItemCount()), false, String.valueOf(pageElementsNumber));
        });
        allBooksAdapter.setOnItemClickListener((view, position) ->
                startBookDetailsFragment(position));
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                allBooksAdapter.notifyItemRemoved(viewHolder.getLayoutPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mAllBooksRecyclerView);
    }

    private void startBookDetailsFragment(int position) {
        BookDetailsFragment bookDetailsFragment = new BookDetailsFragment();
        Book book = allBooksAdapter.getBooks().get(position);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.IS_LOCAL_BOOK, book.isLocalBook());
        if (book.isLocalBook()) {
            bundle.putString(Constants.BOOK_DETALS_KEY, book.getId());
        } else {
            bundle.putString(Constants.BOOK_DETALS_KEY, book.getLink());
        }
        bookDetailsFragment.setArguments(bundle);
        bookDetailsFragment.show(getFragmentManager(), BookDetailsFragment.class.getName());
    }
}

