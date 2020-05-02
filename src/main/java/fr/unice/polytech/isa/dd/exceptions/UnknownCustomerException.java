package fr.unice.polytech.isa.dd.exceptions;

import javax.xml.ws.WebFault;
import java.io.Serializable;

@WebFault(targetNamespace = "http://www.polytech.unice.fr/si/4a/isa/dd/customerService")
public class UnknownCustomerException extends Exception implements Serializable {

    private String _unknownCustomerName;

    public UnknownCustomerException(String name){
        super("This customer " + name +" doesn't exists");
        this._unknownCustomerName = name;
    }

    public String get_unknownCustomerName() {
        return _unknownCustomerName;
    }

    public void set_unknownCustomerName(String _unknownCustomerName) {
        this._unknownCustomerName = _unknownCustomerName;
    }
}
