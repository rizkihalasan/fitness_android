package com.example.leo.fitnessdiy.routes;

/**
 * Created by Leo on 15/02/2018.
 */

public class api {
    public static final String BASE_URL = "http://ekiwae21.000webhostapp.com/fitness-server/";
    // kalo emulator 10.0.2.2 atau 10.0.0.2
    // atau ipconfig, cek ipv4

    public static final String HISTORY_URL = BASE_URL + "history.php?user=";

    public static final String USERS_URL = BASE_URL + "users.php?user=";

    public static final String newJoggingHistory(int id_user, String date,
                                               String start, String end, float distance,
                                               String sPoint, String ePoint) {
        return BASE_URL + "new_jogging_history.php?user=" + id_user +
                "&date="+date+"&start="+start+"&end="+end+"&distance="+distance+"&spoint="+
                sPoint+"&epoint=" + ePoint;
    }

    public static final String JOGGING_HISTORY_URL = BASE_URL + "jogging_history.php?user=";

    public static final String PLANK_HISTORY_URL = BASE_URL + "plank_history.php?user=";

    public static final String PUSHUP_HISTORY_URL = BASE_URL + "pushup_history.php?user=";

    public static final String SITUP_HISTORY_URL = BASE_URL + "situp_history.php?user=";

    public static final String EDIT_LEVEL_URL = BASE_URL + "edit_level.php?";
}
