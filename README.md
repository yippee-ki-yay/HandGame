# HandGame

                            -------------- Hand gesturing na android uređaju -----------------

Članovi tima:
Nenad Palinkašević Ra191/2012
Danilo Sekulić Ra140/2011

1. Motivacija
    Oduvek je postojala zelja kod ljudi za tehnologijama koje ce omoguciti interaktivnost, lakoću i "prirodnost" upotrebe tehničkih pronalazaka. Poslednjih godina izražen je trend korišćenja mobilnih platformi koje omogućavaju korisniku da ne bude vezan za jedno mesto pri korišćenju interneta i multimedijalnih sadržaja. Pojavom ekrana osetljivih na dodir smanjuje se potreba za robusnim tastaturama i omogućuje se lagano upravljanje uredjajem. Krećemo se ka sistemima virtuelne realnosti u kojima će pokret, uz glas, biti dominantan aspekt upravljanja. Pomeranjem očiju, glave, tela i udova izvršavaćemo većinu poslova koje trenutno obavljamo stacionarni, povezani sa nekim žicnim uređajem. Naša želja je razvoj sistema koji će omogućiti kontrolu pokretom ruke.

2. Osnovna ideja
	Pravljenje igrice na Android platformi gde je nacin upravljanja gestikulacija rukom, preciznije šakom. Ideja je da sa prednjom kamerom snimimo šaku korisnika i prepoznamo više različitih stanja (oblika šake) preko kojih će se u real time-u upravljati igricom. Akcenat je na prepoznavanju samih gestikulacija ruke i, za početak, detekcija nekoliko osnovnih "stanja". Kasnijim proširivanjem skupa oblika koji se prepoznaju bili bi dodavani novi nivoi u igricu sa  korišćenjem novog skupa. Nakon implementacije prepoznavanja oblika šake moguće je datu funkcionalnost primeniti i na neku drugu funkciju telefona poput odgovaranja na pozive, upravljanja reprodukcijom muzike, skrolovanja i sličnih operacija.
	Game play igre za sad nije specificiran jer zavisi od kvaliteta i brzine detekcije. Osnovna zamisao je da imamo igrača koji se kreće/izbegava/puca svoje protivnike/prepreke.
	
3. Dosadašnji rad na temu
	Samo prepoznavanje šake nije neistražena oblast i već postoje neke aplikacije koje to primenjuju na mob. uređajima kao npr. https://play.google.com/store/apps/details?id=com.MarksThinkTank.WaveControl. Međutim, ne postoji mnogo igrica koje pružaju ovaj nivo interaktivnosti.
	Postoje CV biblioteke koje pružaju funkcionalnosti koje su nama potrebne, što nam olakšava posao. Nije još odlučeno koja će se tehnika koristiti jer je potrebno detaljnije istraživanje. Neki od mogućih pristupa su: http://simena86.github.io/blog/2013/08/12/hand-tracking-and-recognition-with-opencv/ i eaglesky.github.io/blog/2014/03/27/color-based-hand-gesture-recognition-on-android/.

4. Problemi
	Glavni problem je prepoznati saku u odnosu na okolinu. Ekspozicija, autofokus i slaba rezolucija kamere i losi ambijentalni uslovi mogu otezati prepoznavanje kontura sake. Situacija da pripadnici razlicite rase koriste aplikaciju moze predstavljati problem ukoliko se prepoznavanje sake vrsi preko boje koze. 
	Ogranicenost resursa na mobilnim uredjajima namece potrebu za efiksanim algoritmom implementiranom na brzom programskom jeziku.
	
5. Tehnologije
	Android platforma 4+
	Java i ako je potrebno C++
	OpenCV i po potrebi neki game engine
	
6. Ciljevi
	a) Pomoću OpenCV biblioteke razviti prepoznavanje različitih položaja šake na android platformi.
        b) Razvitak igrice koja koristi implementiranu funkcionalnost (uz mogućnost kasnijeg objavljivanja na google play-u)


Korišćeno okruženje:
    ADT bundle for windows v22.6.2
    Android API nivo 19
    OpenCV 3.0.0 za android
    
Podešavanje okruženja:
    Pokrenuti ADT
    Importovati projekat HandGame
    Importovati OpenCV3.0.0 kao projekat
    Povezati OpenCV sa projektom HandGame tako što se u Properties delu HandGame projekta pod tabom Android čekira openCV biblioteka
    Isključiti opciju isLibrary ako je uključena
    Ukoliko se testira na mobilnom uređaju, u Run configurations -> Target odabrati Always prompt to pick device
    Ukoliko eclipse ne detektuje mobilni uređaj potrebno je instalirati driver-e za dati model uređaja
