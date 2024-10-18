package com.project.pr14;

import com.project.objectes.Llibre;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.lang.Exception;

/**
 * Classe principal que gestiona la lectura i el processament de fitxers JSON per obtenir dades de llibres.
 */
public class PR14GestioLlibreriaJacksonMain {

    private final File dataFile;

    /**
     * Constructor de la classe PR14GestioLlibreriaMain.
     *
     * @param dataFile Fitxer on es troben els llibres.
     */
    public PR14GestioLlibreriaJacksonMain(File dataFile) {
        this.dataFile = dataFile;
    }

    public static void main(String[] args) {
        File dataFile = new File(System.getProperty("user.dir"), "data/pr14" + File.separator + "llibres_input.json");
        PR14GestioLlibreriaJacksonMain app = new PR14GestioLlibreriaJacksonMain(dataFile);
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
            System.out.println(llibres);
            guardarLlibres(llibres, dataFile);
        }
    }

    /**
     * Carrega els llibres des del fitxer JSON.
     *
     * @return Llista de llibres o null si hi ha hagut un error en la lectura.
     */
    public List<Llibre> carregarLlibres() {
        // *************** CODI PRÀCTICA **********************/
        List<Llibre> llibres = new ArrayList<>(); // Creamos una lista vacía de Llibres
        try (JsonReader jsonReader = Json.createReader(new FileReader(dataFile))) {
            // Leemos el archivo JSON y obtenemos directamente el JsonArray
            JsonArray llibresArray = jsonReader.readArray();
            // Recorremos el array de llibres y creamos objetos Llibre
            for (int i = 0; i < llibresArray.size(); i++) {
                JsonObject llibreObject = llibresArray.getJsonObject(i);
                // Extraemos los campos del libro
                int id = llibreObject.getInt("id");
                String titol = llibreObject.getString("titol");
                String autor = llibreObject.getString("autor");
                int any = llibreObject.getInt("any");
                // Creamos un nuevo objeto Llibre y lo añadimos a la lista
                Llibre llibre = new Llibre(id, titol, autor, any);
                llibres.add(llibre);
            }
        } catch (Exception e) {
            System.out.println("Error al carregar llibres del fitxer JSON");
            e.printStackTrace();
        }
        return llibres; // Devolvemos la lista de llibres
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
    public void guardarLlibres(List<Llibre> llibres, File outputDirectory) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Llibre llibre : llibres) {
            JsonObject jsonObject = Json.createObjectBuilder()
                    .add("id", llibre.getId())
                    .add("titol", llibre.getTitol())
                    .add("autor", llibre.getAutor())
                    .add("any", llibre.getAny())
                    .build();
            arrayBuilder.add(jsonObject);
        }
        
        File outputFile = new File(outputDirectory, "llibres_output_jackson.json");
        try (JsonWriter jsonWriter = Json.createWriter(new FileWriter(outputFile))) {
            jsonWriter.writeArray(arrayBuilder.build());
            System.out.println("JSON escrit correctament a " + outputFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
