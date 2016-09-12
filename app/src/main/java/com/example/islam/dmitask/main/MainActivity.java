package com.example.islam.dmitask.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.islam.dmitask.R;
import com.example.islam.dmitask.bookadding.view.BookAddingFragment;
import com.example.islam.dmitask.common.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.add_book_floatingactionbutton)
    FloatingActionButton mAddBookButton;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.add_book_floatingactionbutton)
    public void addBook() {
        BookAddingFragment bookAddingFragment = new BookAddingFragment();
        bookAddingFragment.show(getFragmentManager(), BookAddingFragment.class.getName());
        bookAddingFragment.setTargetFragment(getFragmentManager().
                findFragmentById(R.id.all_books_fragment), Constants.MY_REQUEST_CODE);

    }
}
