package com.ga.gradtech.Cards.NotePad;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.View;

import com.ga.gradtech.Cards.Facebook.FacebookCardViewHolder;

/**
 * Created by samsiu on 4/20/16.
 */
public class NotepadViewHolderConfigurer {
    private static final String TAG = NotepadViewHolderConfigurer.class.getCanonicalName();

    NotePadCardViewHolder vh9;
    int position;
    Activity mainActivity;

    public NotepadViewHolderConfigurer(NotePadCardViewHolder vh9, int position, Activity mainActivity) {
        this.vh9 = vh9;
        this.position = position;
        this.mainActivity = mainActivity;
    }

    public void initNotePad(){
        NotepadSQLiteHelper db = new NotepadSQLiteHelper(mainActivity);
        Cursor cursor = NotepadSQLiteHelper.getInstance(mainActivity).getNotepadItem();
        cursor.moveToFirst();
        if(cursor.getCount() == 0){
            Log.d(TAG, "initNotePad: TABLE IS EMPTY <<<<<=========");
            String initText = "My Notes";
            db.insertNotepadItem(1, "FirstNote", initText);
            vh9.mEditText.setText(initText);
        }else{
            String description = cursor.getString(cursor.getColumnIndex(NotepadSQLiteHelper.COL_NOTEPAD_DESCRIPTION));
            Log.d(TAG, String.format("onCreate: description: %s ", description));
            vh9.mCurrentText.setText(description);
        }
        cursor.close();
    }

    public void setNotePadEditButtonListener(){
        vh9.mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotepadSQLiteHelper db = new NotepadSQLiteHelper(mainActivity);
                int editTextVisibility = vh9.mEditText.getVisibility();
                Log.d(TAG, "onClick: ==>>> Visibility " + editTextVisibility);

                if (editTextVisibility == 4) { // INVISIBLE
                    //String curText = currentText.getText().toString();
                    Cursor cursor = NotepadSQLiteHelper.getInstance(mainActivity).getNotepadItem();
                    cursor.moveToFirst();
                    String description = cursor.getString(cursor.getColumnIndex(NotepadSQLiteHelper.COL_NOTEPAD_DESCRIPTION));

                    Log.d(TAG, "onClick: Edit Clicked ===>>> INVISIBLE 4 - Bring to visible " + description);
                    vh9.mEditText.setText(description);
                    cursor.close();
                    db.close();
                } else if (editTextVisibility == 0) { // VISIBLE
                    vh9.mEditText.setText(vh9.mEditText.getText().toString());
                    Log.d(TAG, "onClick: =====>>>>>  VISIBLE 0");
                }

                vh9.mCurrentText.setVisibility(View.INVISIBLE);
                vh9.mEditText.setVisibility(View.VISIBLE);
            }
        });

    }

    public void setNotePadSaveButtonListener() {
        vh9.mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotepadSQLiteHelper db = new NotepadSQLiteHelper(mainActivity);
                String edText = vh9.mEditText.getText().toString();
                Log.d(TAG, "onClick: ===>>>> SaveButton " + edText);
                vh9.mCurrentText.setText(edText);
                db.updateNotepadeItem(1, "FirstNote", edText);
                vh9.mCurrentText.setVisibility(View.VISIBLE);
                vh9.mEditText.setVisibility(View.INVISIBLE);
                db.close();
            }
        });
    }

    public void setNotePadClearButtonListener() {
        vh9.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NotepadSQLiteHelper db = new NotepadSQLiteHelper(mainActivity);
                String edText = "";
                Log.d(TAG, "onClick: ===>>>> ClearButton " + edText);
                vh9.mEditText.setText(edText);
//                db.updateNotepadeItem(1, "FirstNote", edText);
//                vh9.mCurrentText.setVisibility(View.VISIBLE);
//                vh9.mEditText.setVisibility(View.INVISIBLE);
//                db.close();
            }
        });
    }
}
