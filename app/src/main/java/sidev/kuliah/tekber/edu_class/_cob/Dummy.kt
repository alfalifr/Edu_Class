package sidev.kuliah.tekber.edu_class._cob

import sidev.kuliah.tekber.edu_class.model.*
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.tool.util.`fun`.fkmFrom

val dumm_profile= arrayOf(
    Profil("1", "stud1", "Barjo Ko", "08123321456", "barjo@mail.com"),
    Profil("2", "stud2", "Pak Ijo", "088877123", "ijoroyo@mail.com"),
    Profil("3", "stud3", "Eko Koko", "0123321432", "kokobelok@mail.com"),
    Profil("4", "stud4", "JesMunee", "0521134567", "jesmun@mail.com")
)


val dumm_content_read= arrayOf(
    ContentRead("0", "Pengantar", "Dalam modul ini, kalian akan belajar ttg sejarah perubahan prinsip teori gravitasi dari pandangan Newton hingga Newton."),
    ContentRead("1", "Gaya Sentripetal", "Ini adalah sebuah gaya alami, bkn gaya2an."),
    ContentRead("2", null, "Lanjutan dari gaya sentripetal"),
    ContentRead("3", "Gaya Listrik", "Ini adalah sebuah gaya alami, bkn gaya2an. (2)"),
    ContentRead("4", "Ekonom Terkini", "Ekonom adalah orang yg memiliki keahlian di bidang ekonomi"),
    ContentRead("5", "Pandemonium", "Berasal dari kata 'Pandemi', namun kata ini hanya lah fiksi belaka")
)

val dumm_content_question= arrayOf(
    ContentQuestion("1", "Apa itu gaya sentripetal?", Const.QUESTION_KIND_PILGAN,
        arrayOf("Gaya saling menarik", "Gaya yg menjauhi titik pusat", "Gaya yg dibuat oleh Alam"), arrayOf("0"), null),
    ContentQuestion("2", "Siapa bapak koperasi Indonesia?", Const.QUESTION_KIND_PILGAN,
        arrayOf("Jusuf Kalla", "Soekarno", "Hatta Rajasa", "Tidak ada yg benar"), arrayOf("3"), null),
    ContentQuestion("3", "Syarat sebuah negara?", Const.QUESTION_KIND_PILGAN,
        arrayOf("Punya rakyat", "Punya pemerintahan", "Punya sumber daya", "Diakui yg lain"), arrayOf("0", "1","3"), null),
    ContentQuestion("4", "Jelaskan scr singkat penyebab terjadinya WW 2!", Const.QUESTION_KIND_FILL,
        null, null, null)
)

val dumm_content_video= arrayOf(
    ContentVideo("1", "https://www.youtube.com/watch?v=cf3lgs_fFnM&list=RDMM7foU3Sd3RnE&index=4", "Inspirasi pagi hari"),
    ContentVideo("2", "https://www.youtube.com/watch?v=VTUCTT6I1TU", null),
    ContentVideo("3", "https://www.youtube.com/watch?v=AgPj1Q6D--c", "Ttg koding"),
    ContentVideo("4", "https://www.youtube.com/watch?v=Ci4L6myFFng", "Tentang om ensten"),
    ContentVideo("5", "https://www.youtube.com/watch?v=c_jyHp3bmEw", "Om darwin")
)



val dumm_page= arrayOf(
    Page("1", "Pengantar", 1, fkmFrom(dumm_content_read[0])),
    Page("2", "Physic of Gravity", 2, fkmFrom(dumm_content_video[3], dumm_content_read[1], dumm_content_read[2], dumm_content_read[3])),
    Page("3", "Latihan soal", 3, fkmFrom(*dumm_content_question)),
    Page("4", "All is bout change", 2, fkmFrom(dumm_content_video[3], dumm_content_question[3], dumm_content_read[1]))
)


