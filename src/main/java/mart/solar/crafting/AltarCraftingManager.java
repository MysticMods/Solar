package mart.solar.crafting;

import epicsquid.mysticallib.util.ListUtil;
import epicsquid.mysticallib.util.Util;
import mart.solar.enums.IEnergyEnum;
import mart.solar.particle.ParticleUtil;
import mart.solar.tile.TileEntityAltar;
import mart.solar.tile.TileEntityMenhir;
import mart.solar.util.RgbColor;
import mart.solar.util.RgbColorUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AltarCraftingManager {

    private AltarCraftingRecipe currentCraftingRecipe = null;
    private List<ItemStack> craftingItems = new ArrayList<>();
    private Map<IEnergyEnum, Integer> energyMap = new HashMap<>();

    private Map<EnergySyphon, Integer> syphonMap = new HashMap<>();
    private List<EnergySyphon> toRemove = new ArrayList<>();

    private int activeTicks = 0;
    private int intervalTicks = 0;

    private final TileEntityAltar altar;

    public AltarCraftingManager(TileEntityAltar altar){
        this.altar = altar;
    }

    public void tick(){

        if(currentCraftingRecipe == null){
            return;
        }

        this.activeTicks++;
        if(this.intervalTicks >= 0){
            this.intervalTicks++;
        }
        if(this.activeTicks > 60){
            if(altar.getWorld().isRemote){
                showEnergy();
            }
        }

        if(this.intervalTicks >= 60 || (this.activeTicks > 100 && this.currentCraftingRecipe.getCraftingItems().isEmpty())){

            this.intervalTicks = 0;
            //Check if all the items align, if so stop crafting and pop out item

            if(!checkIfCraftingFinished()){
                if(altar.getItem() != ItemStack.EMPTY){
                    addItemToCrafting();
                }
            }
        }


        for(Map.Entry<EnergySyphon, Integer> entry : syphonMap.entrySet()){
            EnergySyphon syphon = entry.getKey();
            BlockPos menhir = altar.getPos().add(syphon.pos);
            TileEntityMenhir menhirTile = (TileEntityMenhir) altar.getWorld().getTileEntity(menhir);

            if(syphon.energyEnum != menhirTile.getType()){
                toRemove.add(syphon);
                continue;
            }

            if(entry.getValue() >= 100){
                toRemove.add(syphon);

                if(!energyMap.containsKey(menhirTile.getType())){
                    energyMap.put(menhirTile.getType(), 1);
                }
                else{
                    energyMap.put(menhirTile.getType(), energyMap.get(menhirTile.getType()) + 1);
                }
            }

            entry.setValue(entry.getValue() + 1);

            BlockPos pos = altar.getPos();
            if (altar.getWorld().isRemote){
                RgbColor color = RgbColorUtil.getRuneColor(syphon.energyEnum);
                ParticleUtil.spawnParticleSolarLine(altar.getWorld(),
                        menhir.getX() + 0.5f,menhir.getY() + 0.5f,menhir.getZ() + 0.5f,
                        pos.getX() + 0.5f,pos.getY() + 0.5f,pos.getZ() + 0.5f,
                        color.getRed(), color.getGreen(), color.getBlue(), 1, 3, 100);
            }
        }

        for(EnergySyphon syphon : toRemove){
            syphonMap.remove(syphon);
        }
        toRemove.clear();
    }

    private void searchMenhirs(){
        Map<IEnergyEnum, Integer> currentEnergy = new HashMap<>();

        for(Map.Entry<EnergySyphon, Integer> entry : syphonMap.entrySet()){
            IEnergyEnum energyType = entry.getKey().energyEnum;
            if(!currentEnergy.containsKey(energyType)){
                currentEnergy.put(energyType, 1);
            }
            else{
                currentEnergy.put(energyType, currentEnergy.get(energyType) + 1);
            }
        }

        for(Map.Entry<IEnergyEnum, Integer> entry : energyMap.entrySet()){
            IEnergyEnum energyType = entry.getKey();
            if(!currentEnergy.containsKey(energyType)){
                currentEnergy.put(energyType, 1);
            }
            else{
                currentEnergy.put(energyType, currentEnergy.get(energyType) + 1);
            }
        }

        for(BlockPos menhirPos : altar.getMenhirPositions()){
            BlockPos menhir = altar.getPos().add(menhirPos);
            TileEntityMenhir menhirTile = (TileEntityMenhir) altar.getWorld().getTileEntity(menhir);

            if(menhirTile == null){
                clear();
                return;
            }

            boolean isInMap = false;
            for(Map.Entry<EnergySyphon, Integer> entry : syphonMap.entrySet()){
                if(entry.getKey().pos.equals(menhirPos)){
                    isInMap = true;
                    break;
                }
            }
            if (isInMap){
                continue;
            }

            IEnergyEnum menhirEnergyType = menhirTile.getType();
            if(menhirEnergyType == null){
                continue;
            }
            int amount = currentEnergy.getOrDefault(menhirEnergyType, 0);

            if(this.currentCraftingRecipe.getEnergyMap().get(menhirEnergyType) != null && amount < this.currentCraftingRecipe.getEnergyMap().get(menhirEnergyType)){
                syphonMap.put(new EnergySyphon(menhirPos, menhirEnergyType), 0);
                System.out.println("Added too map");
                continue;
            }
        }
    }

    private boolean checkIfCraftingFinished(){
        if(!ListUtil.stackListsMatch(this.craftingItems, this.currentCraftingRecipe.getCraftingItems())){
            return false;
        }

        if(!energyMap.equals(currentCraftingRecipe.getEnergyMap())){
            searchMenhirs();
            return false;
        }

        if(!altar.getWorld().isRemote){
            ItemStack extracted = this.currentCraftingRecipe.getResult().copy();
            altar.getWorld().spawnEntity(new EntityItem(altar.getWorld(), altar.getPos().getX(), altar.getPos().getY() + 3, altar.getPos().getZ(), extracted));
        } else {
            for(int i = 0; i < 20; i++){
                float randX = ((Util.rand.nextInt(600) -300) / 100f);
                float randY = ((Util.rand.nextInt(600) -300) / 100f);
                float randZ = ((Util.rand.nextInt(600) -300) / 100f);
                RgbColor color;
                if(Util.rand.nextInt(2) == 0){
                    color = this.currentCraftingRecipe.getColor(this.currentCraftingRecipe.getTriggerItem().getItem());
                }
                else if(this.craftingItems.size() > 0){
                    color = this.currentCraftingRecipe.getColor(this.craftingItems.get(Util.rand.nextInt(this.craftingItems.size())).getItem());
                }
                else{
                    color = this.currentCraftingRecipe.getColor(this.currentCraftingRecipe.getTriggerItem().getItem());
                }
                ParticleUtil.spawnParticleSolarLine(altar.getWorld(),
                        altar.getPos().getX() + 0.5f,altar.getPos().getY() + 3.5f,altar.getPos().getZ() + 0.5f,
                        altar.getPos().getX() + 0.5f + randX,altar.getPos().getY() + 3.5f + randY,altar.getPos().getZ() + 0.5f + randZ,
                        color.getRed(), color.getGreen(), color.getBlue(), 1, 2, 100);
            }
        }

        clear();
        return true;
    }

    public void setCurrentCraftingRecipe(AltarCraftingRecipe currentCraftingRecipe) {
        this.currentCraftingRecipe = currentCraftingRecipe;
        shootParticle();
    }

    private void shootParticle(){
        BlockPos pos = altar.getPos();
        if (altar.getWorld().isRemote){
            RgbColor color = this.currentCraftingRecipe.getColor(altar.inventory.getStackInSlot(0).getItem());
            ParticleUtil.spawnParticleSolarLine(altar.getWorld(),
                    pos.getX() + 0.5f,pos.getY() + 0.5f,pos.getZ() + 0.5f,
                    pos.getX() + 0.5f,pos.getY() + 3.5f,pos.getZ() + 0.5f,
                    color.getRed(), color.getGreen(), color.getBlue(), 1, 3, 100);
        }
    }

    private void showEnergy(){
        RgbColor color;
        if(Util.rand.nextInt(2) == 0 || this.craftingItems.isEmpty()){
            color = this.currentCraftingRecipe.getColor(this.currentCraftingRecipe.getTriggerItem().getItem());
        }
        else{
            color = this.currentCraftingRecipe.getColor(this.craftingItems.get(Util.rand.nextInt(this.craftingItems.size())).getItem());
        }
        ParticleUtil.spawnParticleSolar(altar.getWorld(),
                altar.getPos().getX() + 0.5f,altar.getPos().getY() + 3f,altar.getPos().getZ() + 0.5f,
                0, 0, 0,
                color.getRed(), color.getGreen(), color.getBlue(), 1, 3 + this.craftingItems.size(), 20);
    }

    private void addItemToCrafting(){
        Map<Item, Integer> recipeItems = new HashMap<>();
        Map<Item, Integer> holdingItems = new HashMap<>();
        for(ItemStack stack : this.currentCraftingRecipe.getCraftingItems()){
            if(recipeItems.get(stack.getItem()) == null){
                recipeItems.put(stack.getItem(), 1);
            }
            else{
                recipeItems.put(stack.getItem(), recipeItems.get(stack.getItem()) + 1);
            }
        }

        for(ItemStack stack : this.craftingItems){
            if(holdingItems.get(stack.getItem()) == null){
                holdingItems.put(stack.getItem(), 1);
            }
            else{
                holdingItems.put(stack.getItem(), holdingItems.get(stack.getItem()) + 1);
            }
        }

        Item item = altar.inventory.getStackInSlot(0).getItem();

        if(!recipeItems.containsKey(item)){
            return;
        }

        if(holdingItems.get(item) == null || recipeItems.get(item) > holdingItems.get(item)){
            this.craftingItems.add(altar.inventory.getStackInSlot(0));
            altar.inventory.extractItem(0, 1, false);
            shootParticle();
        }
    }

    private void clear(){
        this.craftingItems.clear();
        this.intervalTicks = 0;
        this.activeTicks = 0;
        this.currentCraftingRecipe = null;
        this.altar.setAltarState(TileEntityAltar.AltarState.NONE);
        this.syphonMap.clear();
        this.energyMap.clear();
    }

    public class EnergySyphon{

        private BlockPos pos;
        private IEnergyEnum energyEnum;

        public EnergySyphon(BlockPos pos, IEnergyEnum energyEnum){
            this.pos = pos;
            this.energyEnum = energyEnum;
        }

    }
}