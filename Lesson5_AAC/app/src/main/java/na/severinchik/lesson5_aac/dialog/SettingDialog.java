package na.severinchik.lesson5_aac.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import na.severinchik.lesson5_aac.R;
import na.severinchik.lesson5_aac.utils.SharedPreferencesKeys;

public class SettingDialog extends DialogFragment {

    EditText saved_value_et;
    EditText alarm_value_et;
    Button save_btn;

    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.setting_dialog, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);

        init(view);
        Context context = getActivity();
        sharedPreferences = context.getSharedPreferences(SharedPreferencesKeys.SHARED_PREFERENCE_NAME,context.MODE_PRIVATE);

        loadValues();

        save_btn.setOnClickListener(v -> {
            SharedPreferences.Editor editor =  sharedPreferences.edit();
            editor.putInt(SharedPreferencesKeys.SAVE_VALUE, Integer.parseInt(saved_value_et.getText().toString()));
            editor.putInt(SharedPreferencesKeys.ALARMVALUE, Integer.parseInt(alarm_value_et.getText().toString()));
            editor.commit();
            dismiss();
        });

        return view;
    }

    private void loadValues(){
        int savedValued = sharedPreferences.getInt(SharedPreferencesKeys.SAVE_VALUE,0);
        int alarmValue = sharedPreferences.getInt(SharedPreferencesKeys.ALARMVALUE,0);
        saved_value_et.setText(String.valueOf(savedValued));
        alarm_value_et.setText(String.valueOf(alarmValue));
    }

    private void init(View view) {
        saved_value_et = view.findViewById(R.id.settings_saved_value);
        alarm_value_et = view.findViewById(R.id.settings_alarm_value);
        save_btn = view.findViewById(R.id.settings_button_save);

    }


}
