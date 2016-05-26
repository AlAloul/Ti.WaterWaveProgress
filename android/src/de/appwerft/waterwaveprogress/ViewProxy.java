/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package de.appwerft.waterwaveprogress;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;
import org.appcelerator.titanium.TiApplication;

import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.app.Activity;
import cn.modificator.waterwave_progress.*;

@Kroll.proxy(creatableInModule = WaterwaveprogressModule.class)
public class ViewProxy extends TiViewProxy {
	// Standard Debugging variables
	TiApplication appContext;
	Activity activity;
	private static final String LCAT = ViewProxy.class.getSimpleName();

	public WaterWaveProgress mWaterWaveProgressView;
	/* all default attributes: */
	private int mRingColor, mRingBgColor, mWaterColor, mWaterBgColor,
			mFontSize, mTextColor;
	float mCrestCount = 1.5f;
	int mProgress = 10, mMaxProgress = 100;
	private float mRingWidth, mRing2WaterWidth;
	private boolean mShowNumerical = true, mShowRing = true;
	private int mWaveFactor = 0;
	private boolean mIsWaving = false;
	private float mAmplitude = 30.0F; // 20F
	private float mWaveSpeed = 0.070F; // 0.020F
	private int mWaterAlpha = 255; // 255
	progressView view;

	// Constructor of viewproxy class
	public ViewProxy() {
		super();
		appContext = TiApplication.getInstance();
		activity = appContext.getCurrentActivity();
	}

	@Override
	public TiUIView createView(Activity activity) {
		TiUIView view = new progressView(this);
		view.getLayoutParams().autoFillsHeight = true;
		view.getLayoutParams().autoFillsWidth = true;

		return view;
	}

	@Override
	public void handleCreationDict(KrollDict options) {
		super.handleCreationDict(options);
		Log.d(LCAT, "start ViewProxy::handleCreationDict");
		if (options.containsKeyAndNotNull("progress")) {
			mProgress = TiConvert.toInt(options, "progress");
		}
		if (options.containsKeyAndNotNull("maxProgress")) {
			mMaxProgress = TiConvert.toInt(options, "maxProgress");
		}
		if (options.containsKeyAndNotNull("ringWidth")) {
			mRingWidth = TiConvert.toFloat(options, "ringWidth");
		}
		if (options.containsKeyAndNotNull("fontSize")) {
			mFontSize = TiConvert.toInt(options, "fontSize");
		}
		if (options.containsKeyAndNotNull("ring2WaterWidth")) {
			mRing2WaterWidth = TiConvert.toFloat(options, "ring2WaterWidth");
		}
		if (options.containsKeyAndNotNull("ringColor")) {
			mRingColor = TiConvert.toColor(options, "ringColor");
		}
		if (options.containsKeyAndNotNull("ringBgColor")) {
			mRingBgColor = TiConvert.toColor(options, "ringBgColor");
		}
		if (options.containsKeyAndNotNull("waterColor")) {
			mWaterColor = TiConvert.toColor(options, "waterColor");
		}
		if (options.containsKeyAndNotNull("waterBgColor")) {
			mWaterBgColor = TiConvert.toColor(options, "waterBgColor");
		}
		if (options.containsKeyAndNotNull("textColor")) {
			mTextColor = TiConvert.toColor(options, "textColor");
		}
		if (options.containsKeyAndNotNull("showNumerical")) {
			mShowNumerical = TiConvert.toBoolean(options, "showNumerical");
		}
		if (options.containsKeyAndNotNull("showRing")) {
			mShowRing = TiConvert.toBoolean(options, "showRing");
		}
		if (options.containsKeyAndNotNull("crestCount")) {
			mCrestCount = TiConvert.toFloat(options, "crestCount");
		}
		if (options.containsKeyAndNotNull("amplitude")) {
			mAmplitude = TiConvert.toFloat(options, "amplitude");
		}
		if (options.containsKeyAndNotNull("α")) {
			mWaterAlpha = TiConvert.toInt(options, "α");
		}
		if (options.containsKeyAndNotNull("waveFactor")) {
			mWaveFactor = TiConvert.toInt(options, "waveFactor");
		}
		Log.d(LCAT, "ViewProxy::handleCreationDict finished ");
	}

