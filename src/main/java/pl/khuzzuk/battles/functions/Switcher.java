package pl.khuzzuk.battles.functions;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Switcher {
    private final Runnable on;
    private final Runnable off;
    private Shift<Runnable> shift;

    public static Switcher get(@NonNull Runnable on, @NonNull Runnable off) {
        Switcher switcher = new Switcher(on, off);
        switcher.shift = Shift.get(on, off);
        return switcher;
    }

    private void on() {
        on.run();
        shift = Shift.get(off, on);
    }

    public void off() {
        off.run();
        shift = Shift.get(on, off);
    }

    public void change() {
        shift.get().run();
    }
}
