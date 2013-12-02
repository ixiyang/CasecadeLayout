package com.example.casecadelayout;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class CasecadeLayout extends ViewGroup {

	private int mHorizontalSpacing;
	private int mVerticalSpacing;

	public CasecadeLayout(Context context) {
		super(context);
	}

	public CasecadeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.CascadeLayout);
		try {
			mHorizontalSpacing = a.getDimensionPixelSize(
					R.styleable.CascadeLayout_horizontal_spacing,
					getResources().getDimensionPixelSize(
							R.dimen.casecade_horizontal_spacing));
			mVerticalSpacing = a.getDimensionPixelSize(
					R.styleable.CascadeLayout_vertical_spacing,
					getResources().getDimensionPixelSize(
							R.dimen.casecade_vertical_spacing));
		} finally {
			a.recycle();
		}

	}

	public CasecadeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// calculate the layout's width
		int width = 0;
		// calculate the layout's height
		int height = getPaddingTop();
		int verticalSpacing;
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			// measure the child
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			LayoutParams lp = (LayoutParams) child.getLayoutParams();
			verticalSpacing=mVerticalSpacing;
			if (lp.verticalSpacing>0) {
				verticalSpacing=lp.verticalSpacing;
			}
			width = getPaddingLeft() + mHorizontalSpacing * i;
			// the x position of child
			lp.x = width;
			// the y position of child
			lp.y = height;

			width += child.getMeasuredWidth();
			height += verticalSpacing;
		}
		// the width of the layout
		width += getPaddingRight();
		// the height of the layout
		height += getChildAt(getChildCount() - 1).getMeasuredHeight()
				+ getPaddingBottom();
		// set the measure result
		setMeasuredDimension(resolveSize(width, widthMeasureSpec),
				resolveSize(height, heightMeasureSpec));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			LayoutParams lp = (LayoutParams) child.getLayoutParams();
			child.layout(lp.x, lp.y, lp.x + child.getMeasuredWidth(), lp.y
					+ child.getMeasuredHeight());
		}

	}

	private class LayoutParams extends ViewGroup.LayoutParams {

		// the x position of child
		private int x;
		// the y position of child
		private int y;
		//the layout_vertical_spacing
		public int verticalSpacing;

		public LayoutParams(Context context, AttributeSet attrs) {
			super(context, attrs);
			TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.CascadeLayout);
			try{
				verticalSpacing=a.getDimensionPixelSize(R.styleable.CascadeLayout_layout_vertical_spacing,-1);
			}finally{
				a.recycle();
			}
		}

		public LayoutParams(int w, int h) {
			super(w, h);
		}

		public LayoutParams(ViewGroup.LayoutParams p) {
			super(p);
		}
	}

	@Override
	protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
		return p instanceof CasecadeLayout.LayoutParams;
	}

	@Override
	protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
	}

	@Override
	public android.view.ViewGroup.LayoutParams generateLayoutParams(
			AttributeSet attrs) {
		return new LayoutParams(getContext(), attrs);
	}

	@Override
	protected android.view.ViewGroup.LayoutParams generateLayoutParams(
			android.view.ViewGroup.LayoutParams p) {
		return new LayoutParams(p);
	}
}
