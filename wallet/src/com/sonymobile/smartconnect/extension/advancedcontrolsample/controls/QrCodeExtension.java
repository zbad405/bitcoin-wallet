package com.sonymobile.smartconnect.extension.advancedcontrolsample.controls;

import android.content.Context;
import android.graphics.Bitmap;

import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;

public class QrCodeExtension extends ControlExtension
{
	private final Bitmap bitmap;

	public QrCodeExtension(final Context context, final String hostAppPackageName, final Bitmap bitmap)
	{
		super(context, hostAppPackageName);

		this.bitmap = bitmap;
	}

	@Override
	public void onResume()
	{
		super.onResume();

		showBitmap(bitmap);
	}
}
