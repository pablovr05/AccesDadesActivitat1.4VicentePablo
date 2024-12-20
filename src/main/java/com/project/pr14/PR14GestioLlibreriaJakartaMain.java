package com.project.pr14;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.objectes.Llibre;

/**
 * Classe principal que gestiona la lectura i el processament de fitxers JSON per obtenir dades de llibres.
 */
public class PR14GestioLlibreriaJakartaMain {

    private final File dataFile;

    /**
     * Constructor de la classe PR14GestioLlibreriaJSONPMain.
     *
     * @param dataFile Fitxer on es troben els llibres.
     */
    public PR14GestioLlibreriaJakartaMain(File dataFile) {
        this.dataFile = dataFile;
    }

    public static void main(String[] args) {
        File dataFile = new File(System.getProperty("user.dir"), "data/pr14" + File.separator + "llibres_input.json");
        PR14GestioLlibreriaJakartaMain app = new PR14GestioLlibreriaJakartaMain(dataFile);
        app.processarFitxer();
    }

    /**
     * Processa el fitxer JSON per carregar, modificar, afegir, esborrar i guardar les dades dels llibres.
     */
    public void processarFitxer() {
        List<Llibre> llibres = carregarLlibres();
        if (llibres != null) {
            modificarAnyPublicacio(llibres, 1, 1995);
            afegirNouLlibre(llibres, new Llibre(4, "Històries de la ciutat", "Miquel Soler", 2022));
            esborrarLlibre(llibres, 2);
            guardarLlibres(llibres);
        }
    }

    /**
     * Carrega els llibres des del fitxer JSON.
     *
     * @return Llista de llibres o null si hi ha hagut un error en la lectura.
     */
    public List<Llibre> carregarLlibres() {
        // *************** CODI PRÀCTICA **********************/
        List<Llibre> llibres = new ArrayList<Llibre>();
        ObjectMapper op = new ObjectMapper();
        try {
            JsonNode jsonNode = op.readTree(dataFile);
            for (int i = 0; i < jsonNode.size(); i++) {
                int id = jsonNode.get(i).get("id").asInt();
                String titol = jsonNode.get(i).get("titol").asText();
                String autor = jsonNode.get(i).get("autor").asText();
                int any = jsonNode.get(i).get("any").asInt();
                Llibre llibre = new Llibre(id, titol, autor, any);
                llibres.add(llibre);
            }
            return llibres;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null; // Substitueix pel teu
    }

    /**
     * Modifica l'any de publicació d'un llibre amb un id específic.
     *
     * @param llibres Llista de llibres.
     * @param id Identificador del llibre a modificar.
     * @param nouAny Nou any de publicació.
     */
    public void modificarAnyPublicacio(List<Llibre> llibres, int id, int nouAny) {
        // *************** CODI PRÀCTICA **********************/
        for (Llibre llibre : llibres) {
            if (llibre.getId() == id) {
                llibre.setAny(nouAny);
                break;
            }
        }
    }

    /**
     * Afegeix un nou llibre a la llista de llibres.
     *
     * @param llibres Llista de llibres.
     * @param nouLlibre Nou llibre a afegir.
     */
    public void afegirNouLlibre(List<Llibre> llibres, Llibre nouLlibre) {
        // *************** CODI PRÀCTICA **********************/
        llibres.add(nouLlibre);
    }

    /**
     * Esborra un llibre amb un id específic de la llista de llibres.
     *
     * @param llibres Llista de llibres.
     * @param id Identificador del llibre a esborrar.
     */
    public void esborrarLlibre(List<Llibre> llibres, int id) {
        // *************** CODI PRÀCTICA **********************/
        for (Llibre llibre : llibres) {
            if (llibre.getId() == id) {
                llibres.remove(llibre);
                break;
            }
        }
    }

    /**
     * Guarda la llista de llibres en un fitxer nou.
     *
     * @param llibres Llista de llibres a guardar.
     */
    public void guardarLlibres(List<Llibre> llibres) {
        // Crear un ObjectMapper per a gestionar la serialització del JSON
        ObjectMapper objectMapper = new ObjectMapper();
    
        // Crear un nou fitxer amb el nom especificat
        File newFile = new File(dataFile.getParent(), "llibres_output_jakarta.json");
    
        try {
            // Escrivim la llista de llibres com a JSON al nou fitxer especificat
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(newFile, llibres);
            System.out.println("Llibres guardats correctament al fitxer " + newFile.getPath());
        } catch (IOException e) {
            System.out.println("Error en guardar els llibres al fitxer JSON");
            e.printStackTrace();
        }
    }
}