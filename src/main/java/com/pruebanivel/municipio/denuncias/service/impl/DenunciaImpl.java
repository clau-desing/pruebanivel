package com.pruebanivel.municipio.denuncias.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pruebanivel.municipio.denuncias.repository.DenunciaRepository;
import com.pruebanivel.municipio.denuncias.service.DenunciaService;
import com.pruebanivel.municipio.denuncias.entity.Denuncia;
import com.pruebanivel.municipio.denuncias.exception.*;
import com.pruebanivel.municipio.denuncias.repository.*;
import com.pruebanivel.municipio.denuncias.service.*;
import com.pruebanivel.municipio.denuncias.validator.*;

@Service
public class DenunciaImpl implements DenunciaService{
	@Autowired
	private DenunciaRepository repository;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Denuncia> findAll(Pageable page) {
		try {
			List<Denuncia> registros=repository.findAll(page).toList();
			return registros;
		} catch (ValidateException | NoDataFoundException e) {
			throw e;
		}
		catch (GeneralException e) {
			throw new GeneralException("Error del servidor");
		}		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Denuncia> finByNombre(String titulo, Pageable page) {
		try {
			List<Denuncia> registros=repository.findByTituloContaining(titulo,page);
			return registros;
		} catch (ValidateException | NoDataFoundException e) {
			throw e;
		}
		catch (GeneralException e) {
			throw new GeneralException("Error del servidor");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Denuncia findById(int id) {
		try {
			Denuncia registro=repository.findById(id).
					orElseThrow(()->new NoDataFoundException("No existe un registro con ese ID"));
			return registro;
		} catch (ValidateException | NoDataFoundException e) {
			throw e;
		}
		catch (GeneralException e) {
			throw new GeneralException("Error del servidor");
		}
	}

	@Override
	@Transactional
	public Denuncia save(Denuncia denuncia) {
	    try {
	        DenunciaValidator.save(denuncia);

	        // Nuevo registro
	        if (denuncia.getId() == 0) {
	            Denuncia nuevo = repository.save(denuncia);
	            return nuevo;
	        }

	        // Editar registro existente
	        Denuncia registro = repository.findById(denuncia.getId())
	                .orElseThrow(() -> new NoDataFoundException("No existe un registro con ese ID"));
	        
	        registro.setTitulo(denuncia.getTitulo());
	       

	        repository.save(registro);
	        return registro;
	    } catch (ValidateException | NoDataFoundException e) {
	        throw e;
	    } catch (GeneralException e) {
	        throw new GeneralException("Error del servidor");
	    }
	}


	@Override
	@Transactional
	public void delete(int id) {
		try {
			Denuncia registro=repository.findById(id).orElseThrow(()->new NoDataFoundException("No existe un registro con ese ID"));
			repository.delete(registro);
		} catch (ValidateException | NoDataFoundException e) {
			throw e;
		}
		catch (GeneralException e) {
			throw new GeneralException("Error del servidor");
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Denuncia> findAll() {
		try {
			List<Denuncia> registros=repository.findAll();
			return registros;
		} catch (ValidateException | NoDataFoundException e) {
			throw e;
		}
		catch (GeneralException e) {
			throw new GeneralException("Error del servidor");
		}		
	}
}




