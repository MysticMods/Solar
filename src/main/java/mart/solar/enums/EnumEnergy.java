package mart.solar.enums;

public enum EnumEnergy implements IEnergyEnum{

    SOLAR("solar"),
    LUNAR("lunar"),
    FIRE("fire"),
    WATER("water"),
    EARTH("earth"),
    WIND("wind"),
    TIME("time"),
    LIFE("life");

    private String name;

    EnumEnergy(String name){
        this.name = name;
    }

    @Override
    public String getName(){
        return name;
    }

    public static EnumEnergy getFromName(String s){
        for(EnumEnergy type : EnumEnergy.values()){
            if(type.getName().equalsIgnoreCase(s)){
                return type;
            }
        }

        return null;
    }

}
