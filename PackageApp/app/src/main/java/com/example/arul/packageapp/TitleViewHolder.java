package com.example.arul.packageapp;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arul.packageapp.Model.OrderDetailsModel;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;


public class TitleViewHolder extends GroupViewHolder {
    TextView titleName;
    ImageView arrow;

    public TitleViewHolder(View itemView) {
        super(itemView);
        titleName = (TextView) itemView.findViewById(R.id.textview_section_header);
        arrow = (ImageView) itemView.findViewById(R.id.arrow);
    }

    public void setGenreTitle(Context context, ExpandableGroup title) {
        if (title instanceof Title) {
            titleName.setText(((Title) title).getItem());

        }
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        arrow.animate().rotation(360).start();
    }

    private void animateCollapse() {

        arrow.animate().rotation(180).start();
    }


    public static class Title extends ExpandableGroup<OrderDetailsModel> {

        public Title(String title, List<OrderDetailsModel> items) {
            super(title, items);
        }

        public String getItem() {
            return getTitle();
        }
    }

}
