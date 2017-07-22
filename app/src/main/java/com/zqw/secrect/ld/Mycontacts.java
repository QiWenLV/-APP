package com.zqw.secrect.ld;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 启文 on 2017/7/20.
 * 联系人数据
 */
public class Mycontacts {

    /**
     * 获取联系人数据
     * @param context
     * @return
     */
    public static void getContactsJSONString(Context context){
        ContentResolver resolver = context.getContentResolver();

        Log.i("TEST", "11111");
        // 获取手机联系人
        Cursor c = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
//        String phoneNum;
//        Log.i("TEST", "2222");
//        if (c != null) {
//            Log.i("TEST", "3333");
//            while (c.moveToNext()) {
//                phoneNum = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//                Log.i("TEST", ">>>"+phoneNum);
//            }
//            c.close();
//        }

        int contactIdIndex = 0;
        int nameIndex = 0;

        if(c.getCount() > 0) {
            contactIdIndex = c.getColumnIndex(ContactsContract.Contacts._ID);
            nameIndex = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        }
        while(c.moveToNext()) {
            String contactId = c.getString(contactIdIndex);
            String name = c.getString(nameIndex);
            Log.i("TEST", contactId);
            Log.i("TEST", name);

            /*
             * 查找该联系人的phone信息
             */
            Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                    null, null);
            int phoneIndex = 0;
            if (phones.getCount() > 0) {
                phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            }
            while (phones.moveToNext()) {
                String phoneNumber = phones.getString(phoneIndex);
                Log.i("TEST", phoneNumber);
            }

        }// return null;
    }

    public static ArrayList<HashMap<String, String>> readContact(Context context) {
        // 首先,从raw_contacts中读取联系人的id("contact_id")
        // 其次, 根据contact_id从data表中查询出相应的电话号码和联系人名称
        // 然后,根据mimetype来区分哪个是联系人,哪个是电话号码

        Uri rawContactsUri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri = Uri.parse("content://com.android.contacts/data");

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        // 从raw_contacts中读取所有联系人的id("contact_id")
        Cursor rawContactsCursor = context.getContentResolver().query(rawContactsUri,
                new String[] { "contact_id" }, null, null, null);
        if (rawContactsCursor != null) {
            while (rawContactsCursor.moveToNext()) {
                String contactId = rawContactsCursor.getString(0);
                // System.out.println("得到的contact_id="+contactId);

                // 根据contact_id从data表中查询出相应的电话号码和联系人名称, 实际上查询的是视图view_data
                Cursor dataCursor = context.getContentResolver().query(dataUri,
                        new String[] { "data1", "mimetype" }, "contact_id=?",
                        new String[] { contactId }, null);

                if (dataCursor != null) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    while (dataCursor.moveToNext()) {
                        String data1 = dataCursor.getString(0);
                        String mimetype = dataCursor.getString(1);
                        // System.out.println(contactId + ";" + data1 + ";"
                        // + mimetype);
                        if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {//手机号码
                            map.put("phone", data1);
                        } else if ("vnd.android.cursor.item/name".equals(mimetype)) {//联系人名字
                            map.put("name", data1);
                        }
                    }
                    list.add(map);
                    dataCursor.close();

                }
            }
            rawContactsCursor.close();
        }
        Log.i("TEST", "222");
        Log.i("TEST", list.toString());
        return list;
    }


}
