package com.shopping_point.vendor_shopping_point.utils;


import com.shopping_point.vendor_shopping_point.R;

import java.util.ArrayList;

public class Slide {

    private static final ArrayList<Integer> slides = new ArrayList<Integer>() {{
        add(R.drawable.slide1);
        add(R.drawable.slide2);
        add(R.drawable.slide3);
        add(R.drawable.slide4);
        add(R.drawable.slide5);
        add(R.drawable.slide6);
        add(R.drawable.slide7);
    }};

    public static ArrayList<Integer> getSlides() {
        return slides;
    }
}
