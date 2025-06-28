package com.hhplus.project.domain.event.dto;

import com.hhplus.project.domain.event.EventException;
import com.hhplus.project.domain.event.Location;
import com.hhplus.project.support.BaseException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record CreateLocations() {


    public record Command(
            List<LocationInfo> locationList
    ){
        public Command{
            Set<String> uniqueRegions = new HashSet<>();
            for(LocationInfo info : locationList){
                if( !uniqueRegions.add(info.regionCode) ){
                    throw new BaseException(EventException.REGION_CODE_DUPLICATE);
                }
            }
        }

        public List<Location> toDomain(){
            return locationList.stream().map(locationInfo -> new Location(
                    null,
                    locationInfo.regionCode,
                    locationInfo.city,
                    locationInfo.district,
                    locationInfo.neighborhood
            )).toList();
        }
    }

    public record Info(
            List<Long> locationIds
    ){}


    public record LocationInfo(
            String regionCode,
            String city,
            String district,
            String neighborhood
    ){}
}
