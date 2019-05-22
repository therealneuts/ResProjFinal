package com.acote.mastermind;

import android.app.SharedElementCallback;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;
import android.widget.ImageView;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class MastermindPeg {
    public static LinkedList<Color> COLORS = new LinkedList<>();

    static {
        COLORS.add(Color.valueOf(0xFFFF0000));
        COLORS.add(Color.valueOf(0xFF00FF00));
        COLORS.add(Color.valueOf(0xFF0000FF));
        COLORS.add(Color.valueOf(0xFFFFFF00));
        COLORS.add(Color.valueOf(0xFFFF00FF));
        COLORS.add(Color.valueOf(0xFF00FFFF));
        COLORS.add(Color.valueOf(0x00000000));
    }

    private GradientDrawable drawable;
    private ImageView container;
    private ListIterator<Color> iterator = COLORS.listIterator(COLORS.size() -1);
    private int index = COLORS.size() -1;

    public MastermindPeg(View container) {

        if (container instanceof  ImageView) {
            this.container = (ImageView)container;
        }
        else {
            throw new ClassCastException("Failed to cast to ShapeDrawable: " + this.container.getDrawable().getClass().toString());
        }

        if (this.container.getDrawable() instanceof GradientDrawable) {
            this.drawable = (GradientDrawable)this.container.getDrawable();
        }
        else {
            throw new ClassCastException("Failed to cast to ShapeDrawable: " + this.container.getDrawable().getClass().toString());
        }

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cycleColor();
            }
        });
    }

    private void cycleColor() {
        if (iterator.hasNext()){
            index = iterator.nextIndex();
            drawable.setColor(iterator.next().toArgb());
        }
        else {
            index = 0;
            iterator = COLORS.listIterator();
            drawable.setColor(iterator.next().toArgb());
        }
    }

    public void setColor(int pos){
        index = pos;
        drawable.setColor(COLORS.get(pos).toArgb());
    }

    public int getColorPosition() {
        return index;
    }
}
