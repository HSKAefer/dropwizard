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

Das Backend wurde Dropwizard erstellt, das Frontend mit AngularJS und die API Dokumentation mit Swagger

# Über Dropwizard
