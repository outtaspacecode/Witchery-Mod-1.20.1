package net.outta_space.witchery.block.custom.crobblock;

import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class WitcheryCropBlock extends CropBlock {
    public WitcheryCropBlock(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public IntegerProperty getAgeProperty() {
        return super.getAgeProperty();
    }

    @Override
    public int getMaxAge() {
        return super.getMaxAge();
    }
}
