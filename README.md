# EinsatzBridge

[![Issues](https://img.shields.io/github/issues/Finn0811/EinsatzBridge?style=flat-square)](https://img.shields.io/github/issues/Finn0811/EinsatzBridge?style=flat-square)
[![Issues](https://img.shields.io/github/stars/Finn0811/EinsatzBridge)](https://img.shields.io/github/stars/Finn0811/EinsatzBridge)

Diese simple Java-Anwendung empfängt Einsätze als json Objekt über ein Websocket-Event und sendet sie an den [Alamos FE2-Server](https://www.alamos-gmbh.com/service/fe2/).

## :rocket: Funktionsweise
### Websocket-Event
Folgenden Aufbau muss das json Objekt haben:

```
{
    "type": "new_einsatz",
    "einsatz": {
        "id": 1,
        "stichwort": "F1",
        "description": "Brennt XYZ",
        "adresse": "Ort, Ortsteil, Straße Hausnummer",
        "objekt": "",
        "alarmzeit": "",
        "alarmzeit_seconds": "",
        "einheiten": [
            {
                "id": 1,
                "name": "Testeinheit"
            },
            {
                "id": 2,
                "name": "Einheit2"
            }
        ],
        "zusatzinfos": [
            {
                "id": 1,
                "name": "Einsatzort",
                "value": "Zusatzinfo"
            }
        ]
    }
}
```
An den Alarmeingang [Externe Schnittstelle](https://alamos-support.atlassian.net/wiki/spaces/documentation/pages/219480366/Externe+Schnittstelle) des FE2-Servers wird folgendes Objekt übergeben:

```
{
    "message": "Einsatzalarm FF-Beispiel",
    "address": "ff-beispiel",
    "type": "ALARM",
    "sender": "EinsatzBridge",
    "data": {
        "keyword": "F1",
        "keyword_description": "Brennt XYZ",
        "keyword_category": "🔥",
        "keyword_color": "#ff0000",
        "street": "Ort, Ortsteil, Straße Hausnummer",
        "building": "",
        "vehicles": "Testeinheit\nEinheit2"
   }
}
```

Das Emoji (`keyword_category`) und die Farbe (`keyword_color`) wird automatisch in Abhängigkeit vom Stichwort und der dazugehörigen Beschreibung erkannt.
Dies kann in der [Konfigurationsdatei](https://github.com/Finn0811/EinsatzBridge/blob/master/src/main/resources/config.yml) angepasst werden.

Die alarmierte Einheit kann auch in der [Konfigurationsdatei](https://github.com/Finn0811/EinsatzBridge/blob/master/src/main/resources/config.yml) angepasst werden. (Auch dynamisch).