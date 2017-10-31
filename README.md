# Badminton Tournament Platform (BTP)

Testen des Dropwizard Frameworks im Zuge meiner Projektarbeit.

# Starten des Beispielprojektes

* Das Git Projekt klonen / entpacken.
* In der Kommandozeile in das Root Verzeichnis (btp) navigieren und folgenden Maven Befehl ausführen

      mvn clean package
      
* Nach erfolgreichem Build hat man nun ein sogenanntes fat JAR, welches man mit folgendem Befehl starten kann

      java -jar target/matchmaking.jar server config.yml

  Der Parameter server wird von Dropwizard erkannt und sorgt dafür, dass die Anwendung als HTTP server läuft.
  Die config.yml ist eine optionale Konfigurationsdatei. (In diesem Fall wird sie jedoch benötigt)

* Jetzt sollte die Anwendung im Browser über folgenden Link erreichbar sein:

      http://localhost:8080 
      
# Hinweise zum Beispielprojekt

Das Backend wurde mit Dropwizard erstellt, das Frontend mit AngularJS und die API Dokumentation mit Swagger.
Warum ein fat JAR? Es hat den Vorteil, dass alle benötigten Abhängigkeiten bereits in der JAR enthalten sind und man dieses einfach deployen kann. Das JAR wird mit Hilfe des Maven Shade Plugins konfiguriert. 

# Über Dropwizard

Dropwizard ist ein Java Framework für RESTful Web Services. Es erlaubt ein sehr einfaches aufsetzen dieser Services (Lediglich die Maven dependencies müssen gesetzt werden). DW ist ein leichtgewichtiges Framework, welches sehr resourcenschonend ist.
DW bietet ein gebundeltes Gesamtpaket an, was den Vorteil hat, dass vieles bereits in der richtigen Version vorliegt und man dadurch nicht alles selbst konfigurieren muss und was mit welcher Version kompatibel ist.
Dabei verwendet DW unter anderem folgende Frameworks:

* Jetty als HTTP Server
* Darauf läuft Jersey, der die REST Schnittstellen einbindet
* Jackson für die JSON Transformation (Mapping von JSON Objekte auf Java Modelle um mit POJOs arbeiten zu können)
* Hibernate für die Datenbank

Erweiterungen sind unter anderem:

* Metrics (Statistiken - wie viel Zugriffe pro Sekunde?, Helthchecks - Ist die Datenbank online? Service überlastet?)
* Logging mit SLF4J und LOGBACK

# Aufbau der Klassen am Beispiel Team

    Team.java
         
  Das POJO Object mit den getter/setter-Methoden. Falls die Datenbank nicht durch ein Migrationsskript erzeugt werden soll, dann müssen die Annotationen @Entity auf Klassenebene, bzw. @Column auf den Attributen aus dem Package javax.persistence verwendet werden. Dadurch erfolgt ein automatisiertes OR-Mapping.
  
    TeamResource.java
    
  Ist die Resource, die konsumiert wird bzw. das JSON produziert. Wichtig ist hier die Klassenannotation @Path um den Pfad zur Resource festzulegen. Produces/Consumes gibt an, in welchem Format die Requests/Responses vorliegen sollen (xml/JSON/text/..).
  HTTP requests wie GET POST PUT DELETE sind als Annotation an einzelne Methoden gesetzt. Zusätzlich mit dem Response Rückgabetyp erhält man entpsrechende Status Codes (201, 400, ...).
  
    BTPApplication.java
  
  Bis jetzt weiß der Server noch nicht, dass Resource Klassen existieren. Das Programm würde so nicht funktionieren. Daher müssen die Resource Klassen der Anwendung bekannt gemacht werden. Dies geschieht in der BTPApplication Klass, die von Application erbt. Hier werden alle wichtigen Services registriert und Bundles initialisiert.
  
  * Zuerst wird ein HibernateBundle erzeugt.
  * In der initialize Methode fügt man dieses Bundle dann der bootstrap Konfiguration hinzu.
  * In der run Methode erzeugt man eine SessionFactory für das TeamDAO.
  * Zum Schluss fügt man der Environment die TeamResource hinzu.

Die Registrierung der Resourcen kann über ein GuiceBundle auch automatisiert werden, in dem man ein Package angibt, in dem alle Resourcen hinterlegt sind (auto discovery). Sieht in etwa so aus:

```java
   bootstrap.addBundle(GuiceBundle.builder()
   .enableAutoConfig("packagename")
   .modules(new GuiceModule())
   .build());
```

Zurück zu den Resource Klassen. Man kann hier die Pfade auch kombinieren.

