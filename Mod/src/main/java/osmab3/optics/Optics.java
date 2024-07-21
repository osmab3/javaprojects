package osmab3.optics;

import com.google.common.eventbus.Subscribe;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;
import osmab3.optics.block.BlockFurnace2;
import osmab3.optics.tile.TileFurnace2;

@Mod(modid = Tags.MOD_ID, name = "Optics", version = Tags.MOD_VERSION)
public class Optics {

    public static final Block litfurnace2 = new BlockFurnace2(Material.ROCK).setRegistryName(Tags.MOD_ID, "litfurnace2").setTranslationKey("Furnace2");
    public static final Block furnace2 = new BlockFurnace2(Material.ROCK).setRegistryName(Tags.MOD_ID, "furnace2").setTranslationKey("Furnace2").setCreativeTab(CreativeTabs.DECORATIONS);
    public static final Block testblock = new Block(Material.ROCK).setRegistryName(Tags.MOD_ID, "decorativeblock").setTranslationKey("DecorativeBlock").setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

    public static final ItemBlock decorativeblock = (ItemBlock) new ItemBlock(testblock).setRegistryName(testblock.getRegistryName());
    public static final ItemBlock furnacetest = (ItemBlock) new ItemBlock(furnace2).setRegistryName(furnace2.getRegistryName());

    @Mod.Instance
    public static Optics Instance;

    @Mod.EventHandler
    public void construction(FMLConstructionEvent event){
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        GameRegistry.registerTileEntity(TileFurnace2.class, furnace2.getRegistryName());
    }

    @Mod.EventHandler
    public void ini(FMLInitializationEvent event){
        NetworkRegistry.INSTANCE.registerGuiHandler(Instance, new GuiEnder());
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event){
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event){
        event.getRegistry().register(furnacetest);
        event.getRegistry().register(decorativeblock);
    }

    @SubscribeEvent
    public void registerblocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(testblock);
        event.getRegistry().register(furnace2);
        event.getRegistry().register(litfurnace2);
    }

    @SubscribeEvent
    public void registermodels(ModelRegistryEvent event){
        if (FMLLaunchHandler.side() == Side.CLIENT) {
            ModelLoader.setCustomModelResourceLocation(decorativeblock, 0, new ModelResourceLocation(decorativeblock.getRegistryName(),"inventory") );
            ModelLoader.setCustomModelResourceLocation(furnacetest, 0, new ModelResourceLocation(decorativeblock.getRegistryName(), "inventory"));
        }
    }
}

