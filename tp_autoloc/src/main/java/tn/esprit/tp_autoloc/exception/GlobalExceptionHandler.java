package tn.esprit.tp_autoloc.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erreurs = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                erreurs.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(erreurs);
    }

    // AJOUT : gérer les entités introuvables → 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, String> erreur = new HashMap<>();
        erreur.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erreur);
    }

    // AJOUT : gérer les IllegalStateException → 400
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalState(IllegalStateException ex) {
        Map<String, String> erreur = new HashMap<>();
        erreur.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(erreur);
    }

    // AJOUT : gérer les IllegalArgumentException → 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, String> erreur = new HashMap<>();
        erreur.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(erreur);
    }
}