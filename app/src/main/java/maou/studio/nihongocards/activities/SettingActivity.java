package maou.studio.nihongocards.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import maou.studio.nihongocards.R;
import maou.studio.nihongocards.databinding.ActivitySettingBinding;

@SuppressLint("StaticFieldLeak")
public class SettingActivity extends AppCompatActivity {

    protected static ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        setContentView(binding.getRoot());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(binding.container.getId(), new SettingsFragment());
        transaction.commit();
        setOnBack();
    }

    private void setOnBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                MaterialAlertDialogBuilder mBuilder = new MaterialAlertDialogBuilder(SettingActivity.this, R.style.MaterialDialog);
                mBuilder.setMessage(R.string.do_you_want_to_go_back_to_the_home_screen);
                mBuilder.setNegativeButton(getText(R.string.no), (dialog, which) -> dialog.dismiss());
                mBuilder.setPositiveButton(getText(R.string.yes), (dialog, which) -> finish());
                mBuilder.setCancelable(false);
                mBuilder.show();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        protected Snackbar snackbar;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.settings, rootKey);
            CheckBoxPreference checkBoxPreferenceHiragana = findPreference("enable_hiragana");
            CheckBoxPreference checkBoxPreferenceKatakana = findPreference("enable_katakana");
            SeekBarPreference seekBarPreferenceVolume = findPreference("adjust_sound");
        }

        private void showMessage(CoordinatorLayout coordinator, String message) {
            if (snackbar != null && snackbar.isShown()) {
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        showSnackbar(coordinator, message);
                    }
                });
            } else {
                showSnackbar(coordinator, message);
            }
        }

        private void showSnackbar(CoordinatorLayout coordinator, String message) {
            Animation slideUp = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_slide_up);
            snackbar = Snackbar.make(coordinator, message, Snackbar.LENGTH_INDEFINITE);
            int margin = getResources().getDimensionPixelSize(R.dimen.margin_medium);
            View snackbarView = snackbar.getView();
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();
            params.gravity = Gravity.BOTTOM;
            params.setMargins(margin, margin, margin, margin);
            snackbarView.setLayoutParams(params);
            snackbarView.startAnimation(slideUp);
            snackbar.setDuration(5000);
            snackbar.addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    super.onDismissed(snackbar, event);
                }
            });
            snackbar.show();
        }

    }

}