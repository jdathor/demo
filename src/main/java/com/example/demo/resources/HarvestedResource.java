package com.example.demo.resources;

import com.example.demo.model.harvested.Crop;
import com.example.demo.model.harvested.Farm;
import com.example.demo.model.harvested.HarvestedCropDetailsResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/harvested")
@Produces("application/json")
public class HarvestedResource {

    @GET
    @Path("/crops/{season}")
    public void getHarvestedCropsDetailsBySeason(@Suspended AsyncResponse asyncResponse, @PathParam("season") String season) {
        var responseEntity = new HarvestedCropDetailsResponse();
        var farm = new Farm()
                .name("MyFarm")
                .season(season)
                .crops(List.of(new Crop()
                        .name("corn")
                        .harvestedAmount(200)
                ));
        responseEntity.addFarmsItem(farm);
        var response = Response.ok().entity(responseEntity).build();
        asyncResponse.resume(response);
    }

}
