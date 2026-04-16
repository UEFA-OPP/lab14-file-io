# Lab 14 — Save & Load Game (File I/O)

**Нийт оноо:** 100 | **Сэдэв:** FileReader/Writer, BufferedReader/Writer, try-with-resources, parsing

## 🎭 Түүх

Баатар чинь urgent quest-д гарах гэж байна — гэтэл чи ширээнээсээ босч Wi-Fi-гаа суулгах хэрэгтэй. Компьютерыг унтрааж болох ч **Save Point** ашиглаж буй баатрынхаа **HP, MP, алт**-ыг файлд хадгалвал, дараа нь яг ийм л хэвээр сэргээж тоглох боломжтой.

---

## 📋 Урьдчилан бичигдсэн файл

### `Character.java` (бүү өөрчил)

```java
public class Character {
    private String name;
    private int hp, mp, gold;

    public Character(String name, int hp, int mp, int gold) { ... }
    public String getName() { ... }
    public int getHp() { ... }
    public int getMp() { ... }
    public int getGold() { ... }
    public void setHp(int hp) { ... }
    public void setMp(int mp) { ... }
    public void setGold(int gold) { ... }
}
```

---

## 🟢 Core (60 оноо) — 9 тест

### 1. `SaveManager.save(Character c, String path)`

Баатрын төлөвийг CSV форматаар нэг мөрөнд бичнэ:

```
name,hp,mp,gold
```

Жишээ: `Aragorn,100,50,250`

**Шаардлага:**
- `BufferedWriter` + `FileWriter`-ийг **try-with-resources**-ээр ашиглана
- Мөрийн эцэст `\n` шинэ мөр
- `throws IOException`

```java
public static void save(Character c, String path) throws IOException {
    try (BufferedWriter w = new BufferedWriter(new FileWriter(path))) {
        w.write(c.getName() + "," + c.getHp() + "," + c.getMp() + "," + c.getGold());
        w.newLine();
    }
}
```

### 2. `SaveManager.load(String path) → Character`

```java
public static Character load(String path) throws IOException {
    try (BufferedReader r = new BufferedReader(new FileReader(path))) {
        String line = r.readLine();
        String[] parts = line.split(",");
        return new Character(
            parts[0],
            Integer.parseInt(parts[1]),
            Integer.parseInt(parts[2]),
            Integer.parseInt(parts[3])
        );
    }
}
```

**Шаардлага:**
- Эхний мөрийг унших
- Таслалаар split → 4 хэсэг
- 2, 3, 4-р хэсгийг `int`-д хөрвүүлэх

### Round-trip

```java
Character saved = new Character("Aragorn", 100, 50, 250);
SaveManager.save(saved, path);
Character loaded = SaveManager.load(path);
// loaded.getName() == "Aragorn", getHp()==100, ...
```

---

## 🟡 Stretch (30 оноо) — 3 тест

### 3. `saveParty(List<Character> party, String path)` / `loadParty(String path)`

Хэд хэдэн баатрыг мөр мөрөөр нь бичнэ:

```
Aragorn,100,50,250
Legolas,80,100,0
Gimli,120,30,500
```

```java
public static void saveParty(List<Character> party, String path) throws IOException {
    try (BufferedWriter w = new BufferedWriter(new FileWriter(path))) {
        for (Character c : party) {
            w.write(c.getName() + "," + c.getHp() + "," + c.getMp() + "," + c.getGold());
            w.newLine();
        }
    }
}

public static List<Character> loadParty(String path) throws IOException {
    List<Character> party = new ArrayList<>();
    try (BufferedReader r = new BufferedReader(new FileReader(path))) {
        String line;
        while ((line = r.readLine()) != null) {
            if (line.isEmpty()) continue;
            String[] p = line.split(",");
            party.add(new Character(p[0], Integer.parseInt(p[1]),
                Integer.parseInt(p[2]), Integer.parseInt(p[3])));
        }
    }
    return party;
}
```

### 4. `saveInventory(Map<String, Integer> inv, String path)` / `loadInventory(String path)`

Inventory-г мөр тус бүрд `itemname,count` форматаар:

```
Red Potion,3
Sword,1
Arrow,50
```

---

## 🔴 Bonus (10 оноо) — 2 тест

### 5. `saveJson(Character c, String path)`

Гар хөдлөлгөөний **энгийн** JSON формат:

```json
{"name":"Aragorn","hp":100,"mp":50,"gold":250}
```

- `org.json` library хэрэггүй — String concat-оор бичиж болно
- Тест зөвхөн `"name"`, `"hp"`, `"mp"`, `"gold"` гэсэн key-үүд файлд байгаа эсэхийг шалгана

```java
public static void saveJson(Character c, String path) throws IOException {
    try (BufferedWriter w = new BufferedWriter(new FileWriter(path))) {
        w.write("{\"name\":\"" + c.getName() + "\",\"hp\":" + c.getHp()
            + ",\"mp\":" + c.getMp() + ",\"gold\":" + c.getGold() + "}");
    }
}
```

### 6. `load` нь байхгүй файлд `IOException` шиднэ

`FileReader` нь файл байхгүй үед `FileNotFoundException` (→ `IOException`)-г автоматаар шиднэ. Оюутан тусгай catch хийхгүй байсан ч `throws IOException` зарласан учраас method-оос гарна.

---

## 🧪 Тест ажиллуулах

```bash
bash scripts/run_tests.sh
bash scripts/run_tests.sh --tag core
bash scripts/run_tests.sh --tag stretch
bash scripts/run_tests.sh --tag bonus
```

---

## ✅ Шалгуурын жагсаалт (Checklist)

### Core
- [ ] `save(Character, String)` — CSV форматаар бичнэ
- [ ] `load(String)` — CSV-ээс Character парс хийнэ
- [ ] try-with-resources-ээр resource автомат хаагдана
- [ ] Round-trip — save → load → ижил утгатай
- [ ] `throws IOException` зарласан

### Stretch
- [ ] `saveParty`, `loadParty` — мөр тус бүрд нэг баатар
- [ ] `saveInventory`, `loadInventory` — `itemname,count` формат

### Bonus
- [ ] `saveJson` — JSON-like формат, 4 key-тэй
- [ ] `load` байхгүй файлд `IOException` шиднэ

---

## 🚫 Түгээмэл алдаанууд

1. **try-with-resources ашиглахгүй** — `writer.close()` гараар хийх нь шаардлагатай finally блоктой болно, алдаатай
2. **`throws IOException` мартах** — signature-д заавал бичнэ
3. **`newLine()` мартах** — файлд шинэ мөр байхгүй бол `loadParty` мөр тус бүрийг буруу уншина
4. **Абсолют зам харшаах** — `/tmp/save.csv` гэж кодонд бичих хэрэггүй. Тест зам параметрээр өгнө
5. **`split(",")` дараа `Integer.parseInt` алдаа барихгүй** — тест зөв форматтай өгөлт өгнө, парс хэрэгтэй
