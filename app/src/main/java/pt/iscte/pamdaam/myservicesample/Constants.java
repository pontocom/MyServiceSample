package pt.iscte.pamdaam.myservicesample;

/**
 * Created by cserrao on 12/04/16.
 */
public class Constants {
    public interface ACTION {
        public static String MAIN_ACTION = "pt.iscte.pamdaam.myforegroundservice.action.main";
        public static String INIT_ACTION = "pt.iscte.pamdaam.myforegroundservice.action.init";
        public static String PREV_ACTION = "pt.iscte.pamdaam.myforegroundservice.action.prev";
        public static String PLAY_ACTION = "pt.iscte.pamdaam.myforegroundservice.action.play";
        public static String NEXT_ACTION = "pt.iscte.pamdaam.myforegroundservice.action.next";
        public static String STARTFOREGROUND_ACTION = "pt.iscte.pamdaam.myforegroundservice.startforeground";
        public static String STOPFOREGROUND_ACTION = "pt.iscte.pamdaam.myforegroundservice.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }
}
