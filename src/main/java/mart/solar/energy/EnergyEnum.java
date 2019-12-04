package mart.solar.energy;

public enum EnergyEnum implements IEnergyEnum {

    SOLAR("solar"),
    LUNAR("lunar"),
    FIRE("fire"),
    WATER("water"),
    EARTH("earth"),
    WIND("wind"),
    TIME("time"),
    LIFE("life"),
    NONE("none");

    private String name;

    EnergyEnum(String name){
        this.name = name;
    }

    @Override
    public String getName(){
        return name;
    }

    public static EnergyEnum getFromName(String s){
        for(EnergyEnum type : EnergyEnum.values()){
            if(type.getName().equalsIgnoreCase(s)){
                return type;
            }
        }

        return null;
    }
}
