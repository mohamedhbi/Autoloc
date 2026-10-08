package tn.esprit.tp_autoloc;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tp_autoloc.domain.Agence;
import tn.esprit.tp_autoloc.domain.Client;
import tn.esprit.tp_autoloc.domain.Employe;
import tn.esprit.tp_autoloc.domain.Equipement;
import tn.esprit.tp_autoloc.domain.Maintenance;
import tn.esprit.tp_autoloc.domain.Vehicule;
import tn.esprit.tp_autoloc.domain.enums.CategorieVehicule;
import tn.esprit.tp_autoloc.domain.enums.RoleEmploye;
import tn.esprit.tp_autoloc.domain.enums.StatutVehicule;
import tn.esprit.tp_autoloc.exception.ResourceNotFoundException;
import tn.esprit.tp_autoloc.service.IAgenceService;
import tn.esprit.tp_autoloc.service.IClientService;
import tn.esprit.tp_autoloc.service.IEmployeService;
import tn.esprit.tp_autoloc.service.IEquipementService;
import tn.esprit.tp_autoloc.service.IMaintenanceService;
import tn.esprit.tp_autoloc.service.IVehiculeService;

@SpringBootTest
class TpAutolocApplicationTests {

    @Autowired
    private IAgenceService agenceService;
    @Autowired
    private IClientService clientService;
    @Autowired
    private IVehiculeService vehiculeService;
    @Autowired
    private IEmployeService employeService;
    @Autowired
    private IEquipementService equipementService;
    @Autowired
    private IMaintenanceService maintenanceService;


    @Test
    void testAgenceService() {
        Agence agence = new Agence();
        agence.setNom("Agence esprit");
        agence.setVille("Ariana");
        agence.setAdresse("Ghazela");
        agence.setTelephone("71000000");

        Agence saved = agenceService.create(agence);
        System.out.println("[AgenceService] create() = id " + saved.getIdAgence()
                + ", nom " + saved.getNom() + ", ville " + saved.getVille());
        assertNotNull(saved);
        assertNotNull(saved.getIdAgence());
        assertEquals("Agence esprit", saved.getNom());

        Long id = saved.getIdAgence();

        List<Agence> agences = agenceService.findAll();
        System.out.println("[AgenceService] findAll() = " + agences.stream()
                .map(a -> a.getIdAgence() + ":" + a.getNom()).toList());
        assertFalse(agences.isEmpty());

        Agence found = agenceService.findById(id);
        System.out.println("[AgenceService] findById(" + id + ") = " + found.getNom());
        assertNotNull(found);
        assertEquals(id, found.getIdAgence());

        int total = agenceService.findAll().size();
        System.out.println("[AgenceService] count (findAll().size()) = " + total);
        assertTrue(total >= 1);

        Agence modif = new Agence();
        modif.setNom("Agence Lac");
        modif.setVille("Tunis");
        modif.setAdresse("Les Berges du Lac");
        modif.setTelephone("71111111");
        Agence updated = agenceService.update(id, modif);
        System.out.println("[AgenceService] update() = " + updated.getNom() + " / " + updated.getVille());
        assertEquals(id, updated.getIdAgence());
        assertEquals("Agence Lac", updated.getNom());
        assertNotEquals("Agence esprit", updated.getNom());

        Agence avecId = new Agence();
        avecId.setIdAgence(999L);
        avecId.setNom("X");
        assertThrows(IllegalArgumentException.class, () -> agenceService.create(avecId));

        agenceService.deleteById(id);
        System.out.println("[AgenceService] deleteById(" + id + ") effectué");
        assertThrows(ResourceNotFoundException.class, () -> agenceService.findById(id));
        assertTrue(agenceService.findAll().stream().noneMatch(a -> id.equals(a.getIdAgence())));
    }