	public class progressView extends TiUIView {
		public progressView(TiViewProxy proxy) {
			super(proxy);
			Log.d(LCAT, "progressView started");
			LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			LinearLayout container = new LinearLayout(proxy.getActivity());
			container.setLayoutParams(lp);

			mWaterWaveProgressView = new WaterWaveProgress(TiApplication
					.getInstance().getApplicationContext());

			mWaterWaveProgressView.setAmplitude(mAmplitude);
			mWaterWaveProgressView.setCrestCount(mCrestCount);
			mWaterWaveProgressView.setFontSize(mFontSize);
			mWaterWaveProgressView.setIsWaving(mIsWaving);
			mWaterWaveProgressView.setProgress(mProgress);
			mWaterWaveProgressView.setMaxProgress(mMaxProgress);
			mWaterWaveProgressView.setRingWidth(mRingWidth);
			mWaterWaveProgressView.setShowRing(mShowRing);
			mWaterWaveProgressView.setShowNumerical(mShowNumerical);
			mWaterWaveProgressView.setWaterColor(mWaterColor);
			mWaterWaveProgressView.setWaterBgColor(mWaterBgColor);
			mWaterWaveProgressView.setRingColor(mRingColor);
			mWaterWaveProgressView.setRingBgColor(mRingBgColor);
			mWaterWaveProgressView.setTextColor(mTextColor);
			mWaterWaveProgressView.setRingWidth(mRingWidth);
			mWaterWaveProgressView.setRing2WaterWidth(mRing2WaterWidth);
			mWaterWaveProgressView.setWaveFactor(mWaveFactor);
			mWaterWaveProgressView.setWaveSpeed(mWaveSpeed);
			mWaterWaveProgressView.setWaterAlpha(mWaterAlpha);
			container.addView(mWaterWaveProgressView);
			setNativeView(container);
			if (proxy.hasListeners("viewCreated")) {
				fireEvent("viewCreated", null);
			}
		}

		@Override
		public void processProperties(KrollDict d) {
			Log.d(LCAT, "processProperties triggered");
			super.processProperties(d);
		}
	}

	/* I N T E R F A C E S to Titanium */
	@Kroll.method
	public void hideNumerical() {
		if (mWaterWaveProgressView != null)
			mWaterWaveProgressView.setShowNumerical(false);
	}

	@Kroll.method
	public void showNumerical() {
		if (mWaterWaveProgressView != null)
			mWaterWaveProgressView.setShowNumerical(true);
	}

	@Kroll.method
	public void hideRing() {
		if (mWaterWaveProgressView != null)
			mWaterWaveProgressView.setShowRing(false);
	}

	@Kroll.method
	public void showRing() {
		if (mWaterWaveProgressView != null)
			mWaterWaveProgressView.setShowRing(true);
	}

	@Kroll.method
	public void setCrestCount(int arg) {
		if (mWaterWaveProgressView != null)
			mWaterWaveProgressView.setCrestCount(TiConvert.toInt(arg));
	}

	@Kroll.method
	public void setProgress(int arg) {
		Log.d(LCAT, "mProgress=" + arg);
		if (mWaterWaveProgressView != null)
			mWaterWaveProgressView.setProgress(TiConvert.toInt(arg));
	}

	@Kroll.method
	public void setRingWidth(float arg) {if (mWaterWaveProgressView != null)
		mWaterWaveProgressView.setRingWidth(TiConvert.toFloat(arg));
	}

	@Kroll.method
	public void setCrestCount(float arg) {if (mWaterWaveProgressView != null)
		mWaterWaveProgressView.setCrestCount(TiConvert.toFloat(arg));
	}

	@Kroll.method
	public void setAmplitude(float arg) {if (mWaterWaveProgressView != null)
		mWaterWaveProgressView.setAmplitude(TiConvert.toFloat(arg));
	}

	@Kroll.method
	public void setWaveSpeed(float arg) {if (mWaterWaveProgressView != null)
		mWaterWaveProgressView.setWaveSpeed(TiConvert.toFloat(arg));
	}

	@Kroll.method
	public void setWaterAlpha(float arg) {if (mWaterWaveProgressView != null)
		mWaterWaveProgressView.setWaterAlpha(TiConvert.toFloat(arg));
	}

}