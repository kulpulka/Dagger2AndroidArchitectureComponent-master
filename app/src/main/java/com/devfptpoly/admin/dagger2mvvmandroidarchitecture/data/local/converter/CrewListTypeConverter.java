package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.converter;

import androidx.room.TypeConverter;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.model.Crew;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CrewListTypeConverter {
    @TypeConverter
    public List<Crew> fromString(String value) {
        Type listType = new TypeToken<List<Crew>>() {}.getType();
        List<Crew> casts = new Gson().fromJson(value, listType);
        return casts;
    }

    @TypeConverter
    public String fromList(List<Crew> casts) {
        return new Gson().toJson(casts);
    }
}
