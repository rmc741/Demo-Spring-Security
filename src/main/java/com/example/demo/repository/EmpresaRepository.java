package com.example.demo.repository;

import com.example.demo.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa,Long> {

    boolean existsByCnpj(String cnpj);

    @Query("SELECT obj FROM Empresa obj WHERE obj.cnpj =:cnpj")
    Empresa findByCnpj(@Param("cnpj") String cnpj);
}
