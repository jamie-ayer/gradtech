package com.ga.gradtech.Cards.NotePad;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.View;

/**
 * Created by samsiu on 4/20/16.
 */
public class NotepadViewHolderConfigurer {
    private static final String TAG = NotepadViewHolderConfigurer.class.getCanonicalName();

    NotePadCardViewHolder vh6;
    Activity mainActivity;

    public NotepadViewHolderConfigurer(NotePadCardViewHolder vh6, Activity mainActivity) {
        this.vh6 = vh6;
        this.mainActivity = mainActivity;
    }

    public void initNotePad(){
        setNotePadEditButtonListener();
        setNotePadSaveButtonListener();
        setNotePadClearButtonListener();
        getNotePadeDescription();
    }

    public void getNotePadeDescription(){
        NotepadSQLiteHelper db = new NotepadSQLiteHelper(mainActivity);
        Cursor cursor = NotepadSQLiteHelper.getInstance(mainActivity).getNotepadItem();
        cursor.moveToFirst();
        if(cursor.getCount() == 0){
            Log.d(TAG, "initNotePad: TABLE IS EMPTY <<<<<=========");
            String initText = "My Notes";
            db.insertNotepadItem(1, "FirstNote", initText);
            vh6.mEditText.setText(initText);
        }else{
            String description = cursor.getString(cursor.getColumnIndex(NotepadSQLiteHelper.COL_NOTEPAD_DESCRIPTION));
            Log.d(TAG, String.format("onCreate: description: %s ", description));
            vh6.mCurrentText.setText(description);
        }
        cursor.close();
    }

    public void setNotePadEditButtonListener(){
        vh6.mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotepadSQLiteHelper db = new NotepadSQLiteHelper(mainActivity);
                int editTextVisibility = vh6.mEditText.getVisibility();
                Log.d(TAG, "onClick: ==>>> Visibility " + editTextVisibility);

                if (editTextVisibility == 4) { // INVISIBLE
                    //String curText = currentText.getText().toString();
                    Cursor cursor = NotepadSQLiteHelper.getInstance(mainActivity).getNotepadItem();
                    cursor.moveToFirst();
                    String description = cursor.getString(cursor.getColumnIndex(NotepadSQLiteHelper.COL_NOTEPAD_DESCRIPTION));

                    Log.d(TAG, "onClick: Edit Clicked ===>>> INVISIBLE 4 - Bring to visible " + description);
                    vh6.mEditText.setText(description);
                    cursor.close();
                    db.close();
                } else if (editTextVisibility == 0) { // VISIBLE
                    vh6.mEditText.setText(vh6.mEditText.getText().toString());
                    Log.d(TAG, "onClick: =====>>>>>  VISIBLE 0");
                }

                vh6.mCurrentText.setVisibility(View.INVISIBLE);
                vh6.mEditText.setVisibility(View.VISIBLE);
            }
        });

    }

    public void setNotePadSaveButtonListener() {
        vh6.mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotepadSQLiteHelper db = new NotepadSQLiteHelper(mainActivity);
                String edText = vh6.mEditText.getText().toString();
                Log.d(TAG, "onClick: ===>>>> SaveButton " + edText);
                vh6.mCurrentText.setText(edText);
                db.updateNotepadeItem(1, "FirstNote", edText);
                vh6.mCurrentText.setVisibility(View.VISIBLE);
                vh6.mEditText.setVisibility(View.INVISIBLE);
                db.close();
            }
        });
    }

    public void setNotePadClearButtonListener() {
        vh6.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NotepadSQLiteHelper db = new NotepadSQLiteHelper(mainActivity);
                String edText = "";
                Log.d(TAG, "onClick: ===>>>> ClearButton " + edText);
                vh6.mEditText.setText(edText);
//                db.updateNotepadeItem(1, "FirstNote", edText);
                vh6.mCurrentText.setVisibility(View.INVISIBLE);
                vh6.mEditText.setVisibility(View.VISIBLE);
//                db.close();
            }
        });
    }
}
