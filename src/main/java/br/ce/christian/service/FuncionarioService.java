package br.ce.christian.service;


import br.ce.christian.dtos.FuncionarioRequestDTO;
import br.ce.christian.dtos.FuncionarioResponseDTO;
import br.ce.christian.enums.AtividadeExercida;
import br.ce.christian.exceptions.*;
import br.ce.christian.model.Funcionario;
import br.ce.christian.repository.FuncionarioRepository;
import br.ce.christian.utils.CpfUtils;
import br.ce.christian.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioResponseDTO salvar(FuncionarioRequestDTO dto){
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());

        // CPF
        if (dto.getCpf() != null) {
            String cpfNormalizado = CpfUtils.normalizar(dto.getCpf());

            if (!cpfNormalizado.equals(funcionario.getCpf())
                    && funcionarioRepository.existsByCpf(cpfNormalizado)) {
                throw new CpfJaCadastradoException(cpfNormalizado);
            }
            funcionario.setCpf(cpfNormalizado);
        }

        // Email
        if (dto.getEmail() != null) {
            String emailNormalizado = EmailUtils.normalizar(dto.getEmail());

            if (!emailNormalizado.equals(funcionario.getEmail())
                    && funcionarioRepository.existsByEmail(emailNormalizado)) {
                throw new EmailJaCadastradoException(emailNormalizado);
            }
            funcionario.setEmail(emailNormalizado);
        }

        // Cargo
        if (dto.getCargo() == null || dto.getCargo().isBlank()) {
            throw new CargoInvalidoException("nulo");
        }

        AtividadeExercida cargoEnum;
        try {
            cargoEnum = AtividadeExercida.valueOf(dto.getCargo().toUpperCase()); // garante uppercase
        } catch (IllegalArgumentException e) {
            throw new CargoInvalidoException(dto.getCargo());
        }
        funcionario.setCargo(cargoEnum);

        // Salario
        if (dto.getSalario() == null || dto.getSalario().signum() <= 0) {
            throw new SalarioInvalidoException("O salário deve ser maior que 0.");
        }
        funcionario.setSalario(dto.getSalario());

        // Data contratação
        if (dto.getDataContratacao() == null || dto.getDataContratacao().isAfter(LocalDate.now())) {
            throw new DataContratacaoInvalidaException(dto.getDataContratacao());
        }
        funcionario.setDataContratacao(dto.getDataContratacao());
        funcionario.setEndereco(dto.getEndereco());

        Funcionario salvo = funcionarioRepository.save(funcionario);
        return new FuncionarioResponseDTO(salvo);
    }

    public List<FuncionarioResponseDTO> listar (){
        return funcionarioRepository.findAll().stream().map(FuncionarioResponseDTO::new).collect(Collectors.toList());
    }

    public FuncionarioResponseDTO buscarFuncionarioPorId(Long id){
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new FuncionarioNotFoundException(id));
        return new FuncionarioResponseDTO(funcionario);
    }

    public FuncionarioResponseDTO atualizarFuncionario(Long id, FuncionarioRequestDTO dto){
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new FuncionarioNotFoundException(id));

        if (dto.getNome() != null) { funcionario.setNome(dto.getNome()); }

        if (dto.getCpf() != null) {
            String cpfNormalizado = CpfUtils.normalizar(dto.getCpf());

            if (!cpfNormalizado.equals(funcionario.getCpf())
                    && funcionarioRepository.existsByCpf(cpfNormalizado)) {
                throw new CpfJaCadastradoException(cpfNormalizado);
            }
            funcionario.setCpf(cpfNormalizado);
        }

        // Email
        if (dto.getEmail() != null) {
            String emailNormalizado = EmailUtils.normalizar(dto.getEmail());

            if (!emailNormalizado.equals(funcionario.getEmail())
                    && funcionarioRepository.existsByEmail(emailNormalizado)) {
                throw new EmailJaCadastradoException(emailNormalizado);
            }
            funcionario.setEmail(emailNormalizado);
        }

        // Cargo
        if (dto.getCargo() == null || dto.getCargo().isBlank()) {
            throw new CargoInvalidoException("nulo");
        }

        AtividadeExercida cargoEnum;
        try {
            cargoEnum = AtividadeExercida.valueOf(dto.getCargo().toUpperCase()); // garante uppercase
        } catch (IllegalArgumentException e) {
            throw new CargoInvalidoException(dto.getCargo());
        }
        funcionario.setCargo(cargoEnum);

        // Salário
        if (dto.getSalario() != null) {
            if (dto.getSalario().signum() <= 0) {
                throw new SalarioInvalidoException("O salário deve ser maior que 0.");
            }
            funcionario.setSalario(dto.getSalario());
        }

        // Data contratação
        if (dto.getDataContratacao() != null) {
            if (dto.getDataContratacao().isAfter(LocalDate.now())) {
                throw new DataContratacaoInvalidaException(dto.getDataContratacao());
            }
            funcionario.setDataContratacao(dto.getDataContratacao());
        }

        if (dto.getEndereco() != null) { funcionario.setEndereco(dto.getEndereco()); }

        Funcionario atualizado = funcionarioRepository.save(funcionario);
        return new FuncionarioResponseDTO(atualizado);
    }

    public void deletarFuncionario(Long id){
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new FuncionarioNotFoundException(id));
        funcionarioRepository.delete(funcionario);
    }
}