val dumm_module= arrayOf(
    Module("1", "Teori Gravitasi", "Menunjukan perubahan teori gravitasi dari Newton sampai Ensten", null, Duration("W 1", "W 8"),
    fkmFrom(dumm_page[0], dumm_page[1], dumm_page[2])),
    Module("2", "UTS", "Kerjakan dg jujur", null, Duration("W 9"),
    fkmFrom(dumm_page[2])),
    Module("3", "Teori Evolusi", "\"Perubahan membutuhkan waktu yg lama\" -anon", null, Duration("W 10", "W 16"),
    fkmFrom(dumm_page[0], dumm_page[3], dumm_page[2])),
    Module("3", "Teori Terakhir", "Ini yg trahir", null, Duration("W 17"),
    fkmFrom(dumm_page[0], dumm_page[3], dumm_page[2]))
)

val dumm_class= arrayOf(
    ClassModel("1", "Analisis Sains", "A", "Maryati S.Pd", 3, fkmFrom(*dumm_module), null),
    ClassModel("2", "Analisis Ekonom", "B", "Spradiyono Suratmajadimeja", 3, fkmFrom(*dumm_module), null)
)

val dumm_smt_class= arrayOf(
    SemesterClass("1", fkmFrom(*dumm_class), 6)
)

val dumm_time_now= WeekTime("1", "Rabu, 17 Juni 2020", 5)
val dumm_upcoming_class= dumm_class[0]

val dumm_schedule= arrayOf(
    ScheduleModel("1", fkmFrom(dumm_class[0]), "Senin", Duration("09.00", "10.50"), "Gedung A"),
    ScheduleModel("2", fkmFrom(dumm_class[0]), "Rabu", Duration("11.00", "12.50"), "Gedung AB"),
    ScheduleModel("3", fkmFrom(dumm_class[1]), "Kami", Duration("10.00", "11.50"), "Graha Di")
)

val dumm_presense= arrayOf(
    Presence_("1", "17 Juni 2020", Const.STATUS_PRESENCE_PRESENT, "Belajar tentang teori gravitasi", null),
    Presence_("2", "21 Juni 2020", Const.STATUS_PRESENCE_IJIN, null, null),
    Presence_("3", "30 Juni 2020", Const.STATUS_PRESENCE_ALPHA, null, null),
    Presence_("4", "10 Juli 2020", Const.STATUS_PRESENCE_PRESENT, "Teori evolusinya asik", null),
    Presence_("5", "17 Juli 2020", Const.STATUS_PRESENCE_NEW, null, null),
    Presence_("6", "17 Juli 2020", Const.STATUS_PRESENCE_NEW, null, null),
    Presence_("7", "17 Juli 2020", Const.STATUS_PRESENCE_NEW, null, null),
    Presence_("8", "17 Juli 2020", Const.STATUS_PRESENCE_NEW, null, null)
)

val dumm_presence_class= arrayOf(
    PresenceClass("1", fkmFrom(dumm_class[0]), fkmFrom(dumm_schedule[0], dumm_schedule[1]), fkmFrom(*dumm_presense), 2, 1, 1),
    PresenceClass("2", fkmFrom(dumm_class[1]), fkmFrom(dumm_schedule[2]), fkmFrom(*dumm_presense), 2, 1, 1)
)

val dumm_presence_class_smt= arrayOf(
    PresenceClassSmt("1", "6", fkmFrom(*dumm_presence_class))
)

val dumm_news= arrayOf(
    Notif("1", null, "Tugas baru Analisis Sains", "2020-06-18 10:01:00"),
    Notif("2", null, "DL tugas 2 Analisis Sains", "2020-06-18 11:01:00"),
    Notif("3", null, "Tugas baru Analisis Ekonom", "2020-06-18 12:01:00"),
    Notif("4", null, "Tugas baru Analisis Sains!", "2020-06-18 12:10:00"),
    Notif("5", "AS Pindah kelas", "Pindah ke gedung A", "2020-06-18 12:11:10"),
    Notif("6", "Kuis mendadak", "Besok AS ada kuis", "2020-06-18 13:01:00"),
    Notif("7", null, "Tugas baru Analisis Sains", "2020-06-18 15:07:00"),
    Notif("8", null, "Tugas baru Analisis Ekonom", "2020-06-18 20:04:00")
)