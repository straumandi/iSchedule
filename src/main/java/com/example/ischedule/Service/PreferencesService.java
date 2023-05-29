package com.example.ischedule.Service;

import com.example.ischedule.Model.Preferences;

public interface PreferencesService {
    Preferences getPreferencesByUserId(int userId);

    void savePreferences(Preferences preferences);
}
