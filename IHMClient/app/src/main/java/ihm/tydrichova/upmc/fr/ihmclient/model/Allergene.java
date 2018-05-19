package ihm.tydrichova.upmc.fr.ihmclient.model;

public enum Allergene {
    LACTOSE,
    ARACHIDE,
    EAU,
    SALADE,
    GLUTEN;

    @Override
    public String toString() {
        return this.name().toLowerCase().replaceFirst(
                String.valueOf(this.name().charAt(0)).toLowerCase(), String.valueOf(this.name().charAt(0)));
    }
}
