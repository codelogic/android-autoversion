package aav;

import android.app.Application;
import android.os.StrictMode;

public class AavApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      // Strict policy that will crash whenever anything bad happens.
      StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
          .detectAll()
          .penaltyLog()
          .penaltyDeath()
          .build());
      StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
          .detectAll()
          .penaltyLog()
          .penaltyDeath()
          .build());
    }
  }
}