    // ================= Partie 3 : ClientService =================
    @Test
    void testClientService() {
        Client c1 = new Client();
        c1.setNom("Ben Ali");
        c1.setPrenom("Ahmed");
        c1.setEmail("ahmed.benali@mail.com");
        c1.setTelephone("20111111");
        c1.setNumPermis("PERMIS-001");
        c1.setDateInscription(LocalDate.now());

        Client c2 = new Client();
        c2.setNom("Trabelsi");
        c2.setPrenom("Sara");
        c2.setEmail("sara.trabelsi@mail.com");
        c2.setTelephone("20222222");
        c2.setNumPermis("PERMIS-002");
        c2.setDateInscription(LocalDate.now());

        Client saved1 = clientService.create(c1);
        Client saved2 = clientService.create(c2);
        System.out.println("[ClientService] create() 1 = id " + saved1.getIdClient() + ", " + saved1.getNom());
        System.out.println("[ClientService] create() 2 = id " + saved2.getIdClient() + ", " + saved2.getNom());
        assertNotNull(saved1.getIdClient());
        assertNotNull(saved2.getIdClient());
        assertNotEquals(saved1.getIdClient(), saved2.getIdClient());

        Long id1 = saved1.getIdClient();
        Long id2 = saved2.getIdClient();

        List<Client> clients = clientService.findAll();
        System.out.println("[ClientService] findAll() = " + clients.stream()
                .map(c -> c.getIdClient() + ":" + c.getNom()).toList());
        assertTrue(clients.size() >= 2);

        Client found = clientService.findById(id1);
        System.out.println("[ClientService] findById(" + id1 + ") = " + found.getNom() + " " + found.getPrenom());
        assertEquals("Ben Ali", found.getNom());

        int total = clientService.findAll().size();
        System.out.println("[ClientService] count (findAll().size()) = " + total);
        assertTrue(total >= 2);

        // update()
        Client modif = new Client();
        modif.setNom("Ben Ali");
        modif.setPrenom("Ahmed Amine");
        modif.setEmail("ahmed.benali@mail.com");
        modif.setTelephone("20333333");
        modif.setNumPermis("PERMIS-001");
        modif.setDateInscription(LocalDate.now());
        Client updated = clientService.update(id1, modif);
        System.out.println("[ClientService] update() = " + updated.getPrenom() + " / " + updated.getTelephone());
        assertEquals("Ahmed Amine", updated.getPrenom());
        assertEquals("20333333", updated.getTelephone());

        // validation : nom obligatoire
        Client invalide = new Client();
        invalide.setEmail("x@mail.com");
        assertThrows(IllegalArgumentException.class, () -> clientService.create(invalide));

        // Nettoyage + vérification
        clientService.deleteById(id1);
        clientService.deleteById(id2);
        System.out.println("[ClientService] deleteById() effectué pour " + id1 + " et " + id2);
        assertThrows(ResourceNotFoundException.class, () -> clientService.findById(id1));
        assertThrows(ResourceNotFoundException.class, () -> clientService.findById(id2));
    }

    // ================= Partie 4 : VehiculeService =================
    @Test
    void testVehiculeService() {
        Vehicule v = new Vehicule();
        v.setImmatriculation("123 TUN 4567");
        v.setMarque("Renault");
        v.setModele("Clio");
        v.setCategorie(CategorieVehicule.values()[0]);
        v.setTarifJournalier(new BigDecimal("90.00"));
        v.setStatut(StatutVehicule.values()[0]);

        Vehicule saved = vehiculeService.create(v);
        System.out.println("[VehiculeService] create() = id " + saved.getIdVehicule() + ", "
                + saved.getMarque() + " " + saved.getModele());
        assertNotNull(saved);
        assertNotNull(saved.getIdVehicule());
        assertEquals("Renault", saved.getMarque());

        Long id = saved.getIdVehicule();

        List<Vehicule> vehicules = vehiculeService.findAll();
        System.out.println("[VehiculeService] findAll() = " + vehicules.stream()
                .map(x -> x.getIdVehicule() + ":" + x.getMarque()).toList());
        assertFalse(vehicules.isEmpty());

        Vehicule found = vehiculeService.findById(id);
        System.out.println("[VehiculeService] findById(" + id + ") = " + found.getMarque() + " " + found.getModele());
        assertEquals(id, found.getIdVehicule());

        int total = vehiculeService.findAll().size();
        System.out.println("[VehiculeService] count (findAll().size()) = " + total);
        assertTrue(total >= 1);

        // update()
        Vehicule modif = new Vehicule();
        modif.setImmatriculation("123 TUN 4567");
        modif.setMarque("Peugeot");
        modif.setModele("208");
        modif.setCategorie(saved.getCategorie());
        modif.setTarifJournalier(new BigDecimal("110.00"));
        modif.setStatut(saved.getStatut());
        Vehicule updated = vehiculeService.update(id, modif);
        System.out.println("[VehiculeService] update() = Renault -> " + updated.getMarque());
        assertEquals("Peugeot", updated.getMarque());
        assertNotEquals("Renault", updated.getMarque());
        assertEquals(id, updated.getIdVehicule());

        // validation : tarif négatif refusé
        Vehicule negatif = new Vehicule();
        negatif.setImmatriculation("999 TUN 1");
        negatif.setTarifJournalier(new BigDecimal("-1"));
        assertThrows(IllegalArgumentException.class, () -> vehiculeService.create(negatif));

        vehiculeService.deleteById(id);
        System.out.println("[VehiculeService] deleteById(" + id + ") effectué");
        assertThrows(ResourceNotFoundException.class, () -> vehiculeService.findById(id));
    }

