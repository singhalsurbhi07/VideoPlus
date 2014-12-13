package com.example.videoplus.utils;

import com.example.videoplus.db.MySQLiteHelperVideos;

import io.oauth.OAuthData;

public class StaticDataReference {
	private static OAuthData data = null;
	private static MySQLiteHelperVideos sqlHelper = null;
	public static MySQLiteHelperVideos getSqlHelper() {
		return sqlHelper;
	}

	public static void setSqlHelper(MySQLiteHelperVideos sqlHelper) {
		sqlHelper = sqlHelper;
	}

	

	public static OAuthData getData() {
		return data;
	}

	public static void setData(OAuthData data) {
		StaticDataReference.data = data;
	} 
}
