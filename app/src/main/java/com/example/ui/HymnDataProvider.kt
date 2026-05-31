package com.example.data

data class Hymn(
    val id: Int,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false,
    val link: String = "",
    val author: String = ""
)

object HymnDataProvider {
    val hymns = listOf(
        Hymn(
            id = 1,
            title = "Te he notado",
            link = "https://www.youtube.com/watch?v=W7KbOLba7MM",
            author = "",
            content = """I
Te he notado un tanto luchado,
Como que adentro hay un gran vacío
Se ve en tu rostro tu semblante ha cambiado
Como si te hubiesen pedido para ser zarandeado.
No sientes descanso y más te quebrantas,
Te encuentras en el centro de una gran batalla,
Una voz en tu mente te dice que estas sólo,
Pero Cristo está contigo y Él no te dejará.
//Mientras más grande sea tu desierto
Y no te abandones y sigas peleando
Más grande será tu ministerio,
Sólo confía, Él no te dejará.//

II
Te encuentras rodeado y más se agiganta,
Pero ten confianza le darás en la frente,
Allí de rodillas renovaras tus fuerzas
Y tu enemigo caerá delante de ti.
Mientras más grande sea tu desierto
Y no te abandones y sigas peleando
Mase grande será tu ministerio,
Sólo confía, Él no te dejará.
Mientras más grande sea tu desierto
Y no te abandones y sigas peleando,
Más grande será tu ministerio,
Sólo confía, porque Él contigo está."""
        ),
        Hymn(
            id = 2,
            title = "Señor Tú Eres",
            link = "https://www.youtube.com/watch?v=lx_PLqMDnaU",
            author = "",
            content = """//Señor tú eres la persona
Más importante de este lugar//
//Rey de Reyes, Señor de Señores
Tú eres aquel que mi vida cambió//
//Señor tú eres la persona
Más importante de este lugar//
//Rey de Reyes, Señor de Señores
Tú eres aquel que mi vida cambió//"""
        ),
        Hymn(
            id = 3,
            title = "El Camino a Emaús",
            link = "https://www.youtube.com/watch?v=C1QkqMyNnKk",
            author = "",
            content = """Dos de los suyos caminan con gran tristeza,
Van camino a la aldea que está en Emaús,
Un forastero a su lado con ellos camina,
¿Qué pláticas son las vuestras?,
¿Por qué estáis tristes? les preguntó,
Pero no conocieron que era el Señor.

Coro
Quédate, no te vayas, en esta noche ven a posar,
Quédate, forastero, la noche ya viene, el día se va,
Entra en mi aposento si sólo y vacío se encuentra hoy,
Llénalo con tu presencia bendito Señor.
En el camino le cuentan de su tristeza,
Que han matado al Cristo, el Mesías, el Señor,
Y Jesucristo les dice: “era necesario que el Hijo
Del hombre muriera en la cruz por el pecador”,
Y llegaron a la aldea y se quiso marchar."""
        ),
        Hymn(
            id = 4,
            title = "Yo quisiera comprender",
            link = "https://www.youtube.com/watch?v=oSVRINe3ffc",
            author = "",
            content = """Yo quisiera comprender, lo que sufrió por mi Jesús
Tanto desprecio, tanto dolor que en un madero padeció
Aún herido en su costado, el perdonaba mis pecados
Alzó sus ojos y dijo Padre, ellos no saben lo que hacen.

Coro
Oh que triste para aquel, cuando venga mi Señor
Y te diga que no es hora, te has apartado de su amor.
Pero Cristo hoy golpea, él quiere que estés a cuenta
Y no quiere que te pierdas en este mundo a sufrir.
El reloj marcando está, es la hora señalada
Te aconsejo que despiertes de este sueño indiferente,
Quizás será la última hora que esta vida te ofrece
Ven, recíbele a mi Cristo y feliz con el serás."""
        ),
        Hymn(
            id = 5,
            title = "Un milagro",
            link = "https://www.youtube.com/watch?v=H7I9P8VfrXg",
            author = "Conjunto de Dorrego",
            content = """Si tan sólo tocare sus vestiduras,
Dijo alguien que frustrada se sintió,
Su problema era tan grande, pero en Jesús creyó,
Y abriéndose paso en la gente, fue y le tocó.
Cómo esa mujer quiero ser, si tan sólo pudiera creer,
Un milagro hoy recibiría si pudiera creer.

Coro
Un milagro hoy yo necesito, si pudiera creer
Yo iría y le tocaría, y virtud saldría de El
Un milagro hoy tú necesitas, si pudieras creer
Cambiaría la historia en tu vida,
Si tan sólo hoy pudieras creer.
Si tan sólo hoy pudiera vencer yo mis dudas,
Moraría en el ambiente del milagro.
De seguro hoy recibiría, de mi fe Dios se agradaría,
Porque eso es lo que hoy necesito para recibir.
Y si hoy tú preguntas ¿Quién fue, que extendió su mano de fe?
Yo quisiera decirte: “Fui yo, que creí y te toque”."""
        ),
        Hymn(
            id = 6,
            title = "Vaso Nuevo",
            link = "https://www.youtube.com/watch?v=a2aL9-0pwIg",
            author = "",
            content = """Yo quiero ser Señor amado
Como el barro en las manos del Alfarero
Toma mi vida, hazla de nuevo
Yo quiero ser, yo quiero ser
Un vaso nuevo
Yo quiero ser Señor amado
Como el barro en las manos del Alfarero
Toma mi vida, hazla de nuevo
Yo quiero ser, yo quiero ser
Un vaso nuevo"""
        ),
        Hymn(
            id = 7,
            title = "¿Qué está pasando?",
            link = "https://www.youtube.com/watch?v=dxDvnus7e2g",
            author = "",
            content = """I
Que está pasando, hermano mío, cómo has llegado hoy ante Dios,
Te noto triste y confundido, parece que las cosas van peor.
No pienses más en tus problemas, deja que Dios, El obrará
//Aliviará todas tus penas y libertado tú quedarás//

Coro
Recuerda siempre, que hay un camino que fue hecho con su sangre,
Para librarte del pecado y de la muerte,
Si él dio su vida, ¿no te podrá libertar?
Si tu problema se ha hecho grande cómo era aquel gigante,
Recuerda al niño que juntó las cinco piedras,
Tan sólo una al gigante derribó.

II
Cómo has cambiado, hermano mío, ya no eres el mismo de ayer,
Hay algo en ti que te ha vencido y sólo te hace retroceder.
Anímate que su presencia en este día está aquí.
//Clama poder, pide clemencia y al enemigo podrás vencer//"""
        ),
        Hymn(
            id = 8,
            title = "Puro y Santo",
            link = "https://www.youtube.com/watch?v=bqS7J7UyDPc",
            author = "",
            content = """I
Puro y santo quiero ser
Tu voluntad obedecer
Con tu luz enséñame
Con tu amor transfórmame

Coro
//Santo, santo, santo Dios
Oh santo, santo, santo Dios//

II
Grande eres, pequeño soy,
Alabanzas, Señor te doy.
Tú eres rey sobre mi existir
Mi confianza la tengo en ti."""
        ),
        Hymn(
            id = 9,
            title = "Encorvada y Prisionera",
            link = "https://www.youtube.com/watch?v=AYWOD7iRpPs",
            author = "",
            content = """Encorvada y prisionera y de ninguna manera se podía enderezar
Sola y triste y sin consuelo y con la vista hacia el suelo se la podía mirar.
Hasta que la vio Jesús, y llamándola y con voz de autoridad,
Estas palabras habló: Eres libre de tu enfermedad, y el milagro recibió
Eres libre de tu enfermedad, ahora podrás mirar el rostro de quien te sanó,
Eres libre de tu enfermedad, ya no andarás más encorvada
Sino que erguida glorificando siempre a Dios,
Porque en ti hoy se fijó, y con sus manos te tocó, su poder te libertó.
Cuanto tiempo hace que esperas que ligaduras se corten y poderte
enderezar, elevar tu vista al cielo y cantar alabanzas al que tiene autoridad.
Porque un día él te vio y llamándote te ofreció la libertad, Y la
paz él te dio y te dijo: Eres libre, nueva vida tienes hoy.
//Eres libre de tu enfermedad, ahora podrás mirar el rostro de quien te sanó,
Eres libre de tu enfermedad, ya no andarás más encorvada,
Sino que erguida, glorificando siempre a Dios,
Porque en ti hoy se fijó, y con sus manos te tocó su poder te libertó//"""
        ),
        Hymn(
            id = 10,
            title = "El carpintero",
            link = "https://www.youtube.com/watch?v=1hRlIMQRkAY",
            author = "",
            content = """Cargando va el carpintero, en medio de la multitud Una
cruz muy pesada, en su espalda lastimada.
Directo va el hacia el monte, que le llaman La Calavera,
Con sus pies polvorientos, enmudeció y no dijo nada. Con
un beso fue entregado, fue un llamado Iscariote Y con
palos lo golpearon, ni una queja de el salió.
Lo acusaron por pararse en el templo a predicar, Del
amor y la justicia, la paz y la hermandad.
Crucificado está mi Cristo, martilláronle unos clavos.
En sus pies y en sus manos, su costado está sangrando. Está
vivo, no está muerto, con su sangre El me ha salvado Y la
piedra de su tumba está movida, y él no está.
Tres mujeres van corriendo a contarles que lo han visto,
Cristo vive para siempre y muy pronto volverá."""
        ),
        Hymn(
            id = 11,
            title = "Has venido a la orilla",
            link = "https://www.youtube.com/watch?v=FU2kKSxKeAI",
            author = "",
            content = """I
Tú, has venido a la orilla, no has buscado, ni a sabios
Ni a ricos, tan sólo quieres que yo te siga.

Coro
“Señor me has mirado a los ojos, sonriendo has dicho mi nombre,
En la arena he dejado mi barca, junto a ti buscaré otro mar.”

II
Tú, sabes bien lo que tengo, en mi barca, no hay oro
Ni plata, tan sólo redes y mi trabajo.

III
Tú, necesitas mis manos, mi cansancio que a otros descanse,
Amor que quieras seguir amando.

IV
Tú, pescador de otros mares, ansia eterna de almas
Que esperan, amigo bueno, que así te llaman."""
        ),
        Hymn(
            id = 12,
            title = "Te alabaré",
            link = "https://www.youtube.com/watch?v=m4OMXyd-H_E",
            author = "",
            content = """I
Te alabaré, no importa cómo esté.
Te alabaré, en ti yo tengo fe
//Aunque los ojos dejen de brillar,
Aunque la voz se apague y no quiera cantar//

II
Te serviré, no importa cómo este.
Te serviré, en ti yo tengo fe
//Aunque el dolor comience a preguntar,
Aunque la luz se apague y no quiera brillar//

III
Me alegraré en Cristo mi Señor,
Me alegraré en ti mi Salvador.
//Si alguna vez sin fuerza he de quedar,
Tú eres mi fortaleza y tú me harás andar//."""
        ),
        Hymn(
            id = 13,
            title = "La Zarza",
            link = "https://www.youtube.com/watch?v=Opan5-q6bVw",
            author = "",
            content = """I
Quisiera comprender por qué Dios eligió para hablar a Moisés
Aquella humilde zarza plantada en el desierto, habiendo tanta flor.
La voz de su presencia, la llama de su gloria, entre sus hojas ardió
//y parado en una zarza// Al mundo se mostró.

Coro
No puedo imaginar por qué Dios me eligió, si no soy el mejor,
Delante de sus ojos tanto nos parecemos aquella zarza y yo.
//no lo hubiera creído// Que Dios me eligiera a mi
//“Si una zarza pudo arder”// Arder quiero yo también.
///Quémame/// Tan sólo una vez.
///Quémame/// Tan sólo una vez.

II
Hay tanta zarzas tristes a orillas del camino que nadie mira mas.
Los vientos del desierto, la golpearon, la quebraron y casi muerta está,
Pero si vales poco o si no vales nada el cielo no te olvidó,
//aunque seas una zarza // Te sigue mirando Dios."""
        ),
        Hymn(
            id = 14,
            title = "Hasta el enebro",
            link = "https://www.youtube.com/watch?v=lBEGDq0SSWE",
            author = "",
            content = """I
Hasta el enebro llegó Elías, estaba cansado, allí se acostó y se durmió;
Pero el Señor lo despertó, largo camino aún te resta, levántate.

Coro
Levántate, levántate, tú eres mi siervo no estés así, levántate
Levántate, levántate te necesito para mi obra pero de pie.

II
Si en el enebro te has quedado y tus problemas te quitan fuerzas para
trabajar, mira a Jesús, él no se cansó, largo camino aún te resta levántate."""
        ),
        Hymn(
            id = 15,
            title = "Tengo un abogado",
            link = "https://www.youtube.com/watch?v=1gWFbBYRA34",
            author = "",
            content = """Yo tengo un abogado, que me defiende,
Que nunca está ocupado, siempre me atiende
Que nunca pierde un pleito, siempre lo gana
//si a alguno le interesa saber su nombre
Jesús se llama//"""
        ),
        Hymn(
            id = 16,
            title = "Hay Alboroto",
            link = "https://www.youtube.com/watch?v=G6QGgNPuxVI",
            author = "",
            content = """Hay alboroto en las calles es que algo ha pasado,
Veo a la gente corriendo y a muchos gritando.
Padres que buscan sus hijos, tan desesperados,
Parece que aquello que un día se anunció, ha pasado.
Yo no me encuentro en la tierra, yo estoy separado,
Y siento que todo mi ser y mi cuerpo, han cambiado.
Y en este lugar dónde estoy, yo gozo de calma,
Mientras abajo en la tierra, si, gritan las almas.

Coro
//Por qué me he quedado Señor, no sé en qué
Te falle, en que te ofendí, ¿Qué será de mi alma?//
Mi mente y mi capacidad no pueden describir
La gloria y el grande poder que hay en este lugar,
Un trono que está rodeado de miles de ángeles,
Mientras abajo en la tierra se alborotan las calles.
Hay alboroto en las calles es que algo ha pasado,
Veo a la gente corriendo y a muchos gritando.
Padres que buscan sus hijos, tan desesperados,
Parece que aquello que un día se anunció ha pasado.
Despierta, pronto llegará el momento que no habrá en tu vida lamentos.
Cristo nos viene a buscar, procura dar lo mejor de tu vida,
Para cuando llegue ese día el cielo puedas /// conquistar///."""
        ),
        Hymn(
            id = 17,
            title = "Pido perdón",
            link = "https://www.youtube.com/watch?v=tJKwrAy3I4A",
            author = "",
            content = """I
En aquella triste tarde mi maestro padecía,
Por causa de mis pecados por amor a mi moría
En aquella triste tarde su cuerpo se desangraba
Y con su preciosa sangre a la humanidad salvaba.
Fue llevado al matadero según como estaba escrito
Y mataron al cordero, al cordero de Dios bendito
Y fue toda culpa mía, por mi causa, ya lo sé
Y si no es por Jesucristo, mi Dios, ¿Por quién me salvaré?

Coro
Pido perdón por mis insuficiencias, Pido perdón por no saber valorarte,
Pues cuando peco vuelvo a crucificarte
Y aún sin darme cuenta no clamo por tu amor.
Quiero elevar mis manos hacia el cielo Y decirle mi Dios cada mañana
Y por las noches a través de mi ventana
Mirar al firmamento y decirte Que te quiero, que te quiero.

II
Y llegado el tercer día, de una mañana serena
Fue María Magdalena, a la tumba del Señor
Y vio un ángel que decía, ¿Por qué buscas al que vive?
Si Jesús ya no está muerto, con poder se levantó
Según dice el evangelio, Jesús ascendió a los cielos
A sentarse en ese trono, que un día por mí dejó
Y murió crucificado, salvó El mi alma perdida
Y con sus preciosas heridas de la muerte me libró"""
        ),
        Hymn(
            id = 18,
            title = "Yo tengo una casa",
            link = "https://www.youtube.com/watch?v=cYqPbKG6EPQ",
            author = "",
            content = """I
En un pueblo humilde, que yo contemplé,
Vi tanta pobreza que triste quedé,
Pero en un ranchito a un niño escuche,
Con rostro sonriente, cantar muy feliz.

Coro
Yo tengo una casa muy lejos de aquí
Con puertas de perlas y un mar de cristal
Y cuando me vaya más allá del sol,
Veré a Jesucristo que en mi vive hoy.

II
Entonces allí, me puse a llorar,
Más no de tristeza, sino de emoción,
Y junto al niño yo pude cantar,
Pensando en el cielo la misma canción."""
        ),
        Hymn(
            id = 19,
            title = "No temas",
            link = "https://www.youtube.com/watch?v=3A1pOOwyP-E",
            author = "",
            content = """Cuando me encuentro triste siento tu mano que me acaricia,
Cuando me encuentro en pruebas y ya sin fuerzas escucho tu voz.

Coro
No temas oh hijo mío, dura es la lucha, por ti pelearé.
Confía, oh hijo mío porque en mis brazos yo te sostendré.
Recuerda que prometiste, allá en la fuente, seguirle hasta el fin.
Toma hoy la mancera, porque hasta que él vuelva debes trabajar."""
        ),
        Hymn(
            id = 20,
            title = "Por amor",
            link = "https://www.youtube.com/watch?v=FSOPOXqEn3A",
            author = "",
            content = """I
Más tierno que el amor de una madre
Más fragante que las bellas flores
Más hermoso que la risa de un niño
Es tu amor, es tu amor mi Señor

II
Agobiado caminaste en silencio
Y esa cruz lastimaba tus hombros
Tus amigos ya no están a tu lado
Lo sufriste por amor, por amor

Coro
Por amor tú asumiste la muerte de un reo
Por amor tú elegiste el suplicio en la cruz,
Y esos clavos que abrieron las manos y pies,
Fue por amor que diste Señor tu vida en la cruz.

III
Si Satán me ofreciera su reino
Todo cuanto se llame placer
¡A mi Cristo no cambio, en su amor me deleito!
Es tu amor, es tu amor mi Señor."""
        ),
        Hymn(
            id = 21,
            title = "Cristo rompe las cadenas",
            link = "https://www.youtube.com/watch?v=4IFKSYvEGSk",
            author = "",
            content = """///Cristo rompe las cadenas///
Y me da seguridad.
¿Cómo es posible yo vivir sin mi Jesús,
Si el firmamento de mi vida eres Tú?
Tú me salvaste y me libraste de la muerte
¿Cómo es posible yo vivir sin mi Jesús?
///Cristo rompe las cadenas///
Y me da seguridad."""
        ),
        Hymn(
            id = 22,
            title = "Un hombre leproso",
            link = "https://www.youtube.com/watch?v=J2ObP3frGW8",
            author = "",
            content = """I
Un hombre leproso se acerca a Jesús meditando,
Es tanta la pena que lleva por su enfermedad,
He oído que sana a muchos sin nada cobrarles,
He oído que hace el bien dónde quiera que vas,
Por eso te ruego que escuches mi humilde plegaria,
Si quieres Señor límpiame

Coro
///Quiero/// curar tus heridas,
Quiero curar tus heridas, calmar tu dolor
///Quiero/// que tú tengas vida,
Quiero limpiar el pecado de la humanidad.

II
De pronto un poder muy extraño recorre su cuerpo,
No sabe otra cosa que hacer más se pone a llorar,
Sus manos que ayer eran sucias ahora son blancas,
Limpiadas han sido por ese poder celestial,
Por eso mi hermano si ahora te encuentras enfermo,
Ten fe que mi Cristo está aquí y él te quiere sanar."""
        ),
        Hymn(
            id = 23,
            title = "Un día orando",
            link = "https://www.youtube.com/watch?v=61AYhvQU2j4",
            author = "",
            content = """Un día orando le dije a mi Señor
Tú el alfarero y yo el barro soy
Moldea mi vida a tu parecer
Has como tú quieras, hazme un nuevo ser.
Me dijo: ``No me gustas, te voy a quebrantar
Y en un vaso nuevo te voy a transformar,
Pero en el proceso te voy a hacer llorar,
Porque por el fuego te voy a hacer pasar.´´

Coro
//Quiero una sonrisa cuando todo va mal,
Quiero una alabanza en lugar de tu quejar,
Quiero tu confianza en la adversidad
Y quiero que aprendas también a perdonar.//"""
        ),
        Hymn(
            id = 24,
            title = "Te seguiré",
            link = "https://www.youtube.com/watch?v=zwPVkylXBes",
            author = "",
            content = """I
Aunque pruebas tentaren mi vida
Entre luchas yo te voy a seguir.
Y aunque el sol ya no brille más y aquí comience el fin.

Coro
//Te seguiré Jesús, aunque vengan pruebas,
Aunque vengan luchas, yo te voy a seguir//

II
Oh hermano, no te sientas sólo,
Púes Jesús contigo él está.
Ven ahora y en el confía y así seguro estarás.

III
Esos clavos, que traspasaron
Sus manos y también sus pies.
Es por eso que yo te voy a seguir
Y así: conmigo siempre estarás"""
        ),
        Hymn(
            id = 25,
            title = "Ya viene el Señor",
            link = "https://www.youtube.com/watch?v=jKGP55ju4u4",
            author = "",
            content = """I
Ya viene el Señor, todos lo sabemos,
Pero no debemos olvidar la verdad,
Que sin santidad nadie le verá, que allí,
Del pecado ni la sombra entrará.
Una iglesia pura, sin manchas ni arrugas,
Hacia las alturas a encontrar al Señor.
Un pueblo en amor cómo Dios mandó,
Pueblo que le sirva en espíritu y verdad.

Coro
//Ya viene el Señor, no escondas tu talento, Cristo tiene Galardón,
Ya viene el Señor, hoy llena tu vasija del aceite Celestial.//

II
Oye hermano mío que ya no le sirves,
Vuelve nuevamente al primer amor,
Obreros son pocos y la mies es mucha,
Cristo está llamando obreros de valor,
Vuelve a usar los dones que el Señor te ha dado,
Aún hay muchas almas en tiniebla y dolor,
Fuerza hermano ya, hay que trabajar,
Que la noche viene cuando nadie puede obrar."""
        ),
        Hymn(
            id = 26,
            title = "Lo importante es cantar con el alma",
            link = "https://www.youtube.com/watch?v=VXno6E6VitE",
            author = "",
            content = """I
Si tú quieres alabar a Dios tendrás que saber que no basta la voz,
La alabanza que brota de labios, no llega hasta el cielo, no honra al Señor.

Coro
Lo importante es cantar con el alma lavada en la sangre de Cristo el Señor,
Lo importante es darle las gracias con todas las fuerzas de tu corazón.

II
Si tu alma no es santificada si guardas pecados en el corazón,
Es mejor que no cantes entonces sin antes ponerte a cuentas con Dios.

III
Sobre todas las cosas hermosas ponerlo a Jesús en cada canción
Porque él es autor de tú vida si vives si cantas, es por el Señor."""
        ),
        Hymn(
            id = 27,
            title = "Si te visita la tristeza",
            link = "https://www.youtube.com/watch?v=Ixm0EQ7GJ3I",
            author = "",
            content = """I
Si la tristeza hoy visita tu corazón
Y la amargura inundó todo tu ser,
Si te sientes derrotado,
Porque fe no te ha quedado
Jesucristo hoy está mirándote.
Ya no estés triste te ha mirado
Tú amargura él se ha llevado
Y si en algo le has faltado
Esa página ha borrado,
Una nueva tienes hoy por comenzar.

II
Si en la prueba ha faltado tu fe y amor,
Y tus hechos han negado al Señor,
Si te sientes acusado,
Porque sabes que has fallado,
Jesucristo hoy te ofrece su perdón.
Ya no estés triste te ha mirado
tu amargura se ha llevado
Y si en algo le has faltado
Esa página ha borrado,
Una nueva tienes hoy por comenzar.
Ya no estés triste te ha mirado
Tu amargura se ha llevado
Y si en algo le has faltado
En la mar lo ha sepultado
Desde hoy en adelante sedle fiel"""
        ),
        Hymn(
            id = 28,
            title = "El me ama",
            link = "https://www.youtube.com/watch?v=Vhcnq86ujEE",
            author = "",
            content = """Yo tengo un amigo que me ama, me ama, me ama,
Yo tengo un amigo que me ama, su nombre es Jesús.
Que me ama, que me ama, que me ama, sí con tierno amor
Que me ama, que me ama, su nombre es Jesús.
Tenemos un amigo que nos ama, nos ama, nos ama,
Tenemos un amigo que nos ama, su nombre es Jesús.
Que nos ama, que nos ama, que nos ama, sí con tierno amor
Que nos ama, que nos ama, su nombre es Jesús."""
        ),
        Hymn(
            id = 29,
            title = "Yo sé que estás muy cansado",
            link = "https://www.youtube.com/watch?v=ujQFNtpcmeg",
            author = "",
            content = """I
Yo sé que estás muy cansado,
Tus problemas te han vencido,
La angustia que hay en tu alma cada vez te hace llorar.
Estas buscando una puerta que se abra en tu camino,
Pero si escuchas atento hay alguien que te quiere hablar.

Coro
Oye la voz que te dice: “Yo soy Jesús, tu fiel amigo,
Aquel que siempre está contigo cuando todos se van.
Hoy he venido a tu puerta para quitarte la tristeza,
Para llenarte de alegría, llevarte tu soledad”.

II
Ya no sigas escuchando esa voz “Estás perdido”.
No sigas ese camino que te hundes más y más.
Alza tus ojos al cielo, a Aquel que todo lo ha vencido.
Una gota de su sangre hoy te puede libertar"""
        ),
        Hymn(
            id = 30,
            title = "Lávame Señor",
            link = "https://www.youtube.com/watch?v=1THUKHQeXtc",
            author = "",
            content = """I
Aunque todos te dejaren, mi vida daré por ti
Tú mis pies no lavarás Señor, eso me corresponde a mí.
//”Si tus pies yo no lavare” dijo el Señor
“No tendrás parte en mi reino,
Lo que tú hoy no comprendes,
Luego lo comprenderás”//.

Coro
//No sólo mis pies, también mis manos,
Mi cabeza también, lávame Señor,
Necesito que me limpies,
Necesito que me limpies//.

II
Muchas veces como Pedro, también te negué Señor
Y he llorado amargamente al sentirme lejos de tú amor,
// Pero tus ojos tiernos me miraron y me dieron perdón,
Sólo por tu amor que es grande hoy estoy aquí Señor//."""
        ),
        Hymn(
            id = 31,
            title = "Sigo de pie",
            link = "https://www.youtube.com/watch?v=MUKmawOmI78",
            author = "Conjunto de Dorrego",
            content = """I
El dolor que hay en mí, el que me hace llorar, Y
hasta me hace creer que a mi lado no estas.
Necesito sentir de tu mano el calor, Tu
presencia en mí me infunde valor,
Porque debo seguir esperando
Que un día del cielo mi respuesta vendrá, El
dolor y mi angustia allí terminarán.

Coro
Porque sigo de pie, hasta el alba yo lucharé,
Yo no te dejaré, Quiero oír esa voz: ” hoy te bendeciré”.
Tu rostro quiero mirar, mi alma libre al fin será,
Ya la noche pasó y el sol alumbró puedo verte Señor.
Porque sigo de pie.

II
Ya el triunfo alcancé, pues Jesús me ayudo,
Nuevas pruebas tendré y sé que yo venceré.
Necesito tener de tu mano el poder, Y
tu mano tomar y poder avanzar,
Porque debo seguir el camino
Que Dios ha trazado, terminar mi carrera
Y aunque vengan las pruebas lucharé hasta el final."""
        ),
        Hymn(
            id = 32,
            title = "Si vivo",
            link = "https://www.youtube.com/watch?v=KNWz4Ie5dzw",
            author = "Conjunto de Dorrego",
            content = """I
Si vivo, porque tú vives, soy salvo, pues tú me salvaste.
Si canto, tú me diste alegría y si ando, porque me levantaste.

Coro
Es que tengo mil razones, que declaran tu grandeza.
Lo que soy y lo que tengo, te lo debo a ti.
Te lo debo a ti, que viniste a morir para darme alegría,
tú cargabas la cruz, tú sufrías por mí, tú pensabas en mí,
Tú viniste a cambiar mi tristeza en canción y mis noches en día,
Si yo tengo un lugar, si me espera un hogar, te lo debo a ti.

II
No tengo recuerdos amargos, porque todos Señor por amor lo has llevado,
Aquello que fue, en mi vida es historia, de tu fidelidad, sólo tengo memoria."""
        ),
        Hymn(
            id = 33,
            title = "A veces en pesar",
            link = "https://www.youtube.com/watch?v=esNwU2EBDPw",
            author = "",
            content = """I
Asediado voy por el camino, en diversas pruebas y aflicción,
Apartarme quieren de la senda que me lleva a estar con mi Señor.
No le temo a las pruebas del camino que me hieren como espino al pasar,
No podrán apartarme de la senda así tenga que luchar hasta el final.

Coro
A veces en pesar, a veces en dolor,
A veces en desierto tengo que caminar,
Pero no me detengo, camino sin cesar,
Adonde está Jesús, yo tengo que llegar.

II
Luchas y pruebas forman parte de la senda,
Cual espino en el camino siempre están,
Y por más que yo quiera eludirlo, es en vano, no lo puedo evitar.
Al final del camino él me espera, cuando llegue, su hermosura yo veré,
Y me hará olvidar todas mis penas, que en la senda yo tuve que pasar."""
        ),
        Hymn(
            id = 34,
            title = "Te preguntas ¿Por qué?",
            link = "https://www.youtube.com/watch?v=0qGxzgKDjr0",
            author = "",
            content = """I
Te preguntas ¿Por qué? Dios permite que a tu vida llegue la aflicción,
Por qué, a tu alma la ha inundado la angustia y el dolor,
Parece un camino de derrotas, no encuentras explicación,
Más si el Señor al mundo venció, tú eres más que un vencedor.

Coro
Cuando al Calvario Él ascendió, parecía derrota, pero resucitó
Y al tercer día de aquella tumba se levantó.
Quizás te sientas también derrotado, pero no llores, querido hermano,
Ya viene el día en que la iglesia volará, entonces será la gran desilusión
Del diablo que se cree un gran vencedor, él es un perdedor,
Jesús lo venció y a mí me salvó.

II
No preguntes ¿Por qué? El Señor hoy ha venido y te quiere responder,
No ves, la razón que de tus lágrimas la sabe solo él,
Cuando tú piensas que estás sólo, no te abandona el Señor,
Si el de la muerte se levantó, te levantará a ti hoy."""
        ),
        Hymn(
            id = 35,
            title = "Si en Esta Vida",
            link = "https://www.youtube.com/watch?v=gdCzkH5miDM",
            author = "",
            content = """I
Si en esta vida me ha tocado la pobreza
Y si en el mundo un lugar no tuve yo

//Qué más me da la sociedad, yo soy feliz junto a mi Dios,
Tengo su paz dentro de mí, y eso me basta//.

Coro
Y si tuviera que llorar, hasta que tenga que morir,
Yo esperaré en Aquel que dijo: “Yo muy pronto volveré”,
Yo sé que un día volverá, y al fin mis ojos le han de ver,
A Aquel que se inmoló en la cruz, por mis pecados.

II
En mi camino muchas veces me he quedado,
También la muerte mi morada visitó,

//Pero Jesús, mi amigo fiel, su mano nunca me dejó,
Si está conmigo hasta el final, eso me basta//."""
        ),
        Hymn(
            id = 36,
            title = "Pertenecer a Cristo",
            link = "https://www.youtube.com/watch?v=zqVWZhL1ccQ",
            author = "",
            content = """I
En las noches largas o en el frío invierno,
Hay un compañero que a tu lado está,
Cuando el sol calienta o en la cruel tormenta
Este compañero no te va a dejar.

Sana tus dolencias, perdona tus faltas
Y de tentaciones te sabrá librar.

Coro
¡Oh! Qué lindo es pertenecer a Cristo
¡Oh! Qué lindo es pertenecer al Señor.

Pronto las noches pasarán, también las pruebas cesarán,
Pero entre tanto quiero yo con Él estar.

Con todos quiero compartir que soy feliz junto a mi Rey,
Que ya no puedo yo vivir sin mi Jesús.

II
El pan cotidiano Dios pondrá en tu mesa,
Tus necesidades Él las suplirá.
Mil caerán a tu lado, diez mil a tu diestra,
Pero a tu vida no podrá llegar.

Todos tus cabellos los tiene contados,
Y hasta el fin del mundo con nosotros Él está."""
        ),
        Hymn(
            id = 37,
            title = "Sonríe que Jesús te Ama",
            link = "https://www.youtube.com/watch?v=KW5qTPkDoWg",
            author = "",
            content = """//Sonríe que Jesús te ama, sonríe que Jesús te quiere,
Sonríe que Jesús te da la vida, sonríe a Jesús de Nazaret//

///Canta, canta para Él,
Porque Él es Dios, Él es el Rey//"""
        ),
        Hymn(
            id = 38,
            title = "Puedo Sentir",
            link = "https://www.youtube.com/watch?v=MJgBMQEBpXE",
            author = "Conjunto de Dorrego",
            content = """//Cuando tú estás aquí, veo tu mano,
Veo tu mano obrar en mí//

//Los cojos andan, los mudos cantan,
Los ciegos ven cuando tú estás aquí//

Puedo sentir en el dolor,
Tu presencia que camina junto a mí,
Una voz que me dice yo contigo estoy,
Y una fuerza me ayuda a continuar.

Puedo sentir tu protección,
Cuando males me atacan sin cesar
Y mi vida el enemigo quiere derribar,
Tu presencia me ayuda a continuar.

Quieres sentir en el dolor
Esa presencia que camina junto a ti
Esa voz que te dice yo contigo estoy,
Ya no tardes abre hoy tu corazón.

Señor, has salvado mi alma
Qué gran precio has pagado por mí
Hoy mi vida en este humilde canto
Se humilla ante Ti mi Jesús.

Señor, sé que me has perdonado
Ya no quiero ser el mismo de ayer
Hoy mi vida en este humilde canto
Se humilla ante Ti mi Jesús.

Si debo pasar por las aguas
Las aguas no me anegarán
Si debo pasar por el fuego
La llama en mí no arderá.

Así dice tu palabra,
Pero cuesta Señor continuar

//Pues temo que el fuego me queme y que el agua me ahogue
Y no pueda triunfar//

Ya siento en mí ese fuego
Ya siento esa llama obrar
Pero no se quema mi cuerpo
Mis cargas aliviando está.

Señor ya parece que veo
Tu presencia que a mi lado va

//Señor no me dejes, Señor no te apartes
Contigo yo voy a triunfar//

//Ardiendo el fuego en mi alma está//
Gloriosa llama me limpiará
Oh! Aleluya mi alma ardiendo está.

Oh! Señor quiero que ardas en mi ser
Como una zarza quiero arder con tu poder
A los perdidos ganaré como señal
Que estoy ardiendo con el fuego celestial.

Quiero alabarte y adorarte sólo a Ti
Como se adora en espíritu y verdad

//Oh! Señor quiero que ardas en mi ser
Como una zarza quiero arder con tu poder//"""
        ),
        Hymn(
            id = 39,
            title = "Por Ti",
            link = "https://www.youtube.com/watch?v=gmirWSO5Brs",
            author = "",
            content = """Cómo no cantarte a Ti?
Cómo te voy a negar?
Si en mis horas de dolor, siempre estuviste allí
Te busqué y no te escondiste,
Te llamé y me respondiste,
Y si no fuera por Ti, ¿Qué sería de mí?

//Por Ti estoy de pie,
Por Ti, oh, por Ti viviré
Por Ti se sostiene mi fe,
Por Ti venceré.

Por Ti estoy de pie,
Por Ti, oh, por Ti viviré
Y no hay dudas que por Ti llegaré.
Por Ti venceré//"""
        ),
        Hymn(
            id = 40,
            title = "Dame Más Sabiduría",
            link = "https://www.youtube.com/watch?v=op9o20ZIGKY",
            author = "",
            content = """I
Señor manifiesta tu poder
Haz que vuelva a renacer en mi corazón,
Tú que hiciste los cielos y la tierra,
Le diste paz y gozo a mi corazón.

Coro
Señor dame más sabiduría para que yo cada día
Pueda así con alegría alabarte a Ti Señor.

Señor, bendito sea tu nombre,
//Tu nombre sea bendito y glorificado por la eternidad//.

II
Señor tú eres mi protector,
Gloria sea al Salvador por su amor,
Manifiesta tu poder santificado,
Para que yo postrado, adore ante tus pies."""
        ),
        Hymn(
            id = 41,
            title = "Mi Razón de Cantar",
            link = "https://www.youtube.com/watch?v=uc3YiEmfpHs",
            author = "",
            content = """I
No existe palabra en el mundo
Con la que yo pueda expresar
La inmensa gratitud que siento tan solo al cantar.

La gente le canta a la vida
Y entona canciones de amor
Yo quiero agradecer por el canto de mi voz.

Coro
Y elevo mi canción como un ave que puede volar,
Tan lejos hasta llegar al altar de mi Dios,
Y sé que hasta el final Dios será mi razón de cantar
Pues mi voz es talento que Dios me ha querido entregar.

II
No canto al placer de esta vida,
Ni alabo lo que es vanidad,
Yo llevo en mi canción salvación para la humanidad,
Cantando voy muy alegre y aquel que me quiera escuchar
Tendrá que saber que a Jesús solo quiero alabar."""
        ),
        Hymn(
            id = 42,
            title = "Adelante Peregrino",
            link = "https://www.youtube.com/watch?v=VlT_cGUnDkQ",
            author = "",
            content = """I
No mires hacia atrás oh peregrino, avanza hacia delante
Aunque el viento contrario te quiera detener,
Si hay nubes en tu cielo, te hieren las espinas
Y el sol tras la colina pareciese ocultar.

No mires hacia atrás oh peregrino,
La cuesta más pesada
Y el camino más duro ya Cristo caminó para ti.

Coro
Adelante peregrino, ve cantando con Él en tu camino
Con Cristo a tu lado pronto llegarás.

Alza tu vista hacia la cruz y mírale a Jesús
Y la ciudad que Él preparó tú puedes contemplar

//Un cielo de esplendor creado con amor,
La ciudad que Él preparó//

II
No mires hacia atrás oh peregrino,
Escapa por tu vida
Porque a este mundo malo le llega ya su fin,
Sus lujos y placeres, encanto y diversiones,
No mires más sus obras porque perecerán.

No mires hacia atrás oh peregrino,
Recuerda la mujer de Lot
Frente a Sodoma se convirtió en estatua de sal."""
        ),
        Hymn(
            id = 43,
            title = "Un Paso de Fe",
            link = "",
            author = "",
            content = """I
Un paso de fe con determinación puede cambiar tu vida,
Un paso de fe, una fiel decisión,
Es todo cuanto tú hoy necesitas.

Un paso de fe, es un paso hacia Dios,
Un paso hacia adelante,
Por qué desesperar si Dios vuelve a decir:
“Di al pueblo que marche”.

Coro
Ya basta de ¡Ay! Es hora de cantar
Ya basta de fracasos se acercan bendiciones
Da un paso de fe, es tiempo de victoria.

II
Da un paso de fe,
Si todo en derredor parece derrumbarse,
Da un paso de fe,
Será el comenzar para una vida llena de victoria.

Da un paso de fe, en tu debilidad,
Que Dios dará su paso;
Y así comprenderás que el justo por la fe,
Por la fe vivirá."""
        ),
        Hymn(
            id = 44,
            title = "No Preguntaré ¿Por Qué?",
            link = "https://www.youtube.com/watch?v=j5H4JQ-KUc0",
            author = "",
            content = """I
Hay muchas cosas que quizás aquí nunca sabré,
Misterios hay que no podré tal vez nunca entender,
Interrogantes que la vida me plantea al transitar.

//Allá en su reino celestial las mil respuestas obtendré
Y para siempre adoraré al Salvador//.

Coro
Lo que el Señor me dio, lo que no me quiso dar
Todo aquello que Él ha permitido lo debo aceptar.

//No preguntaré ¿Por qué?
No cuestionaré su obrar cuando
Llegue al final del camino lo habré de entender//.

Cuando se sufren las secuelas de una ausencia cruel
O aquel gigante que tememos, vuelve a aparecer,
Cuando doctrinas tan extrañas el evangelio quieren cambiar

//Y pareciera imponerse la injusticia sobre el bien
Vale la pena recordar que escrito está//"""
        ),
        Hymn(
            id = 45,
            title = "La Palmera",
            link = "https://www.youtube.com/watch?v=5KdVGDe1CSc",
            author = "",
            content = """Me parece increíble que aún no te hayas cansado,
Has vuelto a levantarte con furia contra mí.
Tienes mala memoria parece que no recuerdas
Saliste mal herido la última vez.

Me parece mentira, que aún te queden fuerzas,
Y como un viento recio me vienes a golpear,
Pero yo tengo toda la armadura
Y sobre la roca, edifiqué mi hogar,
Porque yo tengo toda la armadura
Y sobre la roca edifiqué mi hogar.

Tengo que advertirte, soy como la palmera,
Después que pasa el viento me vuelvo a enderezar,
Y por si lo olvidaste, yo no me encuentro solo
Porque está conmigo el ángel de Jehová.

Yo no tengo lucha contra sangre y carne
Tú eres mi enemigo te voy a derribar,
Porque yo tengo toda la armadura
Y sobre la roca edifiqué mi hogar."""
        ),
        Hymn(
            id = 46,
            title = "Su Gracia es Mayor",
            link = "",
            author = "",
            content = """I
Su gracia es mayor si las cargas aumentan,
Su fuerza es mayor si la prueba es más cruel,
Si es grande la lucha, mayor es su gracia,
Si más son las pruebas, mayores su paz.

Coro
Su amor no termina, su gracia no acaba,
Un límite no hay al poder de Jesús,
Pues en sus inmensas riquezas en gloria
Abundan sus dones, abunda su amor.

II
Si nuestros recursos se han agotado,
Si fuerzas nos faltan para continuar,
Si a punto estamos de desanimarnos,
La hora ha llegado en que Dios obrará."""
        ),
        Hymn(
            id = 47,
            title = "Un Día a la Vez",
            link = "https://www.youtube.com/watch?v=CT4h3nfnDuk",
            author = "",
            content = """I
Necesitado me encuentro Señor.
Te ruego, Señor yo quiero saber lo que debo hacer,
Señala el camino que debo seguir.
Señor, por mi bien, yo quiero vivir un día a la vez.

Coro
Un día a la vez, mi Cristo,
Es lo que pido de ti,
Te ruego Señor yo quiero saber lo que debo hacer,
Ayer ya pasó, mi Cristo,
Y mañana quizás no vendrá,
Señor por mi bien yo quiero vivir un día a la vez.

II
Ya tú viviste entre los hombres,
Y sabes Señor, que hoy todo es peor,
Hay mucho dolor,
Hay gran violencia y mucha maldad,
Señor, en tu luz yo quiero vivir un día a la vez."""
        ),
        Hymn(
            id = 48,
            title = "Calmó la Tempestad",
            link = "https://www.youtube.com/watch?v=V-uPAmM79Dg",
            author = "",
            content = """I
El mar está tranquilo y la barca esperando
A un grupo muy antiguo, que sale a navegar,
Van rumbo a la otra orilla a tierra gadarena,
Cumpliendo su faena que trae libertad.

Discípulos de Cristo son los que allí navegan,
Él se quedó dormido y en medio de la mar
La barca es sacudida en forma tan violenta
Por las olas y el viento que la hacen tambalear.

Vamos a naufragar gritaban por el miedo
Jesús auxílianos y en respuesta le vieron
Alzó su voz al viento y calmó la tempestad.

II
Hoy navega esta barca, Jesús va con nosotros,
Por más que haya tormenta nunca naufragará,
Él tiene los dominios sobre el cielo y la tierra
El barco va seguro, Él es su capitán.

El mar es este mundo y la barca es la iglesia
Jesús ha prometido que la vendrá a buscar,
Y allá en la otra orilla con Él nos gozaremos
Sigamos navegando este tramo final.

Hombres de poca fe hoy sigan navegando
Nunca los dejaré sigan en mí confiando
Por más que haya tormenta nunca naufragarán."""
        ),
        Hymn(
            id = 49,
            title = "Divino Compañero",
            link = "https://www.youtube.com/watch?v=OJQz5YOKr0w",
            author = "",
            content = """I
Divino compañero del camino,
Tu presencia siento yo al transitar.
Cristo ha disipado toda sombra
Ya tengo luz, la luz divina de su amor.

Coro
Quédate Señor, ya se hace tarde,
Te ofrezco el corazón para posar,
Hazlo tú morada permanente,
Acéptalo, acéptalo mi Salvador.

II
Las sombras de la noche se aproximan,
Y en ellas el tentador acechará,
No me dejes solo en el camino,
Ayúdame, ayúdame hasta llegar."""
        ),
        Hymn(
            id = 50,
            title = "¿Has Perdido la Confianza?",
            link = "https://www.youtube.com/watch?v=36YaCTWQcy4",
            author = "",
            content = """I
¿Has perdido la confianza que pusiste en el Señor?
¿Y las fuerzas ya te faltan porque es grande tu dolor?

//Recuerda que Jesús nunca te desamparó y
En las pruebas que tuviste Él te ayudó//

II
¿Has dejado de orar y no cantas al Señor?
¿Tienes una piedra donde hubo un tierno corazón?

//¿Has perdido el gozo santo del Espíritu de Dios?
¿Qué has hecho del talento que Él te dio?//

III
Mas tus lágrimas están en las manos del Señor,
Tu trabajo y tu esfuerzo no fue vano para Dios.

//Lo que esperas recibir ten paciencia
Lo tendrás si te humillas al Señor de corazón//.

IV
Aunque todos te dejaren, Él de ti se acordará
Y en la prueba más difícil, el Señor te sostendrá.

//Nuevamente deposita tu confianza en el Señor,
Pues solo en Él encontrarás consolación//."""
        ),
        Hymn(
            id = 51,
            title = "Esa Zarza",
            link = "",
            author = "",
            content = """//Esa zarza que en el monte no cesaba de arder//
Esa zarza era la gloria en la presencia de mi Dios

//Esa zarza quiero que arda dentro de mi corazón//

//Esa zarza que en el monte no cesaba de arder//
Esa zarza era la gloria en la presencia de mi Dios

//Esa zarza quiero que arda dentro de mi corazón//

//Fuego, fuego, fuego, fuego que no se apaga
Fuego, fuego, fuego, fuego celestial//

//Fuego que abraza al alma, fuego que santifica,
Fuego que purifica dentro de mi corazón//

//Fuego, fuego, fuego, fuego que no se apaga
Fuego, fuego, fuego, fuego celestial//

//Fuego que abraza al alma, fuego que santifica,
Fuego que purifica dentro de mi corazón//"""
        ),
        Hymn(
            id = 52,
            title = "Bellas Mansiones",
            link = "https://www.youtube.com/watch?v=jKm5US0LQmY",
            author = "",
            content = """I
Mansión gloriosa tengo allá en el cielo
Do las maldades nunca entrarán
Toda tristeza cambiará en consuelo,
Y en dulce canto, el dolor y afán.

Coro
Bellas mansiones, hay allá en la gloria,
Tendré la mía, el gozo sin par.
Suenan las notas de la grata victoria,
Voy pues con gozo a mi dulce hogar.

II
Amigo mío, cuánto anhelo yo verte
Libre de penas y de turbación,
A Jesucristo debes ya entregarte,
Tendrás también, una bella mansión."""
        ),
        Hymn(
            id = 53,
            title = "Siempre Estuviste Aquí",
            link = "https://www.youtube.com/watch?v=tfzsoRyZJ9E",
            author = "",
            content = """I
A tus plantas vengo hoy, anhelo que vengas a mí;
Necesito solo un momento volverte a sentir, mi Señor.

Me alejé sin saber que esperando estabas por mí
Y a pesar de mi error, siempre has estado ahí,
Nunca has fallado Señor.

Fuiste Tú quien rompió las cadenas,
Es tu amor que cambió mi existencia
Y me libertó de las tinieblas que me alejaban de Ti.

Coro
But siempre estuviste aquí, no me olvidaste;
Jamás me has dejado, Señor, me amaste a mí.

Si en algo te sirvo Señor, toma mi vida
A cada instante,
Mi Dios, te quiero servir,
Solo a Ti, mi Señor.

II
Lo que yo soy es gracias a tu amor;
Has tomado mi vida sin merecerlo,
Me has elegido, no me sueltes por favor
De tus manos, ¡Oh, mi Señor!

En tus brazos quiero estar,
Poder abrazarte, cuando no pueda seguir;
Eres Tú quien camina a mi lado,
Me alienta y pude encontrar
Las fuerzas que desgastado había,
Para alejarme de Ti.

Coda
//Siempre estuviste aquí//
Siempre has estado aquí, Señor."""
        ),
        Hymn(
            id = 54,
            title = "Tu gracia en mí Señor",
            link = "https://www.youtube.com/watch?v=ZMydUsfv7jU",
            author = "",
            content = """I
Como poder alcanzar en un mundo
Tan confuso en el cual nadie piensa en Dios,
Si un instrumento imperfecto yo soy, el bien que quiero no hago tal vez.
Que deberemos hacer para poner en práctica todo el obrar del Señor,
Solo cuando el gran tesoro en vasos de barro esté.

Coro
Tú gracia en mí, Señor, me bastará, me fortalecerá.
Y tu poder ¡Oh Dios! Se perfecciona Y aumenta en mi debilidad.

II
Has escogido Señor lo menospreciado, lo débil, lo necio y lo vil.
Para que nadie se jacte ante ti, toda la gloria te demos a ti.
Como la zarza que ardía y no se consumía allá sobre el monte de Horeb.
Así también a tu siervo, alcánzale de tu poder."""
        ),
        Hymn(
            id = 55,
            title = "La iglesia sigue caminando",
            link = "",
            author = "",
            content = """//En la lucha y en la prueba la iglesia sigue caminando//
Sólo se detiene para predicar.
//Por los valles y montañas la iglesia sigue caminando//
Sólo se detiene para predicar.
//Oh, ¡Gloria, Aleluya! la iglesia sigue caminando//
Sólo se detiene para predicar."""
        ),
        Hymn(
            id = 56,
            title = "Renuévame",
            link = "https://www.youtube.com/watch?v=_ltw2y1Y9Yo",
            author = "",
            content = """//Renuévame, Señor Jesús, ya no quiero ser igual
Renuévame, Señor Jesús, pon en mí tu corazón.
Porque todo lo que hay dentro de mí, necesita ser cambiado Señor
Porque todo lo que hay dentro de mi corazón, necesita más de ti.//"""
        ),
        Hymn(
            id = 57,
            title = "Venid, subamos",
            link = "https://www.youtube.com/watch?v=GlSylMPWbx0",
            author = "",
            content = """Su camino nos enseñará, y andaremos en él
Porque de Sion la ley saldrá y la palabra de Dios de Jerusalén.
//Venid subamos al monte de Jehová, a la casa de nuestro Dios//"""
        ),
        Hymn(
            id = 58,
            title = "Salvación",
            link = "",
            author = "",
            content = """I
Fui hecho justo sin merecerlo
Mi culpa ya cubierta está
Toda mi deuda quedó saldada
En la persona del cordero inmortal

Coro
Salvación, camino angosto
Plenitud incomparable don de Dios
Una cruz cubierta en sangre
Fue necesaria para nuestra redención

II
Regenerado y transformado
Cambio mi mente y mi forma de actuar
Hoy solo anhelo servir a Cristo
Y ser la luz, la sal, un claro manantial

III
Santificado soy por Jesucristo me acerco a El y hoy tengo más sagraciación
El enemigo ¡Ya está vencido! Y hoy damos gloria a Dios por que él triunfó"""
        ),
        Hymn(
            id = 59,
            title = "Señor, ¿Quién entrará?",
            link = "https://www.youtube.com/watch?v=Th-85kBkr4s",
            author = "",
            content = """I
//Señor, ¿Quién entrará en tu santuario para adorar//
//El de manos limpias y un corazón puro, Y sin vanidades, que sepa amar//

II
//Señor, yo quiero entrar en tu santuario para adorar//
//Dame manos limpias y un corazón puro, Y sin vanidades enséñame a amar//

III
//Señor, yo puedo entrar en tu santuario para adorar//
//Pues tu me has limpiado, me has perdonado, Tu Espíritu Santo, me llena de amor//"""
        ),
        Hymn(
            id = 60,
            title = "Necesito de ti",
            link = "https://www.youtube.com/watch?v=Nuaq1vbn83U",
            author = "Conjunto de Dorrego",
            content = """Muchas veces me pregunté qué quieres de mí Señor,
Si estando en luchas y pruebas mi vida, quise seguir.
But no pude Señor, soy muy débil, necesito de ti
Y ahora no sé qué debo cambiar para poder continuar.

Coro
Necesito de ti para seguir, yo quiero alabarte
También adorarte, quiero seguir.
Necesitas de mí, debes seguir, Yo quiero limpiarte, también perdonarte,
Quieres seguir, yo estoy junto a ti.

Ahora comprendo Señor, que quieres de mí,
Pues no entendía cuanto me querías, hoy te siento en mí, y ahora puedo continuar mi vida, junto a ti.
Señor me has librado, también perdonado y hoy puedo seguir."""
        ),
        Hymn(
            id = 61,
            title = "Hoy te quiero alabar",
            link = "https://www.youtube.com/watch?v=Rzk3PUoON_c",
            author = "",
            content = """I
Hoy te quiero alabar Señor
Hoy te quiero adorar a Tí
Y te quiero decir Señor
Que lo más lindo eres tú.

Coro
//Deja que este canto suba
Al cielo en dulce voz,
Deja que este canto
Llegue a ti Señor//

II
Hoy te quiero dar gracias Dios
Hoy te quiero yo exaltar
Hoy te quiero decir Señor
Que te amo de corazón"""
        ),
        Hymn(
            id = 62,
            title = "El Apóstol Pablo",
            link = "",
            author = "Conjunto de Dorrego",
            content = """I
El Apóstol Pablo tenía en su carne un aguijón,
Éste afligía, llenaba de angustia su corazón,
Un día orando, pidiéndole al cielo por liberación,
Dios le habló y a Pablo le dijo: “Bástate mi gracia”

Coro
La gracia de Dios, nos era inmerecida,
But cuando Jesús murió en esa cruz, su gracia me alcanzó,
Hoy podemos gozar de salvación eterna,
Gracia abundante, llena de vida, fuerza y poder.
Hoy danos poder, como en pentecostés,
Desata cadenas, levanta al caído, liberta al oprimido,
Hoy llena mi ser, hoy quiero vencer,
Yo quiero amar, saber perdonar, vivir en santidad,
Dios quiere una iglesia sin manchas ni arrugas
Llena de gloria como en Pentecostés,
Dios quiere una iglesia sin manchas ni arrugas,
Él quiere a su esposa llena de poder.

II
Quizás en este día te sientes como Pablo con un aguijón,
Tal vez es un problema cargado de angustia y desolación.
No olvides que el cristiano, debe en su camino cargar un dolor,
Porque por la gracia en la debilidad, está la perfección."""
        ),
        Hymn(
            id = 63,
            title = "Dicen que no existes",
            link = "https://www.youtube.com/watch?v=GBWT6JTnovI",
            author = "",
            content = """//Dicen que no existes, que no eres realidad,
Pero estás vibrando dentro mío una vez más,
Obras en tu pueblo con amor y santidad
Y te muestras más y más//"""
        ),
        Hymn(
            id = 64,
            title = "¿Quién me apartará?",
            link = "https://www.youtube.com/watch?v=aZx02gMwuss",
            author = "Conjunto de Dorrego",
            content = """I
Yo sé que debo aceptar aquello que has decidido
Yo sé que debo seguir, yo sé que debo seguir.
Yo sé que tu gracia me basta y que debo seguir avanzando
Y todo lo que has permitido, vendrá en beneficio de mí.
¿Quién me acusará si Dios me justifica?
¿Quién condenará si Cristo intercede por mí?
¿Quién intentará interponerse en mi camino?
Si Dios es conmigo, ¿Quién contra mí?

Coro
¿Quién me apartará del amor de Jesucristo?
¿Tribulación, angustia o desnudez?
Ni la muerte, ni la vida, ni lo presente, ni lo porvenir,
Nada de esto podrá separarme del amor de mi Dios.

II
Yo sé que el consuelo vendrá cuando llegue al final del camino,
Poder con mis ojos mirar aquello que has prometido.
Estar con aquellos que un día al irse dejaron tristeza,
Con ellos será mi alegría, contigo Señor que grandeza.
Yo quiero llegar a ver tu gloria eterna,
Llegar al final, sentir que me llamas Señor.
¿Quién intentará interponerse en mi camino?
Si Dios es conmigo ¿Quién contra a mí?"""
        ),
        Hymn(
            id = 65,
            title = "Cantad al Señor",
            link = "https://www.youtube.com/watch?v=Fqo8g5b5AOE",
            author = "",
            content = """///Cantad al Señor un cántico nuevo///
Cantad al Señor, cantad al Señor
///Porque nos mostró grandes maravillas///
Cantad al Señor, cantad al Señor
///Porque Él ha hecho y hará maravillas///
Cantad al Señor, cantad al Señor
///Cantad al Señor un cántico nuevo///
Cantad al Señor, cantad al Señor"""
        ),
        Hymn(
            id = 66,
            title = "Él vive",
            link = "",
            author = "Conjunto de Dorrego",
            content = """I
Aunque solo lo dejaron, él vive.
A pesar de la traición, él vive
A pesar de los azotes, él vive.
Aunque el pueblo no lo eligió, él vive
Aunque duro fue subir, él vive.
Con la cruz destrozando sus hombros, él vive.
A pesar de los clavos, él vive
Y esa lanza que abrió su costado, él vive.

Coro
Y yo cantaré y cantaré, que él vive.
No me cansaré de cantar, que él vive.
Triunfó la vida sobre la muerte,
Pudo el amor más que el dolor,
Y porque él vive, yo puedo decir, que vivo también.

II
Aunque el mundo lo que niegue, yo sé que él vive,
Su obrar entre su pueblo me dice, que él vive,
Al cautivo cantar y al derrotado triunfar, él vive
El pecado limpiar y al perdido ganar, él vive."""
        ),
        Hymn(
            id = 67,
            title = "Grandes son tus obras",
            link = "https://www.youtube.com/watch?v=yP6exbnOFIE",
            author = "",
            content = """I
Grandes, maravillosas son tus obras, Señor, Dios, todopoderoso,
Justos y verdaderos tus caminos ///Rey de los santos///
Quién no te temerá ¡Oh Señor! Y glorificará tu nombre, pues sólo tú
Eres Santo, por lo cual todas las naciones vendrán //y te adorarán//

II
Grandes, maravillosas son tus obras, Señor, Dios, todopoderoso,
Justos y verdaderos tus caminos ///Rey de los santos///
Temed a Dios y dadle gloria, porque su juicio ha llegado
Y adorad a aquel que hizo el cielo, la tierra y el mar
Y las fuentes de las aguas //amén, amén, amén, amén//"""
        ),
        Hymn(
            id = 68,
            title = "Todo cambió",
            link = "https://www.youtube.com/watch?v=G8jvmsqZlfU",
            author = "Conjunto de Dorrego",
            content = """I
Todo cambió, en ese día, yo derramé mi alma a Dios y mi corazón.
Todo cambió cuando yo fui a Él, con amargura del alma lloré y le clamé,
Si te dignares de mí y miraras mi aflicción,
Si te acordares de mí y no olvidaras que sierva soy,
Si tú me dieres hoy lo que yo anhelo aquí, yo cantaré así.

Coro
Todo cambió, ya no hay tristeza en mí, Dios me escuchó, Dios se acordó de mí,
Y esa amargura que había en mi alma, Dios la cambió en dulce calma, no hay aflicciones,
Solo alegría, solo alegría.

II
Puede cambiar si tú le clamas hoy
Y derramas tu alma a Dios y tu corazón.
Puede cambiar si no escucharas
A aquel que te acusa sin ver tu gran aflicción.
Entonces dirás yo soy aquella mujer
Que atribulada fui Dios se acordó de mí,
Lo que a Dios pedí, Él me lo dio aquí, por eso canto así."""
        ),
        Hymn(
            id = 69,
            title = "¿Sabes tú de Cristo?",
            link = "https://www.youtube.com/watch?v=oT26MWGJpyA",
            author = "",
            content = """I
Vives cansado y triste, es grande tu aflicción,
Tu sed calmar quisiste, buscas feliz protección.

Coro
¿Sabes tú de Cristo, le conoces ya?
En su amor bendito, salvación y poder te dará.

II
¿A quién te acercas, dime, cuando te acosa el mal?,
Y cuando tu alma gime, ¿Quién es tu paz eternal?

III
En tus desilusiones, tu llanto enjugará,
En rudas tentaciones, tu ruego contestará."""
        ),
        Hymn(
            id = 70,
            title = "¿Cuál es tu enemigo?",
            link = "",
            author = "Conjunto de Dorrego",
            content = """Cuál es la lucha que enfrenta tu vida,
Y cual el enemigo que te hace sufrir,
Escucha bien la historia que llevo en mi memoria y traigo para ti.
Es sobre un pueblo elegido, que estaba esclavizado en la tierra de Egipto,
But un día Dios ordena que tienen que ser liberados
Y con gozo emprenden el nuevo camino a la tierra prometida.
But al salir al desierto por Dios fueron probados
Y sus fuerzas flaquearon, porque ellos dudaron,
Y no entiendo por qué, si Él mostraba su poder,
Si una nube de día y fuego de noche
Su camino marcaba y el sendero alumbraba
Si al llegar al Mar Rojo este se abrió en dos, y el pueblo en seco pasó.

Coro
¿Cuál es tu enemigo? ¿Quién amenaza tu vida?
¿Cuál es el ejército que viene tras de ti?
Mejor mira adelante que hay un Dios potente
Marcando el camino y el peleará por ti,
Marchemos con gozo que hay una tierra
En el cielo que esperando está.
No desanimes, arriba mi hermano
Ya no pases más tiempo llorando en tu desierto,
No sirve lamentarse y tanto reclamarle a Dios porque hay problemas.
Quizás ya estás dudando, por dolor atormentado sólo ves a tu enemigo que te viene intimidando
Y el mar ya se está acercando y no encuentras salida,
Y el fin de tu vida se aproxima en este día
Más yo te digo, confía, la victoria es segura,
Por más grande que sea la prueba en tu vida,
Hoy olvida quien viene alcanzándote detrás,
Y recuerda que sirves a un Dios que es más grande,
Que ha vencido en la cruz a tu enemigo gigante,
Sólo aumenta tu fe en ese Dios de poder, y así su gloria verás."""
        ),
        Hymn(
            id = 71,
            title = "Hay un río",
            link = "https://www.youtube.com/watch?v=uHZSMBVPq-Y",
            author = "",
            content = """//Hay un río que viene de ti//
//Dame de beber de ese río Señor
De ese río que viene de ti//"""
        ),
        Hymn(
            id = 72,
            title = "Tiempo de prepararnos",
            link = "https://www.youtube.com/watch?v=c55TOhnjfxc",
            author = "",
            content = """I
Ya la gracia se termina, la biblia lo dice, los tiempos lo gritan.
La medianoche ha llegado, se escucha el clamor, “El maestro retorna”.
Y el resonar del clarín, ya pronto se oirá en la nueva alborada.
//Se terminó nuestra espera, Iglesia de Cristo, vamos para el cielo//
Primero los que durmieron, dejando sus tumbas, resucitarán.
Porque no eran del mundo, a Dios fueron fieles, con Cristo se fueron.
Luego los que vimos, si a Él le servimos, su rostro veremos.
//Y a volar de los santos un coro celeste dará bienvenida//

Coro
Tiempo es de prepararnos, si, se oye el clamor,
Cristo viene ya, si yo sé que Él vendrá
Señales lo anuncian, que Él volverá.

II
Cuando el hijo del hombre levante su pueblo, tristezas vendrán.
Sobre la faz de la tierra comienza el tormento de los que se quedaron.
Entonces lamentaran, los hombres que aquí nunca se arrepintieron.
//Pero en el cielo, la Iglesia, con arpas entona himnos al Cordero//"""
        ),
        Hymn(
            id = 73,
            title = "Toca mis labios Señor",
            link = "https://www.youtube.com/watch?v=aRs2jfGbY60",
            author = "",
            content = """//Toca mis labios Señor con un carbón de tu altar
Y si los tocas podré tus alabanzas cantar//
Ya me has tocado Señor, Cristo me pudo limpiar,
Por eso ahora podré cantos de triunfo entonar."""
        ),
        Hymn(
            id = 74,
            title = "Unidos",
            link = "https://www.youtube.com/watch?v=WlYFFztAzLQ",
            author = "",
            content = """//Unidos, unidos en su nombre unidos//
//La gloria del Señor en ti resplandecerá
Y todo se llenará de amor y de paz//"""
        ),
        Hymn(
            id = 75,
            title = "¿Quién será esta?",
            link = "https://www.youtube.com/watch?v=WmDKZWMWWiY",
            author = "",
            content = """I
¿Quién será esta? De traje blanco como una novia ataviada está,
De lino fino son sus vestidos Mujer virtuosa ¿Quién te hallará?
Dicen que eres la escogida y por salvarte un rey murió,
Cuenta la historia que te amó tanto y hasta su sangre por ti derramó.

Coro
Bendita el alma que hoy puede hallarte, santificarse en tu verdad,
Mujer virtuosa, bella y hermosa, tú eres la esposa del rey celestial.

II
Los que se limpien serán los hijos, y vestiduras tú les darás
Afortunada todos la llaman, de ropa noble vestida tú estás,
Honor y gloria tu rey te ha dado,
Y una corona te espera allá,
Tu amado viene pronto a llevarte,
A la gran boda del Rey celestial."""
        ),
        Hymn(
            id = 76,
            title = "Jesús Nazareno",
            link = "",
            author = "",
            content = """I
Jesús Nazareno clavado en la cruz
Un velo de sangre su rostro cubrió
Su rostro divino fue desfigurado
Corona de espinas el pueblo le dio

Coro
Eli, Eli, Eli, lama sabactani
Clamaba el excelso, divino Jesús.
El gran Redentor, de nuevo exclamó
With potentes fuerzas su espíritu dio.

II
Con manto de grana y caña en sus manos
Soldados que azotan a Cristo Jesús
Rey de los judíos, gritaba la turba
Doblan las rodillas burlándose de Él.

III
Y crucificado el divino Maestro
Vinagre con hiel, diéronle a beber
Soldados reparten su santo vestuario
Y sobre la capa la suerte se dio."""
        ),
        Hymn(
            id = 77,
            title = "Yo sé que Él vive",
            link = "https://www.youtube.com/watch?v=H46q4Jiphyo",
            author = "",
            content = """I
Si los vientos te obedecen, si la mar por ti se calla,
Porque hay hombres que dicen que tú no vives,
Si la tierra tú la riegas con la lluvia de los cielos,
Por qué hay hombres que dudan tanto de ti.
Es mi anhelo que las flores le dijeran a los hombres,
Por cual mano todas ellas fueron creadas,
Desde luego entenderían que sin ti no existiría,
Lo más bello que en el mundo puedan ver.

Coro
No ves el agua fluir, con la cual tu
Sosiegas la sed que aprisiona tu boca
Y la sombra de un árbol la cual te regala descanso,
Pues entonces por qué te preguntas si hay un creador
Si quieres de ella contar, cada estrella,
Que alumbra lo negro de un inmenso cielo
Si no puedes, confiesa que Dios es Real, es muy cierto,
Es que es tan grande no se puede negar.
//Yo sé que Él vive, pues lo veo en la risa
De un niño cuando voy pasando.
Y al oír el bramido del mar que me dice cantando,
Que hay un Dios verdadero
Que hizo toda la creación//."""
        ),
        Hymn(
            id = 78,
            title = "Tú eres el alfarero",
            link = "https://www.youtube.com/watch?v=NjFgHBEYLCM",
            author = "",
            content = """//Tú eres el alfarero que con tus manos me has transformado//
Y mientras pasan los días, pasan los años más te venero
//rompe mi cántaro, rompe mi copa, rompe mi vida y hazla de nuevo//."""
        ),
        Hymn(
            id = 79,
            title = "Él me levantará",
            link = "https://www.youtube.com/watch?v=ijDcB-KPpuw",
            author = "",
            content = """//Él me levantará, Él me levantará, Él me levantará, así es el Señor//
Él me levantará, Él me levantará en sus manos me sostendrá Él me levantará.
Él me levantará, Él me levantará, en sus manos me sostendrá, así es el Señor
//Me fortalecerá, me fortalecerá me fortalecerá, así es el Señor//
Me fortalecerá, me fortalecerá en sus manos me sostendrá me fortalecerá.
Me fortalecerá, me fortalecerá, en sus manos me sostendrá, así es el Señor"""
        ),
        Hymn(
            id = 80,
            title = "¡Qué gran amor!",
            link = "https://www.youtube.com/watch?v=jYpqpCtAlOA",
            author = "",
            content = """Una frente ha sido coronada
Y espinas han clavado en aquella hermosa sien.
Una cruz sobre sus hombros ponen,
Y conducen a Aquel hombre hacia el Gólgota cruel.
Paso tras paso es azotado
Ni una queja se ha escuchado
¡Qué gran amor!
Paso tras paso es injuriado duramente castigado,
But no habló ¡Qué gran amor!
Sus ojos han sido vendados
Porque no han soportado el reflejo de su amor
Y esos labios que han abofeteado,
Se han abierto y exclamado
Padre mío: ¡Perdónalos!

Manos y pies han perforado
Y unos clavos le han clavado ¡Cuánto dolor!
Manos y pies han traspasado
Y su sangre ha derramado //¡Qué gran amor!//

A diestra y siniestra, dos ladrones,
Y uno de ellos cruelmente, injuriándole está,
But el otro le ha reconocido,
Y ferviente le ha pedido en tu reino quiero estar,
De cierto os digo, que hoy conmigo,
Gozarás del paraíso por la eternidad.
De cierto os digo que hoy conmigo entrarás
En las moradas por la eternidad ¡Qué gran amor!

Su espíritu al Padre ha entregado,
But antes ha exclamado:-¡Consumado es!,
But al hombre no le ha bastado
Y cruelmente en el costado una lanza le clavó
Agua y sangre, ha derramado
Y nació de aquel costado, la iglesia de Dios.
Agua y sangre ha derramado y
Nació para mi alma la salvación.
¡Qué gran amor!"""
        ),
        Hymn(
            id = 81,
            title = "Llena mi vida",
            link = "https://www.youtube.com/watch?v=35wFHoZwnpg",
            author = "",
            content = """I
Aunque se fue, morada allá en el cielo preparó
Más volverá, así como una mañana se marchó
Sé que vendrá, señales hoy lo gritan que es así,
Al escuchar un son en alta esfera,
Vendrá a buscar un pueblo que le espera,
Pueblo que con sangre el ganó.

Coro
//Llena mi vaso de tu espíritu divino,
Llena mi vida del aceite celestial,
Porque yo quiero estar, en las moradas allá,
Quiero escuchar el himno de bienvenida//

II
Tal como fue, en los antiguos días de Noé;
Así será, en los días de retorno de Jesús,
Nadie creyó, que pronto llegaría el cruel final,
But llegó la hora señalada,
La puerta del gran arca fue cerrada,
La gente de entonces pereció."""
        ),
        Hymn(
            id = 82,
            title = "Dios está aquí - Tan cerca de mí",
            link = "https://www.youtube.com/watch?v=w53XHUrwCt4",
            author = "",
            content = """Dios está aquí, tan cierto como el aire que respiro
Tan cierto como en la mañana se levanta el sol
Tan cierto que cuando le hablo él me puede oír.

//Tan cerca de mí// que hasta le puedo tocar
Jesús está aquí
Míralo a tu lado caminando y paseándose en la multitud,
Muchos ciegos van, pero no le ven, ciegos de ceguera espiritual.

//Tan cerca de mí// que hasta le puedo tocar
Jesús está aquí
Le hablaré sin miedo al oído, le contaré cosas que hay en mí
Y que sólo a él le interesarán él es más que amigo para mí.

//Tan cerca de mí// que hasta le puedo tocar
Jesús está aquí."""
        ),
        Hymn(
            id = 83,
            title = "Quémame con fuego Santo",
            link = "https://www.youtube.com/watch?v=sDvvzQ0aufM",
            author = "",
            content = """I
Hoy he llegado a tu casa, Señor sabiendo,
Que si tu espíritu hoy no me asiste voy a perecer,
Siento que el mundo invade mi vida, y turba mi mente,
Haciendo aquello Señor que aborrezco y no quiero hacer.

Coro
//Quiero Señor hoy quemes con fuego santo,
Quiero Señor me revistas con tu poder,
Porque afuera el mundo me espera,
Pretende golpearme y mi vida quitar,
But si hoy tu revistes mi vida, yo voy a triunfar//.

II
Estando aquí en tu presencia, me siento seguro,
Quisiera que el tiempo detenga su marcha y no avance más,
No quiero dejar este santo lugar sin ser revestido
Afuera el mundo me espera y quiere tratarme muy mal."""
        ),
        Hymn(
            id = 84,
            title = "Un hombre nuevo",
            link = "https://www.youtube.com/watch?v=TRZRrd_UGWg",
            author = "",
            content = """I
Una vez perdido vivía yo, lejos y vagante en error.
Más la voz de Cristo me alcanzó, me llamó con tierno amor.

Coro
Hay un nombre nuevo en la gloria, mío es, si mío es
Y los ángeles cantan la historia “Salvo es el pecador”
Oh hay un nombre nuevo en la gloria, mío es, sí mío es,
Todos mis pecados ya son perdonados ¡Gloria al Señor!

II
Cantos de alegría elevo hoy, a mi Rey y buen Salvador,
Es porque mis dones a Cristo doy, que me use en su amor.

III
En la Biblia dice que salvo soy, por la gracia de Jesucristo,
Y a por fe en su nombre a la gloria voy, desde que me rescató"""
        ),
        Hymn(
            id = 85,
            title = "De rodillas es mejor",
            link = "https://www.youtube.com/watch?v=xmcdpBo2JX4",
            author = "",
            content = """I
Cierta vez un pastor, un camino tomó afloat al pensar,
Que su iglesia era fría y su alma vacía, sin tener la piedad.
A un niño encontró, de rodillas lo vio trabajando en el sol,
Banco le ofreció y el niño respondió: “De rodillas es mejor”

Coro
//De rodillas es mejor//
En prueba y dolor
Siempre orad al Señor, de rodillas es mejor.

II
Esta voz le tocó, muy profundo le habló; el pastor meditó.
Todo el día pasó, y la noche llegó, y no pudo dormir.
Procuró olvidar para poder dormir, más fue siempre peor,
Parecíale ver al niño responder: “De rodillas es mejor”

III
Y entonces pensó, el Señor me habló que yo debo orar;
Y a orar comenzó y Dios le respondió la victoria le dio
Esa iglesia sin luz, comenzó a brillar, con gran resplandor,
Y así aprendió que orar al Señor de rodillas es mejor.

IV
Esta historia leí, para mí aprendí que yo debo orar,
Y si tú mi hermano, luchando estás sin poder superar.
Hoy comienza a confiar, a clamar y a orar, de rodillas al Señor,
Y humillado a sus pies vencerás vez tras vez, de rodillas es mejor."""
        ),
        Hymn(
            id = 86,
            title = "Cuando Cristo vino",
            link = "https://www.youtube.com/watch?v=Djvmb28auKA",
            author = "",
            content = """Cuando Cristo vino a mi corazón, mi vida entera cambió
Su paz y su amor alejaron de mí, las sombras, las dudas y el temor.

Coro
//Mi vida comenzó cuando el Señor llegó y hoy puedo cantar de su amor//
Yo quiero que Cristo te transforme a ti, que cambie tu vida también.
Oh ven a la cruz donde murió Jesús y ábrele tu corazón."""
        ),
        Hymn(
            id = 87,
            title = "Peña de Horeb",
            link = "https://www.youtube.com/watch?v=-JGMPHVUGWs",
            author = "",
            content = """I
//Cristo es la peña de Horeb que está brotando,
Agua de vida saludable para ti//

Coro
Ven a tomarla que es más dulce que la miel
Refresca el alma, refresca todo tu ser
Cristo es la peña de Horeb que está brotando
Agua de vida saludable para ti.

II
Cristo es el Lirio del valle de las flores,
El es la rosa blanca y pura de Sarón,
Cristo es la vida y Amor de los Amores
El es eterna fuente de salvación"""
        ),
        Hymn(
            id = 88,
            title = "Pon aceite",
            link = "https://www.youtube.com/watch?v=CHhb25dXBqQ",
            author = "",
            content = """//Pon aceite en mi lámpara Señor//
Que yo quiero servirte con amor
Pon aceite en mi lámpara Señor.

Señor Jesús, tú eres mi vida
Señor Jesús, tú eres mi amor
//Salvaste mi alma perdida,
Por eso te alabo con el corazón//
Con el corazón, con el corazón, con el corazón."""
        ),
        Hymn(
            id = 89,
            title = "Ven en pos de mí",
            link = "https://www.youtube.com/watch?v=6DfD6EWOMus",
            author = "",
            content = """I
Viajaba en triste soledad y nadie me buscó
La carga horrible de impiedad más grande se volvió
Le dije a Jesucristo que me trataban mal
Entonces le oí así decir:
Camino del Calvario mi pie también sufrió,
La cruz que yo cargaba mi cuerpo doblegó.
Prosigue peregrino que el alba ya vendrá
Tu cruz levanta y ven en pos de mí.

II
A veces dije que por Él penoso es trabajar
Que mucho yo sacrifiqué su senda por andar
Mi fama y mi fortuna, por ti yo la deje,
Entonces le oí así decir:
Mi hogar de eterna gloria por ti yo abandoné
Mis manos enclavadas en cruenta cruz miré
Más hoy caminaremos unidos tú y yo
Tu cruz levanta y ven en pos de mí."""
        ),
        Hymn(
            id = 90,
            title = "Oh, que amor",
            link = "https://www.youtube.com/watch?v=L8duglIm-Wk",
            author = "",
            content = """I
//Oh, que amor, qué inmenso amor, el de mi Salvador//
Dios desde el cielo al Salvador, mandó a morir por mí,
Por ti murió, por mí murió, dio sangre carmesí."""
        ),
        Hymn(
            id = 91,
            title = "Ven amigo a Jesús",
            link = "https://www.youtube.com/watch?v=L8duglIm-Wk",
            author = "",
            content = """I
Ven amigo a Jesús, pues él murió por ti,
Recibirás la luz que quiere darte a ti,
Mi buen Jesús murió para darte perdón,
Abre tu corazón y dulce paz tendrás.

Coro
Día fatal vendrá, en que no habrá lugar,
La puerta se abre hoy y tú podrás entrar,
//Más gracia ya no habrá, pues despreciaste hoy,
Acepta pecador la salvación de Dios.//

II
Las manos del Señor se abren hoy para ti
Ven y confía en Él y serás muy feliz,
Tus cuitas pon en Dios, pues Él las llevará,
Quitará tu pesar por su consolación."""
        ),
        Hymn(
            id = 92,
            title = "Hay momentos",
            link = "https://www.youtube.com/watch?v=19Gv7n3KlSE",
            author = "",
            content = """//Hay momentos que las palabras no alcanzan
Para decirte lo que siento por ti mi buen Jesús//
//Yo te agradezco por todo lo que hiciste,
Por todo lo que haces y todo lo que harás//"""
        ),
        Hymn(
            id = 93,
            title = "Estar en tu presencia",
            link = "https://www.youtube.com/watch?v=pptBn7eZXnw",
            author = "",
            content = """//Estar en tu presencia, Señor que grato es//
Que grato es, que grato es,
Es mejor que todos los momentos
Estar en tu presencia Señor que grato es."""
        ),
        Hymn(
            id = 94,
            title = "A donde volveré",
            link = "https://www.youtube.com/watch?v=2Iosjd2VUbI",
            author = "",
            content = """I
A donde volveré mis ojos oh Señor, para encontrar ayuda
De quién sino de ti, sustento me vendrá en horas de aflicción,
En sombras o en luz, encuentro gran quietud, si oro me sustentas,
En paz o en aflicción la mano de mi Dios me da seguridad.

Coro
Dame tu mano, toma la mía oh Dios amado
Cuando estoy débil me hace más fuerte tu poder.
Tu compañía y tu calor, divina mano,
Me lleva al cielo, donde un día te veré.

II
Riquezas y poder, fama y celebridad, rasguñaron mis manos
Tratando de alcanzar lo que podía anhelar como supremo ideal,
But al mirarer a ti, de pronto comprendí que estaba equivocado,
Un mundo descubrí más allá de la cruz, donde sangró tu mano.

III
A donde encontraré refugio sino en ti que eres mi Dios amado,
Las olas de este mar rodean sin cesar mi barca al navegar.
But Señor en ti, confío en gran fervor, tu mano me sustenta
Porque eres tú mi rey y puedo serte fiel, por la eternidad."""
        ),
        Hymn(
            id = 95,
            title = "El poder del cristiano",
            link = "https://www.youtube.com/watch?v=4BkWPK_yxIE",
            author = "",
            content = """El poder del cristiano está en la oración
El que ora constante, vencerá en todo tiempo la tentación,
El poder del cristiano está en Jesús, y Cristo nos dijo:
“Orad siempre, siempre, porque la respuesta está en la oración”
//Y el enemigo caerá vencido, caerá ante tus pies,
Y en toda lucha y en toda prueba tú podrás vencer,
Si oras ferviente con toda tu mente y tu corazón,
Las fuerzas del mal querrán destruirte
Y tu fe herirte, pero no podrán//"""
        ),
        Hymn(
            id = 96,
            title = "Pentecostés",
            link = "https://www.youtube.com/watch?v=V7Ra-OrWcZE",
            author = "",
            content = """I
Pentecostés, pentecostés, es el lema de la iglesia del Señor,
Pentecostés, pentecostés, es el fuego celestial consumidor.
Acércate, buen lidiador a las plantas de Jesús bautizador,
Y llenarás hoy del fulgor, este templo vivo que es tu corazón.

Coro
Ríndete hoy, déjale entrar, es tu creador quiere llenar,
Ríndete hoy, déjale entrar, quiere llenar tu corazón.

II
Grande placer es recibir, el candente fuego purificador,
Que da poder, gran convicción, él da gozo, paz, consuelo hasta morir.
Le alabarás, le servirás, cual testigo fuerte y grande de su amor.
Acércate buen lidiador, no desprecies el bautismo del Señor."""
        ),
        Hymn(
            id = 97,
            title = "Mi deseo",
            link = "https://www.youtube.com/watch?v=lADsmmVto1w",
            author = "",
            content = """I
Quiero dejarme guiar por tu Espíritu,
Y que me arrastre la corriente de tu amor,
Quiero alabarte y darte gloria
Porque tú eres Jesús mi Señor.

II
Quiero beber del agua de la vida,
Quiero comer tu palabra y meditar
En tu grandeza y en tu poder inmenso
En tu inefable y sin igual bondad.

III
Quiero ser fiel, acatar tus mandatos,
Humildemente cumplir tu voluntad,
Para poder por todas las edades
Estar contigo y tu gloria contemplar."""
        ),
        Hymn(
            id = 98,
            title = "El buen pastor",
            link = "https://www.youtube.com/watch?v=M8JkInFTFJ4",
            author = "",
            content = """I
Cuidando está su rebaño el buen pastor,
Más al contarlas una oveja le faltó,
Y alarmado se pregunta ¿Dónde está, qué sucedió?
Estaba herida en el desierto o en la triste soledad.

Coro
El buen pastor, tranquilo no quedó,
Y aún el desierto con valor el confrontó.
Se fue a buscar la oveja que faltó,
Y en la montaña, en el peligro la encontró,
Y la cargó sobre él.

II
La halló en peligro y en la triste soledad,
Por rescatarla aún su sangre derramó,
Sobre su hombro alegremente, la condujo hacia el redil,
Con sus amigos y vecinos su alegría compartió.

III
Hoy eres tú esa oveja que no está,
Y Jesucristo es el pastor que va por ti,
Por el desierto de tu cruel indiferencia él caminó,
Y tus desprecios fueron piedras que su cuerpo lastimó."""
        ),
        Hymn(
            id = 99,
            title = "Alabaré",
            link = "https://www.youtube.com/watch?v=WW62Ga6EDvA",
            author = "",
            content = """///Alabaré/// a mi Señor, ///Alabaré/// a mi Señor
Juan vio el número de los redimidos
Y todos alababan al Señor,
Unos oraban, otros cantaban,
Pero todos alababan al Señor
///Alabaré/// a mi Señor, ///Alabaré/// a mi Señor"""
        ),
        Hymn(
            id = 100,
            title = "Vine a adorar a Dios",
            link = "https://www.youtube.com/watch?v=6CgYSWe1n3U",
            author = "",
            content = """//Vine a adorar a Dios// vine a adorar su nombre, sólo vine para adorar.
//Vine a adorar a Dios// vine a adorar su nombre, sólo vine para adorar.
El vino a mi vida un día muy especial, cambió mi corazón,
En un nuevo corazón y esa es la razón por la que digo que
//Sólo vine para adorar//"""
        ),
        Hymn(
            id = 101,
            title = "El ciervo",
            link = "https://www.youtube.com/watch?v=ZVhwnTsDU3M",
            author = "",
            content = """//Brama como el ciervo por las corrientes de las aguas, mi alma Señor//

Coro
Mi alma tiene sed de ti, mi alma suspira por tu amor
¡Oh! Mi alma tiene sed de ti, mi alma suspira por tu amor
Quiero beber de tu agua Señor y así saciar mi sed.
Nada de este mundo puede igualarse a ti, tu paz, tu gozo, tu amor."""
        ),
        Hymn(
            id = 102,
            title = "Saliendo del pretorio",
            link = "https://www.youtube.com/watch?v=JUgXqiJmXec",
            author = "",
            content = """I
Saliendo del pretorio, marcha una procesión,
Con rumbo al Calvario, sufriendo va un varón
La cruz sobre su espalda, llagándole está,
No puede caminar.
No puede dar un paso, y cae por el camino,
Recibe latigazos sobre su cuerpo herido,
No puede avanzar,
Se escucha sólo un grito: “Levántate maldito”

Coro
No, no puede ser maldito aquel que en su dolor
Exclama con un grito “Perdónales Señor”
Perdónales sus faltas, no mires su actuación,
De ellos ten compasión.

II
Sed, sed tengo de un amigo, sed tengo de amor
Sed tengo que un humano tenga comprensión,
Que acepte esta sangre
Que derramando estoy por su salvación.
No, no puede dar un paso, y cae por el camino,
Recibe latigazos sobre su cuerpo herido,
No puede avanzar, se escucha sólo un grito: “Levántate maldito”"""
        ),
        Hymn(
            id = 103,
            title = "El Hijo se va",
            link = "https://www.youtube.com/watch?v=wg2OGe7x69M",
            author = "",
            content = """I
El hijo se va alegre del hogar, llevando los bienes que al padre pidió,
Juntándolo todo ansioso se va, a tierras lejanas detrás de su afán.
But a su padre triste dejó, dos lágrimas nublan sus ojos de amor,
Mirando el camino en que el hijo se fue,
Pensando que pronto podrá regresar.

Coro
Hijo pródigo vuelve, tu padre te espera,
Ya no vagues así, es tiempo de regresar,
En tu casa tendrás mucha abundancia de pan,
Vuelve al hogar, oh sí vuelve al hogar
//te está esperando tu padre de amor//

II
Lejos del padre, lejos del hogar, se halla cansado, sólo y sin amor,
Todos sus bienes en deleites gastó, y sin sus amigos sólo se sintió.
Junto a los cerdos comiendo él está, con hambre, con frío, llora su pesar,
Recuerda entonces al padre en su hogar y volviendo en sí decide regresar."""
        ),
        Hymn(
            id = 104,
            title = "Cartas a Dios",
            link = "https://www.youtube.com/watch?v=_HDcPtNWQ2M",
            author = "",
            content = """I
Si el mundo y sus afanes tu vida ha abatido
Te sientes perseguido por un terrible mal
Y no encuentras palabras para expresar de tu alma
Tu miedo, tu angustia o tu pesar

Coro
Si puedes tú llorar, llora para Dios
Tus lágrimas son cartas leídas ante Él
Podrás tú cantar y también loar
//Porque al corazón contrito Dios no desoirá//

II
Ana frente al templo lloraba amargamente
Le pedía a Dios un hijo que no podía tener
Y escuchó del cielo el Dios que escucha el llanto
Y a Ana un hijo le dio."""
        ),
        Hymn(
            id = 105,
            title = "No hay Dios como tú",
            link = "https://www.youtube.com/watch?v=0duStzgt28k",
            author = "",
            content = """No hay Dios tan grande como tú //No lo hay//
No hay Dios tan grande como tú //No lo hay//
//No hay Dios que pueda hacer las obras como las que haces tú//
//No es con ejército, ni con espada, más con tu Santo Espíritu//
///Y esos montes se moverán///
Por su Santo Espíritu."""
        ),
        Hymn(
            id = 106,
            title = "Jesús me da su libertad",
            link = "https://www.youtube.com/watch?v=OfYsWf_M68A",
            author = "",
            content = """Jesús me da su libertad //su libertad//
Jesús me da su libertad y ya ninguno me la quitará
No hay más cadenas, ni ataduras, ni opresiones, hay libertad.
Jesús me da su libertad y ya ninguno me la quitará"""
        ),
        Hymn(
            id = 107,
            title = "Tierra Bendita",
            link = "https://www.youtube.com/watch?v=xv1An1wx7Lc",
            author = "",
            content = """I
Tierra bendita y divina es la de Palestina donde nació Jesús,
Eres de las naciones cumbres, bañadas por la lumbre que derramó su luz

Coro
Eres la historia inolvidable porque en tu suelo se derramó
//La sangre, preciosa sangre del Unigénito Hijo de Dios//

II
Cuenta la historia del pasado, que en tu suelo sagrado vivió el Salvador,
Y en tus hermosos olivares habló a los millares su palabra de amor.

III
Quedan en ti testigos mudos que son los viejos muros de la Jerusalén,
Viejas paredes destruidas que si tuvieran vida nos hablarían también."""
        ),
        Hymn(
            id = 108,
            title = "Santo es el Señor",
            link = "https://www.youtube.com/watch?v=63Qk30qWeM4",
            author = "",
            content = """//Santo, santo es el Señor, de los ejércitos Rey//
Su gloria llena la tierra, su gloria llena la iglesia,
Su gloria llena mi vida, Santo es el Señor."""
        ),
        Hymn(
            id = 109,
            title = "En Cristo tengo todo",
            link = "https://www.youtube.com/watch?v=5_a0qJWcC1g",
            author = "",
            content = """I
No tengo nada, nada que esconder, mi vida transparente es,
Ya Cristo ha quitado en su morir las cosas malas del ayer,
No tengo nerviosismos de prever, desgracias que me puedan suceder,
La paz ha inundado ya mi ser y no hay por qué temer.
Mi íntimo rincón de agradecer, está lleno de amor y fe,
La Biblia, la alabanza y la oración, renuevan mi fervor por Él,
No tengo grandes cosas que contar, no tengo ni siquiera un buen pasar,
Mi vida es tan sencilla que al final, cabría en un cantar.

Coro
En Cristo tengo todo, no hay nada que me falte,
Las cosas más comunes tienen sabor por Él.
Hoy tengo por basura lo que antes me halagaba
Y habrá de ser mi orgullo poder morir por Él.

II
La vida me dio mucho y me quitó, a Dios se lo agradezco yo,
El único recuerdo que quedó fue cuando Dios me perdonó,
Entonces Él quitó mi ansiedad, entonces le he servido bien o mal,
Lo cierto es que Él logró simplificar el lío de mi andar.
Si un día alguien me quisiera robar, no mucho se habrá de llevar,
Mi único legado y capital, está en el banco celestial,
But puedo acostarme y descansar, puedo mirar de frente y continuar,
No hay nada que me pueda avergonzar si a Él lo quiero honrar."""
        ),
        Hymn(
            id = 110,
            title = "Usa mi vida",
            link = "https://www.youtube.com/watch?v=CDPIOBHCse4",
            author = "",
            content = """Usa mi vida, Señor, usa mi vida
Yo quiero servirte, con todas las fuerzas de mi corazón.
Usa mi vida, Señor, usa mi vida,
Yo quiero amarte, con todo el cariño de mi corazón.
//Aunque indigno soy, aunque nada tengo,
Que pueda ofrecerte divino Señor,
Yo quiero servirte, con todas las fuerzas,
Con todas las fuerzas,
Con todas las fuerzas de mi corazón//"""
        ),
        Hymn(
            id = 111,
            title = "Soy la triste oveja",
            link = "https://www.youtube.com/watch?v=Sy27Plk4um0",
            author = "",
            content = """I
Soy la triste oveja que dejó al pastor,
Yo andaba perdido cuando Él me encontró,
Con un silbo suave luego me llamó,
Y en sus brazos al dulce hogar me llevó
Las noventa y nueve dejó en el redil,
Se fue al desierto a buscarme a mí,
Con afán inmenso luego me llamó
Me encontró gimiendo de acervo dolor
Se sentó a mi lado y al verme lloró,
Sé conmigo siempre y no tendrás dolor.

II
Ven mi triste oveja, escucha mi voz,
No me desconozcas, soy el buen pastor,
Vamos al rebaño do reina la paz,
Allí donde mora mi Rey celestial.
Si por la fatiga no puedes andar,
Ven entre mis brazos te puedo llevar
Ven mi triste oveja, vamos al redil
Que muy tiernos pastos tengo para ti,
Conmigo por siempre tú vas a vivir."""
        ),
        Hymn(
            id = 112,
            title = "Hay victoria",
            link = "https://www.youtube.com/watch?v=Xl_JqTDhOE4",
            author = "",
            content = """//Hay victoria, hay victoria, hay victoria en el nombre de Jesús//
No podrá el enemigo dañar a nuestras almas
//Porque hay victoria, si hay victoria,
Hay victoria en el nombre de Jesús//"""
        ),
        Hymn(
            id = 113,
            title = "Quédate Señor",
            link = "https://www.youtube.com/watch?v=1QnVhR02n3Y",
            author = "",
            content = """///Quédate Señor/// en cada corazón
///Quédate Señor/// en mí
Oh Cristo mío haz de mi alma un altar para adorarte con devoción,
Para beber del agua de la vida y así saciar la sed del corazón."""
        ),
        Hymn(
            id = 114,
            title = "Cuando el pueblo alaba a Dios",
            link = "https://www.youtube.com/watch?v=G9CzGsvB9B8",
            author = "",
            content = """//Cuando el pueblo del Señor alaba a Dios suceden cosas maravillosas//
//Hay sanidad, poder, liberation y se siente la presencia del Señor//
//Aquí se siente la presencia del Señor//
//Siento el fuego del Espíritu Santo//
//Siento gozo, siento paz, siento el amor que Dios me da//
//Aquí se siente la presencia del Señor//"""
        ),
        Hymn(
            id = 115,
            title = "Hay victoria en mi Jesús",
            link = "https://www.youtube.com/watch?v=2ERAiiLjXXA",
            author = "",
            content = """//Hay victoria en mi Jesús, en mi Jesús//
//Las cadenas que me ataban rotas son por el Señor//
//Y mi copa llena está, llena está//
Del aceite que desciende desde el trono celestial"""
        ),
        Hymn(
            id = 116,
            title = "Ebenezer",
            link = "https://www.youtube.com/watch?v=iJk6Lfqm1ic",
            author = "",
            content = """Ebenezer, hasta aquí nos ayudó el Señor
Con su poder y su mano nos guiará,
Esta canción por los siglos durará
Ebenezer, hasta aquí nos ayudó el Señor."""
        ),
        Hymn(
            id = 117,
            title = "Hoy venimos a sus pies",
            link = "https://www.youtube.com/watch?v=xEHaI4pZv-8",
            author = "",
            content = """I
//Hoy venimos a sus pies a invocar su nombre y a adorar//
Hoy venimos a sus pies a invocar su nombre y adorarle a Él,
A adorar a Jesús Señor.

II
//Olvidemos lo demás, pensemos sólo en Él y en adorar//
Olvidemos lo demás, pensemos sólo en Él y en darle nuestro amor,
Adorar a Jesús Señor.

III
//Santas manos elevad, bendecid su nombre y adorad//
Santas manos elevad, bendecid su nombre y adoradle a Él,
Adorad a Jesús Señor."""
        ),
        Hymn(
            id = 118,
            title = "Evangelista",
            link = "https://www.youtube.com/watch?v=5-kQ959eARI",
            author = "",
            content = """I
Cuando un profeta llamado Balaam,
Se dirigía camino hacia Moab,
Su asna no pudo seguir en su bregar,
Interceptado por un poder angelical.

Coro
Evangelista no te vayas a engañar,
Ni aceptes dádivas que te lleven al mal,
Sólo mi Cristo es quien nos puede guiar,
Y conducirnos a la Patria Celestial.

II
Y conducido por tan dócil animal,
Su asna le habla pidiéndole piedad,
“A muchas partes te he llevado ya,
Hoy me lo impide el ángel de Jehová”"""
        ),
        Hymn(
            id = 119,
            title = "Hay una vida",
            link = "https://www.youtube.com/watch?v=KN4okXp5kl4",
            author = "",
            content = """Yo vine a buscar lo de arriba, yo vine a buscar al Señor,
Yo vine a buscar esa vida escondida en Cristo mi Salvador
Yo vine a buscar lo de arriba, yo vine a buscar al Señor,
Yo vine a buscar esa vida escondida en Cristo que quiero yo.
///Hay una vida/// escondida en Cristo mi Salvador
///Hay una vida/// escondida en Cristo que quiero yo."""
        ),
        Hymn(
            id = 120,
            title = "Quiero más de Cristo",
            link = "https://www.youtube.com/watch?v=KAxg-SXkDUE",
            author = "",
            content = """Por la fe yo he conocido, de su gracia pude recibir,
Su presencia inundó mis ojos sin luz, ya no quiero más vivir sin él.
Es la fuente que emana vida, de esas aguas he bebido yo
Y sanaron toda mi alma herida sin paz, como Cristo no hay nadie igual.
Yo quiero más y más de Cristo, yo quiero más de su poder,
Yo quiero más de su presencia, yo quiero más y más de Él.
Si quieres más y más de Cristo, si quieres más de su poder,
Acude más a su presencia, y más y más tendrás de Él."""
        ),
        Hymn(
            id = 121,
            title = "Te vengo a decir",
            link = "https://www.youtube.com/watch?v=MCEpzJlyQU8",
            author = "",
            content = """I
//Te vengo a decir// Oh, mi Salvador
//Que yo te amo a ti// con el corazón
//Te vengo a decir// toda la verdad
Yo te amo Señor, te adoro Señor con el corazón.

Coro
//Yo quiero cantar// de gozo y de paz
//Yo quiero llorar// de felicidad
//Te vengo a decir// toda la verdad
Yo te amo Señor, te adoro Señor con el corazón.

II
//Te quiero seguir// Oh mi Salvador,
//Y darte mi ser// mi amigo y Dios,
//Te quiero servir// mi Rey mi Señor,
Te vengo a entregar todo lo que soy
Recíbelo Oh Dios"""
        ),
        Hymn(
            id = 122,
            title = "De pronto una nube",
            link = "https://www.youtube.com/watch?v=8C4dd8x1zDA",
            author = "",
            content = """I
De pronto una nube me puse a mirar,
De pronto la misma comienza a agrandar,
El mar con sus olas, el trueno, el sol,
La tierra que brama anunciando está
Que el cielo predicho en siglos atrás
Se abre de nuevo, prepárate ya.

II
Es Cristo que viene, al fin viene ya,
Dichosos son todos los que ha de llevar,
Pues yo me pregunto adónde tú estás,
Buscando ese cielo, huyendo quizás,
Mi Cristo te espera, recibe su paz,
No sigas huyendo, acércate ya.
//La patria del cielo tú recibirás,
No esperes más tiempo, que tarde será//"""
        ),
        Hymn(
            id = 123,
            title = "Mi Dios dondequiera está",
            link = "https://www.youtube.com/watch?v=bgLlySctX7A",
            author = "",
            content = """///Mi Dios dondequiera está/// Aleluya a Jehová
//Si lo buscas en el cielo, si lo buscas en el mar,
Si lo buscas en mi vida, allí lo encontrarás//"""
        ),
        Hymn(
            id = 124,
            title = "Quita la piedra",
            link = "https://www.youtube.com/watch?v=kTkH17tPIyc",
            author = "",
            content = """//Quita la piedra, deja el agua correr,
Mira que las almas se van a perder,
Háblales de Cristo, ese es tu deber,
Agua que se estanca no podrá correr//"""
        ),
        Hymn(
            id = 125,
            title = "La sangre de Cristo",
            link = "https://www.youtube.com/watch?v=WxXFDBtQ38A",
            author = "",
            content = """//La sangre de Cristo tiene poder para deshacer lo malo de mi ser//
//La sangre de Cristo Jesús, preciosa sangre carmesí,
Que derramó allá en la cruz, suficiente ha sido para mí//"""
        ),
        Hymn(
            id = 126,
            title = "Hay un poder",
            link = "https://www.youtube.com/watch?v=fKuoVuLvQIc",
            author = "",
            content = """//Hay un poder, poder, poder, hay un poder que necesito yo//
//Ese poder de Dios, es celestial, ese poder lo necesito yo//"""
        ),
        Hymn(
            id = 127,
            title = "Por todo el mundo",
            link = "https://www.youtube.com/watch?v=8LYjl3s_C8s",
            author = "",
            content = """I
Por todo el mundo el espíritu de Dios se mueve,
Por todo el mundo como Joel profetizó,
Por todo el mundo hay una gran revelación,
Es la gloria del Señor como las aguas cubren el mar.

II
Muy dentro de mí, el Espíritu de Dios se mueve,
Muy dentro de mí como Joel profetizó
Muy dentro de mí hay una gran revelación,
Es la gloria del Señor como las aguas cubren el mar."""
        ),
        Hymn(
            id = 128,
            title = "¿Qué pueblo será mi pueblo?",
            link = "https://www.youtube.com/watch?v=Yfoz6xViyyc",
            author = "",
            content = """I
De caminos errantes yo me alejé,
De senderos oscuros, cubiertos de hiel.
Al seguirte ¡Oh Cristo! Ya todo dejé,
Sólo anhelo amarte y ser de tu grey.

Coro
¿Qué pueblo será mi pueblo? Pregúntame Señor,
Tu pueblo será mi pueblo,
//Y tú Señor// serás mi Dios.

II
No me pidas que un día te deje de amar,
No me pidas que vuelva de nuevo hacia atrás,
Cuanto el mundo me daba muy lejos dejé,
Si tu cielo es mi cielo, ¿Adónde yo iré?"""
        ),
        Hymn(
            id = 129,
            title = "Ven Espíritu divino",
            link = "https://www.youtube.com/watch?v=AOnX_zdHzlw",
            author = "",
            content = """//Ven, ven, ven Espíritu Divino,
Ven, ven, ven, acércate a mí//
///Apodérate/// de todo mi ser
///Apodérate/// de todo mi ser"""
        ),
        Hymn(
            id = 130,
            title = "Majestad",
            link = "https://www.youtube.com/watch?v=RvwG3EezKN0",
            author = "",
            content = """Majestad, gloria a su majestad,
Doy a Cristo, toda gloria, alabanza y honor
Majestad, reino y autoridad, suyo el honor, suyo el poder, gloria a Él
Exaltad, engrandeced su santo nombre,
Adorad, magnificad a Cristo el Rey
Majestad, gloria a su majestad,
Cristo murió, resucitó, hoy es Señor"""
        ),
        Hymn(
            id = 131,
            title = "Todo lo puedo en Cristo",
            link = "https://www.youtube.com/watch?v=VMw_h0vinAc",
            author = "",
            content = """I
Cuando todo parece oscuro, cuando difícil es decidir,
Y la vida parece absurda sin una luz para alumbrar,
Es posible que los amigos a nuestro lado podrán estar,
But en nada cambiará la vida pues a su alcance no está.
Es entonces que yo medito en aquellas promesas
Que me hablan del Señor Jesucristo, de su amor y su gran poder,
Entre aquellas promesas dichas una siempre late en mi ser
Y la grito desde mi alma

Coro
//Todo lo puedo en Cristo que me fortalece// ¡Oh gloria!

II
Hay circunstancias en esta vida difíciles de atravesar,
Y el diablo trata de engañarnos diciendo, “no podréis vencer”
Está vencido y él lo sabe, por eso no hemos de aceptar,
Las mentiras que él nos dice y digamos

III
Al mirar la naturaleza, al mundo y su fugaz placer,
Llegando la enfermedad resistiremos en nombre de Él,
Mantengamos nuestra alabanza, nosotros somos hijos del Rey,
Pues nuestra fe y victoria crece diciendo"""
        ),
        Hymn(
            id = 132,
            title = "Demos gracias al Señor",
            link = "https://www.youtube.com/watch?v=EIg2zsBLk6s",
            author = "",
            content = """//Demos gracias al Señor, demos gracias, demos gracias por su amor//
Por la mañana las aves cantan las alabanzas a Cristo el Salvador,
Y tú mi hermano ¿Por qué no cantas las alabanzas a Cristo el Salvador?"""
        ),
        Hymn(
            id = 133,
            title = "Hay una unción",
            link = "",
            author = "",
            content = """//Hay una unción aquí, cayendo sobre mí,
Mudándome, cambiando mi ser//
//mi espíritu y mi alma se están llenando
Con el poder de tu Espíritu Santo,
Mi vida nunca más será igual//"""
        ),
        Hymn(
            id = 134,
            title = "La Samaritana",
            link = "https://www.youtube.com/watch?v=kfgld9jUczM",
            author = "",
            content = """I
Después de muchos días de camino el Salvador,
Llegó hasta Samaria junto al pozo de Jacob,
Cansado y sediento se dispuso a descansar,
Y con un poco de agua aquella sed poder calmar.
Mas Cristo no tenía como poderla extraer,
Entonces esperó a que le dieran de beber,
Hasta que una mujer samaritana se acercó,
Y fue así como el maestro al pasar le suplicó:

Coro
Dame de beber, calma mi ansiedad,
Traigo mucha sed después de tanto caminar.
Si supieras quién pidiéndote está,
Tú le pedirías agua celestial.

II
Cuando a la samaritana Jesucristo descubrió,
En aquel mismo momento agua santa le pidió,
Y Jesús que conocía dentro de aquel corazón,
Con amor y con ternura le brindó la salvación.
Sin demora y muy gozosa se dispuso a regresar,
Y anunciaba a Jesucristo recorriendo la ciudad.
Quizás hoy el Salvador vino a tu lado a descansar,
Y sediento cual entonces también te suplicará:"""
        ),
        Hymn(
            id = 135,
            title = "Dios está aquí, que precioso es",
            link = "https://www.youtube.com/watch?v=61AWJuXZD1o",
            author = "",
            content = """Coro
Dios está aquí, que precioso es
Él lo prometió donde hay dos o tres
Quédate Señor con tu bendición, quédate Señor en cada corazón
Quédate Señor con tu bendición, quédate Señor en mí.

I
El espíritu de Dios se mueve, se mueve, se mueve
El espíritu de Dios se mueve dentro de mi corazón

II
Oh hermano deja que se mueva, se mueva, se mueva
Oh hermano deja que se mueva dentro de tu corazón"""
        ),
        Hymn(
            id = 136,
            title = "Frente a una flor",
            link = "https://www.youtube.com/watch?v=7vh2pFa5O_A",
            author = "",
            content = """I
Frente a una flor yo me detuve a ver un día,
Lo singular de la belleza que hizo Dios,
Si Salomón con todo el lujo que tenía,
No consiguió vestir como una simple flor.
Si lo silvestre de la vida es tan hermoso,
Como lo expresan las abejas del panal,
Si el Señor cuida de cosas tan pequeñas,
¡Oh cuántos más, Dios cuidará de la humanidad!

Coro
Frente a una flor mi corazón se eleva a Dios en oración,
Y es cada pétalo un misterio para mí
Y las tristezas que sufrí se desvanecen frente a Dios,
Porque si él cuida de una flor, cuida de mí.

II
Y la canción que vuela por el aire manso,
Llena de ensueño la armonía vegetal,
Aunque los hombres hagan cosas muy hermosas,
La simple flor me está diciendo mucho más.
La flor no es fruto del evolucionismo,
No puede ser fruto de la casualidad,
No es la suma del espacio más el tiempo,
Ni mutación de seres vivos del azar.
La flor fue hecha por un Dios que es creador universal,
Inteligente, afectivo y personal.
Él da salud, él creará belleza, vida integral,
Son pinceladas de alguien que sabe pintar."""
        ),
        Hymn(
            id = 137,
            title = "Toda la gloria a Jesús",
            link = "https://www.youtube.com/watch?v=Xq-9P-ZUwR8",
            author = "",
            content = """//Toda la gloria a Jesús
Toda la honra a Jesús
Todas las loas a Jesús
Demos honra y gloria
Siempre a Jesús//"""
        ),
        Hymn(
            id = 138,
            title = "Él llevó mis cargas",
            link = "https://www.youtube.com/watch?v=M7tIo5ALNr0",
            author = "",
            content = """I
No hay problema muy grande que Dios no resuelva,
No hay montaña muy alta que Dios no la mueva,
No hay tormenta oscura que Dios no la calme,
No hay dolor ni angustia que Dios no la sane

Coro
Si él llevó las cargas del mundo,
Sobre sus hombros,
También tu carga, mi hermano, él la llevará
Si él llevó las cargas del mundo,
Sobre sus hombros,
Las cargas que a mí me agobian él las llevará

II
Él dijo ven hacia mí, los trabajados y cansados
Que yo os haré descansar."""
        ),
        Hymn(
            id = 139,
            title = "No dejes de luchar",
            link = "https://www.youtube.com/watch?v=hRv7jDICKdU",
            author = "Conjunto Amigo Fiel",
            content = """I
Ya sin fuerzas, cansado, te sientes desmayar,
La adversidad asedia tu vida sin cesar,
Dobla tus rodillas, canta al Señor,
Él cambia la tristeza por gozo sin igual.

Coro
No dejes de luchar, Jesús es fiel
Él recompensará a aquel que es fiel,
No olvides que Jesús por ti murió,
Un poco sufrirás, más sufrió el Señor.

II
Afrentas y martirios, por causa del Señor,
Cuántos siervos soportaron gozosos por amor,
Gózate en la prueba y en la adversidad,
Tu ministerio cumple y permanece fiel."""
        ),
        Hymn(
            id = 140,
            title = "Tú eres Santo",
            link = "https://www.youtube.com/watch?v=V25WKWx6U9Q",
            author = "",
            content = """Sólo tú eres Santo, sólo tú eres digno
Tú eres hermoso y maravilloso
Derrama tu Espíritu aquí dentro mío,
Que tu gloria llene ahora mismo este lugar"""
        ),
        Hymn(
            id = 141,
            title = "No sé por qué Señor",
            link = "https://www.youtube.com/watch?v=lDRcfDaMpYE",
            author = "",
            content = """No sé por qué Señor, será tu voluntad
Que sufra yo tantas angustias y dolor,
No sé por qué Señor, será tu voluntad
Que sufra así después de darte a ti mi amor.

Pero sé que tu voluntad
Será lo mejor para mí,
Si proviene de ti.
Pero sé que si yo confío en ti,
Nunca más habrá dolor tan sólo amor.

No sé por qué Señor, será tu voluntad
Que yo transite el valle de sombra y dolor,
No sé por qué Señor, será tu voluntad
Que todo plan fracase en medio del dolor.

A veces al llorar, me olvido de tu amor,
De cuanto diste por hacerme tan feliz.
Perdona mi dudar, la fe me acerque a ti,
Porque hoy yo sé cuál es la causa de este mal"""
        ),
        Hymn(
            id = 142,
            title = "Bendición vengo a buscar",
            link = "https://www.youtube.com/watch?v=6kh_kfHwbW4",
            author = "",
            content = """//Bendición vengo a buscar y sin ella no me iré//
//Y dame más Señor de tu gran poder//
//Jacob luchó con un ángel, fue por una bendición//
//Y dame más Señor de tu gran poder//"""
        ),
        Hymn(
            id = 143,
            title = "¡Cuán bello es el Señor!",
            link = "https://www.youtube.com/watch?v=GFBgK7unj8E",
            author = "",
            content = """//¡Cuán bello es el Señor, cuán hermoso es el Señor!
¡Cuán bello es el Señor, hoy le quiero adorar!//

//La belleza de mi Señor, nunca se agotará,
La hermosura de mi Señor, siempre resplandecerá//"""
        ),
        Hymn(
            id = 144,
            title = "Fuera la tristeza",
            link = "https://www.youtube.com/watch?v=ANcfQiZit-E",
            author = "",
            content = """Unas veces llora el corazón mío,
Otras veces gime, sin hallar la paz
Pues estando triste y muy abatido
Es que desde mi alma brotó este cantar:

Fuera la tristeza, fuera el dolor,
Cristo vive y reina y es el Señor,
Por eso le alabo con el corazón,
Porque Cristo vive, porque Cristo reina y es el Señor.

Si te sientes triste y muy deprimido,
Y no hallas consuelo para tu dolor,
Levanta tu fe, Dios está en su trono,
Y canta conmigo esta simple canción:"""
        ),
        Hymn(
            id = 145,
            title = "Aunque tenga que cruzar",
            link = "https://www.youtube.com/watch?v=l8N9n84t-Mg",
            author = "",
            content = """Aunque tenga que cruzar montes y valles
Predicando tu palabra mi Señor,
Aunque guerra me declare el enemigo,
Si tú vas conmigo, nada temeré.

//No temeré Señor, si voy contigo,
No temeré Señor, si vas conmigo,
No temeré Señor, al enemigo,
Porque el ángel del Señor irá conmigo//"""
        ),
        Hymn(
            id = 146,
            title = "Tu amor por mí",
            link = "https://www.youtube.com/watch?v=3eWnYiluPk0",
            author = "",
            content = """Tu amor por mí es más dulce que la miel,
Y tu misericordia es nueva cada día.

//Es por eso que te alabo, es por eso que te sirvo,
Es por eso que te doy todo mi amor//"""
        ),
        Hymn(
            id = 147,
            title = "La Biblia",
            link = "https://www.youtube.com/watch?v=1nbYyRCuSZM",
            author = "",
            content = """La luz del día está entrando ya por la ventana,
Los pajaritos afuera se oyen cantar,
El viejo libro derrama su fresca enseñanza,
Sobre mi alma sedienta que va al manantial.

Hay algo nuevo en la Biblia en cada mañana,
Cosas preciosas que sólo Dios da para el alma,
Son alimento y son vida que sacia y que calma
Y que renueva las fuerzas para continuar.

Es tan sencilla que un niño la entiende y disfruta
De sus historias ya clásicas para contar
Y es tan profunda que sirve de mapa de ruta
Para el que perdió el camino y lo quiere encontrar.

Es un retrato de Cristo cabal y completo
Los evangelios, figura y personalidad,
Las cartas son el ropaje de su cuerpo entero,
Y el antiguo testamento es el fondo ideal."""
        ),
        Hymn(
            id = 148,
            title = "Que se deje sentir tu presencia",
            link = "https://www.youtube.com/watch?v=Z7wfI_NTDYI",
            author = "",
            content = """Que se deje sentir tu presencia y que llene nuestro corazón,
Que se deje sentir tu presencia, te pedimos Señor.

//Te alabo// y mi alma no se cansa de alabarte
//Te alabo// mientras vida exista en mi ser."""
        ),
        Hymn(
            id = 149,
            title = "Mi pensamiento eres tú",
            link = "https://www.youtube.com/watch?v=IRa2lmY6a78",
            author = "",
            content = """///Mi pensamiento eres tú Señor/// Mi pensamiento eres tú.

//Porque tú me has dado la vida, porque tú me has dado el existir,
Porque tú me has dado cariño, me has dado amor//"""
        ),
        Hymn(
            id = 150,
            title = "Amigo fiel",
            link = "https://www.youtube.com/watch?v=w6T7dkkmC9s",
            author = "Conjunto Amigo Fiel",
            content = """Cuando en luchas se encuentra mi alma,
Yo acudo a Él, pronto a Él,
Y le pido ¡Oh Jesús, ayúdame!
¡Oh, Tú ves que soy un débil pecador!

Señor Jesús, Gracias te doy
Porque eres Tú mi amigo fiel,
Y en las luchas y en las pruebas
Tú me ayudas a vencer.
Señor Jesús, Gracias te doy
Porque sin ti yo nada soy
Ayúdame para poder serte más fiel.

Oh mi Dios, mi alma te alaba,
Porque tú eres mi amigo fiel.
Y por eso mi alma hoy está gozosa
Por tu gran misericordia para mí.

Aunque tenga muchas pruebas
Siempre me ayudarás,
Y aunque por el fuego pase,
Quemarme no podrá
Y aunque pase por las aguas,
Las aguas no me anegarán
Porque tú estás conmigo, me ayudarás."""
        ),
        Hymn(
            id = 151,
            title = "Él es mi paz",
            link = "https://www.youtube.com/watch?v=FCd4o1ICN-4",
            author = "",
            content = """//Él es mi paz, se ha llevado todos mis temores,
Él es mi paz, él es mi paz//

//Pongo toda mi ansiedad sobre Él, pues cuida de mí
Él es mi paz, él es mi paz//"""
        ),
        Hymn(
            id = 152,
            title = "Llénanos de ti",
            link = "https://www.youtube.com/watch?v=QUDIujMcwLY",
            author = "",
            content = """¡Oh deja que el Señor te envuelva en su Espíritu de amor,
Satisfaga hoy tu alma y corazón!
Entrégale lo que Él te pide y su Espíritu vendrá,
Sobre ti y vida nueva te dará.

Cristo ¡Oh Cristo! Ven y llénanos
Cristo ¡Oh Cristo! Llénanos de ti.
Alzamos nuestra voz con gozo, nuestra alabanza a Ti,
Con dulzura te entregamos nuestro ser.
Entrega toda tu tristeza en el nombre de Jesús,
Y abundante vida hoy tendrás en Él."""
        ),
        Hymn(
            id = 153,
            title = "Clama a mí",
            link = "https://www.youtube.com/watch?v=Ok5gHq52xss",
            author = "Conjunto Amigo Fiel",
            content = """Hay momentos en la vida donde todo oscuro está
No encuentras la salida a tu triste condición,
Más hoy debes recordar que la llave tienes ya
Para abrir puertas de gracia, sólo tienes que orar.

Clama a mí, dice el Señor
Clama a mí con fe y yo responderé
No tardes más, fuerza y gracia alcanzarás,
Ve y pide hoy con fe, la respuesta llegará.

Te encuentras hoy cautivo del pecado y de la aflicción,
Te persigue el enemigo, te ha robado la oración,
Más hoy debes recordar que la llave tienes ya
Para abrir puertas de gracia, sólo tienes que orar."""
        ),
        Hymn(
            id = 154,
            title = "Jehová está en su templo",
            link = "https://www.youtube.com/watch?v=CxzmOwTTK2U",
            author = "",
            content = """Jehová está en su templo, alábale al que vive

//Alábale, alábale, alábale al que vive//"""
        ),
        Hymn(
            id = 155,
            title = "Tendrás consuelo",
            link = "https://www.youtube.com/watch?v=xvNlZsym-Vs",
            author = "",
            content = """Elías pedía and Dios respondía, fuego y lluvia del cielo, cayó,
Y cuando estuvo triste, Dios le consoló,
Y en un torbellino de carros de fuego al cielo voló.
Si tú quieres que Dios te responda, mira hacia el cielo,
Verás a un Dios grande, que nunca está ausente, que siempre está atento,
Si estás triste, cansado y sin fuerzas, tirado en el suelo,
Hoy siente que Dios ha enviado su ángel trayendo consuelo.

Trayendo consuelo ha venido Jesús, para el alma triste, llena de dolor,
Para aquel que no entiende por qué sucedió y sólo hay preguntas,
Él puede entender si sufriendo estás, Él sabe de muerte y de soledad,
Mira hacia adelante lo mejor está allí, un gran triunfo te espera.
Por qué llorar si de aquí pasarás a un servicio más alto,
Por qué lamentas creyendo perder, si al final has ganado,
//Él puede entender si sufriendo estás, Él sabe de muerte y de soledad,
Mira hacia adelante, lo mejor está allí, un gran triunfo te espera//

Ya siento alegría, Dios me consoló,
Y siento el fuego del cielo que arde en mi corazón,
Existen preguntas, por qué sucedió,
Más veo su gloria, la lluvia cayendo, el cielo llegó.
Si tú quieres que Dios te consuele, mira hacia el cielo,
Verás a Jesús que con autoridad hoy te dice: “No llores”
Si tú quieres tener fortaleza, come y bebe,
Que largo camino de luchas tendrás, más el cielo te espera."""
        ),
        Hymn(
            id = 156,
            title = "Estamos reunidos",
            link = "https://www.youtube.com/watch?v=WbyM5Mm7z2o",
            author = "",
            content = """//Estamos reunidos aquí Señor,
Porque hemos conocido tu amor,
Traemos en nuestros labios un nuevo cántico de loor,
Pues tu gloria ha llenado este lugar//

Aleluya, pues tu gloria ha llenado este lugar."""
        ),
        Hymn(
            id = 157,
            title = "Ahora levántate",
            link = "https://www.youtube.com/watch?v=A6dU01q08Zo",
            author = "",
            content = """Señor hoy me doy cuenta que ya no soy tan fuerte,
Como ayer yo creía que todo lo podía,
El fuego en gran escala que vino contra mí,
Esta vez me alcanzó y sin fuerzas quedé.
Descubrí que soy frágil, sensible y vulnerable,
Y me he sentido sola en medio de la mar,
Más cuando parecía que todo era en vano,
Vino a mí tu palabra diciendo ven

Pero ahora levántate, ponte firme sobre tus pies,
Para esto yo te llamé, para ponerte por testigo
De las cosas que has visto y verás.
Grandes cosas te mostraré, largo camino te resta aún,
Ahora te envío a predicar para que abras tú sus ojos,
Se conviertan a la luz de Dios, ahora levántate.

Hija te vi muy triste, casi desalentada,
En tu rostro observaba la carga y el dolor,
El fuego para el oro resulta necesario,
Pero confía hija por ti vencí.
A veces es posible un poco de quebranto,
Para que no confíes en tu fuerza y valor,
En medio de tus luchas, dolores y tristezas
No te he dejado sola tu fe triunfó."""
        ),
        Hymn(
            id = 158,
            title = "Dame un nuevo corazón",
            link = "https://www.youtube.com/watch?v=tfY4Se7ZRwU",
            author = "",
            content = """//Dame un nuevo corazón, Señor,
un corazón para alabarte más,
Un corazón para adorarte,
dame un nuevo corazón//

//Limpio como el cristal,
dulce como la miel,
Mi corazón será como el tuyo//"""
        ),
        Hymn(
            id = 159,
            title = "Eutico",
            link = "https://www.youtube.com/watch?v=Og_VYH0oR7U",
            author = "",
            content = """Un joven llamado Eutico, que sentado en una ventana,
Rendido en un sueño profundo cuando Pablo allí predicaba,
//Y vencido por el mismo sueño, se cayó del tercer piso abajo,
Y muerto él fue levantado, y abrazándole Pablo les dijo:
“No os alarméis porque el joven no está muerto, sino que está vivo”//

Joven despiértate ya, porque hay peligro de muerte,
Si en la ventana tú estás, te distrae el mundo y puedes perderte,
Y si te sientes morir, Cristo hoy quiere abrazarte,
Vida eterna tendrás y el gozo del cielo tú recibirás.

Cuántas veces tú me hablabas y yo sentado en una ventana,
Perdido y sin ver el peligro de morir yo me encontraba,
//Y vencido por tu amor inmenso, despertándome pronto me dijo:
“No os alarméis porque tú no estás muerto, sino que estás vivo”//"""
        ),
        Hymn(
            id = 160,
            title = "No me va a fallar",
            link = "https://www.youtube.com/watch?v=n3_64WIzvmw",
            author = "",
            content = """//Puedo confiar en el Señor, no me va a fallar//
Si el sol llegase a oscurecer y no brille más,
Puedo confiar en el Señor, no me va a fallar.

//Puedo descansar// pues en su amor no me va a fallar
Si el sol llegase a oscurecer y no brille más,
Puedo confiar en el Señor, no me va a fallar."""
        ),
        Hymn(
            id = 161,
            title = "En el hogar",
            link = "https://www.youtube.com/watch?v=1oEM4Rdq9OA",
            author = "",
            content = """//En el hogar, en el hogar,
Tú necesitas a Jesús//

//Y verás que bien se vive//
Con Jesús en el hogar."""
        ),
        Hymn(
            id = 162,
            title = "Un palacio tengo",
            link = "https://www.youtube.com/watch?v=7ClMBK9mD9A",
            author = "",
            content = """Un palacio tengo más lindo que el sol
Un palacio tengo más lindo que el sol
Un palacio tengo más lindo que el sol
En el más allá

Bendito Cristo contigo estaré
Bendito Cristo contigo estaré
Bendito Cristo contigo estaré
En el más allá

Si tú quieres ir al cielo, ven a Jesús
Si tú quieres ir al cielo, ven a Jesús
Si tú quieres ir al cielo, ven a Jesús
Ven sin demorar"""
        ),
        Hymn(
            id = 163,
            title = "Unos brazos se abrieron",
            link = "https://www.youtube.com/watch?v=aM9lx9Aqru4",
            author = "",
            content = """Unos brazos humildes se abrieron,
En la trágica cruz del Calvario,
El dolor de los clavos sufrieron,
Y la cruz con su sangre bañaron.

//Para dar salvación al más vil pecador,
Y librarlo del mal que vendrá//"""
        ),
        Hymn(
            id = 164,
            title = "Somos un pequeño pueblo",
            link = "https://www.youtube.com/watch?v=nSaF6_jItNU",
            author = "",
            content = """Somos un pequeño pueblo muy feliz
Somos un pequeño pueblo muy feliz
El Señor es nuestro guía y nos colma de alegría
Somos un pequeño pueblo muy feliz."""
        ),
        Hymn(
            id = 165,
            title = "Sé que Jesús vendrá",
            link = "https://www.youtube.com/watch?v=sasjAWyP1aY",
            author = "Conjunto de Dorrego",
            content = """Sé que Jesús vendrá, a buscarme y preparado quiero estar,
Sé que muy pronto veré a su Gloria descender con poder,
Sé que mi llanto terminará,
Cuando en las nubes Jesús me venga a buscar,
Sé que con santos al fin he de morar,
Mi alabanza se unirá a la del coro que habrá de cantar.

Más allá del sol, Señor, quiero llegar,
Más allá del sol sé que me esperas,
Yo quiero vivir junto a ti, quiero alcanzar esa calma,
En el cielo no habrá más tristezas,
Pues el llanto y el dolor no entrarán,
En el cielo corona me espera y Jesús me dará.

Y lo que yo tengo aquí, aunque mucho o muy poco quedará,
Hay un lugar más allá, de hermosura y riqueza sin igual,
Puertas de perlas adornan mi pueblo
Y un río limpio brillante cual el cristal,
Calles de oro recorren mi ciudad,
Donde noche no habrá porque Dios siempre allí brillará.

Gracias Señor, por tanto amor,
Mostrado hacia mí, muriendo en aquella cruz,
Para que hoy yo pueda gozar de una patria feliz,
Preparada por ti para mí, para mí."""
        ),
        Hymn(
            id = 166,
            title = "El alfarero",
            link = "https://www.youtube.com/watch?v=t3MNv0XqOwo",
            author = "",
            content = """//Señor yo quiero abandonarme
Como el barro en las manos del alfarero
Toma mi vida y hazla de nuevo,
Yo quiero ser, yo quiero ser un vaso nuevo//"""
        ),
        Hymn(
            id = 167,
            title = "Quiero cantar una linda canción",
            link = "https://www.youtube.com/watch?v=zNTf0cG1x_A",
            author = "",
            content = """Quiero cantar una linda canción
De aquel que mi vida cambió,
Quiero cantar una linda canción
De aquel que me transformó.

Es mi amigo Jesús
Es mi amigo más fiel,
Él es Dios, él es Rey
Es amor y verdad

Sólo en él encontré esa paz que busqué
Sólo en él encontré la felicidad"""
        ),
        Hymn(
            id = 168,
            title = "Jesús es fiel",
            link = "https://www.youtube.com/watch?v=iZBXnQUGbpg",
            author = "",
            content = """Espéralo aunque tardare vendrá y no tardará,
Jesús es fiel y el cumplirá, sus maravillas pronto verás.

El pedido que le has hecho, él lo oye,
El problema que te estanca, él lo ve,
No perdáis vuestra confianza,
Porque tiene un grande galardón

Tu paciencia es necesaria, espérale,
No preguntes hasta cuando, pronto será,
Hay un tiempo señalado,
Permanece, sigue firme hasta el final.

Espéralo aunque tardare vendrá y no tardará,
Jesús es fiel y el cumplirá, sus maravillas pronto verás.

Espéralo aunque tardare vendrá y no tardará,
Jesús es fiel y el cumplirá, sus maravillas pronto verás."""
        ),
        Hymn(
            id = 169,
            title = "El hombre de Galilea",
            link = "https://www.youtube.com/watch?v=xdI-sTOEhnU",
            author = "",
            content = """////El hombre de Galilea va pasando va////

///déjalo que te toque/// y recibe su bendición
///déjalo que te toque/// y recibe su bendición."""
        ),
        Hymn(
            id = 170,
            title = "Más allá del sol",
            link = "https://www.youtube.com/watch?v=vPrAzFrdXgc",
            author = "",
            content = """Aunque en esta vida no tengo riquezas
Sé que allá en la gloria tengo mi mansión.
Cual alma perdida entre las pobrezas,
De mí, Jesucristo tuvo compasión.

//Más allá del sol// Yo tengo un hogar,
Hogar, bello hogar, más allá del sol.
//Más allá del sol// Yo tengo un hogar,
Hogar, bello hogar, más allá del sol.

Así por el mundo, yo voy caminando,
De pruebas rodeado y de tentación,
Pero Jesucristo que me está probando
Me llevará a salvo hasta su mansión

A todas las razas del linaje humano
Cristo quiere darles plena salvación,
También una casa no hecha de mano
Fue a prepararnos a la Santa Sion."""
        ),
        Hymn(
            id = 171,
            title = "He peleado la batalla",
            link = "https://www.youtube.com/watch?v=QZx1rhGbFx4",
            author = "",
            content = """Al Señor yo le quiero servir porque sé que él me puede salvar
Y promete llevarme a vivir donde siempre le puede mirar.

He peleado la batalla Señor,
Te diré, mi carrera terminó,
Y también he guardado la fe,
Sólo espero me vengas a llevar.

La corona de justicia ya está preparada a quién lucha aquí,
El Señor, justo juez la dará, el día en que tenga que venir.

Tú también te debes preparar, síguele llevando hoy tu cruz,
El Señor pronto viene a buscar a los que aman la venida de Jesús."""
        ),
        Hymn(
            id = 172,
            title = "No temáis",
            link = "https://www.youtube.com/watch?v=geRWBny4NNE",
            author = "",
            content = """Con la barca en alta mar, los discípulos estaban,
Y las olas azotaban con furia sin igual,
El viento era contrario y el mar un remolino,
Pero alguien ha venido caminando sobre el mar.

No temáis porque ha venido el que gobierna la mar
No temáis porque ha venido, el que cuida de tu andar.
Si las olas son muy grandes y el viento contrario es,
No desmayes, no te espantes, porque verás su poder
No desmayes, no te espantes, porque Jesús es fiel.

Espantados y turbados miran ese personaje
Que les habla con dulzura tened ánimo yo soy
Si eres tú Jesús permite, que yo vaya hacia ti
Dijo Pedro al maestro y el Señor le dijo: “ven”

Si tu alma hoy se encuentra en medio de la tormenta,
Si el viento es contrario y te sientes desmayar,
Hoy Jesús te está ofreciendo el auxilio que has pedido,
Es por eso que ha venido caminando sobre el mar."""
        ),
        Hymn(
            id = 173,
            title = "Vaso de Honra",
            link = "https://www.youtube.com/watch?v=Az4SY10F88M",
            author = "",
            content = """Hazme un vaso de honra,
Padre hazme un vaso de fe,
Lléname con tu luz y gracia, y honra,
Que mi corazón se llene sólo de ti.

Señor quiero siempre hacer tu querer, tu ley obedecer,
Me siento tan débil, más con tu poder, he de vencer,
Te rindo mi vida para tu servicio, donde me mandes iré,
Y de día en día seré mensajero llevando amor y paz."""
        ),
        Hymn(
            id = 174,
            title = "Grandes cosas hizo Dios",
            link = "https://www.youtube.com/watch?v=6FR32elSknI",
            author = "",
            content = """//Grandes cosas hizo Dios, todas hermosas para mí//
Maravilloso es, sentir amor, ama, ama, como te ama Dios.

Es el fruto de su amor que nos ha reunido aquí,
Alabemos al Señor con todo el corazón.
Maravilloso es sentir amor, ama, ama, //como te ama Dios//"""
        ),
        Hymn(
            id = 175,
            title = "Alcancé Salvación",
            link = "https://www.youtube.com/watch?v=WHqif23mKlA",
            author = "",
            content = """De paz inundada mi senda ya esté,
O cúbrala un mar de aflicción,
Mi suerte cualquiera que sea diré:
Alcancé, alcancé salvación.

//Alcancé salvación//
Alcancé, alcancé salvación.

La fe se convierte en gran realidad
Al irse la niebla veloz,
Desciende Jesús con su gran majestad
Aleluya estoy bien con mi Dios."""
        ),
        Hymn(
            id = 176,
            title = "Tu presencia está aquí",
            link = "https://www.youtube.com/watch?v=tLMTInNCGiw",
            author = "",
            content = """El brillo de este mundo se opaca ante ti,
La gloria de esta tierra nada es,
Todo cae in tu presencia ¡Oh Rey!
Qué hermosa es tu presencia Señor.

//Tu presencia está aquí,
Tu presencia está en mí
Y tu pueblo te recibe alabándote
Qué hermosa es tu presencia Señor//"""
        ),
        Hymn(
            id = 177,
            title = "Vamos escalando peldaños",
            link = "https://www.youtube.com/watch?v=bc4n19i0b6o",
            author = "",
            content = """//Vamos escalando peldaños, vamos llevando la cruz
Sigamos el camino angosto, con Cristo es mucho mejor//

//Ya viene la recompensa, ya no voy a llorar
Tengo a Cristo en mi vida, por eso puedo escalar.//

//A veces me siento débil, ya no puedo escalar
Levanto mis manos al cielo, y Cristo fuerzas me da//

//Ya viene la recompensa, ya no voy a llorar
Tengo a Cristo en mi vida, por eso puedo escalar.//"""
        ),
        Hymn(
            id = 178,
            title = "Junto al arroyo de Querit",
            link = "https://www.youtube.com/watch?v=pRZMzjCjBZA",
            author = "",
            content = """Cuando hay sombras, cuando hay dudas en tu andar,
Cuando hay pruebas que no puedes afrontar,
Cuando tu ánimo comienza a menguar,
No vaciles Dios aún contigo está.
Al igual que Elías tú puedes confiar,
En que Dios puede tu vida preservar,
Todavía hay un refugio para ti,
Para ti junto al arroyo de Querit.

Fue allí junto al arroyo de Querit,
Donde Elías pudo al mundo resistir,
Fue aceptando del Señor su voluntad,
Como pudo junto a él al fin morar.
Nunca pienses que el Señor te abandonó,
Que ha dejado de alentar tu corazón,
Todavía hay un refugio para ti,
Para ti junto al arroyo de Querit.

Fue Elías un fiel siervo del Señor,
Un milagro del cuidado de su amor,
Fue guiado como puede guiarte a ti,
En un mundo tan difícil y hostil.
No te aflijas tú también puedes lograr,
Como Elías junto a Dios poder morar,
Todavía hay un refugio para ti,
//Para ti junto al arroyo de Querit.//"""
        ),
        Hymn(
            id = 179,
            title = "Renuncié a la miseria",
            link = "https://www.youtube.com/watch?v=MdkjtTMCpaE",
            author = "",
            content = """Hoy renuncié a la miseria,
Al pecado que había en mí, para servir a mi rey
Hoy quedó atrás mi pasado, mi vieja vida,
Mis malos actos para servir a mi rey
Hoy decido revelar mi vida para poderte entregar
Un servicio que agrade a tus ojos, que pueda honrar.

Hoy decido volver a la fuente, cambiar mi camino,
Claudicar entre dos pensamientos de nada me sirve,
//Renunciar a la vida pagana, hoy yo debo tomar decisión,
Yo y mi casa serviremos al Señor//

Renunciar es volver a empezar, es dejar,
No volver a fallar, para servir a mi Rey,
Es tomar la armadura y pelear,
Y vencer toda hueste del mal, para servir a mi rey,
Es decirle que no al enemigo, y así poder resistir,
Y el que anhela verte vencido tendrá que huir."""
        ),
        Hymn(
            id = 180,
            title = "Cristo ha tomado mi vida",
            link = "https://www.youtube.com/watch?v=8ZuyDgZwTz0",
            author = "",
            content = """//Cristo ha tomado mi vida,
Ha tomado mi vida y no la quiere dejar//
Perdido estaba yo, más Cristo me encontró,
Cristo ha tomado mi vida y no la quiere dejar.

//Yo ///me sigo enamorando/// de Él//
Perdido estaba yo, más Cristo me encontró,
Cristo ha tomado mi vida y no la quiere dejar."""
        ),
        Hymn(
            id = 181,
            title = "Como el agua cubre la mar",
            link = "https://www.youtube.com/watch?v=R6Qx0UVvyGQ",
            author = "",
            content = """//Como el agua cubre el mar,
Todo el mundo se ha de llenar,
Del amor de Dios y la gloria del Señor,
Como el agua cubre el mar.//"""
        ),
        Hymn(
            id = 182,
            title = "La Gloria de Jehová",
            link = "https://www.youtube.com/watch?v=WzWK83-4NeA",
            author = "",
            content = """La Gloria de Jehová, bajó al Sinaí
Y aquel monte temblaba porque Dios estaba allí

//Dios estaba allí, Dios estaba allí
Y aquel monte temblaba porque Dios estaba allí//

La Gloria de Jehová, ha llegado hasta aquí
Por eso canto alegre porque Dios está aquí

//Dios está aquí, Dios está aquí
Por eso canto alegre porque Dios está aquí//"""
        ),
        Hymn(
            id = 183,
            title = "Ten confianza",
            link = "https://www.youtube.com/watch?v=J1fOyOoh0zI",
            author = "",
            content = """No descansa, el diablo no duerme,
Está intentando intimidarte, para que te sueltes,
Pero aquella zarza no deja de arder.

Agazapado, espera el momento,
Entre tinieblas, rugiendo fuerte,
Pero aquella zarza no deja de arder.

Ten confianza, el ángel del Señor
Ha hecho un cerco a tu alrededor
Y el diablo no puede por más que lo intente,
Él no puede tocarte.
Has sido comprado a precio de sangre,
Por el Rey que vive, Cordero inmolado,
El Dios de Israel.

Es verdad lo que dicen, que para nada sirvo,
Si muchas metas me he trazado y nunca he llegado,
Pero aquella zarza no deja de arder."""
        ),
        Hymn(
            id = 184,
            title = "Te alabo",
            link = "https://www.youtube.com/watch?v=kUadWiX0U4w",
            author = "",
            content = """Hasta en mis lágrimas hay alabanzas
Porque mis ojos te alaban así,
Tú me salvaste, me perdonaste,
Me diste vida cuando creí."""
        ),
        Hymn(
            id = 185,
            title = "Hijo pródigo (Villanueva)",
            link = "https://www.youtube.com/watch?v=FuLeMPU4ZpE",
            author = "",
            content = """I
El hijo se va alegre del hogar, llevando los bienes que al padre pidió,
Juntándolo todo ansioso se va, a tierras lejanas detrás de su afán.
But a su padre triste dejó, dos lágrimas nublan sus ojos de amor,
Mirando el camino en que el hijo se fue,
Pensando que pronto podrá regresar.

Coro
Hijo pródigo vuelve, tu padre te espera,
Ya no vagues así, es tiempo de regresar,
En tu casa tendrás mucha abundancia de pan,
Vuelve al hogar, oh sí vuelve al hogar
//te está esperando tu padre de amor//

II
Lejos del padre, lejos del hogar, se halla cansado, sólo y sin amor,
Todos sus bienes en deleites gastó, y sin sus amigos sólo se sintió.
Junto a los cerdos comiendo él está, con hambre, con frío, llora su pesar,
Recuerda entonces al padre en su hogar y volviendo en sí decide regresar."""
        ),
        Hymn(
            id = 186,
            title = "Quiero alabarte",
            link = "https://www.youtube.com/watch?v=WDvUV5joiKU",
            author = "",
            content = """//Quiero alabarte más y más aún//
Seguir tu voluntad,
Tu gracia conocer,
Quiero alabarte.

//Las aves del cielo cantan para Ti,
Las bestias del campo reflejan tu poder,
Quiero cantar, quiero levantar mis manos a Ti//"""
        ),
        Hymn(
            id = 187,
            title = "Este es el día",
            link = "https://www.youtube.com/watch?v=3ddYhwppVFs",
            author = "",
            content = """//Este es el día//, //que hizo el Señor//

//Me alegraré// y //me gozaré//

Este es el día que hizo el Señor,
Me alegraré y me gozaré

//Este es el día// que hizo el Señor."""
        ),
        Hymn(
            id = 188,
            title = "Mi cruz no es tan pesada",
            link = "https://www.youtube.com/watch?v=OmIe5aruAUU",
            author = "",
            content = """Me pregunté mil veces sin poder comprender,
Por qué mi cruz doblaba siempre mi sufrido caminar,
Y hablé con Dios mirando al cielo y con angustia exclamé:
¿Por qué Señor, por qué mi cruz pesada es?
Más Dios me hizo comprender la realidad,
Que equivocado estaba yo cuando mi cruz quise dejar,
Y poco a poco con paciencia fue mostrándome el Señor,
Que no era tan pesada esa cruz que me otorgó.

Y me mostró a Cristo coronado de espinas en la cruz,
A Esteban perdonando a sus verdugos al morir,
A Pablo escribiendo en una húmeda prisión,
Y pude oír el cántico glorioso de los mártires de Dios,
Entonces comprendí que no era dura aquí mi cruz,
Y dije: “Oh Señor, Señor perdóname”

La cruz se torna más pesada si dejamos de orar,
Sin el poder de su palabra es imposible caminar,
Sombra de duda y desaliento pareciera detener,
Mi abnegado viaje hacia la ciudad de luz.

Si entregamos nuestro yo en el altar de Dios,
Si renunciamos a este mundo por hacer su voluntad,
En aquel día que arribemos a la ciudad de Sion,
Por vida eterna y por corona, nuestra cruz se cambiará."""
        ),
        Hymn(
            id = 189,
            title = "Yo vivo Señor porque Tú vives",
            link = "https://www.youtube.com/watch?v=kEPnWr3Y6dY",
            author = "",
            content = """Yo vivo Señor porque tú vives,
Porque tú vives, Señor es que yo vivo,

//Me das consuelo, me das abrigo
Y en la aflicción, Mi Señor, estás conmigo.//

Soy salvo, Señor tú me salvaste,
Tú me salvaste Señor eternamente,

//Yo voy al cielo, voy a la gloria,
Porque Señor tú me diste la victoria//"""
        ),
        Hymn(
            id = 190,
            title = "Auméntame la fe",
            link = "https://www.youtube.com/watch?v=IMlt4Nwx_sQ",
            author = "Conjunto Amigo Fiel",
            content = """Es pues la fe, la certeza de lo que se espera,
La convicción de lo que no se ve,
Sin fe es imposible agradar a Dios,
Por gracia somos salvos, por medio de la fe,
Escucha Oh Dios mi ruego,
Auméntame la fe.

Auméntame la fe, y milagros veré,
Cada día victorioso ser,
Es mi anhelo Señor.
Auméntame la fe y que en mí puedan ver
Que yo sirvo a un Dios eterno y fiel
Lleno de gracia y de poder.

Si tu fe como un grano de mostaza fuere,
Montes moverás,
Por fe el justo vivirá,
Por gracia somos salvos, por medio de la fe,
Escucha Oh Dios mi ruego,
Auméntame la fe."""
        ),
        Hymn(
            id = 191,
            title = "Cristo me ama",
            link = "https://www.youtube.com/watch?v=WD3WkpjpHVA",
            author = "",
            content = """Cristo me ama bien lo sé,
Su palabra dice así:
“Que los niños son de aquel,
Quien es nuestro amigo fiel”

///Cristo me ama/// la Biblia dice así.

Cristo me ama Él murió
Y la Gloria nos abrió
Mis pecados borrará
Me dará la entrada allá"""
        ),
        Hymn(
            id = 192,
            title = "Te mando que te esfuerces",
            link = "https://www.youtube.com/watch?v=QpD1QfF_yk8",
            author = "",
            content = """//Mira que te mando que te esfuerces
Y que seas valiente y que seas valiente//

//No temas y desmayes porque el Señor tu Dios
Estará contigo dondequiera que tú vayas//"""
        ),
        Hymn(
            id = 193,
            title = "Si te sientes tan cansado",
            link = "https://www.youtube.com/watch?v=A0kEjfpTbdw",
            author = "",
            content = """Si te sientes tan cansado, y tu cruz está pesada,
Y si piensas que no puedes proseguir esta jornada,
Alza tus ojos a los cielos y contempla a mi Jesús,
Quien murió allá en la cruz, resurgió con vida y luz,
Él te quiere ayudar.

Jesús pasa por aquí, y él te quiere bendecir,
Si quisieras recibir abre hoy tu corazón
Bendición recibirás,
El cielo está abierto, el poder ha descendido,
Es la gloria del Señor, y Él se manifestó,
La victoria él te dará.

Ya no pienses en tristeza, problema o aflicción,
Pues con Cristo en tu vida,
Siempre habrá consolación
Si cayeres de flaqueza, puedes tener esta certeza,
Que el Señor es poderoso, y él es victorioso,
Su ayuda él te dará."""
        ),
        Hymn(
            id = 194,
            title = "Me bautizó",
            link = "https://www.youtube.com/watch?v=MssQCtBi2vY",
            author = "",
            content = """Una noche estaba orando, el Señor me visitó,
Y con su Espíritu Santo de su gloria me invadió,
Y sentí que derramaba como un fuego celestial,
//su poder tocó mi alma y nuevas lenguas me hizo hablar//

///Amor/// que un día se hizo carne,
Jesús mi Salvador él vino así a salvarme,
Por amor él padeció, por amor también murió,
Por amor a aquella noche el Salvador me bautizó.

Oh que hermoso sería, si como aquellos ciento veinte,
Su iglesia hoy viviera la promesa nuevamente,
Y ese mismo viento recio comenzara aquí a soplar
//y con su Espíritu Santo en nuevas lenguas me hiciera hablar//"""
        ),
        Hymn(
            id = 195,
            title = "Mujer virtuosa",
            link = "https://www.youtube.com/watch?v=E8bVIB_oq4s",
            author = "",
            content = """Mujer virtuosa, quién la hallará,
El corazón de su esposo confiado está,
Es la mujer que da siempre el bien y no el mal,
Es quién trabaja y trabaja sin descansar,
Aunque cansada y de noche se levantará,
Y el alimento a sus hijos no faltará.

Es la iglesia, la mujer virtuosa,
Su esposo en ella confiado está,
Que trabaja y cansada soporta la noche,
Pero siempre se levantará.
Que se ciñe de fuerzas para continuar,
Que su lámpara brilla no se apagará,
Que alimenta al pobre y abriga al desnudo,
Y se goza de lo que vendrá.

Ciñe de fuerzas sus lomos, cada día,
Su lámpara siempre encendida, no se apagará,
Es quien extiende sus manos al que pobre está,
No tiene temor del invierno abrigo les da,
Es quien se ríe y se alegra de lo porvenir,
Sus hijos se gozan, su esposo la llama “feliz”"""
        ),
        Hymn(
            id = 196,
            title = "Santo es el Señor",
            link = "https://www.youtube.com/watch?v=0MlGVTRhhMs",
            author = "Conjunto Central de Tucumán",
            content = """Oh Señor en tu presencia, hoy venimos a cantar
Y con nuestro nuevo cántico tu nombre a ensalzar,
Porque sólo tú eres digno de la gloria y el poder,
Y de honra y alabanza por siempre amén.

//Santo, santo, santo, santo es el Señor,
Todopoderoso Dios de salvación//

Solamente tú eres digno de la gloria y el poder,
Y de honra y alabanza por siempre amén.

//Santo, santo, santo, santo es el Señor,
Todopoderoso Dios de salvación//"""
        ),
        Hymn(
            id = 197,
            title = "No dejes de luchar (Medina)",
            link = "https://www.youtube.com/watch?v=XrUHuwH9TTc",
            author = "",
            content = """I
Ya sin fuerzas, cansado, te sientes desmayar,
La adversidad asedia tu vida sin cesar,
Dobla tus rodillas, canta al Señor,
Él cambia la tristeza por gozo sin igual.

Coro
No dejes de luchar, Jesús es fiel
Él recompensará a aquel que es fiel,
No olvides que Jesús por ti murió,
Un poco sufrirás, más sufrió el Señor.

II
Afrentas y martirios, por causa del Señor,
Cuántos siervos soportaron gozosos por amor,
Gózate en la prueba y en la adversidad,
Tu ministerio cumple y permanece fiel."""
        ),
        Hymn(
            id = 198,
            title = "No bajes tus brazos",
            link = "https://www.youtube.com/watch?v=nlUsxf9S-OI",
            author = "Conjunto Amigo Fiel",
            content = """Los tiempos se van cumpliendo, ya Cristo a la puerta está,
Por eso es que tantas pruebas hay que pasar,
El enemigo astuto nos quiere hacer desmayar,
But cristiano ánimo hay que llegar.

No bajes hermano mío hoy tus brazos,
Mas eleva al Señor nueva canción,
//Recuerda que Jesús muy pronto volverá
Y al cielo junto a él nos llevará//

Las pruebas son muy diversas, y todas hay que pasar,
Pues ellas son el camino a la eternidad.
Hermano sigue luchando, que Cristo te va a ayudar, Pues él te lo ha prometido y lo cumplirá.

No olvides aquellos tiempos de la primera hermandad,
La lucha también entonces era tenaz,
Morían en las hogueras y por la espada también,
Porque a Cristo no querían ellos negar."""
        ),
        Hymn(
            id = 199,
            title = "Me llena de poder",
            link = "https://www.youtube.com/watch?v=HyXh2GUZBGA",
            author = "",
            content = """Cristo está junto a mí y me llena de poder
me llena de poder, me llena de poder
Cristo está junto a mí y me llena de poder,
Cristo me llena de poder.

Cristo está junto a mí y me guarda de poder,
me guarda de poder, me guarda de poder.
Cristo está junto a mí y me guarda de poder,
Cristo me guarda de poder."""
        ),
        Hymn(
            id = 200,
            title = "Oraré por ti",
            link = "https://www.youtube.com/watch?v=gpGA2kRMsVo",
            author = "",
            content = """¿Cuál es tu tristeza, hermano querido, cuál es tu dolor,
Andas abatido, de brazos caídos, cuál es la razón?
Si es dura la lucha y pesada es tu carga, yo te quiero ayudar,
//Oraré por ti, hermano querido,
Que el Señor del cielo, hoy, sane tus heridas//

Hermano querido, él te está esperando para trabajar,
Hoy dale tu mano, toma el arado, sin mirar atrás,
Levanta tu cabeza, deja la tristeza, hay que continuar

///Oraré por ti, hermano querido,
Que el Señor del cielo, hoy, sane tus heridas///"""
        ),
        Hymn(
            id = 201,
            title = "Sigue trabajando",
            link = "https://www.youtube.com/watch?v=kK5d-dpAnGw",
            author = "",
            content = """Tú sigue trabajando, no escuches esas cosas
Que sólo te hacen daño, y puedes decaer,
Siempre habrá quién hable mal de ti, si trabajas para Cristo,
Ya verás cómo se quema la hierba con el fuego,
Mientras que tú recibes en el cielo el galardón,
Y serás consolado por haberte humillado,
Y vivas con los santos para la eternidad.

Tú sigue amando al Señor, sigue trabajando
No mires a tu alrededor, sigue trabajando,
Ya verás cómo se quema la hierba con el fuego,
Mientras que tú recibes en el cielo el galardón,
Y serás consolado por haberte humillado,
Y vivas con los santos para la eternidad."""
        ),
        Hymn(
            id = 202,
            title = "Rayo fugaz",
            link = "https://www.youtube.com/watch?v=MkD80d21nZQ",
            author = "",
            content = """Como un rayo fugaz, que se pierde entre la oscuridad,
Como un gran manantial calma completamente mi sed,
Así es el amor de mi hermoso Señor.

//Él es brisa del mar, el rocío del mañana es la miel del panal,
Medicina y es cura hasta mi enfermedad,
Así es la grandeza del amor de Dios.

Por favor nunca digas que Dios te ha desamparado,
Si supieras que sólo su amor es el que te ha sustentado,
Ámale, como a nada en el mundo, búscale,
En el gozo y la pena, escúchale,
Sólo él puede ayudarte cuando tu alma gime,
Y exáltale, cuando nadie en Dios crea, tu adórale,
A mi Dios de la gloria, cántale,
Que después de la prueba el sol brillará,
Y levántate, en el nombre de Cristo, anímate,
Pronto viene a buscarte, esfuérzate,
Seca ahora tus lágrimas y ven a alabar
Al Dios de Israel, al dueño de tu vida, Jehová, Yiréh
Al que todo lo puede, ven a Él
Que todos tus vacíos los puede llenar
El amor de mi Dios//"""
        ),
        Hymn(
            id = 203,
            title = "Hemos creído",
            link = "https://www.youtube.com/watch?v=DBDRWkrDA5E",
            author = "",
            content = """Hemos creído en un poder que no se toca,
Hemos creído en un poder que no se ve,

//Hemos creído en un poder que no se toca
Ni se ve, pero se siente dentro de el corazón//

//Señor bendito, queremos tu poder//

//Por tu poder los sordos oyen,
Por tu poder los ciegos pueden ver,
Por tu poder los cojos también andan,
Señor querido queremos tu poder//"""
        ),
        Hymn(
            id = 204,
            title = "Qué lindo es su mirar",
            link = "https://www.youtube.com/watch?v=SyCTP9UfWX4",
            author = "",
            content = """Solo al pensar que hay un Dios, mi alma se regocija;
porque ha llenado a mi alma de esa paz y esa calma,
que el mundo no me da

//Que lindo, es su mirar;
qué lindo, cuando Él habla a mi alma.
Qué bello es estar con Él,
porque estando con Él, nada me faltará.//

Él me ama, yo le amo también
y junto a Él me siento confiado;
y si te sientes solo, yo te brindo el amor
de Cristo, el Salvador."""
        ),
        Hymn(
            id = 205,
            title = "Las cosas más absurdas",
            link = "https://www.youtube.com/watch?v=7NUEZPsBldA",
            author = "",
            content = """//Las cosas más absurdas de este mundo,
Son las más cuerdas que me ha enseñado Dios//

//Ama a tus enemigos, quiérelos con gran amor
Y si alguno te ofende, ora por él al Señor//

El altísimo Señor dio mandamiento,
Dando muestra de humildad Jesús vivió,
Predicando por todo el mundo buenas nuevas
//porque escrito estaba que en él hay perdón//

//Las cosas más absurdas de este mundo,
Son las más cuerdas que me ha enseñado Dios//

//Ama a tus enemigos, quiérelos con gran amor
Y si alguno te ofende, ora por él al Señor//

Y después dice Jehová estemos a cuenta,
Vengan todos los puros de corazón,
Procediendo todos al arrepentimiento,
//Alcanzaremos eterno galardón//"""
        ),
        Hymn(
            id = 206,
            title = "Pasa por aquí",
            link = "https://www.youtube.com/watch?v=FLLSH74iDkY",
            author = "",
            content = """//Pasa por aquí Señor, pasa por aquí//
//Oh, Señor, pasa por aquí//

//Espíritu Santo, lléname de Ti//
//Oh, Señor, lléname de Ti//

//Quédate Señor aquí, con tu bendición//
//Oh, Señor, quédate aquí//"""
        ),
        Hymn(
            id = 207,
            title = "Despiértame Señor",
            link = "https://www.youtube.com/watch?v=WC0q9sonUO4",
            author = "",
            content = """Despiértame Señor si estoy durmiendo
Que yo como Samuel responderé:
“Si útil puedo serte hasta la muerte
Ordena lo que quieras yo lo haré”

Despiértame del sueño para oírte
Y dame un consejero como Elí
//Que yo como Samuel pueda decirte
Señor, Tu siervo oye heme aquí//

Mándame Señor donde tú quieras,
Que yo como Samuel responderé:
“Si útil puedo serte hasta la muerte
Ordena lo que quieras yo lo haré”"""
        ),
        Hymn(
            id = 208,
            title = "A Dios sea la gloria",
            link = "https://www.youtube.com/watch?v=zMPvFIHuZ3w",
            author = "",
            content = """///A Dios sea la gloria/// por su gran salvación
Su sangre me ha limpiado, su poder me ha levantado
A Dios sea la gloria, por su gran salvación."""
        ),
        Hymn(
            id = 209,
            title = "Nada me apartará",
            link = "https://www.youtube.com/watch?v=x_s1fiio7ls",
            author = "",
            content = """Quién podrá apartarnos de su amor,
Angustia, desnudez, persecución,
Hambre, peligro o espada,
Podrán separarnos del Señor.

No, yo sé, ni la muerte, ni la vida,
Nada del presente o porvenir,
Ni de lo alto, ni de lo profundo,
Del amor de Dios me apartará.

Sin temor yo seguiré su senda,
Su fidelidad veo hasta aquí,
El que alimenta a las aves,
Es el mismo por la eternidad."""
        ),
        Hymn(
            id = 210,
            title = "Mi nombre en gloria",
            link = "https://www.youtube.com/watch?v=eHrucyQJYRQ",
            author = "",
            content = """Mucho he pecado, más con fe Imploré perdón al Señor,
Derramó su gracia y hallé que él es fiel perdonador.

Mi nombre está anotado en gloria, salvo soy, sí, salvo soy,
Y ante el trono cantan la historia: “un pecador volvió”
Mi nombre está anotado en gloria, salvo soy, sí, salvo soy,
Perdonado voy camino al cielo, ¡Gloria al Señor!

Por amor y gracia de Jesús se halla escrito mi nombre allá
Y por el misterio de la cruz tengo vida eternal."""
        ),
        Hymn(
            id = 211,
            title = "En este día feliz",
            link = "https://www.youtube.com/watch?v=xExKaR3vS4I",
            author = "",
            content = """En este día feliz, en este Santo lugar, quiero tener un encuentro con Dios,
Su amor es real y su paz gozaré, quiero tener un encuentro con Dios.

Quiero tener un encuentro con Dios, en este Santo lugar de oración,
Su amor es real y su paz gozaré, quiero tener un encuentro con Dios."""
        ),
        Hymn(
            id = 212,
            title = "Cristo, Gracias te doy",
            link = "https://www.youtube.com/watch?v=IOFwl4Bo3k4",
            author = "",
            content = """Cristo, gracias te doy, con espinas te coronan, Oh Señor,
Cristo, gloria a ti, en humildad tú la vida das por mí,
Cristo, Cordero de Dios, por mi culpa te llevaron a la cruz,
Por los crueles verdugos y ante el pueblo burlador,
Mueres tú hijo de Dios.

Cristo resucitó, el camino, la vida y la verdad,
Cristo todo venció, del abismo entra en su heredad.
Honra, gloria y poder, cielo y tierra a ti se sujetarán

//Cuando seas coronado por tu padre celestial
Rey de la eternidad//"""
        ),
        Hymn(
            id = 213,
            title = "Tú, esfuérzate",
            link = "https://www.youtube.com/watch?v=sJ0yBlN83Fg",
            author = "",
            content = """Tú, esfuérzate en la gracia del Señor Jesús,
Enarbolando la bandera de la cruz,
Sé fiel soldado y agrada a tu Señor,
Sin enredarte en este mundo tentador.

Pon tu mirada, sólo en el cielo, nada te impida allí llegar,
Una corona te está esperando, sigue luchando sin desmayar.

Tú, esfuérzate en la gracia del Señor Jesús,
Guardando siempre la doctrina de su luz,
Como un atleta perfecciona tu saber,
Y el mundo en ti verá a Dios y su poder

Tú, esfuérzate en la gracia del Señor Jesús,
Sembrando siempre la semilla de su cruz,
No desanimes la verdad en proclamar,
Que oscuridad podrá la luz encadenar."""
        ),
        Hymn(
            id = 214,
            title = "Te alabo Señor",
            link = "https://www.youtube.com/watch?v=PUWjwFfY6a4",
            author = "",
            content = """Con mis labios y mi vida, //te alabo Señor//
Con mis labios y mi vida, te alabo bendito Señor
////te alabo Señor////

Porque tú has sido ///precioso para mí///
Porque tú has sido precioso para mí, te alabo bendito Señor."""
        ),
        Hymn(
            id = 215,
            title = "En momentos así",
            link = "https://www.youtube.com/watch?v=uB16vLmWs9I",
            author = "",
            content = """En momentos así, levanto mi voz
Levanto mi alma a Cristo,
En momentos así, levanto mi ser,
Levanto mis manos a él.

///Cuánto te amo oh Dios/// te amo"""
        ),
        Hymn(
            id = 216,
            title = "Al amparo de la roca",
            link = "https://www.youtube.com/watch?v=-BAMRYIS1bA",
            author = "",
            content = """Aunque ruja la tormenta, de mi vida en derredor
Al amparo de la roca salvo estoy
Si la tempestad aumenta no tendré ningún temor
Al amparo de la roca salvo estoy

//Al amparo de la roca salvo estoy//
Si conmigo está el Señor, no tendré ningún temor
Al amparo de la roca salvo estoy

Aunque ruja el mar furioso cuando venga tentación
Al amparo de la roca salvo estoy
Su cuidado cariñoso me dará consolación
Al amparo de la roca salvo estoy

Hallaré feliz reposo si velando en oración
Al amparo de la roca salvo estoy"""
        ),
        Hymn(
            id = 217,
            title = "Una mirada de fe",
            link = "https://www.youtube.com/watch?v=RkCTIrxKtaM",
            author = "",
            content = """//Una mirada de fe, una mirada al Señor,
Es la que puede salvar al pecador//

//Y si tú vienes a Cristo Jesús, Él te perdonará,
Porque una mirada de fe es la que te puede salvar//"""
        ),
        Hymn(
            id = 218,
            title = "Sólo Dios hace al hombre feliz",
            link = "https://www.youtube.com/watch?v=FSJkFsSsv9A",
            author = "",
            content = """//Sólo Dios hace al hombre feliz//
La vida es nada todo se acaba
Sólo Dios hace al hombre feliz"""
        ),
        Hymn(
            id = 219,
            title = "Paz en la tormenta",
            link = "https://www.youtube.com/watch?v=5Jku7mDleEw",
            author = "",
            content = """Cuánto lloras por las veces que intentaste
Y tratas de olvidar las lágrimas que lloraste,
Sólo tienes pena y tristeza, el futuro incierto esperas,
Puedes tener paz en la tormenta.

Puedes tener paz en la tormenta
Fe y esperanza cuando no puedas seguir
Aun con tu mundo hecho pedazos
El Señor guiará tus pasos
En paz en medio de la tormenta

Muchas veces yo me siento igual que tú
Mi corazón anhela algo real
El Señor viene a mí y me ayuda a seguir
En paz en medio de la tormenta"""
        ),
        Hymn(
            id = 220,
            title = "Si me preguntan",
            link = "https://www.youtube.com/watch?v=5fypkAekZUg",
            author = "",
            content = """//Si me preguntan por qué estoy contento,
Si me preguntan por qué canto así,
Es porque Cristo me ha lavado con su sangre
Y en una nube blanca volverá por mí
Es porque Cristo me ha lavado con su sangre
Y en una nube blanca volverá por mí//"""
        ),
        Hymn(
            id = 221,
            title = "Si fui motivo de dolor",
            link = "https://www.youtube.com/watch?v=jjexMrqHVsY",
            author = "",
            content = """Si fui motivo de dolor, oh Cristo,
Si por mi causa el débil tropezó,
Si en tus pisadas caminar no quise,
Perdón te ruego mi Señor y Dios.

Escucha oh Dios mi confesión humilde
Y líbrame de tentación sutil,
Preserva siempre mi alma en tu rebaño,
Perdón te ruego mi Señor y Dios

Si vana y fútil mi palabra ha sido
Si al que sufría en su dolor dejé
No me condenes, tú, por mi pecado
Perdón te ruego mi Señor y Dios"""
        ),
        Hymn(
            id = 222,
            title = "Ardiendo el fuego",
            link = "https://www.youtube.com/watch?v=zASO7uyGQzM",
            author = "",
            content = """//Ardiendo el fuego en mi alma está//
Gloriosa llama me limpiará
Oh Aleluya mi alma ardiendo está.

Oh Señor quiero que ardas en mi ser,
Como una zarza quiero arder con tu poder,
En nuevas lenguas quiero hablar como señal,
Que estoy ardiendo con el fuego celestial,
Quiero alabarte y adorarte solo a ti,
Como se adora en espíritu y verdad,
//Oh Señor quiero que ardas en mi ser,
Como una zarza quiero arder con tu poder//
Como una zarza quiero arder con tu poder."""
        ),
        Hymn(
            id = 223,
            title = "Es exaltado",
            link = "https://www.youtube.com/watch?v=yYMiwQnADJk",
            author = "",
            content = """Es exaltado, por siempre ';exaltado es el rey, le alabaré
Es exaltado, por siempre ';exaltado y yo le alabaré

//Él es Señor, por siempre Él reinará
La creación alaba su majestad
Es exaltado, en lo alto exaltado es el rey//

Es exaltado, en lo alto ';exaltado es el rey"""
        ),
        Hymn(
            id = 224,
            title = "Yo sé que estás aquí",
            link = "https://www.youtube.com/watch?v=_Cc1VYdcfL8",
            author = "",
            content = """//Yo sé que estás aquí, siento tu caminar,
Te mueves en el pueblo trayendo sanidad//

//Con mi fe, te alcanzaré
Con mi fe, te tocaré
Mi milagro recibiré
Y sé que transformado yo seré//"""
        ),
        Hymn(
            id = 225,
            title = "Jesús está aquí, pide lo que quieras",
            link = "https://www.youtube.com/watch?v=djjR1Ti0Q78",
            author = "",
            content = """//Jesús está aquí, pide lo que quieras//
Él tiene poder, Él te lo dará,
Jesús está aquí, Aleluya, pide lo que quieras.

También nosotros por la fe nos acercamos Cristo a ti,
Y aunque tu rostro no se ve, pero sabemos que estás aquí.

//Jesús está aquí, pide lo que quieras//
Él tiene poder, Él te lo dará,
Jesús está aquí, hermano, pide lo que quieras."""
        ),
        Hymn(
            id = 226,
            title = "Amémonos de corazón",
            link = "https://www.youtube.com/watch?v=TsLAa2Zh3l0",
            author = "",
            content = """//Amémonos de corazón y no con amor fingido//
//Para cuando Cristo venga, para cuando Cristo venga,
Nos encuentre preparados//

//Cómo puedes tú orar, enojado con tu hermano//
//Dios no oye la oración, Dios no oye la oración,
Si no estás reconciliado//"""
        ),
        Hymn(
            id = 227,
            title = "Hemos orado",
            link = "https://www.youtube.com/watch?v=H57Np5P8x-A",
            author = "",
            content = """//Hemos orado y ha bajado el poder de Dios//
//Oh gloria a Dios//
Hemos orado y ha bajado el poder de Dios"""
        ),
        Hymn(
            id = 228,
            title = "Es que Dios es así",
            link = "https://www.youtube.com/watch?v=ByDThKUfeOU",
            author = "",
            content = """Cuántas veces corriste cuando Dios te estaba llamando,
Cuántas veces dijiste, ocupado estoy,
Fueron los días, los meses y también los años,
Pero Dios siempre esperando a que tú regresaras.

Es que Dios es así, siempre ama hasta el final,
Es que Dios es así, es su forma de ser,
//Siempre ama hasta el final//
Es que Dios es así, no lo puedo entender.

Es que Dios es así, siempre ama hasta el final,
Es que Dios es así, es su forma de ser,
//Siempre ama hasta el final//
Es que Dios es así, yo no lo puedo entender

Nunca reprochó el que tú le negaras,
Fueron muchas las veces que tú le fallaste,
Pero todos los días a ti regresaba,
Esperando cuidarte, deseando cuidarte."""
        ),
        Hymn(
            id = 229,
            title = "Solamente en Cristo",
            link = "https://www.youtube.com/watch?v=o58AgGeB7cY",
            author = "",
            content = """//Solamente en Cristo, solamente en Él
La salvación se encuentra en Él
No hay otro nombre dado a los hombres
Solamente en Cristo, solamente en Él//"""
        ),
        Hymn(
            id = 230,
            title = "Si tú hablas con Dios",
            link = "https://www.youtube.com/watch?v=jYTkzIUUj0o",
            author = "",
            content = """//Si tú hablas con Dios, las cosas cambiarán orando,
Cualquier necesidad Dios la responderá orando,
Descansar en el Señor, las pruebas cambiarán orando,
Bendita oración yo puedo hablar con Dios, orando//"""
        ),
        Hymn(
            id = 231,
            title = "Yo te busco",
            link = "https://www.youtube.com/watch?v=tioyFWQ5W9Y",
            author = "",
            content = """//Yo te busco// con fuego en mi corazón,
//Yo te busco// recibe mi adoración.

//Te anhelo, te necesito, te amo, más que a mi ser//"""
        ),
        Hymn(
            id = 232,
            title = "Su gloria cubrió los cielos",
            link = "https://www.youtube.com/watch?v=EgW1SoBY-yU",
            author = "",
            content = """//Su gloria cubrió los cielos,
y la tierra se llenó de su alabanza//

Y el resplandor fue como una luz,
rayos brillantes salían de sus manos

Y el resplandor fue como una luz
y allí estaba escondido su poder."""
        ),
        Hymn(
            id = 233,
            title = "Quién dijo que no habría victoria",
            link = "https://www.youtube.com/watch?v=21cVOdFW4zk",
            author = "",
            content = """//Quién dijo que no, que no habría victoria
Habiendo conocido al Cristo de la gloria
Yo digo que sí, que sí venceremos,
Aunque satán no quiera, victoria tendremos//"""
        ),
        Hymn(
            id = 234,
            title = "Yo quiero más de ti",
            link = "https://www.youtube.com/watch?v=FbJzEm3jbTg",
            author = "",
            content = """//Yo quiero más de ti,
Y habitar en tu presencia,
Menguar para que crezcas tú,
Y cada día ser más como tú//

Quebranta mi corazón,
Quebranta mi vida,
Te entrego mi voluntad a ti.

Todo lo que soy Señor,
Todo lo que tengo es tuyo,
//Yo quiero menguar para que crezcas tú//"""
        ),
        Hymn(
            id = 235,
            title = "Los que esperan en Jesús",
            link = "https://www.youtube.com/watch?v=wAB_wcf3INU",
            author = "",
            content = """////Los que esperan en Jesús////

//Como las águilas, como las águilas sus alas levantarán//

Caminarán y no se cansarán,
Correrán, no se fatigarán.

//Nuevas fuerzas tendrán,
Nuevas fuerzas tendrán
Los que esperan en Jesús//"""
        ),
        Hymn(
            id = 236,
            title = "Muévete en mí",
            link = "https://www.youtube.com/watch?v=hTQVD6JJ6jA",
            author = "",
            content = """//El Espíritu de Dios está en este lugar,
El Espíritu de Dios se mueve en este lugar,
Está aquí para consolar, está aquí para liberar,
Está aquí para guiar, el Espíritu de Dios está aquí//

//Muévete en mí, Muévete en mí,
Toca mi mente y mi corazón,
Llena mi vida de tu amor,
Muévete en mí, Santo Espíritu, muévete en mí//"""
        ),
        Hymn(
            id = 237,
            title = "Me voy con Él",
            link = "https://www.youtube.com/watch?v=MsiUgKU7idY",
            author = "",
            content = """Ya viene Cristo, señales hay,
Almas salvadas viene a buscar,
Los que durmieren se quedarán,
Los que velaren se irán con Él.

//Me voy con Él, me voy con Él,
Me voy con Él, me voy con Él,
Yo no me quedo, me voy con Él//"""
        ),
        Hymn(
            id = 238,
            title = "Enamorado de Jesús",
            link = "https://www.youtube.com/watch?v=nayne0A2kBk",
            author = "",
            content = """//Enamorado de Jesús, enamorado,
Enamorado de Jesús//

//Enamorado de él, enamorado de él
En mi corazón tengo escrito
Jesucristo de Nazaret//"""
        ),
        Hymn(
            id = 239,
            title = "Cielo y tierra pasarán",
            link = "https://www.youtube.com/watch?v=MZowLl8Typc",
            author = "",
            content = """//Santo, santo, santo, santo es el Señor//
Santo, santo, santo, dicen los querubines,
//Because mi Dios es santo y la tierra llena de su gloria está//

//Cielo y tierra pasarán, más su palabra no pasará//
//No, no, no pasará//

Bendito el que viene en nombre del Señor,
Hosanna en el cielo, hosanna al Señor
Santo, santo, santo, dicen los querubines,
//Because mi Dios es santo y la tierra llena de su gloria está//

//Cielo y tierra pasarán, más su palabra no pasará//
//No, no, no pasará//"""
        ),
        Hymn(
            id = 240,
            title = "Como el viento sobre el mar",
            link = "https://www.youtube.com/watch?v=prSD9-rDQwU",
            author = "",
            content = """Como el viento sobre el mar, como el rayo tan veloz,
Como el fuego que consume y que nada lo destruye,
Es así el poder de Dios.

Como suave y fresca brisa, como bella es una flor,
Como el cielo azul inmenso
Es así el amor intenso, de mi amado Salvador.

Si necesito consuelo, pues todo no ha sido bueno,
Si me arden hoy las heridas, por tanta lucha en la vida,
A mi Dios me acercaré, y con su amor me aliviará.
Y para mí será un amigo, y al estar siempre conmigo,
Mi alma nunca temerá.

Si necesito la calma, por las angustias de mi alma,
Si no encontraste ternura, en donde hay tanta amargura,
A mi Dios me allegaré, y con su amor me tomará,
Y dulcemente de sus labios, brotará el consejo sabio,
Que en mí permanecerá."""
        ),
        Hymn(
            id = 241,
            title = "Conozco que todo lo puedes",
            link = "https://www.youtube.com/watch?v=2obTgDlUE1k",
            author = "",
            content = """//Conozco que todo lo puedes
Que mi pensamiento no lo puedo esconder
Hablaba lo que no entendía
Y de oídas te había oído//

Más ahora mis ojos te ven,
Yo te preguntaré y tú me enseñarás,
Más ahora mis ojos te ven,
Me rindo a tus pies y me arrepiento Señor."""
        ),
        Hymn(
            id = 242,
            title = "Más que vencedor",
            link = "https://www.youtube.com/watch?v=emFj_dx8Q7U",
            author = "",
            content = """Luchas hay en tu vivir, que causan gran dolor,
Has pensado en desistir, no quieres más luchar,
Para qué desesperar, basta apenas confiar,
Con sus brazos de amor,
Cristo hará de ti más que vencedor.

Si la vida trae a ti tristeza y dolor,
Confía en el Señor Jesús, te ayudará.
Su paz él te dará y la calma reinará,
Con su inmenso amor,
Cristo hará de ti, más que vencedor.

Yo no tengo más temor, mirando a mi Señor,
Y si viene oscuridad, yo puedo ver su luz,
Un gran precio él pagó, al sufrir un gran dolor,
Con su sangre allá en la cruz,
Él me transformó, más que vencedor."""
        ),
        Hymn(
            id = 243,
            title = "Digno eres de Gloria y Alabanza",
            link = "https://www.youtube.com/watch?v=6lxnd7KJonI",
            author = "",
            content = """//Digno eres de gloria y alabanza
Levantamos nuestras manos
Adorándote Señor//

//Grande eres tú, grandes tus milagros son
No hay otro como tú, No hay otro como tú//"""
        ),
        Hymn(
            id = 244,
            title = "Dios manda lluvia",
            link = "https://www.youtube.com/watch?v=UOYwvMeFdMg",
            author = "",
            content = """//Dios manda lluvia, derrama de tu espíritu,
Enciende hoy tu fuego, sana mis heridas
Restáurame Señor//

Manda la lluvia, el rocío de tu amor,
Llenando las vidas de tu pueblo hoy Señor
Manda la lluvia, el rocío de tu amor,
Visita hoy mi vida, cámbiame Señor"""
        ),
        Hymn(
            id = 245,
            title = "Invoqué tu nombre",
            link = "https://www.youtube.com/watch?v=eFchuIJL-hE",
            author = "",
            content = """Invoqué tu nombre, pues mi alma moría,
En mi gran angustia me acordé de ti,
Con brazos abiertos imploré tu ayuda
Y dulce socorro en mi desamparo recibí de ti.

Bendigo tu nombre, Señor de la vida,
Bendigo tu nombre, por tanta bondad,
Levanto los brazos ante tu presencia,
para agradecerte,
Y es tu amor inmenso, que me hace cantar.

Hoy quedan recuerdos de amargos momentos,
Cargados de pruebas, que tanto sufrí,
Ahora a tu lado me siento seguro,
Pues mi alma proteges
y en tu amor eterno habré de vivir."""
        ),
        Hymn(
            id = 246,
            title = "Seguiré cantando",
            link = "https://www.youtube.com/watch?v=ZVm4dLUD8Rk",
            author = "",
            content = """Mi corazón puede cantar si pienso
Que cada prueba es un peldaño más
En la empinada cuesta hacia el cielo,
Y que este mundo es un lugar fugaz.

Y hasta entonces seguiré cantando
Y con valor caminaré con Dios,
Hasta aquel día
En que contemple el cielo
Y al dulce hogar me llame Dios.

Las cosas que aquí causaron llanto
Vistas del cielo pierden magnitud
Lo terrenal su importancia pierde
At recordar cuán poco durará"""
        ),
        Hymn(
            id = 247,
            title = "No desanimes",
            link = "https://www.youtube.com/watch?v=ngLbVyVqEOA",
            author = "",
            content = """Por la vida va el creyente caminando,
Cargando su cruz con valentía
Trabajando para el reino de los cielos,
Sirviendo al Señor con alegría
Y aunque duras son las pruebas del camino
Y a veces es difícil continuar
En angustia, con lágrimas gimiendo
A Jesús él va siguiendo sin jamás desanimar…
Sin detenerse va mirando al frente,
caminando el fiel creyente hacia el reino celestial
Con la mirada puesta en el Señor,
va luchando con valor hasta llegar al final…

No desanimes…. Muy cercano está el momento,
En que llegarás al cielo, donde al fin tendrás consuelo
Y ya no habrá sufrimiento
En aquel día Dios enjugará tu llanto
Del dolor no habrá memoria
Y con Cristo allá en la gloria; Entonarás un nuevo canto…

Herma no temáis al enemigo
Jesucristo está contigo cual poderoso gigante
Muy pronto a la meta llegarás
galardón recibirás y allí cantarás triunfante"""
        ),
        Hymn(
            id = 248,
            title = "¿Cómo debo orar?",
            link = "https://www.youtube.com/watch?v=kNY0Z2QB2Y4",
            author = "",
            content = """¿Cómo debo orar para alcanzar lo imposible?
Desprender virtud de Ti, hoy mi Fe fue suficiente
Mi clamor llegó hasta tu altar.
¿Cómo debo orar si sé que existe el milagro?
Porque creo en Tu poder, el poder que no ha menguado,
Ese poder que hoy se siente aquí.
//Si pudiera yo alcanzar esa Fe que tú me pides
Esa Fe que es la perfecta, la que pide y recibe
Hoy mi carga quedará, en Tus manos seré libre//

Así debes orar: en todo tiempo
Haya noche, haya sol siempre tienes que velar,
Persevera en todo tiempo de rodillas en su presencia
No desmayes que vendrá
La respuesta que esperabas, o la puerta que se abrió,
Esa fuerza que faltaba, la tormenta que pasó
La Victoria que esperabas, de Rodillas se Ganó.

Necesito orar para alcanzar el milagro,
Dialogar con el que tiene un poder que nadie puede
Ni siquiera un poco igualar.
Necesito orar para poder estar firme
Resistiendo en todo tiempo a este príncipe del mundo
Que procura siempre hacerme mal.
//Si quisieras hoy hablar y contar lo que no entiendes,
El Señor escuchará porque él está presente
Tu alma llena quedará y verás lo que se siente//"""
        ),
        Hymn(
            id = 249,
            title = "El gran amor del Señor",
            link = "https://www.youtube.com/watch?v=ZvUwK4cur24",
            author = "",
            content = """//El gran amor del Señor nunca cesa,
Su misericordia jamás tiene fin,
Nuevas cada mañana, cada mañana
Tu gran fidelidad Señor,
Tu gran fidelidad//

Nuevas cada mañana, cada mañana
Tu gran fidelidad Señor,
Tu gran fidelidad"""
        ),
        Hymn(
            id = 250,
            title = "Seguiré adelante",
            link = "https://www.youtube.com/watch?v=UUYMiyv0mdw",
            author = "",
            content = """Seguiré adelante puesta mi mirada
En Aquel que pudo rescatar mi alma,
Seguiré adelante, aunque el diablo trate
Con mil artimañas desviarme de ti,
Seguiré adelante, aunque la tormenta
Sea tan violenta que hunda mi barca,
Y aunque naufragando en el mar me encuentre,
Sé que Jesucristo en su barco hermoso,
Salvará mi alma.

Seguiré adelante, sí, firme, adelante,
Luchando y ganando tremendas batallas,
Y al cumplir sus obras, si en algún momento,
Siento que declino, alzaré mi mano y rogaré la ayuda.
Y sé que tu mano siempre estará lista para defenderme,
En las pruebas y luchas, y sé que tu amor,
Su bendito amor, me guiará hasta el fin.

No le temo a nada, si tú vas conmigo,
Guiando mis pasos, por el buen camino,
Vaya donde vaya, yo estaré contigo,
Porque tu presencia siempre irá conmigo,
No le temo a nada, ni a la misma muerte,
Pues sí sé que muero, me uniré contigo,
Cruzando desiertos y valles de sombras,
Mi alma segura porque vas conmigo,
Nada temerá.

Seguiré adelante, sí, firme, adelante,
Luchando y ganando tremendas batallas,
Y al cumplir sus obras, si en algún momento,
Siento que declino, alzaré mi mano y rogaré la ayuda.
Y sé que tu mano siempre estará lista para defenderme,
En las pruebas y luchas sé que tu amor,
Su bendito amor, me guiará hasta el fin.
Y sé que tu mano siempre estará lista para defenderme,
En las pruebas y luchas sé que tu amor,
Su bendito amor, me guiará hasta el fin."""
        ),
        Hymn(
            id = 251,
            title = "Es solo un poco más",
            link = "https://www.youtube.com/watch?v=MiQlMkrBPwU",
            author = "",
            content = """Una vez más, oscureció
Y el sol se fue del corazón,
Yo ya no puedo soportar,
Sentir la falta de mi hogar.

Oré a Dios en mi aflicción,
A Él abrí mi corazón,
Fue cuando alguien me tocó,
Era Jesús que me habló.

Coro
Es sólo un poco más, sólo un poco más
Porque ya te iré a buscar.
Hay amor real, hay un río de paz,
Hay un cielo que será mi hogar.

Una vez más, amaneció
El sol de fe en mí nació
Y cuando pienso en desistir
Jesús me llama y dice así."""
        ),
        Hymn(
            id = 252,
            title = "Quién te amará",
            link = "https://www.youtube.com/watch?v=fKDtsS0GTEg",
            author = "",
            content = """Quién te amará como lo hizo Jesús
Quién morirá como él por ti en la cruz
Quién te dará en tus horas de tristeza y soledad,
amor y paz solo Jesús te puede dar"""
        ),
        Hymn(
            id = 253,
            title = "Esperar en el Señor",
            link = "https://www.youtube.com/watch?v=Ue_LOQeETAg",
            author = "",
            content = """//Esperar, esperar, esperar en el Señor//
Y aprender bien la lección que a su tiempo él me dirá
¿Qué hacer?, ¿Dónde ir?, ¿Qué decir?

//Esperar, esperar, esperar en el Señor//
Y aprender bien la lección que a su tiempo él te dirá
¿Qué hacer?, ¿Dónde ir?, ¿Qué decir?

//Esperar, esperar, esperar en el Señor//
Y aprender bien la lección que a su tiempo él nos dirá
¿Qué hacer?, ¿Dónde ir?, ¿Qué decir?"""
        ),
        Hymn(
            id = 254,
            title = "¿Quién se opone si Dios te ha elegido?",
            link = "https://www.youtube.com/watch?v=mqb-AOowoXo",
            author = "",
            content = """Nehemías vivía en palacio, como copero del rey.
Cada día oraba y gemía por su nación,
La noticia llegó no era buena, porque su pueblo,
Sufre afrenta, los muros caídos y sufre dolor.
Pide al Padre llegar a su tierra, a sus hermanos,
Levantar lo que estaba caído y edificar,
Animó, trabajó y construyó con las fuerzas del cielo,
Quien se oponía, el nada temía; hay que continuar.

Coro
¿Quién se opone si Dios te ha elegido?, no te detengas;
Aunque veas escombro y ruinas, vas a triunfar,
Si enemigos procuran frenarte, sigue adelante,
Porque el Padre ha dicho que en breve lo aplastará,
Permanece firme en la obra de Dios, un ladrillo más hay que pegar,
Es la obra que Dios te ha confiado, tienes que terminar.

Eres siervo porque Él te ha elegido como obrero del Rey.
Levantar, construir y animar es tu misión.
Cada día eleva plegarias por el rebaño,
Por los que lloran y sufren afrentas y sufren dolor.
Esta obra comienza en la tierra y termina en el cielo
Cuando llegues un coro de ángeles vas a escuchar.
Y Jesús te dará una corona llena de perlas,
Sigue avanzando, un trecho nos queda vas a llegar."""
        ),
        Hymn(
            id = 255,
            title = "Necesito de Ti",
            link = "https://www.youtube.com/watch?v=UIiP6IMzc10",
            author = "",
            content = """Necesito de ti, como la flor del agua
Necesito de ti, como el sol la mañana
Necesito tu dulce paz y tu voz escuchar
Necesito saber que a mi lado tú estás

Coro
Necesito sentir tu presencia en mi ser
Tu palabra de amigo y tu brazo también
Que ofrece al alma un descanso sin par
Oh señor! No me faltes jamás

Aprendí a dejar en Ti mi esperanza
El consuelo esperar en tus dulces palabras
Pues tú me diste amor me diste paz y perdón
Es por eso Señor que a mi lado tú estás"""
        ),
        Hymn(
            id = 256,
            title = "Sólo creé",
            link = "https://www.youtube.com/watch?v=CxP8eJZE1EQ",
            author = "",
            content = """Creer, es más que una simple palabra,
Creer, es más que una declaración,
Creer, es declarar lo que tus ojos aún no han visto,
Teniendo la confianza de que un día Dios lo hará.

Creer, es doblegarse aún en medio de la prueba,
Creer, es no dudar en su palabra de poder,
Creer, es doblegarse a los pies de Jesucristo,
Y dejar en sus manos poderosas tu necesidad.

Coro
Sólo creé, con todo tu ser,
Cierra tus ojos y verás,
Lo que anhela tu corazón,
Dios te lo dará
Cristo lo va a hacer, creé, creé,
Con firme fe, creé, creé,
Espera en Él, tan sólo creé,
//Dios tiene el poder//

Si tú dices a mi siervo que sea sano,
Yo sé que mi siervo sanará,
Así declaró el centurión romano,
Que creyó completamente en el poder del Salvador,
Si tan sólo yo tocare su manto,
Yo sé que sana yo seré,
Así declaro aquella mujer valiente,
En aquella multitud tocó su manto y sana fue.

Jesucristo está obrando, rompiendo cadenas,
Él está sanando tu alma y tu cuerpo,
Dale gloria al Rey, tan sólo creé,
Dale gloria al Rey.
Jesucristo está obrando, rompiendo cadenas,
Él está sanando tu alma y tu cuerpo,
Dale gloria al Rey, El tiene el poder,
Dale gloria al Rey."""
        ),
        Hymn(
            id = 257,
            title = "Es la fe",
            link = "https://www.youtube.com/watch?v=Y2eMxaGRqpA",
            author = "",
            content = """Dicen que la fe,
es más preciosa que el oro,
Y que vivir por fe es agradable al Señor,
Y que es un don de Dios
y que por la fe seremos salvos.
Y que, por la fe,
buen testimonio alcanzaron los antiguos,
Por la fe podemos entender
que el universo fue creado, por El

Es la fe, la convicción de lo que no se ve,
Es la certeza de lo que se espera obtener,
Señor auméntame la fe, Señor auméntanos la fe.
Es la fe, la convicción de lo que no se ve,
Es la certeza de lo que se espera obtener,
Señor auméntame la fe, quiero vivir yo por la fe,
Quiero ser salvo por la fe, Señor auméntanos la fe.

Por la fe Abel,
ofreció a Dios buen sacrificio,
Por la fe transpuesto fue Enoc sin ver la muerte,
Por la fe también Noé construyó el arca y fue salvo,
Por la fe Abraham dejó su tierra
y vivió como extranjero, por la fe
El esperaba la ciudad cuyo arquitecto
y constructor es nuestro Dios.

Es la fe, la convicción de lo que no se ve,
Es la certeza de lo que se espera obtener,
Señor auméntame la fe, Señor auméntanos la fe
Es la fe, la convicción de lo que no se ve,
Es la certeza de lo que se espera obtener,
Señor auméntame la fe, quiero vivir yo por la fe,
Quiero ser salvo por la fe, Señor auméntanos la fe,
Señor auméntanos la fe."""
        ),
        Hymn(
            id = 258,
            title = "Puedo confiar en Dios",
            link = "https://www.youtube.com/watch?v=gcDxulABRIs",
            author = "",
            content = """Hay momentos de esta vida que es difícil comprender,
Hay momentos de dolor y oscuridad,
Pero Cristo es la respuesta que da paz al corazón,
Sólo Él es el camino que me llevará hasta el fin.

Coro
Puedo confiar en Dios, su amor nunca cambiará,
Puedo rendirme a Él, y me sostendrá,
Aunque tenga problemas, conmigo estará,
La tristeza se irá y la alegría tendré con mi Jesús.

Muchas veces desanimo, pierdo fuerzas al luchar,
Tengo miedo de enfrentar la realidad,
Pero Cristo me levanta, pone fe en mi corazón,
Me devuelve la esperanza, me regala una canción.

Si tu vida es solitaria, presta mucha atención,
Lo que falta es Jesús en tu vivir,
Las personas hoy caminan, unas vienen y otras van,
Sólo Cristo permanece junto a ti hasta el final."""
        ),
        Hymn(
            id = 259,
            title = "Mi amigo aquí estoy",
            link = "https://www.youtube.com/watch?v=EpGo22qE3_8",
            author = "",
            content = """Si la tristeza de este mundo embarga tu ser
No tiene sentido tu vida y no sabes qué hacer
Recuerda que hay un Dios bendito que está a tu lado y ofrece ayudar
Y sólo nos dice a nosotros: ¡Debes confiar!

Coro
Mi amigo aquí estoy, nos dice Jesús
No te dejaré, Promesa de Él
Y cuando te sientas muy sólo creyendo que nadie está junto a ti;
Recuerda que mi vida y sangre yo entregué por ti.

Si notas que tus oraciones no contesta Él,
Es porque no has confiado ¡Debes creer!
Y cuando le creas a Dios bajará bendición hasta sobreabundar
Entonces podrás conocer a un Dios de potestad."""
        ),
        Hymn(
            id = 260,
            title = "No existe tristeza",
            link = "https://www.youtube.com/watch?v=k-7WAK_objA",
            author = "",
            content = """Hay algo muy simple, que todo cristiano
No debe olvidarse al peregrinar,
Que en todo momento sus labios se abran
Cantando alabanzas al Dios de verdad
Si estas enfermo o muy abatido,
Si algo perdiste y no entiendes por qué,
Alaba a Cristo, aunque te falten las fuerzas,
Porque la alabanza traerá bendición

Coro
No existe tristeza, para el cristiano
Que estando en pruebas, alaba al Señor
Un gozo muy grande inunda su alma
Porque la alabanza traerá bendición

¿Por qué estás callado?, ni amén hoy haz dicho
¿No sabes que Cristo nos vino a bendecir?
Pero tu silencio, tus labios cerrados
Están impidiendo una gran bendición."""
        ),
        Hymn(
            id = 261,
            title = "Alborada Eterna",
            link = "https://www.youtube.com/watch?v=ULG54QZ4BFw",
            author = "",
            content = """Cuando llegue la alborada, de un nuevo amanecer,
Cuando llegue a la ribera de la Patria Celestial,
En aquel azul eterno do jamás se dice adiós,
//Voy a ver a mis amados// y a Jesús mi Salvador.
En aquel país glorioso, donde reina eterno amor,
Con el coro de millones, yo también voy a cantar,
No más lágrimas ni llantos, ni más penas ni dolor,
//Cantaré con los salvados// la canción de redención.

Este mundo traicionero, donde reina la impiedad,
Donde el odio y la venganza cobran víctimas sin fin.
Sus encantos y placeres pronto van a terminar,
Cuando venga la alborada de aquel día luminoso, cuando Cristo volverá
En aquel país dichoso, donde sombras ya no habrán,
Donde cantan los querubes, su dulcísima canción,
Una melodía celeste, yo también voy a cantar
Con el coro de millones que, de todas las naciones, al Cordero entonarán."""
        ),
        Hymn(
            id = 262,
            title = "Viuda y sin nada",
            link = "https://www.youtube.com/watch?v=Q1PxMZQc2Ms",
            author = "",
            content = """Faltaba todo, sólo tenía un poco de aceite.
En una vasija dentro de la casa,
Faltaba pan, y quien miraba en la cocina
No encontraba leña,
Ni fuego ni brazas, //viuda y sin nada//
El pueblo así hablaba.
Faltaba amigos, y sólo algunos enemigos,
Ciertos cobradores se aproximaban,
Y estos así le hablaban:
"Queremos llevar a sus hijos para hacerlos esclavos"
Y ella lloraba, //viuda y sin nada//
El pueblo así hablaba.

Coro
//Pero, Dios la escucho llorando y se conmovió,
E hizo que ella se acordara del profeta fiel,
Entonces la viuda fue en busca de Eliseo.
Ve, dijo el profeta así:
Dios ya me habló a mí,
Que te va a bendecir,
Que va a cambiar la historia
A partir de ahora. Traigan más vasijas
Porque el aceite va a multiplicar,
Hasta rebasar, ahora, ahora.
Tus hijos no serán esclavos
Y no deberás siquiera un centavo,
Porque tu casa será
Un depósito de bendición de Dios.
Y en la casa que faltaba
Ahora va a sobrar,
La viuda que lloraba
Ahora va a cantar,
Donde no había nada ahora hay de todo,
Aquellos que cobraban ahora pagarán,
El fuego que era frío ahora está quemando,
El granero vacío está rebosando,
Quien te vio sufriendo ahora puede ver
a nuestro Dios obrando, ahora, ahora//"""
        ),
        Hymn(
            id = 263,
            title = "Mi Dios es diferente",
            link = "https://www.youtube.com/watch?v=o63j0gFuLL4",
            author = "",
            content = """Yo tengo un amigo que quiero presentarte
No es un amigo más.
Percibe la tristeza, entiende decepciones,
comprende la ansiedad
Y aunque hace mucho tiempo lo dieron por muerto,
Él vive y es verdad
Aunque la ciencia dude, aunque muchos lo ignoren,
yo sé que es realidad
Mi Dios es diferente, Él siempre está presente,
cualquiera sea el lugar
Y habiendo tanta gente Él siempre se hace un tiempo
y me viene a visitar
Y si me encuentro enfermo o tal vez
desalentado más cerca quiere estar,
Él sabe lo que siento,
Él llega tan profundo donde nadie llegará

Coro
Mi Dios es diferente, El nunca obra igual es diferente
El ama de verdad es diferente
Y con palabras nunca lo podré expresar
Mi Dios es diferente, te ayudará a triunfar es diferente,
el siempre entenderá que es lo que sientes
Abre la puerta, hoy contigo Él quiere estar
Y si vienen momentos que todo es negativo,
que todo sale mal
Mi Dios es positivo y Él siempre me recuerda
que no me va a dejar
Y si me siento ansioso y no encuentro la salida
me invita a descansar
Mi Dios tiene el dominio de todos mis caminos
y Él siempre me guiará
Si tiene un plan en mente nada le hace frente,
nada impedirá
Si Él quiere bendecirte, Si Él quiere abrir las puertas
nadie las cerrará
Si abundan tus preguntas, si muchas son tus dudas,
Él te responderá
Mi Dios es diferente, te dije y ya se siente
Él nos vino a visitar."""
        ),
        Hymn(
            id = 264,
            title = "Confío en Dios",
            link = "https://www.youtube.com/watch?v=F5_PvowZGX8",
            author = "",
            content = """Confío en Dios, muy cerca siempre está
En tierra o mar su protección me da
Tan sólo sé, doquiera esté
Mi Padre amante cuidará de mí

Coro
Confío en Dios, Él es mi amparo fiel
En lucha cruel, mi defensor es Él
Se apaga el sol a su arrebol
Mi Padre amante cuidará de mí

Confío en Dios en horas de aflicción
Me hará triunfar en ruda tentación
Y en mi dolor con tierno amor
Mi Padre amante cuidará de mí

Si por el valle tenebroso voy,
Confiando en mi Pastor a salvo estoy.
Es mi Jesús, mi guía y luz,
Mi Padre amante cuidará de mí."""
        ),
        Hymn(
            id = 265,
            title = "Hay un cuarto varón",
            link = "https://www.youtube.com/watch?v=wLziEabrlJ8",
            author = "",
            content = """Sé que saldré adelante en el nombre de Jesús
Sé que saldré adelante porque Él fue quien me llamo
No todo es color de rosas en el camino del Señor
Estoy pasando esta prueba, pero pronto venceré

Coro
Se que resistiré por la fuerza de Cristo
No será mi virtud sino la de Jesucristo
Hay un cuarto varón que ha vencido el dolor caminando conmigo
Ese cuarto varón es Jesús mi Señor, Salvador y Dios mío

Me ha enseñado a cantarle en medio de mi dolor
He aprendido a confiar, aunque todo este al revés
Y es que somos probados como el oro en el crisol,
Pero fuimos llamados a vencer, si a vencer."""
        ),
        Hymn(
            id = 266,
            title = "Viviendo por la fe",
            link = "https://www.youtube.com/watch?v=uv_yAdXA7Sw",
            author = "Conjunto Amigo Fiel",
            content = """En tribulación, en angustia, en necesidad,
En azotes, en cárcel, apremiados sin piedad,
Como falsos, mas Siempre veraces.
Cual moribundos, mas ¡he aquí Vivimos!
Como entristecidos mas ¡siempre gozosos!
Como sin tener nada mas ¡teniéndolo todo!

Coro
Viviendo por la Fe, Atribulados mas no angustiados,
En apuros, más no desesperados,
Perseguidos mas no desamparados,
Derribados, pero no destruidos.

Estas palabras dijo Pablo el Apóstol
Que junto a otros predicaba la Palabra sin cesar
Sin importar cuán grande fuera la adversidad.
En su cuerpo un aguijón llevaba Mas Dios le dijo:
"Bástate en Mi Gracia", Seguro estaba pues en el confiaba.

Las necesidades, el afán, las ansiedades,
El temor, la incertidumbre toman preso el corazón
De aquellos que no han creído en el Señor.
Mas el cristiano por la Fe es Victorioso,
Aun en la prueba puede estar gozoso,
Tiene paz y consuelo en el dolor."""
        ),
        Hymn(
            id = 267,
            title = "Deja que hablen",
            link = "https://www.youtube.com/watch?v=YZata6WsjA4",
            author = "Conjunto Jerusalén",
            content = """¿Por qué te abates mi siervo así de esa manera?
Si te mostré que estoy contigo, ya no temas
Quita la angustia, la tristeza de tu pecho
Alza tu cabeza y en mi nombre levántate.
//Yo sé que piedras te tiran cada día
Y te calumnian y te tratan de lo peor
Pero fui yo quien te escogí y te llamé,
Esa unción que tienes yo te lo entregué
Y te voy a usar según sea Mi Voluntad.

Deja que hablen, si otros murmuran tú alaba mi nombre
Y esa alabanza será fuego que consume
Perfume suave, tan grato para mí.
Deja que hablen que a su tiempo yo con ellos voy a tratar,
Y aun llorando no te canses de alabar
Pues llegará la vez que sonreirás//"""
        ),
        Hymn(
            id = 268,
            title = "Dios dio, Dios quitó",
            link = "https://www.youtube.com/watch?v=mm3agiaxXbk",
            author = "Conjunto Amigo Fiel",
            content = """Era Job un varón temeroso de su Dios,
Justo y recto, agradable al Creador;
Mas la prueba le llegó como plan de perfección,
Y en dolor el adoró y estas palabras pronunció

Coro
Dios dio, Dios quitó, sea él glorificado
Aun el justo sufrirá, debe ser perfeccionado,
En prueba o en dolor no será desamparado
Si no siempre consolado quien confía en él.

Si en pruebas te hayas hoy, no dejes de serle Fiel
Aunque no entiendas porque confía en Él:
Hay un plan de perfección para cada corazón,
En dolor adora hoy y espera en el Señor"""
        ),
        Hymn(
            id = 269,
            title = "Hoy quiero adorarte a Ti",
            link = "https://www.youtube.com/watch?v=k90gQb0yca0",
            author = "Conjunto Amigo Fiel",
            content = """Hoy quiero adorarte a Ti, humillarme y bendecir
Tu Nombre y entregar te todo mi ser;
Ya el mundo dejo atrás para poder vivir
En sincera integridad.
No quiero aceptar las ofertas que me hace el mal,
Que me invita a renunciar a la vida que tú me das,
No apartaré mis pisadas del sendero Fiel
Que me conducen hacia ti y hacia la eternidad.

Coro
Señor Jesús, Tu eres mi Amigo,
Nadie me puede dañar si tú vas conmigo,
Hasta en el valle de sombra y de muerte,
Tú determinas mi suerte
Siempre en Ti confiaré y en Ti esperaré.

Sé que Tu eres mi Pastor, nada me faltará
Y en delicados pastos me harás descansar,
Mas, cuando rendido este, Tú me confortarás
Y aliento me infundirás
Aunque pecador e indigno de Tu Santo Amor
Lavado yo fui así, por Tu Sangre carmesí;
El acusador ya no puede alejarme de ti
Eres Tú mi Salvador, mi Buen Redentor."""
        ),
        Hymn(
            id = 270,
            title = "Padre Mío",
            link = "https://www.youtube.com/watch?v=za8mHbkOya4",
            author = "Conjunto Jerusalén",
            content = """Padre mío hoy he llegado ante tus pies,
Padre mío vengo cansado y tengo sed,
Vengo buscando hoy de la fuente, quiero beber
De esa agua Santa que le dio vida a una mujer.
Oh Cristo amado, Tu que dijiste: "Vengan a Mi
Los que cansados y trabajados están aquí
Tú que conoces la dura lucha de mi vivir,
Señor quisiera de tu descanso hoy recibir.

Coro
Padre mío dura es la lucha y el caminar
Del peregrino que allá a tu gloria quiere llegar,
Padre mío solo tú puedes darme el valor
Para que siempre en este mundo sea un vencedor.

Cristo Divino hoy yo te ruego poder llegar
A las moradas que tú te fuiste a preparar
Donde la muerte y la inmundicia no entraran
Solo los fieles que se vistieron de santidad.
Señor no importa que yo sea pobre, soy muy feliz,
Más que a la plata y más que al oro te amo a Ti
Señor Tú eres lo más precioso que tengo aquí
Hoy yo te llevo como un tesoro dentro de mi"""
        ),
        Hymn(
            id = 271,
            title = "Bajo del Torrente de la Cruz",
            link = "https://www.youtube.com/watch?v=WSDBgyHZ-oQ",
            author = "Conjunto Central de Tucumán",
            content = """Por la cuesta del calvario cruel una huella de sangre se ve
Grandes gotas vertidas de Aquel que inocente castigado es.
Y tras ellas me fui y caminé conmovido al pensar en Él
Y mis ojos en llanto al ver vierten lágrimas de amor por quien
En la cima clavado ya fue.

Coro
Bajo del torrente de la cruz me humillé,
Bajo del torrente de la cruz me entregué,
Bajo del torrente de la cruz me lavé.
Como Nicodemo y José de Arimatea en la cruz
Me humillé, me entregué, me lavé.

Hoy yo vengo mi buen Salvador
Comprendiendo tu grande dolor
Y en los lienzos de mi corazón
Perfumados con mirra y canción
Hoy yo quiero llevarte Señor."""
        ),
        Hymn(
            id = 272,
            title = "Canta hermano, canta",
            link = "https://www.youtube.com/watch?v=RqROpN7cqds",
            author = "",
            content = """Si te sientes muy cansado, triste, enfermo o confundido
El mal te tiene atado y no encuentras el camino.
Pero Dios tiene un remedio para todo ese mal:
Es la Sangre de mi Cristo, el poder de la alabanza
Que al infierno hace temblar.

Coro
Canta hermano, canta
Una canción que llena el alma,
Cántale a Dios en tu tristeza
Que hoy el cielo escuchará.
Canta hermano, canta,
Que en el dolor te purificas
Tras la tormenta el día aclara,
Y brillarás tu más que el sol.

Si es tu vida una derrota, el problema tu amigo
No consigues la victoria, es muy grande tu enemigo.
Pero Dios hará un milagro: Él te quita la aflicción;
Solo quiere que le alabes,
Y Su Espíritu Bendito llenará tu corazón"""
        ),
        Hymn(
            id = 273,
            title = "Te quiero agradar",
            link = "https://www.youtube.com/watch?v=XW1zjgiXr7Y",
            author = "Conjunto Central de Tucumán",
            content = """//Hay cosas en mi vida
Que quiero sacar//
//Quizás sean pequeñas
Pero me hacen mal,
Me quitan el Gozo y la Santidad//

Con mis propias fuerzas
No puedo sacar
El mal que me lleva
Y me hace pecar.
//Mas vengo esta noche
Señor a implorar.
"Liberta mi vida,
Te quiero Agradar"//"""
        ),
        Hymn(
            id = 274,
            title = "Como el ciervo ansioso brama",
            link = "https://www.youtube.com/watch?v=L6qYZrAVwVI",
            author = "",
            content = """Como el ciervo ansioso brama buscando que beber,
Buscando el agua fresca donde pueda saciar su sed
Así mi alma te busca y quiere beber.
Vengo triste y agobiado, confundido y trabajado
Buscando tu presencia que se pose sobre mí,
Y así renovada mi vida hoy pueda salir.

Coro
Tú prometes dar descanso al alma cansada que hoy viene a ti;
Tu presencia derrama en tu pueblo y que llegue hasta mí.
Como el día de pentecostés a esa gran multitud tu presencia llegó,
Hoy mi alma espera tu gracia Divino Señor, Señor.

Considero la grandeza que este mundo ha realizado
Y no es nada comparado al amor que tú me has dado,
En tu amor yo encuentro la paz, que el mundo me ha quitado.
Hoy te quiero dar las gracias y expresarte mi alegría
Porque siento dentro mío tu presencia todavía,
Que me dice: "hoy sigue adelante, ve al frente y camina"""
        ),
        Hymn(
            id = 275,
            title = "La Sunamita",
            link = "https://www.youtube.com/watch?v=DwkNMQGDkds",
            author = "",
            content = """Es la Iglesia la sunamita fiel que espera el día
Que pueda ver a su amado viniendo en una nube.
Espera el día aquel pueda oír la voz tan dulce y tierna
Del esposo diciéndole: "ven"

Coro
"Paloma mía, amada mía, iglesia mía,
Hermosa mía, perfecta mía, ya no estés triste, yo he venido;
He descendido yo a mi huerto, se han oído las trompetas.
Ya la esposa se ha vestido de lino fino,
Ya no estará la peregrina nunca triste
Porque hoy día han llegado las bodas del Cordero".

Es la Esposa virgen prudente que cuida el aceite,
Aun en dolor, en las pruebas, su lámpara está ardiendo.
Mujer Virtuosa que no descansa aquí
Mas viene el día que del cielo una voz ha de oír.

Final
"Paloma mía, amada mía, iglesia mía,
Hermosa mía, perfecta mía, Levántate y ven"""
        ),
        Hymn(
            id = 276,
            title = "La Trompeta",
            link = "https://www.youtube.com/watch?v=O2IYLKgcr6s",
            author = "",
            content = """Yo sé que pronto volverá
Si, ese día esperaré
Cuando en los cielos se unirán
Todos los fieles de la tierra.
La muerte ya no existirá
y sé que ya no sufriré,
Ya se divisa la mañana
de aquel día jubiloso
En que vuelves otra vez.

Coro
Una trompeta sonará
Como un estruendo en el cielo
Anunciándole a la novia
Que la Boda empezará
Y ese día cantarán
Los redimidos de la tierra,
Los que fueron perdonados
Por la Sangre de Jesús.

Si estas cansado de luchar,
Quizás se terminó tu Fe
Recuerda a Aquel que prometió
Que pronto habría de volver.
No te detengas a pensar,
pronto la noche pasará;
Sigue adelante peregrino
que Jesús está contigo
Y pronto te viene a buscar.

Final
///Gloria, gloria aleluya///
Anuestro Salvador.
//Gloria demos aleluya,
Gloria demos al Señor// Al Señor"""
        ),
        Hymn(
            id = 277,
            title = "Si hoy llegara Jesús",
            link = "https://www.youtube.com/watch?v=C6qvbRokEzw",
            author = "",
            content = """Si hoy llegara Jesús a tu hogar y te pidiera un poco de pan,
Si por las calles le vieras vagar ¿Qué le darías a El?
¿Qué le darías a El si le vieras llorar, un abrigo, una caricia,
O en tu mesa un lugar? //¿Qué le darías a Él? //

Si te dijera que en un hospital está, que está muy solo si le quieres tu cuidar,
Que abandonado en un asilo se quedó ¿Le irías tú a ver?
¿Qué le dirías a él si te diera tal misión, que tu tiempo es muy escaso,
O que ese no es tu don? //¿Qué le dirías a Él? //

¿Has meditado cuántos niños mueren hoy
Por la violencia, el hambre y la ambición;
Cuantos ya viejos se han quedado sin amor? ¿Qué harías tú por Él?
¿Qué es lo que harías por Él por cambiar tanto dolor
En un mundo tan violento, donde nadie piensa en Dios?
//¿Qué es lo que harías por Él?//

Final
¿Qué es lo que harías por Él si viniera a verte hoy?
Él no quiere indiferencia, necesita decisión ¿Qué es lo que harías por él?
¿Qué es lo que harías tú si viniera a verte hoy? Medítalo en tu corazón"""
        ),
        Hymn(
            id = 278,
            title = "Déjame que te alabe",
            link = "https://www.youtube.com/watch?v=Oj-LJCAi514",
            author = "",
            content = """Déjame encontrar tu mano, tu mano, oh Jehová,
Como el siervo que pidió el manto, tu manto dame hoy.
Vengo este día a traerte Señor mi canción,
Vengo humillado buscando de ti el perdón
Di la palabra y hazme libre del dolor.

Coro
Déjame que te alabe una vez y otra vez, como cuando era un niño,
Déjame que hoy te sienta otra vez. Quiero yo, elevar mi alma
A tu trono bendito, sólo así vivirá el corazón.

Del aceite de la unción, mi lámpara, hoy la quiero llenar,
Que se encienda en mi la luz que alumbre en tanta oscuridad.
Habla Maestro, hoy quiero tu voz escuchar,
Traigo el alma cansada, me duele el pecar
Una mirada de los cielos bastará.

Final
Déjame que te alabe otra vez"""
        ),
        Hymn(
            id = 279,
            title = "El Amor de Dios",
            link = "https://www.youtube.com/watch?v=JyJQGUw36AM",
            author = "",
            content = """Cuan sublime es el amor
El que no mide distancia.
Como suave brisa llega
Y te llena el corazón.
Él es quien te fortalece,
Con su diestra te sustenta,
Salvación para el perdido,
Agua fresca al peregrino
Galardón del vencedor

Coro
Es el amor de Dios
El que llena nuestras vidas,
El que cura las heridas
De esas que te hacen llorar.
Te da su mano, como él no hay otro amigo,
No te deja en el camino,
Siempre a tu lado él estará

Es la angustia o la prueba
Lo que te tiene afligido,
El viento sopla contrario
Y no te deja avanzar
Pero no te desanimes
Que Jesús hoy ha traído
A tu lámpara el aceite,
Oh soldado, sé Valiente
Él te puede levantar.

Coro
Es el amor de Dios
El que llena nuestras vidas,
El que cura las heridas
De esas que te hacen llorar.
Te da su mano, como él no hay otro amigo,
No te deja en el camino,
Siempre a tu lado él estará"""
        ),
        Hymn(
            id = 280,
            title = "Gracia Bendita",
            link = "https://www.youtube.com/watch?v=df3TpDCtJ8E",
            author = "",
            content = """Hay una Gracia Bendita que un día del cielo me vino a encontrar,
Perdonando mis pecados, quito mi tristeza y mi enfermedad.
Cuando yo estaba perdido, era el más perdido de los pecadores;
//Él se acercó a mi lado, me dijo: "mi hermano, te vengo a buscar //

Coro
Puso la paz dentro de mí, que no merecía,
Preciosa Sangre carmesí, limpió mi vida.
//Ahora le quiero yo encontrar, más allá de ese cielo azul
Donde un día le alabaré, será mi gratitud//

Hay un camino en mi vida, que día tras día me eleva hacia el cielo,
En los terribles momentos de mis desalientos, su mano él me da.
No me ha dejado un instante, que dicha que siento de ser redimido;
//Puedo sentir su presencia, su dulce mirada que me hace llorar//"""
        ),
        Hymn(
            id = 281,
            title = "La senda estrecha",
            link = "https://www.youtube.com/watch?v=JTzjV3IbsH4",
            author = "",
            content = """Tal vez estás cansado de luchar, decepcionado y te sientes derrotado;
La angustia te oprime el corazón, no encuentras la razón para seguir viviendo
La senda es estrecha y escabrosa con cardos y espinas que te rozan,
Guiones de tu vida vas dejando, pero presta atención, escucha por favor,
//Jesús te quiere hablar//

Coro
"Hijo mío ¿por qué piensas que de ti yo me olvidé?
Fue tan grande el castigo que por ti yo soporte.
Si yo di toda mi sangre fue para que seas feliz,
Hijo mio no lo olvides, quiero verte sonreír".

Quizás estás cansado de esperar, que nubes de pavor se aparten de tu lado,
La pena no te deja ver la luz de Cristo junto a ti brindándote sus manos.
Te llama con voz dulce el buen pastor, curando tus heridas con amor
Su sangre limpiará todo pecado, al cielo volarás, Jesús te llevará
//Y ya no llorarás//

Final
"Hijo Mio no lo olvides que mi vida di por ti"""
        ),
        Hymn(
            id = 282,
            title = "Estoy aquí",
            link = "https://www.youtube.com/watch?v=k1XK1gLaYFw",
            author = "",
            content = """Eres la llama, que no se apaga, bendita nube de Bendición
Cuando la fuente que llena el alma
Limpia mis culpas, quita las penas del corazón.
Me diste tanto en esta vida, no merecía de Tu favor
Por eso hoy día quiero alabarte, oh Dios del cielo, mi Salvación.

Coro
Estoy aquí agradeciéndote por tu inmenso amor
Porque le diste de tu paz al corazón,
Me amaste tanto oh bendito Salvador.
Ayúdame a comprender que el sacrificio fue por mí,
Que aquella sangre derramada en la cruz
Limpió mis culpas y me dio la paz con Dios.

Cuanto sufriste oh mi Maestro, por rescatarme de perdición,
Si era mi vida el mismo infierno
Tú me limpiaste y me libraste del tentador.
Nunca podría pagarte tanto, lo que tú hiciste por puro amor;
Por eso hoy día quiero ofrecerte, como una ofrenda mi corazón."""
        ),
        Hymn(
            id = 283,
            title = "Alfarero",
            link = "https://www.youtube.com/watch?v=gb6jxsaSwSs",
            author = "",
            content = """Alfarero, que das forma a la vasija con tus manos
Como el hombre del Edén que fue formado
Lo hiciste perfecto, él era tu imagen, por ti fue creado
Alfarero, tú que tienes potestad sobre el barro
Que transformas lo vil de este mundo en vasos de honra
Tu misericordia declara tu amor

Coro
Alfarero, tú que vuelves la rueda a girar, tu que puedes el barro amasar
Si quisieras mi vida formarsegún mejor te parezca
Alfarero, en tus manos yo quiero estar, sólo en ellas podrá encontrar
La forma perfecta de tu voluntad

Alfarero, que conoces mi salida y entrada,
Tú que viste la aflicción de mi llegada
Mi alma te anhela, espera en tu gracia, sin ella no es nada
Alfarero, que viniste a redimirnos del pecado
Que das fuerzas al caído y al cansado, derrama tu gloria, te ruego Señor.

Coro
//Alfarero, las vasijas ya limpias están, tu presencia nos ha llenar
De aceite del cielo en este lugar//"""
        ),
        Hymn(
            id = 284,
            title = "Siervo",
            link = "https://www.youtube.com/watch?v=wuENf2oLmiU",
            author = "",
            content = """Siervo que llegaste a la Gloria,
Lugar anhelado por ti
//Cuando fuerzas te faltaban
Y cansado tú estabas,
Tu pensabas en el cielo para seguir//

Siervo que la Buena Batalla
Has peleado guardando la Fe.
//Tu Corona que fue reservada
Dios te dio al fin de tu jornada,
Fue la paga de todas tus obras
Que hiciste aquí//

Coro
Y aunque males recibiste,
en silencio lo sufriste
Esperando la Promesa que
vendría por ti,
Porque el Señor estuvo a tu lado,
Te dio fuerzas para seguir
Y cumpliendo esa promesa
Hoy está junto a ti.

Siervo que camino te resta,
Come y bebe para poder seguir;
//Aun hay almas que te esperan Y
hay un trecho que nos queda,
Que la ayuda de quién te ha llamado
No ha de faltar//

Coro
Yaun que males tú recibas
hay un Dios allá arriba
Que te dice que "al cielo
Valientes lo arrebatarán".
//Mira aquellos que llegaron,
Y su espada ya dejaron,
Si te esfuerzas tu escudo también
Allí colgarás//"""
        ),
        Hymn(
            id = 285,
            title = "Llena hoy mi vida",
            link = "https://www.youtube.com/watch?v=s9BGZuFud6w",
            author = "",
            content = """Eres agua que sacia la sed del peregrino
Que camina por el desierto
Procurando llegar hasta el final
Eres agua que riega los corazones,
Reviviendo la esperanza y llenando los rincones
Y al sentir de Tu Presencia
Esta tierra que está seca
Comienza a revivir.

Coro
Llena hoy mi vida con Tu Presencia
Llénala de Tu existencia
y que empiece a florecer,
Quiero ser un árbol bueno
Y que lleve muchos frutos,
frutos dignos para El
No quiero ser la higuera
que muy frondosa
Solamente aparentaba
Más sin frutos se encontraba
Cava y riega hoy mi vida.
Para que muy pronto pueda
frutos buenos ofrecer.

Como rama injertada yo soy
que por Tu Gracia
Yo tengo vida porque no falta
La rica savia de Aquel que me injerto
Permaneced junto al olivo,
siempre está verde,
Nunca le falta el aceite,
siempre se mantiene vivo,
Si me falta Tu Presencia
Que es la savia que da vida.
Oh Señor yo moriré"""
        ),
        Hymn(
            id = 286,
            title = "Mefi-boset",
            link = "https://www.youtube.com/watch?v=RWwOUsVhI-s",
            author = "",
            content = """Allá en el olvido se vea Mefi-Boset,
Lugar de refugio y sustento, es joven y ve
Que solo se encuentra esperando que alguien le dé,
Él no puede andar es lisiado de los pies.
Su padre un príncipe fue, él ya lo olvidó.
El solo ha quedado y nadie pregunta por él
No puede entender que han venido buscándole,
Humilde y con miedo se encuentra delante del rey.

Coro
No tengas temor porque a la verdad haré
Misericordia por ti por amor a quien tanto amé,
En mi mesa estarás como un príncipe más
Y hoy te devolveré todo cuanto tenías también.
Nunca más estarás postrado allí,
Si sufriste el dolor por haber tu quedado así,
Si lisiado tú estás, te dejaron caer
Pero el Rey te ha traído aquí, misericordia hará por ti

Allá en el palacio se vea Mefi-Boset
Sentado a la mesa comiendo al lado del rey
No puede entender porque vive y pregunten por él,
Que ya no esté solo, hay un reino para él.
Olvida, y mira a Aquel que te trajo hasta aquí
Que viéndote así desvalido él quiso suplir
Con ojos de misericordia tu hambre y tú sed,
Sentarte a la mesa y que vivas, siempre con él."""
        ),
        Hymn(
            id = 287,
            title = "Mirarte sólo a Ti",
            link = "https://www.youtube.com/watch?v=6pezWdMkHNU",
            author = "",
            content = """Mirarte sólo a Ti, Señor
Mirarte sólo a Ti, Señor
Mirarte sólo a Ti, Señor
Y no mirar atrás

//Seguir tu caminar, Señor
Seguir sin desmayar, Señor
Postrado ante tu altar, Señor
Y no mirar atrás//"""
        ),
        Hymn(
            id = 288,
            title = "Job",
            link = "",
            author = "",
            content = """¿Por qué de Dios recibiremos lo bueno y no lo malo?
dijo aquel llamado Job. // Cuando todo lo perdió, cuando nada le quedó
Pudo decir Dios me dio, Dios me quito//

¿Qué integridad Dios me pide hoy recordarla? Que siendo justo aquel
varón Dios le probó //Con todo esto el no pecó ni de su boca una queja
salió Solo bendijo al Dios del cielo que lo aprobó//

Coro
Yo sé que Él Vive, sé que es mi Salvador
Y aunque la prueba sea muy dura yo le veré
Sé que el me ama y que en vano no he de sufrir
Que después que pasara la prueba me bendecirá

¡Qué integridad! Dios te pido, poder alcanzarla. Que el mundo vea que en
el sufrimiento le puedo alabar //Que no haya quejas sino una canción
Que no haya tristezas ni desolación
Solo bendice al Señor que del cielo dará bendición//"""
        ),
        Hymn(
            id = 289,
            title = "El Pastor",
            link = "https://www.youtube.com/watch?v=GssXw1ubi2A",
            author = "",
            content = """I
Estaba a punto de caer, en abismo infernal,
Un arbusto sostenía a la ovejita,
Por salirse del rebaño, por dejar a su pastor,
por huir de su amor, se hizo daño.

Coro
El Pastor, el buen Pastor mostró su amor
Por la oveja descarriada que era yo
Dio su vida para así poder librarme
Del abismo infernal del pecado
Donde a punto estuve yo, de perecer

II
Es Jesús el buen pastor, concebido de amor,
Me ha llamado tiernamente, me ha tomado
Y me trajo a su rebaño, me cuidó en su redil
Nunca mas yo huiré, de su amor"""
        ),
        Hymn(
            id = 290,
            title = "Bástate mi gracia",
            link = "https://www.youtube.com/watch?v=MiHs1JD3fBI",
            author = "",
            content = """"Ya tres veces he orado al Señor por mi aguijón,
Ya tres veces he pedido que lo quite el Señor
Es que no debo gloriarme ni exaltarme en lo que soy, pues la Gloria eterna es
de Dios". Estas son palabras aptas de un Siervo del Señor
El cual por amor a Cristo se gloriaba en su aflicción
En debilidad, afrenta y en gran persecución
Pues la Gloria eterna es de Dios.

Coro
Y le dijo a Pablo: "Bástate en Mi Gracia nada más,
Porque Mi poder se perfecciona en tu debilidad;
Y cuando tú eres débil mi poder contigo está, y mi gracia te sostendrá;
Porque Mi poder se perfecciona en tu debilidad, y mi gracia te sostendrá".
Cuantas veces mi hermano has orado al Señor, por cualquiera que sea tu
debilidad, y no sientes la respuesta del cielo a tu favor,
Trata de escuchar la voz de Dios,
Que te habla como a Pablo en medio de la aflicción
Con palabras de poder y de gran consolación
Que Su Gracia te regala por tu gran necesidad
Pues la Gloria y Gracia son de Dios."""
        ),
        Hymn(
            id = 291,
            title = "Ora mi hermano",
            link = "https://www.youtube.com/watch?v=WdKfe3CQLdU",
            author = "",
            content = """I
Pedro en la cárcel espera aquella sentencia de su hora final,
Ya a Jacobo han matado, a otros golpeado y de él ¿qué será?
Pero la iglesia oraba, Dios la escuchaba y su mano movió,
Envió un ángel del cielo que a Pedro libró.

Coro
Ora mi hermano que hay almas, que sufren cadenas, que sufren prisión,
Ora mi hermano por el que tendido sin fuerzas el camino dejó;
Ora Ferviente si el mundo ha atado a tu hermano a vicios error,
Ora Iglesia porque tu oración llega a Dios.

II
Mientras la iglesia oraba aquella prisión se iluminó;
"Levántate pronto Pedro, ciñe tus sandalias" el ángel le habló.
Las cadenas se cayeron, las puertas se abrieron, esto sucedió
Porque la iglesia oraba y Dios la escuchó."""
        ),
        Hymn(
            id = 292,
            title = "Mi abogado",
            link = "",
            author = "",
            content = """I
Un día en los cielos estará mi alma frente al Tribunal,
El juez será el Dios Eterno, el Rey de los cielos quien todo creó.
Estarán todas mis faltas, todos mis errores que me acusarán;
//Pero estará mi Abogado, el Crucificado, por mi abogará//

Coro
"En la cruz del calvario. yo todo he pagado, por el pecador
Y este que han acusado en mí ha confiado", dirá el Señor.
Y al terminar el juicio, el Juez Majestuoso, dirá con amor:
"Por ese sacrificio que hizo mi Hijo, oye pecador
Tú entrarás en la Gloria a gozar por siempre del reino de Dios".

II
Un día en los cielos estará mi alma frente al tribunal,
El juez será el Dios Eterno, el Dios de Abraham, de Isaac, de Jacob.
Si hoy al mirar mis vestidos que tal vez manchados, no podré entrar,
//Pero tengo mi Abogado, el Crucificado, por mi abogará//

Final
Por ese sacrificio que hizo mi Hijo por ti pecador"""
        ),
        Hymn(
            id = 293,
            title = "Ponte de Pie",
            link = "https://www.youtube.com/watch?v=G78l2cTG2ck",
            author = "",
            content = """I
En el templo de la Hermosa diariamente y de hace tiempo
Han traído nuevamente al cojo de nacimiento
Se ha sentado como siempre a la puerta a mendigar
Pidiendo misericordia solamente una limosna
Para su sustento hallar.
Se aproxima dos varones, estos son Pedro y Juan
Se conocen a lo lejos solo por su caminar,
Son aquellos pescadores que el Maestro escogió
Para llevar el mensaje de poder y salvación

Coro
Ponte de pie camina hermano
No ha cambiado el Señor ni su poder,
Ponte de pie porque hoy mi hermano
//Este mundo verá en ti el poder que te ha cambiado//

II
Estuvo atento esperando de ellos algo recibir
"Míranos” le dijo Pedro, no tenemos para ti
Ni plata, ni oro que te podamos dar,
Pero algo sí tenemos
“en el nombre del Cordero, hoy comienza a caminar”"""
        ),
        Hymn(
            id = 294,
            title = "El Aposento",
            link = "https://www.youtube.com/watch?v=mQhsKgPNCI4",
            author = "",
            content = """I
El Maestro te dice "¿Dónde está el aposento
Donde esta noche entraré a cenar?
El Señor te dice "¿Cuál es el Aposento?
Estará ya dispuesto para que esta noche Yo entre a cenar?

Coro
Y aquel hombre dispuesto abrió la puerta
Y un gran aposento allí les mostro
Y en ese aposento entró el Maestro
Y la última Cena con ellos comió
Señor hoy yo quiero abrirte la puerta
De este aposento que es mi corazón,
Quizás sea esta mi última Cena
Yo quiero comerla como él me ordenó

II
El Señor te dice "Mi tiempo está cerca,
Vendré a tu casa, esta noche cenaré
El Señor te dice "Prepara tu aposento,
Que esté limpio y dispuesto
para que en mi mesa tu puedas estar”"""
        ),
        Hymn(
            id = 295,
            title = "La Senda",
            link = "https://www.youtube.com/watch?v=17AV5jZQ5Uk",
            author = "",
            content = """I
Asediado voy por el camino
En diversas pruebas y aflicción,
Apartarme quieren de la senda
Que me lleva a estar con mi Señor,
No le temo a las pruebas del camino
Que me hieren como espinas al pasar,
No podrán apartarme de la senda
Así tenga que luchar hasta el final

Coro
Aveces en pesar, a veces en dolor, a veces en desiertos
tengo que caminar, pero no me detengo, camino sin cesar
A donde está Jesús yo tengo que llegar

II
Luchas y pruebas forman parte de la senda
Cual espino en el camino siempre están
Y por más que yo quiera eludirlo
Es en vano, no lo puedo evitar
Al final del camino El me espera
Cuando llegue su hermosura yo veré
Y me hará olvidar todas las penas
Que en la senda tuve que pasar."""
        ),
        Hymn(
            id = 296,
            title = "No te vayas sin Él",
            link = "https://www.youtube.com/watch?v=LHiXN0K2Q6Q",
            author = "",
            content = """No te vayas sin él, no te dejes vencer
Pues vivir sin Jesús no tendría razón;
Deja todo por él, ven y ríndete a él
Pues con él llegarás a su gloria sin par.
Antes, cuando esclavo era del mundo
No quería ni siquiera oír de Jesús
Pues creía que Jesús era una gran mentira,
Que todo esto era solo una gran fantasía.
Y en un canto pude oír la voz de Jesús
Que decía: "Fue por ti, que morí en la cruz"
"Ven conmigo, hoy tu vida Yo quiero cambiar",
Y así fue, y ahora te puedo invitar.
No te vayas sin él, no te dejes vencer
Pues vivir sin Jesús no tendría razón;
Deja todo por él, ven y ríndete a él
Pues con él llegarás a su gloria sin par."""
        ),
        Hymn(
            id = 297,
            title = "Va a Pasar",
            link = "https://www.youtube.com/watch?v=t900EIhXmyo",
            author = "",
            content = """Sé que todo se perdió, que todo ya murió, alguien te vino a hablar,
más ese alguien que te está hablando
no está sabiendo que Dios te va a ayudar.
//En el desierto, sí,
aprendí que los mayores milagros acontecen en el desierto, sí;
y sinceramente todo valle también tiene, un fin,
esa prueba en breve va a pasar, no va a quedar así.
Va a pasar, ese dolor y esa angustia, sé que va a pasar,
Dios te vio en esta lucha sé que va a pasar,
yo sé que va a pasar.
Va a llover una lluvia de victoria sé que va a llover, va a llover maná.
Se va a abrir esa puerta está cerrada sé que se va, a abrir,
Dios te vio en esta jornada, sé que se va a abrir.
Yo sé que se va a abrir,
quien te vio llorando angustiado por las madrugadas,
te va a ver sonreír.//
//Va a pasar//"""
        ),
        Hymn(
            id = 298,
            title = "Bartimeo",
            link = "https://www.youtube.com/watch?v=xZaSwjGG-iU",
            author = "",
            content = """I
Otra vez me encuentro mendigando
a la orilla de un camino olvidado
Sin saber quién está a mi lado,
sólo siento las monedas en mis manos,
En mis ojos la luz nunca he tenido,
no conozco el rostro de un amigo
Tampoco la tierra en que he nacido,
sólo escucho el caminar del peregrino.
Un bullicio, en la calle se comenta,
alguien grita que Jesús está muy cerca,
He oído de sus hechos y milagros,
tal vez sea este el Cristo que he esperado.

Coro
//Y gritaré, clamaré, le diré:
"Hijo de David, Escucha mi clamor
“Ten misericordia de mi"//

II
"Hijo Mio ¿qué quieres que te haga?"
Esa voz penetró hasta mis entrañas
"Oh Señor, quiero recobrar la vista
Y sé que eres mi única esperanza".
De pronto sucedió lo que he soñado,
Y me dijo que mi Fe me había salvado,
Mis pecados y mis faltas perdonados
Y mis ojos contemplaron a mi Amado.
Si hoy escuchas que Jesús está muy cerca,
Clámale a fuerte voz que se detenga
Tal vez sientas hoy el toque de Su mano,
Ten confianza que Jesús hoy te ha llamado.

Coro II
Y grítale, clámale, pídele
"Hijo de David Escucha mi clamor:
Ten misericordia de mi"."""
        ),
        Hymn(
            id = 299,
            title = "¿Qué viste en mí?",
            link = "",
            author = "",
            content = """I
¿Qué viste en mí, Señor? ¿Qué viste en mí.
Para ser un siervo lleno de tu amor?
¿Qué viste en mí, si nada tengo que ofrecer?
¿Qué viste en mí, Señor, que viste en mí?

Coro
Tuvo que ser tu Gracia mi Señor.
Yo era un gusano que arrastraba mi dolor
¿Qué viste en mí, Señor? ¿Qué viste en mí?
Si nada tengo y nada soy ¿Qué viste en mí?

II
Desde los cielos me miraste mi Señor
Y mi vergüenza tú cubriste con amor:
De mi pasado nada quiero recordar,
Me diste un nombre que es heredero eternal.

III
Tan grande amor aun no puedo comprender
Y me pregunto mi Señor ¿Qué viste en mí?
Solo te ruego que me guardes mi Señor
Para decir a todos de tu amor."""
        ),
        Hymn(
            id = 300,
            title = "Al caminar de tu mano",
            link = "https://www.youtube.com/watch?v=gqeevAoc6EM",
            author = "",
            content = """I
Al caminar de tu mano Señor
El mal no me puede alcanzar,
Vivo feliz pues sacaste el dolor
De mi ser y has dejado tu paz

Coro
Yo no quiero cambiar
El amor que hallé desde que te conocí;
Y al pensar que en el mundo no existe paz,
Quiero aferrarme de Ti

II
Tanto vagué por caminos sin luz
Sin saber que había un final
Pero un día feliz te encontré,
Mi Jesús conocí la verdad.

III
Todo el pesar alejaste de mi
Y mis pasos sacaste del error,
Sé que Tú amor nunca terminará
Como soy Tú me amas Señor."""
        ),
        Hymn(
            id = 301,
            title = "Hablar con Dios",
            link = "https://www.youtube.com/watch?v=ac0M1e1JZt8",
            author = "",
            content = """En la oración encuentro calma, En la oración encuentro paz,
Orar a Dios refresca el alma, Hablar con Dios me da solaz
Hablar con Dios, que privilegio, Abrir mi alma al Creador.
Sentir que el cielo está abierto, Y oír la voz del Salvador.

Coro
Grande es nuestro Dios, Y las obras de su amor
Y su poder es infinito. Me perdonó mi Salvador.
Hablar con Dios yo necesito, Me da las ganas de vivir,
La vida en él tiene sentido, Pues sé que Él me puede oír."""
        ),
        Hymn(
            id = 302,
            title = "Dios tiene poder",
            link = "https://www.youtube.com/watch?v=4oxeg5OI_Yc",
            author = "",
            content = """Estaba el Señor enseñando a fariseos,
Parecía un día más que a Jesús iban a escuchar
Pero con Jesús estaba el poder para sanar
Y de ese poder un grupo de amigos quiso aprovechar.
Trajeron a él un paralitico estando en su lecho,
Iban a Jesús, sabían muy bien que podía hacer;
Entonces Jesús al enfermo miró y le dijo: "levántate ya"
Y el pueblo entendió Quien tiene la potestad.

Coro
Dios tiene poder, poder, poder, poder Celestial;
Él tiene poder, poder, poder para libertar.
No hay límites ni oposición, cuando Cristo quiere obrar
//Dios tiene poder//
Como aquella vez el Señor hoy quiere enseñarnos,
Parece un día más que con Jesús vinimos a estar
But aquel poder que tanto escuchamos en la antigüedad,
El mismo poder hoy sigue vigente en este lugar.
¿Qué traes a él, tienes un pedido hace mucho tiempo,
Quieres desistir, perdiste tu fe, ya no hay nada que hacer?
Pero hoy Jesús a ti se acercó y te dice: "levántate ya",
Hoy tú podrás ver Quien tiene la potestad."""
        ),
        Hymn(
            id = 303,
            title = "Preciosa Sangre",
            link = "",
            author = "",
            content = """I
Hay un poder que no resiste el enemigo,
Hay un poder que hecha fuera todo mal;
Rompe cadenas y levanta al que ha caído,
Al oprimido que ya no puede avanzar.
Ese poder está en la sangre del Cordero
Solo al nombrarla ella trae libertad,
Ya no me importa si me acusa el enemigo
En esa sangre me puedo yo lavar.

Coro
Preciosa sangre el enemigo
ya se tiene que marchar,
Preciosa sangre
ya está bajando desde el cielo libertad.
Preciosa sangre,
poder tan grande que ha llenado este lugar
Mi vida nunca será igual;
Señor tu sangre me revista,
Quiero siempre cubierta de ella estar.

II
Ya creo ver cómo está huyendo el enemigo,
él no soporta verme limpio una vez más
Y aunque pretenda hacerme ver que yo he caído
Esta vencido y se lo quiero recordar
Yo estaba destinado al sufrimiento eterno
Pero esta sangre mi rumbo ya cambió,
Hoy voy con gozo a donde esperan redimidos
Los que con sangre comprados ya están."""
        ),
        Hymn(
            id = 304,
            title = "Dios no fallará",
            link = "https://www.youtube.com/watch?v=BMVU_khGZ1M",
            author = "",
            content = """I
¿Qué está pasando con tu vida que se encuentra confundida
Y que no sabe a dónde va?
¿Qué está pasando con tu vida que en la noche está perdida
Y ya no puede descansar?
Atrás quedaron buenos tiempos donde todo era perfecto,
Donde nada hacía dudar
//Hoy ha llegado el crudo invierno y tu cielo se ha cubierto,
Temes por lo que vendrá//
Pero no puedes detenerte, solo debes ser valiente,
no hay porque desesperar
Tras cada noche viene el día y tras las nubes el sol brilla,
este tiempo pasará;
Recuerda que Dios marca el paso, él nunca tuvo un fracaso
la victoria llegará
//Levántate no estés caído porque no serás vencido
el Señor hoy peleará//

Coro
Ponte de pie, sigue luchando porque Dios es capitán
No te detengas, no desmayes, no estés mal
No des el gusto al que te quiere ver llorar.
Ponte de pie que Dios es Santo y el merece tu cantar
Y la alabanza la victoria traerá;
Arriba el ánimo que Dios no fallará

II
Qué es eso de vivir perdido, tan errante y confundido
¿No es de Dios tu condición?
Con fe levanta tu cabeza, hoy termina tu tristeza, la victoria ya llegó
Hoy Dios resuelve tu problema va a romper cualquier cadena,
Mandará la solución
//Pues tu dolor lo ha conmovido, todo cielo él ha reunido
Y viene la liberación//
Escucha que viene marchando un ejército que es santo,
que batallas no perdió
Ángeles vienen preparados y por Dios están guiados,
todo está bajo control
Ese lugar que no llegabas, esa herida que sangraba
el Señor ya se encargó
//Por donde el pasa todo cambia, cuando él llega hay esperanza.
Siempre trae bendición//"""
        ),
        Hymn(
            id = 305,
            title = "Dios me ha hecho reír",
            link = "https://www.youtube.com/watch?v=SIrU1upkZAY",
            author = "",
            content = """I
La acompañó la angustia por muchos años,
Largas fueron las noches que Sara lloró,
Su vida se fue pasando con un gran anhelo
Y una Promesa de Dios que no llegó.
Ella no sabía que las Promesas de Dios no pueden faltar
Y el día menos pensado allá en su tienda
El llanto de un tierno niño se pudo escuchar.

Coro
Dios me ha hecho reír.
Después de tanto llorar, después de tanto sufrir
Después de pasar tantas noches en mi soledad
Pensando que Dios de mi se iba a olvidar.
Dios te ha hecho reír
A ti que también como a mi te ha tocado llorar,
Esa misma razón que un día nos hizo sufrir
Es la misma razón con que Dios nos ha hecho reír.

II
La vida trajo caminos que no esperaba,
Me puso cerrojos de llanto y de soledad
Y allí me quedé yo mirando como mis sueños
Se desvanecían cual niebla en la oscuridad.
Más yo no sabía que hasta allí mi Jesús me iba a buscar,
Él se acercó hasta mí y me dio su mano
Y es por eso que hoy como Sara yo puedo cantar.

Final
Dios me ha hecho reír
Y es por Su Gracia que yo ya no lloro más,
Desde que mi alma sintió su dulce perdón
He aprendido que ya no debo llorar.
//Dios me ha hecho reír//"""
        ),
        Hymn(
            id = 306,
            title = "Despierta",
            link = "",
            author = "",
            content = """I
Despierta, la mañana que esperabas
Va llegando apresurada, esta vez será,
Despierta ya, este mundo y sus placeres
Han logrado distraerte del día que aguardabas
Despierta, el reloj allá en los cielos
Está marcando ya es la hora, El regresará,
Los salvos volaran a su encuentro que feliz será.

Coro
Despierta, el Rey de reyes está volviendo en la nube a reinar,
Todo su pueblo se levanta para recibir
Al Rey que viene ya, viene ya.
Porque serás tú el postrero en hacerle volver
Tú eres parte de su cuerpo lo debes saber,
Ahora levántate, viene ya, viene ya, Cristo viene ya.

II
Despierta, pues tu amor se ha enfriado,
Ya no oras ni le sirves, no le adoras más,
Despierta ya, ese gozo que sentías
Y esa paz que te invadía cuando antes le servías.
Despierta, el invierno ha pasado
Y la lluvia se ha mudado, tiempo es de cantar
Pues vuelve con las nubes del cielo el que te redimió."""
        ),
        Hymn(
            id = 307,
            title = "Amor tan grande",
            link = "https://www.youtube.com/watch?v=rTNB5XXZZj0",
            author = "",
            content = """I
Amor tan Grande, profundo y sublime
Es el amor de mi Creador,
No hay nada en el mundo que pueda igualarse
Al tierno amor mi Señor.

Coro
Dios de amor, Dios de amor
Solo eres Tú el Dios de amor,
No hay otro Dios fuera de ti,
Sin tu amor para mí no hay amor.

II
Él solo me ama, protege y guarda
De tantos males que existen aquí;
Por eso lo alabo con toda mi alma
Because me ha dado Jesús dulce calma."""
        ),
        Hymn(
            id = 308,
            title = "Señor ayúdame",
            link = "https://www.youtube.com/watch?v=wlPIWHeKa5E",
            author = "",
            content = """I
Señor Ayúdame por Ti a vivir,
porque sin ti no puedo aquí seguir,
Has que yo tenga en mi continuo andar, tu bendición, tu dulce paz
Quiero alejarme de la tentación, quiero vivir tan solo por tu amor,
Te doy mi ser, te doy mi corazón, dame poder oh buen Señor.

Coro
Si estoy andando en la ciudad, entre los valles o el mar,
Las pruebas luchan con mi fe; Señor ayúdame, quiero vencer.

II
Señor ayúdame a comprender, que soy probado solo para bien,
Te ruego se conmigo en mi luchar, para que así no pueda errar.
A veces mi canción quiere llorar, al ver que a mi redor hay tanto mal,
Mas hasta que tu reino venga oh Dios, dame sostén y protección."""
        ),
        Hymn(
            id = 309,
            title = "Busca a Dios",
            link = "https://www.youtube.com/watch?v=nBP1XV-Gz24",
            author = "",
            content = """I
Busca a Dios mientras puede ser hallado,
Busca a Dios mientras tan cercano está
Vendrán días cuando quieras encontrarlo
Será tarde, él ya no estará.

Coro
Dios es Bueno, es el mismo, no ha cambiado
Siempre está listo para dar felicidad
Por eso hermano, es urgente y necesario,
Buscar a Dios y darle el primer lugar.
Si eres rico, si eres pobre,
Si a tu vida falta algo, busca a Dios;
Si estas triste y afligido
Sin lograr lo que has querido,
Busca a Dios;
Lo encontrarás, te perdonará,
llenará tu vida de amor y paz.

II
Busca a Dios mientras pueda ser hallado
Ya no queda mucho tiempo que perder,
Si hoy buscares su reino y su justicia
Las demás cosas añadidas serán."""
        ),
        Hymn(
            id = 310,
            title = "Tengo motivo",
            link = "",
            author = "",
            content = """I
Tengo motivo de cantar a Dios,
Tengo motivo de vivir para él,
Tengo motivo de gozar de su amor.
Tengo motivo de agradecer.
Si por su vida, él mi vida cambió
Y por su muerte recibí salvación
Tengo motivo de alabar,
Tengo motivo de alegrar mi corazón.

Coro
Porque viniera a este mundo el Señor
Porque viniera a darle tanto perdón,
Tengo motivo de orar,
tengo motivo de ensalzar,
//Tan grande amor//

II
Tengo motivo de hablar de Dios,
Tengo motivo de hacer lo canción,
Tengo motivo de aguardar a mi Rey.
Tengo motivo de serle fiel.
Cuanta belleza su pobreza mostró,
Cuanta riqueza por salvarme dejó.
Tengo motivo de pensar
Que aunque yo tenga que esperar veré a Dios."""
        ),
        Hymn(
            id = 311,
            title = "Que lindo es mi Cristo",
            link = "https://www.youtube.com/watch?v=uO-rR32YHh8",
            author = "",
            content = """I
Que lindo es mi Cristo, cuán grato es su amor
Yo andaba perdido Él vino y me halló
Con sus tiernas manos el me acarició
Tomome en sus brazos y allí me abrazó

Coro
Que dulces caricias las del Salvador
Caricias que llenan mi alma de amor
Su voz me asegura, conmigo Él está
Está para siempre, por la eternidad

II
Que dulce es mi Cristo, cuán grande es tu amor
Que diste tu vida por mí pecador
Dejaste tu trono por venir aquí,
Buscando al perdido, me encontraste a mi"""
        ),
        Hymn(
            id = 312,
            title = "Socórreme Señor",
            link = "https://www.youtube.com/watch?v=ggpEvvUskso",
            author = "",
            content = """I
Te sientes débil confundido ya
Creyendo que haz de perecer
La lucha es dura y pierdes el vigor
Anhelas pues atrás volver
No olvides que Jesús velando está
Aunque contrario el mar esté
Pues de un momento a otro tú también
Caminarás sobre las aguas hacia El

Coro
No dejes que el temor haga perder tu fe
Sigue luchando hasta vencer
Los vientos soplarán y el mar se agitará
Pero a la voz potente del Señor
La tempestad se tornará en calma y paz

II
Los planes del Señor secretos son
Tú debes sólo obedecer
Quizás sea muy difícil para ti
Luchar y aún confiar en El
Mas si tu vista apartas de Jesús
Y miras en tu derredor
Te irás hundiendo en este bravo mar
Y clamarás ansioso ¡Sálvame Señor!"""
        ),
        Hymn(
            id = 313,
            title = "Espíritu de Dios",
            link = "https://www.youtube.com/watch?v=R6mYMSHaFwA",
            author = "",
            content = """I
Espíritu de Dios te agradezco que en mí estés,
Que hayas tú entrado en mi corazón
Eres en mi aflicción, consuelo, paz, seguridad,
Mi pronto auxilio siempre, mi ancla y mi sostén

Coro
Eres todo Señor, yo, yo nada soy
Vivo por ti, pues palpitaste en mí
Motivo eres de mi existir

II
Espíritu de Dios verás aquí un pentecostés,
Como a los ciento veinte derrama tu poder.
Espíritu de Dios, el cielo pueda hoy descender,
Solo un sentir nos una, y entonces llenes mi ser."""
        ),
        Hymn(
            id = 314,
            title = "¿Por qué temer?",
            link = "https://www.youtube.com/watch?v=r-hfsb7W-1g",
            author = "",
            content = """I
Por qué temer si no estamos solos,
Si hay un Dios que camina conmigo en la tempestad.
Por qué temer si nuestro Dios es tan grande,
Un poderoso gigante que en plena batalla te ayuda a luchar
Por qué temer si Jesús es mi amigo,
Su presencia Él ha prometido y sé que no fallará,
Por qué temer si su ayuda es constante,
Seguiré yo adelante hasta que a la meta yo pueda llegar.

Coro
Si estoy vivo peleando batallas, y nadie lo sabe,
Que los carros de Dios hoy se cuentan de a millares
//Aunque el duelo golpee mi puerta y abatida mi alma se encuentra
En Jesús el dador de la vida, yo voy a confiar.//

II
Debo seguir, camino hacia el cielo,
Me levanto, sacudo mis ropas, no vuelvo a mirar atrás,
Debo seguir siendo fiel y constante;
Una corona me espera, si llego a la meta Jesús me dará"""
        ),
        Hymn(
            id = 315,
            title = "El Señor es mi luz",
            link = "",
            author = "",
            content = """I
El Señor es mi luz y mi salvación, ¿De quién temeré?
Él es la fortaleza de mi vida, Roca de salvación.
Alumbró su justicia sobre mí, sobre roca puso mis pies;
mi cabeza levantó, mis enemigos ahuyentó,
alabanzas a su nombre cantaré.

Coro
Aunque ejercito acampe contra mí
no temerá mi corazón, estarás ahí.
Aunque guerra se levante,
aunque todos me rodearen, confiaré en Ti.
¡Si confiaré en Ti! Estaré confiando en Ti

II
Escucha, Señor, la voz de mi clamor,
en Ti confiaré, tu rostro buscaré,
mi ayuda has sido, Dios de mi salvación.
Alumbró su justicia sobre mí, sobre roca puso mis pies;
mi cabeza levantó, mis enemigos ahuyentó,
alabanzas a su nombre cantaré."""
        ),
        Hymn(
            id = 316,
            title = "No te rindas",
            link = "https://www.youtube.com/watch?v=LedgmhNpIPo",
            author = "",
            content = """I
Cuanto tiempo llevas con tu sufrimiento,
no hay remedio que te sirva de consuelo,
te has cansado de esperar, te has cansado de llorar,
te has cansado de luchar y trabajar.
No te rindas la carrera aún no termina,
no te rindas solo tienes que llegar
y aunque solo te has sentido es Jesús tu fiel amigo,
siempre estuvo allí y siempre allí estará.

Coro
Dios va a mandar restauración, prosperidad.
Él va a llegar con libertad, con santidad ya pasará,
ese problema que te aflige pasará
Levántate se fue el invierno hoy es tiempo de cantar,
no te rindas, no abandones si resistes triunfarás,
queda un poco más pronto llegará el final.
Se valiente la victoria cerca está.

II
Como Elías en aquel monte Carmelo,
de rodillas humillado oró hacia el cielo,
y aunque grande la sequía, esperanzas ya no había,
el confiaba que su Dios no iba a fallar,
Sólo escucha una lluvia grande viene
esa nube que se asoma es la respuesta
ese tiempo que faltaba se cumplió y no falta nada,
Dios ha sido fiel y siempre fiel será."""
        ),
        Hymn(
            id = 317,
            title = "Mi Dios es Real",
            link = "https://www.youtube.com/watch?v=5vk872jjUGw",
            author = "",
            content = """I
Hay cosas que yo no comprendo
lugares hay do yo no iré.
Pero si se, y es verdad mi Dios es real,
porque lo siento en mi ser.

Coro
//Mi Dios es real, real en mi ser,
me ha lavado con su sangre carmesí,
Su dulce amor es para mí,
Mi Dios es real, porque lo siento en mi ser//

II
Contigo voy Él me dice hoy,
Y quiero ir a su mansión.
Seguro estoy Él me ayuda hará
Mi Dios es real, porque lo siento en mi ser."""
        ),
        Hymn(
            id = 318,
            title = "¿Dónde estabas Tú?",
            link = "https://www.youtube.com/watch?v=f_VvfdH9A2w",
            author = "",
            content = """I
Dónde estabas Tú? Marta preguntó
Lázaro se fue, Lázaro murió
Dónde estabas Tú? Cuando te busqué
Si hubieras venido el estaría aquí
Más Jesús le vio y sólo respondió
Tu hermano resucitará
Eso que pasó, para gloria es
El duerme y lo voy a despertar
Dónde estabas Tú? María preguntó
Grande era el dolor, Jesús se conmovió
Dónde estabas Tú? Dónde mi Señor?
Si hubieras llegado todo habría cambiado
Dime dónde está, yo le iré a buscar
Si crees todo cambiará
Mi Jesús lloró, pero no tardó
Confía, aun los muertos vivirán.

Coro
Si hoy puedes creer, todo puede ser
Dijo Cristo y todo nuevo hará
Si algo hay que quitar él lo quitara
Y el camino libre quedará.
Aquí es donde estoy hoy dice el Señor
Él se ha detenido
Si crees todo puede ser

II
Dónde estabas Tú? Hoy pregunto yo
Mira cómo estoy, que triste condición,
Dónde estás Señor? Hoy te quiero ver,
Y si has de quedarte, quiero que sea aquí
Sabes cómo estoy, sabes que pasó
Y sabes que siempre espero en ti,
Y si estás aquí quiero aprovechar
Confieso Cristo sólo creo en Ti"""
        ),
        Hymn(
            id = 319,
            title = "Escucho tu voz",
            link = "https://www.youtube.com/watch?v=jPzvJNEwSNc",
            author = "Cantores Unidos del Noa",
            content = """I
Escucho tu voz como una suave brisa,
Me ha inundado tu amor ha llegado a mi vida,
floreció la razón de seguir caminando esta vida,
es el guía para conseguir, mi vida eterna.

Coro
Tirado estaba yo, más Tú me encontraste,
me tendiste tu mano, tus manos de amor.
No solo me sanaste, sino también me salvaste
y por tu misericordia, hoy te alabo Señor.

II
Eres tú mi refugio, la roca eterna,
que en tus alas las halle mi pronto auxilio,
semejante a un castillo será, mi alabanza en tu gloria,
el camino para conseguir, la vida eterna."""
        ),
        Hymn(
            id = 320,
            title = "Siempre caminé",
            link = "https://www.youtube.com/watch?v=How6JsGqkEI",
            author = "",
            content = """I
Siempre caminé en el mundo sin saber que había un Dios.
Siempre caminé, por caminos de tinieblas y maldad.
Nunca imaginé que posara su mirada sobre mí,
y me escogiera desde el vientre de mi madre.
Siempre rehusé, a creer en su palabra y, en su amor.
Siempre rechacé cuando alguien me hablaba del Señor.
Nunca, imaginé que posara su mirada sobre mí,
y al conocerme me mostrara su amor.

Coro
//Y mi me tocó, no habiendo nada bueno en mí,
me transformó, cambió el deseo de vivir, con su amor.
Calmó la sed de mi alma y mi corazón. Él lo llenó//

II
Siempre que caí, a mi lado siempre estuvo el Señor.
Siempre que lloré, con sus manos el mis lágrimas secó.
Nunca, imaginé que posara su mirada sobre mí,
y que mi vida Él pudiera redimir.
Siempre rechacé, no sabiendo que Él me daba salvación,
nunca encontré la felicidad que Cristo, a mí me dio.
Nunca me imaginé que posara su mirada sobre mí,
y que me amara, como nadie más amo."""
        ),
        Hymn(
            id = 321,
            title = "Al final del Camino",
            link = "https://www.youtube.com/watch?v=9D4xl2lVguM",
            author = "",
            content = """I
Cuanto tengo que esperarle no lo sé, solo sé
que una mañana el maestro volverá.
Y aunque pase mucho tiempo siempre al cielo,
miraré, para que, no me abandoné y la fe jamás me falte,
hasta que le vuelva a ver.

Coro
Y al final, del camino, Él me espera para
darme vida nueva y una corona de paz.
Más allá voy a ver a mis amados
los que tanto he llorado, sólo tengo que esperar.
Y al final, cuando entregue mi jornada
voy a verle cara, a cara y ese día llegará.

II
Que estoy lleno de problemas, es verdad,
que me siento algo cansado y afligido puede ser.
But hay algo dentro mío, no es mi fuerza,
es la de Él, que me dice
“Ya no temas, mucho tiempo ya no queda,
Cristo pronto ha de volver"""
        ),
        Hymn(
            id = 322,
            title = "Libre seré",
            link = "https://www.youtube.com/watch?v=5hadEEwD7xo",
            author = "",
            content = """I
Vengo a Ti mi Señor para contarte lo que quizás
nadie podía entenderme, ni siquiera podía escuchar.
Vengo, a decir que tengo miles de errores, falencias sin par;
ruego Señor que me ayudes, te quiero agradar.

Coro
Si tu manto yo tocare seguro estoy, que libre seré,
si tu manto yo tocare mi alma tendrá el soberano poder.
Que al mundo ha vencido, por eso
he venido Señor a tu pies, dame Señor tu virtud
porque quiero vencer.

II
Quiero contarte Jesús que este mundo me presiona más
y seguir tus pisadas día a día me cuesta más,
pero la gran multitud, no pudo apagar la fe de esa mujer;
dame Señor de tu gracia y aumenta mi fe."""
        ),
        Hymn(
            id = 323,
            title = "Una angustia más",
            link = "https://www.youtube.com/watch?v=p-RQcLxEFe0",
            author = "",
            content = """I
Una angustia más me viene a embargar,
no es la primer vez, ni es la última.
Una angustia más que permite Dios
para recordarme que nada soy

Coro
Y en las lágrimas, el Señor está
señalándome mi debilidad,
y su Espíritu viene a consolar,
viene a darme paz, para continuar.

II
Mis pecados son una realidad,
mi limitación me humilla más.
Pero el gozo esta, y es mejor aún
de lo que llamamos felicidad."""
        ),
        Hymn(
            id = 324,
            title = "Creo en Dios",
            link = "https://www.youtube.com/watch?v=4ZELidlfT7I",
            author = "",
            content = """I
¿Cómo puedes no creer en Dios?
¿Cómo intentas ignorar su voz?
Él ha dado muestras de sus grandes maravillas,
¿Puedes ver su poder?
Él eclipsa el sol hasta opacar,
la más densa noche hace brillar,
todo lo transforma, en su designio soberano,
porque es Dios, solo Él es Dios.

Coro
Creo en Dios, su voz levanta el viento y mar en tempestad
y por su mano alzada pronto acallarán,
aun cuando todo este perdido yo creo en Dios,
porque Él es mi esperanza en medio del dolor,
confiado cantaré su grande salvación,
porque vivo y creo en Dios.

II
Dios al mundo envuelve con su amor,
su justicia y tierna compasión.
Más su gracia alcanza a todo aquel
Que arrepentido clama a Él, solo a Él.
Y aunque no lo puedas vislumbrar,
Dios es siempre fiel hasta el final,
porque ha prometido caminar a nuestro lado,
cerca está, porque Él es Dios"""
        ),
        Hymn(
            id = 325,
            title = "Está aquí, Su gloria está aquí",
            link = "https://www.youtube.com/watch?v=VipZlggLfaE",
            author = "",
            content = """El Dios que abrió el Mar Rojo,
El Dios que levantó a Lázaro,
Ese Dios que calmó la tempestad está aquí.
El Dios que sanó a los ciegos,
El Dios que levantó a los muertos,
Ese Dios que ha resucitado está aquí.
Y si tan solo tu crees verás el poder de Dios,
Que en esta noche se está moviendo en este lugar
Esto no es para todos los hermanos
Sólo es para aquellos que creen en Él
Y si en esta hora tu crees verás el poder de Dios.
Está aquí, su gloria está aquí
Sanando tu corazón, sanando tu cuerpo.
Está aquí sanando y libertando
Está aquí restaurando y levantando, hoy abre //tu corazón//"""
        ),
        Hymn(
            id = 326,
            title = "Dios sabe lo que hace",
            link = "https://www.youtube.com/watch?v=a18797sITO0",
            author = "",
            content = """I
Dios sabe lo que hace, Él no llega tarde,
Él no se equivoca, Él está en control.
Dios sabe lo que hace, aún en lo inexplicable.
Él es incuestionable, cuando algo en ti determinó.

Coro
Dios sabe lo que hace, Él conoce los tiempos,
No pierdas la esperanza, Él está en control.
Dios sabe lo que hace, aunque tu no comprendas,
El es tu fortaleza en tiempos de aflicción.
Dios sabe lo que hace.

II
Dios sabe lo que hace, aun cuando algo nace,
Aun cuando algo muere, Él está en control
Dios sabe lo que hace, cuando en la vida cambios suceden,
Con algún propósito, el lo permitió

III
Si esto es algo que yo tengo que pasar, nuevas fuerzas Él me dará.
Aunque tiemble la tierra, se echen los montes a la mar.
Aunque yo no comprenda lo que tengo que pasar
No peleo, no cuestiono, no pregunto, porque…"""
        ),
        Hymn(
            id = 327,
            title = "Tú estás aquí",
            link = "https://www.youtube.com/watch?v=Wzr-KHV0DvQ",
            author = "",
            content = """Aunque mis ojos no te puedan ver,
Te puedo sentir, se que estas aquí
Aunque mis manos no pueden tocar tu rostro Señor,
Se que estas aquí.
Mi corazón puede sentir tu presencia.
Tu estás aquí, Tu estás aquí
Puedo Sentir Tu Majestad..
Tu estás aquí, Tu estás aquí
Mi corazón puede mirar tu hermosura..
Tu estás aquí, Tu estás aquí
Puedo Sentir Tu Gran Amor
Tu estás aquí, Tu estás aquí"""
        ),
        Hymn(
            id = 328,
            title = "Tu presencia es más hermosa",
            link = "https://www.youtube.com/watch?v=vvgwTpNmmv8",
            author = "",
            content = """I
No sé por qué, Señor tu me tocaste, no sé por qué,
En mi tu te fijaste, pero sí sé que es grande tu amor por mi.
Mi corazón está agradecido, mi corazón está a ti rendido,
Contigo siempre quiero Señor estar.

Coro
Porque fuera de ti nada deseo en la tierra,
Tu presencia es más hermosa, que cualquier cosa
Porque fuera de ti nada deseo en la tierra,
Tu presencia es más hermosa, que cualquier cosa

II
Tu eres Señor mi gozo y alegría, tu estás Señor conmigo todo el día,
Contigo siempre quiero Señor estar.

III
Me has librado de la muerte, me has dado nueva vida,
Me sacaste mi tristeza, me ceñiste de alegría.
Por tanto nunca callaré, a ti cantaré, te alabaré gloria mía.
Cristo mi Señor, a ti cantaré, te alabaré gloria mía."""
        ),
        Hymn(
            id = 329,
            title = "Yo he creído",
            link = "https://www.youtube.com/watch?v=LP3z-oDmjH0",
            author = "",
            content = """I
Yo he creído con toda mi alma en un Dios poderoso
He confiado en aquel que salvo mi alma en la cruz
Y a pesar que la prueba amenaza cual brava tormenta
Me doy cuenta que en la barca está Jesús a mi lado

Coro
//Contigo yo tengo Señor, la paz que anhelaba
Contigo yo tengo el consuelo a mi alma y la felicidad,
Contigo yo tengo el futuro en mis manos,
Pues si nada tuviera yo sé que mañana me bendecirás.//

II
Yo he visto con mucha tristeza a aquel que dudaba,
Pesimista, sin esperanza que vive sin fe,
Y la Biblia me muestra la fe de grandiosos profetas
Que levantan, alientan mi ser y puedo creer.

III
Cuántas veces faltando a la mesa a Dios yo clamé,
Y estando cerradas las puertas Jehová las abrió,
Siempre mi Dios respondiste mi humilde plegaria,
Tus promesas se cumplen en mi y te doy las gracias"""
        ),
        Hymn(
            id = 330,
            title = "He visto",
            link = "https://www.youtube.com/watch?v=CBJFurvCnMQ",
            author = "",
            content = """I
He visto cadenas romperse, montañas moverse
He visto las enfermedades desaparecerse,
He visto los yugos pudrirse, he visto barreras caerse,
He visto a Dios en tus problemas siempre detenerse y moverse,
Yo he visto a Dios quitar tristeza y poner su alegría,
Yo he visto que a los moribundos también les da vida,
He visto que quita el dolor, lo he visto sanar mis heridas
He visto y se que en tus dilemas Dios es solución, Dios es la salida

Coro
He visto que cuando lo alabo el derrama su gloria,
Y que cuando su pueblo clama el le da la victoria,
Que en pruebas y dificultades, su iglesia nunca deja sola
Yo he visto a Dios obrar cuando su pueblo lo adora.

II
He visto a Dios hacer posible lo que es imposible a la mente humana
También en medio del desierto lo he visto dar agua,
Poner donde no hay nada, responder cuando tu lo llamas
Yo he visto a Dios decir presente, cuando con fe sus hijos claman.

Coda
Cuando adora"""
        ),
        Hymn(
            id = 331,
            title = "Job",
            link = "",
            author = "Conjunto Amigo Fiel",
            content = """Presentación:
Y respondió Job y dijo: “yo sé que mi Redentor Vive y al fin se levantará
sobre el polvo, y después de deshecha esta mi piel, en mi carne he de ver
a Dios, al Cual veré por mí mismo y mis ojos lo verán y no otro, aunque mi
corazón desfallece dentro de mí”.

//Yo sé que mi Redentor Vive
Y mis ojos le verán//
En prueba o en dolor
Confió en ti,
En angustia o aflicción
A tu lado está//
Yo sé que mi Redentor Vive
Y mis ojos le verán."""
        ),
        Hymn(
            id = 332,
            title = "Joven a ti te dice",
            link = "",
            author = "Conjunto Amigo Fiel",
            content = """I
Una viuda de Naín
Llora sin consolación
Pues su único hijo perdió.
Al llegar a la ciudad
A Jesús encontró
Y un milagro potente él obró.

Coro I:
Joven a ti te digo: “levántate”
Con estas Palabras Jesús vida le dio.
Milagro de amor, no hay más dolor
La muerte en vida cambió.

II
Cuanto tiempo llevas ya
Alejado del Señor,
Has perdido todo lo que él te dio.
Ya no hay paz, sincero amor
No hay Fe, no hay Salvación;
Escucha el mensaje de amor:

Coro II:
//Joven a ti te dice: “levántate”
Una iglesia llora por tu restauración;
Da un paso de Fe, ríndete a él,
Vida y perdón te dará//"""
        ),
        Hymn(
            id = 333,
            title = "Más que Vencedores",
            link = "",
            author = "Trio Acuña",
            content = """I
Somos más que Vencedores en Jesús
Cantamos Gloria, alabando al Salvador,
Saldando cuenta que era nuestra en la cruz
Nos hizo libre para ser un Vencedor.
Dice la Biblia: “a los que aman a Dios
Todas las cosas les ayudaran a bien”,
Aun las pruebas al Cristiano es bendición
Porque en Jesús se siente Mas que un Vencedor.

Coro:
Más que Vencedores somos en Jesús
Mas que Vencedores somos de la luz;
Nuestro Padre es Rey, Señor de señores
Somos en Su Nombre Más que Vencedores.

II
Somos más que Vencedores en Jesús,
No hay derrota para los Hijos de Dios
El Padre Santo como muestra de su amor
Nos hizo libre para ser un Vencedor.
Su Santo Espíritu nos llena de poder
Para Vencer lo imposible por la Fe,
Ya nada puede separarnos de su amor
Cantando alegre la canción del Vencedor."""
        ),
        Hymn(
            id = 334,
            title = "El día ya viene",
            link = "",
            author = "Trio Acuña",
            content = """I
Cristo es mi refugio ¿de quién temeré?
Si él ha prometido: “contigo Estaré”,
Si él es mi confianza ¿de quién temeré?
Si él va conmigo al cielo llegaré.

Coro:
El día ya viene, ya viene en las nubes
Con ángeles santos su iglesia a llevar;
Valor pues Cristianos ya vamos a llegar
Con Cristo al cielo por siempre a reinar.

II
En mis noches tristes consuelo él me da
Porque él ha prometido: “por siempre Estaré”,
Hermanos y amigos, todos me dejaren
“No temas, contigo por siempre Estaré”.

III
Qué triste es la vida de los que ignoran
De mi Refugio que es Cristo Jesús,
Apúrate hermano, encuentra el refugio,
Encuentra el refugio que es Cristo Jesús."""
        ),
        Hymn(
            id = 335,
            title = "Soy la Resurrección",
            link = "",
            author = "Trio Acuña",
            content = """Presentación:
Había un hombre enfermo llamado Lázaro, natural de Betania, del pueblo de María y de Marta, hermanas de Lázaro; esta María fue la que derramó perfume sobre los pies de Jesús y los secó con sus cabellos. Así pues, las dos hermanas mandaron llamar a Jesús, sin embargo el Maestro todavía debía de visitar la ciudad de Judea. Cuando al fin llegó a Betania se encontró con que Lázaro hacia cuatro días había sido sepultado; Marta recibió a Jesús con estas palabras:

I
“Oh Señor si hubieras estado mi hermano no hubiera muerto
Tanto tiempo esperé, pero tú no llegaste,
Ahora es tarde, el ha partido de este mundo y ya no regresará”
“Oh mujer, tu hermano volverá a vivir, créelo, el resucitará”.

Coro:
“Soy la resurrección, Yo Soy la vida
El que cree en mí aunque estuviere muerto
Volverá a la vida”.
“Y todo aquel que vive y cree en mi
//No morirá eternamente, ¿puedes creerlo?”//

Recitado:
Jesús al ver llorar a María y a los judíos que habían llegado con ella, se estremeció profundamente, se conmovió en el Espíritu; les preguntó: “¿Dónde lo sepultaron?” le dijeron: “ven a verlo Señor”. Jesús lloró. Muy conmovido se acercó a la tumba, era una cueva muy centrada y estaba cubierta con una piedra. Jesús entonces les dijo: “quiten la piedra”. Pero Marta, hermana del muerto dijo:

II
“Si Señor yo en ti he creído, que tu eres, que eres el Cristo
El Fiel Hijo de Dios que ha venido al mundo,
Mas ahora su cuerpo ya hiede, es de cuatro días”.
“¿No te he dicho que si creyeres verás en tu vida la Gloria de Dios?”"""
        ),
        Hymn(
            id = 336,
            title = "Palabra de Dios",
            link = "",
            author = "Trio Acuña",
            content = """I
El hombre que no comprende
El misterio de la piedad
Camina por este mundo
Y no piensa en la eternidad.

Pero hay una voz de alerta
Que llama a la realidad,
Es el Verbo hecho carne,
Es Jesús, es la verdad.

Coro:
No te hagas el distraído
Ya oye Palabra de Dios,
//Los que aceptan van al cielo
Y los que no van a sufrir su error//

II
Hay un camino al infierno
Por toda la eternidad
Para el pecador sin Cristo,
Hermanos de la maldad.

Hay un camino al cielo
Por toda la eternidad
Para el que acepte a Cristo
Con toda sinceridad."""
        ),
        Hymn(
            id = 337,
            title = "Ha Resucitado",
            link = "",
            author = "Trio Acuña",
            content = """I
Muy de mañana vinieron al sepulcro
Y hallaron la piedra removida
Y no hallaron el cuerpo de Jesús
Porque ¡Ha Resucitado!

Coro:
No, no está aquí, sino ha resucitado
//Y a la Diestra del Padre
Intercede por ti y por mi//

II
En una peña pusieron el cuerpo de Jesús
Y todo ha terminado,
Unas mujeres llorando están
Sin consolación.

III
No, no dudemos hermanos
Que Cristo un día él vendrá,
El si vendrá y Reinará
Por siglos de los siglos."""
        ),
        Hymn(
            id = 338,
            title = "Marta y María",
            link = "",
            author = "Trio Acuña",
            content = """I
Yendo de camino, pasando por Betania
Entró en aquella aldea,
Marta lo recibe en su humilde hogar,
Una casa muy sencilla.

Sentándose María a los pies del Señor
Escuchaba sus palabras
Y Marta preocupada por cosas que hacer
Ese gran momento descuidaba.
“Pero solo una cosa es necesaria,
La Buena parte ha escogido María
La cual no le será quitada”.

Coro I:
María quiero ser, sentarme a tus pies,
Oír de Tus Palabras, sentir tu amor.
Postrarme a tus pies, recibir tu bendición
Y la gracia que tu Espíritu me da.

II
Tú tienes la dicha de tener a Jesús
En tu casa en este momento,
Si afanes te turban o preocupaciones
Repite como en aquel tiempo:
“Que solo una cosa es necesaria”
La Buena parte escoge en este día
La cual no te será quitada.

Coro II:
María quieres ser, sentarte a sus pies,
Oír de sus Palabras, sentir su amor.
Postrarte a sus pies, recibir su bendición
Y la gracia que su Espíritu te da."""
        ),
        Hymn(
            id = 339,
            title = "El Borde del Manto",
            link = "",
            author = "Trio Acuña",
            content = """I
“Si tan solo pudiera tocar el borde de su manto”
Decía aquella mujer al sufrir su enfermedad;
A los médicos acudió y nada resolvió,
Todo lo que tenia lo había gastado.

Cierto día el Rey de reyes pasando por allí,
Ella se le acerca y toca su manto.

Coro I:
Virtud salió de él y ella sanó
Y el Señor pregunta allí: “¿Quién me ha tocado?”
Más ella no pudiendo ocultarse
Vino hacia él y allí se postró.

Si tan solo yo pudiera tocar tu manto,
Esa es mi Fe Divino Señor,
Muchas cosas en mi vida hoy cambiarían
Con Tu Gran poder que viene de ti.

II
Y después allí humillada le cuenta a aquel pueblo
Porque le había tocado el borde de su manto
Entonces Jesús le dice: “tu Fe te ha sanado”,
Desde aquella hora la mujer fue salva.

Coro II:
Señor, Señor yo quiero tu manto tocar
Y postrarme ante ti y contar la verdad
Y decirle a este mundo lo que es tu poder;
Que Tú sanas, que Tu Salvas y renuevas las fuerzas,
Que Tú sanas, que Tu Salvas en este día."""
        ),
        Hymn(
            id = 340,
            title = "Oh Jehová",
            link = "",
            author = "Trio Acuña",
            content = """I
Solo en Dios mi alma espera y depende mi salud,
Pues en el encuentro gloria y hasta es mi Salvación,
Y aun me consuela si me encuentro en aflicción
Y yo sé que es mi refugio y en tinieblas no andaré.

Coro:
Oh Jehová, de mañana oirás mi oración,
De mañana me presentaré ante ti
Porque tú eres la razón de mi existir.

Y yo sé que no eres Dios que acepta la maldad
Pues lo malo junto a ti no habitará
Y tus ojo mirarán la inmensidad.

II
Aquí estoy, de rodillas suplicando en mi oración
De esa Unción y de la Gracia que Tu Espíritu nos da
Para que pueda vivir en este mundo de maldad
Y seguir Tu ejemplo Santo que me ayudará hasta el fin.

Final:
“Así eres Tu”."""
        ),
        Hymn(
            id = 341,
            title = "Fieras del Camino",
            link = "",
            author = "Trio Acuña",
            content = """I
Voy caminando por un desierto,
Caminando a la Celeste ciudad,
Es difícil distinguir entre lo falso y lo cierto
Pero en Cristo Victorioso voy contento.

Coro:
Fieras por doquiera se presentan
Con sus garras afiladas, a todo el mundo amedrentan;
Mas no temas amado hermano
Porque en Cristo Venceremos
A esta fiera que en el camino se nos presenta.

II
Voy por este medio advirtiendo
Que una de esas fieras es el descontento,
Hay otra que se llama desaliento
La que se Vence orando,
Ayunando y asistiendo al templo.

III
Una enfermedad que da tormento,
Vicio y lamento en el camino encontraremos
Más Cristo en su amor nos da consuelo,
Paz, bondad y allá en el cielo
Vida eterna por siempre tendremos."""
        ),
        Hymn(
            id = 342,
            title = "Lléname de Tu Poder",
            link = "",
            author = "Trio Acuña",
            content = """I
¿Por qué te sientes triste y abatido
Si has conocido la Presencia del Señor?
//¿Por qué al llegar las pruebas tú te alejas?
Siguiendo a Cristo recibirás consolación//

Coro:
Contemplarás su amor sublime
Y escuchará de su boca nuestra alma
//En horas de dolor y de tristeza,
Señor te pido me llenes de tu poder//

II
Recibirás consuelo en tu alma
Y tu confianza solo en él depositarás
//Y caminando tras las pisadas del Señor,
Tu corazón de bendiciones llenarás//"""
        ),
        Hymn(
            id = 343,
            title = "Apocalipsis",
            link = "",
            author = "Trio Acuña",
            content = """I
Muy pronto Jesús vendrá
A llevar los Suyos para siempre
//A los que aguardaron su Palabra
Y han esperado en su venida//

Coro:
“He aquí Yo vengo muy pronto,
Reten todo lo que tienes
//Para que ninguno la arrebate”
Y para siempre con Él Reinarás//

II
“El que venciere será vestido,
Vestido de Ropas Blancas
//Y su nombre no borraré,
No borraré del Libro de la Vida”//

III
“Yo conozco tu obra y tu trabajo,
Yo conozco tu prueba y tu dolor
//Pero tengo una cosa que decirte:
‘Que has dejado tu Primer Amor”//"""
        ),
        Hymn(
            id = 344,
            title = "El Amor de Dios",
            link = "",
            author = "Trio Acuña",
            content = """I
El amor de Dios podrás oírlo en cada amanecer
Mira las aves que con su trinar alaban a Dios.

El amor de Dios podrás buscarlo en cada mañana
Junto al sol que da su calor y nunca se acaba.

Coro:
Este es el amor de Dios
Que dio a Su Hijo a cambio de nosotros
Y que pagamos enviándolo a la cruz
Y que elegimos a un ladrón en vez de Jesús.

II
El amor de Dios podrás sentirlo
Cuando en tu corazón
Mantengas el Gozo
De vivir para Dios con todo tu ser."""
        ),
        Hymn(
            id = 345,
            title = "La Samaritana",
            link = "",
            author = "Trio Acuña",
            content = """I
Si tú quieres saber de tu vida la verdad
En él solo hallarás eterna salvación.

Jesús cansado del camino allí se puso a descansar
Sediento de tanto caminar, buscando agua de beber,
Una mujer de Samaria se acercó
Con su cántaro vacio al pozo de Jacob,
Jesús le dice: “dame de beber
Que Yo te daré aguas de Vida”.

Coro:
//Si tú probares de este dulce manantial
Aguas de Vida correrán por tu interior
Y su dulce Presencia inundará tu ser
De gozo, amor y paz//

II
Corrió hacia la ciudad
Anunciando las nuevas del Maestro
“He hallado a un Hombre
Y me dijo de mi vida la verdad”."""
        ),
        Hymn(
            id = 346,
            title = "El día de Redención",
            link = "",
            author = "Trio Acuña",
            content = """I
Cristo en las nubes volverá
Y a todo su pueblo llevará,
Hacia el cielo nos levantará
Junto a nuestro Padre Celestial.

Coro:
//Hermanos todos oremos juntos,
No desmayemos, siempre adelante
Y esperando aquel Gran día de Redención//

II
Ángeles a coro cantarán:
“Bendito Jesús el Salvador”
Él murió por cada pecador
Para que tengas la Salvación.

III
En el cielo no habrá dolor
Because todo será bendición
Y la luz de Dios alumbrará
Para siempre a nuestro corazón."""
        ),
        Hymn(
            id = 347,
            title = "Bueno es Alabarte Señor",
            link = "",
            author = "Trio Acuña",
            content = """I
Bueno es alabarte Señor
Y cantar Salmos a Tu Nombre
Anunciar por las mañanas
Tu misericordia infinita.

Coro:
Porque Tú habitas en la alabanza,
Porque tú recibes este canto,
Porque sé que levantas al cansado que viene a ti,
Porque sé que llenas mi alma.

II
Pablo y Silas encerrados en prisión,
Azotados y castigados por Tu Nombre,
Ellos llenos de ti
Elevaban alabanzas a los cielos.

III
En mi mente quedó una huella
De aquellos que dieron sus vidas,
No les importaba morir de cualquier manera
Con gran gozo esperaban la Corona.

Final:
“Bueno es alabarte Señor”."""
        ),
        Hymn(
            id = 348,
            title = "Salmo ciento veintiuno",
            link = "",
            author = "Trio Acuña",
            content = """I
Alzaré mis ojos a los montes
¿De dónde vendrá mi socorro?
Mi socorro viene de Jehová
Que hizo los cielos y la tierra.

Coro:
Jehová es tu guardador,
Jehová es tu mano derecha,
El sol no te fatigará
Ni de día ni de noche.

II
Quizás te sientas solo
Pero él es tu mano derecha,
No pierdas la mirada en Dios
Que hizo los cielos y la tierra.

III
Jehová te guardará de todo mal
Él guardará tu alma,
Él es tu salida y tu entrada
Desde ahora y para siempre."""
        ),
        Hymn(
            id = 349,
            title = "Un Hombre Galileo",
            link = "",
            author = "Trio Acuña",
            content = """I
Hace dos mil años un hombre galileo
Vino trayendo Salvación para este mundo,
//Sanaba, limpiaba, curaba las heridas
Del pobre pecador//

II
Hoy el vino a tu vida trayendo salvación
No importa como estés, ven a él,
//Pondrá él en tu vida un nuevo corazón
Lleno de puro amor//

III
Él llama a la puerta del corazón
Espera con paciencia y oirás su voz
//Si abres hoy tu puerta renovará las fuerzas,
Hoy cenarás con él//

IV
Así como un día él vino, así él vendrá
Con poder y Gloria su iglesia a llevar;
//A la eterna morada que él fue a preparar
Yo iré pronto a morar//"""
        ),
        Hymn(
            id = 350,
            title = "Has Sentido",
            link = "",
            author = "Trio Acuña",
            content = """I
Hermano dime por favor si has escuchado al viento,
Al viento de Pentecostés soplando allá en el huerto,
Dime si acaso en tu interior se desplazan los ríos,
Los ríos de Agua Viva.

Coro:
Si has sentido su mano en dolor y aflicción
Cuando a solas llorabas,
Y su paz y su amor tú alma consolaban;
Déjame que te diga que no fue un sentimiento,
Él, en Verdad estaba.

II
Hermano vuelve a frecuentar el camino del huerto
Y el monte de la oración donde está tu alegría,
Ven al bienestar si no tienes de Cristo,
Tu vida en abundancia."""
        ),
        Hymn(
            id = 351,
            title = "Te doy las Gracias",
            link = "",
            author = "Trio Acuña",
            content = """I
En este día hoy te recuerdo
Tu Gran amor que diste por mí
Y que al dejar tu lugar de morada
Me rescataste de este mundo cruel.

Coro:
Por esto Jesús te doy las gracias
Por tu gran amor que diste por mí
Y te entregaste en mi lugar,
Me diste paz, me diste amor.

II
Tú que me escuchas en este día
Mi Buen Jesús que a mí me Salvó,
Si le recibes él te salvará,
Te libertará y te sanará.

III
Pues no desprecies hoy de su luz,
Te está llamando con tierna pasión;
Venid y acéptalo como tu salvador
Y vida eterna de él tendrás."""
        ),
        Hymn(
            id = 352,
            title = "El Profeta Eliseo y la viuda de Serepta",
            link = "",
            author = "Trio Acuña",
            content = """I
Una mujer de las de los hijos de los profetas
Al profeta Eliseo un día dijo así:
“Tu siervo mi marido era un hombre temeroso
Pero ha muerto y ha dejado cuenta y tengo que pagar;
Han llegado los cobradores y quieren llevar mis hijos
Sé que eres un profeta y me tienes que ayudar”.

Coro I:
“¿Qué tienes en tu casa?” el varón preguntó
“Solo un poquito de aceite para mis hijos y yo”.
“Consigue muchas vasijas y comienza a cargar,
Luego paga tus cuentas y el aceite pon en venta
Y con el resto vivirás”.

II
Cierto día a Serepta el profeta Elías llegó,
Hambriento y cansado a la puerta se acercó,
Una mujer que recogía leña para fuego hacer
Y le dijo “dame agua y un pedazo de pan”.
“Vive Jehová tu Dios que no tengo pan cocido,
Lo que tengo es muy poco y yo no te puedo dar”.

Coro II:
“Has para mi primero” Elías contestó
“Después para tu hijo, eso te dice Dios;
No temas, ten confianza, nada te faltará
//Porque el Dios que yo sirvo
El aceite en la vasija nunca dejará faltar”//"""
        ),
        Hymn(
            id = 353,
            title = "Casa de Dios",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
Sobre una piedra junto al camino Jacob cansado se ha dormido
Pero del cielo una escalera junto a él ha descendido,
Ángeles suben, ángeles bajan desde la Gloria del Dios Divino
Que de lo alto de esa escalera dice a Jacob: “Yo estoy contigo”.

Coro I:
//Casa de Dios, puerta del cielo
“No es otra cosa” dijo el patriarca
Y tuvo miedo//

II
Aquella piedra que el patriarca puso debajo de su cabeza
Es Jesucristo la Piedra Viva, el fundamento de la iglesia,
Él da descanso y nuevas fuerzas, él da consuelo y fortaleza,
Por eso hermano ten confianza, pon sobre él hoy tu tristeza.

Recitado:
Y despertó Jacob de su sueño y dijo: “ciertamente Jehová esta en este lugar y yo no lo sabía” y tuvo miedo y dijo: “cuan terrible es este lugar, no es otra cosa que Casa de Dios y puerta del cielo”.

III
Yo no sabía, no imaginaba que en este sitio mi Dios estaba
Pero hoy comprendo cómo Jacob que la iglesia es Casa de Dios;
Ella es la puerta, puerta del cielo, es la escalera para mis sueños,
Por ella me habla Jesús mi dueño, me da su amor, paz y consuelo.

Coro II:
Casa de Dios, puerta del cielo,
Hoy es la iglesia porque por ella
Vamos al cielo."""
        ),
        Hymn(
            id = 354,
            title = "Cada Vez",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
Cada vez que estoy afligido
Me acuerdo de Cristo mi Jesús
Y hay veces que me encuentro muy contento,
Me olvido de ti mi Señor.
Ayúdame a serte siempre Fiel,
En la tristeza y en la felicidad,
Perdóname Señor con tu amor,
Que Siempre me acuerde Dios de Ti.

II
Jesús este mundo está perdido,
Te tienen colgado en una cruz
Y en dolor no quieren ver la muerte,
Se olvidan que hay Dios de Poder.
Perdónalos Señor con tu amor,
Hipocresía en ellos solo hay;
Yo me encontraba como ellos mi Señor
Hasta que regresé a Ti.

III
Al mirar al cielo me doy cuenta
Que Siempre te acuerdes Dios de mí
¿Qué será si tus ojos se apartaran
De mi vida, Señor que será?

//Y mientras yo a veces soy infiel
En la alegría la espalda Dios te doy,
Hipocresía no quiero tener,
Que Siempre me acuerde Dios de Ti//"""
        ),
        Hymn(
            id = 355,
            title = "El Amor del Señor",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
Llega con tu amor oh mi Señor
Y derrámalo en mi corazón
//Para que en el resto del camino
Que me queda en esta vida
Te siga con amor//

II
Si yo no camino con amor,
Lo que haga en vano es Señor
//Si tú en la cruz fuiste clavado
Y por mi todo lo has dado
Pues lo hiciste por amor//

III
Y si mi hermano me ha ofendido mi Señor
Que lo pueda perdonar por tu amor
//Y si a mi hermano he perdonado
Y yo sé que lo he ganado
Y tendré Tu Bendición//

IV
Con grandes expresiones oh mi Dios
El apóstol habla de tu dulce amor
//Y hoy vemos que es cierto
Porque en tu misericordia
Nos diste de ese amor//

V
Estando en el camino del error,
No miraste nuestra pobre condición
//Y hacia nosotros te acercaste
Y así nos abrasaste
Y nos diste tu perdón//"""
        ),
        Hymn(
            id = 356,
            title = "Oh que Amor tan Grande",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
Oh que amor tan grande
Mostró Dios al mundo
Que envió a Jesucristo por su Salvación;
Para todo aquel
Que cree en Su Nombre
Tenga vida eterna allí juntó con Él.

Coro:
Oh que amor tan grande
Mostró el Dios eterno
Que ha dado a Su Hijo
A morir in la cruz.
Es tan grande y fuerte
Que aun ni la misma muerte
Pudo detener a este Gran Amor.

II
Y hoy yo pertenezco
A este amor Divino
Que tuvo de mí
Un día compasión.
Borró mis pecados,
Mis iniquidades
Y todo lo hizo por Su Gran Amor."""
        ),
        Hymn(
            id = 357,
            title = "Tu Sacrificio",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
Oh Jesús amado mio
Hoy elevo a ti mi voz
Because soy agradecido
De tan grande bendición.
En los cielos tú dejaste
Tu Gran Gloria por venir
A sufrir en esta tierra
Para darme Salvación.

Coro:
Oh Jesús tu sacrificio
En Tus Palabras puedo ver
//Y yo sin haberte visto
Hoy de ti me enamore//

II
Las Sagradas Escrituras
Nos revelan de tu amor
Y cuan precio te costamos
Oh Jesús mi Salvador.
En la cruz fuiste inmolado
Por Salvar al pecador,
Y Tu Sangre por limpiarnos
Derramaste mi Señor."""
        ),
        Hymn(
            id = 358,
            title = "Qué gran coincidencia",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
Una tarde como otras tantas una madre caminando va
Afligida y llena de tristeza rumbo al templo por su hijo a orar,
Hace tiempo que no está con ella, hace tiempo se fue del hogar
Es por eso que hoy llora su ausencia pero a Dios por el pide clemencia
Pues le ama y con él quiere estar.

Coro:
Pero miren que gran coincidencia si aquel hijo ingrato yo fui
Que dejando a mí madre en la iglesia hacia el mundo me fui a sufrir;
Y hoy por ese clamor y esas lágrimas al hogar nuevamente volví,
Hoy por eso me siento dichoso, hoy por eso me siento feliz.

II
Muchas veces como Ana en el templo, su alma entera ante Dios derramó,
En silencio, quebrada en llanto por mí vida a Jesús le clamó;
Solo él comprendió su amargura, solo él comprendió su dolor
Y ese hijo perdido y errante, él lo trajo a sus brazos amantes
A su seno de Gracia y amor.

III
Thanks madre, querida iglesia por tus ruegos, paciencia y amor
No te sientas ya sola ni triste, tu clamor lo escucha Jesús,
Memoriza aquellas palabras cuando él iba llevando la cruz:

Recitado:
“Hijas de Jerusalén, no lloréis por Mi, sino llorad por vosotras mismas y por vuestros hijos”."""
        ),
        Hymn(
            id = 359,
            title = "La Resurrección",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
Era el primer día en la semana
Cuando unas mujeres muy temprano
Fueron al sepulcro del Señor
Que tres días antes fue inmolado,
Azotado y maltratado el Salvador.

II
Que sorpresa grande para ellas
Al ver la piedra removida
Y no estaba el cuerpo del Señor;
Cuando se aparecen dos varones
Para darles la noticia angelical:

Coro I:
No está aquí, ha resucitado,
Como habló en Galilea,
El Hijo del Hombre
Ha de ser crucificado.

Recitado:
Hubo un gran terremoto porque el ángel del Señor descendió del cielo, removió la piedra y se sentó sobre ella. Su aspecto era como un relámpago y su vestido blanco como la nieve.
Todos tuvieron miedo, mas el ángel del Señor dijo a las mujeres: “no temáis vosotras ¿buscáis a Jesús? no está aquí ¡Ha Resucitado!”

Coro II:
Como lo anunció, esto se cumplió,
Se levantó entre los muertos
Y a la diestra del Padre
Por mi esta clamando."""
        ),
        Hymn(
            id = 360,
            title = "El fin ha Llegado",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
Qué triste destino del mundo perdido,\nVagando sin rumbo en la oscuridad,\nDe densas tinieblas están rodeados\nLlevando el pecado, su única verdad.\n\nCoro:\nYa todo termina, el fin ha llegado\nY el mundo en pecado se condenó,\nMiserias y llantos es lo que le esperan\nPor causa del pecado que en ellos está.\n\nII\nQué triste de aquellos que hoy rechazaron\nLa Gracia Bendita del Hijo de Dios,\nEternos tormentos lo están esperando,\nSu llanto amargo nunca tendrá fin.\n\nIII\nDichosa es el alma que a Cristo recibe\nPues nunca la muerte vendrá sobre él,\nPues él ha pasado de muerte a vida,\nCorona de vida el recibirá."""
        ),
        Hymn(
            id = 361,
            title = "Lo hizo por Amor",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
Rumbo a la calavera
Va sin especulación,
Va a cumplir la condena
Que el pueblo le aplicó,
//Sin resistencia lo hace,
Todo hizo por amor//

Coro:
Hombres, verdugos con palos,
Espadas y antorchas
Fueron de noche a buscar al Señor siendo que
Todos los días estaba en el templo enseñando,
Mas ahora lo llevan así.
//Sin resistencia lo hace,
Todo hizo por amor//

II
Vistieron de escarlata
Corona insertan en su sien,
Odios, insultos soporta
Mientras camina el Señor.
//Sin resistencia lo hace,
Todo hizo por amor//"""
        ),
        Hymn(
            id = 362,
            title = "Viene Ya",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
Diez vírgenes esperaban
El regreso del Esposo que vendrá,
Cinco de ellas eran prudentes
Que esperaban sin vacilar.
Otras cinco, eran insensatas,
Desprevenidas a la hora en que llegó
Y se escuchaba el clamor:
“El Esposo viene ya”.

Coro I:
//Viene ya, viene ya,
El Esposo viene ya//

II
Aguarda que en el momento
No esperado se escuchará
De la trompeta con voz de mando
Que Jesucristo viene ya.
Alístate en las filas
De los que temen al Salvador,
Procura con más diligencia
Estar más cerca que Él viene ya.

Coro II:
//Viene ya, viene ya,
Jesucristo viene ya//

Recitado:
Dijo el Señor: “por tanto también vosotros estad preparados porque el Hijo del Hombre vendrá a la hora que no pensáis”."""
        ),
        Hymn(
            id = 363,
            title = "En las Moradas",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
Oh Jesús en este día
A ti te entrego mi corazón,
Yo quiero que sea tuyo
Para siempre poder gozar
De las moradas que tú te fuiste a preparar.

Coro:
Hoy yo con los santos quiero morar,
Hoy con Jesucristo quiero vivir
En las moradas que tú te fuiste
A preparar.

Recitado:
Cuan amables son Tus Moradas oh Jehová de los ejércitos, anhela mi alma y aun ardientemente desea los atrios del Señor; mi corazón y mi voz cantan al Dios Vivo y Verdadero.

II
Mi Jesús en el calvario
Dio su vida por nuestras almas,
Hoy hermano, ama a la iglesia
Como Cristo nos enseñó;
Él viene pronto para llevarte
A las moradas que prometió."""
        ),
        Hymn(
            id = 364,
            title = "Pescador de Hombres",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
//Va caminando//
Sobre la arena se ve,
//Son las pisadas del nazareno
Que junto al mar se lo ve//

Coro:
“Pedro, tira la red
//¿No ves el pueblo
Que ya espera?
Pescador de hombres te haré”//

II
//Allá en el muelle//
Juntas dos barcas están,
//Lavan sus redes,
Van muy deprisa
Porque el Maestro vendrá//

Recitado:
Para esto fuisteis llamados, porque también Cristo padeció por nosotros, dejándonos ejemplo para que sigáis sus pisadas.

III
Oh Cristo Amado, en Tus Palabras
Yo confiado estoy
//Y he prometido
Que Tus pisadas
Siempre las he de seguir//"""
        ),
        Hymn(
            id = 365,
            title = "Inmensa Bondad",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
//Es mi cantar Señor a tu Divina Presencia
Con toda reverencia
Por tu inmensa bondad//
Si mi buen Dios por tu bondad.

II
//Te alaben oh Gran Dios
Todas tus obras, tu pueblo,
La Gloria de Tu Reino
Por tu inmensa bondad//
Si mi buen Dios por tu bondad

Recitado:
Por tu inmensa bondad, por tus muchas misericordias, hoy canto para ti Señor.

III
Cercano esta el Señor a todos los que le invocan,
A todos los que lo invocan de veras corazón;
//Justo es el Señor en todos sus caminos,
Cumplirá el deseo de los que le aman a él//
Si mi buen Dios por tu inmensa bondad."""
        ),
        Hymn(
            id = 366,
            title = "Fuente de Vida",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
Aunque perdiera mi vida por servir a Dios
Sé que he ganado ante sus ojos de amor
//Porque ha llegado cambiando el corazón
Que se encontraba triste y lleno de dolor//

Coro:
Fuente de vida Cristo es para mí,
Saciar mi alma quiero de ti,
Amarte con el alma, es entregarse
Al servicio y a su voluntad.

II
Me ha demostrado cuan profundo es su amor
Que en una cruz por mis pecados se entregó,
//Que dicha inmensa la que siento en mí ser,
Cambiar las vidas puedes con tu gran poder//"""
        ),
        Hymn(
            id = 367,
            title = "El Pan de Vida",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
La gente busca a Jesús, a Jesús,
La gente quiere comer el pan otra vez
//Que allá en el desierto él multiplicó
Y a cinco mil hombres él alimentó//

II
La gente busca a Jesús, a Jesús
Pero no saben que el pan de vida es él
//Que vino del cielo enviado por Dios
Para darle vida al mundo pecador//

Coro:
Cristo es el pan Celestial, Celestial
Y el que come de él vida eterna tendrá;
//Ni hambre ni sed, no tendrá jamás
Porque el vivo pan su alma saciará//

III
Señor hoy danos tu pan, tu pan
Para poder caminar y trabajar
//Llevando a las almas que hambrientas están
Tu Santa palabra de Vida y verdad//

Final:
///Señor danos siempre éste pan///"""
        ),
        Hymn(
            id = 368,
            title = "Enseñanza del Maestro",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
Enseñando Cristo dijo:
“De la higuera aprended”
//Comparando su renuevo,
Es porque el verano está cerca//

Muchas señales hay ya,
Todo cumpliéndose está
Cristo Jesús lo anunció,
Afirma bien tu corazón.

Sed pues prudente y Fiel
A lo que él te confió;
//No retrocedas jamás,
Cristo recompensará//

II
Cielo y tierra pasarán
Sus palabras cumplirán,
//Pero el día nadie sabe
Ni los antes solo Dios//

Como en los días de Noé,
Fiestas nupciales serán,
No entendieron a él
Y el diluvio los arrasó.

Cuando regrese el Señor
Sin advertencia será
//Se Fiel a quien te tomó,
Bienaventurado serás//"""
        ),
        Hymn(
            id = 369,
            title = "La Fe del centurión",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
“Muy de lejos he llegado Señor
Buscando tu inmenso poder
//Porque allí en mi casa ha quedado
Un criado a quien quiero a punto de morir”//

“Yo iré y lo sanaré” se oye decir al Señor
//Pero aquel centurión le responde:
“No soy digno que entres en mi techo Señor”//

“Yo soy hombre de autoridad”
Contesta aquel centurión
“Y tengo soldado a mi cargo,
A este le digo que vaya y se va;
Y a este otro le digo que venga
Y así obediente a mi voz siempre está”.

II
“Tu siervo también oh Señor
Obediente a mi voz siempre está
//Pero tú solo di la palabra,
Yo sé que mi criado sanado será”//

“Os digo que ni en Israel
He hallado tantísima Fe;
//Y así como tú has creído
Ve, porque tu criado ya sano es”//

Danos también oh Señor
Esta Fe como el centurión
//Porque sin Fe es imposible
Poder agradarte oh Divino Señor//"""
        ),
        Hymn(
            id = 370,
            title = "Ven a mi Casa",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
De camino iba Jesús y a una aldea el entró
Allá en Betania
Y al pasar por el lugar a su casa le invitó
Una mujer llamada Marta.

El Señor no rehusó y contento aceptó
La invitación de acompañarla,
Pero ella descuido esa grande bendición,
En otras cosas preocupada.

Coro:
Ven a mi casa, yo quiero estar Contigo
Y escuchar de tu palabra,
Ven a mi casa, te entrego a ti mi vida
Cual María se humillaba;
Ven a mi casa, ven a mi casa,
Ven a mi corazón Señor y hazlo tu morada.

II
Jesús pasa por aquí y en la Iglesia él entró
Como en Betania
Hoy le puedes invitar y llevarlo hasta tu hogar
Dentro de tu ser, dentro de tu alma.

Pero tienes que dejar el afán y la ansiedad
Que hay en tu corazón y mente
Y entregarle todo a él,
Y en espíritu y verdad adorarle, para Siempre.

Final:
Ven a mi casa, ven a mi casa hoy,
Ven a mi corazón Señor y hazlo tu morada."""
        ),
        Hymn(
            id = 371,
            title = "Oración de Jesús",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
“Pasa esta copa de mí
Que no sea como yo quiera,
Pasa esta copa Señor,
Que se haga tu voluntad”.

Dolores, quebrantos tenía que pasar,
Y así intensamente oraba el Hijo de Dios.

Coro:
“Pasa este cáliz de amargura”
Era el clamor del Salvador
“Porque la hora se aproxima,
Y el Cordero inmolado será”.

II
Dios el Padre escuchó la oración
Que Su Hijo elevó con dolor
Y un ángel del cielo envió
Que le diera confortación.

Dolores, quebrantos tenía que pasar
Y así intensamente oraba el Hijo de Dios."""
        ),
        Hymn(
            id = 372,
            title = "Camino al Calvario",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
Camino al calvario, camina el Señor
Llevando el pecado del mundo traidor;
Su hermosa cabeza espina le hirió
Sus labios fragantes suplican perdón
Rogándole al Padre por el pecador.

Coro:
Con Precio de Sangre él te rescató
Tu vida entrégale, él te rescató.
//No sigas esperando ven pronto a él,
Que triunfo te espera si vas a Emmanuel//

II
Sus manos preciosas el clavo la hirió,
Su cuerpo tan Santo castigo sufrió,
Sus pies horadados dolores sintió
//Pagando mis culpas, él lo soportó//

III
Camino al calvario se fue mi Señor
Con gran sufrimiento, por ti y por mí;
Con grito de Triunfo él ya expiró
//En el cielo escucha, victoria nos dio//"""
        ),
        Hymn(
            id = 373,
            title = "Inmolado",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
//Ciertamente llevó nuestras enfermedades,
Sufrió nuestros dolores Cristo el Redentor//
Se asombraron de él cuando desfiguraron
Su parecer y hermosura, fue con tanta crueldad.

Coro:
//Inmolado fue el Cordero que Redimió
Con su Sangre linaje que alabe a Dios.
Es aquel león de la tribu de Judá,
La raíz de David, dueño de la potestad//

II
Por sentencia jurídica fue su muerte en la cruz,
Era imprescindible para nuestra Redención;
Fue el gran sacrificio aceptado por Dios,
Se sentó a la diestra del trono de Dios.

Recitado:
Ciertamente llevó nuestras enfermedades, sufrió nuestros dolores, Cristo el Redentor. Se asombraron de él cuando desfiguraron su parecer y hermosura, fue con tanta crueltad."""
        ),
        Hymn(
            id = 374,
            title = "Una Iglesia comprada con Sangre",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
Una iglesia comprada con Sangre
Es la que Cristo vino a salvar,
Una iglesia que no tenga arrugas
Y ni una mancha se pueda encontrar.

Coro:
Jerusalén se llama
Aquella Hermosa y Santa Ciudad;
Y tú, iglesia de Cristo
Por sus calles de oro andarás.

II
Un soldado quedó frente a él
Cuando Cristo en la cruz expiró
Para que se cumpla la Escritura,
Con su lanza a Jesús traspasó.

III
Y de esa herida es que nace
Esta iglesia que él quiere llevar,
Para ser allí presentada
Como esposa del Rey Celestial."""
        ),
        Hymn(
            id = 375,
            title = "Busca el Reino de los Cielos",
            link = "",
            author = "Cantores Unidos del Noa",
            content = """I
Muy pronto viene en las nubes
Cristo de los cielos a buscar
Al que aquí se ha preparado
Y con él al cielo se irá.

No pierdas aquí el tiempo
Porque aquí todo es vanidad,
Los placeres de esta vida
Y sus glorias pasarán.

Coro:
Busca el Reino de los cielos
Y Feliz por siempre serás,
Porque lo que Dios promete
Es por la eternidad.

II
El que busque aquí su vida
Seguro que la perderá,
Más aquel que la perdiere
Por Cristo la encontrará."""
        ),
        Hymn(
            id = 376,
            title = "Heme Aquí",
            link = "",
            author = "Conjunto Jerusalén",
            content = """I
Mero, vil me escogiste siendo yo un pecador
Aunque entre pueblo inmundo he habitado Señor.
Tú escogiste mi vida, me salvaste Señor
Y a tu dulce llamado hoy contesto Señor.

Coro:
//Heme aquí, envíame a mí,
Donde pueda ser útil
Yo te quiero servir//

II
“¿Quién irá por Nosotros?” Señor te oigo decir
“¿Quién irá por nosotros donde hay enfermedad?
¿Quién irá por Nosotros donde hay aflicción?
¿Por las almas perdidas, quien irá, quien irá?”"""
        ),
        Hymn(
            id = 377,
            title = "Ven Señor Jesús",
            link = "",
            author = "Conjunto Jerusalén",
            content = """Jesús vendrá otra vez
Como él lo prometió
Su pueblo llevará al cielo con poder;
Por eso es que el bajó
Del Reino Celestial
Pues él quiere que allí
Vivamos junto a él.

//Es hora de volver el rostro hacia Jesús
Y buscar de su luz para alcanzar perdón;
Poniendo nuestra Fe logramos salvación
Viviéremos así, oh ven Señor Jesús.

//Ven Señor Jesús y habita aquí en mi ser,
Si, ven hoy a mi vida oh Salvador;
Ven y acércanos a aquel lugar de luz
Donde no habrá mas noche ni más dolor//"""
        ),
        Hymn(
            id = 378,
            title = "Hace ya tiempo",
            link = "",
            author = "Conjunto Jerusalén",
            content = """I
Estoy sintiendo que mis fuerzas ya se acaban,
Siento un vacio que no lo puedo llenar,
Que mi alma esta sedienta y necesita
Llenarse de Su Gloria una vez más.

Hace ya tiempo que no siento Su Presencia,
Estoy pensando que ya se alejó de mí
Es que he dejado que me lleve la tormenta
Que el enemigo hoy pesa sobre mí.

Coro:
//Pero yo se que Cristo es Justo y Verdadero
Y con su mano de poder me sostendrá,
No dejará que yo me quede en el camino
Y nuevas fuerzas Jesucristo me dará//

II
Creía yo que Dios me había abandonado,
Creía yo que no servía mi clamor
Pero el Señor esta a mi lado y hoy me dice:
“No temas hijo, que a tu lado siempre estoy”."""
        ),
        Hymn(
            id = 379,
            title = "En Todo está Jesús",
            link = "",
            author = "Conjunto Jerusalén",
            content = """I
Cuando despierto en la mañana esta Jesús,
Cuando yo doblo mis rodillas esta Jesús
En mis quehaceres cotidianos esta Jesús
El es mi Amigo Soberano, el me da luz.

En el colegio, en el trabajo esta Jesús,
Si tú le sirves al Señor de corazón
Y verás que bien se vive cuando a Jesús lo sigues
Te guiara, te guardara, te bendecirá.

El tiene potestad, te librara del mal
No busques más afán en cosas terrenal
Si somos hijos Fiel a nuestro Padre Dios
Una morada en el cielo él nos dará.

II
En la sonrisa de mi hermano esta Jesús,
En el amor de mi Anciano esta Jesús
Cuando no soy un buen Cristiano esta Jesús
El sabe todo, el lo ve todo en prontitud.

En este día tengo tiempo de cambiar
Si yo a Jesús le entrego hoy todo mi ser
Y verás que bien se vive cuando a Jesús lo sigues
Nos guiará, nos guardará, nos bendecirá.

Todo cumplido está, Jesús regresará
Si yo soy Fiel a él pronto me llevará,
En este día Dios yo te entrego mi ser
//Oh ven Señor, tu pueblo espera con fervor//"""
        ),
        Hymn(
            id = 380,
            title = "Mi Cristo ya vendrá",
            link = "",
            author = "Conjunto Jerusalén",
            content = """I
Esperando yo estoy con grande devoción la vuelta de mi Cristo
Con gran consagración, ayuno y oración para irme con él,
Pronto regresara, cumplido todo esta y ya no queda tiempo
Yo quiero irme con él para estar en la Gloria con Cristo mi Señor.

Coro:
Mi Cristo ya vendrá
Su iglesia a buscar,
Muy pronto llegara ese día final.

Prepárate mi hermano
Porque él te llevará,
Prepárate mi hermano
Because si estas durmiendo aquí te quedarás.

II
El Señor ha prometido que con su pueblo unido el siempre Reinara
Pero mi Cristo quiere un pueblo redimido con toda Santidad;
Pronto regresara, cumplido todo esta y ya no queda tiempo
Yo quiero irme con él para estar en la Gloria con Cristo mi Señor."""
        ),
        Hymn(
            id = 381,
            title = "Si no fuera por Ti",
            link = "",
            author = "Conjunto Jerusalén",
            content = """I
Si no fuera por ti, Cristo mi Salvador
¿Qué seria yo en la vida?
Si no fuera por ti esta vida feliz seria desconocida;
Si no fuera por ti no sería lo que soy,
No estaría yo en la iglesia
Si no fuera por ti no sería lo que soy, no sería feliz.

II
Si no fuera por ti Cristo mi Salvador,
Hoy seria de este mundo
Si no fuera por ti yo sería uno más que se iría al infierno;
Si no fuera por ti no tendría razón de vivir o morir
Si no fuera por ti no sería lo que soy, no sería feliz.

III
Si no fuera por ti Cristo mi Salvador
No conocería el gozo
Si no fuera por ti oh Divino Señor no tendría reposo
Si no fuera por ti no podría seguir alabando Tu Nombre
//Si no fuera por ti no sería lo que soy, no sería feliz//"""
        ),
        Hymn(
            id = 382,
            title = "Que Gozo sin igual",
            link = "",
            author = "Conjunto Jerusalén",
            content = """I
El tiempo pasa y Jesucristo volverá,
Vendrá en las nubes como él lo prometió
Dentro de poco una trompeta sonara
Y en el espacio Su Presencia se verá.

A los Salvados con su voz el llamará
Y cada uno con sus ojos le verá,
Nos reuniremos junto a él
Y con Su Gloria y esplendor
A los Salvados con su amor recibirá.

Coro:
Que gozo sin igual poder participar
Of aquel encuentro con el Rey de reyes
//Aquel que nos amó y hasta su vida dio
Por darnos vida eterna y Salvación//

II
Con el fulgor de Su Presencia alumbrará
Y toda lagrima el Señor enjugará,
Cada creyente con gran gozo encontrará
Los que partieron de este mundo de dolor.

Más todo aquel que ha persistido in la maldad
No tendrá parte con Jesús en Su Mansión
Pues la condena del Gran Juez
Recibirá por no aceptar el sacrificio de Jesús en una cruz."""
        ),
        Hymn(
            id = 383,
            title = "El Valor de un Alma",
            link = "",
            author = "Conjunto Jerusalén",
            content = """I
Hermano ¿tú sabes el Valor que tiene un alma?
Jamás los recursos humanos podrían pagar;
El oro, el dinero y la plata del mundo entero
No alcanzaría el Valor de un alma poder comparar.

Coro:
Hermano el Valor de un alma costo un precio caro,
Es necesario sentir por ella inmenso amor.
Cuidando, enseñando, orando y a veces llorando,
Debemos buscar todas las almas que Jesús compró.

II
Hermano ¿Cuántas veces tienes almas a tu lado llorando?
Sufriendo con cargas pesadas y llenas de dolor;
Si no las atiendes y perece, tú eres el culpable,
Ten cuidado porque un alma es de mucho Valor.

III
Hermano recuerda el “id” del Maestro Amado,
No fue un pedido, fue una orden que nos dejó.
¿Cómo podemos quedarnos de brazos cruzados
Si él de brazos abiertos por nosotros murió?"""
        ),
        Hymn(
            id = 384,
            title = "Lléname Señor",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Mi vaso hoy quiere rebosar
De gozo, de amor y dulce paz
Inundado ser, por ese rio Santo
Que hoy siento correr por todo mi interior.
Si tengo algo en mi corazón
Que impide sentir la comunión
Con el gran poder de Tu Preciosa Sangre,
Hoy límpiame Señor, Señor hoy límpiame.

Coro:
Y lléname, si lléname,
Hoy lléname con el calor de Tu Presencia;
Y Cólmame, si cólmame,
Hoy cólmame de tu eterna bendición.
Y lléname, y cólmame
De Tu Presencia y bendición.

II
Tus manos buen alfarero
Bien pueden mi vida trabajar
Y hacer de mí una vasija nueva
Que tú puedas usar según tu voluntad.

Final:
De Ti Bendito Salvador."""
        ),
        Hymn(
            id = 385,
            title = "Tu Sabes que te Amo Señor",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Herido fue el Pastor, muerto y sepultado
Pero ha resucitado
Y aquellas ovejitas que con amor cuidaba
Estaban dispersadas.
Pero una madrugada las encontró muy tristes,
Hambrientas y cansadas
Allí cerca en la playa alzando su voz tierna
“Hijitos” les llamaba.

Coro:
Oh que Feliz momento aquel,
Encontrar al Señor otra vez,
Amanecer con Él frente al mar
Y compartir los peces y el pan.
Preguntas y respuestas de Amor,
Consejos que llenan de emoción,
“¿Me amas más que estos Simon?”
///“Tu sabes que te amo Señor///
También lo sabes todo Señor”;
“Apacienta el rebaño de Dios”.

II
Su manada pequeña tenía que dejarla,
Al cielo escalaba
Dejando la Promesa que el Espíritu Santo
Vendría sobre ellos.
Alzado en una nube, Glorioso ascendía
Frente a sus miradas;
“Varones galileos, así como le vieron
Regresará de nuevo”.

Recitado:
“Simon, hijo de Jonás ¿me amas más que estos?”"""
        ),
        Hymn(
            id = 386,
            title = "Fuente de Vida",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Jesús es la fuente de Vida,
Jesús es la fuente de amor
Y todo cansado y sediento
En él puede saciar su sed.
No debes pagar ningún precio
Si quieres de ella probar,
Su puerta abierta espera,
Amigo no pierdas el tiempo
Acércate a Él sin tardar.

Coro:
Él es la fuente de vida y amor,
Es manantial de agua Viva y poder
Al alma triste consuela y renueva
Si de Su agua acepta beber.
En este mundo no busques amigo
En los placeres no lo encontrarás
Y si de ella hoy mismo tomares,
Serás saciado y descanso tendrás.

Recitado:
Porque le era necesario a Jesús pasar por Samaria, allí junto al pozo de
Jacob tenía que ofrecerle el Agua de Vida a aquella mujer que adoraba lo
que no sabía. Hoy te damos Gracias Señor porque aquellas Palabras
llegaron hasta lo más profundo de nuestro corazón, porque por Tus
Palabras somos Bienaventurados, porque aun, sin haberte visto hemos
creído en Ti, fuente de Vida y Amor.

II
Él mismo dejó Su enseñanza,
Sus bellas palabras que habló
Allí al pasar por Samaria
Con una mujer platicó.
Habló de esta agua de vida
Que Él tiene para el pecador
Que vive agobiado en los vicios
Y busca tener paz y calma,
Jesús es la fuente de amor."""
        ),
        Hymn(
            id = 387,
            title = "Señor dame de Beber",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
De Samaria una mujer
Vino al pozo de Jacob,
Preocupada por sacar
Agua y su sed calmar.
//Pero fue la última vez
Porque el mediodía aquel
El Señor Jesús le dio
Agua Viva de beber//

Coro:
Señor dame de beber
De Tu dulce manantial,
Hoy saciado quiero ser
Con Tu agua Celestial.
//Hazme de nuevo sentir
Dentro de mi corazón
La experiencia sin igual
De Aquel Primer Amor//

Recitado:
¡Gracias Jesús! Gracias por esta agua Viva que tú tienes para con
nuestras vidas.

II
Ella el cántaro dejó
Y corrió a la ciudad
Muy deprisa a contar
Estas nuevas de Verdad.
//A todos testificó
De Jesús el Salvador
Y las almas al Señor
Acudieron con amor//"""
        ),
        Hymn(
            id = 388,
            title = "El Hijo Prodigo",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Cuando el hijo prodigo volvió
Desde la distancia a su hogar
//Todo había gastado en el placer
Pasajero de este mundo infiel//
Al mirar su triste condición
El volviendo en si se preguntó:
//“¿Por qué sufro hambre y soledad
Y los jornaleros tienen pan?”//

Coro:
Me levantaré y volveré,
El Perdón al Padre rogaré:
//“No me llames Hijo por favor,
Solo un jornalero quiero ser”//

II
Con mi Padre yo también viví
But sus Bondades no entendí
//Hasta que un día lo olvidé
Y muchos pesares yo sufrí//
Pero he podido regresar
Al oír Su dulce y tierna voz
//Él me recibió con Gran amor
Y también me dio de Su perdón//

Recitado:
Padre mio, no soy digno de estar en Tu Presencia, pero te quiero dar
gracias, Gracias por Tu amor, por Tu perdón, por Tu grande compasión,
por Todo lo que me das cada día que estoy en Tu casa. A Ti sea toda la
Glory, el Honor, la Alabanza por Siempre. Amén."""
        ),
        Hymn(
            id = 389,
            title = "La Visión de Daniel",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Daniel junto al rio Hidekel
Contempló al Señor en su Gloria
//Sus fuerzas flaquearon
Al son de Sus Palabras
Más le tocó su mano
Y en pie se levantó//

Coro:
Tócame como a Daniel, dame las fuerzas también
Hoy estoy en Tu Presencia, también Tu siervo yo soy.
Háblame como a Daniel, dime lo que debo hacer
Hoy estoy en Tu Presencia, tócame y háblame.

II
Vestido de lino un Varón
Con sus lomos ceñidos de oro
//Sus ojos como fuego,
Su Rostro cual relámpago,
Su voz como un estruendo
De grande multitud/

Recitado:
Y Aquel que tenia semejanza de hombre, me tocó otra vez, me fortaleció y
me dijo: “Muy amado, no temas, la paz sea contigo, esfuérzate y
aliéntate”. Y mientras Él me hablaba, recobre las fuerzas y dije: “Hable mi
Señor, porque me has fortalecido”.

III
La Omnipresencia del Señor
Al Profeta dejo sin aliento
//Su rostro puesto en tierra
Quedó enmudecido
But Él tocó sus labios
Y así él pudo hablar//"""
        ),
        Hymn(
            id = 390,
            title = "Camino del Mar",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Camino del mar por la otra ribera,
Antigua ciudad, un puerto de barcas de humildes pesqueros
De triste mirar, de ojos cargados de sueño,
De rostros curtidos por el viento frio,
De hombres sencillos como Andrés y Pedro.
La brisa invernal sopla suave en la playa
Y por el arenal muy lento camina Jesús el Maestro
Y en la soledad contempla a aquellos obreros
Que arreglan sus redes gastadas del tiempo,
Son Juan y Jacobo hijos de Zebedeo.

Coro:
El Hijo de Dios, el Verbo hecho carne,
Sandalias en sus pies transitó Galilea.
Su luz alumbró al pueblo asentado en tinieblas
Como lo anunciaba de antaño el Profeta:
“Al fin llenará de Su Gloria la tierra”.

Recitado:
Así comenzó su ministerio Jesús, el hijo del carpintero; tan humilde, pero
en Él estaba la vida, en Él estaba el amor y en Él estaba el perdón.

II
Venid pues a Él pescadores de hombres,
Se escucha Su voz, retumba Su eco por toda la costa,
En ella también el corazón de Andrés y Pedro,
De Juan y Jacobo que allí le siguieron,
Que dejaron todo por ser mensajeros.
Grande multitud de afligidos y enfermos,
Acuden a Él en busca de auxilio en malos problemas,
Mas con Su poder liberta y transforma las vidas,
Llevando del pueblo todas las dolencias,
Predicando el Santo Evangelio del Reino."""
        ),
        Hymn(
            id = 391,
            title = "Por desiertos sin Caminos",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Por desiertos sin caminos,
Sin saber donde vivir,
Hambriento y sediento
Yo viví en soledad.
But un día alcé mis ojos
Y un camino encontré
Y a lo lejos vi un madero
Y en las piedras vi la Sangre
Derramada por Jesús.

Coro:
Vi Su cuerpo maltratado, vi los clavos en Sus manos,
Vi los clavos en Sus pies, Su costado traspasado.
Vi Su Sangre derramada y mi alma allí lavé
Y en aquel mismo momento comprendí aquel sufrimiento
Y humillado ante la cruz a Jesús yo me entregué.

II
Y allí nació en mi alma
La esperanza de vivir
“una fuente he hallado
Do mi ser poder calmar”.
Y hoy yo vivo muy Gozoso
Y camino junto a Él,
Mi esperanza es Vida Eterna
De llegar hacia los cielos
Y vivir ya junto a Él."""
        ),
        Hymn(
            id = 392,
            title = "Quiero ser un Trigo Bueno",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Ya se acerca el tiempo de la siega
Pues el frio invierno ya pasó,
Preparad los graneros para el trigo
Que del cielo el Segador descenderá.
Él vendrá con Poder y con Gran Gloria
Y Consigo la cosecha llevará,
La cizaña desechada del Buen trigo
Consumida por el fuego quedará.

Coro:
//Quiero ser un trigo bueno en Tu granero
Escogido por Tu Gracia y por Tu amor;
//Quiero estar en la cosecha más Gloriosa,
Quiero ir Contigo Amado Salvador//

II
Este mundo de maldad hoy nos acecha
No nos deja crecer en la Verdad
Jesucristo es la simiente poderosa
Todo aquel que está en Él no caerá.
Él vendrá con Poder y con Gran Gloria
Y Consigo la cosecha llevará,
La cizaña desechada del Buen trigo
Consumida por el fuego quedará."""
        ),
        Hymn(
            id = 393,
            title = "Veremos al Rey",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Mi alma te alaba
Y Bendice Tu Nombre oh Rey Salvador
Y anhela ferviente
Tu Gloria Imponente poder contemplar.
Mi Ser te proclama, mi voz se hace llanto
Por Ti Amado Rey
Y ansiosa espera ese día sublime
Que pronto se acerca y veremos al Rey.

Coro:
Veremos al Rey, al Rey Jesucristo,
Veremos al Rey, al Rey Victorioso.
Que fue inmolado por mi vil pecado
Allá en la cruz,
Más viene Glorioso y al son de Trompetas
En nubes del cielo veremos al Rey.

II
Yo espero ese día
De Jubilo y Gozo, de Gloria sin par
Y quiero estar listo
Velando y orando yo quiero esperar.
Él viene a llevarnos, pues Él ha dejado
Esta Promesa Fiel:
“Voy a preparar moradas Celestes,
Descanso eterno junto al Padre Dios”."""
        ),
        Hymn(
            id = 394,
            title = "El Ensueño de la Iglesia",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Ha llegado la mañana pero aun oscuro es
Y al sepulcro va deprisa solitaria una mujer,
But amarga es la sorpresa que le espera al llegar
Pues quitada ve la piedra y el Maestro ya no está.
Ella vuelve a los suyos la noticia a contar
Mas regresa tras de ellos y comienza a llorar
“¿Por qué lloras y a Quien buscas?” le pregunta una voz
Que recuerda haberla oído, pues siente consolación.

Coro I:
“María”, “Raboni”; “oh Maestro no pensaba encontrarte otra vez”.
“María”, “Raboni”; “no me toques,
Porque al Padre no he subido pues aun,
Pero ve a mis hermanos y todo esto cuéntales
Y que luego en Galilea Yo con ellos estaré”.

Recitado I:
Abrí yo a mi Amado, pero mi Amado se había ido, había ya pasado; y tras su
hablar salió mi alma, lo busqué y no lo hallé, lo llamé y no me respondió.

II
En Cantar de los Cantares Salomón da una visión
De la esposa enamorada que va en busca de su amor,
Caminando por las calles una noche en la ciudad
Afanosa preguntando a su Amado puede hallar.
En su búsqueda constante así María lo encontró,
Más no pudo asir de Él pero su alma consoló;
Hoy la Iglesia también busca y espera al Señor
Elevando sus montañas aromadas de canción.

Coro II:
Iglesia de Cristo, hoy llora en la Presencia de Tu Amado Salvador,
Iglesia de Cristo, Él Vive, Él te ama, reconoce hoy Su voz.
Búscale ha resucitado para darte Salvación,
Ve corriendo cual María y no calles del Señor.

Recitado II:
“Yo Soy el Buen Pastor, el Buen Pastor su vida da por las ovejas”. 
“Yo Soy el Buen Pastor, conozco a mis ovejas y las mías me conocen”."""
        ),
        Hymn(
            id = 395,
            title = "Recíbeme Tal como Soy",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Quiero Señor como María a tus pies hoy humillarme
Y escuchar esas palabras de amor que me conmueven,
Todo deje por encontrarme junto a Ti este momento
//Deje mi hogar, y el afán por adorarte
Recíbeme tal como soy en Tu Presencia//

Coro:
Quiero romper mi corazón en alabanza
De gratitud y de amor para Tu Gloria
Dulce expresión que va naciendo en mi alma
Mientras el llanto me inunda las palabras.
María fue y te perfumó con su fragancia
Ungió Tus pies y de ese olor llenó la casa,
Yo nada tengo solo mira estas lágrimas
//Jesús te amo, de mi lado no te vayas//

II
Yo te busqué muy afligido como Marta te buscaba
Y te encontré y Tú me diste el consuelo, paz y calma;
Lázaro fui, pues del pecado y su muerte me has librado
//Oh cuanto me amas, oh Señor Tu eres mi Amigo
Toda mi vida solo a Ti yo quiero darte//"""
        ),
        Hymn(
            id = 396,
            title = "Mi Testimonio",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """Presentación:
Querido amigo, quisiera compartir contigo una de las tantas obras que
Dios hizo en mi vida: “vivía confundido y sin esperanza y a causa de esto
todo me iba peor, hasta el momento en que llegue a apostar en juegos del
azar y muchas veces, aposté hasta lo que no tenia, ignorando que Su
Palabra dice: ‘Jehová es la porción de mi herencia, Tú sustentas mi suerte’.
Pero hoy puedo asegurar que la luz de Su Palabra me alumbró y Dios
limpio de mi corazón todo esto y solo espero en Él. Por eso te invito que
medites esta canción, y ‘apuestes’ a Jesucristo ya que en Él hallarás la 
Vida Eterna”.

I
Ayer yo he visto mucha gente,
Cuantos rostros diferentes
Con angustia y desazón.
Gentes, que ignorando al Dios Viviente
Van en busca de dinero
En los juegos del azar.
Vidas extraviadas, confundidas
Que caminan en tinieblas
De este mundo en perdición.

Coro:
//Señor, Señor porque la gente triste va
Quizás todo han perdido por ganar,
Mañana no tendrán para el pan
Sus hijos sufren hambre y mucho más.
Yo estaba confundido por igual
Más Tú me has libertado de ese mal,
La luz de Tu Palabra me alumbró,
Mi mente y corazón ella limpió
Gracias te doy Señor por Tu Gran compasión;
Hoy solo espero en Ti y en Tu amor
Y en Tu bondad//"""
        ),
        Hymn(
            id = 397,
            title = "Sembrador",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Dejó Su taller de carpintero
Allí en Nazaret su humilde pueblo,
La alforja tomó y salió al campo
Dispuesto a empezar con su trabajo.
Su mano agarró a la mansera
Y el surco empezó allá en la arena
A orillas del mar de Galilea
Sembró con poder y vida eterna.

Coro:
Al Gran sembrador de mis recuerdos 
Le quiero cantar y en estos versos
Contar que Él anduvo en este suelo
Sembrando el poder de Su Evangelio.
Un día murió en un madero
Más Resucitó y fue al cielo,
De allí volverá con su granero 
Para cosechar el fruto bueno.

II
Fue de sol a sol, de pueblo en pueblo
Sembrando Su amor, paz y consuelo
Con Fe y oración y con desvelo
El grano esparció con gran esmero.
Y tu sembrador, de Cristo obrero
Tendrás que llorar muchos pañuelos
Como Él lloró solo en el huerto
Regando el sudor sobre el terreno.

Recitado:
Oh infatigable sembrador, Jesucristo Divino que caminaste en este suelo
sembrando amor, paz y consuelo; el hombre vil y pecador te clavó en un
madero, ignorando que esa semilla cayó en buena tierra y en Tu Iglesia se
formó. Y hoy por esa infinita misericordia, hay obreros que siguen
pregonando Tu Evangelio, cumpliéndose así Tu Palabra cuando dice:
“Mirad, andando y llorando el que lleva la Preciosa semilla, mas volverá a
venir con Regocijo, trayendo sus gavillas”."""
        ),
        Hymn(
            id = 398,
            title = "Elías y Eliseo",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """Presentación:
Y Elías le dijo: “Te ruego que te quedes aquí, porque Jehová me ha
enviado al Jordán”, y él le dijo: “Vive Jehová y vive tu alma que no te
dejaré”.

I
Junto al rio Jordán dos hombres van,
Platicando sin cesar al caminar
//Uno es siervo de Dios, el otro es servidor
Que ferviente sirvió a su señor//
Uno al cielo se irá con esplendor
But el otro seguirá Fiel su misión
//Una gran bendición para el dejará
Por seguirle hasta allí sin dudar//

Coro I:
Es Elías Profeta de Dios, Eliseo un Fiel servidor,
//Un ejemplo a seguir,
La Palabra cumplir y el Señor nos dará Galardón//

Recitado:
Y cuando habían pasado Elías dijo a Eliseo: “pide lo que quieras que haga
por ti, antes que yo sea quitado de ti”. Y dijo Eliseo: “te ruego que una
doble porción de tu espíritu sea sobre mi”.

II
El Profeta le habló con gran amor:
“Eliseo aquí quédate yo me voy”
//Mas siguió tras de él y al cruzar el Jordán
Un humilde favor le pidió//
Una doble porción quiero Señor 
De Tu Espíritu Santo sobre mí
//Mas la Gloria de Dios al instante bajó
Y al Profeta al cielo llevó//

Coro II:
Fue un carro de fuego que allí
Desde el cielo bajó con Poder
//Pero el manto quedó y las aguas golpeó
Y el Jordán dividido quedó//"""
        ),
        Hymn(
            id = 399,
            title = "Joven Rico",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """Presentación:
“Maestro Bueno ¿qué haré para heredar la vida eterna?” Jesús le dijo:
“¿por qué me llamas bueno? ninguno hay bueno sino Solo Dios ¿los
mandamientos sabes?” “todo lo he guardado desde mi juventud”. Jesús
oyendo esto le dijo: “aun te falta una cosa: ‘vende todo lo que tienes y
dalo a los pobres y tendrás tesoros en el cielo y ven, sígueme”.

I
Hermanos hoy yo les quiero contar una historia notable y singular
//De un joven que quería heredar la vida eterna y Celestial//
Este hombre era rico, y leal a Dios en sus dichos al hablar
//Mas Cristo descubrió su falsedad, su egolatría y vanidad//

Coro:
Jesús dame un nuevo corazón, 
Humilde, bondadoso y con amor,
Sincero, sin orgullo ni acepción,
Amante, compasivo y sin rencor.
Yo quiero obtener la Salvación
Tesoro escondido en un rincón 
Del cielo donde solo puede entrar 
El Limpio de manos y Puro de Corazón.

Recitado:
Y al ver Jesús que se había entristecido mucho le dijo: “cuan difícilmente
entraran en el Reino de Dios los que tienen riquezas”. Y los que oyeron
esto le dijeron: “Señor ¿quién pues podrá ser Salvo?” Jesús les dijo: “lo
que es imposible para los hombres es posible para Dios”.

II
Muy triste este hombre se marchó por la vileza de su corazón
//Pues la sabia respuesta del Señor tocó en el centro de su ambición//
Hermano, no te vayas por favor entrégate a Jesús sin restricción
//Hoy vende tu altiva posesión, toma tu cruz y síguele al Señor//"""
        ),
        Hymn(
            id = 400,
            title = "Vi la Salvación",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
María y José llevaron a Jesús cuando niño era él
Al templo allá en Jerusalén,
Simeón le recibió y en brazos lo tomó
Y dando Gloria a Dios bendijo al Señor.

Coro:
//Mis ojos han visto tu salvación//
Bendigo Tu Nombre, Tu Nombre mi Dios,
Bendigo Tu Nombre Señor.

II
Fue uno de los diez leprosos que limpio
Que ante Cristo se postro y vio la Salvación;
Un ciego en Jericó la vista le clamó
Jesús le devolvió y vio la Salvación.

III
También aquel ladrón que estaba en la cruz
Al lado de Jesús miró la Salvación,
Esteban al morir sus ojos levantó
Al cielo y contempló también la Salvación.

Recitado:
Bendito sea Dios, bendito sea el Señor, bendito sea aquel día en que Tu
también has llegado hasta mi vida; por eso hoy Señor desde el altar de mi
corazón levanto mi voz en alabanza y digo como Simeón: “mis ojos, han
visto tu Salvación”."""
        ),
        Hymn(
            id = 401,
            title = "La Oración de Jesús",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Sus rodillas clavadas en la tierra 
Y sus ojos mirando hacia el cielo
Y un clamor incesante en sus labios,
Conmovido con tristeza y gran dolor.
“Padre Mio la hora ha llegado,
Glorifica a Tu Hijo, Glorifícame,
Glorifícame a Tu lado por la Gloria
Que Yo tuve antes de la creación”.

Coro:
//Maestro// con tus lágrimas regaste aquel huerto
En humillación de espíritu, alma y cuerpo,
Aquel llanto derramaste Tú por mí.
//Maestro// en Tu oración llevaste mi recuerdo,
Por mi vida y por el conocimiento 
De creer en Tu Palabra de Verdad;
//Maestro// Santifícame Señor en Tu Verdad.

II
Su agonía intensa y amarga
Y su rostro mojado por el gran sudor
Grandes gotas que caían hasta el suelo
En la noche fría del Getsemaní.
“En Tu Nombre guárdalos Dios Mio,
En el mundo hoy están pero no son de él,
Padre Justo, aquellos que me Has dado,
Donde estoy Yo quiero que Conmigo estén”."""
        ),
        Hymn(
            id = 402,
            title = "¿Dónde está el Niño?",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
¿Dónde está el Niño, el Niño donde está?
Lo están buscando sus padres sin cesar,
Hace tres días que con ellos no está
Todo es tristeza, angustia y gran pesar.

Coro I:
¿Dónde está, el Niño donde está?
Quiero encontrarlo y llevarlo hasta mi hogar.
¿Dónde está, el Niño donde está?
Quiero sentir esas caricias de bondad;
Quiero escuchar esa voz tierna,
Quiero sentir de Su Presencia,
Quiero encontrarlo y no dejarlo Nunca más.

II
¿Dónde está el Niño, el Niño donde está?
En los Negocios del Padre Celestial,
Allá en el Templo lo pueden encontrar
Y nuevamente todo es felicidad.
¿Dónde está el Niño, el Niño donde está?
Hermano mio te quiero preguntar,
Si lo has perdido y triste tú estás,
Hoy nuevamente lo puedes encontrar.

Recitado:
Querido hermano ¿crees que has perdido al Señor Jesús? pues hoy, has
como María, vuelve al Templo y allí en la oración, en la alabanza,
escuchando Su Palabra o a través de un hermano lo encontrarás, ya que el
mismo Jesús nos dijo: “He aquí, Yo estoy con vosotros todos los días y
hasta el fin”.

Coro II:
¿Dónde estás, mi Cristo dónde estás?
Quiero encontrarte y llevarte hasta mi hogar,
¿Dónde estás, mi Cristo dónde estás?
Quiero sentir esas caricias de bondad;
//Quiero escuchar esa voz tierna,
Quiero sentir de Tu Presencia,
Quiero encontrarlo y no dejarte Nunca más//"""
        ),
        Hymn(
            id = 403,
            title = "Canta por tu Libertad",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Ayer escuché un suave y dulce cantar
Que un ave pequeña podía entonar,
Ella estaba presa sin su libertad
Pero aun cantaba en su soledad.
Al verla sentí yo tristeza sin par
But en el momento pensé sin dudar
Que hasta los cielos quería llegar
Y a Dios con su trino pedir libertad.

Coro:
Como Pablo y Silas pudieron cantar 
Cuando estaban presos por predicar
Y Dios desde el cielo les dio libertad.
Hoy canta mi hermano, canta sin dudar,
Canta porque Cristo te escuchará,
Canta a Jesucristo por tu libertad.

II
En esta enseñanza pude recordar 
Que estando en pecado solía cantar
Estaba muy triste y quería encontrar
En Dios el consuelo, el amor y la paz.
Y Cristo Bendito me pudo escuchar
El clamor de mi alma y me vino a Salvar,
Extendió Su mano y me dio libertad
Y hoy puedo con gozo Su Nombre alabar.

III
Si por este mundo hoy preso tú estás,
Placeres y vicios que te hacen pecar,
Y triste te encuentras sin amor, sin paz
Hoy canta mi amigo por tu libertad."""
        ),
        Hymn(
            id = 404,
            title = "Bautízame Juan",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """Presentación:
El siguiente día vio Juan a Jesús que venía a él y dijo: “He aquí el Cordero
de Dios que quita el pecado del mundo, este es Aquel de quien yo dije:
‘después de mi viene un Varón el cual es antes de mí, porque era primero
que yo y yo no le conocía pero el que me envió a bautizar con agua, Aquel
me dijo: ‘sobre quien veas descender el Espíritu y permanece sobre Él,
Éste es el que bautiza con el Espíritu Santo”.

I
Allí esta Él, se lo ve venir, 
Entre la gente camina el Señor
Distinto es al hombre mortal, 
A la distancia se puede notar;
¿Sera uno más el Hijo de Dios 
Que en el Jordán se bautizará?
Él va a cumplir con un Gran deber 
Para dejar Su ejemplo Fiel.

Coro:
“Bautízame Juan” clamaba el Señor 
“esto es menester, debemos cumplir”
Juan obedeció y le sumergió,
El cielo se abrió y allí descendió:
Cual Blanca Paloma el Espíritu Santo
Sobre Su cabeza vino a reposar
Y una voz del cielo clamaba diciendo:
“Este es Mi Hijo Amado, Complacido Estoy”.

II
Profetizado fue por Isaías 
Y el mismo Juan lo vino a anunciar:
“Yo no soy digno de desatarle 
Esos calzados que lleva en Sus pies”.
Y también dijo: “yo bautizo en agua 
But el que viene tiene Gran Poder:
‘Bautiza en Fuego y Espíritu Santo 
Y está en Su mano Su Aventador”."""
        ),
        Hymn(
            id = 405,
            title = "Ríos de Agua Viva",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Vengo a Tu casa esta noche
A escuchar Tu Palabra
Rendido ante Tus pies
Como María en Betania.
Espíritu, alma y cuerpo
Hoy quiero Señor entregarte
Y libre de mis afanes,
Quiero en verdad adorarte.

Coro:
Señor que Hermoso es estar Contigo
Y sentir que esos ríos de Agua Viva
Corren por mí ser.
Señor que Hermoso es estar Contigo
Y sentir que Tu Espíritu Santo
Va llenando nuestro ser.

II
Sobre Tu Cuerpo la Iglesia
Mi corazón te derrama
Este alabastro quebrado,
Perfume de alabanza.

Recitado:
¡Gracias Señor! porque cuando llegue a Tu Casa vine cansado y trabajado
por el afán y la ansiedad. Pero al oír Tus Palabras pude sentir que por mi
interior corrieron esos Ríos de Agua Viva llenando mi vida de gozo, de
amor y de paz."""
        ),
        Hymn(
            id = 406,
            title = "Vuelve Señor",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
El tiempo hoy pasa veloz
No puedo notar cada instante,
Un año que llega y se va
Los días son cortos y se acaban.
La noche no tarda en llegar
Y pronto habrá un nuevo día
Y al ver otro amanecer
Yo espero Señor Tu Venida.

Coro:
Vuelve Señor, regresa ya
Escucha el clamor con llanto y dolor
De un pueblo que espera por Ti.
Vuelve Señor sin demorar,
Ya no tardes mas, desciende a llevar
Tu Pueblo te espera Señor.

II
Más pienso y vuelvo a meditar
Leyendo Tú Libro Sagrado
En esas promesas de amor
Que Tú al andar has dejado:

Recitado:
“No se turbe vuestro corazón, creed en Dios, creed también en Mi, en la
Casa de Mi Padre muchas moradas hay, si así no fuere Yo os lo hubiera
dicho; Voy pues a preparar lugar para vosotros y si me fuere y os
preparare lugar, vendré otra vez y os tomaré a Mi mismo para que donde
Yo estoy vosotros también estéis."""
        ),
        Hymn(
            id = 407,
            title = "La Siembra",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Alzad vuestros ojos y mirad los campos
Because ya están blancos para ir a la siega
Y el que siega recibe su salario
Y recoge frutos para vida eterna
Y el que siembra gozará junto con él.

Coro:
//Uno es el que siembra,
Otro es el que siega
Mas la recompensa 
Dará el Señor//

II
Allí junto al pozo de Jacob, sentado
Jesús a los discípulos habló enseñando
“A segar a vosotros He mandado
No labrasteis, otros trabajaron
Mas vosotros habéis entrado en su labor”.

III
Obreros necesita el Salvador, hay campos
Que esperan para ir a trabajar, Él te llama 
Como a Pedro, Jacobo y Juan llamosle
Tan humildes y por Él todo dejaron
Pero fueron llenos del Poder de Dios."""
        ),
        Hymn(
            id = 408,
            title = "Carta al Hijo Prodigo",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Te has marchado del hogar
Sin motivo y sin razón
Te has dejado un vacio inmenso,
Tu partida nos dejó tristeza y desazón
Tu ausencia nos causa dolor.

Coro I:
Nuestro Padre siempre está
Contemplando desde aquí
Aquel camino por donde Él te vio marchar
Con tu herencia total que el Amante te otorgó.
Pero anhela sin cesar
Mirarte regresar de nuevo aquí;
Nadie ocupa tu lugar, nada lo podrá llenar
//Si no estás tú//

II
Cuanto tiempo ya pasó
Sin saber nada de ti,
Nos angustia, nos causa aflicción;
Pues quisiera yo saber si has podido encontrar
Conformismo y felicidad.

Coro II:
Si tal vez se terminó 
Tu herencia y bienestar
Y estas tú triste y hoy quieres regresar
But dudas si quizás el Perdón Dios te dará.
Su amor no cambiará
Él te ama más y más, vuelve al hogar;
Nadie ocupa tu lugar, nada lo podrá llenar
//Si no estás Tu// 

Final:
“Ven”."""
        ),
        Hymn(
            id = 409,
            title = "Cantar de los Cantares",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
“Hermana, Amiga, Paloma, ven”.
La voz de Mi Amado despertó mi corazón,
Dormido en el invierno aterido estaba yo
“Levántate” me dijo “oh amiga Mía, ven,
El frio se ha mudado y la lluvia ya se fue”.
La voz de mí Amado a mí ser estremeció
Y hoy vivo enamorado prisionero de Su amor,
Lo gris de mi tristeza y mi llanto transformó
En una primavera de alegría y Bendición.

Coro:
Las flores en la tierra han mostrado su color,
La higuera dio su fruto y las vides dan su olor.
//Despiértate y levántate Iglesia del Señor
Porque ha venido el tiempo de la canción//

II
Mi Amado es blanco y rubio distinguido entre diez mil,
Es manso, es humilde, noble y sabio Rabí;
Su voz como trompeta que me dice: “Ven a Mi
Que tengo una morada en el cielo para ti”.
“Paloma Mía que en la peña escondida estas
Desciende hasta mi huerto hoy contigo quiero hablar;
Muéstrame tu rostro, tu voz quiero escuchar,
Porque tu voz es Dulce y tu aspecto sin igual”."""
        ),
        Hymn(
            id = 410,
            title = "Bienvenido a Casa",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Bienvenido a Casa hermano querido,
“Bienvenido a Casa” te dice el Señor,
Olvida el pasado y acude a Sus brazos
Él te está esperando, te dará el perdón.
Limpia Él tu vida, no pierdas el tiempo
Porque ya mañana tarde puede ser 
//Siéntate a la mesa, sírvete la cena
Cristo te convida no le seas infiel//

Coro:
Grande es la alegría al verte de nuevo,
Grande es el gozo de nuestro corazón,
Grande es la fiesta que hay en el cielo
Cuando arrepentido vuelve un pecador;
Grande es la fiesta que hay en el cielo
Cuando un Hijo Prodigo vuelve al Señor.

II
Toma del vestido y ponte de nuevo
Toma el calzado y ciñe tus pies,
También el anillo sellado con Fuego,
Ponte en tu mano el pan otra vez.
Vístete ahora, vístete de Bodas,
Viste hoy tu vida con la Santidad
//Para que un día entres a las Bodas
De Cristo el Cordero que preparada está//"""
        ),
        Hymn(
            id = 411,
            title = "La Fe",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Dicen que la Fe es más preciosa que el oro
Y que vivir por Fe es agradable al Señor
Y que es un Don de Dios y por la Fe seremos Salvos.
Y que por la Fe buen testimonio alcanzaron los antiguos por la Fe,
Podemos entender que el universo fue creado por Él.

Coro:
Es la Fe la convicción de lo que no se ve,
Es la certeza de lo que se espera obtener
Señor auméntame la Fe, Señor auméntanos la Fe.
Es la Fe la convicción de lo que no se ve,
Es la certeza de lo que se espera obtener
Señor auméntame la Fe, quiero vivir yo por la Fe,
Quiero ser Salvo por la Fe, Señor auméntanos la Fe.

II
Por la Fe Abel ofreció a Dios buen sacrificio
Y por la Fe traspuesto fue Enoc sin ver la muerte,
Por la Fe también Noé construyó el arca y fue Salvo.
Por la Fe Abraham dejó su tierra y vivió como extranjero por la Fe,
El esperaba la ciudad cuyo arquitecto y constructor en nuestro Dios.

Recitado:
¿Y que mas digo? porque el tiempo me faltaría contando de Gedeón, de
Barac, de Sansón, de Jefté, de David, así como de Samuel y de los profetas;
que por Fe conquistaron reinos, hicieron justicia, alcanzaron promesa,
taparon boca de leones, apagaron fuegos impetuosos, evitaron filo de
espada, sacaron fuerzas de debilidad, se hicieron fuertes en batalla,
pusieron en fuga ejércitos extranjeros.
Otros, experimentaron vituperios y azotes, y a mas de esto, prisiones y
cárceles. Fueron apedreados, aserrados, puestos a prueba, muertos a filo
de espada, anduvieron de acá para alla cubiertos de pieles, pobres,
angustiados, maltratados."""
        ),
        Hymn(
            id = 412,
            title = "La Unidad",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
//Uno es Jesús con el Padre,
Uno es el Espíritu Santo
Que hoy habla y ordena buscar
Y guardar la unidad, el amor y paz//

Coro:
Jesús el Buen Maestro oró por sus discípulos
Y también por nosotros que hoy creemos en Él,
//Para que seamos uno como Él es con el Padre,
Para que seamos Perfectos viviendo en unidad//

Recitado:
“Mas no ruego solamente por estos, sino también por los que han de
creer en Mi por la palabra de ellos, para que todos sean Uno, como Tú oh
Padre en Mi y Yo en Ti, que también ellos sean uno en Nosotros, para que
el mundo crea que Tu me enviaste, la Gloria que me diste Yo les he dado,
para que sean uno así como Nosotros somos Uno”.

II
Uno es el cuerpo de Cristo,
Una es la Iglesia Su Cuerpo
Sus huesos no pudieron quebrar
Para que hoy pueda estar Su Iglesia en Unidad."""
        ),
        Hymn(
            id = 413,
            title = "Más que Vencedores",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Si amamos a Dios, también Él nos ama
Por él es amor, Dios es Amor,
Él nos escogió desde el principio
Que a Su Hijo dio por justificar nuestros pecados.

Coro I:
//Nada, nada nos separará del Amor de Dios//
Dios es con nosotros no hay que temer
Más que Vencedores hoy somos por Él.
Nada, nada nos separará del Amor de Dios.

II
Si amamos a Dios, también Él nos ama
Por él es amor, Dios es Amor,
Si en mi esta Dios, ya nada temo
El Perfecto amor no tiene temor, yo amo a Cristo.

Coro II:
//Nada, nada nos separará del Amor de Dios//
Ni espada, ni muerte, hambre o desnudez,
Angustia, peligro ni persecución.
Nada, nada nos separará del Amor de Dios.
Dios es amor, Dios es amor, Dios es amor, Dios es Amor.

Recitado:
Como está escrito: “por causa de Ti somos muertos todo el tiempo, somos
contados como ovejas de matadero. Antes, en todas estas cosas somos
Mas que Vencedores por medio de Aquel que nos amó; por lo cual estoy
Seguro, de que ni la muerte, ni la vida, ni ángeles, ni principados, ni
potestades, ni lo presente, ni lo porvenir, ni lo alto, ni lo profundo y
ninguna otra cosa creada nos podrá separar del Amor de Dios que es en
Cristo Jesús, Señor nuestro”."""
        ),
        Hymn(
            id = 414,
            title = "Pedro",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Hubo un hombre en Galilea que tenía barca y redes y de oficio pescador,
Con fatiga trabajaba, día y noche traficaba hasta que vino el Señor,
Fue tan solo una mirada muy tierna y animada con final de invitación:
“Yo te cambiaré el oficio, desde hoy en delante de hombres serás pescador”.

II
Quiero ser como aquel hombre, Pedro era su sobrenombre y tenía poca Fe,
Con Jesús el siempre andaba, casi siempre fracasaba, pero el tenia Fe
Caminó sobre las aguas, le siguió a Jesús al huerto
Y con su espada defendió,
Ya en el patio junto al fuego, acosado y perturbado por tres veces le negó.

Coro:
“Pedro ¿me amas esta vez?” “Pedro ¿me amas más que al pez?”
“Oh Señor yo siempre te amaré, si Tu sabes que te amo,
Tu sabes todas las cosas más yo siempre te amaré”.

III
Luego el hombre fracasado con gran voz e inspirado, predicó en Pentecostés
Tres mil almas convertidas, todas fueron bautizadas solo con tirar la red,
Ya en el templo de La Hermosa,
Junto a Juan miran al cojo quien les pide una merced;
“Yo no tengo plata ni oro, mas te ordeno que camines por Jesús de Nazaret”.

IV
Cuando el cojo fue sanado gobernantes resentidos, a Pedro van a prender
Les pusieron en la cárcel, le intimaron que no hablasen de Jesús de Nazaret
Y aunque Pedro encarcelado,
El mensaje lo había dado y no había más que hacer
Alguien le cambió el oficio
Y esta vez cinco mil hombres estaban dentro de la red.

V
Luego siguió trabajando aquel hombre que pescando el Señor lo habilitó
La Palabra fue el anzuelo, el poder fueron las redes mas la Gracia es de Dios,
Cuando en casa de Cornelio predicando a los gentiles, con denuedo les habló,
Hombres, mujeres y niños todos fueron bautizados con el Fuego del Señor."""
        ),
        Hymn(
            id = 415,
            title = "El Dulce Cantor",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Era un humilde muchacho
Que tocaba el arpa y cantaba muy bien
Era pastor de ovejas, David se llamaba y vivía en Belén.
El a Dios siempre oraba
En las tardes y mañanas elevaba su clamor,
El a Dios siempre alababa, en integridad andaba
Los caminos del Señor.

Coro:
“Jehová es mi Pastor, nada me faltará”,
Así cantaba al Señor con su arpa David,
David el pastor.
“Te Exaltaré mi Dios, te Exaltaré mi Rey”
Así cantaba al Señor con su arpa David,
El dulce cantor.

II
Era un humilde muchacho
Prudente en palabras, obediente a Dios
Lleno del Espíritu Santo,
Unción que Dios del cielo sobre él derramó,
Cuando a Saúl atormentaba
El espíritu malo que venía sobre él,
David con su arpa tocaba,
Melodías que aliviaban la tribulación del rey."""
        ),
        Hymn(
            id = 416,
            title = "Zorobabel",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """Presentación:
Así ha hablado Jehová de los ejércitos diciendo: “este pueblo dice: ‘no ha
llegado aun el tiempo, el tiempo de que la casa de Jehová sea
reedificada”. Entonces vino palabra de Jehová por medio del Profeta
Hageo diciendo:

I
Zorobabel, mira Mi casa desierta está
Y este pueblo vacila al pensar,
Dice: “no es tiempo de reedificar”.
Mucho sembráis pero es poco lo que recogéis
No os saciáis al comer ni al beber
Y en saco roto va vuestro jornal.

Coro:
Esfuérzate Zorobabel,
Pueblo de Dios cobrad animo y trabajad.
Esfuérzate Zorobabel
“Porque Yo estoy con vosotros”
Dice Jehová.

II
Mucho buscáis, poco halláis y en casa encerráis,
Pero de un soplo lo dispersaré
Porque a Mi casa como nada veis.
“Mas” dice Dios “Mio es el oro y la plata también,
Si me probáis en esto abriré
Mis ventanas y os Bendeciré”.

III
“¿Alguien quedó entre vosotros?”
Pregunta el Señor
“¿Que haya visto la gloria anterior
Cuando este Templo se reedificó?”"""
        ),
        Hymn(
            id = 417,
            title = "El Maná",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
De mañana junto al amanecer
Iba el pueblo de Israel a recoger
El Maná que Dios hacia descender,
Pan del cielo que les daba de comer.

Coro:
Y gustando de este incomparable bien
Día a día iba el pueblo a recoger
Cada uno la medida de un gomel
Sin faltarle ni sobrarle al comer.
//Mañana tras mañana yo también
De rodillas quiero ir a recoger
Sus misericordias nuevas y beber
Del pozo del Viviente que me ve//

Recitado:
Jesucristo dijo: “Yo Soy el Pan Vivo que descendió del cielo, el que a Mi
viene no tendrá hambre y el que en Mi Cree, no tendrá sed jamás.

II
Pan del cielo para mi alma Cristo es,
Mi sustento y ayuda de mi ser
Él despierta mi oído al hablar con Él
Sus Palabras me infunden Fuerza y Fe."""
        ),
        Hymn(
            id = 418,
            title = "El Leproso",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
En un leprosario allá en la montaña
Un hombre leproso la muerte esperaba,
No había remedio y alguien que curara
El mal que en su cuerpo ya no soportaba.
Muy triste, aislado de aquellos que amaba
No había en su vida ninguna esperanza
Mas una noticia conmueve su alma:
“Allá va Jesús y dicen que sana”.

Coro:
“Yo iré a buscarlo” dijo aquel leproso,
Corrió entre la gente y llegó donde estaba,
“Señor si quieres puedes limpiarme”
Postrado ante Él así le rogaba.
Then movido a misericordia
Su mano extendiendo Jesús le contesta:
“Quiero, se limpio”, y en aquel instante
Fue sano y limpio de toda su lepra.

Recitado:
Jesucristo es el Mismo ayer, hoy y por los siglos; Jesucristo me Salvó,
Jesucristo me limpió y Jesucristo me sanó.

II
Feliz aquel hombre regresa a su casa
Abraza a los suyos que tanto extrañaba
“no cuentes a nadie” le encargó el Maestro
Pero él del Señor a todos hablaba.
En este milagro está reflejada
Mi vida en el mundo antes que llegara
A los pies de Cristo, el cual con Su Sangre
Limpio mis pecados, la lepra de mi alma.

Final:
Si hoy quieres hermano a Cristo acercarte
Y como el leproso ante el humillarte
//Jesús tus pecados puede perdonarte
Su Sangre Preciosa hoy puede limpiarte//"""
        ),
        Hymn(
            id = 419,
            title = "A la casa de Tres Amigos",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
A la casa de tres amigos, una noche el Maestro fue a cenar
Compartiendo allí con ellos palabras de vida, de amor y paz.
Marta era la que servía, Lázaro escuchaba con atención
//Mas María tomó una libra de perfume de nardo puro
Y los pies de Jesús ungió//

Coro:
La fragancia de aquel perfume
A toda la casa llenó
Y por esa grata ofrenda el Señor se regocijó.
“Déjenla, porque buena obra
Ha hecho Conmigo” dijo el Señor;
//Mas yo quiero hacer lo mismo
Hoy con esta humilde alabanza,
Entregarle mi adoración//

Recitado:
Mi Amado descendió a Su huerto, a la era de las especias, para apacentar
a los huertos y para recoger los lirios.

II
Marta es la que representa el servicio en la Casa del Señor,
El silencio y la paz de Lázaro significa en la Iglesia la comunión,
Es María la entrega plena de la Iglesia a Cristo en adoración
//El perfume es la alabanza con un corazón quebrantado,
Humillado ante el Señor//"""
        ),
        Hymn(
            id = 420,
            title = "Belén de Judá",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """I
Belén, Belén de Judá
Ya no eres más pequeña,
Pequeña no eres más.
Belén, Belén de Judá
De ti saldrá Un Guiador
Que estará en Mi pueblo, lo apacentará.

Coro:
Pudiste ver a Su estrella brillar
Y escuchar el anuncio angelical,
Pudiste oír a la gran multitud
De las huestes del cielo que alababan a Dios,
Que decían: “Gloria a Dios”.
Belén, Belén de Judá
Ya no eres más pequeña,
Pequeña no eres más
//Porque en ti ha nacido
Jesús el Rey del cielo,
El Príncipe de Paz//

II
Belén, Belén de Judá
En humilde pesebre
Pudiste acunar a Cristo el Salvador,
Al Niño Admirable,
Dios Fuerte y Consejero, al Príncipe de Paz."""
        ),
        Hymn(
            id = 421,
            title = "Alabad a Dios",
            link = "",
            author = "Conjunto Central de Tucumán",
            content = """Alabad a Dios en su santuario,
Alabadle en la Magnificencia de Su firmamento,
Alabadle por Sus proezas,
//Alabadle conforme a la muchedumbre
De Su Grandeza//
//Alabadle a son de bocina,
Alabadle con salterio y arpa,
Alabadle con pandero y danza,
Alabadle con cuerdas y flautas,
Alabadle con címbalos resonantes,
Alabadle con címbalos de júbilo,
//Alabadle, Alabadle a Dios//
Todo lo que respira Alabe a Dios,
Todo lo que respira Alabe al Señor.
//Alabadle, Alabadle a Dios//
Aleluya, Aleluya, Amén."""
        ),
        Hymn(
            id = 422,
            title = "Yo te Pido",
            link = "",
            author = "Conjunto de Salta",
            content = """I
Yo te pido Señor de los cielos
Que bendigas a todos aquellos
Que llevando van por este mundo
//El mensaje de la Salvación//
Tu Palabra lo dice bien claro
Que todos aquellos de limpio corazón
Recompensa tendrán en los cielos
//Si luchando por Tu causa van//

Coro:
Ya los tiempos están avisando
Que Tu venida se acerca Señor,
Por favor mi Señor yo te pido
//Que los guardes por siempre del mal//

II
Fortalece Señor a los fieles
Que limpiaron su vida con Tu Sangre,
Yo los amo con amor eterno
//Ellos son mis hermanos Señor//"""
        ),
        Hymn(
            id = 423,
            title = "Yo Quiero estar Allá",
            link = "",
            author = "Conjunto de Salta",
            content = """I
Cuando venga el Señor de los cielos
A la tierra Su iglesia a buscar
//Los que aquí siempre le fueron fieles
Por los siglos irán a gozar//

Coro:
Yo quiero estar allá,
Quiero con Cristo Reinar
Y para siempre gozar
//De su inmenso amor sin igual//

II
Ya se cumplen las Santas Palabras
Que el Señor Jesucristo dejo
//Se escuchan rumores de guerra
Y el hambre y la ciencia aumento//

III
Se levantan hoy falsos profetas,
La maldad ya se multiplicó
//Mas el que hasta el fin persevere
Vida eterna in los cielos tendrá//"""
        ),
        Hymn(
            id = 424,
            title = "Oh Gloria Aleluya",
            link = "",
            author = "Conjunto de Salta",
            content = """I
//Inmaculado es el Cordero,
Es el Cordero, el Cordero de Dios//
El es el Santo, Él es sin manchas,
Él es el Cristo por eso es el Señor.

Coro:
///Oh Gloria Aleluya///
Al Bendito Cordero de Dios
Que Dios el Padre Jehová nos lego.

Recitado:
A Ti, a ti Señor, el Santo de Israel. Admirable, Consejero, y Príncipe,
Príncipe de paz.

II
//Coronas de oro luce su frente blanca
Si su mano el levanta, la tierra es un temblor//
El es el Santo, Él es sin manchas,
Él es el Cristo por eso es el Señor."""
        ),
        Hymn(
            id = 425,
            title = "Yo contigo Estoy",
            link = "",
            author = "Conjunto de Salta",
            content = """I
Salió Jacob de Beerseba y fue camino hacia Canaán,
Cuando el día declinaba en un lugar quiso descansar
Entonces muy fatigado se acostó y allí durmió
Y soñó, he aquí:
Entre el cielo y la tierra una escalera estaba en pie
Y en lo alto estaba Dios
El Cual le hablo y le dijo así:

Coro I:
“Yo Soy el Dios de Abraham,
De tu padre Isaac,
La tierra donde estas te la daré a ti”.
“Yo contigo estoy, nunca te dejaré
Donde quiera que vas
Siempre te guardaré”.

Recitado:
Y despertando, despertando Jacob de su sueño, se levanto de mañana y
tomo la piedra que había puesto de cabecera y la alzo por señal diciendo:
“Ciertamente Jehová esta en este lugar y yo no lo sabía; cuan terrible es
ete lugar, no es otra cosa que Casa de Dios y puerta, puerta del cielo”.

II
Hermano si muy cansado en el camino te encuentras tu
El día va declinando y ya la noche llegando esta
Y ha llegado a tu vida un profundo sueño espiritual.
He aquí el Señor espera con brazos tiernos
Y su descanso te quiere dar;
Escucha su voz de amor
Que en la dura prueba te dice así:

Coro II:
//“Yo contigo estoy, nunca te dejaré
Donde quiera que vas
Siempre te guardaré”//"""
        ),
        Hymn(
            id = 426,
            title = "Yo te Canto mi Jesús",
            link = "",
            author = "Conjunto de Salta",
            content = """I
Hoy muy triste y agobiado he llegado oh Señor
Con dolor en mi corazón.
Pero tú con amor me esperabas al entrar
Fue tu paz que quitó mi aflicción
Y alegre yo te canto y elevo mi canción
Hasta el trono donde estas oh Señor.

Coro:
Solo hay en mi alma una inmensa felicidad
Y un deseo de alabarte oh Señor.
Yo te canto mi Jesús con todo mi corazón
La canción que en tu amor me diste hoy.

II
Si muy triste y agobiado has llegado ante Dios
Con dolor en tu corazón;
Deja hoy tus tristezas y pesares a sus pies
Y escucha Su Palabra de amor;
El te dice: “Ven a Mi si cargado tu estas”
Solo en él dulce paz hallarás."""
        ),
        Hymn(
            id = 427,
            title = "Hosanna",
            link = "",
            author = "Conjunto de Salta",
            content = """Presentación:
Cuando llegaban ya cerca de la bajada del monte de los Olivos, toda la
multitud de los discípulos gozándose comenzó a alabar a Dios a grandes
voces por todas las maravillas que habían visto diciendo: “Bendito el Rey
que viene en el Nombre del Señor, paz en el cielo y Gloria, Gloria en las
alturas”.

I
Mantos y palmas desplegando van
Alegre el pueblo de Jerusalén
Ya a lo lejos se comienza a ver
A Jesucristo que llegando esta.

Coro:
Y mientras mil voces resuenan por allí
“Hosanna al que viene en el Nombre del Señor”
Y como un estruendo de grata aclamación
Prorrumpen en voz Triunfal:
//“Hosanna, Hosanna al Rey//

II
Como a la entrada de Jerusalén
Un día nosotros vamos a cantar
A Jesucristo que vendrá otra vez
Para llevarnos a Su eterno hogar."""
        ),
        Hymn(
            id = 428,
            title = "Debería ser yo",
            link = "",
            author = "Conjunto de Salta",
            content = """I
A veces paro a pensar en la cruz,
In lo que hizo mi amado Jesús,
En cuantas veces me detuve en el camino
Y hasta pensé en abandonar mi cruz.
Y olvido la corona de espinas
Y la esponja con vinagre que le dieron a beber
Que siendo injustamente acusado
Podría haber renunciado más lo hizo por mí.

Coro:
Debería ser yo mas Jesús lo prefirió
Y al calvario subió, el culpable soy yo
Pero él lo asumió.
Debería ser yo mas él no claudicó
Y paso a paso la llevó, mi cruz él sufrió,
Por mí allí murió.

II
Yo suelo oír a aquel soldado diciendo:
“¿Sera que es el Rey de los judíos?”
Él le responde así: “tu lo has dicho”,
La tierra entonces se estremeció.
A veces, cuando estoy atribulado
Olvido los latigazos que le dieron a Jesús,
No, yo no soportaría sufrir tanto así
Más él lo resistió."""
        ),
        Hymn(
            id = 429,
            title = "Por un Camino",
            link = "",
            author = "Conjunto de Salta",
            content = """I
Por un camino de inmenso dolor
Muy maltratado va el Hijo de Dios
Sobre sus hombros cargando una cruz
Siendo Inocente sus labios no abrió;
Gotas de Sangre su rostro cubrió
Por las espinas que el hombre tejió
Burlas, desprecio y golpes sufrió
Más de sus ojos fluía el amor.

Coro I:
¡Oh que dolor, oh que dolor!
//Cuando sufría por mí el Salvador//

Recitado:
Solo, solo y triste a la cumbre llegó, allí, allí crucificaron al Hijo de Dios en
sus vestidos, en sus vestidos suertes se hecho, la profecía así se cumplió
entre ladrones colgado quedó; “Rey de los judíos” allí se escribió, el sol
radiante perdió su esplendor y densas tinieblas la tierra, la tierra cubrió.

II
Muy de mañana vinieron a ver
Unas mujeres la tumba del Rey
Mas ese día la tierra tembló
Porque del cielo un ángel llegó;
Con vestiduras de gran resplandor
Habló y les dijo: “no tengan temor”
“Sé que buscáis a Jesús el Señor
El no está aquí pues la muerte Venció”.

Coro II:
¡Resucitó, Resucitó!
//Y a la diestra del Padre se sentó//
¡Resucitó, Aleluya!"""
        )
    )
}
