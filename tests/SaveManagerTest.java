import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DisplayName("Lab 14: Save & Load Game (File I/O)")
public class SaveManagerTest {

    @TempDir
    Path tmp;

    private Character hero;

    @BeforeEach
    void setUp() {
        hero = new Character("Aragorn", 100, 50, 250);
    }

    // ==================== 🟢 CORE ====================

    @Test
    @Tag("core")
    @DisplayName("save файл шинээр үүсгэнэ")
    void saveCreatesFile() throws IOException {
        Path p = tmp.resolve("hero.csv");
        SaveManager.save(hero, p.toString());
        assertTrue(Files.exists(p), "Файл үүсгэгдэх ёстой");
        assertTrue(Files.size(p) > 0, "Файл хоосон биш байх ёстой");
    }

    @Test
    @Tag("core")
    @DisplayName("save-ийн формат 'name,hp,mp,gold'")
    void saveHasCsvFormat() throws IOException {
        Path p = tmp.resolve("hero.csv");
        SaveManager.save(hero, p.toString());
        String content = Files.readString(p).trim();
        String[] parts = content.split(",");
        assertEquals(4, parts.length, "4 CSV талбартай байх ёстой");
        assertEquals("Aragorn", parts[0]);
        assertEquals("100", parts[1]);
        assertEquals("50", parts[2]);
        assertEquals("250", parts[3]);
    }

    @Test
    @Tag("core")
    @DisplayName("load Character буцаана")
    void loadReturnsCharacter() throws IOException {
        Path p = tmp.resolve("hero.csv");
        SaveManager.save(hero, p.toString());
        Character loaded = SaveManager.load(p.toString());
        assertNotNull(loaded);
    }

    @Test
    @Tag("core")
    @DisplayName("Round-trip: save → load ижил утгатай")
    void roundTripMatches() throws IOException {
        Path p = tmp.resolve("hero.csv");
        SaveManager.save(hero, p.toString());
        Character loaded = SaveManager.load(p.toString());
        assertEquals("Aragorn", loaded.getName());
        assertEquals(100, loaded.getHp());
        assertEquals(50, loaded.getMp());
        assertEquals(250, loaded.getGold());
    }

    @Test
    @Tag("core")
    @DisplayName("Round-trip ажилтай янз бүрийн утгуудад")
    void roundTripVarious() throws IOException {
        Character c1 = new Character("Bob", 1, 2, 3);
        Character c2 = new Character("Gimli", 200, 0, 9999);

        Path p1 = tmp.resolve("c1.csv");
        Path p2 = tmp.resolve("c2.csv");
        SaveManager.save(c1, p1.toString());
        SaveManager.save(c2, p2.toString());

        Character loaded1 = SaveManager.load(p1.toString());
        Character loaded2 = SaveManager.load(p2.toString());
        assertEquals("Bob", loaded1.getName());
        assertEquals(1, loaded1.getHp());
        assertEquals(9999, loaded2.getGold());
    }

    @Test
    @Tag("core")
    @DisplayName("save method throws IOException зарласан")
    void saveDeclaresIOException() throws Exception {
        java.lang.reflect.Method m = SaveManager.class.getMethod("save", Character.class, String.class);
        boolean has = false;
        for (Class<?> ex : m.getExceptionTypes()) {
            if (IOException.class.isAssignableFrom(ex)) { has = true; break; }
        }
        assertTrue(has, "save method нь throws IOException зарласан байх ёстой");
    }

    @Test
    @Tag("core")
    @DisplayName("load method throws IOException зарласан")
    void loadDeclaresIOException() throws Exception {
        java.lang.reflect.Method m = SaveManager.class.getMethod("load", String.class);
        boolean has = false;
        for (Class<?> ex : m.getExceptionTypes()) {
            if (IOException.class.isAssignableFrom(ex)) { has = true; break; }
        }
        assertTrue(has, "load method нь throws IOException зарласан байх ёстой");
    }

