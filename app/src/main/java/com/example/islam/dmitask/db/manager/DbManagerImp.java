package com.example.islam.dmitask.db.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.islam.dmitask.db.dao.BookDetailDao;
import com.example.islam.dmitask.db.dbhelper.DaoMaster;
import com.example.islam.dmitask.db.dbhelper.DaoSession;
import com.example.islam.dmitask.db.dto.BookDetail;
import com.example.islam.dmitask.util.Logger;

import java.util.List;

import javax.inject.Inject;


/**
 * Created by islam on 7/25/16.
 */
public class DbManagerImp implements DbManager {
    Context context;
    SQLiteDatabase db;
    DaoMaster.DevOpenHelper helper;
    DaoMaster daoMaster;
    DaoSession daoSession;
    BookDetailDao bookDetailDao;

    @Inject
    public DbManagerImp(Context context) {
        this.context = context;
    }

    private void initializeDB() {
        helper = new DaoMaster.DevOpenHelper(context, "cities-db", null);
        if (db == null) {
            db = helper.getWritableDatabase();
            Logger.i("initializeDB->: db==null");
        } else {
            if (!db.isOpen()) {
                db = helper.getWritableDatabase();
                Logger.i("initializeDB->: db!=null && !db.isOpen()");
            } else {
                Logger.i("initializeDB->: db!=null && db.isOpen()");
            }

        }
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        bookDetailDao = daoSession.getBookDetailDao();

    }

    @Override
    public void newBook(BookDetail bookDetail) {
        try {
            initializeDB();
            bookDetailDao.insert(bookDetail);
        } finally {
            closeDb();
        }
    }


    @Override
    public List<BookDetail> loadAllBooks() {
        List<BookDetail> allBooks = null;
        try {
            initializeDB();
            allBooks = bookDetailDao.loadAll();
        } finally {
            closeDb();
        }
        return allBooks;
    }

    @Override
    public BookDetail loadBookById(String id) {
        BookDetail bookDetail = null;
        try {
            initializeDB();
            bookDetail = bookDetailDao.load(id);
        } finally {
            closeDb();
        }
        return bookDetail;
    }

    @Override
    public void removeBook(BookDetail book) {
        try {
            initializeDB();
            bookDetailDao.delete(book);
        } finally {
            closeDb();
        }
    }

    private void closeDb() {
        db.close();
    }
}
