package fr.unice.polytech.isa.dd.exceptions;

import javax.xml.ws.WebFault;
import java.io.Serializable;

@WebFault(targetNamespace = "http://www.polytech.unice.fr/si/4a/isa/dd/customerService")
public class AlreadyExistingCustomerException extends Exception implements Serializable {

    private String conflictName;

    public AlreadyExistingCustomerException(String name){
        super("This customer : "+name +" already exits");
        this.conflictName = name;
    }

    public String getConflictName() {
        return conflictName;
    }

    public void setConflictName(String conflictName) {
        this.conflictName = conflictName;
    }
}
