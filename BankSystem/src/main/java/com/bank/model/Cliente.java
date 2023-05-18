package com.bank.model;

import java.util.ArrayList;

public class Cliente {
    private String id;
    private String nombre;
    private String apellido;
    private String curp;
    private String fechaDeNacimiento;
    private int edad;
    private ArrayList<Cuenta> cuentas = new ArrayList<>();

    private Cliente(ClienteBuilder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.curp = builder.curp;
        this.fechaDeNacimiento = builder.fechaDeNacimiento;
        this.edad = builder.edad;
        this.cuentas = builder.cuentas;
    }

    public static ClienteBuilder builder(){
        return new ClienteBuilder();
    }
    
/*     public Cliente(String nombre, String apellido, String fechaDeNacimiento, int edad, String curp) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.curp = curp;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.edad = edad;
    }

    public Cliente(String nombre, String apellido, String fechaDeNacimiento, int edad, String curp, Cuenta cuenta) {
        this(nombre, apellido,fechaDeNacimiento, edad, curp);  
        cuentas.add(cuenta);
    }

    public Cliente(String id, String nombre, String apellido, String fechaDeNacimiento, int edad, String curp,
            ArrayList<Cuenta> cuentas) {
        this(nombre, apellido, fechaDeNacimiento, edad, curp); 
        this.id = id; 
        this.cuentas = cuentas; 
    }
 */

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCurp() {
        return curp;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public Integer getEdad() {
        return edad;
    }

    public ArrayList<Cuenta> getCuentas() {
        return cuentas;
    }

    //class builder
    public static class ClienteBuilder {
        private String id;
        private String nombre;
        private String apellido;
        private String curp;
        private String fechaDeNacimiento;
        private int edad;
        private ArrayList<Cuenta> cuentas;

        public ClienteBuilder(){}
        
        public Cliente build() {
            return new Cliente(this);
        }

        public ClienteBuilder id(String id){
            this.id = id; 
            return this; 
        }
        public ClienteBuilder nombre(String nombre){
            this.nombre = nombre; 
            return this; 
        }
        public ClienteBuilder apellido(String apellido){
            this.apellido = apellido; 
            return this; 
        }
        public ClienteBuilder curp(String curp){
            this.curp = curp; 
            return this; 
        }
        public ClienteBuilder fechaDeNacimiento(String fechaDeNacimiento){
            this.fechaDeNacimiento = fechaDeNacimiento; 
            return this; 
        }
        public ClienteBuilder edad(int edad){
            this.edad = edad; 
            return this; 
        }
        
        public ClienteBuilder cuentas (Cuenta cuenta){
            this.cuentas = new ArrayList<>(); 
            cuentas.add(cuenta);
            return this;
        }
        public ClienteBuilder cuentas(ArrayList<Cuenta> cuentas) {
            this.cuentas = cuentas;
            return this;
        }
    }
}
