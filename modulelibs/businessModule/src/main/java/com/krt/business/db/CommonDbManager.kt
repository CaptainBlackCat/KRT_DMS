package com.krt.business.db

import android.content.ContentValues
import com.krt.business.db.contract.UserContract
import com.krt.frame.app.config.KRT

object CommonDbManager {

    private var mDBHelper: CommonDBHelper

    init {
//        val name = Environment.getExternalStorageDirectory().absolutePath + "/krt.db"

        val name = "krt.db"
        mDBHelper = CommonDBHelper(KRT.getApplicationContext(), name)
    }

    fun getUser(): HashMap<String, String> {
        val db = mDBHelper.writableDatabase

        val cursor = db.query(
                UserContract.COLUMN_TABLE_NAME, null,
                null, null, null, null, null
        )

        val hashMap = HashMap<String, String>()

        while (cursor.moveToNext()) {
            hashMap[UserContract.COLUMN_NAME_COMPANY_ID] =
                    cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_NAME_COMPANY_ID))
            hashMap[UserContract.COLUMN_NAME_EMP_ID] =
                    cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_NAME_EMP_ID))
            hashMap[UserContract.COLUMN_NAME_DEALER_ID] =
                    cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_NAME_DEALER_ID))
            hashMap[UserContract.COLUMN_NAME_USER_CODE] =
                    cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_NAME_USER_CODE))
            hashMap[UserContract.COLUMN_NAME_TOKEN] =
                    cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_NAME_TOKEN))
            hashMap[UserContract.COLUMN_NAME_HEAD_PORTRAIT] =
                    cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_NAME_HEAD_PORTRAIT))
            hashMap[UserContract.COLUMN_NAME_USERNAME] =
                    cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_NAME_USERNAME))
            hashMap[UserContract.COLUMN_NAME_DUTY_NAME] =
                    cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_NAME_DUTY_NAME))
            hashMap[UserContract.COLUMN_NAME_EMP_NAME] =
                    cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_NAME_EMP_NAME))
            hashMap[UserContract.COLUMN_NAME_DUTY_NAME_CN] =
                    cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_NAME_DUTY_NAME_CN))
            hashMap[UserContract.COLUMN_NAME_DUTY_ID] =
                    cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_NAME_DUTY_ID))
        }

        cursor.close()
        db.close()

        return hashMap
    }

    /**
     * 清除用户
     */
    fun clearUser() {
        val db = mDBHelper.writableDatabase
        db.execSQL("delete from " + UserContract.COLUMN_TABLE_NAME)
        db.close()
    }

    /**
     * 保存用户
     */
    fun saveUser(
            companyId: String,
            empId: String,
            dealerId: String,
            userCode: String,
            token: String,
            headPortrait: String,
            userName: String,
            dutyName: String,
            empName: String?,
            dutyNameCN: String?,
            dutyId: Int
    ): Boolean {
        val db = mDBHelper.writableDatabase

        db.beginTransaction()

        //先清空 用户 数据
        db.execSQL("delete from " + UserContract.COLUMN_TABLE_NAME)

        //再保存用户数据
        val values = ContentValues()
        values.put(UserContract.COLUMN_NAME_COMPANY_ID, companyId)
        values.put(UserContract.COLUMN_NAME_EMP_ID, empId)
        values.put(UserContract.COLUMN_NAME_DEALER_ID, dealerId)
        values.put(UserContract.COLUMN_NAME_USER_CODE, userCode)
        values.put(UserContract.COLUMN_NAME_TOKEN, token)
        values.put(UserContract.COLUMN_NAME_HEAD_PORTRAIT, headPortrait)
        values.put(UserContract.COLUMN_NAME_USERNAME, userName)
        values.put(UserContract.COLUMN_NAME_DUTY_NAME, dutyName)
        values.put(UserContract.COLUMN_NAME_EMP_NAME, empName)
        values.put(UserContract.COLUMN_NAME_DUTY_NAME_CN, dutyNameCN)
        values.put(UserContract.COLUMN_NAME_DUTY_ID, dutyId)

        val result = db.insert(UserContract.COLUMN_TABLE_NAME, null, values)

        db.setTransactionSuccessful()
        db.endTransaction()
        db.close()

        return result > 0
    }
}