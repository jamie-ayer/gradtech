package com.ga.gradtech;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by leisforkokomo on 4/22/16.
 */
public class RVSpaceDecoration extends RecyclerView.ItemDecoration {
    private final int mSpaceBetweenCards;

    public RVSpaceDecoration(int mSpaceBetweenCards) {
        this.mSpaceBetweenCards = mSpaceBetweenCards;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {;
        outRect.left = mSpaceBetweenCards;
        outRect.right = mSpaceBetweenCards;
        outRect.bottom = mSpaceBetweenCards;

        if (parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1){
         outRect.top = mSpaceBetweenCards;
        }
    }
}

