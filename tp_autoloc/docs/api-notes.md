# Notes API — AutoLoc (Atelier 5)

## A. Endpoints

| Contrôleur | Verbe + route | Service appelé | Succès | Erreurs |
|---|---|---|---|---|
| AgenceController | GET `/api/agences` | `IAgenceService.findAll()` | 200 | — |
| AgenceController | GET `/api/agences/{id}` | `IAgenceService.findById(id)` | 200 | 404 |
| AgenceController | POST `/api/agences` | `IAgenceService.create(agence)` | 201 | 400 |
| AgenceController | PUT `/api/agences/{id}` | `IAgenceService.update(id, agence)` | 200 | 404, 400 |
| AgenceController | DELETE `/api/agences/{id}` | `IAgenceService.deleteById(id)` | 204 | 404 |
| ClientController | GET `/api/clients` | `IClientService.findAll()` | 200 | — |
| ClientController | GET `/api/clients/{id}` | `IClientService.findById(id)` | 200 | 404 |
| ClientController | POST `/api/clients` | `IClientService.create(client)` | 201 | 400 |
| ClientController | PUT `/api/clients/{id}` | `IClientService.update(id, client)` | 200 | 404, 400 |
| ClientController | DELETE `/api/clients/{id}` | `IClientService.deleteById(id)` | 204 | 404 |
| EmployeController | GET `/api/employes` | `IEmployeService.findAll()` | 200 | — |
| EmployeController | GET `/api/employes/{id}` | `IEmployeService.findById(id)` | 200 | 404 |
| EmployeController | POST `/api/employes` | `IEmployeService.create(employe)` | 201 | 400 |
| EmployeController | PUT `/api/employes/{id}` | `IEmployeService.update(id, employe)` | 200 | 404, 400 |
| EmployeController | DELETE `/api/employes/{id}` | `IEmployeService.deleteById(id)` | 204 | 404 |
| VehiculeController | GET `/api/vehicules` | `IVehiculeService.findAll()` | 200 | — |
| VehiculeController | GET `/api/vehicules/{id}` | `IVehiculeService.findById(id)` | 200 | 404 |
| VehiculeController | POST `/api/vehicules` | `IVehiculeService.create(vehicule)` | 201 | 400 |
| VehiculeController | PUT `/api/vehicules/{id}` | `IVehiculeService.update(id, vehicule)` | 200 | 404, 400 |
| VehiculeController | DELETE `/api/vehicules/{id}` | `IVehiculeService.deleteById(id)` | 204 | 404 |
| EquipementController | GET `/api/equipements` | `IEquipementService.findAll()` | 200 | — |
| EquipementController | GET `/api/equipements/{id}` | `IEquipementService.findById(id)` | 200 | 404 |
| EquipementController | POST `/api/equipements` | `IEquipementService.create(equipement)` | 201 | 400 |
| EquipementController | PUT `/api/equipements/{id}` | `IEquipementService.update(id, equipement)` | 200 | 404, 400 |
| EquipementController | DELETE `/api/equipements/{id}` | `IEquipementService.deleteById(id)` | 204 | 404 |
| ContratController | GET `/api/contrats` | `IContratService.findAll()` | 200 | — |
| ContratController | GET `/api/contrats/{id}` | `IContratService.findById(id)` | 200 | 404 |
| ContratController | POST `/api/contrats` | `IContratService.create(contrat)` | 201 | 400 |
| ContratController | PUT `/api/contrats/{id}` | `IContratService.update(id, contrat)` | 200 | 404, 400 |
| ContratController | DELETE `/api/contrats/{id}` | `IContratService.deleteById(id)` | 204 | 404 |
| PaiementController | GET `/api/paiements` | `IPaiementService.findAll()` | 200 | — |
| PaiementController | GET `/api/paiements/{id}` | `IPaiementService.findById(id)` | 200 | 404 |
| PaiementController | POST `/api/paiements` | *(non exposé)* | — | 405 |
| ReservationController | GET `/api/reservations` | `IReservationService.findAll()` | 200 | — |
| ReservationController | GET `/api/reservations/{id}` | `IReservationService.findById(id)` | 200 | 404 |
| ReservationController | POST `/api/reservations` | `IReservationService.create(reservation)` | 201 | 400 |
| ReservationController | PUT `/api/reservations/{id}` | `IReservationService.update(id, reservation)` | 200 | 404, 400 |
| ReservationController | DELETE `/api/reservations/{id}` | `IReservationService.deleteById(id)` | 204 | 404 |
| MaintenanceController | GET `/api/maintenances` | `IMaintenanceService.findAll()` | 200 | — |
| MaintenanceController | GET `/api/maintenances/{id}` | `IMaintenanceService.findById(id)` | 200 | 404 |
| MaintenanceController | POST `/api/maintenances` | `IMaintenanceService.create(maintenance)` | 201 | 400 |
| MaintenanceController | PUT `/api/maintenances/{id}` | `IMaintenanceService.update(id, maintenance)` | 200 | 404, 400 |
| MaintenanceController | DELETE `/api/maintenances/{id}` | `IMaintenanceService.deleteById(id)` | 204 | 404 |

