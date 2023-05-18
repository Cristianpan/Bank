package com.bank.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.bank.daos.DaoCliente;
import com.bank.model.Cliente;
import org.springframework.ui.Model;

@Controller
public class CtrlReporte {

    @Autowired 
    DaoCliente daoCliente; 

    @GetMapping("/reporte")
    public String getReporteClientes(Model model){
        try {
            ArrayList<Cliente> clientes = daoCliente.obtenerClientes(); 
            model.addAttribute("clientes", clientes);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage()); 
        }
        return "reporte"; 
    }
    
}
