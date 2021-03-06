package com.maxel.cursomc.resources;

import com.maxel.cursomc.domain.Categoria;
import com.maxel.cursomc.dto.CategoriaDTO;
import com.maxel.cursomc.service.CategoriaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @ApiOperation(value="Busca por id")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Categoria> find(@PathVariable Integer id) { //@PathVariable: indica que a variável vem da URL
        Categoria obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @ApiOperation(value="Cria uma nova categoria")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto) { //Indica que o objeto vai ser construído a partir de um JSON enviado pelo body
        Categoria obj = service.fromDto(objDto);
        obj = service.insert(obj);
        //Retorna a URL onde foi feita requisição junto com o ID do novo objeto criado
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return  ResponseEntity.created(uri).build();
    }

    @ApiOperation(value="Atualiza categoria")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO objDto) {
        Categoria obj = service.fromDto(objDto);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value="Excluí uma categoria pelo id")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Não é possível excluir uma categoria que possui produtos"), @ApiResponse(code = 404, message = "Código inexistente") })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value="Busca todas as categorias")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<Categoria> list = service.findAll();
        List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @ApiOperation(value="Busca todas as categorias paginadas")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<CategoriaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "lines", defaultValue = "24") Integer linesPerPage, @RequestParam(value = "order", defaultValue = "nome") String orderBy, @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
        Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
        return ResponseEntity.ok().body(listDto);
    }
}
