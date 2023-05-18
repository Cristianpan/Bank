package com.bank.error; 

public class SystemException extends Exception {
    public SystemException () {
        super("Ha habido un error en el sistema. Por favor intente nuevamente"); 
    }
    
}
