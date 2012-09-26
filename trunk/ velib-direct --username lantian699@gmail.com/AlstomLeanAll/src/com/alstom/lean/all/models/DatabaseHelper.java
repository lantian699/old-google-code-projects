package com.alstom.lean.all.models;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Helper class which creates/updates our database and provides the DAOs.
 * 
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "alstomService.db";
	private static final int DATABASE_VERSION = 1;

	private static DatabaseHelper mInstance = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	public static DatabaseHelper getInstance(final Context context) {
		if (mInstance == null) {
			mInstance = new DatabaseHelper(context);

		}
		return mInstance;
	}

	public void clearDataBase() {
		try {
			TableUtils.clearTable(connectionSource, Project.class);
			TableUtils.clearTable(connectionSource, Person.class);
			TableUtils.clearTable(connectionSource, Task.class);
			TableUtils.clearTable(connectionSource, Plant.class);
			TableUtils.clearTable(connectionSource, Block.class);
			TableUtils.clearTable(connectionSource, Unit.class);
			TableUtils.clearTable(connectionSource, System.class);
			TableUtils.clearTable(connectionSource, ComponentLevel1.class);
			TableUtils.clearTable(connectionSource, ComponentLevel2.class);
			TableUtils.clearTable(connectionSource, ComponentLevel3.class);
			TableUtils.clearTable(connectionSource, VisualInspection.class);
			TableUtils.clearTable(connectionSource, Mesurement.class);

		} catch (SQLException e) {
			Log.e("DataBaseHelper", "Could not clear tables", e);
		}
	}

	
	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Project.class);
			TableUtils.createTable(connectionSource, Person.class);
			TableUtils.createTable(connectionSource, Task.class);
			TableUtils.createTable(connectionSource, Plant.class);
			TableUtils.createTable(connectionSource, Block.class);
			TableUtils.createTable(connectionSource, Unit.class);
			TableUtils.createTable(connectionSource, System.class);
			TableUtils.createTable(connectionSource, ComponentLevel1.class);
			TableUtils.createTable(connectionSource, ComponentLevel2.class);
			TableUtils.createTable(connectionSource, ComponentLevel3.class);
			TableUtils.createTable(connectionSource, VisualInspection.class);
			TableUtils.createTable(connectionSource, Mesurement.class);

		} catch (SQLException e) {
			Log.e("DataBaseHelper", "Could not create new tables", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			
			TableUtils.dropTable(connectionSource, Project.class, true);
			TableUtils.dropTable(connectionSource, Person.class, true);
			TableUtils.dropTable(connectionSource, Task.class, true);
			TableUtils.dropTable(connectionSource, Plant.class, true);
			TableUtils.dropTable(connectionSource, Block.class, true);
			TableUtils.dropTable(connectionSource, Unit.class, true);
			TableUtils.dropTable(connectionSource, System.class, true);
			TableUtils.dropTable(connectionSource, ComponentLevel1.class, true);
			TableUtils.dropTable(connectionSource, ComponentLevel2.class, true);
			TableUtils.dropTable(connectionSource, ComponentLevel3.class, true);
			TableUtils.dropTable(connectionSource, VisualInspection.class, true);
			TableUtils.dropTable(connectionSource, Mesurement.class, true);

			onCreate(sqLiteDatabase, connectionSource);

		} catch (SQLException e) {
			Log.e("DataBaseHelper", "Could not upgrade tables", e);
		}
	}
}
