package br.ce.christian.service;

import br.ce.christian.dtos.ClienteRequestDTO;
import br.ce.christian.dtos.ClienteResponseDTO;
import br.ce.christian.exceptions.ClienteNotFoundException;
import br.ce.christian.exceptions.CpfJaCadastradoException;
import br.ce.christian.exceptions.EmailJaCadastradoException;
import br.ce.christian.model.Cliente;
import br.ce.christian.repository.ClienteRepository;
import br.ce.christian.utils.CpfUtils;
import br.ce.christian.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteResponseDTO salvar(ClienteRequestDTO dto){
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());

        // CPF
        String cpfNormalizado = CpfUtils.normalizar(dto.getCpf());
        if (cpfNormalizado != null && clienteRepository.existsByCpf(cpfNormalizado)) {
            throw new CpfJaCadastradoException(cpfNormalizado);
        }
        cliente.setCpf(cpfNormalizado);


        // Email
        if (dto.getEmail() != null) {
            String emailNormalizado = EmailUtils.normalizar(dto.getEmail());
            if (clienteRepository.existsByEmail(emailNormalizado)) {
                throw new EmailJaCadastradoException(emailNormalizado);
            }
            cliente.setEmail(emailNormalizado);
        }

        cliente.setEndereco(dto.getEndereco());
        cliente.setPedidos(new ArrayList<>());

        Cliente salvo = clienteRepository.save(cliente);
        return new ClienteResponseDTO(salvo);
    }

    public List<ClienteResponseDTO> listar(){

        return clienteRepository.findAll().stream()
                .map(ClienteResponseDTO::new)
                .collect(Collectors.toList());

    }

    public ClienteResponseDTO buscarPorId(Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
        return new ClienteResponseDTO(cliente);
    }

    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto){

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));

        if(dto.getNome() != null){ cliente.setNome(dto.getNome()); }

        // CPF
        if (dto.getCpf() != null) {
            String cpfNormalizado = CpfUtils.normalizar(dto.getCpf());

            if (!cpfNormalizado.equals(cliente.getCpf())
                    && clienteRepository.existsByCpf(cpfNormalizado)) {
                throw new CpfJaCadastradoException(cpfNormalizado);
            }
            cliente.setCpf(cpfNormalizado);
        }

        // Email
        if (dto.getEmail() != null) {
            String emailNormalizado = EmailUtils.normalizar(dto.getEmail());

            if (!emailNormalizado.equals(cliente.getEmail())
                    && clienteRepository.existsByEmail(emailNormalizado)) {
                throw new EmailJaCadastradoException(emailNormalizado);
            }
            cliente.setEmail(emailNormalizado);
        }

        if (dto.getEndereco() != null){ cliente.setEndereco(dto.getEndereco()); }

        Cliente clienteAtualizado = clienteRepository.save(cliente);
        return new ClienteResponseDTO(clienteAtualizado);

    }

    public void deletar(Long id){
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNotFoundException(id);
        }
        clienteRepository.deleteById(id);
    }

}
