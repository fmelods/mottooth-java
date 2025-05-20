package br.com.fiap.mottooth.service;

import br.com.fiap.mottooth.dto.MotoDTO;
import br.com.fiap.mottooth.model.Cliente;
import br.com.fiap.mottooth.model.ModeloMoto;
import br.com.fiap.mottooth.model.Moto;
import br.com.fiap.mottooth.repository.ClienteRepository;
import br.com.fiap.mottooth.repository.ModeloMotoRepository;
import br.com.fiap.mottooth.repository.MotoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MotoService {

    private final MotoRepository motoRepository;
    private final ClienteRepository clienteRepository;
    private final ModeloMotoRepository modeloMotoRepository;

    @Cacheable(value = "motos", key = "#id")
    public MotoDTO findById(Long id) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Moto não encontrada com ID: " + id));
        return convertToDTO(moto);
    }

    @Cacheable(value = "motos", key = "'placa:' + #placa")
    public MotoDTO findByPlaca(String placa) {
        Moto moto = motoRepository.findByPlaca(placa)
                .orElseThrow(() -> new EntityNotFoundException("Moto não encontrada com placa: " + placa));
        return convertToDTO(moto);
    }

    @Cacheable(value = "motos", key = "'page:' + #pageable.pageNumber + ':' + #pageable.pageSize")
    public Page<MotoDTO> findAll(Pageable pageable) {
        return motoRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Cacheable(value = "motos", key = "'filter:' + #placa + ':' + #clienteId + ':' + #modeloId + ':' + #pageable.pageNumber + ':' + #pageable.pageSize")
    public Page<MotoDTO> findByFilters(String placa, Long clienteId, Long modeloId, Pageable pageable) {
        return motoRepository.findByFilters(placa, clienteId, modeloId, pageable).map(this::convertToDTO);
    }

    @Transactional
    @CacheEvict(value = "motos", allEntries = true)
    public MotoDTO save(MotoDTO motoDTO) {
        Moto moto = convertToEntity(motoDTO);
        moto = motoRepository.save(moto);
        return convertToDTO(moto);
    }

    @Transactional
    @CacheEvict(value = "motos", allEntries = true)
    public MotoDTO update(Long id, MotoDTO motoDTO) {
        if (!motoRepository.existsById(id)) {
            throw new EntityNotFoundException("Moto não encontrada com ID: " + id);
        }
        
        Moto moto = convertToEntity(motoDTO);
        moto.setId(id);
        moto = motoRepository.save(moto);
        return convertToDTO(moto);
    }

    @Transactional
    @CacheEvict(value = "motos", allEntries = true)
    public void delete(Long id) {
        if (!motoRepository.existsById(id)) {
            throw new EntityNotFoundException("Moto não encontrada com ID: " + id);
        }
        motoRepository.deleteById(id);
    }

    private MotoDTO convertToDTO(Moto moto) {
        MotoDTO dto = new MotoDTO();
        dto.setId(moto.getId());
        dto.setPlaca(moto.getPlaca());
        
        if (moto.getCliente() != null) {
            dto.setClienteId(moto.getCliente().getId());
            dto.setNomeCliente(moto.getCliente().getNome());
        }
        
        if (moto.getModeloMoto() != null) {
            dto.setModeloMotoId(moto.getModeloMoto().getId());
            dto.setModeloNome(moto.getModeloMoto().getNome());
            dto.setFabricante(moto.getModeloMoto().getFabricante());
        }
        
        return dto;
    }

    private Moto convertToEntity(MotoDTO dto) {
        Moto moto = new Moto();
        moto.setId(dto.getId());
        moto.setPlaca(dto.getPlaca());
        
        if (dto.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + dto.getClienteId()));
            moto.setCliente(cliente);
        }
        
        if (dto.getModeloMotoId() != null) {
            ModeloMoto modeloMoto = modeloMotoRepository.findById(dto.getModeloMotoId())
                    .orElseThrow(() -> new EntityNotFoundException("Modelo de moto não encontrado com ID: " + dto.getModeloMotoId()));
            moto.setModeloMoto(modeloMoto);
        }
        
        return moto;
    }
}
