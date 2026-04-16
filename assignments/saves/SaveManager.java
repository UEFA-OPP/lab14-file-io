import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveManager {

    // ─────── 🟢 Core (60 оноо) ───────

    // TODO: save(Character c, String path) → void
    // - throws IOException
    // - try-with-resources BufferedWriter + FileWriter ашиглана
    // - бичих формат: "name,hp,mp,gold\n"
    // - жишээ: "Aragorn,100,50,250"

    // TODO: load(String path) → Character
    // - throws IOException
    // - try-with-resources BufferedReader + FileReader ашиглана
    // - эхний мөрийг readLine()
    // - таслалаар split → parts[0] (name), parts[1..3] (int parseInt)
    // - new Character(parts[0], hp, mp, gold) буцаана

    // ─────── 🟡 Stretch (30 оноо) ───────

    // TODO: saveParty(List<Character> party, String path) → void
    // - throws IOException
    // - баатар тус бүрийг нэг мөрөнд бичнэ

    // TODO: loadParty(String path) → List<Character>
    // - throws IOException
    // - мөр тус бүрийг парс хийж жагсаалтад нэмнэ

    // TODO: saveInventory(Map<String, Integer> inv, String path) → void
    // - throws IOException
    // - бичих формат: "itemname,count\n"

    // TODO: loadInventory(String path) → Map<String, Integer>
    // - throws IOException
    // - мөр тус бүрийг парс хийж Map-д нэмнэ

    // ─────── 🔴 Bonus (10 оноо) ───────

    // TODO: saveJson(Character c, String path) → void
    // - throws IOException
    // - бичих формат: {"name":"Aragorn","hp":100,"mp":50,"gold":250}
    // - String concat-оор хангалттай (ямар ч library хэрэггүй)

    // TODO: load(String path) нь байхгүй файлд автоматаар IOException шиднэ
    // - FileReader нь байхгүй файлыг нээх үед IOException шидэх тул
    //   нэмэлт код бичих шаардлагагүй. Зүгээр 'throws IOException'
    //   зарлаж үлдээхэд л шаардлагатай bonus-ыг хангах.
}
