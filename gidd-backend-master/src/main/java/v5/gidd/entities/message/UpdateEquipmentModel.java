package v5.gidd.entities.message;

import v5.gidd.entities.Inspectable;
import v5.gidd.throwable.GiddException;

public class UpdateEquipmentModel implements Inspectable {
    private Long id;
    private String name;
    private Long activity;
    private Long claimedByUser;

    public UpdateEquipmentModel() {
    }

    public UpdateEquipmentModel(Long id, String name, Long activity, Long claimedByUser) {
        this.id = id;
        this.name = name;
        this.activity = activity;
        this.claimedByUser = claimedByUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getActivity() {
        return activity;
    }

    public void setActivity(Long activity) {
        this.activity = activity;
    }

    public Long getClaimedByUser() {
        return claimedByUser;
    }

    public void setClaimedByUser(Long claimedByUser) {
        this.claimedByUser = claimedByUser;
    }

    @Override
    public void inspect() {
        if (name == null || name.equals(""))
            throw new GiddException(Status.EQUIPMENT_INVALID_NAME, "Navn på utstyr er påkrevd");
        if (activity == null || activity < 0)
            throw new GiddException(Status.ACTIVITY_NOT_FOUND, "Kunne ikke endre utstyrliste. Aktiviteten er ugyldig.");
    }
}
