# Notes API — AutoLoc (Atelier 5)

## A. Tableau des Endpoints

J'ai fait un tableau global pour résumer. Le préfixe /api est géré dans le `application.properties`, donc dans les contrôleurs on met juste `/agences`, `/clients`, etc.

| Contrôleur | Verbe + route | Méthode Service | Succès | Erreurs |
|---|---|---|---|---|
| AgenceController | GET `/agences` | findAll() | 200 | — |
| AgenceController | GET `/agences/{id}` | findById(id) | 200 | 404 |
| AgenceController | POST `/agences` | create(agence) | 201 | 400 |
| AgenceController | PUT `/agences/{id}` | update(id, agence) | 200 | 404, 400 |
| AgenceController | DELETE `/agences/{id}` | deleteById(id) | 204 | 404 |
| ClientController | GET `/clients` | findAll() | 200 | — |
| ClientController | GET `/clients/{id}` | findById(id) | 200 | 404 |
| ClientController | POST `/clients` | create(client) | 201 | 400 |
| ClientController | PUT `/clients/{id}` | update(id, client) | 200 | 404, 400 |
| ClientController | DELETE `/clients/{id}` | deleteById(id) | 204 | 404 |
| EmployeController | GET `/employes` | findAll() | 200 | — |
| EmployeController | GET `/employes/{id}` | findById(id) | 200 | 404 |
| EmployeController | POST `/employes` | create(employe) | 201 | 400 |
| EmployeController | PUT `/employes/{id}` | update(id, employe) | 200 | 404, 400 |
| EmployeController | DELETE `/employes/{id}` | deleteById(id) | 204 | 404 |
| VehiculeController | GET `/vehicules` | findAll() | 200 | — |
| VehiculeController | GET `/vehicules/{id}` | findById(id) | 200 | 404 |
| VehiculeController | POST `/vehicules` | create(vehicule) | 201 | 400 |
| VehiculeController | PUT `/vehicules/{id}` | update(id, vehicule) | 200 | 404, 400 |
| VehiculeController | DELETE `/vehicules/{id}` | deleteById(id) | 204 | 404 |
| EquipementController | GET `/equipements` | findAll() | 200 | — |
| EquipementController | GET `/equipements/{id}` | findById(id) | 200 | 404 |
| EquipementController | POST `/equipements` | create(equipement) | 201 | 400 |
| EquipementController | PUT `/equipements/{id}` | update(id, equipement) | 200 | 404, 400 |
| EquipementController | DELETE `/equipements/{id}` | deleteById(id) | 204 | 404 |
| ContratController | GET `/contrats` | findAll() | 200 | — |
| ContratController | GET `/contrats/{id}` | findById(id) | 200 | 404 |
| ContratController | POST `/contrats` | create(contrat) | 201 | 400 |
| ContratController | PUT `/contrats/{id}` | update(id, contrat) | 200 | 404, 400 |
| ContratController | DELETE `/contrats/{id}` | deleteById(id) | 204 | 404 |
| PaiementController | GET `/paiements` | findAll() | 200 | — |
| PaiementController | GET `/paiements/{id}` | findById(id) | 200 | 404 |
| PaiementController | POST `/paiements` | *Non exposé* | — | 405 |
| ReservationController | GET `/reservations` | findAll() | 200 | — |
| ReservationController | GET `/reservations/{id}` | findById(id) | 200 | 404 |
| ReservationController | POST `/reservations` | create(reservation) | 201 | 400 |
| ReservationController | PUT `/reservations/{id}` | update(id, reservation) | 200 | 404, 400 |
| ReservationController | DELETE `/reservations/{id}` | deleteById(id) | 204 | 404 |
| MaintenanceController | GET `/maintenances` | findAll() | 200 | — |
| MaintenanceController | GET `/maintenances/{id}` | findById(id) | 200 | 404 |
| MaintenanceController | POST `/maintenances` | create(maintenance) | 201 | 400 |
| MaintenanceController | PUT `/maintenances/{id}` | update(id, maintenance) | 200 | 404, 400 |
| MaintenanceController | DELETE `/maintenances/{id}` | deleteById(id) | 204 | 404 |

## B. Annotations Jackson (Le casse-tête des boucles infinies)

J'ai eu pas mal d'erreurs `StackOverflow` au début à cause des relations bidirectionnelles. Voici ce que j'ai appliqué :

| Relation | Annotation | Pourquoi j'ai fait ça |
| :--- | :--- | :--- |
| `Contrat.paiements` et `Paiement.contrat` | `@JsonManagedReference` sur la liste + `@JsonBackReference` sur le contrat dans Paiement | C'est une **composition**. Un paiement n'existe pas sans contrat. On sérialise la liste des paiements dans le Contrat, mais on ignore le contrat dans le Paiement pour casser la boucle. |
| `Agence.employes` / `Agence.vehicules` | `@JsonIgnore` | L'employé et le véhicule sont créés seuls (on passe juste `{"agence": {"idAgence": 1}}`). Donc quand on affiche une agence, pas besoin de renvoyer tous ses employés dans le JSON de l'employé. |
| `Vehicule.reservations` et `maintenances` | `@JsonIgnore` | Pareil, les réservations et maintenances sont créées avec l'ID du véhicule, pas besoin de les sérialiser depuis le véhicule. |
| `Contrat.reservation` | `@JsonIgnore` | C'est le côté inverse du `@OneToOne`. La clé étrangère est dans `Reservation`, donc on l'ignore ici. |

**La différence entre `@JsonIgnore` et `@JsonBackReference` :**
- `@JsonIgnore` : Je supprime totalement le champ du JSON. Point final.
- `@JsonBackReference` : Je garde l'objet Java, mais je l'ignore *uniquement* lors de la transformation en JSON (la sérialisation) pour éviter de tourner en rond.

## C. Postman vs Swagger

- **Postman** : C'est ce qui m'a servi à vraiment tester les erreurs (les 400, 404). J'ai créé une collection avec les scripts `pm.test`. Par exemple, pour vérifier qu'un POST avec un ID existant renvoie bien un 400, ou qu'un DELETE sur un ID inconnu renvoie 404.
- **Swagger UI** (sur `/api/swagger-ui.html`) : Ça, c'est magique. SpringDoc a généré toute la doc tout seul. Ça m'a permis de tester rapidement un `GET /agences/9999` pour voir la fameuse erreur 404 en JSON (`"message": "Agence introuvable avec l'identifiant 1"`) sans ouvrir Postman.

## D. Anomalies SonarQube

En lançant l'analyse sur le package `web.controller`, Sonar m'a remonté deux choses :

1. **`System.out.println` dans les tests** : C'est une mauvaise pratique. J'ai laissé ça dans `TpAutolocApplicationTests` pour voir les résultats dans la console, mais en prod, il faudrait utiliser un `Logger`.
2. **Exposition des entités JPA** : Sonar m'a prévenu que renvoyer directement `Agence` ou `Contrat` dans le contrôleur, c'est risqué. Ça lie l'API à la base de données. Si on renomme une colonne en base, on casse l'API. La correction viendra avec les DTO (prévu pour la suite du cours).