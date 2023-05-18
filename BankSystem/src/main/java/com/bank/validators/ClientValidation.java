package com.bank.validators;

public class ClientValidation {
    public static boolean validarDatosCliente (String nombre, String apellidos, String curp) throws Exception{
        return validarNombre(nombre, apellidos) && validarCurp(curp); 
    }
    
    private static boolean validarNombre(String nombre, String apellidos) throws Exception{
        String patron = "[a-zA-Z ]+";
        if (!nombre.matches(patron)){
            throw new Exception("El nombre ingresado es incorrecto. Verifique que no contenga caracteres especiales y/o alfanuméricos"); 
        } 
         
        if (!apellidos.matches(patron)) {
            throw new Exception("El apellido ingresado es incorrecto. Verifique que no contenga caracteres especiales y/o alfanuméricos"); 
        }
        
        return nombre.matches(patron) && apellidos.matches(patron);
    }
    
    private static boolean validarCurp(String curp) throws Exception {
        String patron = "^[A-Z]{4}[0-9]{6}[H,M]{1}[A-Z]{6}[0-9]{1}$"; 
        
        if (!curp.matches(patron)) {
            throw new Exception("El formato CURP es incorrecto. Verifiquelo e intente nuevamente"); 
        }

        return curp.matches(patron); 
    }   
}
