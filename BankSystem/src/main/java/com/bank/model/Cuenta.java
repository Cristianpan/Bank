package com.bank.model;

import com.bank.utils.DateFormatter;

public class Cuenta {
    private String noCuenta; 
    private String clienteId; 
    private String fechaVencimiento; 
    private Double saldo;

    private Cuenta (CuentaBuilder builder){
        this.clienteId = builder.clienteId; 
        this.noCuenta = builder.noCuenta;
        this.fechaVencimiento = builder.fechaVencimiento; 
        this.saldo = builder.saldo;         
    }

    public static CuentaBuilder builder(){
        return new CuentaBuilder();
    }

    public String getNoCuenta() {
        return noCuenta;
    }
    public String getClienteId() {
        return clienteId;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }
    public Double getSaldo() {
        return saldo;
    }


    public static class CuentaBuilder {
        private String noCuenta; 
        private String clienteId; 
        private String fechaVencimiento = DateFormatter.generarFechaDeVencimiento(); 
        private Double saldo = 0.0;

        public CuentaBuilder(){}
        
        public Cuenta build(){
            return new Cuenta(this);
        }
        
        public CuentaBuilder noCuenta(String noCuenta){
            this.noCuenta = noCuenta; 
            return this; 
        }
        public CuentaBuilder clienteId(String clienteId){
            this.clienteId = clienteId; 
            return this; 
        }
        public CuentaBuilder fechaVencimiento(String fechaVencimiento){
            this.fechaVencimiento = fechaVencimiento; 
            return this; 
        }
        public CuentaBuilder saldo(Double saldo){
            this.saldo = saldo; 
            return this; 
        }

    }
}
