package com.example.ischedule.Service;

import com.example.ischedule.Model.Preferences;

public interface PreferencesService {
    Preferences getPreferencesByUserId(int userId);

    Preferences savePreferences(Preferences preferences);
}
