package rx.plugins;

/**
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public class RxJavaTestPlugins extends RxJavaPlugins {
    RxJavaTestPlugins() {
        super();
    }

    public static void resetPlugins(){
        getInstance().reset();
    }
}
