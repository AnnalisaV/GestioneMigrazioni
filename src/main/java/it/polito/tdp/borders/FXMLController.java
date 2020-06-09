/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.CountryAndNConf;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxNazione"
    private ComboBox<?> boxNazione; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
    	
    	int year=0; 
    	if (this.txtAnno.getLength()==0) {
    		txtResult.appendText("ERRORE : Indicare un anno \n");
    		return; 
    	}
    	
    	try {
    		year= Integer.parseInt(this.txtAnno.getText()); 
    	}catch(NumberFormatException nfe){
    		txtResult.appendText("ERRORE : Indicare un anno in numeri \n");
    		return; 
    	}
    	if(year < 1816 || year>2006) {
    		txtResult.appendText("ERRORE : Indicare un anno compreso fra 1816-2006 \n");
    		return; 
    	}
    	
    	//ok 
    	model.creaGrafo(year);
    	txtResult.appendText("Grafo creato con "+model.nVertex()+" vertex and "+model.nArchi()+" edges \n");
    	
    	for (CountryAndNConf cc : this.model.getConfinanti()) {
    		txtResult.appendText(cc.toString()+"\n");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxNazione != null : "fx:id=\"boxNazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