    @Test
    @Tag("core")
    @DisplayName("Давтан save/load — resource leak байхгүй")
    void noResourceLeakOnRepeatedUse() throws IOException {
        Path p = tmp.resolve("repeat.csv");
        for (int i = 0; i < 20; i++) {
            Character c = new Character("H" + i, i, i * 2, i * 10);
            SaveManager.save(c, p.toString());
            Character loaded = SaveManager.load(p.toString());
            assertEquals("H" + i, loaded.getName());
            assertEquals(i, loaded.getHp());
        }
    }

    @Test
    @Tag("core")
    @DisplayName("save overwrite — шинэ файлаар солино")
    void saveOverwrites() throws IOException {
        Path p = tmp.resolve("hero.csv");
        SaveManager.save(new Character("First", 1, 1, 1), p.toString());
        SaveManager.save(new Character("Second", 99, 99, 99), p.toString());
        Character loaded = SaveManager.load(p.toString());
        assertEquals("Second", loaded.getName());
        assertEquals(99, loaded.getHp());
    }

    // ==================== 🟡 STRETCH ====================

    @Test
    @Tag("stretch")
    @DisplayName("saveParty / loadParty round-trip")
    void partyRoundTrip() throws IOException {
        List<Character> party = new ArrayList<>();
        party.add(new Character("Aragorn", 100, 50, 250));
        party.add(new Character("Legolas", 80, 100, 0));
        party.add(new Character("Gimli", 120, 30, 500));

        Path p = tmp.resolve("party.csv");
        SaveManager.saveParty(party, p.toString());

        List<Character> loaded = SaveManager.loadParty(p.toString());
        assertEquals(3, loaded.size());
        assertEquals("Aragorn", loaded.get(0).getName());
        assertEquals(80, loaded.get(1).getHp());
        assertEquals(500, loaded.get(2).getGold());
    }

    @Test
    @Tag("stretch")
    @DisplayName("saveInventory / loadInventory round-trip")
    void inventoryRoundTrip() throws IOException {
        Map<String, Integer> inv = new HashMap<>();
        inv.put("Red Potion", 3);
        inv.put("Sword", 1);
        inv.put("Arrow", 50);

        Path p = tmp.resolve("inv.csv");
        SaveManager.saveInventory(inv, p.toString());

        Map<String, Integer> loaded = SaveManager.loadInventory(p.toString());
        assertEquals(3, loaded.size());
        assertEquals(3, loaded.get("Red Potion"));
        assertEquals(1, loaded.get("Sword"));
        assertEquals(50, loaded.get("Arrow"));
    }

    @Test
    @Tag("stretch")
    @DisplayName("Хоосон party save / load")
    void emptyPartyRoundTrip() throws IOException {
        Path p = tmp.resolve("empty.csv");
        SaveManager.saveParty(new ArrayList<>(), p.toString());
        List<Character> loaded = SaveManager.loadParty(p.toString());
        assertNotNull(loaded);
        assertEquals(0, loaded.size());
    }

    // ==================== 🔴 BONUS ====================

    @Test
    @Tag("bonus")
    @DisplayName("saveJson файлд 'name', 'hp', 'mp', 'gold' key-үүд байна")
    void saveJsonContainsKeys() throws IOException {
        Path p = tmp.resolve("hero.json");
        SaveManager.saveJson(hero, p.toString());
        String content = Files.readString(p);
        assertTrue(content.contains("\"name\""), "'name' key байх ёстой");
        assertTrue(content.contains("\"hp\""), "'hp' key байх ёстой");
        assertTrue(content.contains("\"mp\""), "'mp' key байх ёстой");
        assertTrue(content.contains("\"gold\""), "'gold' key байх ёстой");
        assertTrue(content.contains("Aragorn"), "нэр файлд бичигдсэн байх ёстой");
        assertTrue(content.contains("100"), "hp утга файлд бичигдсэн байх ёстой");
    }

    @Test
    @Tag("bonus")
    @DisplayName("load байхгүй файл үед IOException шиднэ")
    void loadThrowsOnMissingFile() {
        Path p = tmp.resolve("nonexistent.csv");
        assertThrows(IOException.class, () -> SaveManager.load(p.toString()));
    }
}
