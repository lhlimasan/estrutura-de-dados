package br.com.equipe.clientes;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// ClienteController.java
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ClienteController {
    private Map<String, Cliente> clientes = new HashMap<>();

    @GetMapping("/")
    public String redirectCadastro(){
        return "redirect:/cadastrar";
    }

    @PostMapping("/cadastrar")
    public String cadastrarCliente(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        if(verificarDados(cliente).contains("campos")){
            redirectAttributes.addFlashAttribute("campos","Preencher todos os campos");
            return "redirect:/cadastrar";
        }else if(verificarDados(cliente).contains("cpf")){
            redirectAttributes.addFlashAttribute("cpf", "O cpf deve conter 11 digitos");
            return "redirect:/cadastrar";
        }else{
            clientes.putIfAbsent(cliente.getCpf(), cliente);
            return "redirect:/buscar?cpf=" + cliente.getCpf();
        }

    }

    @PostMapping("/limpar")
    public String limparFormulario(HttpServletRequest request){
        if(request.getParameter("rota").equals("cadastro")){
            return "redirect:/cadastrar";
        }else{
            return "redirect:/buscar";
        }
    }

    @GetMapping("/cadastrar")
    public String paginaInicial() {
        return "cadastrar";// Nome do arquivo HTML para a página de cadastro
    }

    @GetMapping("/buscar")
    public String buscarCliente(@RequestParam(required = false) String cpf, Model model) {
        if(cpf == null){
            List<Cliente> clientesList = listarClientes();
            model.addAttribute("clienteList", clientesList);
        }else if(clientes.get(cpf) != null){
            Cliente cliente = clientes.get(cpf);
            model.addAttribute("cliente", cliente);
        }else{
            model.addAttribute("erro", "Cliente não foi encontrado");
        }

        return "buscar";
    }

    public String verificarDados(Cliente cliente){
        String nome = cliente.getNome();
        String cpf = cliente.getCpf();
        String email = cliente.getEmail();
        String telefone = cliente.getTelefone();
        String validado = "";

        if(nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || telefone.isEmpty()){
            validado = "campos";
        }else if(cpf.length() != 11){
            validado = "cpf";
        }else{
            validado = "correto";
        }
        return validado;
    }

    public List<Cliente> listarClientes(){
        List<Cliente> clientesList = new ArrayList<>();
        for(Map.Entry<String, Cliente> entry : clientes.entrySet()){
            Cliente cliente = entry.getValue();
            clientesList.add(cliente);
        }
        return clientesList;
    }
}
