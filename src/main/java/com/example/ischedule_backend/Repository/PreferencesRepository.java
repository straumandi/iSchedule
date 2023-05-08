package com.example.ischedule_backend.Repository;

import com.example.ischedule_backend.Model.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferencesRepository extends JpaRepository<Preferences, Integer> {

    /*
     * Find the preferences associated with a given user ID.
     */
    Preferences findByUserId(int userId);

}
