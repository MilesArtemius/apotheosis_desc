package classes.keeping;

import classes.Main;

import java.util.HashMap;

public class ConstantsKeeper {
    private static final String POSITIVE = "true";
    private static final String NEGATIVE = "false";

    private static ConstantsKeeper keeper;
    private HashMap<String, String> database;

    public static ConstantsKeeper get() {
        if (keeper == null) keeper = new ConstantsKeeper();
        return keeper;
    }

    private ConstantsKeeper() {
        database = new HashMap<>();
        String homeLocation;
        try {
            String jarLocation = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            homeLocation = jarLocation.substring(1, jarLocation.lastIndexOf('/'));
        } catch (Exception e) {
            e.printStackTrace();
            homeLocation = System.getProperty("user.home");
        }
        database.put(Constants.HOME, homeLocation);

        database.putAll(FileManager.loadConstants(homeLocation));
    }

    public HashMap<String, String> getDatabase() {
        return database;
    }

    public String getConstant(String key) {
        return database.get(key);
    }

    public void setConstant(String key, String value) {
        database.put(key, value);
        FileManager.saveConstants(database.get(Constants.HOME), database);
    }
}
