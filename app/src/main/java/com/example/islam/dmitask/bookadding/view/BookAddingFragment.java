package com.example.islam.dmitask.bookadding.view;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.islam.dmitask.R;
import com.example.islam.dmitask.allbooks.adapter.AllBooksAdapter;
import com.example.islam.dmitask.application.DMITaskApplication;
import com.example.islam.dmitask.bookadding.presenter.BookAddingPresenter;
import com.example.islam.dmitask.common.Constants;
import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by islam on 11/09/16.
 */
public class BookAddingFragment extends DialogFragment implements BookAddingView {
    @Bind(R.id.progressBar)
    ProgressBar loadingBar;
    @Bind(R.id.book_id_edittext)
    EditText mBookIdEditField;
    @Bind(R.id.book_price_edittext)
    EditText mBookPriceEditField;
    @Bind(R.id.book_auther_edittext)
    EditText mAutherEditField;
    @Bind(R.id.book_title_edittext)
    EditText mBookTitleEditField;
    @Bind(R.id.book_image_edittext)
    EditText mBookImageEditField;
    @Bind(R.id.add_button)
    Button mAddBookButton;
    @Inject
    BookAddingPresenter bookAddingPresenter;
    AllBooksAdapter allBooksAdapter;

    public BookAddingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_adding, container);
        ButterKnife.bind(this, view);
        getDialog().setTitle(getResources().getString(R.string.add_new_book_title));
        attachPresenter();
        paramFormValidationNotEmty();
        return view;
    }

    private void paramFormValidationNotEmty() {

        Observable<Boolean> bookIDbservable = RxTextView.textChanges(mBookIdEditField)
                .map(inputText -> (!(inputText.length() == 0)));

        Observable<Boolean> priceObservable = RxTextView.textChanges(mBookPriceEditField)
                .map(inputText -> (!(inputText.length() == 0)));

        Observable<Boolean> autherObservable = RxTextView.textChanges(mAutherEditField)
                .map(inputText -> (!(inputText.length() == 0)));
        Observable<Boolean> titleObservable = RxTextView.textChanges(mBookTitleEditField)
                .map(inputText -> (!(inputText.length() == 0)));
        Observable<Boolean> imageObservable = RxTextView.textChanges(mBookImageEditField)
                .map(inputText -> (!(inputText.length() == 0)));
        Observable.combineLatest(bookIDbservable, priceObservable,
                autherObservable, titleObservable, imageObservable, (aBoolean, aBoolean2, aBoolean3,
                                                                     aBoolean4, aBoolean5
                ) -> (aBoolean && aBoolean2 && aBoolean3 && aBoolean4 && aBoolean5))
                .subscribe(valid -> mAddBookButton.setEnabled(valid));

    }

    private void attachPresenter() {
        DMITaskApplication.getInstance().getApplicationComponent().inject(this);
        bookAddingPresenter.setView(this);
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
    public void dismiss() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constants.BOOK_ID, mBookIdEditField.getText().toString());
        getTargetFragment().onActivityResult(getTargetRequestCode(), getActivity().RESULT_OK, resultIntent);
        getDialog().dismiss();
    }

    @Override
    public void showErrorMessage(String message) {
        Snackbar.make(loadingBar, message, Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.add_button)
    public void addBook() {
        bookAddingPresenter.newBook(mBookIdEditField.getText().toString(), mBookPriceEditField.
                getText().toString(), mAutherEditField.getText().toString(), mBookTitleEditField.
                getText().toString(), mBookImageEditField.getText().toString());
    }
}
