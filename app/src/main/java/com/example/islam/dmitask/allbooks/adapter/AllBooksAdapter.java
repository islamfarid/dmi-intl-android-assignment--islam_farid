package com.example.islam.dmitask.allbooks.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.islam.dmitask.R;
import com.example.islam.dmitask.models.Book;
import com.sa90.infiniterecyclerview.InfiniteAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by islam on 10/09/16.
 */
public class AllBooksAdapter extends InfiniteAdapter implements OnItemClickListener {
    ArrayList<Book> books;
    Context context;
    OnItemClickListener mItemClickListener;

    public AllBooksAdapter(Context context, ArrayList<Book> books) {
        this.books = books;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder getLoadingViewHolder(ViewGroup parent) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_loading_item, parent, false);
        return new LoadingItem(item);
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public int getViewType(int position) {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_list_item, parent, false);
        return new BookItem(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BookItem) {
            Book book = books.get(position);
            ((BookItem) holder).bookTitle.setText(book.getTitle());
            ((BookItem) holder).bookPrice.setText(book.getPrice() + "$");
        }
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    class BookItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.title_textview)
        TextView bookTitle;
        @Bind(R.id.price_textview)
        TextView bookPrice;

        public BookItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    class LoadingItem extends RecyclerView.ViewHolder {
        @Bind(R.id.progressBar)
        ProgressBar loadingBar;

        public LoadingItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
