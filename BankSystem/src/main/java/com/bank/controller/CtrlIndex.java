package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bank.daos.DaoCliente;
import com.bank.daos.DaoCuenta;
import com.bank.model.Cliente;
import com.bank.model.Cuenta;
import com.bank.validators.BalanceValidation;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CtrlIndex {
    @Autowired
    private DaoCliente daoCliente;
    @Autowired
    private DaoCuenta daoCuenta;

    // Getters
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/{curp}")
    public String index(Model model, @PathVariable(name = "curp") String curp) {
        try {
            Cliente cliente = daoCliente.getClienteByCurp(curp);
            if (cliente == null) {
                throw new Exception("No existe un cliente registrado con el CURP: " + curp);
            }
            model.addAttribute("cliente", cliente);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "index";
    }

    // Delete
    @GetMapping("/eliminarCliente/{curp}")
    public String eliminarCliente(@PathVariable(name = "curp") String curp, HttpServletRequest request, RedirectAttributes redirect) {
        try {
            Cliente cliente = daoCliente.getClienteByCurp(curp);
            BalanceValidation.validarSaldo(cliente.getCuentas());
            daoCliente.eliminarCliente(cliente.getId());
        } catch (Exception e) {
            redirect.addFlashAttribute("message", e.getMessage());
            return "redirect:" + request.getHeader("referer");
        }

        return "redirect:/";
    }

    @GetMapping("/eliminarCuenta/{noCuenta}")
    public String eliminarCuenta(@PathVariable(name = "noCuenta") String noCuenta, HttpServletRequest request,
            RedirectAttributes redirect) {
        try {
            Cuenta cuenta = daoCuenta.getCuentaCliente(noCuenta);
            BalanceValidation.validarSaldo(cuenta);
            daoCuenta.eliminarCuenta(noCuenta);

            if (!daoCuenta.tieneCuentas(cuenta.getClienteId())) {
                daoCliente.eliminarCliente(cuenta.getClienteId());
                redirect.addFlashAttribute("message",
                        "Se ha borrado el registro del cliente debido a que no tiene cuentas existentes");
                return "redirect:/";
            }
        } catch (Exception e) {
            redirect.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/agregarCuenta/{id}")
    public String agregarCuentaACliente(@PathVariable(name = "id") String id, Model model, HttpServletRequest request,
            RedirectAttributes redirect) {
        try {
            Cuenta cuenta = Cuenta.builder()
                    .clienteId(id)
                    .build();
            daoCuenta.agregarCuentaCliente(cuenta);
        } catch (Exception e) {
            redirect.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:" + request.getHeader("referer");
    }
}
