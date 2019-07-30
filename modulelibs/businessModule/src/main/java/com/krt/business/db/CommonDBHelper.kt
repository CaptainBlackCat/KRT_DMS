package com.krt.business.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.krt.business.db.contract.UserContract

class CommonDBHelper(context: Context, dbName: String, version: Int = 1) :
        SQLiteOpenHelper(context, dbName, null, version) {

    override fun onCreate(db: SQLiteDatabase?) {

        /**
         * 创建  用户登入表
         */
        db?.execSQL(
                "CREATE TABLE if not exists " + UserContract.COLUMN_TABLE_NAME + " ("
                        + UserContract.COLUMN_NAME_COMPANY_ID + TEXT_TYPE + " NOT NULL" + COMMA_SEP
                        + UserContract.COLUMN_NAME_EMP_ID + TEXT_TYPE + " NOT NULL" + COMMA_SEP
                        + UserContract.COLUMN_NAME_DEALER_ID + TEXT_TYPE + " NOT NULL" + COMMA_SEP
                        + UserContract.COLUMN_NAME_USER_CODE + TEXT_TYPE + " NOT NULL" + COMMA_SEP
                        + UserContract.COLUMN_NAME_TOKEN + TEXT_TYPE + " NOT NULL" + COMMA_SEP
                        + UserContract.COLUMN_NAME_HEAD_PORTRAIT + TEXT_TYPE + " NOT NULL" + COMMA_SEP
                        + UserContract.COLUMN_NAME_USERNAME + TEXT_TYPE + " NOT NULL" + COMMA_SEP
                        + UserContract.COLUMN_NAME_DUTY_NAME + TEXT_TYPE + " NOT NULL" + COMMA_SEP
                        + UserContract.COLUMN_NAME_EMP_NAME + TEXT_TYPE + COMMA_SEP
                        + UserContract.COLUMN_NAME_DUTY_NAME_CN + TEXT_TYPE + COMMA_SEP
                        + UserContract.COLUMN_NAME_DUTY_ID + TEXT_TYPE + " NOT NULL"
                        + ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }


    companion object {
        val TEXT_TYPE = " TEXT "
        val BLOB_TYPE = " BLOB "
        val DATETIME_TYPE = " DATETIME "
        val INTEGER_TYPE = " INTEGER "
        val REAL_TYPE = " REAL "    //浮点数 double float
        val COMMA_SEP = ","
    }

}