package com.appgro.controller;

import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.MercadoPagoResponse;
import com.appgro.service.pago.MercadoPagoService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api/mercadopago")
public class MercadoPagoController {

    private static final Logger LOGGER = Logger.getLogger(MercadoPagoController.class);

    private final MercadoPagoService mercadoPagoService;

    public MercadoPagoController(MercadoPagoService mercadoPagoService) {
        this.mercadoPagoService = mercadoPagoService;
    }

    @PostMapping("/realizarPago")
    public EntidadResponse<MercadoPagoResponse> realizarPago(){
        return mercadoPagoService.realizarPago();
    }

    @PutMapping("/recibirPago/{fechaEntrega}")
    public void recibirPago(@PathVariable("fechaEntrega") String fechaEntrega) throws ParseException {
        LOGGER.info("Entra en recibirPago");
        mercadoPagoService.recibirPago(new SimpleDateFormat("dd.MM.yyyy").parse(fechaEntrega));
    }
}
