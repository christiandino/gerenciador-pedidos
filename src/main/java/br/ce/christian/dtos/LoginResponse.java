package br.ce.christian.dtos;

public record LoginResponse(String accessToken, Long expiresIn) {
}