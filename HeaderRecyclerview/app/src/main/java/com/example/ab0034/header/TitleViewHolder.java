package com.example.ab0034.header;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ColorSpace;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

public class TitleViewHolder extends GroupViewHolder {
    TextView titleName;
    ImageView arrow;


    public TitleViewHolder(View view) {
        super(view);
        titleName = (TextView) itemView.findViewById(R.id.textview_section_header);
        arrow = (ImageView) itemView.findViewById(R.id.arrow);

    }

    public void expand() {
        arrow.animate().scaleX(0.5f);
    }

    public void collapse() {
        arrow.animate().scaleX(0f);
    }

    public void setGenreTitle(Context context, ExpandableGroup title) {
        if (title instanceof Title) {
            titleName.setText(((Title) title).getItem());

        }
    }

    static class Title extends ExpandableGroup<HeaderDto> {
        public Title(String title, List<HeaderDto> items) {
            super(title, items);
        }

        public String getItem() {
            return getTitle();
        }
    }


}
