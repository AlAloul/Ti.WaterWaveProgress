package cn.modificator.waterwave_progress;

import java.lang.ref.WeakReference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Path.Direction;
import android.graphics.Region.Op;
import android.os.Handler;
import android.os.Message;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import org.appcelerator.kroll.common.Log;

/**
 * @author Administrator
 * 
 */
public class WaterWaveProgress extends View {

	private static final String LCAT = "WaterView";
	// 水的画笔 // 画圆环的画笔// 进度百分比的画笔
	private Paint mPaintWater = null, mRingPaint = null, mTextPaint = null;

	// 圆环颜色 // 圆环背景颜色 // 当前进度 //水波颜色 // 水波背景色 //进度条和水波之间的距离 //进度百分比字体大小
	// //进度百分比字体颜色
	private int mRingColor, mRingBgColor, mWaterColor, mWaterBgColor,
			mFontSize, mTextColor;
	// 进度 //浪峰个数
	float crestCount = 1.5f;

	int mProgress = 10, mMaxProgress = 100;

	// 画布中心点
	private Point mCenterPoint;
	// 圆环宽度
	private float mRingWidth, mRing2WaterWidth;
	// 是否显示进度条 //是否显示进度百分比
	private boolean mShowProgress = false, mShowNumerical = true;

	/** 产生波浪效果的因子 */
	private long mWaveFactor = 0L;
	/** 正在执行波浪动画 */
	private boolean isWaving = false;
	/** 振幅 */
	private float mAmplitude = 30.0F; // 20F
	/** 波浪的速度 */
	private float mWaveSpeed = 0.070F; // 0.020F
	/** 水的透明度 */
	private int mWaterAlpha = 255; // 255

	private MyHandler mHandler = null;

	private static class MyHandler extends Handler {
		private WeakReference<WaterWaveProgress> mWeakRef = null;

		private int refreshPeriod = 100;

		public MyHandler(WaterWaveProgress host) {
			mWeakRef = new WeakReference<WaterWaveProgress>(host);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (mWeakRef.get() != null) {
				mWeakRef.get().invalidate();
				sendEmptyMessageDelayed(0, refreshPeriod);
			}
		}
	}

	/* CONSTRUCTOR */
	public WaterWaveProgress(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		mCenterPoint = new Point();

		// 如果手机版本在4.0以上,则开启硬件加速
		if (VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH) {
			setLayerType(View.LAYER_TYPE_HARDWARE, null);
			// setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		mRingPaint = new Paint();
		mRingPaint.setAntiAlias(true);
		mRingPaint.setColor(mRingColor); // 圆环颜色
		mRingPaint.setStyle(Paint.Style.STROKE);
		mRingPaint.setStrokeWidth(mRingWidth); // 圆环宽度

		mPaintWater = new Paint();
		mPaintWater.setStrokeWidth(1.0F);
		mPaintWater.setColor(mWaterColor);
		// mPaintWater.setColor(getResources().getColor(mWaterColor));
		mPaintWater.setAlpha(mWaterAlpha);

		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(mTextColor);
		mTextPaint.setStyle(Paint.Style.FILL);
		mTextPaint.setTextSize(mFontSize);

		mHandler = new MyHandler(this);
		
		Log.d(LCAT, "Progress: " + mProgress + " / " + mMaxProgress);

	}

	public void animateWave() {
		if (!isWaving) {
			mWaveFactor = 0L;
			isWaving = true;
			mHandler.sendEmptyMessage(0);
		}
	}

	@SuppressLint({ "DrawAllocation", "NewApi" })
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取整个View（容器）的宽、高
		int width = getWidth();
		int height = getHeight();
		width = height = (width < height) ? width : height;
		mAmplitude = width / 20f;

		mCenterPoint.x = width / 2;
		mCenterPoint.y = height / 2;
		{ // 重新设置进度条的宽度和水波与进度条的距离,,至于为什么写在这,我脑袋抽了可以不
			mRingWidth = mRingWidth == 0 ? width / 20 : mRingWidth;
			mRing2WaterWidth = mRing2WaterWidth == 0 ? mRingWidth * 0.6f
					: mRing2WaterWidth;
			mRingPaint.setStrokeWidth(mRingWidth);
			mTextPaint.setTextSize(mFontSize == 0 ? width / 5 : mFontSize);
			if (VERSION.SDK_INT == VERSION_CODES.JELLY_BEAN) {
				setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			} else {
				setLayerType(View.LAYER_TYPE_HARDWARE, null);
			}
		}

		RectF oval = new RectF();
		oval.left = mRingWidth / 2;
		oval.top = mRingWidth / 2;
		oval.right = width - mRingWidth / 2;
		oval.bottom = height - mRingWidth / 2;

		if (isInEditMode()) {
			mRingPaint.setColor(mRingBgColor);
			canvas.drawArc(oval, -90, 360, false, mRingPaint);
			mRingPaint.setColor(mRingColor);
			canvas.drawArc(oval, -90, 90, false, mRingPaint);
			canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mCenterPoint.x
					- mRingWidth - mRing2WaterWidth, mPaintWater);
			return;
		}

