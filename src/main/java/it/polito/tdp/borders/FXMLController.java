
package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
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

    @FXML
    private ComboBox<Country> cmbBoxNazioni;
    
    @FXML
    private Button btnStatiRaggiungibili;
    
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
    	try {
    		int x= Integer.parseInt(txtAnno.getText());
    		if(x<1816 || x>2016) {
    			txtResult.setText("Inserire un anno compreso tra 1816 e 2016!");
    			return;
    		}
    		model.createGraph(x);
    		cmbBoxNazioni.getItems().setAll(model.getCountries());
    		btnStatiRaggiungibili.setDisable(false);
    		txtResult.appendText("Numero componenti connesse: "+model.getNumberOfConnectedComponents()+"\n");
    		for(Country c:model.getCountries()) {
    			txtResult.appendText(c.getStateName()+" - Stati confinanti: "+model.getNConfini(c)+"\n");
    		}
    	}
    	catch(NumberFormatException e) {
    		txtResult.setText("Inserire un valore numerico valido!");
    	}

    }
    
    @FXML
    void doCalcolaStatiRaggiungibili(ActionEvent event) {
    	txtResult.clear();
    	List<Country> result=model.statiRaggiungibili(cmbBoxNazioni.getValue());
    	System.out.println("Stati raggiungibili: "+result.size());
    	for(Country c:result) {
    		txtResult.appendText(c.getStateName()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
