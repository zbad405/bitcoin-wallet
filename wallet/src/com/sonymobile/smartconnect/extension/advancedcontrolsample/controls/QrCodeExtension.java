package com.sonymobile.smartconnect.extension.advancedcontrolsample.controls;

import java.math.BigInteger;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.SpannableStringBuilder;

import com.google.bitcoin.core.Address;
import com.google.bitcoin.core.Wallet.BalanceType;
import com.google.bitcoin.uri.BitcoinURI;
import com.sonyericsson.extras.liveware.aef.control.Control;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;

import de.schildbach.wallet.Constants;
import de.schildbach.wallet.WalletApplication;
import de.schildbach.wallet.util.GenericUtils;
import de.schildbach.wallet.util.Qr;
import de.schildbach.wallet_test.R;

public class QrCodeExtension extends ControlExtension
{
	private final WalletApplication application;

	private int page = 0;

	public QrCodeExtension(final WalletApplication application, final String hostAppPackageName)
	{
		super(application, hostAppPackageName);

		this.application = application;
	}

	@Override
	public void onResume()
	{
		super.onResume();

		update();
	}

	@Override
	public void onSwipe(final int direction)
	{
		if (direction == Control.Intents.SWIPE_DIRECTION_LEFT)
			page++;
		else if (direction == Control.Intents.SWIPE_DIRECTION_RIGHT)
			page--;

		page = page % 2;

		update();
	}

	private void update()
	{
		System.out.println("==== " + page);

		clearDisplay();

		if (page == 0)
		{
			final BigInteger balance = application.getWallet().getBalance(BalanceType.ESTIMATED);
			final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(application);
			final int precision = Integer.parseInt(prefs.getString(Constants.PREFS_KEY_BTC_PRECISION, Constants.PREFS_DEFAULT_BTC_PRECISION));
			final Editable balanceStr = new SpannableStringBuilder(GenericUtils.formatValue(balance, precision));

			showLayout(R.layout.smartwatch_balance, null);
			sendText(R.id.smartwatch_balance_body, balanceStr.toString());
		}
		else if (page == 1)
		{
			final Address selectedAddress = application.determineSelectedAddress();
			final String addressStr = BitcoinURI.convertToBitcoinURI(selectedAddress, null, null, null);

			showBitmap(Qr.bitmap(addressStr, 180));
		}
	}
}
