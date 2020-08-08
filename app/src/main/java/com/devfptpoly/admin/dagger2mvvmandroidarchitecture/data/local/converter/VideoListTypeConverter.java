package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.converter;

import androidx.room.TypeConverter;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.model.Video;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class VideoListTypeConverter {
    @TypeConverter
    public List<Video> fromString(String value) {
        Type listType = new TypeToken<List<Video>>() {}.getType();
        List<Video> videos = new Gson().fromJson(value, listType);
        return videos;
    }

    @TypeConverter
    public String fromList(List<Video> videos) {
        return new Gson().toJson(videos);
    }
}
