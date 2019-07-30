package com.krt.base.utils

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract


object ContactsUtils {

    fun getPhoneContacts(context: Context?, uri: Uri?): Array<String?>? {
        val contact = arrayOfNulls<String>(2)

        val cr = context?.contentResolver

        val cursor = cr?.query(uri, null, null, null, null)

        if (cursor != null) {
            cursor.moveToFirst()
            val nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            contact[0] = cursor.getString(nameFieldColumnIndex)
            val contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            val phoneCursor = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null
            )
            if (phoneCursor != null) {
                phoneCursor.moveToFirst()

                phoneCursor.getColumnName(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                phoneCursor.count

                try {
                    contact[1] =
                            phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                } catch (e: Exception) {
                    contact[1] = ""
                }
            }
            phoneCursor?.close()
            cursor.close()
        } else {
            return null
        }
        return contact
    }

}