/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.AlbumBilancio;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenze"
    private Button btnAdiacenze; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA2"
    private ComboBox<?> cmbA2; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doCalcolaAdiacenze(ActionEvent event) {
    	Album scelto=this.cmbA1.getValue();
    	if(scelto==null) {
    		this.txtResult.appendText("Selezionare un album");
    		return;
    	}
    	
    	List<AlbumBilancio> bilanci=this.model.getBilanci(scelto);
    	
    	for(AlbumBilancio a: bilanci) {
    		this.txtResult.appendText(a+"\n");
    	}
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	//prendi il numero/prezzo dalla casella di testo
    	
    	String prezzostringa=this.txtN.getText();
    	if(prezzostringa.equals("")) {
    		this.txtResult.appendText("Inserire un numero valido");
    		return;
    	}
    	
    	try {
    		Double prezzo=Double.parseDouble(prezzostringa);
    		
    		//creo il grafo
    		this.model.creaGrafo(prezzo); //LO POSSO CREARE QUI OPPURE FUORI, NON CAMBIA
    		
    		this.txtResult.appendText("Grafo creato con: \n");
    		this.txtResult.appendText("#Vertici: "+ this.model.nVertici()+"\n");
    		this.txtResult.appendText("#Archi: "+ this.model.nArchi()+"\n");
    		
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("Errore di lettura dell'input");
    		return;
    	}

		//popolo la seconda tendina
    	this.cmbA1.getItems().clear();
    	this.cmbA1.getItems().addAll(this.model.getAlbums());
		
    	
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenze != null : "fx:id=\"btnAdiacenze\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA2 != null : "fx:id=\"cmbA2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    
    public void setModel(Model model) {
    	this.model = model;
    }
}
