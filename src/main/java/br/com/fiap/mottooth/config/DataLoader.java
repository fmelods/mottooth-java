package br.com.fiap.mottooth.config;

import br.com.fiap.mottooth.model.*;
import br.com.fiap.mottooth.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(
            ModeloMotoRepository modeloMotoRepository,
            ModeloBeaconRepository modeloBeaconRepository,
            ClienteRepository clienteRepository,
            MotoRepository motoRepository,
            BeaconRepository beaconRepository,
            PatioRepository patioRepository,
            LocalizacaoRepository localizacaoRepository) {
        
        return args -> {
            // Criar modelos de moto
            ModeloMoto modelo1 = new ModeloMoto();
            modelo1.setNome("Sport 110i");
            modelo1.setFabricante("Mottu");
            modeloMotoRepository.save(modelo1);
            
            ModeloMoto modelo2 = new ModeloMoto();
            modelo2.setNome("CG 160");
            modelo2.setFabricante("Honda");
            modeloMotoRepository.save(modelo2);
            
            // Criar modelos de beacon
            ModeloBeacon beaconModelo1 = new ModeloBeacon();
            beaconModelo1.setNome("BLE Tracker");
            beaconModelo1.setFabricante("Mottu Tech");
            modeloBeaconRepository.save(beaconModelo1);
            
            ModeloBeacon beaconModelo2 = new ModeloBeacon();
            beaconModelo2.setNome("iBeacon Pro");
            beaconModelo2.setFabricante("Apple");
            modeloBeaconRepository.save(beaconModelo2);
            
            // Criar clientes
            Cliente cliente1 = new Cliente();
            cliente1.setNome("João Silva");
            cliente1.setCpf("123.456.789-00");
            cliente1.setEmail("joao.silva@email.com");
            cliente1.setTelefone("(11) 98765-4321");
            clienteRepository.save(cliente1);
            
            Cliente cliente2 = new Cliente();
            cliente2.setNome("Maria Oliveira");
            cliente2.setCpf("987.654.321-00");
            cliente2.setEmail("maria.oliveira@email.com");
            cliente2.setTelefone("(11) 91234-5678");
            clienteRepository.save(cliente2);
            
            // Criar motos
            Moto moto1 = new Moto();
            moto1.setPlaca("ABC1234");
            moto1.setCliente(cliente1);
            moto1.setModeloMoto(modelo1);
            motoRepository.save(moto1);
            
            Moto moto2 = new Moto();
            moto2.setPlaca("XYZ9876");
            moto2.setCliente(cliente2);
            moto2.setModeloMoto(modelo2);
            motoRepository.save(moto2);
            
            // Criar beacons
            Beacon beacon1 = new Beacon();
            beacon1.setUuid("uuid-001-ABC");
            beacon1.setBateria(85);
            beacon1.setMoto(moto1);
            beacon1.setModeloBeacon(beaconModelo1);
            beaconRepository.save(beacon1);
            
            Beacon beacon2 = new Beacon();
            beacon2.setUuid("uuid-002-XYZ");
            beacon2.setBateria(92);
            beacon2.setMoto(moto2);
            beacon2.setModeloBeacon(beaconModelo2);
            beaconRepository.save(beacon2);
            
            // Criar pátio
            Patio patio1 = new Patio();
            patio1.setNome("Pátio Central");
            patioRepository.save(patio1);
            
            Patio patio2 = new Patio();
            patio2.setNome("Pátio Zona Sul");
            patioRepository.save(patio2);
            
            // Criar localizações
            Localizacao loc1 = new Localizacao();
            loc1.setPosicaoX(new BigDecimal("10.5"));
            loc1.setPosicaoY(new BigDecimal("20.3"));
            loc1.setDataHora(LocalDateTime.now().minusHours(2));
            loc1.setMoto(moto1);
            loc1.setPatio(patio1);
            localizacaoRepository.save(loc1);
            
            Localizacao loc2 = new Localizacao();
            loc2.setPosicaoX(new BigDecimal("15.7"));
            loc2.setPosicaoY(new BigDecimal("25.9"));
            loc2.setDataHora(LocalDateTime.now().minusHours(1));
            loc2.setMoto(moto1);
            loc2.setPatio(patio1);
            localizacaoRepository.save(loc2);
            
            Localizacao loc3 = new Localizacao();
            loc3.setPosicaoX(new BigDecimal("30.2"));
            loc3.setPosicaoY(new BigDecimal("40.8"));
            loc3.setDataHora(LocalDateTime.now());
            loc3.setMoto(moto2);
            loc3.setPatio(patio2);
            localizacaoRepository.save(loc3);
        };
    }
}