		// 如果没有执行波浪动画，或者也没有指定容器宽高，就画个简单的矩形
		if ((width == 0) || (height == 0) || isInEditMode()) {
			canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, width / 2
					- mRing2WaterWidth - mRingWidth, mPaintWater);
			return;
		}

		// 水与边框的距离
		float waterPadding = mShowProgress ? mRingWidth + mRing2WaterWidth
				: 0;
		// 水最高处
		int waterHeightCount = mShowProgress ? (int) (height - waterPadding * 2)
				: height;

		// 重新生成波浪的形状
		mWaveFactor++;
		if (mWaveFactor >= Integer.MAX_VALUE) {
			mWaveFactor = 0L;
		}

		// 画进度条背景
		mRingPaint.setColor(mRingBgColor);
		// canvas.drawArc(oval, -90, 360, false, mRingPaint);
		// //和下面效果一样,只不过这个是画个360度的弧,下面是画圆环
		canvas.drawCircle(width / 2, width / 2, waterHeightCount / 2
				+ waterPadding - mRingWidth / 2, mRingPaint);
		mRingPaint.setColor(mRingColor);
		// 100为 总进度
		canvas.drawArc(oval, -90, (mProgress * 1f) / mMaxProgress * 360f,
				false, mRingPaint);

		// 计算出水的高度
		float waterHeight = waterHeightCount
				* (1 - (mProgress * 1f) / mMaxProgress) + waterPadding;
		int staticHeight = (int) (waterHeight + mAmplitude);
		Path mPath = new Path();
		mPath.reset();
		if (mShowProgress) {
			mPath.addCircle(width / 2, width / 2, waterHeightCount / 2,
					Direction.CCW);
		} else {
			mPath.addCircle(width / 2, width / 2, waterHeightCount / 2,
					Direction.CCW);
		}
		// canvas添加限制,让接下来的绘制都在园内
		canvas.clipPath(mPath, Op.REPLACE);
		Paint bgPaint = new Paint();
		bgPaint.setColor(mWaterBgColor);
		// 绘制背景
		canvas.drawRect(waterPadding, waterPadding, waterHeightCount
				+ waterPadding, waterHeightCount + waterPadding, bgPaint);
		// 绘制静止的水
		canvas.drawRect(waterPadding, staticHeight, waterHeightCount
				+ waterPadding, waterHeightCount + waterPadding, mPaintWater);

		// 待绘制的波浪线的x坐标
		int xToBeDrawed = (int) waterPadding;
		int waveHeight = (int) (waterHeight - mAmplitude
				* Math.sin(Math.PI
						* (2.0F * (xToBeDrawed + (mWaveFactor * width)
								* mWaveSpeed)) / width));
		// 波浪线新的高度
		int newWaveHeight = waveHeight;
		while (true) {
			if (xToBeDrawed >= waterHeightCount + waterPadding) {
				break;
			}
			// 根据当前x值计算波浪线新的高度
			newWaveHeight = (int) (waterHeight - mAmplitude
					* Math.sin(Math.PI
							* (crestCount * (xToBeDrawed + (mWaveFactor * waterHeightCount)
									* mWaveSpeed)) / waterHeightCount));

			// 先画出梯形的顶边
			canvas.drawLine(xToBeDrawed, waveHeight, xToBeDrawed + 1,
					newWaveHeight, mPaintWater);

			// 画出动态变化的柱子部分
			canvas.drawLine(xToBeDrawed, newWaveHeight, xToBeDrawed + 1,
					staticHeight, mPaintWater);
			xToBeDrawed++;
			waveHeight = newWaveHeight;
		}
		if (mShowNumerical) {
			String progressTxt = String.format("%.0f", (mProgress * 1f)
					/ mMaxProgress * 100f)
					+ "%";
			float mTxtWidth = mTextPaint.measureText(progressTxt, 0,
					progressTxt.length());
			canvas.drawText(progressTxt, mCenterPoint.x - mTxtWidth / 2,
					mCenterPoint.x * 1.5f - mFontSize / 2, mTextPaint);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = widthMeasureSpec;
		int height = heightMeasureSpec;
		width = height = (width < height) ? width : height;
		setMeasuredDimension(width, height);
	}

	public void setAmplitude(float amplitude) {
		Log.d(LCAT, "setAmplitude");
		mAmplitude = amplitude;
	}

	public void setWaterAlpha(float alpha) {
		mWaterAlpha = (int) (255.0F * alpha);
		mPaintWater.setAlpha(mWaterAlpha);
	}

	
	public void setProgress(int progress) {
		progress = progress > 100 ? 100 : progress < 0 ? 0 : progress;
		mProgress = progress;
		invalidate();
	}

	public int getProgress() {
		return mProgress;
	}

	public void setWaveSpeed(float speed) {
		mWaveSpeed = speed;
	}

	public void setShowRing(boolean b) {
		mShowProgress = b;
	}

	public void setShowNumerical(boolean b) {
		mShowNumerical = b;
	}

	public void setRingColor(int mRingColor) {
		this.mRingColor = mRingColor;
	}

	public void setRingBgColor(int mRingBgColor) {
		this.mRingBgColor = mRingBgColor;
	}

	public void setWaterColor(int mWaterColor) {
		this.mWaterColor = mWaterColor;
	}

	public void setWaterBgColor(int mWaterBgColor) {
		this.mWaterBgColor = mWaterBgColor;
	}

	public void setFontSize(int mFontSize) {
		this.mFontSize = mFontSize;
	}

	public void setTextColor(int mTextColor) {
		this.mTextColor = mTextColor;
	}

	public void setMaxProgress(int mMaxProgress) {
		this.mMaxProgress = mMaxProgress;
	}

	public void setCrestCount(float crestCount) {
		this.crestCount = crestCount;
	}

	public void setRing2WaterWidth(float arg) {
		this.mRing2WaterWidth = arg;
	}

	public void setRingWidth(float width) {
		this.mRingWidth = width;
	}

	public void setIsWaving(boolean arg) {
		this.isWaving = arg;
	}

	public void setWaveFactor(long arg) {
		this.mWaveFactor = arg;
	}

}