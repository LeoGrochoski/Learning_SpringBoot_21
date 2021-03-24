package com.example.carros.domain;


import com.example.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarros() {

        //Método utilizando lambdas para DTO em JPA (Aula 38)
        return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());

        // Utilizando de forma manual
//        List<CarroDTO> List = new ArrayList<>();
//        for (Carro c : carros) {
//            List.add(new CarroDTO(c));
//        }
//        return list//
    }

    public Optional<CarroDTO> getCarroById(Long id) {
        return rep.findById(id).map(CarroDTO::create);
    }

    public List<CarroDTO> getCarrosByTipo(String tipo) {
        return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(),"Não foi possivel inserir o registro");

        return CarroDTO.create(rep.save(carro));
    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id, "Não foi possivel atualizar o registro");

        //Busca o carro no banco de dados
        Optional<Carro> optional = rep.findById(id);
        if (optional.isPresent()) {
            Carro db = optional.get();
//
//            //copiar as propriedades
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id" + db.getId());
//
//            //Atualiza o carro
            rep.save(db);
//
            return CarroDTO.create(db);
        } else {
            return null;
//            throw new RuntimeException("Não foi possivel atualizar o registro");
        }
    }
        //Utilizando metodo Lambda
//        getCarrosById(id).map(db -> {
//            //Copiar as propriedades
//                db.setNome(carro.getNome());
//                db.setTipo(carro.getTipo());
//                System.out.println("Carro id" + db.getId());
//
//                //Atualiza o Carro
//                rep.save(db);
//
//                return db;
//
//                }).orElseThrow(() -> new RuntimeException("Não foi possivel atualizar o registro"));
//    }

    public boolean delete(Long id) {
            if(getCarroById(id).isPresent()) {
                rep.deleteById(id);
                return true;
            }
                return false;
        }
    }
