package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bank.daos.DaoCliente;
import com.bank.model.Cliente;
import com.bank.model.Cuenta;
import com.bank.validators.ClientValidation;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CtrlFormCliente {
    @Autowired
    private DaoCliente daoCliente;
    private Cliente cliente;

    @GetMapping("/formCliente")
    public String formCliente(Model model) {
        model.addAttribute("cliente", Cliente.builder().build());
        return "formCliente";
    }

    @GetMapping("/formCliente/{idCliente}")
    public String getClientePorId(Model model, @PathVariable(name = "idCliente") String idCliente, HttpServletRequest request,
            RedirectAttributes redirect) {
        try {
            cliente = daoCliente.getClienteById(idCliente);
            model.addAttribute("cliente", cliente);
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:" + request.getHeader("referer");
        }
        return "formCliente";
    }

    @PostMapping("/formCliente")
    public String agregarCliente(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String fechaDeNacimiento, @RequestParam int edad, @RequestParam String curp,
            Model model, RedirectAttributes redirect) {

        try {
            ClientValidation.validarDatosCliente(nombre, apellido, curp);
            if (daoCliente.existeCurpRegistrada(curp)) {
                throw new Exception("El CURP ya ha sido registrado, por favor ingrese otro");
            }
            Cliente cliente = Cliente.builder()
                    .nombre(nombre)
                    .apellido(apellido)
                    .fechaDeNacimiento(fechaDeNacimiento)
                    .edad(edad)
                    .curp(curp)
                    .cuentas(Cuenta.builder().build())
                    .build();

            daoCliente.agregarCliente(cliente);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("cliente", Cliente.builder().nombre(nombre).apellido(apellido)
                    .fechaDeNacimiento(fechaDeNacimiento).edad(edad).build());
            return "formCliente";
        }

        return "redirect:/" + curp;
    }

    @PostMapping("/formCliente/{clienteId}")
    public String actualizarDatosCliente(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String fechaDeNacimiento, @RequestParam Integer edad, @RequestParam String curp,
            @PathVariable(name = "clienteId") String clienteId,
            Model model, RedirectAttributes redirect, HttpServletRequest request) {
        try {
            ClientValidation.validarDatosCliente(nombre, apellido, curp);
            if (daoCliente.existeCurpRegistrada(curp) && !curp.equals(cliente.getCurp())) {
                throw new Exception("El CURP ya ha sido registrado, por favor ingrese otro");
            }

            cliente = Cliente.builder()
                    .id(clienteId)
                    .nombre(nombre)
                    .apellido(apellido)
                    .fechaDeNacimiento(fechaDeNacimiento)
                    .edad(edad)
                    .curp(curp)
                    .build();
            daoCliente.actualizarCliente(cliente);
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
            model.addAttribute("cliente", Cliente.builder().nombre(nombre).apellido(apellido)
                    .fechaDeNacimiento(fechaDeNacimiento).edad(edad).build());
            return "redirect:" + request.getHeader("referer");
        }

        return "redirect:/" + curp;
    }
}
