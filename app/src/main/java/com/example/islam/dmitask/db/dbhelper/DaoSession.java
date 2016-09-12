package com.example.islam.dmitask.db.dbhelper;

import android.database.sqlite.SQLiteDatabase;

import com.example.islam.dmitask.db.dao.BookDetailDao;
import com.example.islam.dmitask.db.dto.BookDetail;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 *
 * @see AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bookDetailDaoConfig;

    private final BookDetailDao bookDetailDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bookDetailDaoConfig = daoConfigMap.get(BookDetailDao.class).clone();
        bookDetailDaoConfig.initIdentityScope(type);

        bookDetailDao = new BookDetailDao(bookDetailDaoConfig, this);

        registerDao(BookDetail.class, bookDetailDao);
    }

    public void clear() {
        bookDetailDaoConfig.getIdentityScope().clear();
    }

    public BookDetailDao getBookDetailDao() {
        return bookDetailDao;
    }

}