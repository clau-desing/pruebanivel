package com.pruebanivel.municipio.denuncias.service;

import java.util.List;
import org.springframework.data.domain.Pageable;

import com.pruebanivel.municipio.denuncias.entity.Denuncia;

public interface DenunciaService {
	public List<Denuncia> findAll(Pageable page);
	public List<Denuncia> findAll();
	public List<Denuncia> finByNombre(String titulo,Pageable page);
	public Denuncia findById(int id);
	public Denuncia save(Denuncia denun);
	public void delete(int id);
}




