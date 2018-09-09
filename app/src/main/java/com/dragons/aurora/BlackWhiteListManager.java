/*
 * Aurora Store
 * Copyright (C) 2018  Rahul Kumar Patel <whyorean@gmail.com>
 *
 * Yalp Store
 * Copyright (C) 2018 Sergey Yeriomin <yeriomin@gmail.com>
 *
 * Aurora Store (a fork of Yalp Store )is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Aurora Store is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Aurora Store.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.dragons.aurora;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dragons.aurora.helpers.Prefs;

import java.util.Set;

public class BlackWhiteListManager {

    private SharedPreferences preferences;
    private Set<String> blackWhiteSet;

    public BlackWhiteListManager(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        blackWhiteSet = Prefs.getStringSet(context, Aurora.PREFERENCE_UPDATE_LIST);
        if (blackWhiteSet.size() == 1 && blackWhiteSet.contains("")) {
            blackWhiteSet.clear();
        }
    }

    public boolean add(String s) {
        boolean result = blackWhiteSet.add(s);
        save();
        return result;
    }

    public boolean set(Set<String> s) {
        blackWhiteSet = s;
        if (blackWhiteSet.size() == 1 && blackWhiteSet.contains("")) {
            blackWhiteSet.clear();
        }
        save();
        return true;
    }

    public boolean isBlack() {
        return preferences.getString(Aurora.PREFERENCE_UPDATE_LIST_WHITE_OR_BLACK, Aurora.LIST_BLACK).equals(Aurora.LIST_BLACK);
    }

    public boolean isUpdatable(String packageName) {
        boolean isContained = contains(packageName);
        boolean isBlackList = isBlack();
        return (isBlackList && !isContained) || (!isBlackList && isContained);
    }

    public Set<String> get() {
        return blackWhiteSet;
    }

    public boolean contains(String s) {
        return blackWhiteSet.contains(s);
    }

    public boolean remove(String s) {
        boolean result = blackWhiteSet.remove(s);
        save();
        return result;
    }

    private void save() {
        Prefs.putStringSet(preferences, Aurora.PREFERENCE_UPDATE_LIST, blackWhiteSet);
    }
}
