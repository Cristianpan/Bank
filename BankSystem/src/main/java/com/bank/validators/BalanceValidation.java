package com.bank.validators;

import java.util.ArrayList;

import com.bank.model.Cuenta;

public class BalanceValidation {
    public static boolean validarSaldo(Cuenta cuentaCliente) throws Exception {
        if (cuentaCliente.getSaldo() > 0){
            throw new Exception("No es posible eliminar la cuenta: " + cuentaCliente.getNoCuenta() + ". Retire los fondos e intente nuevamente"); 
        }
        
        return true; 
    }
    
    public static boolean validarSaldo (ArrayList<Cuenta> cuentasCliente) throws Exception {
        for (Cuenta cuentaCliente : cuentasCliente) {
            if (cuentaCliente.getSaldo() > 0){
                throw new Exception("No es posible eliminar el registro del cliente. Retire los fondos de sus cuentas e intente nuevamente.");  
            }
        }

        return true; 
    }
}
