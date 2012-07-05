package org.mariotaku.twidere.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;

/**
 * This is a fragment that will be used during transition from activities to
 * fragments.
 */
public abstract class ActivityHostFragment extends LocalActivityManagerFragment {

	private final static String ACTIVITY_TAG = "hosted";

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final Intent intent = new Intent(getActivity(), getActivityClass());
		intent.putExtras(getArguments());

		final Window w = getLocalActivityManager().startActivity(ACTIVITY_TAG, intent);
		final View wd = w != null ? w.getDecorView() : null;

		if (wd != null) {
			final ViewParent parent = wd.getParent();
			if (parent != null) {
				final ViewGroup v = (ViewGroup) parent;
				v.removeView(wd);
			}

			wd.setVisibility(View.VISIBLE);
			wd.setFocusableInTouchMode(true);
			if (wd instanceof ViewGroup) {
				((ViewGroup) wd).setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
			}
		}
		return wd;
	}

	protected abstract Class<? extends Activity> getActivityClass();
}