package com.fag.mapas;

import com.fag.mapas.model.Contorno;
import com.fag.mapas.model.Coordenada;
import com.fag.mapas.model.Coordenada2;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alexandro
 */
@RestController
@RequestMapping("/service")
public class Services {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public Services(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*@GetMapping("/equipamentos/list")
    public List<String> equipamentosList() {
        String sql = "SELECT row_to_json(fc) as dados\n" +
                        "   FROM (SELECT 'FeatureCollection' As type, \n" +
                        "              array_to_json(array_agg(f)) As features\n" +
                        "         FROM (SELECT 'Feature' As type,\n" +
                        "                      ST_AsGeoJSON(posicao)::json As geometry,\n" +
                        "                      row_to_json(lp) As properties\n" +
                        "                 from equipamento \n" +
                        "               inner join ( select 'FFFFFF' as cor, equip.id from equipamento as equip ) as lp on equipamento.id = lp.id ) \n" +
                        "       As f )  As fc";

        List<String> data = jdbcTemplate.query(sql, new RowMapper<String>(){
                            public String mapRow(ResultSet rs, int rowNum) 
                                                         throws SQLException {
                                    return rs.getString(1);
                            }
                       });
        return data;
    }
    
    
        String sql = " SELECT row_to_json(fc) as dados\n"
                + "  FROM (SELECT 'FeatureCollection' As type, \n"
                + "              array_to_json(array_agg(f)) As features\n"
                + "         FROM (SELECT 'Feature' As type,\n"
                + "                      ST_AsGeoJSON(geom)::json As geometry,\n"
                + "                      row_to_json(row(contorno.name)) As properties\n"
                + "                 from contorno ) \n"
                + "       As f )  As fc ";
    
    
     */
    @GetMapping("/equipamentos/list")
    public List<Coordenada> equipamentosList() {
        String sql = " select st_x(posicao), st_y(posicao) from equipamento ";
        List<Coordenada> data = jdbcTemplate.query(sql, new RowMapper<Coordenada>() {
            public Coordenada mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Coordenada(rs.getDouble(1), rs.getDouble(2));
            }
        });
        return data;
    }

    @GetMapping("/equipamentoscores/list")
    public List<Coordenada2> equipamentoscoresList() {
        String sql = " select st_x(e.posicao), st_y(e.posicao), c.cor, c.nome from equipamento as e inner join cliente as c on e.cliente_id = c.id ";
        List<Coordenada2> data = jdbcTemplate.query(sql, new RowMapper<Coordenada2>() {
            public Coordenada2 mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Coordenada2(rs.getDouble(1), rs.getDouble(2), "#" + rs.getString(3), rs.getString(4)); // concatena # na cor 
            }
        });
        return data;
    }

    @GetMapping("/gps/list")
    public List<String> gpsList() {
        String sql = "select name, ST_asgeojson(geom) as geom from contorno  ";
        List<String> data = jdbcTemplate.query(sql, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Gson().toJson(new Contorno(rs.getString(1), rs.getString(2)));
            }
        });
        return data;
    }

    @GetMapping("/municipio/list")
    public List<String> municipioList() {
        String sql = "select nome, ST_asgeojson(geom) as geom from municipio where geocodigo like ('41%') ";
        List<String> data = jdbcTemplate.query(sql, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Gson().toJson(new Contorno(rs.getString(1), rs.getString(2)));
            }
        });
        return data;
    }

    @GetMapping("/municipio/list2")
    public ResponseEntity<Resource> download(String param) throws IOException {
        /*String sql = "select nome, ST_asgeojson(geom) as geom from municipio where geocodigo like ('42%') ";
        List<String> data = jdbcTemplate.query(sql, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Gson().toJson(new Contorno(rs.getString(1), rs.getString(2)));
            }
        });
        FileWriter writer = new FileWriter("output.txt"); 
        for(String str: data) {
          writer.write(str);
        }
        writer.close();
        File file = new File("output.txt");
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));*/
        

        File file = new File("d:\\municipios-pr.json");
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        
/*        StringWriter sw = new StringWriter();
        PrintWriter writer = new PrintWriter(sw);
        for (String line : data) {
            writer.println(line);
        }
        writer.flush();
        ByteArrayResource resource = new ByteArrayResource(sw.toString().getBytes("UTF8")); */

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Content-Type", "text/json"); 
        headers.add("charset","UTF-8");
//        headers.add("Content-Encoding", "gzip");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                //.contentType(MediaType.parseMediaType("application/gzip"))
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

}
