package roommate.domain.model;


import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public  class Workspace {

    private  final Long id;
    private String name;
    private Set<Equipment> equipments;
    private String roomname;
    public Workspace(Long id,String name, Set<Equipment> equipments, String roomname) {
        this.id = id;
        this.name = name;
        this.equipments = equipments;
        this.roomname = roomname;
    }
    public Workspace(Long id,String name,String roomname) {
        this.id = id;
        this.name = name;
        this.roomname = roomname;
    }
    public Workspace(String name) {
       this(null,name,new HashSet<>(), null);

    }

    public Workspace() {
        this(null,null,new HashSet<>(),null);

    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Workspace(String name, String roomname) {
        this(null,name,new HashSet<>(), roomname);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Equipment> getEquipments() {
        return Collections.unmodifiableSet(equipments);
//        return equipments.stream().map(eq -> new Equipment(eq.description())).collect(Collectors.toSet());
    }
    public Workspace addEquipment(Set<Equipment> equipments) {
        return new Workspace(this.id,this.getName(),equipments,this.roomname);
    }

    public String getRoomname() {
        return roomname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workspace workspace = (Workspace) o;
        return Objects.equals(id, workspace.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
