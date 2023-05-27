package com.example.ischedule.Service.impl;

import com.example.ischedule.Model.Preferences;
import com.example.ischedule.Repository.PreferencesRepository;
import com.example.ischedule.Service.PreferencesService;
import org.springframework.stereotype.Service;

@Service
public class PreferencesServiceImpl implements PreferencesService {
    private final PreferencesRepository preferencesRepository;

    public PreferencesServiceImpl(PreferencesRepository preferencesRepository) {
        this.preferencesRepository = preferencesRepository;
    }

    @Override
    public Preferences getPreferencesByUserId(int userId) {
        return preferencesRepository.findByUserId(userId);
    }

    @Override
    public Preferences savePreferences(Preferences preferences) {
        return preferencesRepository.save(preferences);
    }
}
