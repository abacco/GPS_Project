Sezione Learning/Training e Installazioni -----------------------------

Il progetto utilizza le seguenti tecnologie:
- Framework Quarkus (Red Hat)
    - Guida Ufficiale Quarkus :
        - https://quarkus.io/
    - Tutorial from scratch di come settare un progetto Quarkus:
        - https://quarkus.io/guides/getting-started

- Docker (Installare Docker Desktop:)
        - https://docs.docker.com/desktop/install/windows-install/

- MongoDB (installare MongoDB):
        - https://www.mongodb.com/docs/manual/tutorial/install-mongodb-on-windows/

 - Git
    - https://git-scm.com/downloads
    - https://gitforwindows.org/

- Soucetree
    - https://www.sourcetreeapp.com/
----------------------------------------------------------------------
Sezione Setup Progetto -----------------------------------------------
Una volta installati Docker e MongoDB:
- eseguire:
    - git clone https://github.com/abacco/GPS_Project.git (Clonazione Progetto -  richiesto solo una volta)
    - Avviare docker desktop:
        - da shell intellij digitare:
            - docker compose up (Viene avviato il container docker per l'uso di Mosquitto)
    - Avviare Mongodb Compass:
        - creare una nuova shell (anche da intellij) e digitare:
            - mongosh "mongodb://localhost:27017"
    - Avviare l'applicazione quarkus:
            - mvn quarkus:dev

Inserire in un browser: http://localhost:8080/MIO_ENDPOINT per visualizzare l'output
-----------------------------------------------------------------------
Sezione plus:
- Capiterà a volte che con lo shutdown di mongodb, ci sia ancora il relativo processo
in ascolto sulla porta scelta in fase si up & run, per killare un processo in ascolto
su di una determinata porta:
    - aprire il cmd (git o non git) e digitare:
        - netstat -ano | findstr :27017 (troviamo il processo in ascolto sulla porta x)
        - taskkill /PID 11692 /F (killiamo il processo con il relativo PID in maniera forzata "/F")
