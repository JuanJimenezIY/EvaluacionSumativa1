package com.distribuida;

import com.distribuida.db.Book;
import com.distribuida.servicios.ServicioBook;
import com.google.gson.Gson;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.enterprise.inject.spi.CDI;
import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;




public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static ContainerLifecycle lifecycle= null;
    private static void crearBook(ServerRequest req, ServerResponse res) {
        ServicioBook service = CDI.current().select(ServicioBook.class).get();
        Gson gson = new Gson();
        String body = req.content().as(String.class);
        service.insert(gson.fromJson(body, Book.class));
        res.send("Libro creado");
    }
    private static void actualizarBook(ServerRequest req, ServerResponse res) {
        ServicioBook service = CDI.current().select(ServicioBook.class).get();
        Gson gson = new Gson();
        String body = req.content().as(String.class);
        Book book = gson.fromJson(body, Book.class);
        Integer id = Integer.valueOf(req.path().pathParameters().get("id"));
        book.setId(id);
        service.update(book);
        res.send("Libro actualizado: " + id);
    }
    private static void borrarBook(ServerRequest req, ServerResponse res) {
        ServicioBook service = CDI.current().select(ServicioBook.class).get();
        Integer id = Integer.valueOf(req.path().pathParameters().get("id"));
        service.delete(id);
        res.send("Libro eliminado: " + id);
    }

    public static void main(String[] args) {

        lifecycle = WebBeansContext.currentInstance().getService(ContainerLifecycle.class);
        lifecycle.startApplication(null);
        ServicioBook service = CDI.current().select(ServicioBook.class).get();

       WebServer.builder()
                .port(8081)
                .routing(builder -> builder
                        .get("/books", (req, res) -> {
                            String responseEntity = new Gson().toJson(service.findAll());
                            res.send(responseEntity);

                        })
                        .get("/books/{id}",(req, res) -> {
                            Gson gson = new Gson();
                            String responseEntity=gson.toJson(service.findById(Integer.valueOf(req.path().pathParameters().get("id"))));

                            res.send(responseEntity);
                        })
                        .post("/books",Main::crearBook)
                        .put("/books/{id}",Main::actualizarBook)
                        .delete("/books/{id}", Main::borrarBook)
                )
                .build()
                .start();


    } }


