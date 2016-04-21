package com.ga.gradtech.Cards.NotePad;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ga.gradtech.R;

/**
 * Created by samsiu on 4/19/16.
 */
public class NotePadCardViewHolder extends RecyclerView.ViewHolder {

    public TextView mCurrentText;
    public TextView mEditText;
    public Button mSaveButton;
    public Button mEditButton;
    public Button mDeleteButton;

    public NotePadCardViewHolder(View itemView) {
        super(itemView);
        this.mCurrentText = (TextView)itemView.findViewById(R.id.card_notepad_currentText);
        this.mEditText = (TextView)itemView.findViewById(R.id.card_notepad_editText);
        this.mSaveButton = (Button)itemView.findViewById(R.id.card_notepad_saveButton);
        this.mEditButton = (Button)itemView.findViewById(R.id.card_notepad_edit_button);
        this.mDeleteButton = (Button)itemView.findViewById(R.id.card_notepad_clear_button);
    }
}
