# 🐉 Lab 14 — File I/O: Save & Load Game

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![JUnit](https://img.shields.io/badge/JUnit-5-green?logo=junit5)
![Auto-Grader](https://img.shields.io/badge/Auto--Grader-Enabled-blue)
![AI Detection](https://img.shields.io/badge/AI%20Detection-Enabled-red)

> Dungeon of OOP-д баатар олон цагийн тэмцэл явуулж болох ч, компьютер унтрахаар бүх ахиц алга болно. **File I/O** — `FileWriter`, `BufferedReader`, try-with-resources — нь **Save Point** болж, чиний баатрын төлөв, эд зүйлс, party-г дискэнд хадгалан дараа ачаалах боломж олгоно.

## 📚 Суралцах материал

- **Теори:** [`UEFA-OPP-resources/docs/week-14-file-io/`](https://github.com/UEFA-OPP/UEFA-OPP-resources/tree/main/docs/week-14-file-io)
- **Git workflow заавар:** [`UEFA-OPP-resources/docs/git-workflow/`](https://github.com/UEFA-OPP/UEFA-OPP-resources/tree/main/docs/git-workflow)

## 🏗️ Хавтасны бүтэц

```
lab14-template/
├── README.md                        # Энэ файл
├── .gitignore
├── assignments/
│   └── saves/
│       ├── Character.java           # ← Урьдчилан бичигдсэн (бүү өөрчил)
│       ├── SaveManager.java         # ← Та энд код бичнэ
│       └── README.md                # Даалгаврын дэлгэрэнгүй заавар
├── tests/
│   └── SaveManagerTest.java         # JUnit 5 тестүүд (бүү өөрчил)
├── scripts/
│   ├── run_tests.sh
│   └── ai_detector.py
└── .github/workflows/grade.yml
```

## 🚀 Лаб хийх заавар (Алхам алхмаар)

### Алхам 1: Repo-г Fork хийх

1. Браузераар [`UEFA-OPP/lab14-template`](https://github.com/UEFA-OPP/lab14-template) руу орно
2. **Fork** товч дарна
3. Owner-ээр өөрийн account-ийг сонгоод **Create fork** дарна

### Алхам 2: Компьютер дээрээ Clone хийх

```bash
git clone https://github.com/<таны-username>/lab14-template.git
cd lab14-template
```

### Алхам 3: Branch үүсгэх

```bash
git checkout -b lab14/<өөрийн-нэр>
```

### Алхам 4: Даалгаврын зааврыг унших

```bash
cat assignments/saves/README.md
```

### Алхам 5: Код бичих

`assignments/saves/SaveManager.java` дахь `// TODO`-уудыг соль:

- 🟢 **Core (60 оноо)** — `save`, `load`, round-trip, try-with-resources
- 🟡 **Stretch (30 оноо)** — `saveParty`, `loadParty`, `saveInventory`, `loadInventory`
- 🔴 **Bonus (10 оноо)** — `saveJson`, `load` нь алдаатай файлд `IOException` шиднэ

### Алхам 6: Локал тест ажиллуулах

```bash
bash scripts/run_tests.sh
bash scripts/run_tests.sh --tag core
bash scripts/run_tests.sh --tag stretch
bash scripts/run_tests.sh --tag bonus
```

### Алхам 7: Commit хийх

```bash
git add assignments/
git commit -m "Implement SaveManager - <your name>"
```

### Алхам 8: GitHub руу Push хийх

```bash
git push origin lab14/<өөрийн-нэр>
```

### Алхам 9: Pull Request (PR) үүсгэх

PR title-д **өөрийн нэр, бүлгийг** бичнэ. Жишээ: `Bat-Erdene - SE401`

### Алхам 10: Автомат шалгалтын дүнг харах

GitHub Actions автоматаар ажиллана. Үр дүн PR-т коммент болж гарна.

## 📊 Оноо тооцох систем

| Tier | Жин | Тайлбар |
|------|-----|---------|
| 🟢 **Core** | **60%** | save/load CSV, try-with-resources |
| 🟡 **Stretch** | **30%** | saveParty, saveInventory |
| 🔴 **Bonus** | **10%** | saveJson, IOException |

## 🤖 AI Detection policy

| Оноо | Түвшин | Үр дагавар |
|------|--------|------------|
| 0-19 | ✅ **LOW** | Асуудалгүй. |
| 20-39 | ⚠️ **MEDIUM** | Багш шалгана. |
| 40+ | 🚨 **HIGH** | Онооноос **50% хасна**. |

## ⚠️ Дүрэм

1. **Тест файлыг өөрчлөхгүй**
2. **`Character.java`-г өөрчлөхгүй**
3. **Файлыг `@TempDir`-ээр үүсгэнэ** (тест өөрөө түр замаар хангана) — гар хөдлөлгөөнөөр `/tmp` руу бичих шаардлагагүй
4. **AI ашиглахгүй**

## 🛠️ Шаардлага

- **Java 17+**
- **Python 3.11+**
- **Bash**, **curl**, **Git**

## 📞 Асуулт байвал

Багшаасаа асуу. Амжилт хүсье, адвенчурер! 🗡️🛡️
