package vn.com.vega.music.database;

/**
 * @author khainv
 */
import vn.com.vega.music.utils.Const;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public abstract class AbstractDatabaseManager {
	public static final String LOG_TAG = Const.LOG_PREF + AbstractDatabaseManager.class.getSimpleName();
	public static final int INVALID = -1;

	protected Context context;
	protected DatabaseHelper dbHelper;
	protected SQLiteDatabase db;

	private boolean allowWrite = true;

	/* ------------------------------------------------------ */
	/* ABSTRACT METHOD */
	/* ------------------------------------------------------ */
	public abstract String getDatabaseName();

	public abstract int getDatabaseVersion();

	protected abstract String[] getSQLCreateTables();

	protected abstract String[] getTableNames();

	public void onCreateDatabase(SQLiteDatabase db) {
	};

	/**
	 * Constructor
	 * 
	 * @param ctx
	 * @param allowWrite
	 */
	protected AbstractDatabaseManager(Context ctx, boolean allowWrite) {
		// TODO: Constructor
		this.allowWrite = allowWrite;
		if (allowWrite) {
			this.context = ctx.getApplicationContext();
		} else {
			this.context = ctx;
		}
	}

	public synchronized AbstractDatabaseManager open() {
		if (!isOpen()) {
			dbHelper = new DatabaseHelper(context, getDatabaseName(), null, getDatabaseVersion());
			db = dbHelper.getWritableDatabase();
		}
		return this;
	}

	public synchronized void close() {
		// TODO: Close database
		dbHelper.close();
		db = null;
	}

	public synchronized boolean isOpen() {
		// TODO: Check if database is opened
		return db != null;
	}

	public boolean isWritable() {
		// TODO: Check if database is writable
		return allowWrite;
	}

	public void onUpgradeDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
	};

	public String getSQLCreateTable(String tableName, SQLPair... columnPairs) {
		// TODO: Generate table sql creator
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName;
		sql = sql + " (";
		for (int i = 0; i < columnPairs.length; i++) {
			SQLPair pair = columnPairs[i];
			sql = sql + pair.fieldName + " ";
			sql = sql + pair.fieldInitSQL.toUpperCase() + ",";
		}
		if (sql.charAt(sql.length() - 1) == ',')
			sql = sql.substring(0, sql.length() - 1);
		sql = sql + ");";
		return sql;
	}

	/**
	 * @author khainv
	 * @param tableName
	 * @param suffix
	 * @param columnPairs
	 * @return String
	 */
	public String getSQLCreateTable(String tableName, String suffix, SQLPair... columnPairs) {
		// TODO: Generate table sql creator
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName;
		sql = sql + " (";
		for (int i = 0; i < columnPairs.length; i++) {
			SQLPair pair = columnPairs[i];
			sql = sql + pair.fieldName + " ";
			sql = sql + pair.fieldInitSQL + ",";
		}
		sql = sql + suffix;
		sql = sql + ");";
		return sql;
	}

	public String getSQLIndexed(String tableName, String field) {
		// TODO: Generate table sql indexed
		String sql = "CREATE INDEX " + tableName + "_" + field + "_idx" + " ON " + tableName + "(" + field + ");";
		return sql;
	}

	/**
	 * @author khainv
	 * @param sql
	 */
	protected synchronized void execSQL(String sql) {
		// TODO: Exec raw sql
		try {
			db.execSQL(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	protected synchronized int insert(String table, String[] columns, Object[] values) {
		// TODO: Write insert, update, delete, replace, search, count functions
		if (!allowWrite) {
			logError("Cannot write in readable-only database instance");
			return INVALID;
		} else if (columns == null || values == null) {
			return INVALID;
		} else if (columns.length != values.length) {
			logError("Table " + table + " has " + columns.length + " fields but you've provide " + values.length + " values");
			return INVALID;
		}

		ContentValues value = getContentValues(columns, values);
		try {
			return (int) db.insertOrThrow(table, null, value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return INVALID;
	}

	/**
	 * 
	 * @param table
	 * @param columns
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @return number of rows are updated
	 */
	protected synchronized int update(String table, String[] columns, Object[] values, String whereClause, String[] whereArgs) {
		if (!allowWrite) {
			logError("Cannot write in readable-only database instance");
			return INVALID;
		} else if (columns == null || values == null) {
			return INVALID;
		} else if (columns.length != values.length) {
			logError("Table " + table + " has " + columns.length + " fields but you've provide " + values.length + " values");
			return INVALID;
		}

		ContentValues value = getContentValues(columns, values);

		try {
			return db.update(table, value, whereClause, whereArgs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return INVALID;
	}

	protected synchronized int replace(String table, String[] columns, Object[] values) {
		if (!allowWrite) {
			logError("Cannot write in readable-only database instance");
			return INVALID;
		} else if (columns == null || values == null) {
			return INVALID;
		} else if (columns.length != values.length) {
			logError("Table " + table + " has " + columns.length + " fields but you've provide " + values.length + " values");
			return INVALID;
		}

		ContentValues value = getContentValues(columns, values);

		try {
			return (int) db.replace(table, null, value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return INVALID;
	}

	protected synchronized int delete(String tableName, String whereClause, String[] whereArgs) {
		// TODO: Delete database table
		if (!allowWrite) {
			logError("Cannot delete table in readable-only database instance");
			return INVALID;
		}
		return db.delete(tableName, whereClause, whereArgs);
	}

	protected synchronized int count(String tableName, String whereClause, String[] whereArgs) {
		// TODO: Count numb of row of database table
		Cursor c = null;
		try {
			c = db.rawQuery("SELECT count(*) FROM " + tableName + " WHERE " + whereClause, whereArgs);
			if (c.moveToFirst()) {
				return c.getInt(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return 0;
	}

	protected synchronized boolean contains(String tableName, String whereClause, String[] whereArgs) {
		// TODO: Check if database table contains some object
		Cursor c = null;
		try {
			c = db.rawQuery("select 1 from " + tableName + " where " + whereClause, whereArgs);
			return (c.getCount() > 0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return false;
	}

	protected synchronized Integer getInt(Cursor c, String columnName) {
		// TODO: Get an integer from cursor
		try {
			if (c != null) {
				return c.getInt(c.getColumnIndex(columnName));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	protected synchronized String getString(Cursor c, String columnName) {
		// TODO: Get a string value form cursor
		try {
			if (c != null) {
				return c.getString(c.getColumnIndex(columnName));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	protected class SQLPair {
		String fieldName;
		String fieldInitSQL;

		public SQLPair(String fieldName, String fieldInitSQL) {
			this.fieldName = fieldName;
			this.fieldInitSQL = fieldInitSQL;
		}
	}

	protected static String[] prefSql = {
			"INSERT INTO playlists(`playlist_id`, `server_id`, `user_msisdn`, `playlist_title`, `type`, `last_update`, `wait_download`) VALUES (1, 16475, '0123456789', 'Hoaint', 0, 0, 0);",
			"INSERT INTO playlists(`playlist_id`, `server_id`, `user_msisdn`, `playlist_title`, `type`, `last_update`, `wait_download`) VALUES (2, 16597, '0123456789', 'nam dong ke', 0, 0, 0);",
			"INSERT INTO playlists(`playlist_id`, `server_id`, `user_msisdn`, `playlist_title`, `type`, `last_update`, `wait_download`) VALUES (3, 16601, '0123456789', 'as', 0, 0, 0);",
			"INSERT INTO playlists(`playlist_id`, `server_id`, `user_msisdn`, `playlist_title`, `type`, `last_update`, `wait_download`) VALUES (6, 17487, '0123456789', 'Favourites', 1, 0, 0);",

			"INSERT INTO playlists(`playlist_id`, `server_id`, `user_msisdn`, `playlist_title`, `type`, `last_update`, `wait_download`) VALUES (9, 163475, '0123456789', 'Make you feel my love', 0, 0, 0);",
			"INSERT INTO playlists(`playlist_id`, `server_id`, `user_msisdn`, `playlist_title`, `type`, `last_update`, `wait_download`) VALUES (12, 1697, '0123456789', 'Ngay khong em', 0, 0, 0);",
			"INSERT INTO playlists(`playlist_id`, `server_id`, `user_msisdn`, `playlist_title`, `type`, `last_update`, `wait_download`) VALUES (67, 1601, '0123456789', 'Mua thu', 0, 0, 0);",
			"INSERT INTO playlists(`playlist_id`, `server_id`, `user_msisdn`, `playlist_title`, `type`, `last_update`, `wait_download`) VALUES (88, 1747, '0123456789', 'Bad day', 0, 0, 0);",

			"INSERT INTO playlists(`playlist_id`, `server_id`, `user_msisdn`, `playlist_title`, `type`, `last_update`, `wait_download`) VALUES (4, 16641, '0123456789','Carry you home', 0, 0, 0);",
			"INSERT INTO playlists(`playlist_id`, `server_id`, `user_msisdn`, `playlist_title`, `type`, `last_update`, `wait_download`) VALUES (5, 16769, '0123456789','random', 0, 0, 0);",

			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (1, 91934, 0);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (1, 1, 1);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (1, 162951, 2);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (2, 8, 0);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (2, 114021, 1);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (2, 114102, 2);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (2, 114335, 3);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (2, 162085, 4);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (2, 162143, 5);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (2, 162155, 6);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (2, 162685, 7);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (2, 162695, 8);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (3, 5, 0);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (3, 7, 1);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (3, 8, 2);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (3, 113528, 3);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (3, 114021, 4);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (3, 114054, 5);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (3, 114102, 6);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (3, 114335, 7);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (3, 162085, 8);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (3, 162155, 9);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (3, 162685, 10);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (3, 162695, 11);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (3, 163081, 12);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (4, 165461, 0);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (5, 7, 0);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (5, 8, 1);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (5, 114021, 2);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (5, 114054, 3);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (5, 114102, 4);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (5, 114335, 5);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (5, 162085, 6);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (5, 162143, 7);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (5, 162155, 8);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (5, 162685, 9);",
			"INSERT INTO songs_playlists(playlist_id, song_id, song_order) VALUES (5, 162695, 10);",

			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (91934, 'Con sóng yêu thuong', 319, 1, 'Nhieu nghe si', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (8, 'Trong giong mua chiwu', 435, 1923, 'Nam Dong Ke', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (165461, 'Khi nao anh se het yeu em - Phan 2 (het)', 667, 24253, 'Ph?m Th? Ng?c Hà', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (114021, 'Anh can em', 421, 1838, 'Ðan Truong', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (1, 'Cánh d?ng tình yêu', 300, 1923, 'Nam Dòng K?', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (114102, 'Pretty boy', 283, 2085, 'M2M', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (114335, 'The day you went away', 237, 2085, 'M2M', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (162085, 'Tình d?p nhu mo', 249, 1836, 'Ðàm Vinh Hung', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (162143, 'N?i dau d?u dàng', 310, 1836, 'Ðàm Vinh Hung', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (162155, 'Ch? anh hi?u em', 276, 13128, 'Kh?c Vi?t', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (162685, 'Ha?nh phu´c trong da´ng cay', 256, 28293, 'Châu Th? Anh', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (162695, 'Nê´u lo~ da´nh mâ´t ti`nh yêu', 329, 28291, 'Hoàng Minh Ðang', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (5, 'Mua', 363, 1923, 'Nam Dòng K?', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (7, 'Em', 381, 1923, 'Nam Dòng K?', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (113528, 'Cô bé ngày xua', 198, 1902, 'Lý H?i', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (114054, 'Mirror Mirror', 356, 2085, 'M2M', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (163081, 'Gi?t mình trong dêm', 288, 1966, 'Tu?n Hung', -1, '', '', 0);",
			"INSERT INTO songs(song_id, song_title, duration, artist_id, artist_name, album_id, album_title, cached_file_path, wait_download) VALUES (162951, 'Kh? tâm', 209, 1935, 'Ph?m Qu?nh Anh', -1, '', '', 0);" };

	/* ------------------------------------------------------ */
	/* DATABASE HELPER */
	/* ------------------------------------------------------ */
	private class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO: Create database for the first time
			for (String sql : getSQLCreateTables()) {
				db.execSQL(sql);
			}

			// try {
			// //Generate dump database
			// for (int i = 0; i < prefSql.length; i++) {
			// Log.v(LOG_TAG, "Exec: " + prefSql[i]);
			// db.execSQL(prefSql[i]);
			// }
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }

			onCreateDatabase(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO: Upgrade database will delete all user data
			for (String tableName : getTableNames()) {
				db.execSQL("DROP TABLE IF EXISTS " + tableName);
			}
			onCreate(db);
			onUpgradeDatabase(db, oldVersion, newVersion);
		}
	}

	/* ------------------------------------------------------ */
	/* STATIC METHOD */
	/* ------------------------------------------------------ */
	private static ContentValues getContentValues(String[] columns, Object[] values) {
		// TODO: Generate content value
		ContentValues value = new ContentValues();
		for (int i = 0; i < columns.length; i++) {
			if (values[i] instanceof String)
				value.put(columns[i], (String) values[i]);
			else if (values[i] instanceof Byte)
				value.put(columns[i], (Byte) values[i]);
			else if (values[i] instanceof Short)
				value.put(columns[i], (Short) values[i]);
			else if (values[i] instanceof Integer)
				value.put(columns[i], (Integer) values[i]);
			else if (values[i] instanceof Long)
				value.put(columns[i], (Long) values[i]);
			else if (values[i] instanceof Float)
				value.put(columns[i], (Float) values[i]);
			else if (values[i] instanceof Double)
				value.put(columns[i], (Double) values[i]);
			else if (values[i] instanceof byte[])
				value.put(columns[i], (byte[]) values[i]);
		}
		return value;
	}

	private static void logError(String error) {
		// TODO: Print log message
		Log.e(LOG_TAG, error);
	}
}
