package br.ce.christian.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoriaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoriaNotFound(CategoriaNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClienteNotFound(ClienteNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(FornecedorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFornecedorNotFound(FornecedorNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(FuncionarioNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFuncionarioNotFound(FuncionarioNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePedidoNotFound(PedidoNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ProdutoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProdutoNotFound(ProdutoNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ProdutoJaPertenceAOutroFornecedorException.class)
    public ResponseEntity<ErrorResponse> handleProdutoJaPertenceAOutroFornecedorException(ProdutoJaPertenceAOutroFornecedorException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PedidoSemProdutoException.class)
    public ResponseEntity<ErrorResponse> handlePedidoSemProduto(PedidoSemProdutoException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ProdutoNaoPertenceCategoriaException.class)
    public ResponseEntity<ErrorResponse> handleProdutoNaoPertenceCategoria(ProdutoNaoPertenceCategoriaException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(CategoriaJaExistenteException.class)
    public ResponseEntity<ErrorResponse> handleCategoriaJaExistente(CategoriaJaExistenteException ex){
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ProdutoJaExistenteNaCategoriaException.class)
    public ResponseEntity<ErrorResponse> handleProdutoJaExistenteNaCategoria(ProdutoJaExistenteNaCategoriaException ex){
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ParametroCategoriaNumeroInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleParametroCategoriaNumeroInvalidoException(ParametroCategoriaNumeroInvalidoException ex){
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErrorResponse> handleEmailJaCadastradoException(EmailJaCadastradoException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(CpfJaCadastradoException.class)
    public ResponseEntity<ErrorResponse> handleCpfJaCadastrado(CpfJaCadastradoException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(FornecedorJaExistenteException.class)
    public ResponseEntity<ErrorResponse> handleFornecedorJaExistente(FornecedorJaExistenteException ex){
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(CargoInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleCargoInvalido(CargoInvalidoException ex){
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(SalarioInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleSalarioInvalido(SalarioInvalidoException ex){
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DataContratacaoInvalidaException.class)
    public ResponseEntity<ErrorResponse> handleDataContratacaoInvalidaException(DataContratacaoInvalidaException ex){
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(FuncionarioSemPermissaoException.class)
    public ResponseEntity<ErrorResponse> handleFuncionarioSemPermissaoException(FuncionarioSemPermissaoException ex){
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ProdutoNotFoundStringException.class)
    public ResponseEntity<ErrorResponse> handleProdutoNotFoundStringException(ProdutoNotFoundStringException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new ValidationError(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());

        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                LocalDateTime.now(),
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    // fallback para qualquer outro erro não tratado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado: " + ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildError(HttpStatus status, String message) {
        ErrorResponse error = new ErrorResponse(
                status.value(),
                message,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }
}
