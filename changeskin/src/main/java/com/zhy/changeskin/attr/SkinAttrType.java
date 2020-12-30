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
        //更换Vector里面的颜色
    IMAGEVIEW_COLOR("ImageViewColor") {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void apply(View view, String resName) {
            Drawable drawable = view.getBackground();
            int color = getResourceManager().getColor(resName);
            if (drawable != null) {
                drawable.setTint(color);
                ((ImageView) view).setImageDrawable(drawable);
                // view.setBackground(drawable);
            }
        }
    }, // android:tag="skin:color_theme:SelectorColor"
    BACKGROUND_D("background_d") {
        @Override
        public void apply(View view, String resName) {
            Drawable drawable = getResourceManager().getDrawableByName(resName);
            if (drawable != null) {
                view.setBackgroundDrawable(drawable);
            }
        }

    },
    // android:tag="skin:color_theme:SelectorColor"
    BACKGROUND_C("background_c") {
        @Override
        public void apply(View view, String resName) {
            try {
                int color = getResourceManager().getColor(resName);
                view.setBackgroundColor(color);
            } catch (Resources.NotFoundException ex) {
                ex.printStackTrace();
            }
        }
    },
    // android:tag="skin:color_theme:SelectorColor"
        //更换SelectorColor里面的字体和背景色
    SELECTOR_COLOR("SelectorColor") {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void apply(View view, String resName) {


            int defaultColor = Color.parseColor("#ffffff");
            //默认的底色是白色
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
    },
    // android:tag="skin:color_theme:SelectortextColor"
        //为false的时候换字体的颜色
    SELECTOR_TEXT_COLOR_N("SelectortextColor") {
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
            int defaultColor = textColors.getDefaultColor();
            if (textColors != null) {
                ColorStateList textColors2 = new ColorStateList(new int[][]{new int[]{i1}, new int[]{i2}}
                        , new int[]{defaultColor, color});
                ((TextView) view).setTextColor(textColors2);
            }
        }
    },
    // android:tag="skin:color_theme:shapeSolidColor"
        //单独更换shapeSolidColor的颜色
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
        //单独更换shape 线框的颜色
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
    // android:tag="skin:color_theme,4:shapeStrokeColorFONT"
        //单独更换shape 线框和字体的颜色
    SHAPE_STROKE_FONTCOLOR("shapeStrokeColorFONT") {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void apply(View view, String resName) {
            GradientDrawable drawable = (GradientDrawable) view.getBackground();
            if (drawable != null) {
                int color = getResourceManager().getColor(resName);
                ((TextView) view).setTextColor(color);
                drawable.setStroke(1, color);
                view.setBackground(drawable);
            }
        }
    },   // android:tag="skin:color_theme:shapeGradientColor"
        //单独更换渐变的颜色
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
    // android:tag="skin:color_theme,4:shapeGradientStrokeColor"
        //单独更换渐变的颜色有边框
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
    // android:tag="skin:color_theme,4:shapeeGradientColorFONT_S"
        //单独更换渐变的边框 和字体颜色
    SHAPE_GEGRADIENT_FONTCOLOR_s("shapeeGradientColorFONT_S") {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void apply(View view, String resName) {
            GradientDrawable drawable = (GradientDrawable) view.getBackground();
            if (drawable != null) {
                int color = getResourceManager().getColor(resName);
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
