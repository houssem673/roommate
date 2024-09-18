package roommate.application.helper;

import roommate.domain.model.Equipment;
import roommate.web.request_entities.FilterForm;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class FilterBuilder {

    private static final DateTimeFormatter DATUM_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter ZEIT_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private Set<Equipment> equipments = new HashSet<>();



    public FilterBuilder withDate(LocalDate date){
        this.date = date;
        return this;

    }
    public FilterBuilder withStartTime(LocalTime start){
        this.start = start;
        return this;

    }
    public FilterBuilder withEndTime(LocalTime end){
        this.end = end;
        return this;

    }
    public FilterBuilder withEquipment(Set<Equipment> equipments){
        this.equipments = equipments;
        return this;

    }
    public FilterForm build(){
        return new FilterForm();

    }


}
