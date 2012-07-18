package velib.model;

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

	private static final String DATABASE_NAME = "velib.db";
	private static final int DATABASE_VERSION = 2;

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
			TableUtils.clearTable(connectionSource, StationVelib.class);
			TableUtils.clearTable(connectionSource, InfoStation.class);

		} catch (SQLException e) {
			Log.e("DataBaseHelper", "Could not clear tables", e);
		}
	}

	
	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, StationVelib.class);
			TableUtils.createTable(connectionSource, InfoStation.class);

		} catch (SQLException e) {
			Log.e("DataBaseHelper", "Could not create new tables", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, StationVelib.class, true);
			TableUtils.dropTable(connectionSource, InfoStation.class, true);

			onCreate(sqLiteDatabase, connectionSource);

		} catch (SQLException e) {
			Log.e("DataBaseHelper", "Could not upgrade tables", e);
		}
	}
}
