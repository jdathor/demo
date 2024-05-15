package com.example.demo.resources;

import com.example.demo.model.planted.Crop;
import com.example.demo.model.planted.Farm;
import com.example.demo.model.planted.PlantedCropDetailsResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Stream;

@Path("/planted")
@Produces("application/json")
public class PlantedResource {

    @GET
    @Path("/crops/{season}")
    public void getPlantedCropsDetailsBySeason(@Suspended AsyncResponse asyncResponse, @PathParam("season") String season) {
        var responseEntity = new PlantedCropDetailsResponse();
        if ("summer".equalsIgnoreCase(season)) {
            responseEntity = summer();
        }
        var response = Response.ok().entity(responseEntity).build();
        asyncResponse.resume(response);
    }

    @GET
    @Path("/crops")
    public void getPlantedCropsDetailsBySeason(@Suspended AsyncResponse asyncResponse) {
        var responseEntity = new PlantedCropDetailsResponse();
        responseEntity.farms(Stream.concat(summer().getFarms().stream(), autumn().getFarms().stream()).toList());

        var response = Response.ok().entity(responseEntity).build();
        asyncResponse.resume(response);
    }

    private PlantedCropDetailsResponse summer() {
        var responseEntity = new PlantedCropDetailsResponse();
        var farm = new Farm()
                .name("MyFarm")
                .season("summer")
                .crops(List.of(new Crop()
                        .name("corn")
                        .expectedAmount(200)
                ));
        responseEntity.addFarmsItem(farm);

        return responseEntity;
    }

    private PlantedCropDetailsResponse autumn() {
        var responseEntity = new PlantedCropDetailsResponse();
        var farm = new Farm()
                .name("MyFarm")
                .season("autumn")
                .crops(List.of(new Crop()
                        .name("potato")
                        .expectedAmount(500)
                ));
        responseEntity.addFarmsItem(farm);

        return responseEntity;
    }
}
