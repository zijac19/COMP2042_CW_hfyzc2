package main;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SettingsTest {
    Settings settings = new Settings();

    @Test
    public void Volume() {
        assertEquals(-10, settings.Volume());
    }

}
