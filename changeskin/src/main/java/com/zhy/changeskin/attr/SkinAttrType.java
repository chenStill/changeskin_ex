package com.zhy.changeskin.attr;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.UserManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.zhy.changeskin.R;
import com.zhy.changeskin.ResourceManager;
import com.zhy.changeskin.SkinManager;

import java.util.logging.Logger;


/**
 * Created by zhy on 15/9/28.
 */
public enum SkinAttrType {
    BACKGROUND("background") {
        @Override
        public void apply(View view, String resName) {
            Drawable drawable = getResourceManager().getDrawableByName(resName);
            if (drawable != null) {
                view.setBackgroundDrawable(drawable);
            } else {
                try {
                    int color = getResourceManager().getColor(resName);
                    view.setBackgroundColor(color);
                } catch (Resources.NotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }, COLOR("textColor") {
        @Override
        public void apply(View view, String resName) {
            ColorStateList colorList = getResourceManager().getColorStateList(resName);
            if (colorList == null) return;
            ((TextView) view).setTextColor(colorList);
        }
    }, SRC("src") {
        @Override
        public void apply(View view, String resName) {
            if (view instanceof ImageView) {
                Drawable drawable = getResourceManager().getDrawableByName(resName);
                if (drawable == null) return;
                ((ImageView) view).setImageDrawable(drawable);
            }

        }
    }, DIVIDER("divider") {
        @Override
        public void apply(View view, String resName) {
            if (view instanceof ListView) {
                Drawable divider = getResourceManager().getDrawableByName(resName);
                if (divider == null) return;
                ((ListView) view).setDivider(divider);
            }
        }
    }, // android:tag="skin:color_theme:ImageViewColor"
    IMAGEVIEW_COLOR("ImageViewColor") {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void apply(View view, String resName) {
            Drawable drawable = view.getBackground();
            // int color = getResourceManager().getColor(resName);
            int color = getResourceManager().getColor(resName);
            if (drawable != null) {
                //drawable.setTint(Color.parseColor("#FFEB3B"));
                drawable.setTint(color);
                //   view.setImageDrawable(drawable);
                ((ImageView) view).setImageDrawable(drawable);
                // view.setBackground(drawable);
            }
        }
    },
    // android:tag="skin:color_theme:SelectorColor"
    SELECTOR_COLOR_S("SelectorColor_s") {
        //为true的时候更换Shpae里面的
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void apply(View view, String resName) {


            int defaultColor = Color.parseColor("#ffffff");
            //int changeColor = getResourceManager().getColor(resName + "_set_2");
            int changeColor = getResourceManager().getColor(resName + "_set_1");
            ColorStateList textColors = ((TextView) view).getTextColors();
            String s = textColors.toString();
            int beginIndex = s.indexOf("mStateSpecs=[[");
            String s1 = s.substring(beginIndex + 14, s.indexOf("], ["));
            int beginIndex1 = s.indexOf("], [");
            String s2 = s.substring(beginIndex1 + 4, s.indexOf("]]mColors"));
            int i1 = -android.R.attr.state_selected;
            //默认
            int i2 = android.R.attr.state_selected;
            try {
                i1 = Integer.parseInt(s1);
                i2 = Integer.parseInt(s2);
            } catch (Exception e) {
            }

            Log.d("textColors", s);
            Log.d("textColors", s1 + " " + i1);
            Log.d("textColors", s2 + " " + i2);
            if (textColors != null) {
                ColorStateList textColors2 = new ColorStateList(new int[][]{new int[]{i1}, new int[]{i2}}
                        , new int[]{changeColor, defaultColor});
                ((TextView) view).setTextColor(textColors2);
            }

            StateListDrawable stateListDrawable = (StateListDrawable) view.getBackground();
            if (stateListDrawable != null) {
                StateListDrawable stateListDrawable2 = new StateListDrawable();
                for (int i = 0; i < stateListDrawable.getStateCount(); i++) {
                    GradientDrawable drawable1 = (GradientDrawable) stateListDrawable.getStateDrawable(i);
                    if (i == 0) {
                        drawable1.setStroke(2, changeColor);
                        drawable1.setColor(defaultColor);
                    } else {
                        drawable1.setColor(changeColor);
                        drawable1.setStroke(0, null);
                    }
                    int[] stateSet = stateListDrawable.getStateSet(i);
                    stateListDrawable2.addState(stateSet, drawable1);
                }
                view.setBackgroundDrawable(stateListDrawable2);
            }
        }
    },    // android:tag="skin:color_theme:SelectorColor"  // android:tag="skin:color_theme:SelectorColor"
    SELECTOR_COLOR_S1("SelectorColor_s1") {
        //为true的时候更换Shpae里面的
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void apply(View view, String resName) {


            int color = getResourceManager().getColor(resName + "_set_2");
            ColorStateList textColors = ((TextView) view).getTextColors();
            String s = textColors.toString();
            int beginIndex = s.indexOf("mStateSpecs=[[");
            String s1 = s.substring(beginIndex + 14, s.indexOf("], ["));
            int beginIndex1 = s.indexOf("], [");
            String s2 = s.substring(beginIndex1 + 4, s.indexOf("]]mColors"));
            int i1 = -android.R.attr.state_selected;
            //默认
            int i2 = android.R.attr.state_selected;

            try {
                i1 = Integer.parseInt(s1);
                i2 = Integer.parseInt(s2);
            } catch (Exception e) {
            }

            Log.d("textColors", s);
            Log.d("textColors", s1 + " " + i1);
            Log.d("textColors", s2 + " " + i2);
           // int defaultColor = textColors.getDefaultColor();
            int defaultColor = Color.parseColor("#ffffff");
            if (textColors != null) {
                ColorStateList textColors2 = new ColorStateList(new int[][]{new int[]{i1}, new int[]{i2}}
                        , new int[]{color, defaultColor});

                ((TextView) view).setTextColor(textColors2);
            }


            int color2 = getResourceManager().getColor(resName + "_set_2");
            StateListDrawable stateListDrawable = (StateListDrawable) view.getBackground();
            if (stateListDrawable != null) {
                StateListDrawable stateListDrawable2 = new StateListDrawable();
                for (int i = 0; i < stateListDrawable.getStateCount(); i++) {
                    GradientDrawable drawable1 = (GradientDrawable) stateListDrawable.getStateDrawable(i);
                    if (i == 0) {
                        drawable1.setStroke(2, color2);
                        drawable1.setColor(Color.parseColor("#ffffff"));
                       // ((TextView) view).setTextColor(color2);
                    } else {
                        drawable1.setColor(color2);
                        drawable1.setStroke(0, null);
                       // ((TextView) view).setTextColor(Color.parseColor("#FFFFFF"));
                    }
                    int[] stateSet = stateListDrawable.getStateSet(i);
                    stateListDrawable2.addState(stateSet, drawable1);
                }
                view.setBackgroundDrawable(stateListDrawable2);
            }
        }
    },    // android:tag="skin:color_theme:SelectorColor"
    // android:tag="skin:color_theme:SelectortextColor"
    SELECTOR_TEXT_COLOR_N("SelectortextColor") {
        //为false的时候换肤
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void apply(View view, String resName) {
            int color = getResourceManager().getColor(resName + "_set_2");
            ColorStateList textColors = ((TextView) view).getTextColors();
            String s = textColors.toString();
            //ColorStateList{mStateSpecs=[[16842913], [-16842913]]mColors=[-16777216, -16524603]mDefaultColor=-16777216}
            //b  b ColorStateList{mStateSpecs=[[-16842919], [16842919]]mColors=[-16524603, -16777216]mDefaultColor=-16524603}
            int beginIndex = s.indexOf("mStateSpecs=[[");
            String s1 = s.substring(beginIndex + 14, s.indexOf("], ["));
            int beginIndex1 = s.indexOf("], [");
            String s2 = s.substring(beginIndex1 + 4, s.indexOf("]]mColors"));
            int i1 = -android.R.attr.state_selected;
            //默认
            int i2 = android.R.attr.state_selected;

            try {
                i1 = Integer.parseInt(s1);
                i2 = Integer.parseInt(s2);
            } catch (Exception e) {
            }

            Log.d("textColors", s);
            Log.d("textColors", s1 + " " + i1);
            Log.d("textColors", s2 + " " + i2);
            int defaultColor = textColors.getDefaultColor();
            if (textColors != null) {
                ColorStateList textColors2 = new ColorStateList(new int[][]{new int[]{i1}, new int[]{i2}}
                        , new int[]{defaultColor, color});

                ((TextView) view).setTextColor(textColors2);
            }
        }
    },
    // android:tag="skin:color_theme:shapeSolidColor"
    SHAPE_SOLID_COLOR("shapeSolidColor") {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void apply(View view, String resName) {

            GradientDrawable drawable = (GradientDrawable) view.getBackground();
            if (drawable != null) {
                int color = getResourceManager().getColor(resName);
                drawable.setColor(color);
                view.setBackground(drawable);
            }
        }
    },
    // android:tag="skin:color_theme,4:shapeStrokeColor"
    SHAPE_STROKE_COLOR("shapeStrokeColor") {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void apply(View view, String resName) {
            GradientDrawable drawable = (GradientDrawable) view.getBackground();
            if (drawable != null) {
                int color = getResourceManager().getColor(resName);
                view.setBackgroundColor(color);
                drawable.setStroke(1, color);
                view.setBackground(drawable);
            }
        }
    },
    // android:tag="skin:color_theme,4:shapeStrokeColor"
    SHAPE_STROKE_FONTCOLOR_s("shapeStrokeColorFONT_S") {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void apply(View view, String resName) {
            GradientDrawable drawable = (GradientDrawable) view.getBackground();
            if (drawable != null) {
                int color = getResourceManager().getColor(resName);
                // view.setBackgroundColor(color);
                ((TextView) view).setTextColor(color);
                drawable.setStroke(1, color);
                view.setBackground(drawable);
            }
        }
    },  // android:tag="skin:color_theme,4:shapeStrokeColor"
    SHAPE_STROKE_FONTCOLOR_N("shapeStrokeColorFONT_N") {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void apply(View view, String resName) {
            GradientDrawable drawable = (GradientDrawable) view.getBackground();
            if (drawable != null) {
                int color1 = Color.parseColor("#FFFFFFFF");
                int color = getResourceManager().getColor(resName);
                // view.setBackgroundColor(color1);
                drawable.setColor(color);
                ((TextView) view).setTextColor(color1);
                drawable.setStroke(2, color1);
                view.setBackground(drawable);
            }
        }
    },   // android:tag="skin:color_theme:shapeSolidColor"
    SHAPE_GRADIENT_COLOR("shapeGradientColor") {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void apply(View view, String resName) {
            int color = getResourceManager().getColor(resName + "_str");
            int color2 = getResourceManager().getColor(resName + "_end");
            GradientDrawable drawable = (GradientDrawable) view.getBackground();
            if (drawable != null) {
                int colors[] = {color, color2};
                //int colors[]={Color.parseColor("#FAD064"),Color.parseColor("#03DAC5")};
                drawable.setColors(colors);
                view.setBackground(drawable);
            }
        }
    },
    // android:tag="skin:color_theme,4:shapeStrokeColor"
    SHAPE_GRADIENT_STROKE_COLOR("shapeGradientStrokeColor") {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void apply(View view, String resName) {
            GradientDrawable drawable = (GradientDrawable) view.getBackground();
            if (drawable != null) {
                int color = getResourceManager().getColor(resName);
                view.setBackgroundColor(color);
                drawable.setStroke(1, color);
                view.setBackground(drawable);
            }
        }
    },
    // android:tag="skin:color_theme,4:shapeStrokeColor"
    SHAPE_GEGRADIENT_FONTCOLOR_s("shapeeGradientColorFONT_S") {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void apply(View view, String resName) {
            GradientDrawable drawable = (GradientDrawable) view.getBackground();
            if (drawable != null) {
                int color = getResourceManager().getColor(resName);
                // view.setBackgroundColor(color);
                ((TextView) view).setTextColor(color);
                drawable.setStroke(1, color);
                view.setBackground(drawable);
            }
        }
    };

    String attrType;

    SkinAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getAttrType() {
        return attrType;
    }


    public abstract void apply(View view, String resName);

    public ResourceManager getResourceManager() {
        return SkinManager.getInstance().getResourceManager();
    }

}
