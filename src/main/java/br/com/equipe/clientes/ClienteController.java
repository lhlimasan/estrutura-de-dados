package br.com.equipe.clientes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// ClienteController.java
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ClienteController {
    private Map<String, Cliente> clientes = new HashMap<>();

    @PostMapping("/cadastrar")
    public RedirectView cadastrarCliente(@ModelAttribute Cliente cliente) {
        if(verificarDados(cliente)){
            clientes.put(cliente.getCpf(), cliente);
            return new RedirectView("/buscar?cpf=" + cliente.getCpf());
        }else{
            return new RedirectView("/cadastrar");
        }

    }

    @PostMapping("/limpar")
    public String limparFormulario(){
        return "redirect:/cadastrar";
    }

    @GetMapping("/cadastrar")
    public String paginaInicial() {
        return "cadastrar";// Nome do arquivo HTML para a página de cadastro
    }


    @GetMapping("/buscar")
    public String buscarCliente(@RequestParam(required = false) String cpf, Model model) {
        Cliente cliente = clientes.get(cpf);
        model.addAttribute("cliente", cliente);
        return "buscar";
    }

    public boolean verificarDados(Cliente cliente){
        String nome = cliente.getNome();
        String cpf = cliente.getCpf();
        String email = cliente.getEmail();
        String telefone = cliente.getTelefone();
        boolean validado;

        if(nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || telefone.isEmpty()){
            validado = false;
        }else if(cpf.length() != 11){
            validado = false;
        }else{
            validado = true;
        }
        return validado;
    }
}