## B. Annotations Jackson

| Association | Annotation retenue | Justification |
|---|---|---|
| `Contrat.paiements` ↔ `Paiement.contrat` | `@JsonManagedReference("contrat-paiements")` sur la liste + `@JsonBackReference("contrat-paiements")` sur l'enfant | **Composition** : un paiement n'a pas de sens hors de son contrat ; il est créé dans le JSON du parent (cascade `ALL`, `orphanRemoval = true`). Le côté enfant est masqué à la sérialisation pour couper la boucle. |
| `Agence.employes`, `Agence.vehicules` | `@JsonIgnore` côté inverse | L'enfant est créé **seul** avec `{"agence": {"idAgence": 1}}`. Le parent ne doit pas être renvoyé dans le JSON de l'enfant pour éviter la boucle. |
| `Client.reservations` | `@JsonIgnore` côté inverse | Idem : `Reservation` est créée seule avec `{"client": {"idClient": 1}}`. |
| `Vehicule.reservations`, `Vehicule.maintenances` | `@JsonIgnore` côté inverse | Idem : ces enfants sont créés seuls. |
| `Equipement.vehicules` | `@JsonIgnore` côté inverse | Côté propriétaire = `Vehicule.equipements` ; l'inverse doit être coupé. |
| `Contrat.reservation` | `@JsonIgnore` | Côté inverse du `@OneToOne` (la clé est dans `Reservation`). |

**`@JsonBackReference` vs `@JsonIgnore`** :
- `@JsonBackReference` : utilisée dans une **relation bidirectionnelle de composition** où l'enfant doit rester sérialisé côté parent mais pas côté enfant (coupe la boucle, conserve le lien).
- `@JsonIgnore` : exclut complètement le champ de la sérialisation ; utilisée quand le parent n'a aucune raison d'apparaître dans le JSON de l'enfant.

## C. Postman et Swagger

- **Postman** : outil de **test**. On envoie une requête et on vérifie le statut HTTP (`pm.test`). Exemple dans l'atelier : « Créer un contrat avec un identifiant » vérifie `Statut 400` ; « Créer un contrat avec ses paiements » vérifie `Statut 201` et mémorise l'identifiant.
- **Swagger UI** : outil de **documentation**. Accessible sur `/api/swagger-ui.html`, il génère la doc depuis les contrôleurs et permet un « Try it out ». Exemple : on a testé `GET /api/agences/1` et obtenu un **404** avec le message d'erreur JSON.

## D. Anomalies SonarQube

1. **`java:S106` — `System.out.println`** dans les tests. Correction : utiliser un logger.
2. **`java:S4684` — exposition directe des entités JPA** dans les contrôleurs. Correction (Chapitre 10) : introduire des DTO.

## E. Questions

1. **Quel composant reçoit toutes les requêtes ?** `DispatcherServlet`. Il consulte le `HandlerMapping` qui, à partir du verbe HTTP et de l'URL, identifie la méthode à invoquer.
2. **Pourquoi dépendre de `IContratService` et non du repository ?** Pour respecter la séparation des couches : le contrôleur ne fait que de la traduction HTTP. Dépendre de l'interface permet de changer d'implémentation et facilite les tests.
3. **Pourquoi POST = 201 et DELETE = 204 ?** 201 signale la création d'une ressource (avec son id). 204 signale une réussite sans corps de retour. Une ressource absente = problème client (404), pas une erreur serveur (500).
4. **Pourquoi PaiementController n'expose que des GET ?** Les paiements sont gérés via le Contrat (cascade). Un `POST /api/paiements` renvoie **405 Method Not Allowed**.
5. **Risques d'exposer les entités JPA ?** Couplage API/base, fuite de champs internes, boucles de sérialisation. `Contrat.paiements` utilise `@JsonManagedReference` car c'est une composition ; `Agence.vehicules` utilise `@JsonIgnore` car le véhicule est créé seul.