    // ================= Partie 5 : Employe =================
    @Test
    void testEmployeService() {
        Employe e = new Employe();
        e.setNom("Gharbi");
        e.setPrenom("Mohamed");
        e.setRole(RoleEmploye.values()[0]);

        Employe saved = employeService.create(e);
        System.out.println("[EmployeService] create() = id " + saved.getIdEmploye() + ", "
                + saved.getNom() + " " + saved.getPrenom() + ", role " + saved.getRole());
        assertNotNull(saved.getIdEmploye());

        Long id = saved.getIdEmploye();

        List<Employe> liste = employeService.findAll();
        System.out.println("[EmployeService] findAll() = " + liste.stream()
                .map(x -> x.getIdEmploye() + ":" + x.getNom()).toList());
        assertFalse(liste.isEmpty());

        Employe found = employeService.findById(id);
        System.out.println("[EmployeService] findById() = " + found.getNom());
        assertEquals("Gharbi", found.getNom());

        // "existsById" via le service
        boolean existe = liste.stream().anyMatch(x -> id.equals(x.getIdEmploye()));
        System.out.println("[EmployeService] existe = " + existe);
        assertTrue(existe);

        Employe modif = new Employe();
        modif.setNom("Gharbi");
        modif.setPrenom("Ali");
        modif.setRole(saved.getRole());
        Employe updated = employeService.update(id, modif);
        System.out.println("[EmployeService] update() = Mohamed -> " + updated.getPrenom());
        assertEquals("Ali", updated.getPrenom());

        employeService.deleteById(id);
        boolean apres = employeService.findAll().stream().anyMatch(x -> id.equals(x.getIdEmploye()));
        System.out.println("[EmployeService] existe après suppression = " + apres);
        assertFalse(apres);
        assertThrows(ResourceNotFoundException.class, () -> employeService.findById(id));
    }

    // ================= Partie 5 : Equipement =================
    @Test
    void testEquipementService() {
        Equipement e1 = new Equipement();
        e1.setLibelle("GPS");
        Equipement e2 = new Equipement();
        e2.setLibelle("Siège bébé");

        Equipement saved1 = equipementService.create(e1);
        Equipement saved2 = equipementService.create(e2);
        System.out.println("[EquipementService] create() 1 = id " + saved1.getIdEquipement() + ", " + saved1.getLibelle());
        System.out.println("[EquipementService] create() 2 = id " + saved2.getIdEquipement() + ", " + saved2.getLibelle());
        assertNotNull(saved1.getIdEquipement());
        assertNotEquals(saved1.getIdEquipement(), saved2.getIdEquipement());

        Long id = saved1.getIdEquipement();

        List<Equipement> liste = equipementService.findAll();
        System.out.println("[EquipementService] findAll() = " + liste.stream()
                .map(x -> x.getIdEquipement() + ":" + x.getLibelle()).toList());
        assertTrue(liste.size() >= 2);

        Equipement found = equipementService.findById(id);
        System.out.println("[EquipementService] findById() = " + found.getLibelle());
        assertEquals("GPS", found.getLibelle());

        equipementService.deleteById(id);
        System.out.println("[EquipementService] deleteById(" + id + ") effectué");
        assertThrows(ResourceNotFoundException.class, () -> equipementService.findById(id));

        // Nettoyage du 2e équipement
        equipementService.deleteById(saved2.getIdEquipement());
        assertThrows(ResourceNotFoundException.class,
                () -> equipementService.findById(saved2.getIdEquipement()));
    }

    // ================= Partie 5 : Maintenance =================
    @Test
    void testMaintenanceService() {
        Maintenance m = new Maintenance();
        m.setDescription("Vidange et contrôle des freins");

        Maintenance saved = maintenanceService.create(m);
        System.out.println("[MaintenanceService] create() = id " + saved.getIdMaintenance()
                + ", " + saved.getDescription());
        assertNotNull(saved.getIdMaintenance());

        Long id = saved.getIdMaintenance();

        List<Maintenance> liste = maintenanceService.findAll();
        System.out.println("[MaintenanceService] findAll() = " + liste.stream()
                .map(x -> x.getIdMaintenance() + ":" + x.getDescription()).toList());
        assertFalse(liste.isEmpty());

        Maintenance found = maintenanceService.findById(id);
        System.out.println("[MaintenanceService] findById() = " + found.getDescription());
        assertEquals("Vidange et contrôle des freins", found.getDescription());

        boolean existe = liste.stream().anyMatch(x -> id.equals(x.getIdMaintenance()));
        assertTrue(existe);

        maintenanceService.deleteById(id);
        boolean apres = maintenanceService.findAll().stream().anyMatch(x -> id.equals(x.getIdMaintenance()));
        System.out.println("[MaintenanceService] existe après suppression = " + apres);
        assertFalse(apres);
        assertThrows(ResourceNotFoundException.class, () -> maintenanceService.findById(id));
    }
}